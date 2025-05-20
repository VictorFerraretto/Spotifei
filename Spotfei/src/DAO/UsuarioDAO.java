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
    //conexao com o banco de dados
    private Connection conn;

    public UsuarioDAO(Connection conn) {
        this.conn = conn;        
    }
    // consultar no banco se tem o usuario
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
    // inserindo a pessoa na tabela do banco de dados
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
    
    // inserindo o usuário na tabela do banco de dados
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
    // metodo para listar todos os usuarios
    public ResultSet listaDeNomesDosUsuarios() throws SQLException{
        String sql = "SELECT p.nome FROM Usuario u " +
                "JOIN Pessoa p ON u.pessoa_id = p.id";
        PreparedStatement stmt = conn.prepareStatement(sql);
        return stmt.executeQuery();
    }
    
    //metodo para buscar as informações dos usuarios
    public ResultSet buscarNomes(String username) throws SQLException{
        String sql = "SELECT u.username, u.senha, u.tipo_usuario, p.nome, p.email, p.telefone " +
                 "FROM Usuario u " +
                 "JOIN Pessoa p ON u.pessoa_id = p.id " +
                 "WHERE p.nome ILIKE ?";
    
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setString(1, "%" + username + "%"); // busca parcial
    return stmt.executeQuery();
    }
    
    // metodo simples so para contar os usuarios
    public int contarUsuarios() {
    String sql = "SELECT COUNT(id) AS total FROM usuario";
    try (PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
            return rs.getInt("total");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return 0;
}
    
    

}
