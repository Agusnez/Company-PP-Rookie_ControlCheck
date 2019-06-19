public class ScriptFinder {

	/**
	 * @param args
	 */

	public static String changeString(final int x, final int y) {
		final String text = "<bean id=\"finder" + x + "\" class=\"domain.Finder\"> \n  " + "<property name=\"keyWord\" value=\"f\" /> \n" + "<property name=\"minimumSalary\" value=\"\" /> \n" + "<property name=\"maximumSalary\" value=\"\" /> \n"
			+ "<property name=\"maximumDeadline\" value=\"\" /> \n" + "<property name=\"lastUpdate\" value=\"2019/01/01\" /> \n" + "<property name=\"positions\" > \n" + "<list> \n" + "</list> \n" + "</property> \n"
			+ "<property name=\"hacker\" ref=\"hacker" + x + "\" /> \n" + "</bean>" + "\n";
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
			res += ScriptFinder.changeString(x, y);
			x = x + 1;
			y = y + 1;
			if (y == 100)
				y = 1;

		}

		System.out.println(res);

	}
}
