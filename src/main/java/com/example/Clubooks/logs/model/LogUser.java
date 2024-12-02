package com.example.Clubooks.logs.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "logs")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogUser {

    private String userId;
    private String action;
    private String schema;
    private String recordId;
    private String oldData;
    private String newData;
    private Instant timestamp;

}
