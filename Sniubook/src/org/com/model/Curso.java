/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.com.model;

import java.util.ArrayList;

/**
 *
 * @author SAMSUNG
 */
public class Curso {
    private String nome;
    private int duracao;
    private String campus;
    private String turno;
    private ArrayList<Periodo> listaPeriodos;
    
	public Curso(String nome, int duracao, String campus, String turno, ArrayList<Periodo> listaPeriodos) {
		this.nome = nome;
		this.duracao = duracao;
		this.campus = campus;
		this.turno = turno;
		this.listaPeriodos = listaPeriodos;
	}

	public String getNome() {
		return nome;
	}

	public int getDuracao() {
		return duracao;
	}

	public String getCampus() {
		return campus;
	}

	public String getTurno() {
		return turno;
	}

	public ArrayList<Periodo> getListaPeriodos() {
		return listaPeriodos;
	}
	
}
