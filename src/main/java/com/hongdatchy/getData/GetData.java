package com.hongdatchy.getData;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.StringReader;
import java.util.stream.Collectors;

import com.hongdatchy.entities.data.Detector;
import com.hongdatchy.entities.data.MyPackage;
import com.hongdatchy.entities.data.Slot;
import com.hongdatchy.repository.ContractRepo;
import com.hongdatchy.repository.DetectorRepo;
import com.hongdatchy.repository.PackageRepo;
import com.hongdatchy.repository.SlotRepo;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

@Component
public class GetData {

    private static String originator = "admin:admin";
    private static String cseProtocol = "http";
    private static String cseIp = "127.0.0.1";
    private static int csePort = 8080;
    private static String cseId = "in-cse";
    private static String cseName = "in-name";

    private static String aeMonitorName = "mymonitor";
    private static String aeProtocol = "http";
    private static String aeIp = "127.0.0.1";
    private static int aePort = 1600;
    private static String subName = "monitorsub";
    private static String targetCse = "mn-cse/mn-name";

    private static String csePoa = cseProtocol + "://" + cseIp + ":" + csePort;
    private static String appPoa = aeProtocol + "://" + aeIp + ":" + aePort;

    private static JSONObject ae;
    private static JSONObject sub;

    @Autowired
    private DetectorRepo detectorRepo;
    @Autowired
    private SlotRepo slotRepo;
    @Autowired
    private ContractRepo contractRepo;

    @Autowired
    private PackageRepo packageRepo;

    private static DetectorRepo detectorRepoStatic;
    private static SlotRepo slotRepoStatic;
    private static ContractRepo contractRepoStatic;
    private static PackageRepo packageRepoStatic;

    @PostConstruct
    private void initStatic(){
        detectorRepoStatic = this.detectorRepo;
        slotRepoStatic = this.slotRepo;
        contractRepoStatic = this.contractRepo;
        packageRepoStatic = this.packageRepo;
    }

    public static void main(String[] args) throws JSONException, InterruptedException {

        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress(aePort), 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.createContext("/", new MyHandler());
        server.setExecutor(Executors.newCachedThreadPool());
        server.start();

        JSONArray array = new JSONArray();
        array.put(appPoa);
        JSONObject obj = new JSONObject();
        obj.put("rn", aeMonitorName);
        obj.put("api", 12346);
        obj.put("rr", true);
        obj.put("poa", array);
        ae = new JSONObject();
        ae.put("m2m:ae", obj);
        // System.out.println("[INFO]"+ "Create AE " + aeMonitorName);
        RestHttpClient.post(originator, csePoa + "/~/" + cseId + "/" + cseName,
                ae.toString(), 2);

        array = new JSONArray();
        array.put("/" + cseId + "/" + cseName + "/" + aeMonitorName);
        obj = new JSONObject();
        obj.put("nu", array);
        obj.put("rn", subName);
        obj.put("nct", 2);
        sub = new JSONObject();
        sub.put("m2m:sub", obj);
        // System.out.println("\n[INFO]"+ "Sub to " + targetCse);

        RestHttpClient.post(originator, csePoa + "/~/" + targetCse,
                sub.toString(), 23);
        // WHY ????? --> notify AE create

        // System.out.println("\n[INFO] Discover all containers in " + csePoa +
        // "/~/" + targetCse );

        String parentCnt = targetCse;
        subCnt(parentCnt);

    }

    static void subCnt(String parentCnt) throws JSONException, InterruptedException {
        HttpResponse httpResponse = RestHttpClient.get(originator, csePoa
                + "/~/" + parentCnt + "?fu=1&ty=3");
        JSONObject result = new JSONObject(httpResponse.getBody());
        JSONArray uril_arr = result.getJSONArray("m2m:uril");
        for (Object urlCnt : uril_arr) {
      //  for (int i=0; i < uril_arr.length(); i++) {
//            System.out.println("\n[doSubCnt] Sub to uri container " + urlCnt);
//            RestHttpClient.post(originator, csePoa + "/~" + urlCnt,
//                   sub.toString(), 23);
            if(((String) urlCnt).indexOf("DATA_LORA")==-1){
                System.out.println("\n[doSubCnt] Sub to uri container " + urlCnt);
                RestHttpClient.post(originator, csePoa + "/~" + urlCnt,
                        sub.toString(), 23);
            }
//            RestHttpClient.post(originator, csePoa + "/~" + uril_arr.getJSONObject(i),
//                    sub.toString(), 23);
            Thread.sleep(2000);
        }
    }

    static String getParent(String uri, String name) {
        String strParent = null;
        String regex = ".*/(?<parentName>\\w+)." + name + ".*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(uri);
        System.out.println(uri + "\n" + name + "\n" + regex);
        while (matcher.find()) {
            strParent = matcher.group("parentName");
        }

        return strParent;
    }
    static int count =0;
    static class MyHandler implements HttpHandler {

        HashMap<String, String> map = new HashMap<>();
        public void handle(HttpExchange httpExchange) {

//            System.out.println(detectorRepoStatic.findAll());
            try {
                InputStream in = httpExchange.getRequestBody();

                String requestBody = "";
                int i;
                char c;
                while ((i = in.read()) != -1) {
                    c = (char) i;
                    requestBody = (String) (requestBody + c);
                }

                JSONObject json = new JSONObject(requestBody);

                if (json.getJSONObject("m2m:sgn").has("m2m:vrq")) {

                } else if (json.getJSONObject("m2m:sgn").getJSONObject("m2m:nev").getJSONObject("m2m:rep").has("m2m:ae")) {

                    // read in to ae
                    JSONObject rep = json.getJSONObject("m2m:sgn")
                            .getJSONObject("m2m:nev").getJSONObject("m2m:rep")
                            .getJSONObject("m2m:ae");

                    int ty = rep.getInt("ty");
                    // System.out.println("LOL Sure AE. Resource type: " + ty);

                    if (ty == 2) {
                        // ty = 2 => print info new AE
                        String aeName = rep.getString("rn");
                        System.out
                                .println("\n[EVENT] New AE has been registred: "
                                        + aeName);
                        System.out.println("\n[ACTION] Wait 3 seconds");
                        Thread.sleep(3000);
                        System.out.println("\n[ACTION] Sub to container in AE "
                                + aeName);
                        String parentCnt = targetCse + "/" + aeName;
                        subCnt(parentCnt);

                    }
                } else if (json.getJSONObject("m2m:sgn").getJSONObject("m2m:nev")
                        .getJSONObject("m2m:rep").has("m2m:cnt")) {
                    JSONObject rep = json.getJSONObject("m2m:sgn")
                            .getJSONObject("m2m:nev").getJSONObject("m2m:rep")
                            .getJSONObject("m2m:cnt");
                    String cntName = rep.getString("rn");
                    String parent = getParent(rep.getString("ol"), cntName);
                    System.out.println("\n[EVENT] New CNT has been registred: "
                            + cntName + " in " + parent);
                    String parentCnt = targetCse + "/" + "LORAGW/DETECTORS/"
                            + cntName;

                    System.out.println("[ACTION] Discover all containers in "
                            + csePoa + "/~/" + parentCnt);
                    subCnt(parentCnt);
                } else {
                    JSONObject rep = json.getJSONObject("m2m:sgn")
                            .getJSONObject("m2m:nev").getJSONObject("m2m:rep")
                            .getJSONObject("m2m:cin");

                    int ty = rep.getInt("ty");
                    if (ty == 4) {

                        System.out
                                .println(" ---- SOP : Start processing message -----");

                        String ciName = rep.getString("rn");
                        String content = rep.getString("con");

                        System.out.println("\n[EVENT] New Content Instance "
                                + ciName + " has been created in "
                                + rep.getString("pi"));
                        System.out.println("[INFO] Content: " + content);

                        //Reading obj xml to get information.
                        Document doc = convertStringToXMLDocument(content);
                        doc.getDocumentElement().normalize();
                        NodeList strList = doc.getElementsByTagName("str");
    //                    System.out.println(strList);

                        for (int ii = 0; ii < strList.getLength(); ii++) {
                            Node node = strList.item(ii);
                            if (node.getNodeType() == Node.ELEMENT_NODE) {
                                Element str = (Element) node;

                                System.out.println(str.getAttribute("name")
                                        + "=" + str.getAttribute("val"));
                                map.put(str.getAttribute("name"), str.getAttribute("val"));
                            }
                        }

                        Element Int = (Element) doc.getElementsByTagName("int")
                                .item(0);
                        if (Int != null) {
                            System.out.println(Int.getAttribute("name") + " = "
                                    + Int.getAttribute("val"));
                            map.put(Int.getAttribute("name"), Int.getAttribute("val"));
                        }
                        System.out
                                .println(" ---- EOP : End of reading a message ty=4 -----");
                    }

                }
                List<String> l = new ArrayList<>(map.keySet());
//                System.out.println(l);

                if(l.size() == 8){

                    System.out.println(
                            packageRepoStatic.create(
                                    MyPackage.builder()
                                            .id(map.get("ID"))
                                            .batteryLevel(map.get("Battery Level"))
                                            .communicationLevel(map.get("Communication Level"))
                                            .location(map.get("Location"))
                                            .nodeAddress(map.get("Node Address"))
                                            .packetNumber(Integer.parseInt(map.get("Packet Number")))
                                            .state(map.get("State").equals("1"))
                                            .time(map.get("Time"))
                                            .build()
                            )
                    );
                    String time = map.get("Time");
                    int year = Integer.parseInt(time.substring(0,4));
//                        month in calendar of java must - 1
                    int month = Integer.parseInt(time.substring(4,6)) -1 ;
                    int day = Integer.parseInt(time.substring(6,8));
                    int hour = Integer.parseInt(time.substring(8,10));
                    int minute = Integer.parseInt(time.substring(10,12));
                    int second = Integer.parseInt(time.substring(12,14));
                    Calendar cal = Calendar.getInstance();
                    cal.set(Calendar.YEAR, year);
                    cal.set(Calendar.MONTH, month);
                    cal.set(Calendar.DAY_OF_MONTH, day);
                    cal.set(Calendar.HOUR_OF_DAY, hour);
                    cal.set(Calendar.MINUTE, minute);
                    cal.set(Calendar.SECOND, second);
                    cal.set(Calendar.MILLISECOND, 0);
//                        fake field cho detector
                    int fieldId = 1;

                    List<Slot> slots = slotRepoStatic.findAll().stream()
                            .filter(slot -> slot.getFieldId() ==fieldId)
                            .collect(Collectors.toList());
                    Detector oldDetector = detectorRepoStatic.findById(Integer.parseInt(map.get("ID")));
                    Detector detector = Detector.builder()
                            .id(Integer.parseInt(map.get("ID")))
                            .addressDetector(map.get("Node Address") == null ? "255.255.0.x" : map.get("Node Address"))
                            .lastTimeSetup(oldDetector == null
                                    || oldDetector.getSlotId() != Integer.parseInt(map.get("Location"))
                                    ? cal.getTime() : oldDetector.getLastTimeSetup())
                            .lastTimeUpdate(cal.getTime())
                            .batteryLevel(map.get("Battery Level"))
                            .operatingMode("Mode 1")
                            .slotId(slots.get(Integer.parseInt(map.get("Location"))-1).getId())
                            .loracomLevel("Loracom lever 1")
                            .gatewayId(1)
                            .build();
//                        detector
                    System.out.println(detector);
                    detectorRepoStatic.createAndUpdate(detector);
//                        slot
                    if(oldDetector != null){
                        Slot slot = slotRepoStatic.findById(detector.getSlotId());
                        slot.setStatus(map.get("State").equals("1"));
                        slotRepoStatic.createAndUpdate(slot);
                    }
                }

                String responseBudy = "";
                byte[] out = responseBudy.getBytes("UTF-8");
                httpExchange.sendResponseHeaders(200, out.length);
                OutputStream os = httpExchange.getResponseBody();
                os.write(out);
                os.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private static Document convertStringToXMLDocument(String xmlString) {
        // Parser that produces DOM object trees from XML content
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        // API to obtain DOM Document instance
        DocumentBuilder builder = null;
        try {
            // Create DocumentBuilder with default configuration
            builder = factory.newDocumentBuilder();

            // Parse the content to Document object
            Document doc = builder.parse(new InputSource(new StringReader(
                    xmlString)));
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}
