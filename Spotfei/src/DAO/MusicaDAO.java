/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Model.Musica;
import java.time.LocalDate;
/**
 *
 * @author Gustavo
 */
public class MusicaDAO {
    private Connection conn;

    public MusicaDAO(Connection conn) {
        this.conn = conn;
    }

   public void inserirMusica(String titulo, String genero, 
            LocalDate dataLancamento, int artistaId) throws SQLException {
    String sql = "INSERT INTO Musica (titulo, artista_id, genero, lancamento) "
                 + "VALUES (?, ?, ?, ?)";
    PreparedStatement ms = conn.prepareStatement(sql);
    ms.setString(1, titulo);         // primeiro o titulo
    ms.setInt(2, artistaId);         // segundo o artista_id
    ms.setString(3, genero);         // terceiro o genero
    ms.setDate(4, java.sql.Date.valueOf(dataLancamento));  // quarto o lancamento
    ms.executeUpdate();
}
}
    

