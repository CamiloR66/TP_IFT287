//// Travail fait par :
////   Camilo Andres Rangel Varon - RANC2202
////   Xavier Vallières - VALX2701
package tp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonGeneratorFactory;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.helpers.DefaultHandler;

public class Devoir1 {

    private static final String CMD_IMPORTER = "importer";
    private static final String CMD_EXPORTER = "exporter";
    private static final String CMD_QUITTER = "quitter";
    private static final String TYPE_XML = "xml";
    private static final String TYPE_JSON = "json";
	private static MainBody mainBody;
	private static Document document;  


    public static void main(String[] args) {
        try
        {
            // Il est possible que vous ayez à déplacer la connexion ailleurs.
            // N'hésitez pas à le faire!
            BufferedReader reader = ouvrirFichier(args);
            String transaction = lireTransaction(reader);
            while (!finTransaction(transaction))
            {
                executerTransaction(transaction);
                transaction = lireTransaction(reader);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    private static String getExtensionFichier(String nomFichier) {
        if(nomFichier.lastIndexOf(".") != -1 && nomFichier.lastIndexOf(".") != 0)
            return nomFichier.substring(nomFichier.lastIndexOf(".")+1);
        else return "";
    }

    /**
     * Decodage et traitement d'une transaction
     */
    static void executerTransaction(String transaction) throws Exception, IFT287Exception
    {
        try
        {
            System.out.print(transaction + " ");
            // Decoupage de la transaction en mots
            StringTokenizer tokenizer = new StringTokenizer(transaction, " ");            
			String dtdName = "HumanBody.dtd";
			

            if (tokenizer.hasMoreTokens())
            {
                String mode = tokenizer.nextToken();
                String nomFichier = readString(tokenizer);
                String extension = getExtensionFichier(nomFichier);

                if (mode.equals(CMD_IMPORTER)){
                    if(extension.equals(TYPE_XML)){
                        System.out.println("Debut de l'importation du fichier XML " + nomFichier);
                        // Votre code d'importation XML ici (Partie 2)
                        
                        SAXParserFactory factory = SAXParserFactory.newInstance();
                		factory.setValidating(true);
                		
                        SAXParser Parser = factory.newSAXParser();
            			DefaultHandler Handler = new ParserHumanBody();
            			Parser.parse(new File(nomFichier), Handler);
            			mainBody = ((ParserHumanBody) Handler).getMainBody();



                    }
                    else if (extension.equals(TYPE_JSON)){
                        System.out.println("Debut de l'importation du fichier JSON " + nomFichier);
                        //Votre code d'importation JSON ici (Partie 4)
                        DocumentBuilderFactory factory2 = DocumentBuilderFactory.newInstance();
                		                      
                        document = factory2.newDocumentBuilder().newDocument();
            			mainBody.XMLConverter(document);

                    }
                    else {
                        System.out.println("Le système ne supporte actuellement pas l'importation des fichiers au format " + extension);
                    }
                }
                else if (mode.equals(CMD_EXPORTER)){
                    if(extension.equals(TYPE_XML)){
                        System.out.println("Debut de l'exportation vers le fichier XML " + nomFichier);
                        // Votre code d'exportation XML ici (Partie 4)
                        FileOutputStream fichierSortie = new FileOutputStream(nomFichier);
            			PrintStream sortie = new PrintStream(fichierSortie);

            			TransformerFactory reFactory = TransformerFactory.newInstance();
            			Transformer tranJSONtoXML = reFactory.newTransformer();
            			
            			tranJSONtoXML.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, dtdName);
            			tranJSONtoXML.setOutputProperty(OutputKeys.INDENT, "yes");
            			
            			DOMSource source = new DOMSource(document);
            			StreamResult result = new StreamResult(sortie);
            			tranJSONtoXML.transform(source, result);


                    }
                    else if (extension.equals(TYPE_JSON)){
                        System.out.println("Debut de l'exportation vers le fichier JSON " + nomFichier);
                        //Votre code d'exportation JSON ici (Partie 3)
                        Map<String, Object> config = new HashMap<String, Object>(1);
            			config.put(JsonGenerator.PRETTY_PRINTING, true);
                        
                        FileWriter writer = new FileWriter(nomFichier);
            			JsonGeneratorFactory f = Json.createGeneratorFactory(config);
            			JsonGenerator gen = f.createGenerator(writer);

            			mainBody.JSONconverter(gen);
            			gen.close();  

                    }
                    else {
                        System.out.println("Le système ne supporte actuellement pas l'exportation vers les fichiers au format " + extension);
                    }
                }
                else{
                    System.out.println("Commande inconnue, choisir entre 'importer' ou 'exporter'");
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(" " + e.toString());
        }
    }


    // ****************************************************************
    // *   Les methodes suivantes n'ont pas besoin d'etre modifiees   *
    // ****************************************************************

    /**
     * Ouvre le fichier de transaction, ou lit à partir de System.in
     */
    public static BufferedReader ouvrirFichier(String[] args) throws FileNotFoundException {
        if (args.length < 1)
            // Lecture au clavier
            return new BufferedReader(new InputStreamReader(System.in));
        else
            // Lecture dans le fichier passe en parametre
            return new BufferedReader(new InputStreamReader(new FileInputStream(args[0])));
    }

    /**
     * Lecture d'une transaction
     */
    static String lireTransaction(BufferedReader reader) throws IOException
    {
        return reader.readLine();
    }

    /**
     * Verifie si la fin du traitement des transactions est atteinte.
     */
    static boolean finTransaction(String transaction)
    {
        // fin de fichier atteinte
        return (transaction == null || transaction.equals(CMD_QUITTER));
    }

    /** Lecture d'une chaine de caracteres de la transaction entree a l'ecran */
    static String readString(StringTokenizer tokenizer) throws Exception
    {
        if (tokenizer.hasMoreElements())
            return tokenizer.nextToken();
        else
            throw new Exception("Autre parametre attendu");
    }

}
