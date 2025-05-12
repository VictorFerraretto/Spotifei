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
   
    public String procurarMusica(String musica) throws SQLException{
        StringBuilder resultado = new StringBuilder();
        String sql = "SELECT \n" +
                     "    m.id,\n" +
                     "    m.titulo,\n" +
                     "    a.nome_artistico AS artista,\n" +
                     "    m.genero,\n" +
                     "    m.lancamento\n" +
                     "FROM \n" +
                     "    Musica m\n" +
                     "JOIN \n" +
                     "    Artista a ON m.artista_id = a.id\n" +
                     "WHERE m.titulo ILIKE ? OR " +
                     "      a.nome_artistico ILIKE ? OR " +
                     "      m.genero ILIKE ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                for (int i = 1; i <= 3; i++) {
                    stmt.setString(i, "%" + musica + "%");
                }

                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    resultado.append("Título: "
                              + "").append(rs.getString("titulo")).append("\n")
                             .append("Artista: "
                              + "").append(rs.getString("artista")).append("\n")
                             .append("Gênero: "
                             + "").append(rs.getString("genero")).append("\n")
                             .append("Lançamento: "
                             + "").append(rs.getDate("lancamento")).append("\n")
                             .append("---------------------------\n");
                }
            }

            return resultado.toString();
    }
    
    //metodo que exclui a musica sem excluir o artista junto
    public boolean excluirMusicaPorTitulo(String titulo) {
    String sql = "DELETE FROM musica WHERE titulo ILIKE ?";

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, titulo);
        int linhasAfetadas = stmt.executeUpdate();
        return linhasAfetadas > 0;

    } catch (SQLException e) { //tratamento para erro
        e.printStackTrace();
        return false;
    }
}

}
    

