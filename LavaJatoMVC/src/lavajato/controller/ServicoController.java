/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lavajato.controller;

import java.util.List;
import lavajato.model.dao.ServicoDAO;
import lavajato.model.entities.Servico;

public class ServicoController {
    private ServicoDAO servicoDAO;

    public ServicoController() {
        this.servicoDAO = new ServicoDAO();
    }

    public void cadastrarServico(String nome, String descricao, double preco) {
        Servico servico = new Servico(0, nome, descricao, preco);
        servicoDAO.cadastrar(servico);
    }

    public List<Servico> listarServicos() {
        return servicoDAO.listarTodos();
    }

    public Servico buscarServicoPorId(int id) {
        return servicoDAO.buscarPorId(id);
    }

    public void atualizarServico(Servico servico) {
        servicoDAO.atualizar(servico);
    }

    public void excluirServico(int id) {
        servicoDAO.excluir(id);
    }
}
