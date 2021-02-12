package io.project.mapjson.reqroomres.reqroomres.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Repository
@Converter(autoApply = true)
public class StringMapConverter implements AttributeConverter<Map<String, Double>, String> {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<String, Double> occupancy_to_price){

        if(occupancy_to_price == null){
            return "{}";
        }


        try{
            return mapper.writeValueAsString(occupancy_to_price);

        }catch(IOException e){
            throw new IllegalArgumentException("Error converting map to JSON", e);

        }

    }


    @Override
    public Map<String, Double> convertToEntityAttribute(String occupancy_to_price){

        if(occupancy_to_price == null){
            return new HashMap<>();
        }


        try{
            return mapper.readValue(occupancy_to_price, new TypeReference<Map<String, Double>>() {});
            // check to override functions of typereference
        }catch(IOException e){
            throw new IllegalArgumentException("Error converting JSON to map", e);

        }


    }




}
