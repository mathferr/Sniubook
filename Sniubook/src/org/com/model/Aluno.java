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
    private String senha;


    public Aluno(int registroAcademico, String nome, String cpf, String email, String campus, String turma, String periodo, String curso, String turno, String senha) {
		super(nome, cpf, email);
		this.registroAcademico = registroAcademico;
		this.curso = curso;
		this.turma = turma;
		this.periodo = periodo;
		this.campus = campus;
		this.turno = turno;
		this.senha = senha;
	}
    
    public Aluno(String nome, String cpf, String email, String campus, String curso, String turno, String senha) {
		super(nome, cpf, email);
		this.curso = curso;
		this.campus = campus;
		this.turno = turno;
		this.senha = senha;
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
    
    public String getSenha() {
		return senha;
	}

	public void setRegistroAcademico(int registroAcademico) {
		this.registroAcademico = registroAcademico;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public void setTurma(String turma) {
		this.turma = turma;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public void setCampus(String campus) {
		this.campus = campus;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
    
}
