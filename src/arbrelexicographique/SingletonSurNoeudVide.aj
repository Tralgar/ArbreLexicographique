package arbrelexicographique;

public aspect SingletonSurNoeudVide {
	private NoeudVide instance = new NoeudVide();

	private pointcut appelConstructeur() : call(NoeudVide.new()) && ! within(SingletonSurNoeudVide);
	
	NoeudVide around() : appelConstructeur() {
		return instance;
	}
}
