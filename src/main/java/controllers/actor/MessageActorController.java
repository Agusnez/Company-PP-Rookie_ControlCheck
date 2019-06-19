
package controllers.actor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ConfigurationService;
import services.MessageService;
import controllers.AbstractController;
import domain.Actor;
import domain.Message;
import forms.MessageForm;

@Controller
@RequestMapping("/message/actor")
public class MessageActorController extends AbstractController {

	@Autowired
	private MessageService			messageService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private ActorService			actorService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final Collection<Message> messages, messagesDELETE, messagesSYSTEM;

		final Actor actor = this.actorService.findByPrincipal();

		final String banner = this.configurationService.findConfiguration().getBanner();
		final Boolean rebrandingNotification = this.configurationService.findConfiguration().getRebrandingNotification();

		messages = this.messageService.AllmessagePerActor(actor.getId());
		messagesDELETE = this.messageService.AllmessageDELETEPerActor(actor.getId());
		messagesSYSTEM = this.messageService.AllmessageSYSTEM();

		messages.removeAll(messagesDELETE);
		messages.removeAll(messagesSYSTEM);
		messagesSYSTEM.removeAll(messagesDELETE);

		result = new ModelAndView("message/list");
		result.addObject("messages", messages);
		result.addObject("messagesDELETE", messagesDELETE);
		result.addObject("messagesSYSTEM", messagesSYSTEM);
		result.addObject("rebrandingNotification", rebrandingNotification);
		result.addObject("banner", banner);

		result.addObject("requestURI", "message/actor/list.do");

		return result;
	}
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int messageId) {
		ModelAndView result;
		final Message message1;
		Boolean security = false;

		final String banner = this.configurationService.findConfiguration().getBanner();

		final Boolean existMessage = this.messageService.existId(messageId);

		if (existMessage) {

			security = this.messageService.securityDisplayMessage(messageId);

			if (security) {

				message1 = this.messageService.findOne(messageId);

				result = new ModelAndView("message/display");
				result.addObject("message1", message1);
				result.addObject("banner", banner);
				result.addObject("requestURI", "message/actor/display.do");

			} else {
				result = new ModelAndView("redirect:/welcome/index.do");
				result.addObject("banner", banner);
			}

		} else {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		}

		return result;
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int actorId) {
		final ModelAndView result;
		final String banner = this.configurationService.findConfiguration().getBanner();

		final Boolean exist = this.actorService.existActor(actorId);
		if (exist) {
			final MessageForm message2 = this.messageService.create(actorId);

			result = this.createEditModelAndView(message2);
		} else {
			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute(value = "message") final MessageForm message2, final BindingResult binding) {
		ModelAndView result;
		Message message3 = null;
		try {
			Assert.isTrue(message2.getRecipientId() != 0);
			message3 = this.messageService.reconstruct(message2, binding);

			Assert.isTrue(message2.getId() == 0);

			if (binding.hasErrors())
				result = this.createEditModelAndView(message2);
			else
				try {

					Assert.isTrue(message3.getSender() == this.actorService.findByPrincipal());
					Assert.isTrue(message3.getRecipient() != this.actorService.findByPrincipal());
					Assert.isTrue(message3.getId() == 0);

					this.messageService.save(message3);
					result = new ModelAndView("redirect:/message/actor/list.do");
				} catch (final Throwable oops) {
					result = this.createEditModelAndView(message2, "message.commit.error");
				}

		} catch (final Exception e) {
			result = this.createEditModelAndView(message2, "message.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int messageId) {

		ModelAndView result;
		final Message message1;
		Boolean security = false;
		final String banner = this.configurationService.findConfiguration().getBanner();

		final Boolean existMessage = this.messageService.existId(messageId);

		if (existMessage) {

			security = this.messageService.securityMessage(messageId);

			if (security) {

				message1 = this.messageService.findOne(messageId);

				this.messageService.delete(message1);

				result = new ModelAndView("redirect:/message/actor/list.do");
				result.addObject("banner", banner);

			} else
				result = new ModelAndView("redirect:/message/actor/list.do");
		} else {

			result = new ModelAndView("misc/notExist");
			result.addObject("banner", banner);
		}

		return result;
	}

	@RequestMapping(value = "/rebrandingNotification", method = RequestMethod.GET)
	public ModelAndView rebrandingNotification() {

		ModelAndView result;

		final Boolean rebrandingNotification = this.configurationService.findConfiguration().getRebrandingNotification();

		if (!rebrandingNotification) {
			this.messageService.notificationRebranding();

			result = new ModelAndView("redirect:/message/actor/list.do");
		} else
			result = new ModelAndView("redirect:/welcome/index.do");

		return result;
	}

	protected ModelAndView createEditModelAndView(final MessageForm message2) {
		final ModelAndView result;
		result = this.createEditModelAndView(message2, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final MessageForm message2, final String errorText) {
		final ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("message/create");
		result.addObject("messageError", errorText);
		result.addObject("message", message2);
		result.addObject("banner", banner);
		result.addObject("enlace", "message/actor/edit.do");

		return result;
	}
}
