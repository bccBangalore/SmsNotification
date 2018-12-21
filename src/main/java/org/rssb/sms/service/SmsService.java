package org.rssb.sms.service;

import org.rssb.sms.model.SmsRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class SmsService {
    Logger logger = LoggerFactory.getLogger(SmsService.class);


    @Value("${smsServiceUrl}")
    String smsServiceUrl;
    @Value("${username}")
    String username;
    @Value("${password}")
    String password;
    @Value("${senderId}")
    String senderId;

    /**
     * This method calls Striker's API to send message
     * @param smsRequest
     * @return
     */
    public String sendSms(SmsRequest smsRequest) {

        logger.info("Enter:sendSms");
        String response;
        try {
            RestTemplate restTemplate = new RestTemplate();
            UriComponentsBuilder builder = UriComponentsBuilder
                    .fromUriString(smsServiceUrl)
                    .queryParam("username", "wadhwar")
                    .queryParam("password", password)
                    .queryParam("from", senderId)
                    .queryParam("to", smsRequest.getMobileNo())
                    .queryParam("msg", smsRequest.getMessage())
                    .queryParam("type", 1);

            logger.info("Calling Striker:"+builder.toUriString());
            response = restTemplate.getForObject(builder.toUriString(), String.class);
            logger.info("Response from Striker::"+response);
        }
        catch (Exception e)
        {
            System.out.println("Exception in sendSms");
            throw new RuntimeException();
        }
        return response;
    }

    /**
     * This method validates if mobile No & message passed in payload
     * are in the required format
     * @param smsRequest
     * @return
     */
    public boolean validatePayload(SmsRequest smsRequest)
    {
        String mobNo=smsRequest.getMobileNo();
        String message=smsRequest.getMessage();
        String regexStr = "^[0-9]{10}$";
        if(mobNo!=null && !mobNo.isEmpty() && mobNo.matches(regexStr)
                && message !=null && !message.isEmpty())
            return true;

            return false;
    }
}
