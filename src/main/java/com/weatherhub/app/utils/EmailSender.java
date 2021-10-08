package com.weatherhub.app.utils;

import com.sendgrid.Response;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.SendGrid;
import com.sendgrid.Request;
import com.sendgrid.Method;

import java.io.IOException;

public class EmailSender {
    public static void sendEmail(String email, String code) throws IOException {
        Email from = new Email("va123ma123@gmail.com");
        Email to = new Email(email); // use your own email address here

        String subject = "WeatherHub Password Reset Code";
        Content content = new Content("text/html", code);

        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
        Request request = new Request();

        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());

        Response response = sg.api(request);

        System.out.println(response.getStatusCode());
        System.out.println(response.getHeaders());
        System.out.println(response.getBody());
    }
}

