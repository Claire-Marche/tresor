package claire_avenia;

import java.awt.Color;

public abstract class Personnage extends Entite{
	protected Color color;
	
	public Personnage(Case pos, Color color) {
		super(pos);
		pos.inverserEtat();
		this.color = color;
	}
	
	public abstract Case choixDeDeplacement(Grille grille);
	
	public void deplacement(Case pos) {
		this.pos.inverserEtat();
		this.pos = pos;
		this.pos.inverserEtat();
	}
	
	public Color getColor() {
		return this.color;
	}
}
