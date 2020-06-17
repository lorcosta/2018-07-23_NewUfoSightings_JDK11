package it.polito.tdp.newufosightings;

import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.newufosightings.model.Model;
import it.polito.tdp.newufosightings.model.State;
import it.polito.tdp.newufosightings.model.StatoConPesi;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

//controller turno A --> switchare al branch master_turnoB per turno B

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea txtResult;

    @FXML
    private TextField txtAnno;

    @FXML
    private Button btnSelezionaAnno;

    @FXML
    private ComboBox<String> cmbBoxForma;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private TextField txtT1;

    @FXML
    private TextField txtAlfa;

    @FXML
    private Button btnSimula;

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	this.txtResult.clear();
    	String annoString=this.txtAnno.getText();
    	Integer anno=null;
    	try {
    		anno=Integer.parseInt(annoString);
    	}catch (NumberFormatException e) {
    		e.printStackTrace();
    		this.txtResult.appendText("ATTENZIONE! Il valore inserito non è un numero corretto.\n");
    	}
    	if(anno<1940 || anno>2014) {
    		this.txtResult.appendText("ATTENZIONE! Vengono considerati validi sono gli anni compresi tra il 1940 e il 2014\n");
    		return;
    	}
    	String shape=this.cmbBoxForma.getValue();
    	if(shape==null) {
    		this.txtResult.appendText("ATTENZIONE! Forse non e' stato creato il grafo! Riprovare...\n");
    		doSelezionaAnno(event);
    	}
    	model.creaGrafo(shape, anno);
    	Integer vertici=model.getNumVertici(), archi=model.getNumArchi();
    	if(vertici==0 && archi.equals(0)) {
    		this.txtResult.appendText("ATTENZIONE! Qualcosa e' andato storto nella creazione del grafo.\n");
    		return;
    	}
    	this.txtResult.appendText("GRAFO CREATO!\n #VERTICI: "+vertici+" e #ARCHI: "+archi+"\n");
    	this.txtResult.appendText("\nSTATI CON RELATIVI PESI DEGLI SRCHI ADIACENTI:\n");
    	List<StatoConPesi> statiConPesi=model.getStatiConPesi();
    	for(StatoConPesi s:statiConPesi) {
    		this.txtResult.appendText(s+"\n");
    	}
    	
    }

    @FXML
    void doSelezionaAnno(ActionEvent event) {
    	this.txtResult.clear();
    	String annoString=this.txtAnno.getText();
    	Integer anno=null;
    	try {
    		anno=Integer.parseInt(annoString);
    	}catch (NumberFormatException e) {
    		e.printStackTrace();
    		this.txtResult.appendText("ATTENZIONE! Il valore 'Anno' inserito non è un numero corretto.\n");
    	}
    	if(anno<1940 || anno>2014) {
    		this.txtResult.appendText("ATTENZIONE! Vengono considerati validi sono gli anni compresi tra il 1940 e il 2014\n");
    		return;
    	}
    	this.cmbBoxForma.getItems().addAll(model.getForme(anno));
    }

    @FXML
    void doSimula(ActionEvent event) {
    	this.txtResult.clear();
    	String timeString=this.txtT1.getText();
    	Integer time=null;
    	try {
    		time=Integer.parseInt(timeString);
    	}catch (NumberFormatException e) {
    		e.printStackTrace();
    		this.txtResult.appendText("ATTENZIONE! Il valore 'T1' inserito non è un numero corretto.\n");
    	}
    	if(time>365 || time<0) {
    		this.txtResult.appendText("ATTENZIONE! Inserire un valore di 'T1' compreso tra 0 e 365");
    	}
    	String alfaString=this.txtAlfa.getText();
    	Integer alfa=null;
    	try {
    		alfa=Integer.parseInt(alfaString);
    	}catch (NumberFormatException e) {
    		e.printStackTrace();
    		this.txtResult.appendText("ATTENZIONE! Il valore 'Alfa' inserito non è un numero corretto.\n");
    	}
    	if(alfa>100 || alfa<0) {
    		this.txtResult.appendText("ATTENZIONE! Inserire un valore di 'Alfa' compreso tra 0 e 100");
    	}
    	Collection<State> stati=model.simula(time,alfa);
    	this.txtResult.appendText("STATO-->DEFCON LEVEL\n");
    	for(State s:stati) {
    		this.txtResult.appendText(s+"-->"+s.getDefconLevel()+"\n");
    	}
    }

    @FXML
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
        assert btnSelezionaAnno != null : "fx:id=\"btnSelezionaAnno\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
        assert cmbBoxForma != null : "fx:id=\"cmbBoxForma\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
        assert txtT1 != null : "fx:id=\"txtT1\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
        assert txtAlfa != null : "fx:id=\"txtAlfa\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
	}
}
