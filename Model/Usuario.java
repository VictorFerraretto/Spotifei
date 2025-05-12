/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Victor
 */
public class Usuario extends Pessoa {
    private String username, senha, tipoUsuario;

    public Usuario() {
    }

    public Usuario(String username, String senha, String tipoUsuario, 
                   int id, String nome, String email, String telefone) {
        super(id, nome, email, telefone);
        this.username = username;
        this.senha = senha;
        this.tipoUsuario = tipoUsuario;
    }
        
      
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }
    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
   
    
    
}
