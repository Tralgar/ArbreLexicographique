package arbrelexicographique.v0;

public class Marque extends NoeudAbstrait {

	public Marque(NoeudAbstrait frere) {
		super(frere);
	}

	public boolean contient(String s) {
		return s.isEmpty() || (frere != null && frere.contient(s));
		//
		//
		// if (s.isEmpty())
		// return true;
		// if (frere == null)
		// return false;
		// return frere.contient(s);
	}

	public boolean prefixe(String s) {
		return s.isEmpty() || (frere != null && frere.prefixe(s));
	}

	public int nbMots() {
		return 1 + (frere != null ? frere.nbMots() : 0);
	}

	public NoeudAbstrait ajout(String s) {
		if (!s.isEmpty()) {
			if (frere == null) {
				NoeudAbstrait n = new Marque(null);
				for (int i = s.length() - 1; i >= 0; i--)
					n = new Noeud(null, n, s.charAt(i));
				frere = n;
			} else
				frere = frere.ajout(s);
		}
		return this;
	}

	public NoeudAbstrait suppr(String s) {
		if (s.isEmpty())
			return frere;
		if (frere == null)
			return this;
		frere = frere.suppr(s);
		return this;
	}

	public String toString() {
		return toString("");
	}
	
	public String toString(String s) {
		String res = s + "\n";
		if (frere == null)
			return res;
		return res + frere.toString(s);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
