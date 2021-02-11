package io.project.mapjson.roomres.readcsv;

import io.project.mapjson.roomres.model.RoomReservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class CsvReader {

    @Autowired
    private KafkaTemplate<String, RoomReservation> kafkaTemplate;

    private static final String TOPIC = "Kafka_mappednew";

    public void producedata() {

        String line = "";
        String splitBy = ",";

        try {
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/Reservations.csv"));
            while ((line = br.readLine()) != null) {
                String[] data = line.split(splitBy);

                Long hotel_id = Long.parseLong(data[0]);
                Date date = new SimpleDateFormat("dd/MM/yyyy").parse(data[1]);
                String room_category_id = data[2];
                Map<String, Double> occupancy_to_price = new HashMap<>();

                Integer data_size = data.length;
                for(Integer data_index = 3; data_index < data_size; data_index++){

                    Integer occupancy = data_index-2;
                    String key = occupancy.toString();
                    Double value = Double.parseDouble(data[data_index]);
                    occupancy_to_price.put(key, value);

                    if(data_index == data_size-1){

                        while(occupancy < 10){
                            occupancy++;
                            key = occupancy.toString();
                            occupancy_to_price.put(key, value);
                        }

                    }

                }

                RoomReservation roomReservation = new RoomReservation(hotel_id, date, room_category_id, occupancy_to_price);

                kafkaTemplate.send(TOPIC, roomReservation);


            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println("CSV Read");

    }


}
