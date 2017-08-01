
package domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Activity extends DomainEntity {

	private String				title;
	private List<String>		pictures;
	private String				description;
	private Integer				dayWeek;
	private Date				startTime;
	private Date				endTime;
	private Integer				numSeats;
	private List<Customer>		customers;
	private List<Annotation>	annotations;
	private Gym					gym;
	private List<Trainer>		trainers;


	//Getters
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

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "hh:mm")
	public Date getStartTime() {
		return startTime;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "hh:mm")
	public Date getEndTime() {
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

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(Date endTime) {
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
