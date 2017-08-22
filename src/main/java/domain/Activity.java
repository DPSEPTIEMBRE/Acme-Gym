
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Activity extends DomainEntity {

	//Atributtes
	
	private String				title;
	private List<String>		pictures;
	private String				description;
	private Integer				dayWeek;
	private String				startTime;
	private String				endTime;
	private Integer				numSeats;
	private List<Customer>		customers;
	private List<Annotation>	annotations;
	private Gym					gym;
	private List<Trainer>		trainers;
	private Boolean				isCancelled;
	


	//Getters
	
	@NotNull
	public Boolean getIsCancelled() {
		return isCancelled;
	}

	@NotBlank
	public String getTitle() {
		return title;
	}

	@NotEmpty
	@ElementCollection(targetClass = String.class)
	public List<String> getPictures() {
		return pictures;
	}

	@NotBlank
	public String getDescription() {
		return description;
	}

	@Range(min = 1, max = 7)
	public Integer getDayWeek() {
		return dayWeek;
	}

	@NotBlank
	@Pattern(regexp="^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$")
	public String getStartTime() {
		return startTime;
	}

	@NotBlank
	@Pattern(regexp="^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$")
	public String getEndTime() {
		return endTime;
	}

	@NotNull
	public Integer getNumSeats() {
		return numSeats;
	}

	@NotNull
	@ManyToMany
	public List<Customer> getCustomers() {
		return customers;
	}

	@NotNull
	@OneToMany(mappedBy = "activity")
	public List<Annotation> getAnnotations() {
		return annotations;
	}

	@NotNull
	@ManyToOne(optional = false)
	public Gym getGym() {
		return gym;
	}

	@NotNull
	@ManyToMany
	public List<Trainer> getTrainers() {
		return trainers;
	}

	//Setters
	
	public void setIsCancelled(Boolean isCancelled) {
		this.isCancelled = isCancelled;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public void setPictures(List<String> pictures) {
		this.pictures = pictures;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDayWeek(Integer dayWeek) {
		this.dayWeek = dayWeek;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public void setNumSeats(Integer numSeats) {
		this.numSeats = numSeats;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	public void setAnnotations(List<Annotation> annotations) {
		this.annotations = annotations;
	}

	public void setGym(Gym gym) {
		this.gym = gym;
	}

	public void setTrainers(List<Trainer> trainers) {
		this.trainers = trainers;
	}

}
