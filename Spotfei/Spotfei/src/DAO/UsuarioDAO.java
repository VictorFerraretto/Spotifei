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

    
    public void inserirUsuarioePessoa(Usuario usuario) throws SQLException {
    // Inserindo a pessoa na tabela do banco de dados
    String sqlPessoa = "INSERT INTO pessoa (nome, email, telefone) "
                        + "VALUES (?, ?, ?) RETURNING id";
    PreparedStatement psPessoa = conn.prepareStatement(sqlPessoa);
    psPessoa.setString(1, usuario.getNome());
    psPessoa.setString(2, usuario.getEmail());
    psPessoa.setString(3, usuario.getTelefone());

    ResultSet rs = psPessoa.executeQuery();
    int pessoaId = 0;
    if (rs.next()) {
        pessoaId = rs.getInt("id");
    }
    rs.close();
    psPessoa.close();
    
    // Inserindo o usuário na tabela do banco de dados
    String sqlUsuario = "INSERT INTO usuario (pessoa_id, username, senha, "
                        + "tipo_usuario) VALUES (?, ?, ?, ?)";
    PreparedStatement psUsuario = conn.prepareStatement(sqlUsuario);
    psUsuario.setInt(1, pessoaId);
    psUsuario.setString(2, usuario.getUsername());
    psUsuario.setString(3, usuario.getSenha()); 
    psUsuario.setString(4, usuario.getTipoUsuario()); 

    psUsuario.executeUpdate();
    psUsuario.close();

    conn.close();
}
    

}
