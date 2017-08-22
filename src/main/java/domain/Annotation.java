
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Annotation extends DomainEntity {

	//Atributtes
	
	private String		momentWritten;
	private String		text;
	private Integer		rate;
	private Actor		actorWrites;
	private Actor		actorStores;
	private Activity	activity;
	private Gym			gym;


	//Getters

	@NotBlank
	@Pattern(regexp="^([1-9]|([012][0-9])|(3[01]))-([0]{0,1}[1-9]|1[012])-\\d\\d\\d\\d [012]{0,1}[0-9]:[0-6][0-9]$")
	public String getMomentWritten() {
		return momentWritten;
	}

	@NotBlank
	public String getText() {
		return text;
	}

	@NotNull
	@Range(min = 0, max = 3)
	public Integer getRate() {
		return rate;
	}

	@NotNull
	@ManyToOne(optional = false)
	public Actor getActorWrites() {
		return actorWrites;
	}

	
	@ManyToOne(optional = true)
	public Actor getActorStores() {
		return actorStores;
	}

	
	@ManyToOne(optional = true)
	public Activity getActivity() {
		return activity;
	}

	
	@ManyToOne(optional = true)
	public Gym getGym() {
		return gym;
	}

	//Setters

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public void setMomentWritten(String momentWritten) {
		this.momentWritten = momentWritten;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}

	public void setActorWrites(Actor actorWrites) {
		this.actorWrites = actorWrites;
	}

	public void setActorStores(Actor actorStores) {
		this.actorStores = actorStores;
	}
	public void setGym(Gym gym) {
		this.gym = gym;
	}

}
