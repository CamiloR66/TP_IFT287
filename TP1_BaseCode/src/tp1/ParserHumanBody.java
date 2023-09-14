package tp1;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ParserHumanBody extends DefaultHandler{

	private static final String SYSTEM = "System";
    private static final String FLOW = "Flow";
    private static final String CONNECTIBLE = "Connectible";
    private static final String CONNECTION = "Connection";
    private static final String TO = "to";
    private static final String ORGAN = "Organ";
	private StringBuilder elementValue;
	private boolean boolConnectibleType = false;
    private MainBody body;
    private system system;
    private Flow flow;
    private Connectible connectible;
    private Connection connection;
    private to to;
    private Organ organ;
    
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
        if (elementValue == null) {
            elementValue = new StringBuilder();
        } else {
            elementValue.append(ch, start, length);
        }
	}
    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
    }
    
    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    @Override
    public void startElement(String uri, String lName, String qName, Attributes attr) throws SAXException {
    	super.startElement(uri, lName, qName, attr);
        if(boolConnectibleType){

            int indexLength = attr.getIndex("length");
            int indexStartRadius = attr.getIndex("startRadius");
            int indexEndRadius = attr.getIndex("endRadius");
            int indexVolume = attr.getIndex("volume");

            connectible = new Connectible(attr.getValue(attr.getIndex("name")),Integer.parseInt(attr.getValue(attr.getIndex("id"))), ConnectibleType.valueOf(qName));
            if(indexLength!=-1){
                connectible.setLength(true);
                connectible.setLength(Float.parseFloat(attr.getValue(indexLength)));
            }
            if(indexStartRadius!=-1){
                connectible.setStartRadius(true);
                connectible.setStartRadius(Float.parseFloat(attr.getValue(indexStartRadius)));
            }
            if(indexEndRadius!=-1){
                connectible.setEndRadius(true);
                connectible.setEndRadius(Float.parseFloat(attr.getValue(indexEndRadius)));
            }
            if(indexVolume!=-1){
                connectible.setVolume(true);
                connectible.setVolume(Float.parseFloat(attr.getValue(indexVolume)));
            }
            flow.addConnectible(connectible);
        }
        switch (qName) {
            case "MainBody":
                body = new MainBody(attr.getValue(attr.getIndex("bodyName")),Integer.parseInt(attr.getValue(attr.getIndex("bodyID"))));
                break;
            case SYSTEM:
                system = new system(attr.getValue(attr.getIndex("name")),Integer.parseInt(attr.getValue(attr.getIndex("id"))), Integer.parseInt(attr.getValue(attr.getIndex("type"))));
                body.addSystem(system);
                break;
            case FLOW:
                flow = new Flow(Integer.parseInt(attr.getValue(attr.getIndex("id"))), attr.getValue(attr.getIndex("name")));
                system.addFlow(flow);
                break;
            case CONNECTIBLE:
                boolConnectibleType = true;
                //flow.addConnectible(connectible);
                break;
            case CONNECTION: 
            	connection = new Connection(Integer.parseInt(attr.getValue(attr.getIndex("id"))));
            	flow.addConnection(connection);
                break;
            case TO:
                to = new to(Integer.parseInt(attr.getValue(attr.getIndex("id"))));
                connection.addTo(to);
                break;
            case ORGAN:
                organ = new Organ(attr.getValue(attr.getIndex("name")),Integer.parseInt(attr.getValue(attr.getIndex("id"))),Integer.parseInt(attr.getValue(attr.getIndex("systemID"))));
                body.addOrgan(organ);
                break;

        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
    	super.endElement(uri, localName, qName);
        switch (qName) {
            case CONNECTIBLE:
                boolConnectibleType = false;
                break;
            case SYSTEM:
                body.addSystem(system);
                break;
            case FLOW:
                system.addFlow(flow);
                break;
            case CONNECTION:
                flow.addConnection(connection);
                break;
        }
    }
	public MainBody getMainBody() {
		return body;
	}
    

}
