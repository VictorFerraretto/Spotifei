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
    
}
