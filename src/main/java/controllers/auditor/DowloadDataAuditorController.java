
package controllers.auditor;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import services.AuditService;
import services.AuditorService;
import services.MessageService;
import services.SocialProfileService;
import controllers.AbstractController;
import domain.Audit;
import domain.Auditor;
import domain.Message;
import domain.SocialProfile;

@Controller
@RequestMapping("/data/auditor")
public class DowloadDataAuditorController extends AbstractController {

	@Autowired
	private AuditorService			auditorService;

	@Autowired
	private SocialProfileService	socialProfileService;

	@Autowired
	private MessageService			messageService;
	
	@Autowired
	private AuditService			auditService;


	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public void test(final HttpSession session, final HttpServletResponse response) throws IOException {

		final String language = LocaleContextHolder.getLocale().getLanguage();

		if (language == "en") {
			String myString = "Below these lines you can find all the data we have at Acme-Rookies:\r\n";

			final Auditor a = this.auditorService.findByPrincipal();
			final Collection<SocialProfile> sc = this.socialProfileService.findAllByActor(a.getId());
			final Collection<Message> msgs = this.messageService.messagePerActor(a.getId());
			final Collection<Audit> audits = this.auditService.findAuditsByAuditorId(a.getId());
			
			myString += "\r\n\r\n";

			myString += a.getName() + a.getSurnames() + " " + " " + a.getAddress() + " " + a.getEmail() + " " + a.getPhone() + " " + a.getPhoto() + " \r\n";
			myString += "\r\n\r\n";
			myString += "Social Profiles:\r\n";
			for (final SocialProfile s : sc)
				myString += s.getNick() + " " + s.getLink() + " " + s.getSocialName() + "\r\n";
			myString += "\r\n\r\n";
			myString += "Messages:\r\n\r\n";
			for (final Message msg : msgs)
				if(msg.getRecipient() != null) {
					myString += "Sender: " + msg.getSender().getName() + " " + msg.getSender().getSurnames() + " Recipient: " + msg.getRecipient().getName() + " " + msg.getRecipient().getSurnames() + " Moment: " + msg.getMoment() + " Subject: "
							+ msg.getSubject() + " Body: " + msg.getBody() + " Tags: " + msg.getTags();
				} else {
					myString += "Sender: " + msg.getSender().getName() + " " + msg.getSender().getSurnames() + " Recipient: All Moment: " + msg.getMoment() + " Subject: "
							+ msg.getSubject() + " Body: " + msg.getBody() + " Tags: " + msg.getTags();
				}
			myString += "\r\n\r\n";
			myString += "Audits:\r\n";
			for (final Audit audit : audits)
				myString += audit.getText() + " Score:" + audit.getScore() + " Moment:" + audit.getMoment() + "\r\n";

			response.setContentType("text/plain");
			response.setHeader("Content-Disposition", "attachment;filename=my_data_as_auditor.txt");
			final ServletOutputStream out = response.getOutputStream();
			out.println(myString);
			out.flush();
			out.close();

		} else {
			String myString = "Debajo de estas lineas puedes encontrar todos los datos que tenemos de ti en Acme-Rookies:\r\n";

			final Auditor a = this.auditorService.findByPrincipal();
			final Collection<SocialProfile> sc = this.socialProfileService.findAllByActor(a.getId());
			final Collection<Message> msgs = this.messageService.messagePerActor(a.getId());
			final Collection<Audit> audits = this.auditService.findAuditsByAuditorId(a.getId());

			
			myString += "\r\n\r\n";

			myString += a.getName() + " " + a.getSurnames() + " " + a.getAddress() + " " + a.getEmail() + " " + a.getPhone() + " " + a.getPhoto() + " \r\n";
			myString += "\r\n\r\n";
			myString += "Perfiles Sociales:\r\n";
			for (final SocialProfile s : sc)
				myString += s.getNick() + " " + s.getLink() + " " + s.getSocialName() + "\r\n";
			myString += "\r\n\r\n";
			myString += "Mensajes:\r\n\r\n";
			for (final Message msg : msgs)
				if(msg.getRecipient() != null) {
					myString += "Emisor: " + msg.getSender().getName() + " " + msg.getSender().getSurnames() + " Destino: " + msg.getRecipient().getName() + " " + msg.getRecipient().getSurnames() + " Fecha: " + msg.getMoment() + " Asunto: "
							+ msg.getSubject() + " Cuerpo: " + msg.getBody() + " Etiquetas: " + msg.getTags();
				} else {
					myString += "Emisor: " + msg.getSender().getName() + " " + msg.getSender().getSurnames() + " Destino: Todos Fecha: " + msg.getMoment() + " Asunto: "
							+ msg.getSubject() + " Cuerpo: " + msg.getBody() + " Etiquetas: " + msg.getTags();
				}
			myString += "\r\n\r\n";
			myString += "Auditorias:\r\n";
			for (final Audit audit : audits)
				myString += audit.getText() + " Puntuacion:" + audit.getScore() + " Momento:" + audit.getMoment() + "\r\n";

			response.setContentType("text/plain");
			response.setHeader("Content-Disposition", "attachment;filename=mis_datos_como_auditor.txt");
			final ServletOutputStream out = response.getOutputStream();
			out.println(myString);
			out.flush();
			out.close();
		}
	}

}
