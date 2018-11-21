package claire_avenia;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Jeu extends JFrame{

	final static int COTE_CASE = 30;
	
	private Joueur j;
	private Grille grille;
	private boolean enJeu;
	
	public Jeu(boolean map, int largeur, int longueur, int nb_pierres, int nb_chasseurs, int nb_escrocs, int nb_furtifs) {
		super();
		this.setSize(largeur * COTE_CASE, longueur * COTE_CASE);
		this.setResizable(false);
		this.init(map, nb_pierres, nb_chasseurs, nb_escrocs, nb_furtifs, largeur, longueur);
		this.setVisible(true);
	}
	
	public void init(boolean map, int nb_pierres, int nb_chasseurs, int nb_escrocs, int nb_furtifs, int largeur, int longueur) {
		Container ctx = this.getContentPane();
		this.enJeu = true;
		this.j = new Joueur("nom", nb_pierres);
		this.grille = new Grille(longueur, largeur, nb_chasseurs, nb_escrocs, nb_furtifs, map);
		ctx.removeAll();
		ctx.setLayout(new GridLayout(longueur, largeur));
		this.grille.draw(ctx);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ctx.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (enJeu) {
					int width = ctx.getWidth();
					int height = ctx.getHeight();
					int diff_x = (largeur * COTE_CASE - width) / 2; //Détermine la différence de position entre la position absolue dans la JFrame, et le Container (le contexte).
					int diff_y = (longueur * COTE_CASE - height) / 2;
					int x = (e.getX() - diff_y) / (width / largeur) ;
					int y = (e.getY() - diff_x) / (height / longueur);
					Case c = grille.getPos(x, y);
					if (c.isLibre()) {
						j.poserPierre(c);
						tour(map, largeur, longueur, nb_pierres, nb_chasseurs, nb_escrocs, nb_furtifs, ctx);
					}
				}
			}
		});
		ctx.revalidate();
		ctx.repaint();
	}
	
	public void tour(boolean map, int largeur, int longueur, int nb_pierres, int nb_chasseurs, int nb_escrocs, int nb_furtifs, Container ctx) {
		grille.deplacementChasseurs();
		grille.update(ctx);
		if (this.victoireChasseurs()) {
			this.enJeu = false;
			this.ecranFin(map, largeur, longueur, nb_pierres, nb_chasseurs, nb_escrocs, nb_furtifs, false);
		} else if (this.victoireJoueur()) {
			this.enJeu = false;
			this.ecranFin(map, largeur, longueur, nb_pierres, nb_chasseurs, nb_escrocs, nb_furtifs, true);
		}
	}
	
	public boolean victoireChasseurs() {
		for (Chasseur c : this.grille.getChasseurs()) {
			if (c.pos.getDistance(this.grille.getTresor()) == 0) {
				return true;
			}
		}
		return false;
	}
	
	public boolean victoireJoueur() {
		if (this.grille.getTresor().entoure(this.grille)) {
			return true;
		}
		
		for (Chasseur c : this.grille.getChasseurs()) {
			if (!c.entoure(this.grille)) {
				return false;
			}
		}
		
		return true;
	}
	
	public void ecranFin(boolean map, int largeur, int longueur, int nb_pierres, int nb_chasseurs, int nb_escrocs, int nb_furtifs, boolean victoireJoueur) {
		Container ctx = this.getContentPane();
		ctx.setLayout(new BorderLayout());
		ctx.removeAll();
		ctx.add(new JLabel((victoireJoueur ? "Félicitations, vous avez gagné" : "Vous avez perdu..."), JLabel.CENTER), BorderLayout.CENTER);
		JButton rejouer = new JButton("Rejouer");
		rejouer.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				init(map, nb_pierres, nb_chasseurs, nb_escrocs, nb_furtifs, largeur, longueur);
			}
		});
		ctx.add(rejouer, BorderLayout.SOUTH);
		ctx.revalidate();
		ctx.repaint();
	}
}
