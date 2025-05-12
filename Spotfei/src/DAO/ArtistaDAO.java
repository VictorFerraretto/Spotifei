/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Model.Artista;

/**
 *
 * @author Gustavo
 */
public class ArtistaDAO {
    
    private Connection conn;

    public ArtistaDAO(Connection conn) {
        this.conn = conn;        
    }
    
    // consulta o artista pelo nome
    public ResultSet consultarArtistasPorNome(String nomeArtistico) 
                                                throws SQLException {
    String sql = "SELECT a.id, a.nome_artistico, a.genero, p.nome, "
                  + "p.email, p.telefone " +
                 "FROM Artista a " +
                 "JOIN Usuario u ON a.usuario_id = u.id " +
                 "JOIN Pessoa p ON u.pessoa_id = p.id " +
                 "WHERE a.nome_artistico = ?";
    
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setString(1, nomeArtistico);
    return stmt.executeQuery();
}
    public boolean inserirArtista(String nome, String nomeArtistico, 
            String email, String telefone, String genero) {
    String sqlPessoa = "INSERT INTO pessoa (nome, email, telefone) "
            + "VALUES (?, ?, ?) RETURNING id";
    String sqlArtista = "INSERT INTO artista (pessoa_id, "
            + "nome_artistico, genero) VALUES (?, ?, ?)";
    String sqlVerificaNomeArtistico = "SELECT id FROM artista WHERE nome_artistico = ?";

    try {
        // Verifica se o artista já existe no banco de dados
        PreparedStatement stmtVerifica = conn.prepareStatement(sqlVerificaNomeArtistico);
        stmtVerifica.setString(1, nomeArtistico);
        ResultSet rs = stmtVerifica.executeQuery();

        if (rs.next()) {
            return false; // Já existe um artista com esse nome artístico
        }

        // Desliga o autocommit para transação segura
        conn.setAutoCommit(false);

        // Inserindo na tabela pessoa
        PreparedStatement stmtPessoa = conn.prepareStatement(sqlPessoa);
        stmtPessoa.setString(1, nome);
        stmtPessoa.setString(2, email);
        stmtPessoa.setString(3, telefone);
        rs = stmtPessoa.executeQuery();

        if (!rs.next()) {
            conn.rollback();
            return false; // Falha ao inserir pessoa
        }

        int pessoaId = rs.getInt("id");

        // Inserindo na tabela artista
        PreparedStatement stmtArtista = conn.prepareStatement(sqlArtista);
        stmtArtista.setInt(1, pessoaId);
        stmtArtista.setString(2, nomeArtistico);
        stmtArtista.setString(3, genero);
        stmtArtista.executeUpdate();

        // Commit da transação
        conn.commit();
        return true;

    } catch (SQLException e) {
        e.printStackTrace();
        try {
            conn.rollback(); // desfaz qualquer mudança se algum erro acontecer
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    } finally {
        try {
            conn.setAutoCommit(true); // restaura autocommit
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
    
}
