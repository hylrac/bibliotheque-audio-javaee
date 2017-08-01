package fr.sopra.pox3.ejb;

import fr.sopra.pox3.entities.MaisonDeDisque;

public class AuteurDTO {

	int id;
	String nom;
	MaisonDeDisque maisonDeDisque;
	
	public MaisonDeDisque getMaisonDeDisque() {
		return maisonDeDisque;
	}
	public void setMaisonDeDisque(MaisonDeDisque maisonDeDisque) {
		this.maisonDeDisque = maisonDeDisque;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	
	
	
}
