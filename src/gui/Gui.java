package gui;

import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JTree;
import java.awt.BorderLayout;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import arbrelexicographique.ArbreLexicographique;

import javax.swing.JMenu;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Enumeration;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTabbedPane;
import javax.swing.JLayeredPane;
import java.awt.Color;
import javax.swing.JList;
import javax.swing.JTextArea;

public class Gui {

	private JFrame frame;
	private JTextField txt_word;
	private JTextArea txa_list;
	private ArbreLexicographique arbre;
	private JLabel lbl_nbMots;
	private JTree tree;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui window = new Gui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Gui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		arbre = new ArbreLexicographique();

		frame = new JFrame();
		frame.setBounds(100, 100, 600, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnFichier = new JMenu("Fichier");
		menuBar.add(mnFichier);

		JMenuItem mntmCharger = new JMenuItem("Charger");
		mntmCharger.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser(new File("."));
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					String nomFichier = chooser.getSelectedFile().getName();
					Gui.this.arbre.charge(nomFichier);
					updateGui();
				}
			}
		});
		mnFichier.add(mntmCharger);

		JMenuItem mntmSauvegarder = new JMenuItem("Sauvegarder");
		mntmSauvegarder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser(new File("."));
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichiers texte", "txt", "text");
				chooser.setFileFilter(filter);
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					String nomFichier = chooser.getSelectedFile().getName();
					Gui.this.arbre.sauve(nomFichier);
				}
			}
		});
		mnFichier.add(mntmSauvegarder);

		JMenuItem mntmQuitter = new JMenuItem("Quitter");
		mntmQuitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		mnFichier.add(mntmQuitter);

		JMenu mnAide = new JMenu("Aide");
		menuBar.add(mnAide);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton btn_add = new JButton("Ajouter");
		btn_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String txt = Gui.this.txt_word.getText();
				if (txt.length() >= 2 && Pattern.matches("[a-zA-Z]+", txt)) {
					if (Gui.this.arbre.ajout(txt)) {
						updateGui();
						ouvrirChemin(txt);
						System.out.println("Ajout : le mot '" + txt + "' a bien été ajouté !");
					} else {
						if (Gui.this.arbre.contient(txt)) {
							System.err.println("ERROR : le mot " + txt + " est déjà présent dans l'arbre!");
						} else {
							System.err.println("ERROR : Ajout du mot '" + txt + "' impossible...");
						}
					}
				} else {
					System.err.println("ERROR : le mot doit contenir au moins 2 caractères et seulement des lettres !");
				}
			}
		});
		panel.add(btn_add);

		JButton btn_delete = new JButton("Supprimer");
		btn_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String txt = Gui.this.txt_word.getText();
				if (txt.length() >= 2 && Pattern.matches("[a-zA-Z]+", txt)) {
					if (Gui.this.arbre.suppr(txt)) {
						updateGui();
						System.out.println("Suppression : le mot '" + txt + "' a bien été supprimé !");
					} else {
						if (!Gui.this.arbre.contient(txt)) {
							System.err.println("ERROR : le mot " + txt + " n'existe pas dans l'arbre !");
						} else {
							System.err.println("ERROR : Suppression du mot '" + txt + "' impossible...");
						}
					}
				} else {
					System.err.println("ERROR : le mot doit contenir au moins 2 caractères et seulement des lettres !");
				}
			}
		});
		panel.add(btn_delete);

		JButton btn_search = new JButton("Chercher");
		btn_search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String txt = Gui.this.txt_word.getText();
				if (txt.length() >= 2 && Pattern.matches("[a-zA-Z]+", txt)) {
					if (Gui.this.arbre.contient(txt)) {
						ouvrirChemin(txt);
						System.out.println("Contient : " + txt + " trouvé !");
					} else {
						System.out.println("Contient : " + txt + " non trouvé...");
					}
				} else {
					System.err.println("ERROR : le mot doit contenir au moins 2 caractères et seulement des lettres !");
				}
			}
		});
		panel.add(btn_search);

		JButton btn_prefixe = new JButton("Prefixe");
		btn_prefixe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String txt = Gui.this.txt_word.getText();
				if (txt.length() >= 2 && Pattern.matches("[a-zA-Z]+", txt)) {
					if (Gui.this.arbre.prefixe(txt)) {
						ouvrirChemin(txt);
						System.out.println("Préfixe : " + txt + " trouvé !");
					} else {
						System.out.println("Préfixe : " + txt + " non trouvé...");
					}
				} else {
					System.err.println("ERROR : le mot doit contenir au moins 2 caractères et seulement des lettres !");
				}
			}
		});
		panel.add(btn_prefixe);

		JLabel lbl_what = new JLabel("Quoi ?");
		panel.add(lbl_what);

		txt_word = new JTextField();
		txt_word.setText("mot");
		panel.add(txt_word);
		txt_word.setColumns(10);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		JLayeredPane lyp_arbre = new JLayeredPane();
		lyp_arbre.setForeground(Color.BLACK);
		tabbedPane.addTab("Arbre", null, lyp_arbre, null);
		tree = new JTree();
		arbre.setVue(tree);
		tree.setBounds(0, 0, 600, 400);
		JScrollPane jsp_tree = new JScrollPane();
		jsp_tree.setBounds(0, 0, 600, 400);
		jsp_tree.setViewportView(tree);
		lyp_arbre.add(jsp_tree);

		JLayeredPane lyp_liste = new JLayeredPane();
		tabbedPane.addTab("Liste", null, lyp_liste, null);
		txa_list = new JTextArea();
		txa_list.setBounds(0, 0, 600, 400);
		JScrollPane jsp_liste = new JScrollPane();
		jsp_liste.setBounds(0, 0, 600, 400);
		jsp_liste.setViewportView(txa_list);
		lyp_liste.add(jsp_liste);

		lbl_nbMots = new JLabel("Aucun mot");
		frame.getContentPane().add(lbl_nbMots, BorderLayout.SOUTH);
		lbl_nbMots.setForeground(Color.RED);
	}

	private void updateGui() {
		txa_list.setText(arbre.toString());
		lbl_nbMots.setText(arbre.nbMots() + " mots");
	}

	private void ouvrirChemin(String txt) {
		TreeNode treeNode = (TreeNode) this.tree.getModel().getRoot();
		DefaultMutableTreeNode lastNode = (DefaultMutableTreeNode) parcoursNoeud(treeNode, txt);
		tree.expandPath(new TreePath(lastNode.getPath()));
	}

	private TreeNode parcoursNoeud(TreeNode treeNode, String txt) {
		if (!treeNode.isLeaf()) {
			for (int i = 0; i < treeNode.getChildCount(); i++) {
				if (treeNode.getChildAt(i).toString().equals(txt.substring(0, 1))) {
					if (txt.length() > 1) {
						return parcoursNoeud(treeNode.getChildAt(i), txt.substring(1));
					}

					return treeNode.getChildAt(i);
				}
			}
		}

		return treeNode;
	}
}
