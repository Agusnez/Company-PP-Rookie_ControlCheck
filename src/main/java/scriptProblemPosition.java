public class scriptProblemPosition {

	/**
	 * @param args
	 */

	//3 problem, 2 true añadidos a position, 1 con false para editar y 1 a true para añadir
	public static String changeString1(final int x, final int y) {
		final String text = "<bean id=\"problem" + x + "\" class=\"domain.Problem\"> \n  " + "<property name=\"title\" value=\"Problem ya añadido a position\" /> \n" + "<property name=\"statement\" value=\"statement\" /> \n"
			+ "<property name=\"hint\" value=\"hint\" /> \n" + "<property name=\"attachments\" > \n" + "<list> \n" + "<value>http://www.attachment.com</value> \n" + "</list> \n" + "</property> \n" + "<property name=\"finalMode\" value=\"true\" /> \n"
			+ "<property name=\"company\" ref=\"company" + x + "\" /> \n" + "<property name=\"positions\" > \n" + "<list> \n" + "<ref bean=\"position" + x + "\"/> \n" + "</list> \n" + "</property> \n" + "</bean>" + "\n";
		return text;
	}

	public static String changeString2(final int x, final int y) {
		final String text = "<bean id=\"problem" + y + "\" class=\"domain.Problem\"> \n  " + "<property name=\"title\" value=\"Problem ya añadido a position\" /> \n" + "<property name=\"statement\" value=\"statement\" /> \n"
			+ "<property name=\"hint\" value=\"hint\" /> \n" + "<property name=\"attachments\" > \n" + "<list> \n" + "<value>http://www.attachment.com</value> \n" + "</list> \n" + "</property> \n" + "<property name=\"finalMode\" value=\"true\" /> \n"
			+ "<property name=\"company\" ref=\"company" + x + "\" /> \n" + "<property name=\"positions\" > \n" + "<list> \n" + "<ref bean=\"position" + x + "\"/> \n" + "</list> \n" + "</property> \n" + "</bean>" + "\n";
		return text;
	}

	public static String changeString3(final int x, final int y) {
		final String text = "<bean id=\"problem" + y + "\" class=\"domain.Problem\"> \n  " + "<property name=\"title\" value=\"Problem final Mode a true no añadido a nada\" /> \n" + "<property name=\"statement\" value=\"statement\" /> \n"
			+ "<property name=\"hint\" value=\"hint\" /> \n" + "<property name=\"attachments\" > \n" + "<list> \n" + "<value>http://www.attachment.com</value> \n" + "</list> \n" + "</property> \n" + "<property name=\"finalMode\" value=\"true\" /> \n"
			+ "<property name=\"company\" ref=\"company" + x + "\" /> \n" + "<property name=\"positions\" > \n" + "<list> \n" + "</list> \n" + "</property> \n" + "</bean>" + "\n";
		return text;
	}

	public static String changeString4(final int x, final int y) {
		final String text = "<bean id=\"problem" + y + "\" class=\"domain.Problem\"> \n  " + "<property name=\"title\" value=\"Problem final mode false\" /> \n" + "<property name=\"statement\" value=\"statement\" /> \n"
			+ "<property name=\"hint\" value=\"hint\" /> \n" + "<property name=\"attachments\" > \n" + "<list> \n" + "<value>http://www.attachment.com</value> \n" + "</list> \n" + "</property> \n" + "<property name=\"finalMode\" value=\"false\" /> \n"
			+ "<property name=\"company\" ref=\"company" + x + "\" /> \n" + "<property name=\"positions\" > \n" + "<list> \n" + "</list> \n" + "</property> \n" + "</bean>" + "\n";
		return text;
	}

	public static String changeString5(final int x, final int y) {
		final String text = "<bean id=\"position" + x + "\" class=\"domain.Position\"> \n  " + "<property name=\"ticker\" value=\"ticker\" /> \n" + "<property name=\"title\" value=\"title\" /> \n"
			+ "<property name=\"description\" value=\"description\" /> \n" + "<property name=\"deadline\" value=\"2019/12/12\" /> \n" + "<property name=\"profile\" value=\"profile\" /> \n" + "<property name=\"skills\" value=\"skills\" /> \n"
			+ "<property name=\"technologies\" value=\"technologies\" /> \n" + "<property name=\"offeredSalary\" value=\"500.0\" /> \n" + "<property name=\"company\" ref=\"company" + x + "\" /> \n" + "<property name=\"finalMode\" value=\"true\" /> \n"
			+ "</bean>" + "\n";
		return text;
	}

	public static String changeString6(final int x, final int y) {
		final String text = "<bean id=\"position" + y + "\" class=\"domain.Position\"> \n  " + "<property name=\"ticker\" value=\"ticker\" /> \n" + "<property name=\"title\" value=\"title\" /> \n"
			+ "<property name=\"description\" value=\"description\" /> \n" + "<property name=\"deadline\" value=\"2019/12/12\" /> \n" + "<property name=\"profile\" value=\"profile\" /> \n" + "<property name=\"skills\" value=\"skills\" /> \n"
			+ "<property name=\"technologies\" value=\"technologies\" /> \n" + "<property name=\"offeredSalary\" value=\"500.0\" /> \n" + "<property name=\"company\" ref=\"company" + x + "\" /> \n" + "<property name=\"finalMode\" value=\"false\" /> \n"
			+ "</bean>" + "\n";
		return text;
	}

	public static void main(final String[] args) {
		// TODO Auto-generated method stub

		//		<bean id="position1" class="domain.Position">
		//		<property name="ticker" value="firs-1234" />
		//		<property name="title" value="position1" />
		//		<property name="description" value="description position 1" />
		//		<property name="deadline" value="2019/08/12" />
		//		<property name="profile" value="Java developer" />
		//		<property name="skills" value="javascript,django" />
		//		<property name="technologies" value="PC" />
		//		<property name="offeredSalary" value="500.0" />
		//		<property name="finalMode" value="true" />
		//		<property name="company" ref="company1" />
		//		</bean>

		int x = 1;
		int y = 100;
		int z = 200;
		int w = 300;
		int o = 100;

		String res = "";
		while (x < 100) {

			res += scriptProblemPosition.changeString6(x, o);
			res += "\n";

			x = x + 1;
			y = y + 1;
			z = z + 1;
			w = w + 1;
			o = o + 1;

		}

		System.out.println(res);

	}
}
