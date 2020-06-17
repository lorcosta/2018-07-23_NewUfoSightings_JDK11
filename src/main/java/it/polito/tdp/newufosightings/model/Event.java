package it.polito.tdp.newufosightings.model;

import java.time.LocalDate;
import java.util.List;

public class Event implements Comparable<Event>{
	public enum TypeEvent{
		AVVISTAMENTO,//avviene un avvistamento perciò il DEFCON diminuisce
		SCADENZA_ALLERTA,// è passato un tempo T1 perciò il DEFCON auementa
	}
	private State stato;
	private LocalDate datetime;//necessario perchè dopo T1 giorni il livello DEFCON aumenta
	private List<State> neighbours;
	private TypeEvent type;
	/**
	 * @param stato
	 * @param datetime
	 * @param neighbours
	 */
	public Event(State stato, LocalDate datetime, TypeEvent type,List<State> neighbours) {
		super();
		this.stato = stato;
		this.datetime = datetime;
		this.neighbours = neighbours;
		this.type=type;
	}
	public State getStato() {
		return stato;
	}
	public LocalDate getDatetime() {
		return datetime;
	}
	public List<State> getNeighbours() {
		return neighbours;
	}
	public TypeEvent getEvent() {
		return type;
	}
	public void setEvent(TypeEvent type) {
		this.type = type;
	}
	@Override
	public int compareTo(Event other) {
		return this.datetime.compareTo(other.getDatetime());
	}
}
