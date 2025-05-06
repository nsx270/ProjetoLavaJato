/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lavajato.controller;

import lavajato.model.dao.AgendamentoDAO;
import lavajato.model.dao.ClienteDAO;
import lavajato.model.dao.ServicoDAO;
import lavajato.model.entities.Agendamento;
import lavajato.model.entities.Cliente;
import lavajato.model.entities.Servico;
import java.util.Date;
import java.util.List;

public class AgendamentoController {
    private AgendamentoDAO agendamentoDAO;
    private ClienteDAO clienteDAO;
    private ServicoDAO servicoDAO;

    public AgendamentoController() {
        this.agendamentoDAO = new AgendamentoDAO();
        this.clienteDAO = new ClienteDAO();
        this.servicoDAO = new ServicoDAO();
    }

    public void agendarServico(int clienteId, int servicoId, Date dataHora, String observacoes) {
    Cliente cliente = clienteDAO.buscarPorId(clienteId);
    Servico servico = servicoDAO.buscarPorId(servicoId);
    
    if (cliente != null && servico != null) {
        Agendamento agendamento = new Agendamento(0, cliente, servico, dataHora, observacoes);
        agendamentoDAO.agendar(agendamento);
        System.out.println("Agendamento criado para: " + cliente.getNome()); // Debug
    } else {
        System.out.println("Cliente ou serviço não encontrado!"); // Debug
        }
    }

    public List<Agendamento> listarAgendamentosPorData(Date data) {
        return agendamentoDAO.listarPorData(data);
    }

    public void cancelarAgendamento(int id) {
        agendamentoDAO.cancelar(id);
    }

    public void atualizarAgendamento(Agendamento agendamento) {
        agendamentoDAO.atualizar(agendamento);
    }

    public List<Cliente> listarClientes() {
        return clienteDAO.listarTodos();
    }

    public List<Servico> listarServicos() {
        return servicoDAO.listarTodos();
    }
}
