package claire_avenia;

import java.lang.Comparable;
import javax.swing.*;
import java.awt.*;

public class Chasseur extends Personnage implements Comparable<Chasseur> {
	protected Tresor objectif;
	protected static Color couleur = Color.GREEN;
	
	public Chasseur(Case pos, Color color, Tresor objectif) {
		super(pos, color);
		this.objectif = objectif;
	}
	
	public Chasseur(Case pos, Tresor objectif) {
		super(pos, Chasseur.couleur);
		this.objectif = objectif;
	}
	
	public int compareTo(Chasseur that) {
		return that.getPos().getDistance(this.objectif) - this.pos.getDistance(this.objectif);
	}
	
	public Case choixDeDeplacement(Grille grille) {
		Case dest = null;
		int dest_dist = -1;
		
		int initI = (Math.random() > .5 ? 1 : -1);
		int initJ = (Math.random() > .5 ? 1 : -1);
		
		for (int i = initI; (initI > 0 && i > -2) || (initI < 0 && i < 2); i += initI * -2) {
			for (int j = initJ; (initJ > 0 && j > -2) || (initJ < 0 && j < 2); j += initJ * -2) {
				Case c = grille.getPos(this.pos.getX() + (j == -1 ? i : 0), this.pos.getY() + (j == 1 ? i : 0));
				int dist = (c != null) ? c.getDistance(this.objectif) : -1;
				if (dist != -1 && (dest == null || dist < dest_dist)) {
					dest = c;
					dest_dist = dist;
				}
			}
		}
	
		return dest;
	}
}
