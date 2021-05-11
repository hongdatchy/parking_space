package com.hongdatchy.service_impl;

import com.hongdatchy.entities.data.Contract;
import com.hongdatchy.entities.data.Field;
import com.hongdatchy.entities.data.User;
import com.hongdatchy.entities.json.FieldJson;
import com.hongdatchy.entities.payload.*;
import com.hongdatchy.repository.BlackListRepo;
import com.hongdatchy.repository.ContractRepo;
import com.hongdatchy.repository.UserRepo;
import com.hongdatchy.service.ContractService;
import com.hongdatchy.service.FieldService;
import com.hongdatchy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService_Impl implements UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    ContractRepo contractRepo;

    @Autowired
    BlackListRepo blackListRepo;

    @Autowired
    FieldService fieldService;

    @Autowired
    ContractService contractService;

    @Value("${timeConditionsToOrder}")
    String timeConditionsToOrder;

    @Override
    public boolean login(LoginForm loginForm) {
        return userRepo.login(loginForm);
    }

    @Override
    public boolean register(RegisterForm registerForm) {
        return userRepo.register(registerForm);
    }

    @Override
    public User createAndUpdate(UserPayload userPayload) {
        return userRepo.createAndUpdate(payload2Data(userPayload));
    }

    @Override
    public boolean delete(int id) {
        return userRepo.delete(id);
    }

    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    public Contract book(BookPayload bookPayload, String email) {
        User user = userRepo.findByEmail(email);
        if(user == null){
            return null;
        }
        FieldJson fieldJson = fieldService.data2Json(new Field(bookPayload.getFieldId(),"","","","","",50000.0,"",new BigDecimal("0.0"), ""));
        if(fieldJson.getTotalSlot() > fieldJson.getBusySlot()/2 + fieldJson.getTotalBook()
            && bookPayload.getTimeInBook().getTime() < bookPayload.getTimeOutBook().getTime()
            && bookPayload.getTimeInBook().getTime() - new Timestamp(new Date().getTime()).getTime() >= Integer.parseInt(timeConditionsToOrder)// 30 minute
        ){
            return userRepo.book(bookPayload, user);
        }else {
            return null;
        }
    }

    @Override
    public boolean changePass(ChangePassForm changePassForm, String email) {
        User user = userRepo.findByEmail(email);
        return user != null && userRepo.changePass(changePassForm, user);
    }

    public User payload2Data(UserPayload userPayload){
        return User.builder()
                .id(userPayload.getId())
                .address(userPayload.getAddress())
                .idNumber(userPayload.getIdNumber())
                .equipment(userPayload.getEquipment())
                .email(userPayload.getEmail())
                .password(userPayload.getPassword())
                .lastTimeAccess(null)
                .phone(userPayload.getPhone())
                .birth(userPayload.getBirth())
                .image(userPayload.getImage())
                .sex(userPayload.getSex())
                .build();
    }

    @Override
    public boolean verifyAccount(String mail, String code) {
        return userRepo.verifyAccount(mail, code);
    }

    @Override
    public Contract updateTime(TimeUpdateForm timeUpdateForm, String email) throws ParseException {
        User user = userRepo.findByEmail(email);
        List<Contract> contracts = contractRepo.findAll().stream()
                .filter(contract -> (
                            contract.getId()== timeUpdateForm.getContractId()
                        ))
                .collect(Collectors.toList());
        if(contracts.size() == 0){
            return null;
        }
        Contract contract = contracts.get(0);
        if(!contract.getUserId().equals(user.getId())){
            return null;
        }
        contract.setTimeCarIn(getTime(timeUpdateForm.getTimeCarIn()));
        contract.setTimeCarOut(getTime(timeUpdateForm.getTimeCarOut()));
        contract.setStatus("V");
        return contractRepo.createAndUpdate(contract);
    }

    @Override
    public List<Contract> getListContract(String email) {
        User user = userRepo.findByEmail(email);
        if(user == null){
            return null;
        }
        return contractRepo.findAll().stream()
                .filter(contract -> (contract.getUserId().equals(user.getId())))
                .collect(Collectors.toList());
    }

    @Override
    public Contract updateContractForUser(ContractPayload contractPayload, String email) {
        User user = userRepo.findByEmail(email);
        boolean isValidateContract = contractRepo.findAll().stream()
                .filter(contract1 -> (contract1.getId().equals(contractPayload.getId())))
                .count() != 0;
        if(!isValidateContract
                || user == null
                || !contractPayload.getUserId().equals(user.getId())
                || contractPayload.getTimeInBook().getTime() >= contractPayload.getTimeOutBook().getTime()){
            return null;
        }
        return contractRepo.createAndUpdate(contractService.payload2data(contractPayload));
    }

    Timestamp getTime(String time) throws ParseException {
        Timestamp timestamp = null;
        if(!time.equals("")){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            Date parsedDate = dateFormat.parse(time);
            timestamp = new Timestamp(parsedDate.getTime());
        }
        return timestamp;
    }
}
