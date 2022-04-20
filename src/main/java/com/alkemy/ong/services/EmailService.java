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
import com.sendgrid.helpers.mail.objects.Personalization;

import java.io.IOException;

@Service
@PropertySource("classpath:sendgrid.properties")
public class EmailService {

    @Value("${sendgrid.from.name}")
    private String fromEmail;

    @Value("${app.sendgrid.key}")
    private String apiKey;

    @Value("${app.sendgrid.templateId}")
    private String templateId;

    public Response sendEmail(String email) throws IOException {

        Email from = new Email(fromEmail);
        String subject = "Envío de mail éxitoso";
        Email to = new Email(email);

        Content content = new Content(
                "text/plain",
                "Registro exitoso!");

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

    public Response sendWelcomeEmail(String email) throws IOException {

        Email from = new Email(fromEmail);
        String subject = "Welcome to Somos Mas";

        Email to = new Email(email);
        Mail mail = new Mail();
        mail.setFrom(from);

        Personalization personalization = new Personalization();
        personalization.addTo(to);

        mail.addPersonalization(personalization);
        mail.setTemplateId(templateId);

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
