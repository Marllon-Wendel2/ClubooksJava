package com.example.Clubooks.logs.service;

import com.example.Clubooks.logs.dtos.LogsDTO;
import com.example.Clubooks.logs.model.LogUser;
import com.example.Clubooks.logs.repository.LogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class LogsService {

    @Autowired
    private LogsRepository logsRepository;

    @Transactional
    public String createLogs(LogUser data) {

        LogUser logUser = new LogUser();

        logUser.setAction(data.getAction());
        logUser.setUserId(data.getUserId());
        logUser.setSchema(data.getSchema());
        logUser.setOldData(data.getOldData());
        logUser.setNewData(data.getNewData());
        logUser.setTimestamp(Instant.now());

        logsRepository.save(logUser);

        return "Log criado com sucesso";
    }
}
