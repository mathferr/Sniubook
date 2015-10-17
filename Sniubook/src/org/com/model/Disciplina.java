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
public class Disciplina {
    private int codigo;
    private String nome;
    private int cargaHoraria;
    private ArrayList<Professor> listaProfessores;

    public Disciplina(int codigo, String nome, int cargaHoraria, ArrayList<Professor> listaProfessores) {
        this.codigo = codigo;
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
        this.listaProfessores = listaProfessores;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public ArrayList<Professor> getListaProfessores() {
        return listaProfessores;
    }
    
    
    
}
