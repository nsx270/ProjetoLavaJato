/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lavajato.model.dao;

import lavajato.model.entities.Cliente;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    private static List<Cliente> clientes = new ArrayList<>(); // static para persistir
    private static int nextId = 1; // static para manter o contador

    public void cadastrar(Cliente cliente) {
        cliente.setId(nextId++);
        clientes.add(cliente);
        System.out.println("Cliente cadastrado: " + cliente.getNome()); // Debug
    }

    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes);
    }
    
    public Cliente buscarPorId(int id) {
    for (Cliente cliente : clientes) {
        if (cliente.getId() == id) {
            return cliente;
        }
    }
    return null;
    }
    
    public Cliente buscarPorCpf(String cpf) {
        for (Cliente cliente : clientes) {
            if (cliente.getCpf().equals(cpf)) {
                return cliente;
            }
        }
        return null;
    }

    public void atualizar(Cliente clienteAtualizado) {
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getId() == clienteAtualizado.getId()) {
                clientes.set(i, clienteAtualizado);
                break;
            }
        }
    }

    public void excluir(int id) {
        clientes.removeIf(cliente -> cliente.getId() == id);
    }
}
