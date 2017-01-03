package arbrelexicographique.v3;

public class NoeudVide extends NoeudAbstrait {
	private static NoeudVide instance = new NoeudVide();
	
	private NoeudVide() {
		super(null);
	}
	
	public static NoeudVide getInstance() {
		return instance;
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

	public NoeudAbstrait ajout(String s) throws ArbreLexicographiqueException {
		NoeudAbstrait n = new Marque(this);
		for (int i = s.length() - 1; i >= 0; i--)
			n = new Noeud(this, n, s.charAt(i));
		return n;
	}

	public NoeudAbstrait suppr(String s) throws ArbreLexicographiqueException {
		 throw new ArbreLexicographiqueException("Suppression impossible");
	}

	public String toString(String s) {
		return "";
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
