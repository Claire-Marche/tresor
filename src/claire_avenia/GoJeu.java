package claire_avenia;

import java.util.Scanner;

public class GoJeu {
	static final boolean MAP = false;
	/* Lorsque le booléeen est à true, le jeu charge une map que l'on a sélectionné.
	 * Pour qu'une map soit sélectionnée et chargée correctement, il faut décomenter sa partie correspondante dans cette classe, 
	 * ainsi que dans la classe Grille.
	 */
	
	static final int DIST_MIN = 2;
	
	public static void main(String args[]) {
		int largeur;
		int longueur;
		int nb_pierres;
		int nb_chasseurs;
		int nb_escrocs;
		int nb_furtifs;
		if (MAP) {
			/* Map 0 : nb_pierres = 8 ; longueur = 20 ; largeur = 30 ; nbChasseurs = 1 ; nbEscrocs = 0 ; nbFurtifs = 0
			largeur = 30;
			longueur = 20;
			nb_pierres = 8;
			nb_chasseurs = 1;
			nb_escrocs = 0;
			nb_furtifs = 0;
			*/
			
			/* Map 1 : longueur = 20 ; largeur = 30 ; nbChasseurs = 5 ; nbEscrocs = 1 ; nbFurtifs = 0
			largeur = 30;
			longueur = 20;
			nb_pierres = 20;
			nb_chasseurs = 5;
			nb_escrocs = 1;
			nb_furtifs = 0;
			*/
			
			/* Map 2 : longueur = 30 ; largeur = 50 ; nbChasseurs = 8 ; nbEscrocs = 0 ; nbFurtifs = 3
			largeur = 50;
			longueur = 30;
			nb_pierres = 20;
			nb_chasseurs = 8;
			nb_escrocs = 0;
			nb_furtifs = 3;
			*/
		} else {
			Scanner sc = new Scanner(System.in);
			
			System.out.println("Bienvenue à la chasse au trésor");
			System.out.println("Voulez-vous paramétrer le jeu ? (Oui / Non)");
			
			String answer;
			do {
				answer = sc.nextLine().toLowerCase();
			} while (!answer.equals("oui") && !answer.equals("non"));
			
			if (answer.equals("oui")) {
				System.out.println("Veuillez saisir la largeur de la grille");
				do {
					largeur = sc.nextInt();
				} while (largeur <= 0);
				
				System.out.println("Veuillez saisir la longueur de la grille");
				do {
					longueur = sc.nextInt();
				} while (longueur <= 0);
				
				System.out.println("Veuillez saisir le nombre de pierres à disposition du joueur");
				do {
					nb_pierres = sc.nextInt();
				} while (nb_pierres <= 0);
				
				System.out.println("Veuillez saisir le nombre de chasseurs");
				do {
					nb_chasseurs = sc.nextInt();
				} while (nb_chasseurs <= 0 || nb_chasseurs > largeur * longueur - (1 + 2 * DIST_MIN * (DIST_MIN - 1)));
				// 1 + 2n(n-1) correspond au nombre de cases de distance au trésor inférieure à n. Ainsi, il ne faut pas trop de chasseurs, afin qu'aucun ne doive se
				// retrouver sur une case de distance inférieure à DIST_MIN.
				
				System.out.println("Veuillez saisir le nombre de chasseurs qui sont des escrocs");
				do {
					nb_escrocs = sc.nextInt();
				} while (nb_escrocs < 0 || nb_escrocs > nb_chasseurs);
				
				System.out.println("Veuillez saisir le nombre de chasseurs qui sont furtifs");
				do {
					nb_furtifs = sc.nextInt();
				} while (nb_furtifs < 0 || nb_escrocs + nb_furtifs > nb_chasseurs);
			} else {
				largeur = 30;
				longueur = 20;
				nb_pierres = 8;
				nb_chasseurs = 5;
				nb_escrocs = 0;
				nb_furtifs = 0;
			}
		}
		Jeu jeu = new Jeu(MAP, largeur, longueur, nb_pierres, nb_chasseurs, nb_escrocs, nb_furtifs);
	}
}