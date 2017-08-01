
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Gym extends DomainEntity {

	private String				logo;
	private String				name;
	private String				address;
	private String				fee;
	private List<Trainer>		trainers;
	private List<Customer>		customers;
	private List<Activity>		activities;
	private List<Annotation>	annotations;


	//Getters
	@NotNull
	@NotBlank
	public String getLogo() {
		return logo;
	}

	@NotBlank
	public String getName() {
		return name;
	}

	@NotBlank
	public String getAddress() {
		return address;
	}

	@NotNull
	public String getFee() {
		return fee;
	}

	@NotNull
	@OneToMany(mappedBy = "gym")
	public List<Trainer> getTrainers() {
		return trainers;
	}

	@OneToMany(mappedBy = "gym")
	@NotNull
	public List<Customer> getCustomers() {
		return customers;
	}

	@NotNull
	@OneToMany(mappedBy = "gym")
	public List<Activity> getActivities() {
		return activities;
	}

	@NotNull
	@OneToMany(mappedBy = "gym")
	public List<Annotation> getAnnotations() {
		return annotations;
	}

	//Setters
	public void setLogo(String logo) {
		this.logo = logo;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public void setTrainers(List<Trainer> trainers) {
		this.trainers = trainers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}

	public void setAnnotations(List<Annotation> annotations) {
		this.annotations = annotations;
	}

}
