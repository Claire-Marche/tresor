package claire_avenia;

public abstract class Entite {
	protected Case pos;
	
	public Entite(Case pos) {
		this.pos = pos;
	}
	
	public boolean entoure(Grille grille) {
		Case c;
		for (int i = -1; i < 2; i+=2) {
			for (int j = -1; j < 2; j+=2) {
				c = grille.getPos(this.pos.getX() + (j == -1 ? i : 0), this.pos.getY() + (j == 1 ? i : 0));
				if (c != null) {
					if (c.getDistance(grille.getTresor())  != -1) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public Case getPos() {
		return this.pos;
	}
}
