package it.polito.tdp.newufosightings.db;

import it.polito.tdp.newufosightings.model.State;

public class Confine {
	private State s1;
	private State s2;
	private Double peso;
	
	/**
	 * @param s1
	 * @param s2
	 * @param peso
	 */
	public Confine(State s1, State s2, Double peso) {
		super();
		this.s1 = s1;
		this.s2 = s2;
		this.peso = peso;
	}
	public State getS1() {
		return s1;
	}
	public void setS1(State s1) {
		this.s1 = s1;
	}
	public State getS2() {
		return s2;
	}
	public void setS2(State s2) {
		this.s2 = s2;
	}
	public Double getPeso() {
		return peso;
	}
	public void setPeso(Double peso) {
		this.peso = peso;
	}
	
	
}
