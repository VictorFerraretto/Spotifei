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
}
