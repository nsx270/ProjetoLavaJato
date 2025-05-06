/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lavajato.controller;

import java.util.List;
import lavajato.model.dao.ClienteDAO;
import lavajato.model.entities.Cliente;

public class ClienteController {
    private ClienteDAO clienteDAO;

    public ClienteController() {
        this.clienteDAO = new ClienteDAO();
    }

    public void cadastrarCliente(String nome, String cpf, String telefone, String email, String placaVeiculo) {
        Cliente cliente = new Cliente(nome, cpf, telefone, email, placaVeiculo);
        clienteDAO.cadastrar(cliente);
    }

    public List<Cliente> listarClientes() {
        return clienteDAO.listarTodos();
    }

    public Cliente buscarClientePorCpf(String cpf) {
        return clienteDAO.buscarPorCpf(cpf);
    }

    public void atualizarCliente(Cliente cliente) {
        clienteDAO.atualizar(cliente);
    }

    public void excluirCliente(int id) {
        clienteDAO.excluir(id);
    }
}
