package com.backend.challenge.rockets.repository;

import com.backend.challenge.rockets.messages.Message;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.TopicPartitionOffset;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MessageConsumer {

    private final KafkaTemplate<String, Message> template;

    public MessageConsumer(KafkaTemplate<String, Message> template) {
        this.template = template;
    }


    public List<Message> get() {
        Set<TopicPartitionOffset> topicPartitionOffsets = template
                .partitionsFor("rockets")
                .stream()
                .map(partitionInfo -> new TopicPartitionOffset("rockets", partitionInfo.partition(), 2L))
                .collect(Collectors.toSet());

        ConsumerRecords<String, Message> results = this.template.receive(topicPartitionOffsets, Duration.ofSeconds(5));
        List<Message> messages = new ArrayList<>();
        results.forEach(result -> {
            messages.add(result.value());
        });
        return messages;
    }
}
