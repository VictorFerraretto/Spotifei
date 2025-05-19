/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;
import DAO.UsuarioDAO;
import DAO.Conexao;
import Model.Autentificacao;
import Model.Sessao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import Model.Usuario;
import View.Adm;
import View.Home;
import View.Login;
/**
 *
 * @author Victor
 */
public class ControllerLogin {
    private Login view;
    
    public ControllerLogin(Login view) {
        this.view = view;
    }
    
    public void loginUsuario() {
    
        String username = view.getTxt_usuario().getText();
        String senha = view.getTxt_senha().getText();  


        if (username.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Por favor, "
                                          + "preencha todos os campos.", "Erro", 
                                          JOptionPane.ERROR_MESSAGE);

        }


        Conexao conexao = new Conexao();

        try (Connection conn = conexao.getConnection()) {

            UsuarioDAO dao = new UsuarioDAO(conn);
            ResultSet res = dao.consultarPorUsernameESenha(username, senha);


            if (res.next()) {

                int id = res.getInt("id");
                String nome = res.getString("nome");
                String email = res.getString("email");
                String telefone = res.getString("telefone");
                String tipoUsuario = res.getString("tipo_usuario");


                Usuario usuario = new Usuario(username, senha, tipoUsuario, 
                                                id, nome, email, telefone);
                
                if(usuario.autenticar()){
                 // Salva o usuário logado na Sessão
                Sessao.getInstancia().setUsuarioLogado(usuario);

                JOptionPane.showMessageDialog(view, "Login efetuado!", "Aviso",
                                              JOptionPane.INFORMATION_MESSAGE);
                if ("admin".equalsIgnoreCase(tipoUsuario)) {
                    Adm adm = new Adm();
                    adm.setVisible(true);
                } else {
                    Home home = new Home();
                    home.setVisible(true);
                }           
                view.setVisible(false);
                }
            } else {

                JOptionPane.showMessageDialog(view, "Login NÃO efetuado!", "Aviso",
                                              JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Erro de conexão: "
                                          + e.getMessage(), "Aviso",
                                          JOptionPane.ERROR_MESSAGE);
        }
    }
    
        
}       

