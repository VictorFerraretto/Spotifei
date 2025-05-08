/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.UsuarioDAO;
import DAO.Conexao;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import Model.Usuario;
import View.Cadastro;

/**
 *
 * @author Gustavo
 */
public class ControllerCadastro {
    private Cadastro view;
    
    public ControllerCadastro(Cadastro view){
        this.view = view;
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
}

