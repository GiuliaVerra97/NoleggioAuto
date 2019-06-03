package it.polito.tdp.model;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

import it.polito.tdp.model.Evento.TipoEvento;

/*Il simulatore deve:
 * 1 aggiornare lo stato del mondo 
 * 2 modificare i parametri di simulazione 
 * 3 modificare delle statistiche raccolte(simulazioni-output)
 * 
 * Bisogna capire quali sono i valori che rappresentano queste 3 variabilli
 */


public class Simulatore {

	
	private PriorityQueue<Evento>queue=new PriorityQueue<>();
	
	//Stato del mondo
	private int autoTotali;		//parametro fisso che descrive il  mondo
	private int autoDisponibili;		//valore che può variare da 0 a autoTotale
	
	
	//parametri di simulazione=>dati che possono variare la soluzione, il simulatore lavora sopra questi dati
	private LocalTime oraInizio=LocalTime.of(8, 0);		//da quando è possibile prendere un auto
	private LocalTime oraFine=LocalTime.of(20, 0);		//fino a quando è possibile prendere un auto
	private Duration intervalloArrivoCliente=Duration.ofMinutes(10); // ogni quanto tempo arriva un cliente, intervallo fisso
	private List<Duration> durateNoleggio;		//si possono noleggiare auto per 1, 2, 3 ore
	
	//Statistiche raccolte=>output
	private int numClientiTotali;
	private int numClinetiInsoddisfatti;
	
	//variabili interne
	private Random rand=new Random();		
	
	
	
	public Simulatore() {
		durateNoleggio=new ArrayList<Duration>();
		durateNoleggio.add(Duration.ofHours(1));
		durateNoleggio.add(Duration.ofHours(2));
		durateNoleggio.add(Duration.ofHours(3));
	}
	
	
	
	
	
	
	/**
	 * Imposta i valori indispensabili per far si che la risoluzione possa avvenire. 
	 * Parto da una situazione iniziale.
	 */
	public void init(int autoTotali) {
		this.autoTotali=autoTotali;
		this.autoDisponibili=this.autoTotali;		//all'inizio l'officina è piena
		this.numClientiTotali=0;
		this.numClinetiInsoddisfatti=0;
		this.queue.clear();						//la coda deve essere pulita
	
		//carico gli eventi:arriva un cliente ogni intervalloArrivoCliente a partire dall' oraInizio
		for(LocalTime ora=oraInizio; ora.isBefore(oraFine); ora=ora.plus(intervalloArrivoCliente)) {
			queue.add(new Evento(ora, TipoEvento.CLIENTE_ARRIVA));		//non posso già indicare qui quanto dura il noleggio perchè magari non ho abbastanza auto per fargliela noleggiare
		}
	
	}
	
	
	
	
	
	public void run() {
		
		while(!queue.isEmpty()) {
			
			Evento ev=queue.poll();		//prende l'evento alla testa della coda e lo rimuove da essa
			
			switch(ev.getTipo()) {
			
				case CLIENTE_ARRIVA:
					
					this.numClientiTotali++;
					
					if(this.autoDisponibili==0) {
						this.numClinetiInsoddisfatti++;
					}else {		
						//noleggio auto
						this.autoDisponibili--;
						int i=rand.nextInt(durateNoleggio.size());	//estraggo l'indice casuale della lista durateNoleggio		
						Duration noleggio=durateNoleggio.get(i);	//ricavo il valore dell'indice estratto tramite random
						LocalTime rientro=ev.getTempo().plus(noleggio);		//quando il cliente restituirà l'auto
						
						queue.add(new Evento(rientro, TipoEvento.AUTO_RESTITUITA));
					}
					
					break;
					
				case AUTO_RESTITUITA:
					this.autoDisponibili++;
					break;
			
			}
			
		}
		
	}

	
	
	
	
	//le statistiche e le variabili interne non devono avere il set

	public LocalTime getOraInizio() {
		return oraInizio;
	}


	public void setOraInizio(LocalTime oraInizio) {
		this.oraInizio = oraInizio;
	}


	public LocalTime getOraFine() {
		return oraFine;
	}


	public void setOraFine(LocalTime oraFine) {
		this.oraFine = oraFine;
	}


	public Duration getIntervalloArrivoCliente() {
		return intervalloArrivoCliente;
	}


	public void setIntervalloArrivoCliente(Duration intervalloArrivoCliente) {
		this.intervalloArrivoCliente = intervalloArrivoCliente;
	}


	public List<Duration> getDurateNoleggio() {
		return durateNoleggio;
	}


	public void setDurateNoleggio(List<Duration> durateNoleggio) {
		this.durateNoleggio = durateNoleggio;
	}


	public int getAutoTotali() {
		return autoTotali;
	}


	public int getAutoDisponibili() {
		return autoDisponibili;
	}


	public int getNumClientiTotali() {
		return numClientiTotali;
	}


	public int getNumClinetiInsoddisfatti() {
		return numClinetiInsoddisfatti;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
