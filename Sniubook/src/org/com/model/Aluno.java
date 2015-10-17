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

    public Aluno(int registroAcademico, String nome, int cpf, String email) {
        super(nome, cpf, email);
        this.registroAcademico = registroAcademico;
    }

    public int getRegistroAcademico() {
        return registroAcademico;
    }
    
    
}
