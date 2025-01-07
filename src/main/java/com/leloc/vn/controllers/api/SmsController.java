package com.leloc.vn.controllers.api;

import com.leloc.vn.services.SmsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SmsController {

    private final SmsService smsService;

    public SmsController(SmsService smsService) {
        this.smsService = smsService;
    }

    @GetMapping("/get-sms-content")
    public SmsResponse getSmsContent(@RequestParam String token) {
        return smsService.getContent(token);
    }
}
