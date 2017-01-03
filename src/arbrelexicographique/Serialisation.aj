package arbrelexicographique;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

public aspect Serialisation {
	declare parents: ArbreLexicographique implements Serializable;

	public void ArbreLexicographique.sauve(String nomFichier) {
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(new File(nomFichier)));
			bw.write(this.toString());
			bw.close();
		} catch (IOException e) {
			System.out.println("Erreur lors de l'ouverture du fichier pour la sauvegarde de l'arbre");
			e.printStackTrace();
		}
	}

	public void ArbreLexicographique.charge(String nomFichier) {
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(new File(nomFichier)));
			String mot = "";
			while ((mot = br.readLine()) != null) {
				this.ajout(mot);
				System.out.println("CHARGE : adding " + mot);
			}
			br.close();
		} catch (IOException e) {
			System.out.println("Erreur lors de l'ouverture du fichier pour le chargement de l'arbre");
			e.printStackTrace();
		}
	}
}
