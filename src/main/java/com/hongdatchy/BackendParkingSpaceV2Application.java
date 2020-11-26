package com.hongdatchy;

import com.hongdatchy.entities.data.Contract;
import com.hongdatchy.entities.data.Slot;
import com.hongdatchy.getData.GetData;
import com.hongdatchy.repository.ContractRepo;
import com.hongdatchy.repository.SlotRepo;
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

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@EnableSwagger2
public class BackendParkingSpaceV2Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(BackendParkingSpaceV2Application.class, args);
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.ant("/**")).build();
    }

    @Autowired
    SlotRepo slotRepo;

    @Autowired
    ContractRepo contractRepo;

    @Value("${timeRefreshDataSlot}")
    String timeRefreshDataSlot;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("*** Start server ***");
        GetData initGetData = new GetData();
        initGetData.main(args);
        while (true){
            List<Contract> contracts = contractRepo.findAll().stream()
                    .filter(contract -> contract.getTimeOutBook().getTime() - new Date().getTime()<=0
                    && contract.getTimeOutBook().getTime() - new Date().getTime() >= -Integer.valueOf(timeRefreshDataSlot))
                    .collect(Collectors.toList());
            System.out.println("List expired contract:\n"+contracts);
            if(contracts.size() != 0){
                for (Contract contract: contracts) {
                    Slot slot = slotRepo.findById(contract.getSlotId());
                    slot.setStatus(false);
                    slotRepo.createAndUpdate(slot);
                }
            }
            Thread.sleep(Integer.valueOf(timeRefreshDataSlot));
        }



    }
}
