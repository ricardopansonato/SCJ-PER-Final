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
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "ITINERARY", catalog = "JPAFINAL", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "ORDER_ID" } ) })
public class Itinerary implements Serializable {

	private static final long serialVersionUID = 7438248986534674256L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	@Column(name="ITINERARY_ID")
    private Long itineraryId;
	
	@ManyToOne(fetch=FetchType.LAZY) 
	@JoinColumns({@JoinColumn(name="ORDER_ID",referencedColumnName="ORDER_ID")})
    private Order order;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="START_DATE")	
	private Calendar startDate;
	
	@Column(name="DESTINATION", length=100)
	private String destination;
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="itinerary") 
	private Set<Service> services = new LinkedHashSet<>();

	public Long getItineraryId() {
		return itineraryId;
	}

	public void setItineraryId(Long itineraryId) {
		this.itineraryId = itineraryId;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Set<Service> getServices() {
		return services;
	}

	public void setServices(Set<Service> services) {
		this.services = services;
	}

	public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	@Override
	public String toString() {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		StringBuilder sb = new StringBuilder();
		sb.append("   |--Itinerary Id  : " + getItineraryId() + "\n");
		sb.append("   |--Destination   : " + getDestination() + "\n");
		sb.append("   |--Start date    : " + formatter.format(getStartDate().getTime()) + "\n");
		getServices().forEach(s -> {
			sb.append(s);
		});
		
		return sb.toString();
	}
}

