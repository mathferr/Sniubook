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
public class Periodo {
    private int codigo;
    private ArrayList<Disciplina> listaDisciplinas;
    

    public Periodo(int codigo, ArrayList<Disciplina> listaDisciplinas) {
        this.codigo = codigo;
        this.listaDisciplinas = listaDisciplinas;
    }

    public int getCodigo() {
        return codigo;
    }

    public ArrayList<Disciplina> getListaDisciplinas() {
        return listaDisciplinas;
    }
    
    
}
