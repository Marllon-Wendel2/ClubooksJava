package com.example.Clubooks.logs.controller;

import com.example.Clubooks.logs.dtos.LogsDTO;
import com.example.Clubooks.logs.model.LogUser;
import com.example.Clubooks.logs.service.LogsService;
import com.example.Clubooks.user.dto.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logs")
public class LongController {

    @Autowired
    private LogsService logsService;

    @PostMapping
    public ResponseEntity<ApiResponse> createLog(@RequestBody @Valid LogUser data) {

        try {
            String message = logsService.createLogs(data);

            return ResponseEntity.ok(new ApiResponse(true, message));
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}