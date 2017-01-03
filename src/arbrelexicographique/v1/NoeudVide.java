package arbrelexicographique.v1;

public class NoeudVide extends NoeudAbstrait {

	public NoeudVide() {
		super(null);
	}

	public boolean contient(String s) {
		return false;
	}

	public boolean prefixe(String s) {
		return false;
	}

	public int nbMots() {
		return 0;
	}

	public NoeudAbstrait ajout(String s) {
		NoeudAbstrait n = new Marque(this);
		for (int i = s.length() - 1; i >= 0; i--)
			n = new Noeud(new NoeudVide(), n, s.charAt(i));
		return n;
	}

	public NoeudAbstrait suppr(String s) {
		return this;
	}

	public String toString(String s) {
		return "";
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
