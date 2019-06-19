import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ScriptApplication {

	/**
	 * @param args
	 */

	public static String changeString1(final int x, final int y) {
		final String text = "<bean id=\"application" + x + "\" class=\"domain.Application\"> \n  " + "<property name=\"moment\" value=\"2019/01/01\" /> \n" + "<property name=\"status\" value=\"SUBMITTED\" /> \n"
			+ "<property name=\"answer\" value=\"answer\" /> \n" + "<property name=\"submitMoment\" value=\"2019/01/02\" /> \n" + "<property name=\"position\" ref=\"position" + x + "\" /> \n" + "<property name=\"curriculum\" ref=\"curriculum" + y
			+ "\" /> \n" + "<property name=\"hacker\" ref=\"hacker" + x + "\" /> \n" + "</bean>" + "\n";
		return text;
	}

	public static String changeString2(final int x, final int y) {
		final String text = "<bean id=\"personalData" + y + "\" class=\"domain.PersonalData\"> \n  " + "<property name=\"fullName\" value=\"fullName\" /> \n" + "<property name=\"statement\" value=\"statement\" /> \n"
			+ "<property name=\"phone\" value=\"+34 3333\" /> \n" + "<property name=\"linkGitHubProfile\" value=\"http://www.github.com\" /> \n" + "<property name=\"linkLinkedInProfile\" value=\"http://www.linkedin.com/" + x + "\" /> \n" + "</bean>"
			+ "\n";
		return text;
	}

	public static String generateTicker() {

		final Date moment = new Date(System.currentTimeMillis() - 1000);

		final DateFormat dateFormat = new SimpleDateFormat("yyMMdd");
		final String dateString = dateFormat.format(moment);

		final String alphaNumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		final StringBuilder salt = new StringBuilder();
		final Random rnd = new Random();
		while (salt.length() < 6) { // length of the random string.
			final int index = (int) (rnd.nextFloat() * alphaNumeric.length());
			salt.append(alphaNumeric.charAt(index));
		}
		final String randomAlphaNumeric = salt.toString();

		final String ticker = dateString + "-" + randomAlphaNumeric;

		return ticker;

	}

	public static String changeString3(final int x, final int y) {
		final String text = "<bean id=\"curriculum" + y + "\" class=\"domain.Curriculum\"> \n  " + "<property name=\"ticker\" value=\"" + ScriptApplication.generateTicker() + "\" /> \n" + "<property name=\"noCopy\" value=\"false\" /> \n"
			+ "<property name=\"personalData\" ref=\"personalData" + y + "\" /> \n" + "<property name=\"hacker\" ref=\"hacker" + x + "\" /> \n" + "</bean>" + "\n";
		return text;
	}

	public static void main(final String[] args) {
		// TODO Auto-generated method stub

		//		<bean id="curriculum7" class="domain.Curriculum">
		//		<property name="ticker" value="170319-461DFG" />
		//		<property name="noCopy" value="true" />
		//		<property name="personalData" ref="personalData7" />
		//		<property name="hacker" ref="hacker7" />
		//		</bean>

		int x = 1;
		int y = 100;

		String res = "";
		while (x < 100) {
			res += ScriptApplication.changeString1(x, y);
			res += "\n";
			res += ScriptApplication.changeString2(x, y);
			res += "\n";
			res += ScriptApplication.changeString3(x, y);
			res += "\n";
			x = x + 1;
			y = y + 1;

		}

		System.out.println(res);

	}
}
