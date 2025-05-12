/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.Conexao;
import DAO.CurtidasDAO;
import java.util.List;
import javax.swing.JOptionPane;
import View.VisualizarEstatisticas;
import java.sql.SQLException;
/**
 *
 * @author Gustavo
 */
public class ControllerCurtidas {
    
    private VisualizarEstatisticas view;
    
    public ControllerCurtidas(VisualizarEstatisticas view){
        this.view = view; 
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
    
}
