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
public class Curso {
	private String codigo;
    private String nome;
    private int duracao;
    
	public Curso(String codigo, String nome, int duracao) {
		this.codigo = codigo;
		this.nome = nome;
		this.duracao = duracao;
	}
	
	public String getCodigo() {
		return codigo;
	}

	public String getNome() {
		return nome;
	}

	public int getDuracao() {
		return duracao;
	}

}
