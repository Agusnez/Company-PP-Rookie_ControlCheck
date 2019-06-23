
package controllers.provider;

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

import services.ItemService;
import services.MessageService;
import services.ProviderService;
import services.SocialProfileService;
import services.SponsorshipService;
import controllers.AbstractController;
import domain.Item;
import domain.Message;
import domain.Provider;
import domain.SocialProfile;
import domain.Sponsorship;

@Controller
@RequestMapping("/data/provider")
public class DowloadDataProviderControler extends AbstractController {

	@Autowired
	private ProviderService			providerService;

	@Autowired
	private SocialProfileService	socialProfileService;

	@Autowired
	private MessageService			messageService;
	
	@Autowired
	private ItemService				itemService;
	
	@Autowired
	private SponsorshipService		sponsorshipService;


	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public void test(final HttpSession session, final HttpServletResponse response) throws IOException {

		final String language = LocaleContextHolder.getLocale().getLanguage();

		if (language == "en") {
			String myString = "Below these lines you can find all the data we have at Acme-Rookies:\r\n";

			final Provider p = this.providerService.findByPrincipal();
			final Collection<SocialProfile> sc = this.socialProfileService.findAllByActor(p.getId());
			final Collection<Message> msgs = this.messageService.messagePerActor(p.getId());
			final Collection<Sponsorship> spons = this.sponsorshipService.findAllByProviderId(p.getId());
			final Collection<Item> items = this.itemService.findItemsByProviderId(p.getId());

			myString += "\r\n\r\n";

			myString += p.getName() + p.getSurnames() + " " + p.getProviderMake() + " " + p.getAddress() + " " + p.getEmail() + " " + p.getPhone() + " " + p.getPhoto() + " \r\n";
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
					myString += "\r\n";
				} else {
					myString += "Sender: " + msg.getSender().getName() + " " + msg.getSender().getSurnames() + " Recipient: All Moment: " + msg.getMoment() + " Subject: "
							+ msg.getSubject() + " Body: " + msg.getBody() + " Tags: " + msg.getTags();
					myString += "\r\n";
				}
			myString += "\r\n\r\n";
			myString += "Sponsorships:\r\n\r\n";
			for (final Sponsorship s : spons)
					myString += "Banner: " + s.getBanner() + " Target:" + s.getTarget() + " Cost: " + s.getCost() + "\r\n";
			myString += "\r\n\r\n";
			myString += "Items:\r\n\r\n";
			for (final Item i : items)
					myString += "Name: " + i.getName() + " Pictures:" + i.getPictures() + " Description: " + i.getDescription() + "\r\n";
				

			response.setContentType("text/plain");
			response.setHeader("Content-Disposition", "attachment;filename=my_data_as_provider.txt");
			final ServletOutputStream out = response.getOutputStream();
			out.println(myString);
			out.flush();
			out.close();

		} else {
			String myString = "Debajo de estas lineas puedes encontrar todos los datos que tenemos de ti en Acme-Rookies:\r\n";

			final Provider p = this.providerService.findByPrincipal();
			final Collection<SocialProfile> sc = this.socialProfileService.findAllByActor(p.getId());
			final Collection<Message> msgs = this.messageService.messagePerActor(p.getId());
			final Collection<Sponsorship> spons = this.sponsorshipService.findAllByProviderId(p.getId());
			final Collection<Item> items = this.itemService.findItemsByProviderId(p.getId());

			myString += "\r\n\r\n";

			myString += p.getName() + " " + p.getSurnames() + " " + p.getProviderMake() + " " + p.getAddress() + " " + p.getEmail() + " " + p.getPhone() + " " + p.getPhoto() + " \r\n";
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
			
			myString += "\r\n\r\n";
			myString += "Sponsorships:\r\n\r\n";
			for (final Sponsorship s : spons)
					myString += "Banner: " + s.getBanner() + " Objetivo:" + s.getTarget()+ " Coste: " + s.getCost() + "\r\n";
			myString += "\r\n\r\n";
			myString += "Objetos:\r\n\r\n";
			for (final Item o : items)
					myString += "Name: " + o.getName() + " Description: " + o.getDescription() + "\r\n";
				

			response.setContentType("text/plain");
			response.setHeader("Content-Disposition", "attachment;filename=mis_datos_como_proveedor.txt");
			final ServletOutputStream out = response.getOutputStream();
			out.println(myString);
			out.flush();
			out.close();
		}
	}
}
