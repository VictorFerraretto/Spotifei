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
    public ControllerCurtidas(Login view2){
        this.view2 = view2; 
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
    public void AdicionarMusicaCurtida(String username) {
        
       String nome = username;
       String musica = view3.getTxt_nome_musica().getText();
       
        try {
            Conexao conexao = new Conexao();
            CurtidasDAO curtidasDAO = new CurtidasDAO(conexao.getConnection());
            if (nome.isEmpty() && musica.isEmpty()){
                JOptionPane.showMessageDialog(view3, ""
                                          + "Digite um nome para buscar.",
                                          "Aviso", JOptionPane.WARNING_MESSAGE);
            }else{
                boolean sucesso = curtidasDAO.Curtir(nome, musica);

                if (sucesso) {
                    JOptionPane.showMessageDialog(view3, "Música curtida com "
                            + "sucesso!", "Sucesso", 
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(view3, 
                            "Nenhuma música encontrada "
                            + "com esse título.", "Erro", 
                            JOptionPane.ERROR_MESSAGE);
                }
            }
            
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Erro ao curtir ",
                                          "Erro", JOptionPane.ERROR_MESSAGE);
        }
       
       
       
    }
}
