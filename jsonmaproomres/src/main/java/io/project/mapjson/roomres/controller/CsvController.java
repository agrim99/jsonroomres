package io.project.mapjson.roomres.controller;

import io.project.mapjson.roomres.readcsv.CsvReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class CsvController {

    private CsvReader csvReader;

    @Autowired
    public CsvController(CsvReader csvReader){
        this.csvReader = csvReader;
    }

    @GetMapping("/publish")
    public String post(){
        csvReader.producedata();
        return "Published Successfully";
    }


}
