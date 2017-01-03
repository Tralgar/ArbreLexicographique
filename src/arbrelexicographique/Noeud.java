package arbrelexicographique;

public class Noeud extends NoeudAbstrait {
	public NoeudAbstrait fils;
	private char valeur;

	public Noeud(NoeudAbstrait frere, NoeudAbstrait fils, char valeur) {
		super(frere);
		if (fils == null)
			throw new IllegalArgumentException("Je ne veux pas d'un fils null !");
		if (frere == null)
			throw new IllegalArgumentException("Je ne veux pas d'un frere null !");

		this.fils = fils;
		this.valeur = valeur;
	}

	public boolean contient(String s) {
		if (s.isEmpty())
			return false;
		char c = s.charAt(0);
		if (c < valeur)
			return false;
		if (c == valeur)
			return fils.contient(s.substring(1));
		// c > valeur
		return frere.contient(s);
	}

	public boolean prefixe(String s) {
		if (s.isEmpty())
			return true;
		char c = s.charAt(0);
		if (c < valeur)
			return false;
		if (c == valeur)
			return fils.prefixe(s.substring(1));
		// c > valeur
		return frere.prefixe(s);
	}

	public int nbMots() {
		return fils.nbMots() + frere.nbMots();
	}

	public NoeudAbstrait ajout(String s) throws ArbreLexicographiqueException {
		NoeudAbstrait n;
		if (s.isEmpty()) {
			n = new Marque(this);
			return n;
		}
		char c = s.charAt(0);
		if (c < valeur) {
			n = new NoeudVide().ajout(s);
			n.frere = this;
			return n;
		}
		if (c == valeur) {
			fils = fils.ajout(s.substring(1));
			return this;
		}
		// c > valeur
		frere = frere.ajout(s);
		return this;
	}

	public NoeudAbstrait suppr(String s) throws ArbreLexicographiqueException {
		NoeudAbstrait n;
		if (s.isEmpty()) 
				 throw new ArbreLexicographiqueException("Suppression impossible");
		char c = s.charAt(0);
		if (c < valeur) {
			 throw new ArbreLexicographiqueException("Suppression impossible");
		}
		if (c == valeur) {
			fils = fils.suppr(s.substring(1));
			if (fils instanceof NoeudVide)
				return frere;
			return this;
		}
		// c > valeur
		frere = frere.suppr(s);
		return this;
	}

	public String toString(String s) {
		return fils.toString(s + valeur) + frere.toString(s);
	}

}
