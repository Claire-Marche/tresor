package claire_avenia;

public class Pierre extends Entite {
	public Pierre(Case pos) {
		super(pos);
		pos.inverserEtat();
	}
}
