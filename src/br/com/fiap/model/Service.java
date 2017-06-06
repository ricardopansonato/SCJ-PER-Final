package br.com.fiap.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "SERVICE", catalog = "JPAFINAL", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "SERVICE_ID" } ) })
public class Service implements Serializable {

	private static final long serialVersionUID = -7155644506490106554L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SERVICE_ID", unique = true, nullable = false)
	private Long id;

	@Column(name = "DESCRIPTION", length=100)
	private String description;
	
	@Column(name = "PRICE", length=100)
	private Double price;
	
	@Column(name = "SERVICE_TYPE")	
	@Enumerated(EnumType.STRING)
	private ServiceType service;
	
	@ManyToOne(fetch=FetchType.LAZY) 
	@JoinColumns({@JoinColumn(name="ITINERARY_ID",referencedColumnName="ITINERARY_ID")})
    private Itinerary itinerary;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Itinerary getItinerary() {
		return itinerary;
	}

	public void setItinerary(Itinerary itinerary) {
		this.itinerary = itinerary;
	}

	public ServiceType getService() {
		return service;
	}

	public void setService(ServiceType service) {
		this.service = service;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("      |--Service Id   : " + getId() + "\n");
		sb.append("      |--Description  : " + getDescription() + "\n");
		sb.append("      |--Price        : " + getPrice() + "\n");
		sb.append("      |--Type         : " + getService() + "\n");
		return sb.toString();
	}
}
