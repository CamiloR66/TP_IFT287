// Travail fait par :
//   NomEquipier1 - Matricule
//   NomEquipier2 - Matricule

package tp1;

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

        // Votre code de conversion devrait aller ici
        
        System.out.println("Conversion terminee.");
    }

}
