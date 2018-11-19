package org.rssb.sms.controller;

import org.rssb.sms.model.SmsRequest;
import org.rssb.sms.service.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class SmsController {

    @Autowired
    SmsService smsService;

    Logger logger = LoggerFactory.getLogger(SmsController.class);

    @RequestMapping("/ping")
    public String alive() {
        return "Sms App is healthy !";
    }

    /**
     * This method delegates message to SmsService which in turn sends it
     * to end user
     *
     * @param smsRequest
     * @return
     */
    @RequestMapping(value = "/sms", method = RequestMethod.POST)
    public ResponseEntity<String> sendSms(@RequestBody SmsRequest smsRequest) {
        if (!smsService.validatePayload(smsRequest)) {
            System.out.println("Wrong Payload");
            throw new IllegalArgumentException();
        }

        String response = smsService.sendSms(smsRequest);

        return ResponseEntity.ok(response);


    }

}
