
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Customer extends Actor {

	private List<Activity>	activities;
	private Gym				gym;


	//Getters
	@NotNull
	@ManyToMany
	public List<Activity> getActivities() {
		return activities;
	}
	@NotNull
	@ManyToOne(optional = false)
	public Gym getGym() {
		return gym;
	}

	//Setters
	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}

	public void setGym(Gym gym) {
		this.gym = gym;
	}

}
