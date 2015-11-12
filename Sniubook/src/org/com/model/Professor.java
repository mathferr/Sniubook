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
public class Professor extends Pessoa {
    private int registroProfissional;
    private float rate;

    public Professor(int registroProfissional, String nome, String cpf, String email, float rate) {
        super(nome, cpf, email);
        this.registroProfissional = registroProfissional;
        this.rate = rate;
    }

    public int getRegistroProfissional() {
        return registroProfissional;
    }
    
    public float getRate() {
		return rate;
	}
    
}
