
package controllers.rookie;

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
import services.CurriculumService;
import services.FinderService;
import services.MessageService;
import services.RookieService;
import services.SocialProfileService;
import controllers.AbstractController;
import domain.Application;
import domain.Curriculum;
import domain.EducationData;
import domain.Finder;
import domain.Message;
import domain.MiscellaneousData;
import domain.PositionData;
import domain.Rookie;
import domain.SocialProfile;

@Controller
@RequestMapping("/data/rookie")
public class DownloadDataRookieController extends AbstractController {

	@Autowired
	private RookieService			rookieService;

	@Autowired
	private SocialProfileService	socialProfileService;
	
	@Autowired
	private MessageService			messageService;
	
	@Autowired
	private FinderService			finderService;
	
	@Autowired
	private ApplicationService		applicationService;
	
	@Autowired
	private CurriculumService		curriculumService;


	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public void test(final HttpSession session, final HttpServletResponse response) throws IOException {

		final String language = LocaleContextHolder.getLocale().getLanguage();

		if (language == "en") {
			String myString = "Below these lines you can find all the data we have at Acme-Rookie:\r\n";

			final Rookie c = this.rookieService.findByPrincipal();
			final Collection<SocialProfile> sc = this.socialProfileService.findAllByActor(c.getId());
			final Collection<Message> msgs = this.messageService.messagePerActor(c.getId());
			final Finder finder = this.finderService.findFinderByRookie(c.getId());
			final Collection<Application> apps = this.applicationService.findByRookieId(c.getId());
			final Collection<Curriculum> cvs = this.curriculumService.findAllByRookieId(c.getId());

			myString += "\r\n\r\n";

			myString += c.getName() + " " + c.getSurnames() + " " + c.getAddress() + " " + c.getEmail() + " " + c.getPhone() + " " + c.getPhoto() + " \r\n";
			myString += "\r\n\r\n";
			myString += "Social Profiles:\r\n";
			for (final SocialProfile s : sc)
				myString += s.getNick() + " " + s.getLink() + " " + s.getSocialName() + "\r\n";
			myString += "\r\n\r\n";
			myString += "Messages:\r\n\r\n";
			for (final Message msg : msgs)
				if(msg.getRecipient() != null) {
					myString += "Sender: " + msg.getSender().getName() + " " + msg.getSender().getSurnames() + " Recipient: " + msg.getRecipient().getName() + " " + msg.getRecipient().getSurnames() + " Moment: " + msg.getMoment() + " Subject: "
							+ msg.getSubject() + " Body: " + msg.getBody() + " Tags: " + msg.getTags() + "\r\n";
				} else {
					myString += "Sender: " + msg.getSender().getName() + " " + msg.getSender().getSurnames() + " Recipient: All Moment: " + msg.getMoment() + " Subject: "
							+ msg.getSubject() + " Body: " + msg.getBody() + " Tags: " + msg.getTags() + "\r\n";;
				}
			myString += "\r\n\r\n";
			myString += "Finder:\r\n";
			myString +=  "Keyword:" + finder.getKeyWord() + " Maximum deadline:" + finder.getMaximumDeadline() + " Maximum salary:" + finder.getMaximumSalary() + " Minimum salary:" + finder.getMinimumSalary() + " Last update:" + finder.getLastUpdate() +  "\r\n";
			myString += "\r\n\r\n";
			myString += "Applications:\r\n";
			for (final Application a : apps)
				myString += "Status:" + a.getStatus() + " Submition moment:" + a.getSubmitMoment().toString() + " Answer:" + a.getAnswer() + "\r\n";
			myString += "\r\n\r\n";
			myString += "Curricula:\r\n\r\n";
			for (final Curriculum cv : cvs) {
				myString += "Educational data:" + cv.getPersonalData().getFullName() + " Phone:" + cv.getPersonalData().getPhone() + " Statement:" + cv.getPersonalData().getStatement() + " Github:" + cv.getPersonalData().getLinkGitHubProfile() + " LinkedIn:" + cv.getPersonalData().getLinkLinkedInProfile() + "\r\n";
				if (!cv.getPositionDatas().isEmpty()) {
					myString += "Position datas:\r\n";
					for(final PositionData p: cv.getPositionDatas())
						myString += "Title:" + p.getTitle() + " Description:" + p.getDescription() + " Start Date:" + p.getStartDate() +  " End Date:" + p.getEndDate() +  "\r\n";
				}
				if (!cv.getEducationDatas().isEmpty()) {
					myString += "Education datas:\r\n";
					for(final EducationData edu: cv.getEducationDatas())
						myString += "Degree:" + edu.getDegree() + " Institution:" + edu.getInstitution() + " Mark:" + edu.getMark() + " Start Date:" + edu.getStartDate() +  " End Date:" + edu.getEndDate() +  "\r\n";
				}
				if (!cv.getMiscellaneousDatas().isEmpty()) {
					myString += "Miscellaneous datas:\r\n";
					for(final MiscellaneousData m: cv.getMiscellaneousDatas())
						myString += "Text:" + m.getText() + " Attachments:" + m.getAttachments() + "\r\n";
				}
					
			}

			
			
			response.setContentType("text/plain");
			response.setHeader("Content-Disposition", "attachment;filename=my_data_as_rookie.txt");
			final ServletOutputStream out = response.getOutputStream();
			out.println(myString);
			out.flush();
			out.close();

		} else {
			String myString = "Debajo de estas lineas puedes encontrar todos los datos que tenemos de ti en Acme-Rookie:\r\n";

			final Rookie c = this.rookieService.findByPrincipal();
			final Collection<SocialProfile> sc = this.socialProfileService.findAllByActor(c.getId());
			final Collection<Message> msgs = this.messageService.messagePerActor(c.getId());
			final Finder finder = this.finderService.findFinderByRookie(c.getId());
			final Collection<Application> apps = this.applicationService.findByRookieId(c.getId());
			final Collection<Curriculum> cvs = this.curriculumService.findAllByRookieId(c.getId());

			myString += "\r\n\r\n";

			myString += c.getName() + " " + c.getSurnames() + " " + c.getAddress() + " " + c.getEmail() + " " + c.getPhone() + " " + c.getPhoto() + " \r\n";
			myString += "\r\n\r\n";
			myString += "Perfiles Sociales:\r\n";
			for (final SocialProfile s : sc)
				myString += s.getNick() + " " + s.getLink() + " " + s.getSocialName() + "\r\n";
			myString += "\r\n\r\n";
			myString += "Mensajes:\r\n\r\n";
			for (final Message msg : msgs)
				if(msg.getRecipient() != null) {
					myString += "Emisor: " + msg.getSender().getName() + " " + msg.getSender().getSurnames() + " Receptor: " + msg.getRecipient().getName() + " " + msg.getRecipient().getSurnames() + " Momento: " + msg.getMoment() + " Asunto: "
							+ msg.getSubject() + " Cuerpo: " + msg.getBody() + " Tags: " + msg.getTags();
					myString += "\r\n";
				} else {
					myString += "Emisor: " + msg.getSender().getName() + " " + msg.getSender().getSurnames() + " Receptor: Todos Momento: " + msg.getMoment() + " Asunto: "
							+ msg.getSubject() + " Cuerpo: " + msg.getBody() + " Tags: " + msg.getTags();
					myString += "\r\n";
				}
			myString += "\r\n\r\n";
			myString += "Finder:\r\n";
			myString +=  "Palabra clave:" + finder.getKeyWord() + " Fecha maxima:" + finder.getMaximumDeadline() + " Salario maximo:" + finder.getMaximumSalary() + " Salario minimo:" + finder.getMinimumSalary() + " Ultima vez:" + finder.getLastUpdate() +  "\r\n";
			myString += "\r\n\r\n";
			myString += "Solicitudes:\r\n";
			for (final Application a : apps)
				myString += "Estado:" + a.getStatus() + " Fecha de submision:" + a.getSubmitMoment().toString() + " Solucion:" + a.getAnswer() + "\r\n";
			myString += "\r\n\r\n";
			myString += "Curricula:\r\n\r\n";
			for (final Curriculum cv : cvs) {
				myString += "Datos educacionales:" + cv.getPersonalData().getFullName() + " Telefono:" + cv.getPersonalData().getPhone() + " Comentario:" + cv.getPersonalData().getStatement() + " Github:" + cv.getPersonalData().getLinkGitHubProfile() + " LinkedIn:" + cv.getPersonalData().getLinkLinkedInProfile() + "\r\n";
				if (!cv.getPositionDatas().isEmpty()) {
					myString += "Posiciones:\r\n";
					for(final PositionData p: cv.getPositionDatas())
						myString += "Titulo:" + p.getTitle() + " Descripcion:" + p.getDescription() + " Fecha Inicio:" + p.getStartDate() +  " Fecha fin:" + p.getEndDate() +  "\r\n";
				}
				if (!cv.getEducationDatas().isEmpty()) {
					myString += "Educacion:\r\n";
					for(final EducationData edu: cv.getEducationDatas())
						myString += "Grado:" + edu.getDegree() + " Institucion:" + edu.getInstitution() + " Nota:" + edu.getMark() + " Fecha Inicio:" + edu.getStartDate() +  " Fecha fin:" + edu.getEndDate() +  "\r\n";
				}
				if (!cv.getMiscellaneousDatas().isEmpty()) {
					myString += "Miscelaneo:\r\n";
					for(final MiscellaneousData m: cv.getMiscellaneousDatas())
						myString += "Texto:" + m.getText() + " Adjuntos:" + m.getAttachments() + "\r\n";
				}
					
			}
			
			response.setContentType("text/plain");
			response.setHeader("Content-Disposition", "attachment;filename=mis_datos_como_rookie.txt");
			final ServletOutputStream out = response.getOutputStream();
			out.println(myString);
			out.flush();
			out.close();
		}
	}
}
