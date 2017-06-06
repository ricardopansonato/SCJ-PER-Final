package br.com.fiap.program;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;

import br.com.fiap.dao.GenericDao;
import br.com.fiap.dao.impl.ItineraryDaoJpaImpl;
import br.com.fiap.dao.impl.OrderDaoJpaImpl;
import br.com.fiap.dao.impl.ServiceDaoJpaImpl;
import br.com.fiap.model.Itinerary;
import br.com.fiap.model.Order;
import br.com.fiap.model.Service;
import br.com.fiap.model.ServiceType;

public class Main {
	
	public static void main(String[] args) throws ParseException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("system");
		creatingOrderItineraryAndService(emf);
		creatingItineraryAndService(emf);
		creatingService(emf);
		System.exit(0);
	}
	
	private static void creatingService(EntityManagerFactory emf) throws ParseException {
		EntityManager em = emf.createEntityManager();
		GenericDao<Service, Long> serviceDao = new ServiceDaoJpaImpl(em);
		
		Service service = new Service();
		service.setDescription(JOptionPane.showInputDialog("Descrição do serviço: "));
		service.setService(ServiceType.valueOf(JOptionPane.showInputDialog("Tipo do serviço(TRANSFER/MEAL/TICKET): ").toUpperCase()));
		service.setPrice(Double.parseDouble(JOptionPane.showInputDialog("Preço do serviço: ")));
		
		serviceDao.create(service);
		em = emf.createEntityManager();
		serviceDao = new ServiceDaoJpaImpl(em);
		Service findService = serviceDao.read(service.getId());
		
		System.out.println(findService);
		findService.setDescription(JOptionPane.showInputDialog("Atualizando descrição: "));
		serviceDao.update(findService);
		findService = serviceDao.read(findService.getId());
		System.out.println(findService);
		
		if(confirm()) {
			serviceDao.delete(findService);
			findService = serviceDao.read(findService.getId());
			System.out.println(findService == null ? "Service has been removed" : "Service has not been removed");
		}
		em.close();
	}
	
	private static void creatingItineraryAndService(EntityManagerFactory emf) throws ParseException {
		EntityManager em = emf.createEntityManager();
		GenericDao<Itinerary, Long> itineraryDao = new ItineraryDaoJpaImpl(em);
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		Itinerary itinerary = new Itinerary();
		itinerary.setStartDate(Calendar.getInstance());
		itinerary.setDestination(JOptionPane.showInputDialog("Destino da viagem: "));
		Calendar.getInstance().setTime(dateFormat.parse(JOptionPane.showInputDialog("Início da viagem(DD/MM/AAAA): ")));
		
		Long serviceQuantity = Long.parseLong(JOptionPane.showInputDialog("Quantidade de serviços contratados: "));
		
		Set<Service> services = new LinkedHashSet<>();
		for (long servicePosition = 1; servicePosition <= serviceQuantity; servicePosition++) {
			Service service = new Service();
			service.setDescription(JOptionPane.showInputDialog("Descrição do serviço[" + servicePosition + "]: "));
			service.setService(ServiceType.valueOf(JOptionPane.showInputDialog("Tipo do serviço[" + servicePosition + "](TRANSFER/MEAL/TICKET): ").toUpperCase()));
			service.setPrice(Double.parseDouble(JOptionPane.showInputDialog("Preço do serviço[" + servicePosition + "]: ")));
			service.setItinerary(itinerary);
			services.add(service);
		}
		
		itinerary.setServices(services);
		itineraryDao.create(itinerary);
		
		em = emf.createEntityManager();
		itineraryDao = new ItineraryDaoJpaImpl(em);
		Itinerary findItinerary = itineraryDao.read(itinerary.getItineraryId());
		
		System.out.println(findItinerary);
		findItinerary.setDestination(JOptionPane.showInputDialog("Atualizando destino: "));
		itineraryDao.update(findItinerary);
		findItinerary = itineraryDao.read(itinerary.getItineraryId());
		System.out.println(findItinerary);
		
		if(confirm()) {
			itineraryDao.delete(findItinerary);
			findItinerary = itineraryDao.read(findItinerary.getItineraryId());
			System.out.println(findItinerary == null ? "Itinerary has been removed" : "Itinerary has not been removed");
		}
		em.close();
	}
	
	private static void creatingOrderItineraryAndService(EntityManagerFactory emf) throws ParseException {
		EntityManager em = emf.createEntityManager();
		GenericDao<Order, Long> orderDao = new OrderDaoJpaImpl(em);
		
		Order order = new Order();
		order.setIpAddress(JOptionPane.showInputDialog("IP Address: "));
		order.setUsername(JOptionPane.showInputDialog("Usuário: "));
		order.setRegistrationDate(Calendar.getInstance());
		
		Long itinerariesQuantity = Long.parseLong(JOptionPane.showInputDialog("Quantidade de itinerários contratados: "));
		Set<Itinerary> itineraries = new LinkedHashSet<>();
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		for (long itineraryPosition = 1; itineraryPosition <= itinerariesQuantity; itineraryPosition++) {
			Itinerary itinerary = new Itinerary();
			itinerary.setStartDate(Calendar.getInstance());
			itinerary.setDestination(JOptionPane.showInputDialog("Destino da viagem[" + itineraryPosition + "]: "));
			Calendar.getInstance().setTime(dateFormat.parse(JOptionPane.showInputDialog("Início da viagem[" + itineraryPosition + "](DD/MM/AAAA): ")));
			
			Long serviceQuantity = Long.parseLong(JOptionPane.showInputDialog("Quantidade de serviços contratados[" + itineraryPosition + "]: "));
			
			Set<Service> services = new LinkedHashSet<>();
			for (long servicePosition = 1; servicePosition <= serviceQuantity; servicePosition++) {
				Service service = new Service();
				service.setDescription(JOptionPane.showInputDialog("Descrição do serviço[" + itineraryPosition + "][" + servicePosition + "]: "));
				service.setService(ServiceType.valueOf(JOptionPane.showInputDialog("Tipo do serviço[" + itineraryPosition + "][" + servicePosition + "](TRANSFER/MEAL/TICKET): ").toUpperCase()));
				service.setPrice(Double.parseDouble(JOptionPane.showInputDialog("Preço do serviço[" + itineraryPosition + "][" + servicePosition + "]: ")));
				service.setItinerary(itinerary);
				services.add(service);
			}
			
			itinerary.setServices(services);
			itinerary.setOrder(order);
			itineraries.add(itinerary);
		}
		
		order.setItems(itineraries);
		orderDao.create(order);
		em.close();
		
		em = emf.createEntityManager();
		orderDao = new OrderDaoJpaImpl(em);
		Order findOrder = orderDao.read(order.getId());
		
		System.out.println(findOrder);
		findOrder.setUsername(JOptionPane.showInputDialog("Atualizando usuário: "));
		orderDao.update(findOrder);
		findOrder = orderDao.read(order.getId());
		System.out.println(findOrder);
		
		if(confirm()) {
			orderDao.delete(findOrder);
			findOrder = orderDao.read(order.getId());
			System.out.println(findOrder == null ? "Order has been removed" : "Order has not been removed");
		}
		
		em.close();
	}
	
	private static boolean confirm() {
		Object[] options = { "Sim", "Não" };
		int input = JOptionPane.showOptionDialog(null, "Deseja remover os dados?", "Informação", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
		return input>0 ? false : true;
	}
}