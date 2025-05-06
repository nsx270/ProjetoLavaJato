/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lavajato.model.entities;

import java.util.Date;

public class Agendamento {
    private int id;
    private Cliente cliente;
    private Servico servico;
    private Date dataHora;
    private String observacoes;

    // Construtor, getters e setters
    public Agendamento() {}
    
    public Agendamento(int id, Cliente cliente, Servico servico, Date dataHora, String observacoes) {
        this.id = id;
        this.cliente = cliente;
        this.servico = servico;
        this.dataHora = dataHora;
        this.observacoes = observacoes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}
