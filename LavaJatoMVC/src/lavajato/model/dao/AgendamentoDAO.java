/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lavajato.model.dao;

import lavajato.model.entities.Agendamento;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AgendamentoDAO {
    private static List<Agendamento> agendamentos = new ArrayList<>(); // Adicione 'static'
    private static int nextId = 1; // Adicione 'static'

    public void agendar(Agendamento agendamento) {
        agendamento.setId(nextId++);
        agendamentos.add(agendamento);
        System.out.println("Agendamento salvo: " + agendamento.getId()); // Para debug
    }

    public List<Agendamento> listarTodos() {
        return new ArrayList<>(agendamentos);
    }

    public List<Agendamento> listarPorData(Date data) {
        List<Agendamento> resultado = new ArrayList<>();
        for (Agendamento ag : agendamentos) {
            if (isSameDay(ag.getDataHora(), data)) {
                resultado.add(ag);
            }
        }
        return resultado;
    }

    private boolean isSameDay(Date date1, Date date2) {
        // Implementação simplificada para comparar dias
        return date1.getDate() == date2.getDate() && 
               date1.getMonth() == date2.getMonth() && 
               date1.getYear() == date2.getYear();
    }

    public void cancelar(int id) {
        agendamentos.removeIf(ag -> ag.getId() == id);
    }

    public void atualizar(Agendamento agendamentoAtualizado) {
        for (int i = 0; i < agendamentos.size(); i++) {
            if (agendamentos.get(i).getId() == agendamentoAtualizado.getId()) {
                agendamentos.set(i, agendamentoAtualizado);
                break;
            }
        }
    }
}
