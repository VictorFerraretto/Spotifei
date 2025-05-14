/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.Conexao;
import DAO.CurtidasDAO;
import View.Avalicao;
import View.Login;
import java.util.List;
import javax.swing.JOptionPane;
import View.VisualizarEstatisticas;
import java.sql.SQLException;
import Controller.ControllerLogin;
import Model.Sessao;
import Model.Usuario;
/**
 *
 * @author Gustavo
 */
public class ControllerCurtidas {
    
    private VisualizarEstatisticas view;
    private Login view2;
    private Avalicao view3;
     
    public ControllerCurtidas(VisualizarEstatisticas view){
        this.view = view; 
    }
    
    public ControllerCurtidas(Avalicao view3){
        this.view3 = view3; 
    }
    // metodo para mostrar as curtidas no txa_top5
    public void mostrarCurtidas() {
    try {
        Conexao conexao = new Conexao();
        CurtidasDAO curtidasDAO = new CurtidasDAO(conexao.getConnection());
        List<String> top5 = curtidasDAO.Curtidas(); 

        if (top5.isEmpty()) {
            view.getTxa_top5().setText("Nenhuma música foi curtida!!");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (String linha : top5) {
            sb.append(linha).append("\n");
        }

        view.getTxa_top5().setText(sb.toString());

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Erro ao buscar "
                                + "top 5 curtidas: " + e.getMessage(),
                                          "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void AdicionarMusicaCurtida() {
        String nomeMusica = view3.getTxt_nome_musica().getText();

        // Pega o usuário logado da sessão
        Usuario usuarioLogado = Sessao.getInstancia().getUsuarioLogado();

        if (usuarioLogado == null) {
            JOptionPane.showMessageDialog(view3, 
                "Usuário não está logado.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idUsuario = usuarioLogado.getId();

        if (nomeMusica.isEmpty()) {
            JOptionPane.showMessageDialog(view3, 
                "Digite o nome da música.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Conexao conexao = new Conexao();
            CurtidasDAO curtidasDAO = new CurtidasDAO(conexao.getConnection());

            boolean sucesso = curtidasDAO.Curtir(idUsuario, nomeMusica);

            if (sucesso) {
                JOptionPane.showMessageDialog(view3, 
                    "Música curtida com sucesso!", "Sucesso", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(view3, 
                    "Nenhuma música encontrada com esse título.", "Erro", 
                    JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view3, 
                "Erro ao curtir a música: " + e.getMessage(), "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
