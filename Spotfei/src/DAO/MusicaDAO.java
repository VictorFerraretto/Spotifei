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
import java.util.ArrayList;
import java.util.List;
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
   
    public ResultSet buscarArtista(String nome_artista) throws SQLException {
        String sqlBuscaArtista = "SELECT id FROM Artista WHERE nome_artistico = ?";
        PreparedStatement stmtBusca = conn.prepareStatement(sqlBuscaArtista);
        stmtBusca.setString(1, nome_artista);
        return stmtBusca.executeQuery();
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
        String buscarMusicaSql = "SELECT id FROM Musica WHERE titulo ILIKE ?";
        String excluirHistoricoSql = "DELETE FROM HistoricoBuscas WHERE musica_id = ?";
        String excluirMusicaPlaylistSql = "DELETE FROM MusicaPlaylist "
                                        + "WHERE musica_id = ?";
        String excluirCurtidaSql = "DELETE FROM Curtidas WHERE musica_id = ?";
        String excluirDescurtidaSql = "DELETE FROM Descurtidas WHERE musica_id = ?";
        String excluirMusicaSql = "DELETE FROM Musica WHERE id = ?";

        try (
            PreparedStatement buscarStmt = conn.prepareStatement(buscarMusicaSql)
        ) {
            buscarStmt.setString(1, titulo);
            ResultSet rs = buscarStmt.executeQuery();

            if (rs.next()) {
                int musicaId = rs.getInt("id");

                // exclui do Historico
                try (PreparedStatement excluirHist = conn.prepareStatement
                    (excluirHistoricoSql)) {
                    excluirHist.setInt(1, musicaId);
                    excluirHist.executeUpdate();
                }

                // exclui de MusicaPlaylist
                try (PreparedStatement excluirMP = conn.prepareStatement
                    (excluirMusicaPlaylistSql)) {
                    excluirMP.setInt(1, musicaId);
                    excluirMP.executeUpdate();
                }
                // excluir de Curtida
                try (PreparedStatement stmt = conn.prepareStatement(excluirCurtidaSql)) {
                    stmt.setInt(1, musicaId);
                    stmt.executeUpdate();
                }

                // excluir de Descurtida
                try (PreparedStatement stmt = conn.prepareStatement(excluirDescurtidaSql)) {
                    stmt.setInt(1, musicaId);
                    stmt.executeUpdate();
                }
                // excluir de Musica
                try (PreparedStatement excluirMusica = conn.prepareStatement
                    (excluirMusicaSql)) {
                    excluirMusica.setInt(1, musicaId);
                    int linhasAfetadas = excluirMusica.executeUpdate();
                    return linhasAfetadas > 0;
                }

            } else {
                System.out.println("Música não encontrada.");
                return false;
            }

        } catch (SQLException e) {
            System.err.println("Erro ao excluir música: " + e.getMessage());
            return false;
        }
    }
    
    //outro metedo simples para contar o total de musicas no banco de dados
    public int contarMusicas() {
        String sql = "SELECT COUNT(id) AS total FROM musica";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) { //tratamento para erro
            e.printStackTrace();
        }
        return 0;
    }
    
    
    public List<Musica> procurarMusicasParaHistorico(String titulo) throws SQLException {
        List<Musica> musicas = new ArrayList<>();

        String sql = "SELECT m.id, m.titulo, m.genero, m.lancamento, a.nome_artistico AS artista " +
                     "FROM Musica m " +
                     "JOIN Artista a ON m.artista_id = a.id " +
                     "WHERE m.titulo ILIKE ? OR a.nome_artistico ILIKE ? OR m.genero ILIKE ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (int i = 1; i <= 3; i++) {
                stmt.setString(i, "%" + titulo + "%");
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Musica m = new Musica();
                m.setId(rs.getInt("id"));
                m.setTitulo(rs.getString("titulo"));
                m.setGenero(rs.getString("genero"));
                m.setLancamento(rs.getDate("lancamento").toLocalDate());
                musicas.add(m);
            }
        }

        return musicas;
    }
 
}
    

