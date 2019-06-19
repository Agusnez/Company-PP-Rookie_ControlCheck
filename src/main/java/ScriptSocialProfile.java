public class ScriptSocialProfile {

	/**
	 * @param args
	 */

	public static String changeString(final int x, final int y) {
		final String text = "<bean id=\"socialProfile" + x + "\" class=\"domain.SocialProfile\"> \n  " + "<property name=\"nick\" value=\"nick\" /> \n" + "<property name=\"socialName\" value=\"socialName\" /> \n"
			+ "<property name=\"link\" value=\"http://www.facebook.com/link/\" /> \n" + "<property name=\"actor\" ref=\"company" + x + "\" /> \n" + "</bean>" + "\n";
		return text;
	}

	public static void main(final String[] args) {
		// TODO Auto-generated method stub

		//		<bean id="socialProfile1" class="domain.SocialProfile">
		//		<property name="nick" value="El Gran Poder" />
		//		<property name="socialName" value="Hermandad Jesus del Gran Poder" />
		//		<property name="link" value="http://www.facebook.com/link1/" />
		//		<property name="actor" ref="brotherhood1" />
		//		</bean>

		int x = 1;
		int y = 2;

		String res = "";
		while (x < 100) {
			res += ScriptSocialProfile.changeString(x, y);
			x = x + 1;
			y = x + 1;
			if (y == 100)
				y = 1;

		}

		System.out.println(res);

	}
}
