/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lavajato.model.dao;

import lavajato.model.entities.Servico;
import java.util.ArrayList;
import java.util.List;

public class ServicoDAO {
    private static List<Servico> servicos = new ArrayList<>();
    private static int nextId = 1;

    public void cadastrar(Servico servico) {
        servico.setId(nextId++);
        servicos.add(servico);
    }

    public List<Servico> listarTodos() {
        return new ArrayList<>(servicos);
    }

    public Servico buscarPorId(int id) {
        for (Servico servico : servicos) {
            if (servico.getId() == id) {
                return servico;
            }
        }
        return null;
    }

    public void atualizar(Servico servicoAtualizado) {
        for (int i = 0; i < servicos.size(); i++) {
            if (servicos.get(i).getId() == servicoAtualizado.getId()) {
                servicos.set(i, servicoAtualizado);
                break;
            }
        }
    }

    public void excluir(int id) {
        servicos.removeIf(servico -> servico.getId() == id);
    }
}
