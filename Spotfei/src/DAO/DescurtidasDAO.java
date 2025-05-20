/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

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
public class DescurtidasDAO {
    //conexao com o banco de dados
    private Connection conn;

    public DescurtidasDAO(Connection conn) {
        this.conn = conn;        
    }
    
    //usei o mesmo metodo do que nas curtidas
    public List<String> Descurtidas() {
    String sql = "SELECT m.titulo, COUNT(d.musica_id) AS descurtidas " +
                 "FROM descurtidas d " +
                 "JOIN musica m ON d.musica_id = m.id " +
                 "GROUP BY m.titulo " +
                 "ORDER BY descurtidas DESC " +
                 "LIMIT 5";
    List<String> resultado = new ArrayList<>();

    try (PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            String linha = "Música: " + rs.getString("titulo") +
                           " Descurtidas: " + rs.getInt("descurtidas");
            resultado.add(linha);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return resultado;
    }
    //mesmo metodo que no curtir as musicas
    public boolean Descurtir(int idUsuario, String nomeMusica) throws SQLException {
    String sqlBusca = "SELECT id FROM musica WHERE titulo = ?";
    String sqlLike = "INSERT INTO Descurtidas (usuario_id, musica_id) VALUES (?, ?)";

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
    //mesmo metodo que no buscar musicas curtidas pelo usuario_id
    public String buscarMusicasDescurtidaPeloUsuario(int usuarioId) throws SQLException {
    StringBuilder resultado = new StringBuilder();
    String sql = "SELECT " +
                 "    m.id, " +
                 "    m.titulo, " +
                 "    a.nome_artistico AS artista, " +
                 "    m.genero, " +
                 "    m.lancamento " +
                 "FROM Descurtidas d " +
                 "JOIN Musica m ON d.musica_id = m.id " +
                 "JOIN Artista a ON m.artista_id = a.id " +
                 "WHERE d.usuario_id = ?";
    
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
    
    return resultado.length() > 0 ? resultado.toString() : "Nenhuma música descurtida encontrada.\n";
    }
}
