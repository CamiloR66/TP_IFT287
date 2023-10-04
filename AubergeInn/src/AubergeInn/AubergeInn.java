// Travail fait par :
//   NomEquipier1 - Matricule
//   NomEquipier2 - Matricule

package AubergeInn;

import java.io.*;
import java.util.StringTokenizer;
import java.sql.*;
import AubergeInn.Connexion;
import AubergeInn.Modele.*;
import AubergeInn.Gestionnaire.*;


/**
 * Fichier de base pour le TP2 du cours IFT287
 *
 * <pre>
 * 
 * Kerson Boisrond
 * Universite de Sherbrooke
 * Version 2.0 - 18 Septembre 2023
 * IFT287 - Exploitation de BD relationnelles et OO
 * 
 * Ce programme permet d'appeler des transactions d'un systeme
 * de gestion utilisant une base de donnees.
 *
 * Paramètres du programme
 * 0- site du serveur SQL ("local" ou "dinf")
 * 1- nom de la BD
 * 2- user id pour etablir une connexion avec le serveur SQL
 * 3- mot de passe pour le user id
 * 4- fichier de transaction [optionnel]
 *           si non spécifié, les transactions sont lues au
 *           clavier (System.in)
 *
 * Pré-condition
 *   - La base de donnees doit exister
 *
 * Post-condition
 *   - Le programme effectue les mises à jour associees à chaque
 *     transaction
 * </pre>
 */
public class AubergeInn {
    private static Connexion cx;

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        if (args.length < 4) {
            System.out.println(
                    "Usage: java AubergeInn.AubergeInn <serveur> <bd> <user> <password> [<fichier-transactions>]");
            return;
        }

        cx = null;

        try {
            // Il est possible que vous ayez à déplacer la connexion ailleurs.
            // N'hésitez pas à le faire!
            cx = new Connexion(args[0], args[1], args[2], args[3]);
            BufferedReader reader = ouvrirFichier(args);
            String transaction = lireTransaction(reader);
            while (!finTransaction(transaction)) {
                executerTransaction(transaction);
                transaction = lireTransaction(reader);
            }
        } finally {
            if (cx != null)
                cx.fermer();
        }
    }

    /**
     * Decodage et traitement d'une transaction
     */
    static void executerTransaction(String transaction) throws Exception, IFT287Exception
    {
        try
        {
            System.out.print(transaction);
            // Decoupage de la transaction en mots
            StringTokenizer tokenizer = new StringTokenizer(transaction, " ");
            if (tokenizer.hasMoreTokens())
            {
                String command = tokenizer.nextToken();
                // Vous devez remplacer la chaine "commande1" et "commande2" par
                // les commandes de votre programme. Vous pouvez ajouter autant
                // de else if que necessaire. Vous n'avez pas a traiter la
                // commande "quitter".
                if (command.equals("commande1"))
                {
                    // Lecture des parametres
                    String param1 = readString(tokenizer);
                    Date param2 = readDate(tokenizer);
                    int param3 = readInt(tokenizer);
                    // Appel de la methode des gestionnaires qui traite la transaction specifique
                }
                else if (command.equals("commande2"))
                {
                    // Lire les parametres ici et appeler la bonne methode
                    // de traitement pour la transaction
                }if (command.equals("ajouterClient")) {
                    // Extract parameters
                    int idClient = readInt(tokenizer);
                    String prenom = readString(tokenizer);
                    String nom = readString(tokenizer);
                    int age = readInt(tokenizer);
                    
                    Client client = new Client(idClient, prenom, nom, age);

                    client.printClient();

                    // Call the method to add a client
                    // addClient(idClient, prenom, nom, age);
                } else if (command.equals("supprimerClient")) {
                    // Extract parameter
                    int idClient = readInt(tokenizer);
    
                    // Call the method to delete a client
                    // deleteClient(idClient);
                } else if (command.equals("ajouterChambre")) {
                    // Extract parameters
                    int idChambre = readInt(tokenizer);
                    String nomChambre = readString(tokenizer);
                    String typeLit = readString(tokenizer);
                    float prixBase = Float.parseFloat(readString(tokenizer));
    
                    // Call the method to add a room
                    // addRoom(idChambre, nomChambre, typeLit, prixBase);
                } else if (command.equals("supprimerChambre")) {
                    // Extract parameter
                    int idChambre = readInt(tokenizer);
    
                    // Call the method to delete a room
                    // deleteRoom(idChambre);
                } else if (command.equals("ajouterCommodite")) {
                    // Extract parameters
                    int idCommodite = readInt(tokenizer);
                    String description = readString(tokenizer);
                    float surplusPrix = Float.parseFloat(readString(tokenizer));
    
                    // Call the method to add a commodity
                    // addCommodity(idCommodite, description, surplusPrix);
                } else if (command.equals("inclureCommodite")) {
                    // Extract parameters
                    int idChambre = readInt(tokenizer);
                    int idCommodite = readInt(tokenizer);
    
                    // Call the method to include a commodity in a room
                    // includeCommodityInRoom(idChambre, idCommodite);
                } else if (command.equals("enleverCommodite")) {
                    // Extract parameters
                    int idChambre = readInt(tokenizer);
                    int idCommodite = readInt(tokenizer);
    
                    // Call the method to remove a commodity from a room
                    // removeCommodityFromRoom(idChambre, idCommodite);
                } else if (command.equals("afficherChambresLibres")) {
                    // Extract parameters
                    String dateDebut = tokenizer.nextToken();
                    String dateFin = tokenizer.nextToken();
    
                    // Call the method to display available rooms between dates
                    // displayAvailableRooms(dateDebut, dateFin);
                } else if (command.equals("afficherClient")) {
                    // Extract parameter
                    int idClient = readInt(tokenizer);

                    
    
                    // Call the method to display client information
                    // displayClient(idClient);
                } else if (command.equals("afficherChambre")) {
                    // Extract parameter
                    int idChambre = readInt(tokenizer);
    
                    // Call the method to display room information
                    // displayRoom(idChambre);
                } else if (command.equals("reserver")) {
                    // Extract parameters
                    int idClient = readInt(tokenizer);
                    int idChambre = readInt(tokenizer);
                    String dateDebut = readString(tokenizer);
                    String dateFin = readString(tokenizer);
                }
    
                    // Call the method to make a reservation
                    // makeReservation(idClient, idChambre, dateDebut, dateFin);
                else
                {
                    System.out.println(" : Transaction non reconnue");
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(" " + e.toString());
            // Ce rollback est ici seulement pour vous aider et éviter des problèmes lors de la correction
            // automatique. En théorie, il ne sert à rien et ne devrait pas apparaître ici dans un programme
            // fini et fonctionnel sans bogues.
            cx.rollback();
        }
    }

    // ****************************************************************
    // * Les methodes suivantes n'ont pas besoin d'etre modifiees *
    // ****************************************************************

    /**
     * Ouvre le fichier de transaction, ou lit à partir de System.in
     */
    public static BufferedReader ouvrirFichier(String[] args) throws FileNotFoundException {
        if (args.length < 5)
            // Lecture au clavier
            return new BufferedReader(new InputStreamReader(System.in));
        else
            // Lecture dans le fichier passe en parametre
            return new BufferedReader(new InputStreamReader(new FileInputStream(args[4])));
    }

    /**
     * Lecture d'une transaction
     */
    static String lireTransaction(BufferedReader reader) throws IOException {
        return reader.readLine();
    }

    /**
     * Verifie si la fin du traitement des transactions est atteinte.
     */
    static boolean finTransaction(String transaction) {
        // fin de fichier atteinte
        return (transaction == null || transaction.equals("quitter"));
    }

    /** Lecture d'une chaine de caracteres de la transaction entree a l'ecran */
    static String readString(StringTokenizer tokenizer) throws Exception {
        if (tokenizer.hasMoreElements())
            return tokenizer.nextToken();
        else
            throw new Exception("Autre parametre attendu");
    }

    /**
     * Lecture d'un int java de la transaction entree a l'ecran
     */
    static int readInt(StringTokenizer tokenizer) throws Exception {
        if (tokenizer.hasMoreElements()) {
            String token = tokenizer.nextToken();
            try {
                return Integer.valueOf(token).intValue();
            } catch (NumberFormatException e) {
                throw new Exception("Nombre attendu a la place de \"" + token + "\"");
            }
        } else
            throw new Exception("Autre parametre attendu");
    }

    static Date readDate(StringTokenizer tokenizer) throws Exception {
        if (tokenizer.hasMoreElements()) {
            String token = tokenizer.nextToken();
            try {
                return Date.valueOf(token);
            } catch (IllegalArgumentException e) {
                throw new Exception("Date dans un format invalide - \"" + token + "\"");
            }
        } else
            throw new Exception("Autre parametre attendu");
    }

}
