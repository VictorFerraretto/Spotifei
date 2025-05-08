/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.time.LocalDate;

/**
 *
 * @author Gustavo
 */
public class Musica {
    private int id;
    private String titulo;
    private int artistaId; 
    private String genero;
    private LocalDate lancamento; 
    
    // construtor com o id, para depois usar com excluir e buscar musicas
    public Musica(int id, String titulo, int artistaId, String genero, 
                    LocalDate lancamento) {
        this.id = id;
        this.titulo = titulo;
        this.artistaId = artistaId;
        this.genero = genero;
        this.lancamento = lancamento;
    }
    
    // construtor sem id, para adicionar a 
    //musica no bando de dados, pois o banco 
    //ja adiciona automadicamente
    public Musica(String titulo, int artistaId, String genero, 
                    LocalDate lancamento) {
        this.titulo = titulo;
        this.artistaId = artistaId;
        this.genero = genero;
        this.lancamento = lancamento;
    }

    // Getters e setters
    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getArtistaId() {
        return artistaId;
    }

    public String getGenero() {
        return genero;
    }

    public LocalDate getLancamento() {
        return lancamento;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setArtistaId(int artistaId) {
        this.artistaId = artistaId;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setLancamento(LocalDate lancamento) {
        this.lancamento = lancamento;
    }
}
