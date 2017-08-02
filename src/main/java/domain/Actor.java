
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
public class Actor extends DomainEntity {

	//Atributtes
	
	private String				actorName;
	private String				surname;
	private String				email;
	private String				phone;
	private String				postalAddress;
	private String				city;
	private String				country;
	private UserAccount			userAccount;
	private List<Annotation>	annotationWriter;
	private List<Annotation>	annotationStore;


	//Getters

	@NotBlank
	public String getActorName() {
		return actorName;
	}

	@NotBlank
	public String getSurname() {
		return surname;
	}

	@Email
	public String getEmail() {
		return email;
	}

	@Pattern(regexp = "^\\+([1-9][0-9]{0,2}) (\\([1-9][0-9]{0,2}\\)) ([a-zA-Z0-9 -]{4,})$")
	public String getPhone() {
		return phone;
	}

	@NotNull
	public String getPostalAddress() {
		return postalAddress;
	}

	@NotNull
	@Valid
	@OneToOne(cascade = CascadeType.ALL, optional = false)
	public UserAccount getUserAccount() {
		return userAccount;
	}

	@NotNull
	public String getCity() {
		return city;
	}

	@NotNull
	public String getCountry() {
		return country;
	}

	@OneToMany(mappedBy = "actorWrites")
	@NotNull
	public List<Annotation> getAnnotationWriter() {
		return annotationWriter;
	}

	@NotNull
	@OneToMany(mappedBy = "actorStores")
	public List<Annotation> getAnnotationStore() {
		return annotationStore;
	}

	//Setters
	public void setActorName(String actorName) {
		this.actorName = actorName;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setPostalAddress(String postalAddress) {
		this.postalAddress = postalAddress;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setAnnotationWriter(List<Annotation> annotationWriter) {
		this.annotationWriter = annotationWriter;
	}

	public void setAnnotationStore(List<Annotation> annotationStore) {
		this.annotationStore = annotationStore;
	}

}
