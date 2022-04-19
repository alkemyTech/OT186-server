package com.alkemy.ong.services;


import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@PropertySource("classpath:sendgrid.properties")
public class EmailService {

    @Value("${sendgrid.from.name}")
    private String fromEmail;

    @Value("${app.sendgrid.key}")
    private String apiKey;


    public Response sendEmail(String email) throws IOException {

        Email from = new Email(fromEmail);
        String subject = "Envío de mail exitoso";
        Email to = new Email(email);
        Content content = new Content("text/plain", "Registro exitoso!");
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();
        Response response = new Response();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            response = sg.api(request);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return response;
    }

}
