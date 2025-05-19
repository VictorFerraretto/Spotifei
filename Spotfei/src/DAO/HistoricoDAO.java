/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

/**
 *
 * @author Victor
 */
public class HistoricoDAO {
    private Connection conn;

    public HistoricoDAO(Connection conn) {
        this.conn = conn;        
    }
    
    
    public void adicionarBusca(int usuarioId, int musicaId) throws SQLException {
        String sql = "INSERT INTO HistoricoBuscas (usuario_id, musica_id) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            stmt.setInt(2, musicaId);
            stmt.executeUpdate();
        }
    }
    
    public String buscarUltimasBuscas(int usuarioId) throws SQLException {
        StringBuilder resultado = new StringBuilder();

        String sql = """
            SELECT 
                m.titulo,
                a.nome_artistico AS artista,
                m.genero,
                m.lancamento
            FROM 
                HistoricoBuscas hb
            JOIN 
                musica m ON hb.musica_id = m.id
            JOIN 
                artista a ON m.artista_id = a.id
            WHERE 
                hb.usuario_id = ?
            ORDER BY 
                hb.data_busca DESC
            LIMIT 10
        """;

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

        return resultado.toString();
    }
    
    
}
