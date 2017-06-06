package br.com.fiap.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "ORDER", catalog = "JPAFINAL", uniqueConstraints = {
		@UniqueConstraint(columnNames = "ORDER_ID") })
public class Order implements Serializable {

	private static final long serialVersionUID = -2721039625296921665L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORDER_ID", unique = true, nullable = false)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="REGISTRATION_DATE")
	private Calendar registrationDate;
	
	@Column(name = "USERNAME", length = 100)
	private String username;

	@Column(name = "IP_ADDRESS", length = 60)
	private String ipAddress;

	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY, mappedBy="order") 
	private Set<Itinerary> items = new LinkedHashSet<>();
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Calendar getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Calendar registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Set<Itinerary> getItems() {
		return items;
	}

	public void setItems(Set<Itinerary> items) {
		this.items = items;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		StringBuilder sb = new StringBuilder();
		sb.append("|--Order Id          : " + id + "\n");
		sb.append("|--Username          : " + username + "\n");
		sb.append("|--Ip address        : " + ipAddress + "\n");
		sb.append("|--Registration date : " + formatter.format(registrationDate.getTime()) + "\n");
		items.forEach(i -> {
			sb.append(i);
		});
		
		return sb.toString();
	}
}
