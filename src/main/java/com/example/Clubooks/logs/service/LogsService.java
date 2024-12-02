package com.example.Clubooks.logs.service;

import com.example.Clubooks.logs.dtos.LogsDTO;
import com.example.Clubooks.logs.model.LogUser;
import com.example.Clubooks.logs.repository.LogsRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;

import java.time.Instant;
import java.util.UUID;

@Service
public class LogsService {
    @Autowired
    private Validator validator;

    @Autowired
    private LogsRepository logsRepository;

    @Transactional
    public String createLogs(LogsDTO data) {
        Set<ConstraintViolation<LogsDTO>> violations = validator.validate(data);
        if(!violations.isEmpty()) {
            throw new IllegalArgumentException("Invalid User data: " + violations);
        }

        LogUser logUser = new LogUser();

        logUser.setAction(data.action());
        logUser.setUserId(data.userId());
        logUser.setSchema(data.schema());
        logUser.setOldData(data.oldUser());
        logUser.setNewData(data.attUser());
        logUser.setRecordId(UUID.randomUUID().toString());
        logUser.setTimestamp(Instant.now());

        logsRepository.save(logUser);

        return "Log criado com sucesso";
    }
}
