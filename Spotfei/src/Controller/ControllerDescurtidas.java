/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.Conexao;
import DAO.DescurtidasDAO;
import java.util.List;
import javax.swing.JOptionPane;
import View.VisualizarEstatisticas;
import java.sql.SQLException;

/**
 *
 * @author Gustavo
 */
public class ControllerDescurtidas {
    
    private VisualizarEstatisticas view;
    
    public ControllerDescurtidas(VisualizarEstatisticas view){
        this.view = view; 
    }
    
    // mesmo metedo usado na curtidas
    public void Descurtidas() {
    try {
        Conexao conexao = new Conexao();
        DescurtidasDAO descurtidasDAO = new DescurtidasDAO(conexao.getConnection());
        List<String> top5 = descurtidasDAO.Descurtidas();

        if (top5.isEmpty()) {
            view.getTxa_top5_desc().setText("Nenhuma música foi descurtida!!!");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (String linha : top5) {
            sb.append(linha).append("\n");
        }

        view.getTxa_top5_desc().setText(sb.toString());

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(view, "Erro ao buscar "
                + "Top 5 descurtidas: " + e.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
    
    
}
