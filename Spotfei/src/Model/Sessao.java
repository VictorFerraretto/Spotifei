/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author unifvnovais
 */
public class Sessao {
    private static Sessao instancia;
    private Usuario usuarioLogado;

    private Sessao() {}

    public static Sessao getInstancia() {
        if (instancia == null) {
            instancia = new Sessao();
        }
        return instancia;
    }

    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void limparSessao() {
        usuarioLogado = null;
    }
}
