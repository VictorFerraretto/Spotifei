/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Model.Usuario;
/**
 *
 * @author Victor
 */
public class UsuarioDAO {
    private Connection conn;

    public UsuarioDAO(Connection conn) {
        this.conn = conn;        
    }

    public ResultSet consultarPorUsernameESenha(String username, String senha) throws SQLException {
        String sql = "SELECT u.id, u.username, u.senha, p.nome, p.email, p.telefone, u.tipo_usuario "
                   + "FROM Usuario u "
                   + "JOIN Pessoa p ON u.pessoa_id = p.id "
                   + "WHERE u.username = ? AND u.senha = ?";
        
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, username);
        stmt.setString(2, senha);
        return stmt.executeQuery();
    }

    
    public void inserir(Usuario usuario) throws SQLException{
        String sql = "insert into Usuario (nome, usuario, senha) values ('"
                      + usuario.getNome()    + "', '"
                      + usuario.getUsername() + "', '"
                      + usuario.getSenha()   + "')";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.execute();
        conn.close();
    }
    

}
