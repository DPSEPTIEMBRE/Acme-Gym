package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class TabuWord extends DomainEntity {
	
	//Atributtes
	
	private String	name;

	//Getters
	
	@NotBlank
	public String getName() {
		return name;
	}

	//Setters
	
	public void setName(String name) {
		this.name = name;
	}
	
	

}
