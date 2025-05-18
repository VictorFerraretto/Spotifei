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
    public void removerPlaylist(String nome, int usuarioId) throws SQLException {
        String sql = "DELETE FROM Playlist WHERE nome = ? AND usuario_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setInt(2, usuarioId);
            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas == 0) {
                throw new SQLException("Nenhuma playlist foi removida. Verifique se o nome está correto.");
            }
        }
    }
    
    public String buscarPlaylist(int usuarioId) {
        StringBuilder resultado = new StringBuilder();

        String sql = """
            SELECT p.id AS playlist_id, p.nome AS playlist_nome, m.titulo AS musica_titulo
            FROM Playlist p
            LEFT JOIN MusicaPlaylist mp ON p.id = mp.playlist_id
            LEFT JOIN Musica m ON mp.musica_id = m.id
            WHERE p.usuario_id = ?
            ORDER BY p.id;
        """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();

            int playlistAtual = -1;
            while (rs.next()) {
                int idPlaylist = rs.getInt("playlist_id");
                String nomePlaylist = rs.getString("playlist_nome");
                String tituloMusica = rs.getString("musica_titulo");

                // Se a playlist mudou, imprimimos o cabeçalho dela
                if (idPlaylist != playlistAtual) {
                    if (playlistAtual != -1) {
                        resultado.append("\n");
                    }
                    resultado.append("Playlist: ").append(nomePlaylist).append("\n");
                    resultado.append("------------------------\n");
                    playlistAtual = idPlaylist;
                }

                // Adiciona a música, se houver
                if (tituloMusica != null) {
                    resultado.append("   ♪ ").append(tituloMusica).append("\n");
                } else {
                    resultado.append("   (Sem músicas)\n");
                }
            }

            if (resultado.length() == 0) {
                resultado.append("Você ainda não possui playlists.\n");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            resultado.append("Erro ao buscar playlists.");
        }

        return resultado.toString();
    }
}
    
    

