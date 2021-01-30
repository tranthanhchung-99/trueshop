package com.linkin.service;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

public interface EmailService {
	void sendSimpleMessage(String to, String subject, String text);

	void sendTemplateMessage(String to, String subject, Map<String, Object> model, String templateName);

}

@Service
class EmailServiceImpl implements EmailService {
	private static Logger logger = LoggerFactory.getLogger(EmailService.class);

	@Autowired
	public JavaMailSender emailSender;//tiem bean

	@Autowired
	private SpringTemplateEngine templateEngine;

	@Override
	@Async
	public void sendSimpleMessage(String to, String subject, String text) {
		try {
			MimeMessage message = emailSender.createMimeMessage();//tao mail
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());

			helper.setFrom("shoptthpoly@gmail.com");//thong tin nguoi gui
			helper.setText(text, true);//noi dung mail
			helper.setTo(to);//email nguoi nhan
			helper.setSubject(subject);//tieu de email

			emailSender.send(message);//gui mail
			System.out.println("đã gửi mail");
		} catch (Exception ex) {
			logger.error("Email sending ex: " + ex);
		}
	}

	@Override
	@Async
	public void sendTemplateMessage(String to, String subject, Map<String, Object> model, String templateName) {
		try {
			Context context = new Context();
			context.setVariables(model);
			String html = templateEngine.process("email-template/" + templateName, context);

			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());
			helper.setFrom("shoptthpoly@gmail.com");
			helper.setText(html, true);
			helper.setTo(to);
			helper.setSubject(subject);

			emailSender.send(message);
		} catch (Exception ex) {
			logger.error("Email sending ex: " + ex);
		}
	}
}