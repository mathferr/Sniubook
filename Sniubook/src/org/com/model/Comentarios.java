package org.com.model;

public class Comentarios {
	private String nomeAluno;
	private String comentario;
	private float ratingAluno;	
	
	public Comentarios(String nomeAluno, String comentario, float ratingAluno) {
		this.nomeAluno = nomeAluno;
		this.comentario = comentario;
		this.ratingAluno = ratingAluno;
	}

	public String getNomeAluno() {
		return nomeAluno;
	}
	
	public String getComentario() {
		return comentario;
	}
	
	public float getRatingAluno() {
		return ratingAluno;
	}
	
	
}
