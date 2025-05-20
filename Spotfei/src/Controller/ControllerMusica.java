/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Controller;

/**
 *
 * @author Gustavo
 */

// todos os importes necessarios 
import DAO.Conexao;
import DAO.HistoricoDAO;
import DAO.MusicaDAO;
import Model.Musica;
import Model.Sessao;
import Model.Usuario;
import View.CadastrarMusica;
import View.BuscarMusica;
import View.ExcluirMusica;
import View.Historico;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import View.VisualizarEstatisticas;
import java.util.List;

public class ControllerMusica {
    //linkando com as interfaces
    private CadastrarMusica view;
    private BuscarMusica view2;
    private ExcluirMusica view3;
    private VisualizarEstatisticas view4;
    private Historico view5;
    
    public ControllerMusica(CadastrarMusica view) {
        this.view = view;
    }

    public ControllerMusica(BuscarMusica view2) {
        this.view2 = view2;
    }

    public ControllerMusica(ExcluirMusica view3) {
        this.view3 = view3;
    }
    
    public ControllerMusica(VisualizarEstatisticas view4){
        this.view4 = view4;
    }
    public ControllerMusica(Historico view5){
        this.view5 = view5;
    }
    // cadastrando musicas nop banco
    public void cadastrarMusica() {
        String titulo = view.getTxt_titulo_musica().getText();
        String genero = view.getTxt_genero_musica().getText();
        String lancamento = view.getTxt_lancamento_musica().getText();
        LocalDate data_lancamento = LocalDate.parse(lancamento);
        String nome_artista = view.getTxt_artista_musica().getText();

        if (titulo.isEmpty() || genero.isEmpty() || lancamento.isEmpty() 
                || nome_artista.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Complete todos os campos "
                    + "necessários!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Conexao conexao = new Conexao();
            // seleciona o artista pelo nome
            String sqlBuscaArtista = "SELECT id FROM Artista WHERE "
                                    + "nome_artistico = ?";
            PreparedStatement stmtBusca = conexao.getConnection().
                    prepareStatement(sqlBuscaArtista);
            stmtBusca.setString(1, nome_artista);
            ResultSet rs = stmtBusca.executeQuery();

            if (!rs.next()) {
                JOptionPane.showMessageDialog(view, "Não encontramos "
                        + "o artista! Cadastre o artista primeiro.", 
                        "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int artistaId = rs.getInt("id");

            MusicaDAO musicaDAO = new MusicaDAO(conexao.getConnection());
            musicaDAO.inserirMusica(titulo, genero, data_lancamento, artistaId);

            JOptionPane.showMessageDialog(view, "Música "
                    + "cadastrada com sucesso!", "Sucesso", 
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Erro ao "
                    + "cadastrar música: " + e.getMessage(), 
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    // buscando musicas
    public void buscarMusica() {
        String musica = view2.getTxt_buscar().getText();

        if (musica.isEmpty()) {
            JOptionPane.showMessageDialog(view2, "Complete todos os campos "
                    + "necessários!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Conexao conexao = new Conexao();
            MusicaDAO musicaDAO = new MusicaDAO(conexao.getConnection());
            String resultado = musicaDAO.procurarMusica(musica);
            HistoricoDAO historicoDAO = new HistoricoDAO(conexao.getConnection());
            
            if (resultado.isEmpty()) {
                view2.getjTextArea1().setText("Nenhuma música encontrada.");
            } else {
                view2.getjTextArea1().setText(resultado);
            }
            Usuario usuarioLogado = Sessao.getInstancia().getUsuarioLogado();
            if (usuarioLogado != null) {
                List<Musica> musicas = musicaDAO.procurarMusicasParaHistorico(musica);
                for (Musica m : musicas) {
                    historicoDAO.adicionarBusca(usuarioLogado.getId(), m.getId());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view2, "Erro ao buscar música: " 
                    + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    // excluindo musica pelo titulo 
    public void excluirMusica() {
        String titulo = view3.getTxt_excluir_musica().getText();

        if (titulo.isEmpty()) {
            JOptionPane.showMessageDialog(view3, "Digite o título da "
                    + "música que deseja excluir.", "Aviso", 
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Conexao conexao = new Conexao();
            MusicaDAO musicaDAO = new MusicaDAO(conexao.getConnection());

            boolean sucesso = musicaDAO.excluirMusicaPorTitulo(titulo);

            if (sucesso) {
                JOptionPane.showMessageDialog(view3, "Música excluída com "
                        + "sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(view3, "Nenhuma música encontrada "
                        + "com esse título.", "Erro", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view3, "Erro ao excluir música: " 
                    + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    // molstra o total de musicas no banco
    public void mostrarTotalMusicas() {
        try {
            Conexao conexao = new Conexao();
            MusicaDAO musicasDAO = new MusicaDAO(conexao.getConnection());

            int total = musicasDAO.contarMusicas();

            view4.getTxa_total_musicas().setText("Total "
                    + "de músicas: " + total);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view4, 
                    "Erro ao contar músicas: " + e.getMessage(),
                                          "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    // mostra o historico de buscas do usuario
    public void mostrarHistoricoBuscas() {
        Usuario usuarioLogado = Sessao.getInstancia().getUsuarioLogado();
        if (usuarioLogado == null) {
            JOptionPane.showMessageDialog(view5, "Usuário não logado.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            HistoricoDAO dao = new HistoricoDAO(new Conexao().getConnection());
            String musicas = dao.buscarUltimasBuscas(usuarioLogado.getId());

            if (musicas.isEmpty()) {
                view5.getTxt_musicasBus().setText("Nenhuma busca recente.");
            } else {
                view5.getTxt_musicasBus().setText(musicas);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view5, "Erro ao buscar histórico: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
}
