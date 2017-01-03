package arbrelexicographique.v1;

public class Marque extends NoeudAbstrait {

	public Marque(NoeudAbstrait frere) {
		super(frere);
		if (frere == null)
			throw new IllegalArgumentException("Je ne veux pas d'un frere null !");
	}

	public boolean contient(String s) {
		return s.isEmpty() || frere.contient(s);
		//
		//
		// if (s.isEmpty())
		// return true;
		// if (frere == null)
		// return false;
		// return frere.contient(s);
	}

	public boolean prefixe(String s) {
		return s.isEmpty() || frere.prefixe(s);
	}

	public int nbMots() {
		return 1 + frere.nbMots();
	}

	public NoeudAbstrait ajout(String s) {
		if (!s.isEmpty())
			frere = frere.ajout(s);
		return this;
	}

	public NoeudAbstrait suppr(String s) {
		if (s.isEmpty())
			return frere;
		frere = frere.suppr(s);
		return this;
	}

	public String toString(String s) {
		String res = s + "\n";
		return res + frere.toString(s);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
