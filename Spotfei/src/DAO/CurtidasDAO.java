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
    
    public boolean Curtir(String nome, String titulo) throws SQLException{

        String sql = "INSERT INTO Curtidas (usuario_id, musica_id) "
                    + "SELECT u.id, m.id "
                    + "FROM Usuario u "
                    + "JOIN Pessoa p ON u.pessoa_id = p.id "
                    + "JOIN Musica m ON m.titulo = ? "
                    + "WHERE p.nome = ? "
                    + "AND NOT EXISTS ("
                    + "    SELECT 1 "
                    + "    FROM Curtidas c "
                    + "    WHERE c.usuario_id = u.id "
                    + "    AND c.musica_id = m.id"
                    + ");";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, titulo);
            stmt.setString(2, nome);
            
        } catch (SQLException e) { //tratamento para erro
            e.printStackTrace();
            return false;
        }
        return true;
        
               
    }
    
    
}
