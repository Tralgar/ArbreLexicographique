package arbrelexicographique.v3;

public class ArbreLexicographique {
	private NoeudAbstrait entree;

	public ArbreLexicographique() {
		entree = NoeudVide.getInstance();
	}

	public String toString() {
		return entree.toString("");
	}

	public boolean contient(String s) {
		return entree.contient(s);
	}

	public boolean prefixe(String s) {
		return entree.prefixe(s);
	}

	public int nbMots() {
		return entree.nbMots();
	}

	public boolean ajout(String s) {
		try {
			entree = entree.ajout(s);
		} catch (ArbreLexicographiqueException ale) {
			return false;
		}
		return true;
	}

	public boolean suppr(String s) {
		try {
			entree = entree.suppr(s);
		} catch (ArbreLexicographiqueException ale) {
			return false;
		}
		return true;
	}
	
	public boolean estVide() {
		return entree instanceof NoeudVide;
	}

	public static void main(String[] args) {
		ArbreLexicographique arbre = new ArbreLexicographique();
		System.out.println(arbre.ajout("exemple"));
		arbre.ajout("personne");
		arbre.ajout("exo");
		System.out.println(arbre.ajout("exemple"));
		arbre.ajout("dernier");
		System.out.println(arbre);
		System.out.println(arbre.suppr("absent"));
		System.out.println(arbre.suppr("personne"));
		System.out.println(arbre.suppr("personne"));
		System.out.println(arbre);
		System.out.println(arbre.contient("mot"));
		System.out.println(arbre.contient("dernier"));
		System.out.println(arbre.prefixe("der"));
		System.out.println(arbre.prefixe("exa"));
		System.out.println(arbre.nbMots());
	}

}
