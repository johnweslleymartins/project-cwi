package com.wordpress.sicredi.processfiles.batchapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wordpress.sicredi.processfiles.batchapi.model.AccountData;
import com.wordpress.sicredi.processfiles.batchapi.service.AccountDataService;

@RestController
@RequestMapping("/api")
public class AccountDataController {

    @Autowired
    private AccountDataService accountDataService;

    @GetMapping("/home")
    public String message() {
        return "<h2>Bem vinda a API BATCH</h2>";
    }

    @GetMapping("/batch")
    public String execute() {
        return accountDataService.batchExecute().toString();
    }

    @GetMapping("/accountdata")
    public List<AccountData> all() {
        return accountDataService.findAll();
    }
}
