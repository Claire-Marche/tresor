package claire_avenia;

import java.awt.Color;

public class Escroc extends Chasseur {
	private final static double PROBA_PIEGER = 0.2;
	protected static Color couleur = Color.MAGENTA;
	
	public Escroc(Case pos, Tresor objectif) {
		super(pos, Escroc.couleur, objectif);
	}
	
	public Case choixDeDeplacement(Grille grille) {
		Case dest = null;
		int dest_dist = -1;
		
		int initI = (Math.random() > .5 ? 1 : -1);
		int initJ = (Math.random() > .5 ? 1 : -1);
		
		for (int i = initI; (initI > 0 && i > -2) || (initI < 0 && i < 2); i += initI * -2) {
			for (int j = initJ; (initJ > 0 && j > -2) || (initJ < 0 && j < 2); j += initJ * -2) {
				boolean pieger = (Math.random() < Escroc.PROBA_PIEGER);
				Case c = grille.getPos(this.pos.getX() + (j == -1 ? i : 0), this.pos.getY() + (j == 1 ? i : 0));
				int dist = (c != null) ? c.getDistance(this.objectif) : -1;
				if (dist > 0 && c.isPiege()) {
					dist /= 2;
				}
				if (dist != -1 && (dest == null || dist < dest_dist)) {
					dest = c;
					dest_dist = dist;
				}
				if (dist > 0 && pieger) {
					//System.out.println("Case piégée");
					c.pieger();
				}
			}
		}
		return dest;
	}
}
