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
    // conexao com o banco
    private Connection conn;

    public PlaylistDAO(Connection conn) {
        this.conn = conn;        
    } 
    // inseri a tabela playlist uma playlist nova
    public void adicionarPlaylist(String nome, int usuarioId) throws SQLException{
        String sql = "INSERT INTO Playlist (nome, usuario_id) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setInt(2, usuarioId);
            stmt.executeUpdate();
        }
    }
    // remove da tabela playlist uma playlist
    public void removerPlaylist(String nome, int usuarioId) throws SQLException {
        // primeiro, opegamos o id da playlist
        String obterIdSql = "SELECT id FROM Playlist WHERE nome = ? AND usuario_id = ?";
        try (PreparedStatement obterIdStmt = conn.prepareStatement(obterIdSql)) {
            obterIdStmt.setString(1, nome);
            obterIdStmt.setInt(2, usuarioId);
            ResultSet rs = obterIdStmt.executeQuery();

            if (rs.next()) {
                int playlistId = rs.getInt("id");

                // agora, removendo as musicas associadas a playlist
                String removerMusicasSql = "DELETE FROM MusicaPlaylist WHERE playlist_id = ?";
                try (PreparedStatement removerMusicasStmt = conn.prepareStatement(removerMusicasSql)) {
                    removerMusicasStmt.setInt(1, playlistId);
                    removerMusicasStmt.executeUpdate();
                }

                // por fim, removendo a playlist
                String removerPlaylistSql = "DELETE FROM Playlist WHERE id = ?";
                try (PreparedStatement removerPlaylistStmt = conn.prepareStatement(removerPlaylistSql)) {
                    removerPlaylistStmt.setInt(1, playlistId);
                    int linhasAfetadas = removerPlaylistStmt.executeUpdate();
                    if (linhasAfetadas == 0) {
                        throw new SQLException("Nenhuma playlist foi removida. Verifique se o nome está correto.");
                    }
                }
            } else {
                throw new SQLException("Playlist não encontrada para o usuário informado.");
            }
        }
    }
    //metodo que busca uma playlist
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

                // se a playlist mudou, mostramos o que tem dentro dela
                if (idPlaylist != playlistAtual) {
                    if (playlistAtual != -1) {
                        resultado.append("\n");
                    }
                    resultado.append("Playlist: ").append(nomePlaylist).append("\n");
                    resultado.append("------------------------\n");
                    playlistAtual = idPlaylist;
                }

                // adiciona a música na playlist, se houver
                if (tituloMusica != null) {
                    resultado.append("     ").append(tituloMusica).append("\n");
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
    
    public void adicionarMusicaNaPlaylistPorNomes(String nomePlaylist, 
            String tituloMusica, int usuarioId) throws SQLException {
        // buscando o id da playlist
        String sqlPlaylist = "SELECT id FROM Playlist WHERE nome = ? AND usuario_id = ?";
        PreparedStatement stmtPlaylist = conn.prepareStatement(sqlPlaylist);
        stmtPlaylist.setString(1, nomePlaylist);
        stmtPlaylist.setInt(2, usuarioId);
        ResultSet rsPlaylist = stmtPlaylist.executeQuery();

        if (!rsPlaylist.next()) {
            throw new SQLException("Playlist não encontrada.");
        }
        int playlistId = rsPlaylist.getInt("id");

        // buscando o id da música
        String sqlMusica = "SELECT id FROM Musica WHERE titulo = ?";
        PreparedStatement stmtMusica = conn.prepareStatement(sqlMusica);
        stmtMusica.setString(1, tituloMusica);
        ResultSet rsMusica = stmtMusica.executeQuery();

        if (!rsMusica.next()) {
            throw new SQLException("Música não encontrada.");
        }
        int musicaId = rsMusica.getInt("id");

        // colocando na tabela de MusicaPlaylist
        String sqlInsert = "INSERT INTO MusicaPlaylist (playlist_id, musica_id) VALUES (?, ?)";
        PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert);
        stmtInsert.setInt(1, playlistId);
        stmtInsert.setInt(2, musicaId);
        stmtInsert.executeUpdate();
    }
    //remove a musica da playlist
    public boolean removerMusicaDaPlaylistPorNomes(String nomePlaylist, 
                String tituloMusica, int idUsuario) throws SQLException {
        String sql = """
            DELETE FROM MusicaPlaylist
            WHERE playlist_id = (
                SELECT id FROM Playlist
                WHERE nome = ? AND usuario_id = ?
            )
            AND musica_id = (
                SELECT id FROM Musica
                WHERE titulo = ?
            )
        """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nomePlaylist);
            stmt.setInt(2, idUsuario);
            stmt.setString(3, tituloMusica);

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        }
    }
    // editando o nome da playlist desejada
    public boolean renomearPlaylist(String nomeAntigo, String nomeNovo,
                                    int idUsuario) throws SQLException {
    String sql = "UPDATE Playlist SET nome = ? WHERE nome = ? AND usuario_id = ?";

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, nomeNovo);
        stmt.setString(2, nomeAntigo);
        stmt.setInt(3, idUsuario);

        int linhasAfetadas = stmt.executeUpdate();
        return linhasAfetadas > 0;
    }
}
}
    
    

