package com.alkemy.ong.services;

import org.springframework.stereotype.Service;
import sendinblue.ApiClient;
import sendinblue.ApiException;
import sendinblue.Configuration;
import sendinblue.auth.ApiKeyAuth;
import sibApi.TransactionalEmailsApi;
import sibModel.CreateSmtpEmail;
import sibModel.SendSmtpEmail;
import sibModel.SendSmtpEmailSender;
import sibModel.SendSmtpEmailTo;

import java.io.IOException;
import java.util.List;

@Service
public class EmailService {

    private final String FROM_EMAIL = System.getenv("SENDER_EMAIL");
    private final String API_KEY = System.getenv("SENDIBLUE_API_KEY");
    private final String WELCOME_TEMPLATE_ID = "1";


    public void sendWelcomeEmail(String email) throws IOException {

        ApiClient defaultClient = Configuration.getDefaultApiClient();
        ApiKeyAuth apiKey = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
        apiKey.setApiKey(API_KEY);
        TransactionalEmailsApi api = new TransactionalEmailsApi();

        SendSmtpEmailTo to = new SendSmtpEmailTo();
        to.setEmail(email);
        List<SendSmtpEmailTo> toList = List.of(to);

        SendSmtpEmail sendSmtpEmail = new SendSmtpEmail();
        sendSmtpEmail.setTemplateId(1L);
        sendSmtpEmail.setTo(toList);

        CreateSmtpEmail response = null;
        try {
            response = api.sendTransacEmail(sendSmtpEmail);
        }catch (ApiException e){
            System.out.println(response.toString());
        }
    }

    public void sendEmailContact(String toEmail) {

        ApiClient defaultClient = Configuration.getDefaultApiClient();
        // Configure API key authorization: api-key
        ApiKeyAuth apiKey = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
        apiKey.setApiKey(API_KEY);
        TransactionalEmailsApi api = new TransactionalEmailsApi();

        SendSmtpEmailSender sender = new SendSmtpEmailSender();
        sender.setEmail(FROM_EMAIL);
        sender.setName("Somos Más ONG");

        SendSmtpEmailTo to = new SendSmtpEmailTo();
        to.setEmail(toEmail);
        List<SendSmtpEmailTo> toList = List.of(to);

        SendSmtpEmail sendSmtpEmail = new SendSmtpEmail();
        sendSmtpEmail.setSender(sender);
        sendSmtpEmail.setTo(toList);
        sendSmtpEmail.setSubject("Mensaje de contacto recibido!");
        sendSmtpEmail.setHtmlContent("<html><body><p>Recibimos correctamente tu mensaje. ¡A la brevedad nos estaremos comunicando!</p></body></html>");

        CreateSmtpEmail response = new CreateSmtpEmail();
        try {
            response = api.sendTransacEmail(sendSmtpEmail);
        } catch (ApiException e) {
            System.out.println(response.toString());
        }
    }
}
