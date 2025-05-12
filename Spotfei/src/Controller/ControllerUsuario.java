/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.Conexao;
import DAO.UsuarioDAO;
import View.ConsultarUsuarios;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Gustavo
 */

// Novo Controller para ajudar com os usuarios
public class ControllerUsuario {
    private ConsultarUsuarios view;
    
    public ControllerUsuario(ConsultarUsuarios view){
        this.view = view; 
    }
    
    //metodo que busca os usuarios pelo nome
    public void buscarUsuarioPorNome() {
        String nome = view.getTxt_buscar_nome().getText();

        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Digite um nome para buscar.",
                                          "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Conexao conexao = new Conexao();
            UsuarioDAO usuarioDAO = new UsuarioDAO(conexao.getConnection());
            ResultSet rs = usuarioDAO.buscarNomes(nome);

            StringBuilder resultado = new StringBuilder();
            while (rs.next()) {
                resultado.append("Nome: ").append
                (rs.getString("nome")).append("\n");
                resultado.append("Username: ").append
                (rs.getString("username")).append("\n");
                resultado.append("Email: ").append
                (rs.getString("email")).append("\n");
                resultado.append("Telefone: 11 ").append
                (rs.getString("telefone")).append("\n");
                resultado.append("Tipo de Usuário: ").append
                (rs.getString("tipo_usuario")).append("\n");
                resultado.append("-----------------------------\n");
            }

            if (resultado.length() == 0) {
                view.getTxa_usuario().setText("Nenhum usuário "
                                    + "encontrado com esse nome.");
            } else {
                view.getTxa_usuario().setText(resultado.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Erro ao "
                    + "buscar usuário: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
