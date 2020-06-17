package it.polito.tdp.newufosightings;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.newufosightings.model.Model;
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
    		this.txtResult.appendText("ATTENZIONE! Il valore inserito non è un numero corretto.\n");
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
