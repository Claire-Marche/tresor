package claire_avenia;

import javax.swing.*;
import java.awt.*;

public class Case {
	private int x, y;
	private boolean libre;
	private boolean piege;
	
	public Case(int x, int y) {
		this.x = x;
		this.y = y;
		this.libre = true;
	}
	
	public int getDistance(Tresor tresor) {
		if (!this.libre && !this.equals(tresor.getPos())) {
			return -1; //par convention l'infini est représenté par -1
		}
		
		return (Math.abs(this.x - tresor.getPos().getX()) + Math.abs(this.y - tresor.getPos().getY())) * (this.piege ? 2 : 1);
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public boolean isLibre() {
		return this.libre;
	}
	
	public boolean isPiege() {
		return this.piege;
	}
	
	public void inverserEtat() {
		this.libre = !this.libre;
	}
	
	public void pieger() {
		this.piege = true;
	}
	
	public boolean equals(Case other) {
		return this.x == other.x && this.y == other.y;
	}
}
