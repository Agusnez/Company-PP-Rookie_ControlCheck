
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Curriculum extends DomainEntity {

	private String							ticker;
	private Boolean							noCopy;
	private PersonalData					personalData;
	private Collection<PositionData>		positionDatas;
	private Collection<EducationData>		educationDatas;
	private Collection<MiscellaneousData>	miscellaneousDatas;
	private Rookie							rookie;


	@NotBlank
	@SafeHtml
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	@NotNull
	public Boolean getNoCopy() {
		return this.noCopy;
	}

	public void setNoCopy(final Boolean noCopy) {
		this.noCopy = noCopy;
	}

	@Valid
	@OneToOne(optional = false, cascade = CascadeType.ALL)
	public PersonalData getPersonalData() {
		return this.personalData;
	}

	public void setPersonalData(final PersonalData personalData) {
		this.personalData = personalData;
	}

	@Valid
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<PositionData> getPositionDatas() {
		return this.positionDatas;
	}

	public void setPositionDatas(final Collection<PositionData> positionDatas) {
		this.positionDatas = positionDatas;
	}

	public void addPositionData(final PositionData positionData) {
		this.positionDatas.add(positionData);
	}

	public void removePositionData(final PositionData positionData) {
		this.positionDatas.remove(positionData);
	}

	@Valid
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<EducationData> getEducationDatas() {
		return this.educationDatas;
	}

	public void setEducationDatas(final Collection<EducationData> educationDatas) {
		this.educationDatas = educationDatas;
	}

	public void addEducationData(final EducationData educationData) {
		this.educationDatas.add(educationData);
	}

	public void removeEducationData(final EducationData educationData) {
		this.educationDatas.remove(educationData);
	}

	@Valid
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<MiscellaneousData> getMiscellaneousDatas() {
		return this.miscellaneousDatas;
	}

	public void setMiscellaneousDatas(final Collection<MiscellaneousData> miscellaneousDatas) {
		this.miscellaneousDatas = miscellaneousDatas;
	}

	public void addMiscellaneousData(final MiscellaneousData miscellaneousData) {
		this.miscellaneousDatas.add(miscellaneousData);
	}

	public void removeMiscellaneousData(final MiscellaneousData miscellaneousData) {
		this.miscellaneousDatas.remove(miscellaneousData);
	}

	@Valid
	@ManyToOne(optional = false)
	public Rookie getRookie() {
		return this.rookie;
	}

	public void setRookie(final Rookie rookie) {
		this.rookie = rookie;
	}

}
