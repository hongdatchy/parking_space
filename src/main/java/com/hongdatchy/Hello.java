package com.hongdatchy;

import com.hongdatchy.repository.DetectorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Hello {

    @Autowired
    private DetectorRepo detectorRepo;

    private static DetectorRepo a;
    @PostConstruct
    private void initStaticDao () {
        a = this.detectorRepo;
    }
    public static void main(String[] args) {
        System.out.println(a.findAll());
    }

}
