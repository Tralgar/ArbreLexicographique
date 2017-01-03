package arbrelexicographique.v0;

public abstract class NoeudAbstrait {
	protected NoeudAbstrait frere;

	public NoeudAbstrait(NoeudAbstrait frere) {
		this.frere = frere;
	}

	public abstract boolean contient(String s);

	public abstract boolean prefixe(String s);

	public abstract int nbMots(); // nombre d'éléments

	public abstract NoeudAbstrait ajout(String s);

	public abstract NoeudAbstrait suppr(String s);

	public abstract String toString(); // éléments séparés par \n
	public abstract String toString(String s); // éléments séparés par \n

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		NoeudAbstrait entree = new Marque(null);
		entree = entree.ajout("exemple");
		entree = entree.suppr("");
		
		entree = entree.ajout("personne");
		entree = entree.ajout("exo");
		entree = entree.ajout("exemple");
		entree = entree.ajout("dernier");
		System.out.println(entree);
		entree = entree.suppr("absent");
		entree = entree.suppr("personne");
		entree = entree.suppr("personne");
		System.out.println(entree);
		System.out.println(entree.contient("mot"));
		System.out.println(entree.contient("dernier"));
		System.out.println(entree.prefixe("der"));
		System.out.println(entree.prefixe("exa"));
		System.out.println(entree.nbMots());
	}

}
