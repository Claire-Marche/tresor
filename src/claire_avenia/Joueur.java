package claire_avenia;

import java.util.ArrayList;

public class Joueur {
	private String nom;
	private int nb_pierres;
	private int score;
	private ArrayList<Pierre> pierres;
	
	public Joueur(String nom, int nb_pierres) {
		this.nom = nom;
		this.nb_pierres = nb_pierres;
		this.score = 0;
		this.pierres = new ArrayList<Pierre>(nb_pierres);
	}
	
	public void poserPierre(Case pos) {
		if (this.nb_pierres == 0) {
			this.pierres.get(0).pos.inverserEtat();
			this.pierres.remove(0);
			this.nb_pierres++;
		}
		this.pierres.add(new Pierre(pos));
		this.nb_pierres--;
	}
}
