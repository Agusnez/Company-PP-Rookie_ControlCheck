
package controllers.company;

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

import services.ApplicationService;
import services.CompanyService;
import services.MessageService;
import services.PositionService;
import services.ProblemService;
import services.QuoletService;
import services.SocialProfileService;
import controllers.AbstractController;
import domain.Application;
import domain.Company;
import domain.Message;
import domain.Position;
import domain.Problem;
import domain.Quolet;
import domain.SocialProfile;

@Controller
@RequestMapping("/data/company")
public class DownloadDataCompanyController extends AbstractController {

	@Autowired
	private CompanyService			companyService;

	@Autowired
	private SocialProfileService	socialProfileService;

	@Autowired
	private MessageService			messageService;
	
	@Autowired
	private ProblemService			problemService;
	
	@Autowired
	private PositionService			positionService;
	
	
	@Autowired
	private ApplicationService		applicationService;
	
	//TODO: CC
	@Autowired
	private QuoletService			quoletService;


	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public void test(final HttpSession session, final HttpServletResponse response) throws IOException {

		final String language = LocaleContextHolder.getLocale().getLanguage();

		if (language == "en") {
			String myString = "Below these lines you can find all the data we have at Acme-Rookies:\r\n";

			final Company c = this.companyService.findByPrincipal();
			final Collection<SocialProfile> sc = this.socialProfileService.findAllByActor(c.getId());
			final Collection<Message> msgs = this.messageService.messagePerActor(c.getId());
			final Collection<Problem> problems = this.problemService.findProblemByCompanyId(c.getId());
			final Collection<Position> positions = this.positionService.findPositionsByCompanyId(c.getId());
			
			

			myString += "\r\n\r\n";

			myString += c.getName() + c.getSurnames() + " " + c.getCommercialName() + " " + " " + c.getAddress() + " " + c.getEmail() + " " + c.getPhone() + " " + c.getPhoto() + " \r\n";
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
					myString += "Sender: " + msg.getSender().getName() + " " + msg.getSender().getSurnames() + " Recipient: All" + " Moment: " + msg.getMoment() + " Subject: "
							+ msg.getSubject() + " Body: " + msg.getBody() + " Tags: " + msg.getTags();
				}
			myString += "\r\n\r\n";
			myString += "Problems:\r\n";
			for (final Problem p : problems)
				myString += p.getTitle() + " Hint:" + p.getHint() + " Statement:" + p.getStatement() + " Attachments:" + p.getAttachments() + " Final mode:" + p.getFinalMode() + "\r\n";
			myString += "\r\n\r\n";
			myString += "Positions:\r\n";
			for (final Position po : positions) {
				myString += po.getTitle() + " Description:" + po.getDescription()  + " Profile:" + po.getProfile() + " Skills:" + po.getSkills() + " Technologies:" + po.getTechnologies() + " Offered salary:" + po.getOfferedSalary() + "\r\n";
				//TODO: CC
				final Collection<Application> apps = this.applicationService.findByPositionId(po.getId());
				for (final Application a: apps) {
					final Collection<Quolet> qs= this.quoletService.quoletsPerApplicationId(a.getId());
					for (final Quolet q: qs)
						myString += "> Associated quolet: " + q.getTicker() + " Publication moment:" + q.getPublicationMoment() + " Body:" + q.getBody() + " Picture: " + q.getPicture() + " Final mode: " + q.getFinalMode() + "\r\n";
					myString += "\r\n";
				}
			}
			myString += "\r\n\r\n";

			response.setContentType("text/plain");
			response.setHeader("Content-Disposition", "attachment;filename=my_data_as_company.txt");
			final ServletOutputStream out = response.getOutputStream();
			out.println(myString);
			out.flush();
			out.close();

		} else {
			String myString = "Debajo de estas lineas puedes encontrar todos los datos que tenemos de ti en Acme-Rookies:\r\n";

			final Company c = this.companyService.findByPrincipal();
			final Collection<SocialProfile> sc = this.socialProfileService.findAllByActor(c.getId());
			final Collection<Message> msgs = this.messageService.messagePerActor(c.getId());
			final Collection<Problem> problems = this.problemService.findProblemByCompanyId(c.getId());
			final Collection<Position> positions = this.positionService.findPositionsByCompanyId(c.getId());
			
			myString += "\r\n\r\n";

			myString += c.getName() + " " + c.getSurnames() + " " + c.getCommercialName() + " " + c.getAddress() + " " + c.getEmail() + " " + c.getPhone() + " " + c.getPhoto() + " \r\n";
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
			myString += "Problemas:\r\n";
			for (final Problem p : problems)
				myString += p.getTitle() + " Pista:" + p.getHint() + " Declaracion:" + p.getStatement() + " Adjuntos:" + p.getAttachments() + " Modo final:" + p.getFinalMode() + "\r\n";
			myString += "\r\n\r\n";
			myString += "Posiciones:\r\n";
			for (final Position po : positions) {
				myString += po.getTitle() + " Descripcion:" + po.getDescription()  + " Perfil:" + po.getProfile() + " Aptitudes:" + po.getSkills() + " Tecnologias:" + po.getTechnologies() + " Salario:" + po.getOfferedSalary() + "\r\n";
				//TODO: CC
				final Collection<Application> apps = this.applicationService.findByPositionId(po.getId());
				for (final Application a: apps) {
					final Collection<Quolet> qs= this.quoletService.quoletsPerApplicationId(a.getId());
					for (final Quolet q: qs)
						myString += "> Quolets asociadas: " + q.getTicker() + " Fecha de publicacion:" + q.getPublicationMoment() + " Cuerpo:" + q.getBody() + " Foto: " + q.getPicture() + " Modo final: " + q.getFinalMode() + "\r\n";
					myString += "\r\n";
				}
			}
			myString += "\r\n\r\n";

			response.setContentType("text/plain");
			response.setHeader("Content-Disposition", "attachment;filename=mis_datos_como_compania.txt");
			final ServletOutputStream out = response.getOutputStream();
			out.println(myString);
			out.flush();
			out.close();
		}
	}
}
