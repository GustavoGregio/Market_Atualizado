/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author gusta
 */
public class Comercio {
    private int id;
    private String nome;
    private int Cnpj;
    private Endereco endereco;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCnpj() {
        return Cnpj;
    }

    public void setCnpj(int Cnpj) {
        this.Cnpj = Cnpj;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Comercio() {
    }

    public Comercio(int id, String nome, int Cnpj, Endereco endereco) {
        this.id = id;
        this.nome = nome;
        this.Cnpj = Cnpj;
        this.endereco = endereco;
    }
    
    
    
    
}
