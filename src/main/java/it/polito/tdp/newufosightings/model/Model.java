package it.polito.tdp.newufosightings.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.newufosightings.db.Confine;
import it.polito.tdp.newufosightings.db.NewUfoSightingsDAO;

public class Model {
	private NewUfoSightingsDAO dao= new NewUfoSightingsDAO();
	private Graph<State,DefaultWeightedEdge> graph;
	private Map<String,State> idMapState;
	private Simulator sim=new Simulator();
	private Integer anno;
	private String shape;
	
	public Model() {
		this.idMapState=new HashMap<>();
		dao.loadAllStates(idMapState);
	}
	public List<String> getForme(Integer anno) {
		return dao.getForme(anno);
	}
	
	public void creaGrafo(String shape,Integer year) {
		this.shape=shape;
		this.anno=year;
		this.graph=new SimpleWeightedGraph<State,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(this.graph, dao.loadAllStates(idMapState));
		List<Confine> confini=new ArrayList<>(dao.getStatiConfinanti(shape, year, idMapState));
		for(Confine c:confini) {
			if(this.graph.vertexSet().contains(c.getS1()) && this.graph.vertexSet().contains(c.getS2())
					&& this.graph.getEdge(c.getS1(), c.getS2())==null && !c.getS1().equals(c.getS2())) {
				Graphs.addEdgeWithVertices(this.graph, c.getS1(), c.getS2(), c.getPeso());
			}
		}
	}
	public Integer getNumVertici() {
		return this.graph.vertexSet().size();
	}
	public Integer getNumArchi() {
		return this.graph.edgeSet().size();
	}
	
	public List<StatoConPesi> getStatiConPesi(){
		List<StatoConPesi> result=new ArrayList<StatoConPesi>();
		for(State s:this.graph.vertexSet()) {
			Double pesoTot=0.;
			List<State> vicini=Graphs.neighborListOf(this.graph, s);
			idMapState.get(s.getId()).setNeighbors(vicini);
			for(State vicino:vicini) {
				pesoTot+=this.graph.getEdgeWeight(this.graph.getEdge(s, vicino));
			}
			result.add(new StatoConPesi(s,pesoTot));
		}
		return result;
	}
	/**
	 * Ritorna la lista di stati tramite i quali si può accedere al DEFCON level
	 * @param time
	 * @param alfa
	 */
	public Collection<State> simula(Integer time, Integer alfa) {
		sim.init(time,alfa,idMapState,shape,anno);
		sim.run();
		return idMapState.values();
	}

}
