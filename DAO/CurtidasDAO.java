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
    
}
