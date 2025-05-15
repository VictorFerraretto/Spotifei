/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author unifvnovais
 */
public class PlaylistDAO {
    private Connection conn;

    public PlaylistDAO(Connection conn) {
        this.conn = conn;        
    } 
    
    public void adicionarPlaylist(String nome, int usuarioId) throws SQLException{
        String sql = "INSERT INTO Playlist (nome, usuario_id) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setInt(2, usuarioId);
            stmt.executeUpdate();
        }
    }
    public String BuscarPlaylist(int usuarioId) throws SQLException{
        StringBuilder resultado = new StringBuilder();
        String sql = "SELECT nome FROM Playlist WHERE usuario_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            stmt.executeUpdate();
            

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                resultado.append("Nome: "
                          + "").append(rs.getString("nome")).append("\n")
                         .append("---------------------------\n");
            }
            }
            return resultado.toString();
        }
}
    
    

