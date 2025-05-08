/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Gustavo
 */
public class Artista extends Pessoa{
    private int id_artista;
    private String nome_artistico;
    private String genero;

    public Artista(int id_artista, String nome_artistico, String genero, int id, 
                    String nome, String email, String telefone) {
        super(id, nome, email, telefone);
        this.id_artista = id_artista;
        this.nome_artistico = nome_artistico;
        this.genero = genero;
    }

    public int getId_artista() {
        return id_artista;
    }

    public void setId_artista(int id_artista) {
        this.id_artista = id_artista;
    }

    public String getNome_artistico() {
        return nome_artistico;
    }

    public void setNome_artistico(String nome_artistico) {
        this.nome_artistico = nome_artistico;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
    
    

    
    
    
    
}
