package com.hongdatchy;

import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.stream.Collectors;

import com.hongdatchy.entities.data.Slot;
import com.hongdatchy.getData.GetDataDetector;
import com.hongdatchy.repository.SlotRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableSwagger2
public class BackendParkingSpaceV2Application implements CommandLineRunner {

    @Autowired
    SlotRepo slotRepo;

    public static void main(String[] args) {
        SpringApplication.run(BackendParkingSpaceV2Application.class, args);
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.ant("/**")).build();
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("******************** Start server ********************");
        GetDataDetector.main(args);


        int i;
        char c;
        while (true){
            int row = 0;
            File file = new File ("C:/Users/Microsoft Windows/OneDrive/Desktop/test-backend/cam.txt");
            if (!file.exists()) {
                continue;
            }
            FileReader fr =
                    new FileReader("C:\\Users\\Microsoft Windows\\OneDrive\\Desktop\\test-backend\\cam.txt");
            String data = "";
            while ((i=fr.read()) != -1){
                c = (char) i ;
                if(c == '\n'){
                    row ++;
                }
                data += c;
            }
            row ++;
            processNewDataCam(data, row);

            Thread.sleep(1000);
        }
    }

    public void processNewDataCam(String data, int row){

        String[] rows = data.split("\\r?\\n");
        for (int i = 0; i< row; i++){
            String status = rows[i].split(" ")[1];
            System.out.println(rows[i]);
//            fake field cho slot
            int fieldId =1;
            Slot oldSlot = slotRepo.findAll().stream()
                    .filter(slot -> slot.getFieldId() == fieldId)
                    .collect(Collectors.toList())
                    .get(Integer.parseInt(rows[i].split(" ")[0]) -1);
            oldSlot.setStatusCam(status.equals("1"));
            slotRepo.createAndUpdate(oldSlot);
        }
    }
}
