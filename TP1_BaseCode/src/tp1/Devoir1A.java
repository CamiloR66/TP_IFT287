// Travail fait par :
//   NomEquipier1 - Matricule
//   NomEquipier2 - Matricule

package tp1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

/*import jakarta.json.Json;
import jakarta.json.stream.JsonGenerator;
import jakarta.json.stream.JsonGeneratorFactory;*/

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonGeneratorFactory;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Fichier de base pour le Devoir1A du cours IFT287
 *
 * <pre>
 * 
 * Vincent Ducharme
 * Universite de Sherbrooke
 * Version 1.0 - 6 août 2016
 * IFT287 - Exploitation de BD relationnelles et OO
 * 
 * Ce programme permet de convertir un fichier XML en son équivalent en JSON.
 *
 * Paramètres du programme
 * 0- Nom du fichier XML
 * 1- Nom du fichier JSON
 * 
 * </pre>
 */
public class Devoir1A
{
	private static MainBody mainBody;

	public static void main(String[] args)
	{
		if (args.length < 2)
		{
			System.out.println("Usage: java tp1.Devoir1A <fichierXML> <fichierJSON>");
			return;
		}

		String nomFichierXML = args[0];
		String nomFichierJSON = args[1];

		System.out.println("Debut de la conversion du fichier " + nomFichierXML + " vers le fichier " + nomFichierJSON);

		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setValidating(true);
		try
		{
			SAXParser Parser = factory.newSAXParser();
			DefaultHandler Handler = new ParserHumanBody();
			Parser.parse(new File(nomFichierXML), Handler);
			mainBody = ((ParserHumanBody) Handler).getMainBody();


			Map<String, Object> config = new HashMap<String, Object>(1);
			config.put(JsonGenerator.PRETTY_PRINTING, true);

			FileWriter writer = new FileWriter(nomFichierJSON);
			JsonGeneratorFactory f = Json.createGeneratorFactory(config);
			JsonGenerator gen = f.createGenerator(writer);

			mainBody.JSONconverter(gen);
			gen.close();      
		}
		catch (ParserConfigurationException e)
		{
			// TODO Auto-generated catch block

			e.printStackTrace();
		}
		catch (SAXException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
			// TODO: handle exception
		}


		// Votre code de conversion devrait aller ici


		System.out.println("Conversion terminee.");



		System.out.println("Debut de la conversion du fichier " + nomFichierJSON + " vers le fichier " + nomFichierXML);
		DocumentBuilderFactory factory2 = DocumentBuilderFactory.newInstance();
		Document document;
		try {
			document = factory2.newDocumentBuilder().newDocument();
			mainBody.XMLConverter(document);
			String dtdName = "HumanBody.dtd";

			FileOutputStream output = new FileOutputStream(nomFichierXML+"1");
			PrintStream out = new PrintStream(output);

			TransformerFactory allSpark = TransformerFactory.newInstance();
			Transformer optimusPrime = allSpark.newTransformer();
			optimusPrime.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, dtdName);
			optimusPrime.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(out);
			optimusPrime.transform(source, result);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}








	}


	/* private static void exportJSON(MainBody body, JsonGenerator g) {

    	g.writeStartObject();
    	g.write("bodyID", body.bodyID);
    	JsonGenerator generateSystem = g.writeStartArray("Systems");
    	for(system sys : body.getListeSystems()) {
    		generateSystem.writeStartObject();
    		generateSystem.write("name", sys.getName());
    		generateSystem.write("id", sys.getId());
    		generateSystem.write("type", sys.getType());
			JsonGenerator generateFlow = g.writeStartArray("Flow");
			for(Flow flow : sys.getListeFlow()) {
				generateFlow.writeStartObject();
				generateFlow.write("name", flow.getName());
				generateFlow.write("id", flow.getId());
				JsonGenerator generateConnectible = g.writeStartArray("Connectible");
				generateConnectible.writeStartObject();
				for (Connectible connectible : flow.getListeConnectible()) {
					generateConnectible.writeStartArray(connectible.getConnectibleType().toString());
					generateConnectible.writeStartObject();
					System.out.println("  " + sys.getName() + "  " + flow.getName() + "  "
							+ connectible.getConnectibleType().toString() + "  ");
					generateConnectible.writeEnd();
					generateConnectible.writeEnd();
				}
				generateConnectible.writeEnd();
				generateConnectible.writeEnd();
				JsonGenerator generateConnection = generateFlow.writeStartArray("Connections");
				for (Connection conn : flow.getListeConnections()) {
					generateConnection.writeStartObject();
					generateConnection.write("id", conn.getId());
					JsonGenerator genTo = generateConnection.writeStartArray("to");
					for (to toCon : conn.getListeTo()) {
						genTo.writeStartObject();
						genTo.write("id", (JsonValue) toCon);
						genTo.writeEnd();
					}
					genTo.writeEnd();
					generateConnection.writeEnd();
				}
				generateConnection.writeEnd();
				generateFlow.writeEnd();
			}
			generateFlow.writeEnd();
			generateSystem.writeEnd();
		}


    }*/

}
