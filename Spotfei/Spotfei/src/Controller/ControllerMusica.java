/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.Conexao;
import DAO.MusicaDAO;
import View.CadastrarMusica;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



/**
 *
 * @author Gustavo
 */
public class ControllerMusica {
    private CadastrarMusica view;

    public ControllerMusica(CadastrarMusica view) {
        this.view = view;
    }
    
    public void cadastrarMusica(){
        String titulo = view.getTxt_titulo_musica().getText();
        String genero = view.getTxt_genero_musica().getText();
        String lancamento = view.getTxt_lancamento_musica().getText();
        LocalDate data_lancamento = LocalDate.parse(lancamento);
        String nome_artista = view.getTxt_artista_musica().getText();
        
        // caso o usuario nao digite nada
        if (titulo.isEmpty() || genero.isEmpty() || lancamento.isEmpty() ||
        nome_artista.isEmpty()) {
        JOptionPane.showMessageDialog(view, "Complete todos os campos "
                                      + "necessarios!", "Aviso",
                                       JOptionPane.WARNING_MESSAGE);
        return;
        }
        
        try {
        Conexao conexao = new Conexao();

        // Busca o ID do artista
        String sqlBuscaArtista = "SELECT id FROM Artista WHERE nome_artistico = ?";
        PreparedStatement stmtBusca = conexao.getConnection().
                                        prepareStatement(sqlBuscaArtista);
        stmtBusca.setString(1, nome_artista);
        ResultSet rs = stmtBusca.executeQuery();

        if (!rs.next()) {
            JOptionPane.showMessageDialog(view, "Não encontramos o artista! "
                                            + "Cadastre o artista primeiro.", 
                                            "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int artistaId = rs.getInt("id");

        // Usa o MusicaDAO para inserir
        MusicaDAO musicaDAO = new MusicaDAO(conexao.getConnection());
        musicaDAO.inserirMusica(titulo, genero, data_lancamento, artistaId);

        JOptionPane.showMessageDialog(view, "Música cadastrada com sucesso!", 
                                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(view, "Erro ao cadastrar música: " 
                            + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
    }
    }
    
}
