
package forms;

public class ApplicationForm {

	private String	answer;

	private int		position;
	private int		curriculum;
	private int		id;
	private int		version;
	private int		problem;


	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(final String answer) {
		this.answer = answer;
	}

	public int getPosition() {
		return this.position;
	}

	public void setPosition(final int position) {
		this.position = position;
	}

	public int getCurriculum() {
		return this.curriculum;
	}

	public void setCurriculum(final int curriculum) {
		this.curriculum = curriculum;
	}

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(final int version) {
		this.version = version;
	}

	public int getProblem() {
		return this.problem;
	}

	public void setProblem(final int problem) {
		this.problem = problem;
	}

}
