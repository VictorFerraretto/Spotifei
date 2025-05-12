/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.ArtistaDAO;
import DAO.UsuarioDAO;
import DAO.Conexao;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import Model.Usuario;
import Model.Artista;
import View.CadastrarArtista;
import View.Cadastro;
import java.awt.HeadlessException;

/**
 *
 * @author Gustavo
 */
public class ControllerCadastro {
    private Cadastro view;
    private CadastrarArtista view2;
    
    public ControllerCadastro(Cadastro view){
        this.view = view;
    }
    
    public ControllerCadastro(CadastrarArtista view2){
        this.view2 = view2;
    }
    
    public void cadastrarUsuario(){
    String nome = view.getTxt_nome_cadastro().getText();
    String username = view.getTxt_username_cadastro().getText();
    String senha = view.getTxt_senha_cadastro().getText();
    String email = view.getTxt_email_cadastro().getText();
    String telefone = view.getTxt_telefone_cadastro().getText();

    if (nome.isEmpty() || username.isEmpty() || senha.isEmpty() ||
        email.isEmpty() || telefone.isEmpty()) {
        JOptionPane.showMessageDialog(view, "Preencha todos os campos!", "Aviso",
                JOptionPane.WARNING_MESSAGE);
        return;
    }

    Usuario usuario = new Usuario(username, senha, "comum", 0, nome, email, telefone);
    Conexao conexao = new Conexao();
    try {
        Connection conn = conexao.getConnection();
        UsuarioDAO dao = new UsuarioDAO(conn);
        dao.inserirUsuarioePessoa(usuario);
        JOptionPane.showMessageDialog(view, "Usuário cadastrado!", "Aviso", 
                                        JOptionPane.INFORMATION_MESSAGE);
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(view, "Erro ao cadastrar usuário!", "Erro", 
                                        JOptionPane.ERROR_MESSAGE);
    }
    }
    
    public void cadastrarArtista(){
    String nome = view2.getTxt_nome_artista().getText();
    String nome_artistico = view2.getTxt_nome_artistico().getText();
    String email = view2.getTxt_email_artista().getText();
    String telefone = view2.getTxt_telefone_artista().getText();
    String genero = view2.getTxt_genero_artista().getText();

    if (nome.isEmpty() || nome_artistico.isEmpty() || email.isEmpty() ||
        telefone.isEmpty() || genero.isEmpty()) {
        JOptionPane.showMessageDialog(view, "Preencha todos os campos!", "Aviso",
                                      JOptionPane.WARNING_MESSAGE);
        return;
    }

    try {
        // Conexão com o banco de dados e chamando o DAO
        Conexao conexao = new Conexao();
        ArtistaDAO artistaDAO = new ArtistaDAO(conexao.getConnection());

        boolean sucesso = artistaDAO.inserirArtista(nome, nome_artistico, 
                email, telefone, genero);

        if (sucesso) {
            JOptionPane.showMessageDialog(view, "Artista "
                    + "cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            // limpando os campos aqui
        } else {
            JOptionPane.showMessageDialog(view, "Erro: Já existe um artista "
                    + "cadastrado com esse nome artístico.", "Erro", JOptionPane.ERROR_MESSAGE);
        }

    } catch (HeadlessException | SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(view, "Erro: " + e.getMessage(),
                                      "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
        
}


