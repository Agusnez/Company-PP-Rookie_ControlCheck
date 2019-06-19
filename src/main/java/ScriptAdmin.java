import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

public class ScriptAdmin {

	/**
	 * @param args
	 */

	public static String changeString1(final int x, final int y) {
		String pass = "admin" + x;

		final Md5PasswordEncoder code = new Md5PasswordEncoder();

		pass = code.encodePassword(pass, null);

		final String text = "<bean id=\"userAccountAdmin" + x + "\" class=\"security.UserAccount\"> \n  " + "<property name=\"username\" value=\"admin" + x + "\" /> \n" + "<property name=\"password\" value=\"" + pass + "\" /> \n"
			+ "<property name=\"authorities\"> \n" + "<list> \n" + "<bean class=\"security.Authority\"> \n" + "<property name=\"authority\" value=\"ADMIN\" /> \n" + "</bean> \n" + "</list> \n" + "</property> \n"
			+ "<property name=\"isNotBanned\" value=\"true\" /> \n" + "</bean>" + "\n";
		return text;
	}
	public static String changeString2(final int x, final int y) {

		final String text = "<bean id=\"creditCardAdmin" + x + "\" class=\"domain.CreditCard\"> \n" + "<property name=\"holderName\" value=\"admin" + x + "\" /> \n" + "<property name=\"make\" value=\"Mastercard\" /> \n"
			+ "<property name=\"number\" value=\"5571841748494767\" /> \n" + "<property name=\"expMonth\" value=\"05\" /> \n" + "<property name=\"expYear\" value=\"2028\" /> \n" + "<property name=\"cvv\" value=\"123\" /> \n" + "</bean>" + "\n";

		return text;
	}

	public static String changeString3(final int x, final int y) {
		final String text = "<bean id=\"administrator" + x + "\" class=\"domain.Administrator\"> \n" + "<property name=\"name\" value=\"administrator" + x + "\" /> \n" + "<property name=\"surnames\" value=\"surname\" /> \n"
			+ "<property name=\"vat\" value=\"" + y + "\" /> \n" + "<property name=\"email\" value=\"indentifier@domain.com\" /> \n" + "<property name=\"photo\" value=\"http://www.photo.com\" /> \n" + "<property name=\"address\" value=\"address\" /> \n"
			+ "<property name=\"userAccount\" ref=\"userAccountAdmin" + x + "\" /> \n" + "<property name=\"creditCard\" ref=\"creditCardAdmin" + x + "\" /> \n" + "</bean>";

		return text;
	}
	public static void main(final String[] args) {
		// TODO Auto-generated method stub

		//	<bean id="company1" class="domain.Company">
		//		<property name="name" value="Moran, Stephens and Marquez" />
		//		<property name="surnames" value="utilize integrated content" />
		//		<property name="commercialName" value="Moran, Stephens and Marquez Group" />
		//		<property name="vat" value="842866" />
		//		<property name="photo" value="https://placekitten.com/500/500" />
		//		<property name="email" value="phillipsamber@example.net" />
		//		<property name="phone" value="+34988691813" />
		//		<property name="address" value="Via de Esther Rosado" />
		//		<property name="userAccount" ref="userAccountCompany1" />
		//		<property name="creditCard" ref="creditCardCompany1" />
		//	</bean>
		int x = 1;
		int y = 100000;

		String res = "";
		while (x < 100) {
			res += ScriptAdmin.changeString1(x, y);
			res += "\n";
			res += ScriptAdmin.changeString2(x, y);
			res += "\n";
			res += ScriptAdmin.changeString3(x, y);
			res += "\n\n";
			x = x + 1;
			y = y + 1;

		}

		System.out.println(res);

		//		try {
		//			final PrintWriter out = new PrintWriter("messages.txt");
		//			out.print(text);
		//		} catch (final FileNotFoundException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}

	}
}
