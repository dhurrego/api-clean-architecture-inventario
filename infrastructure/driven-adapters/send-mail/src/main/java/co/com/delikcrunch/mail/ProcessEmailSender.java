package co.com.delikcrunch.mail;

import co.com.delikcrunch.model.common.exception.BusinessException;
import co.com.delikcrunch.model.notification.EmailNotification;
import co.com.delikcrunch.model.notification.gateways.ProcessEmail;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class ProcessEmailSender implements ProcessEmail {

	@Value("${app.mail.remitente}")
	private String remitente;

	@Value("${app.mail.destinatario-principal}")
	private String destinatario;

	private final SendGrid sendGrid;

	private final SpringTemplateEngine templateEngine;

	public ProcessEmailSender(SendGrid sendGrid, SpringTemplateEngine templateEngine) {
		this.sendGrid = sendGrid;
		this.templateEngine = templateEngine;
	}

	public void enviarMail(EmailNotification emailNotification) {
		Email from = new Email(remitente);
		Email to = new Email(destinatario);

		Context context = new Context();
		Map<String, Object> contentMap = new HashMap<>();
		contentMap.put("fechaActual", LocalDate.now());
		contentMap.put("contenido", emailNotification.getContenido());
		contentMap.put("asunto", emailNotification.getAsunto());
		context.setVariables(contentMap);

		String html = templateEngine.process("email/email-template", context);

		Content content = new Content("text/html", html);

		Mail mail = new Mail(from, emailNotification.getAsunto(), to, content);
		Request request = new Request();
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			Response response = sendGrid.api(request);

			if(response.getStatusCode() != HttpStatus.SC_ACCEPTED) {
				throw new BusinessException("Error enviando correo electronico a ".concat(destinatario), response.getStatusCode());
			}

			log.info("Correo enviado a {} con asunto {} -> Codigo estado: {}", destinatario, emailNotification.getAsunto(), response.getStatusCode());
		} catch (IOException e) {
			throw new BusinessException("Error enviando correo electronico: ".concat(e.getMessage()), 500);
		}
	}
}