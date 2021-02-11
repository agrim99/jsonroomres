package io.project.mapjson.roomres.listener;

import io.project.mapjson.roomres.model.RoomReservation;
import io.project.mapjson.roomres.repository.RoomReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @Autowired
    private RoomReservationRepository roomReservationRepository;

    @KafkaListener(topics = "Kafka_mappednew", groupId = "group_json", containerFactory = "kafkaListenerContainerFactory")
    public void consumeJson(RoomReservation roomReservation){

        System.out.println("Consumed Message");
        roomReservationRepository.save(roomReservation);

    }


}
