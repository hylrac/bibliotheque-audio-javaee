package fr.sopra.pox3.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name= "maisons_de_disque")
public class MaisonDeDisque {
	
	@Id
	@GeneratedValue ( strategy = GenerationType.TABLE)
	private int id;

	@OneToMany (mappedBy = "maison", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	private List<Auteur> auteurs = new ArrayList<>();
	
	private String nom;

	

	public List<Auteur> getAuteurs() {
		return auteurs;
	}

	public void addAuteur ( Auteur auteur) {
		auteurs.add(auteur);
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	
	
}
