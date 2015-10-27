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
    private String turma;
    private String periodo;
    private String campus;
    private String turno;


    public Aluno(int registroAcademico, String nome, String cpf, String email, String campus, String turma, String periodo, String curso, String turno) {
		super(nome, cpf, email);
		this.registroAcademico = registroAcademico;
		this.curso = curso;
		this.turma = turma;
		this.periodo = periodo;
		this.campus = campus;
		this.turno = turno;
	}

	public int getRegistroAcademico() {
        return registroAcademico;
    }
    
    public String getCurso() {
		return curso;
    }
    
    public String getTurma() {
		return turma;
	}
    
    public String getPeriodo() {
		return periodo;
	}
    
    public String getCampus() {
		return campus;
	}
    
    public String getTurno(){
    	return turno;
    }
    
}
