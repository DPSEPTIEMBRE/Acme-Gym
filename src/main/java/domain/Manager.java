
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Manager extends Actor {

	//Atributtes
	
	private List<Gym> gyms;


	//Getters

	@NotNull
	@OneToMany
	public List<Gym> getGyms() {
		return gyms;
	}

	//Setters
	public void setGyms(List<Gym> gyms) {
		this.gyms = gyms;
	}

}
