package it.polito.tdp.newufosightings.model;

import java.util.ArrayList;
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
	
	public Model() {
		this.idMapState=new HashMap<>();
		dao.loadAllStates(idMapState);
	}
	public List<String> getForme(Integer anno) {
		return dao.getForme(anno);
	}
	
	public void creaGrafo(String shape,Integer year) {
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

}
