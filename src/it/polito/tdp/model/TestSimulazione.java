package it.polito.tdp.model;

import java.time.Duration;

public class TestSimulazione {
	
	public static void main(String[] args) {
		
		
		Simulatore sim=new Simulatore();
		sim.setIntervalloArrivoCliente(Duration.ofMinutes(30));		//modifico l'intervallo di arrivo
		sim.init(15);
		sim.run();
		System.out.print("I clienti totali sono: "+sim.getNumClientiTotali()+" di cui "+sim.getNumClinetiInsoddisfatti()+" insoddisfatti ");
		
	}
	

}
