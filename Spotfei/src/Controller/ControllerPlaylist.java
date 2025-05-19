/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.Conexao;
import View.Playlist;
import DAO.PlaylistDAO;
import Model.Sessao;
import Model.Usuario;

import java.sql.SQLException;
import javax.swing.JOptionPane;


/**
 *
 * @author unifvnovais
 */
public class ControllerPlaylist {
    
    private Playlist view;

    public ControllerPlaylist(Playlist view) {
        this.view = view;
    }
    
    public void inserirPlaylist() {
        String nomePlay = view.getTxt_playlist().getText();

        if (nomePlay.isEmpty()) {
           JOptionPane.showMessageDialog(view, "Complete todos os campos necessários!", "Aviso", JOptionPane.WARNING_MESSAGE);
           return;  // importante sair aqui para não continuar
        }
        try {
            Conexao conexao = new Conexao();
            PlaylistDAO playDAO = new PlaylistDAO(conexao.getConnection());
            Usuario usuarioLogado = Sessao.getInstancia().getUsuarioLogado();
            playDAO.adicionarPlaylist(nomePlay, usuarioLogado.getId()); 

            JOptionPane.showMessageDialog(view, "Playlist adicionada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Erro ao inserir Playlist: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }  
    }
    
    public void deletarPlaylist() {
        String nomePlay = view.getTxt_playlist().getText();

        if (nomePlay.isEmpty()) {
           JOptionPane.showMessageDialog(view, "Complete todos os campos necessários!", "Aviso", JOptionPane.WARNING_MESSAGE);
           return;  // importante sair aqui para não continuar
        }
        try {
            Conexao conexao = new Conexao();
            PlaylistDAO playDAO = new PlaylistDAO(conexao.getConnection());
            Usuario usuarioLogado = Sessao.getInstancia().getUsuarioLogado();
            playDAO.removerPlaylist(nomePlay, usuarioLogado.getId()); 

            JOptionPane.showMessageDialog(view, "Playlist removida com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Erro ao deletar Playlist: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }  
    }
    
    public void procurarPlaylist(){
        try {
        Conexao conexao = new Conexao();
        PlaylistDAO playDAO = new PlaylistDAO(conexao.getConnection());
        Usuario usuarioLogado = Sessao.getInstancia().getUsuarioLogado();
        String resultado = playDAO.buscarPlaylist(usuarioLogado.getId());

        view.getjTextArea1().setText(resultado);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Erro ao procurar Playlist: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } 

    } 
    
    public void adicionarMusicaNaPlaylist() {
        String nomePlay = view.getTxt_playAddMusica().getText();
        String tituloMusica = view.getTxt_addMusica().getText();
        if (nomePlay.isEmpty() || tituloMusica.isEmpty()) {
           JOptionPane.showMessageDialog(view, "Complete todos os campos necessários!", "Aviso", JOptionPane.WARNING_MESSAGE);
           return;  // importante sair aqui para não continuar
        }
        try {
            Conexao conexao = new Conexao();
            PlaylistDAO playlistDAO = new PlaylistDAO(conexao.getConnection());
            Usuario usuario = Sessao.getInstancia().getUsuarioLogado();

            playlistDAO.adicionarMusicaNaPlaylistPorNomes(nomePlay, tituloMusica, usuario.getId());

            JOptionPane.showMessageDialog(null, "Música adicionada com sucesso à playlist!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao adicionar música na playlist: " + e.getMessage());
        }
    }
    
    public void removerMusicaDaPlaylist() {
        String nomePlay = view.getTxt_playAddMusica().getText();
        String tituloMusica = view.getTxt_addMusica().getText();

        if (nomePlay.isEmpty() || tituloMusica.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Complete todos os campos necessários!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Conexao conexao = new Conexao();
            PlaylistDAO playlistDAO = new PlaylistDAO(conexao.getConnection());
            Usuario usuario = Sessao.getInstancia().getUsuarioLogado();
            boolean sucesso = playlistDAO.removerMusicaDaPlaylistPorNomes(nomePlay, tituloMusica, usuario.getId());

            if (sucesso) {
                JOptionPane.showMessageDialog(null, "Música removida com sucesso da playlist!");
            } else {
                JOptionPane.showMessageDialog(null, "Música ou playlist não encontrada.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao remover música da playlist: " + e.getMessage());
        }
    }
    
    public void renomearPlaylist() {
        String nomeAntigo = view.getTxt_playEditar().getText();
        String novoNome = view.getTxt_novoNome().getText();

        if (nomeAntigo.isEmpty() || novoNome.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Complete todos os campos necessários!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Conexao conexao = new Conexao();
            PlaylistDAO playlistDAO = new PlaylistDAO(conexao.getConnection());
            Usuario usuario = Sessao.getInstancia().getUsuarioLogado();

            boolean sucesso = playlistDAO.renomearPlaylist(nomeAntigo, novoNome, usuario.getId());

            if (sucesso) {
                JOptionPane.showMessageDialog(null, "Nome da playlist alterado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Playlist não encontrada.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao renomear playlist: " + e.getMessage());
        }
    }
}
