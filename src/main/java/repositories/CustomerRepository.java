package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	//Los clientes que se han apuntado a mas actividades
	@Query("select c from Customer c where c.activities.size = (select max(d.activities.size) from Customer d)")
	List<Customer> customersWithMoreActivities();

	//El mínimo, máximo, media y desviación estándar del número de gimnasios por cliente
	@Query("select min(c.gyms.size), max(c.gyms.size), avg(c.gyms.size) from Customer c")
	Object[] minMaxAvgDesviationGymsByCustomers();

	//La media y desviación estándar del número de notas escritas por cliente
	@Query("select avg(c.annotationWriter.size) from Customer c")
	Object[] avgDesviationNotesWrittersByCustomers();

	//La media y desviación estándar del número de notas asociadas a clientes
	@Query("select avg(c.annotationStore.size) from Customer c")
	Object[] avgDesviationNotesStoreByCustomers();
	
	//La media y desviación estándar del número de estrellas por cliente
	@Query("select avg(s.rate) from Customer c join c.annotationStore s")
	Object[] avgDesviationStarsByCustomers();
	
	//La media de estrellas por cliente, agrupadas por país
	@Query("select avg(s.rate) from Customer c join c.annotationStore s group by c.country")
	Double avgStarsCountryByCustomers();

	//La media de estrellas por cliente, agrupadas por ciudad
	@Query("select avg(s.rate) from Customer c join c.annotationStore s group by c.city")
	Double avgStarsCityByCustomers();
}
