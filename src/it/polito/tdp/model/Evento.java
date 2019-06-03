package it.polito.tdp.model;

import java.time.LocalTime;

public class Evento implements Comparable<Evento> {
	
	//insieme di costanti che identificano i tipi di eventi che si possono verificare
	public enum TipoEvento{		
		CLIENTE_ARRIVA, 
		AUTO_RESTITUITA
	}
	
	private LocalTime tempo;
	private TipoEvento tipo;
	
	
	//costruttore
	public Evento(LocalTime tempo, TipoEvento tipo) {
		super();
		this.tempo = tempo;
		this.tipo = tipo;
	}


	//devo sempre fare il confronto, quindi si implementa la classe Comparable
	public int compareTo(Evento other) {
		return this.tempo.compareTo(other.tempo);
	}


	
	
	//get e set
	public LocalTime getTempo() {
		return tempo;
	}

	public TipoEvento getTipo() {
		return tipo;
	}



	

}
