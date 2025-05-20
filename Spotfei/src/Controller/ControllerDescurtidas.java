/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.Conexao;
import DAO.DescurtidasDAO;
import Model.Sessao;
import Model.Usuario;
import View.Avalicao;
import View.Historico;
import java.util.List;
import javax.swing.JOptionPane;
import View.VisualizarEstatisticas;
import java.sql.SQLException;

/**
 *
 * @author Gustavo
 */
public class ControllerDescurtidas {
    // linkando com  as interfaces
    private VisualizarEstatisticas view;
    private Historico view2;
    private Avalicao view3;
    public ControllerDescurtidas(VisualizarEstatisticas view){
        this.view = view; 
    }
    public ControllerDescurtidas(Historico view2){
        this.view2 = view2; 
    }
    public ControllerDescurtidas(Avalicao view3){
        this.view3 = view3; 
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
    // mesmo metodo usada na curtidas, mas agora para descurtidas
    public void AdicionarMusicaDescurtida() {
        String nomeMusica = view3.getTxt_nome_musica().getText();

        
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
            DescurtidasDAO descurtidasDAO = new DescurtidasDAO(conexao.getConnection());

            boolean sucesso = descurtidasDAO.Descurtir(idUsuario, nomeMusica);

            if (sucesso) {
                JOptionPane.showMessageDialog(view3, 
                    "Música descurtida com sucesso!", "Sucesso", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(view3, 
                    "Nenhuma música encontrada com esse título.", "Erro", 
                    JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view3, 
                "Erro ao descurtir a música: " + e.getMessage(), "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    // metodo que mostra a musica descurtida pelo usuario
    public void mostrarMusicasDescurtidasPeloUsuario() {
        Usuario usuarioLogado = Sessao.getInstancia().getUsuarioLogado();

        if (usuarioLogado == null) {
            JOptionPane.showMessageDialog(view2, "Usuário não logado.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            DescurtidasDAO dao = new DescurtidasDAO(new Conexao().getConnection());
            String resultado = dao.buscarMusicasDescurtidaPeloUsuario(usuarioLogado.getId());
            
            if (resultado.isEmpty()) {
                view2.getTxt_musicasDes().setText("Nenhuma música encontrada.");
            } else {
                view2.getTxt_musicasDes().setText(resultado);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view2, "Erro ao buscar curtidas: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
