package claire_avenia;

import java.util.ArrayList;
import java.util.PriorityQueue;
import javax.swing.*;
import java.awt.*;

public class Grille {
	private ArrayList<Case> grille;
	private PriorityQueue<Chasseur> chasseurs;
	private Tresor tresor;
	private int largeur, longueur;
	
	static final int DIST_MIN = 2;
	
	public Grille(int longueur, int largeur, int nbChasseurs, int nbEscrocs, int nbFurtifs, boolean map) {
		this.largeur = largeur;
		this.longueur = longueur;
		this.grille = new ArrayList<Case>();
		int tresor_pos = (int) (Math.random() * (this.longueur * this.largeur));
		int [] rangs = null;
		
		/* Map 0 : nb_pierres = 8 ; longueur = 20 ; largeur = 30 ; nbChasseurs = 1 ; nbEscrocs = 0 ; nbFurtifs = 0
		tresor_pos = 15 * 30 + 10;
		rangs = new int[1];
		rangs[0] = 2 * 30 + 5;
		 */
		
		/* Map 1 : longueur = 20 ; largeur = 30 ; nbChasseurs = 5 ; nbEscrocs = 1 ; nbFurtifs = 0
		 tresor_pos = 15 * 30 + 9;
		 rangs = new int[5];
		 rangs[0] = 3 * 30 + 9;
		 rangs[1] = 30 + 9;
		 rangs[2] = 90;
		 rangs[3] = 105;
		 rangs[4] = 137;
		 */
		 
		 /* Map 2 : longueur = 30 ; largeur = 50 ; nbChasseurs = 8 ; nbEscrocs = 0 ; nbFurtifs = 3
		 tresor_pos = 20 * 30 + 26;
		 rangs = new int[8];
		 rangs[0] = 3 * 30 + 9;
		 rangs[1] = 16 * 30 + 18;
		 rangs[2] = 26 * 30 + 40;
		 rangs[3] = 105;
		 rangs[4] = 137;
		 rangs[5] = 389;
		 rangs[6] = 405;
		 rangs[7] = 587;
		 */
		  
		for (int i = 0; i < this.longueur; i++) {
			for (int j = 0; j < this.largeur; j++) {
				this.grille.add(new Case(j, i));
			}
		}
 
		 
		this.tresor = new Tresor(this.grille.get(tresor_pos));
		this.chasseurs = new PriorityQueue<Chasseur>();
		int escrocs_restants = nbEscrocs;
		int furtifs_restants = nbFurtifs;
		for (int i = 0; i < nbChasseurs; i++) {
			int rang;
			Case pos;
			do {
				if (map) {
					rang = rangs[i];
				} else {
					rang = (int) (Math.random() * (this.longueur * this.largeur));
				}
				pos = this.grille.get(rang);
			} while (!map && pos.getDistance(this.tresor) < DIST_MIN);
			if (escrocs_restants > 0) {
				escrocs_restants--;
				this.chasseurs.add(new Escroc(pos, this.tresor));
			} else if (furtifs_restants > 0) {
				furtifs_restants--;
				this.chasseurs.add(new Furtif(pos, this.tresor));
			} else {
				this.chasseurs.add(new Chasseur(pos, this.tresor));
			}
		}
	}
	
	public PriorityQueue<Chasseur> getChasseurs() {
		return this.chasseurs;
	}
	
	public Tresor getTresor() {
		return this.tresor;
	}
	
	public Case getPos(int x, int y) {
		if (x >= this.largeur || y >= this.longueur || x < 0 || y < 0) {
			return null;
		}
		
		//System.out.println("(" + x + ", " + y + ") : " + y + " * " + this.largeur + " + " + x + " = " + (y * this.largeur + x));
		return this.grille.get(y * this.largeur + x);
	}
	
	public void draw(Container context) {
		for (Case c : this.grille) {
			JPanel cRep = new JPanel();
			//JLabel displayDist = new JLabel(c.getDistance(this.tresor) + "");
			//cRep.add(displayDist);
			cRep.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			if (c.getDistance(this.tresor) == -1) {
				cRep.setBackground(Color.DARK_GRAY); //Couleur par défaut donnée pour une entité
				for (Chasseur h : this.chasseurs) { //On vérifie si l'entité n'est pas un chasseur, pour en modifier dans ce cas la couleur
					if (c.equals(h.getPos())) {
						Color color = h.getColor(); 
						cRep.setBackground(color);
						break;
					}
				}
			}
			
			/*if (c.equals(this.tresor.getPos())) {
				cRep.setBackground(Color.RED);
			}*/
			context.add(cRep);
		}
	}
	
	public void update(Container context) {
		context.removeAll();
		this.draw(context);
		context.revalidate();
		context.repaint();
	}
	
	public void deplacementChasseurs() {
		ArrayList<Chasseur> deplace = new ArrayList<Chasseur>();
		Chasseur c = this.chasseurs.poll();
		while (c != null) {
			Case dest = c.choixDeDeplacement(this);
			if (dest != null) {
				c.deplacement(dest);
			}
			deplace.add(c);
			c = this.chasseurs.poll();
		}
		
		//System.out.println("nombre de chasseurs déplacés : " + deplace.size());
		
		for (Chasseur cDeplace : deplace) {
			this.chasseurs.add(cDeplace);
		}
	}
}
