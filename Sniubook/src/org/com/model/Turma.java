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
public class Turma {
    private int codigo;
    private ArrayList<Aluno> listaAlunos;

    public Turma(int codigo, ArrayList<Aluno> listaAlunos) {
        this.codigo = codigo;
        this.listaAlunos = listaAlunos;
    }

    public int getCodigo() {
        return codigo;
    }

    public ArrayList<Aluno> getListaAlunos() {
        return listaAlunos;
    }
    
    
}
