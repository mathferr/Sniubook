/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.com.model;

/**
 *
 * @author SAMSUNG
 */
public class Aluno extends Pessoa {
    private int registroAcademico;
    private String curso;
    private String periodo;
    private String campus;

    public Aluno(String nome, String cpf, String email, int registroAcademico, String curso, String periodo, String campus) {
		super(nome, cpf, email);
		this.registroAcademico = registroAcademico;
		this.curso = curso;
		this.periodo = periodo;
		this.campus = campus;
	}

	public int getRegistroAcademico() {
        return registroAcademico;
    }
    
    public String getCurso() {
		return curso;
	}
    
    public String getCampus() {
		return campus;
	}
    
    public String getPeriodo() {
		return periodo;
	}
}
