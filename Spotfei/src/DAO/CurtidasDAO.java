/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Musica;
import java.util.List; 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Gustavo
 */
public class CurtidasDAO {
    
    private Connection conn;

    public CurtidasDAO(Connection conn) {
        this.conn = conn;        
    }
    
    // importei o List para me ajudar aqui
    public List<String> Curtidas() {
    String sql = "SELECT m.titulo, COUNT(c.musica_id) AS curtidas " +
                 "FROM curtidas c " +
                 "JOIN musica m ON c.musica_id = m.id " +
                 "GROUP BY m.titulo " +
                 "ORDER BY curtidas DESC " +
                 "LIMIT 5";
    List<String> resultado = new ArrayList<>();

    try (PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            String linha = "Música: " + rs.getString("titulo") +
                           " Curtidas: " + rs.getInt("curtidas");
            resultado.add(linha);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return resultado;
    }
    
    public boolean Curtir(int idUsuario, String nomeMusica) throws SQLException {
    String sqlBusca = "SELECT id FROM musica WHERE titulo = ?";
    String sqlLike = "INSERT INTO Curtidas (usuario_id, musica_id) VALUES (?, ?)";

    try (PreparedStatement stmtBusca = conn.prepareStatement(sqlBusca)) {
        stmtBusca.setString(1, nomeMusica);
        ResultSet rs = stmtBusca.executeQuery();

        if (rs.next()) {
            int idMusica = rs.getInt("id");

            try (PreparedStatement stmtLike = conn.prepareStatement(sqlLike)) {
                stmtLike.setInt(1, idUsuario);
                stmtLike.setInt(2, idMusica);
                stmtLike.executeUpdate();
                return true;
            }
        } else {
            return false; // Música não encontrada
        }
    }
    }
    public String buscarMusicasCurtidasPeloUsuario(int usuarioId) throws SQLException {
    StringBuilder resultado = new StringBuilder();
    String sql = "SELECT " +
                 "    m.id, " +
                 "    m.titulo, " +
                 "    a.nome_artistico AS artista, " +
                 "    m.genero, " +
                 "    m.lancamento " +
                 "FROM Curtidas c " +
                 "JOIN Musica m ON c.musica_id = m.id " +
                 "JOIN Artista a ON m.artista_id = a.id " +
                 "WHERE c.usuario_id = ?";
    
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, usuarioId);
        ResultSet rs = stmt.executeQuery();
        
        while (rs.next()) {
            resultado.append("Título: ").append(rs.getString("titulo")).append("\n")
                     .append("Artista: ").append(rs.getString("artista")).append("\n")
                     .append("Gênero: ").append(rs.getString("genero")).append("\n")
                     .append("Lançamento: ").append(rs.getDate("lancamento")).append("\n")
                     .append("---------------------------\n");
        }
    }
    
    return resultado.length() > 0 ? resultado.toString() : "Nenhuma música curtida encontrada.\n";
    }
    

        
}
