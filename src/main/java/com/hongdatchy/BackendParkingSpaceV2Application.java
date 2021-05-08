package com.hongdatchy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import com.hongdatchy.entities.data.DataCamAndDetector;
import com.hongdatchy.entities.data.Slot;
import com.hongdatchy.getData.GetDataDetector;
import com.hongdatchy.getData.GetTime;
import com.hongdatchy.repository.DataCamAndDetectorRepo;
import com.hongdatchy.repository.SlotRepo;
import com.hongdatchy.service_impl.GoogleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;


@SpringBootApplication
@EnableSwagger2
public class BackendParkingSpaceV2Application implements CommandLineRunner {

    @Autowired
    SlotRepo slotRepo;

    @Autowired
    GoogleService googleService;

    @Autowired
    DataCamAndDetectorRepo dataCamAndDetectorRepo;

    List<String> rows = new ArrayList<>();

    List<Slot> slots = new ArrayList<>();


    public static void main(String[] args) {
        SpringApplication.run(BackendParkingSpaceV2Application.class, args);
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.ant("/**")).build();
    }

    @Value("${pathCamStatus}")
    String pathDataCam;
    @Value("${pathDetectorStatus}")
    String pathDetectorStatus;

    @Autowired
    com.fasterxml.jackson.databind.ObjectMapper objectMapper;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("******************** Start server ********************");
//        set timezone cho backend
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+7"));
//        set timezone cho controller
        objectMapper.setTimeZone(TimeZone.getDefault());

        //update();
    }

    public void update() throws FileNotFoundException, InterruptedException, UnsupportedEncodingException {
        while (true){
            if(!getDataCam()){
                System.out.println("file data cam does not exist");
            }
            writeDataDetector();
            Thread.sleep(5000);
        }
    }

    public boolean getDataCam() throws FileNotFoundException {
        File file = new File (pathDataCam);
        if (!file.exists()) {
            return false;
        }

        Scanner myReader = new Scanner(file);
        List<String> newRows = new ArrayList<>();
        while (myReader.hasNextLine()) {
            String row = myReader.nextLine();
            newRows.add(row);
        }
        if(!rows.equals(newRows)) {
            System.out.println("Data cam has changed");
            rows.clear();
            rows.addAll(newRows);
            for (int i = 1; i< rows.size(); i++){
                boolean status = rows.get(i).split(" ")[1].equals("1");
//                fake field cho slot
                int fieldId = 1;
//                so thu tu sua slot trong field
                int stt = Integer.parseInt(rows.get(i).split(" ")[0]) - 1;
                Slot oldSlot = slotRepo.findAll().stream()
                        .filter(slot -> slot.getFieldId() == fieldId)
                        .collect(Collectors.toList())
                        .get(stt);
                oldSlot.setStatusCam(status);
                slotRepo.createAndUpdate(oldSlot);
                //            dataCamAndDetector
                dataCamAndDetectorRepo.createAndUpdate(DataCamAndDetector.builder()
                        .statusCam(status)
                        .slotId(stt)
                        .time(GetTime.getTime(rows.get(0)))
                        .build());
            }
            System.out.println("Data cam has updated successfully");
        }
        myReader.close();
        return true;
    }

    public void writeDataDetector() throws  FileNotFoundException, UnsupportedEncodingException {
        List<Slot> newSlots = slotRepo.findAll();
        if(!slots.equals(newSlots)){
            PrintWriter writer = new PrintWriter(pathDetectorStatus, "UTF-8");
            for (Slot slot : newSlots){
                writer.println(slot.getId() + " " + (slot.getStatusDetector() == null ? "2" : slot.getStatusDetector() ? "1": "0"));
            }
            writer.close();
            slots.clear();
            slots.addAll(newSlots);
        }
    }
}
