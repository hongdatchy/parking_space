package com.hongdatchy;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
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
        getDataCam();
    }
    public void getDataCam() throws FileNotFoundException, InterruptedException {
        List<String> rows = new ArrayList<>();
        while (true){
            File file = new File ("C:/Users/Microsoft Windows/OneDrive/Desktop/test-backend/cam.txt");
            if (!file.exists()) {
                continue;
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
                for (String row: rows){
                    boolean status = row.split(" ")[1].equals("1");
//                fake field cho slot
                    int fieldId = 1;
//                so thu tu sua slot trong field
                    int stt = Integer.parseInt(row.split(" ")[0]) - 1;
                    Slot oldSlot = slotRepo.findAll().stream()
                            .filter(slot -> slot.getFieldId() == fieldId)
                            .collect(Collectors.toList())
                            .get(stt);
                    oldSlot.setStatusCam(status);
                    slotRepo.createAndUpdate(oldSlot);
                }
                System.out.println("Data cam has updated successfully");
            }
            myReader.close();
            Thread.sleep(5000);
        }
    }
}
