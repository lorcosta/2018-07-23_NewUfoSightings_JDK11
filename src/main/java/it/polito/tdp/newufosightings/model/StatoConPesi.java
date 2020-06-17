package it.polito.tdp.newufosightings.model;

public class StatoConPesi {
	private State state;
	private Double peso;
	
	/**
	 * @param state
	 * @param peso
	 */
	public StatoConPesi(State state, Double peso) {
		super();
		this.state = state;
		this.peso = peso;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public Double getPeso() {
		return peso;
	}
	public void setPeso(Double peso) {
		this.peso = peso;
	}
	@Override
	public String toString() {
		return state.getName()+"-->"+peso;
	}
	
}
