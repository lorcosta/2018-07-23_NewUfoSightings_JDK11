package it.polito.tdp.newufosightings.model;

import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import it.polito.tdp.newufosightings.db.NewUfoSightingsDAO;
import it.polito.tdp.newufosightings.model.Event.TypeEvent;

public class Simulator {
	private NewUfoSightingsDAO dao;
	private Queue<Event> queue;
	private Integer time;
	private Integer alfa;
	
	public Simulator() {
		this.dao=new NewUfoSightingsDAO();
	}
	 public void init(Integer time, Integer alfa,Map<String,State> idMapState,String shape, Integer anno) {
		 this.time=time;
		 this.alfa=alfa;
		 this.queue=new PriorityQueue<Event>();
		 List<Event> avvistamenti=dao.getAvvistamenti(idMapState, anno,shape);
		 for(Event e:avvistamenti) {
			 this.queue.add(e);
		 }
	 }
	 public void run() {
		 while(!this.queue.isEmpty()) {
			 Event e=this.queue.poll();
			 processEvent(e);
		 }
	 }
	private void processEvent(Event e) {
		switch(e.getEvent()) {
		case AVVISTAMENTO:
			Double defconLevelStato=e.getStato().getDefconLevel();
			if(defconLevelStato>=2) {
				defconLevelStato--;
				e.getStato().setDefconLevel(defconLevelStato);
				this.queue.add(new Event(e.getStato(),e.getDatetime().plusDays(time),TypeEvent.SCADENZA_ALLERTA,e.getNeighbours()));
				Double random=Math.random()*100;
				if(random<=alfa) {
					for(State vicino:e.getStato().getNeighbors()) {
						Double defconLevelVicino=vicino.getDefconLevel();
						if(defconLevelVicino>=1.5) {
							defconLevelVicino-=0.5;
							vicino.setDefconLevel(defconLevelVicino);
						}else {
							System.err.println("Il vicino "+vicino+" dello stato "+e.getStato()+" ha un livello DEFCON gia' troppo basso per poterlo diminuire ulteriormente.");
							break;
						}
					}
				}
			}else {
				System.err.println("L'evento "+e.getEvent()+" del "+e.getDatetime()+" nello stato "+e.getStato()+" ha generato un livello DEFCON minore di 1");
				break;
			}
			
			break;
		case SCADENZA_ALLERTA:
			defconLevelStato=e.getStato().getDefconLevel();
			if(defconLevelStato<=4) {
				defconLevelStato++;
				e.getStato().setDefconLevel(defconLevelStato);
				Double random=Math.random()*100;
				if(random<=alfa) {
					for(State vicino:e.getStato().getNeighbors()) {
						Double defconLevelVicino=vicino.getDefconLevel();
						if(defconLevelVicino<=4.5) {
							defconLevelVicino+=0.5;
							vicino.setDefconLevel(defconLevelVicino);
						}else {
							System.err.println("Il vicino "+vicino+" dello stato "+e.getStato()+" ha un livello DEFCON gia' troppo alto per poterlo aumentare ulteriormente.");
							break;
						}
					}
				}
			}else {
				System.err.println("L'evento "+e.getEvent()+" del "+e.getDatetime()+" nello stato "+e.getStato()+" ha generato un livello DEFCON maggiore di 5");
				break;
			}
			break;
		default:
			break;
		
		}
		
	}
}
