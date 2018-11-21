package claire_avenia;

import java.awt.Color;

public class Furtif extends Chasseur {
	private final static double PROBA_INVISIBLE = 0.5;
	protected static Color couleur = Color.BLACK;
	private boolean invisible;
	
	public Furtif(Case pos, Tresor objectif) {
		super(pos, Furtif.couleur, objectif);
		this.invisible = false;
		
	}
	
	public Case choixDeDeplacement(Grille grille) {
		if (!this.invisible && Math.random() > Furtif.PROBA_INVISIBLE) {
			this.invisible = true;
		} else {
			this.invisible = false;
		}
	
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
	
	public Color getColor() {
		if (this.invisible) {
			return null;
		}
		return this.color;
	}
}
