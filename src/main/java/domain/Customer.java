
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Customer extends Actor {

	//Atributtes
	
	private List<Activity>	activities;
	private List<Gym>		gyms;


	//Getters
	@NotNull
	@ManyToMany
	public List<Activity> getActivities() {
		return activities;
	}
	@NotNull
	@ManyToMany
	public List<Gym> getGyms() {
		return gyms;
	}

	//Setters
	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}

	public void setGyms(List<Gym> gyms) {
		this.gyms = gyms;
	}

}
