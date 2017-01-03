package arbrelexicographique;

import java.util.Enumeration;

import javax.swing.JTree;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.*;

public aspect Visualisation {

	declare parents: ArbreLexicographique implements TreeModel;
	declare parents: NoeudAbstrait implements TreeNode;

	private DefaultTreeModel ArbreLexicographique.model;
	private DefaultMutableTreeNode NoeudAbstrait.treeNode;
	private JTree ArbreLexicographique.vue;

	private int debug = 0;

	public void ArbreLexicographique.setVue(JTree jt) {
		jt.setModel(this.model);
		this.vue = jt;
	}

	/** CREATION OBJETS **/
	pointcut creerArbreLexicographique(ArbreLexicographique a) : this(a) && execution(ArbreLexicographique.new());

	after(ArbreLexicographique a) : creerArbreLexicographique(a) {
		a.model = new DefaultTreeModel(new DefaultMutableTreeNode());
		if (debug == 1)
			System.out.println("new ArbreLexicographique");
	}

	pointcut creerMarque(NoeudAbstrait frere) : this(frere) && execution(Marque.new(NoeudAbstrait));

	after(NoeudAbstrait frere) : creerMarque(frere) {
		frere.treeNode = new DefaultMutableTreeNode();
		if (debug == 1)
			System.out.println("new Marque");
	}

	pointcut creerNoeud(Noeud n, NoeudAbstrait frere, NoeudAbstrait fils, char valeur) : this(n) && args(frere, fils, valeur) && execution(Noeud.new(NoeudAbstrait, NoeudAbstrait, char));

	after(Noeud n, NoeudAbstrait frere, NoeudAbstrait fils, char valeur) : creerNoeud(n, frere,  fils,  valeur) {
		n.treeNode = new DefaultMutableTreeNode(valeur);
		n.treeNode.add(fils.treeNode);
		if (debug == 1)
			System.out.println("new Noeud");
	}

	/** CHANGEMENT ENTREE **/
	pointcut changeEntree(ArbreLexicographique a) : target(a) && set(NoeudAbstrait ArbreLexicographique.entree);

	pointcut changeEntreeAjout(ArbreLexicographique a) : changeEntree(a) && withincode(boolean ArbreLexicographique.ajout(String));

	after(ArbreLexicographique a) : changeEntreeAjout(a) {
		// get root return object
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) a.model.getRoot();
		root.add(a.entree.treeNode);
		a.model.reload();
		if (debug == 1)
			System.out.println("changement entree ajout ArbreLexicographique");
	}

	pointcut changeEntreeSuppr(ArbreLexicographique a) : changeEntree(a) && withincode(boolean ArbreLexicographique.suppr(String));

	before(ArbreLexicographique a) : changeEntreeSuppr(a) {
		// get root return object
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) a.model.getRoot();
		root.remove(a.entree.treeNode);
		if (debug == 1)
			System.out.println("changement entree suppr ArbreLexicographique");
	}

	after(ArbreLexicographique a) : changeEntreeSuppr(a) {
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) a.model.getRoot();
		root.add(a.entree.treeNode);
		a.model.reload();
	}

	/** CHANGEMENT FRERE **/
	pointcut changeFrere(NoeudAbstrait na, NoeudAbstrait n) : this(na) && target(n) && set(NoeudAbstrait NoeudAbstrait.frere);

	pointcut changeFrereAjout(NoeudAbstrait na, NoeudAbstrait n) : changeFrere(na, n) && withincode(NoeudAbstrait NoeudAbstrait+.ajout(String));

	after(NoeudAbstrait na, NoeudAbstrait n) : changeFrereAjout(na, n) {
		// get parent return treeNode
		DefaultMutableTreeNode parent = (DefaultMutableTreeNode) na.treeNode.getParent();
		parent.add(n.frere.treeNode);
		if (debug == 1)
			System.out.println("changement frere ajout NoeudAbstrait");
	}

	pointcut changeFrereSuppr(NoeudAbstrait na, NoeudAbstrait n) : changeFrere(na, n) && withincode(NoeudAbstrait NoeudAbstrait+.suppr(String));

	before(NoeudAbstrait na, NoeudAbstrait n) : changeFrereSuppr(na, n) {
		// get parent return treeNode
		DefaultMutableTreeNode parent = (DefaultMutableTreeNode) na.treeNode.getParent();
		parent.remove(n.frere.treeNode);
		if (debug == 1)
			System.out.println("changement frere suppr NoeudAbstrait");
	}

	after(NoeudAbstrait na, NoeudAbstrait n) : changeFrereSuppr(na, n) {
		// get parent return treeNode
		DefaultMutableTreeNode parent = (DefaultMutableTreeNode) na.treeNode.getParent();
		if (n.frere.treeNode != null) {
			parent.add(n.frere.treeNode);
		}
		if (debug == 1)
			System.out.println("changement frere ajout NoeudAbstrait");
	}

	/** CHANGEMENT FILS **/
	pointcut changeFils(Noeud n) : target(n) && set(NoeudAbstrait Noeud.fils);

	pointcut changeFilsAjout(Noeud n) : changeFils(n) && withincode(NoeudAbstrait Noeud.ajout(String));

	after(Noeud n) : changeFilsAjout(n) {
		n.treeNode.add(n.fils.treeNode);
		if (debug == 1)
			System.out.println("changement fils ajout Noeud");
	}

	pointcut changeFilsSuppr(Noeud n) : changeFils(n) && withincode(NoeudAbstrait Noeud.suppr(String));

	before(Noeud n) : changeFilsSuppr(n) {
		// get parent return treeNode
		n.treeNode.remove(n.fils.treeNode);
		if (debug == 1)
			System.out.println("changement frere suppr NoeudAbstrait");
	}

	after(Noeud n) : changeFilsSuppr(n) {
		if (n.fils.treeNode != null) {
			n.treeNode.add(n.fils.treeNode);
		}
		if (debug == 1)
			System.out.println("changement fils ajout Noeud");
	}

	/** IMPLEMENTATIONS DES INTERFACES **/
	// ArbreLexico
	public void ArbreLexicographique.addTreeModelListener(TreeModelListener l) {
		this.model.addTreeModelListener(l);
	}

	public Object ArbreLexicographique.getChild(Object parent, int index) {
		return this.model.getChild(parent, index);
	}

	public int ArbreLexicographique.getChildCount(Object parent) {
		return this.model.getChildCount(parent);
	}

	public int ArbreLexicographique.getIndexOfChild(Object parent, Object child) {
		return this.model.getIndexOfChild(parent, child);
	}

	public Object ArbreLexicographique.getRoot() {
		return this.model.getRoot();
	}

	public boolean ArbreLexicographique.isLeaf(Object node) {
		return this.model.isLeaf(node);
	}

	public void ArbreLexicographique.removeTreeModelListener(TreeModelListener l) {
		this.model.removeTreeModelListener(l);
	}

	public void ArbreLexicographique.valueForPathChanged(TreePath path, Object newValue) {
		this.model.valueForPathChanged(path, newValue);
	}

	// NoeudAbstrait
	public Enumeration NoeudAbstrait.children() {
		return this.treeNode.children();
	}

	public boolean NoeudAbstrait.getAllowsChildren() {
		return this.treeNode.getAllowsChildren();
	}

	public TreeNode NoeudAbstrait.getChildAt(int childIndex) {
		return this.treeNode.getChildAt(childIndex);
	}

	public int NoeudAbstrait.getChildCount() {
		return this.treeNode.getChildCount();
	}

	public int NoeudAbstrait.getIndex(TreeNode node) {
		return this.treeNode.getIndex(node);
	}

	public TreeNode NoeudAbstrait.getParent() {
		return this.treeNode.getParent();
	}

	public boolean NoeudAbstrait.isLeaf() {
		return this.treeNode.isLeaf();
	}

	public void NoeudAbstrait.insert(MutableTreeNode arg0, int arg1) {
		this.treeNode.insert(arg0, arg1);
	}

	public void NoeudAbstrait.remove(int arg0) {
		this.treeNode.remove(arg0);

	}

	public void NoeudAbstrait.remove(MutableTreeNode arg0) {
		this.treeNode.remove(arg0);
	}

	public void NoeudAbstrait.removeFromParent() {
		this.removeFromParent();
	}

	public void NoeudAbstrait.setParent(MutableTreeNode arg0) {
		this.treeNode.setParent(arg0);
	}

	public void NoeudAbstrait.setUserObject(Object arg0) {
		this.treeNode.setUserObject(arg0);
	}
}
