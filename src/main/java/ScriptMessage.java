public class ScriptMessage {

	/**
	 * @param args
	 */

	public static String changeString(final int x, final int y) {
		final String text = "<bean id=\"message" + x + "\" class=\"domain.Message\"> \n  " + "<property name=\"moment\" value=\"2019/01/01\" /> \n" + "<property name=\"subject\" value=\"hola\" /> \n" + "<property name=\"body\" value=\"hola\" /> \n"
			+ "<property name=\"tags\" value=\"hola\" /> \n" + "<property name=\"sender\" ref=\"company" + x + "\" /> \n" + "<property name=\"recipient\" ref=\"company" + y + "\" /> \n" + "<property name=\"spam\" value=\"false\" /> \n" + "</bean>" + "\n";
		return text;
	}

	public static void main(final String[] args) {
		// TODO Auto-generated method stub

		//		<bean id="message1" class="domain.Message">
		//		<property name="moment" value="2017/09/13 13:53" />
		//		<property name="subject" value="Información sobre un hermandad" />
		//		<property name="body" value="Hola buenas, saben si hoy habrá pasamano" />
		//		<property name="tags" value="tag1" />
		//		<property name="sender" ref="member1" />
		//		<property name="recipient" ref="brotherhood1" />
		//		</bean>

		int x = 1;
		int y = 2;

		String res = "";
		while (x < 100) {
			res += ScriptMessage.changeString(x, y);
			x = x + 1;
			y = x + 1;
			if (y == 100)
				y = 1;

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
