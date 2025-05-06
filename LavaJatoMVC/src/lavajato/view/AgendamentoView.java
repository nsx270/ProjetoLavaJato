/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lavajato.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import lavajato.controller.AgendamentoController;
import lavajato.controller.ClienteController;
import lavajato.controller.ServicoController;
import lavajato.model.entities.Agendamento;
import lavajato.model.entities.Cliente;
import lavajato.model.entities.Servico;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AgendamentoView extends JFrame {
    private AgendamentoController agendamentoController;
    private ClienteController clienteController;
    private ServicoController servicoController;
    private JTable tabelaAgendamentos;
    private DefaultTableModel tableModel;
    private JComboBox<Cliente> comboClientes;
    private JComboBox<Servico> comboServicos;
    private JFormattedTextField txtData;
    private JFormattedTextField txtHora;

    public AgendamentoView() {
        agendamentoController = new AgendamentoController();
        clienteController = new ClienteController();
        servicoController = new ServicoController();
        initComponents();
        carregarAgendamentos();
    }

    private void initComponents() {
        setTitle("Gerenciamento de Agendamentos");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        // Painel superior com filtros e botões
        JPanel panelSuperior = new JPanel(new GridLayout(2, 1));

        // Painel de filtros
        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblData = new JLabel("Data:");
        txtData = new JFormattedTextField(new SimpleDateFormat("dd/MM/yyyy"));
        txtData.setColumns(10);
        JButton btnFiltrar = new JButton("Filtrar");
        
        panelFiltros.add(lblData);
        panelFiltros.add(txtData);
        panelFiltros.add(btnFiltrar);

        // Painel de botões
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAgendar = new JButton("Novo Agendamento");
        JButton btnEditar = new JButton("Editar");
        JButton btnCancelar = new JButton("Cancelar");
        
        panelBotoes.add(btnAgendar);
        panelBotoes.add(btnEditar);
        panelBotoes.add(btnCancelar);

        panelSuperior.add(panelFiltros);
        panelSuperior.add(panelBotoes);

        // Tabela de agendamentos
        tableModel = new DefaultTableModel(new Object[]{"ID", "Cliente", "Serviço", "Data/Hora", "Valor"}, 0);
        tabelaAgendamentos = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabelaAgendamentos);

        panel.add(panelSuperior, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Ações dos botões
        btnFiltrar.addActionListener(e -> {
            try {
                Date data = (Date) txtData.getValue();
                if (data != null) {
                    carregarAgendamentosPorData(data);
                } else {
                    carregarAgendamentos();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Data inválida!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnAgendar.addActionListener(e -> abrirFormularioAgendamento(null));
        btnEditar.addActionListener(e -> {
            int linhaSelecionada = tabelaAgendamentos.getSelectedRow();
            if (linhaSelecionada >= 0) {
                int id = (int) tableModel.getValueAt(linhaSelecionada, 0);
                // Aqui precisaríamos de um método para buscar agendamento por ID
                // Implementação simplificada
                JOptionPane.showMessageDialog(this, "Edição de agendamento selecionado");
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um agendamento para editar");
            }
        });

        btnCancelar.addActionListener(e -> {
            int linhaSelecionada = tabelaAgendamentos.getSelectedRow();
            if (linhaSelecionada >= 0) {
                int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja cancelar este agendamento?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    int id = (int) tableModel.getValueAt(linhaSelecionada, 0);
                    agendamentoController.cancelarAgendamento(id);
                    carregarAgendamentos();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um agendamento para cancelar");
            }
        });

        add(panel);
    }

    private void carregarAgendamentos() {
        tableModel.setRowCount(0);
        List<Agendamento> agendamentos = agendamentoController.listarAgendamentosPorData(new Date());
        for (Agendamento ag : agendamentos) {
            tableModel.addRow(new Object[]{
                ag.getId(),
                ag.getCliente().getNome(),
                ag.getServico().getNome(),
                new SimpleDateFormat("dd/MM/yyyy HH:mm").format(ag.getDataHora()),
                String.format("R$ %.2f", ag.getServico().getPreco())
            });
        }
    }

    private void carregarAgendamentosPorData(Date data) {
        tableModel.setRowCount(0);
        List<Agendamento> agendamentos = agendamentoController.listarAgendamentosPorData(data);
        for (Agendamento ag : agendamentos) {
            tableModel.addRow(new Object[]{
                ag.getId(),
                ag.getCliente().getNome(),
                ag.getServico().getNome(),
                new SimpleDateFormat("dd/MM/yyyy HH:mm").format(ag.getDataHora()),
                String.format("R$ %.2f", ag.getServico().getPreco())
            });
        }
    }

    private void abrirFormularioAgendamento(Agendamento agendamento) {
        JDialog dialog = new JDialog(this, agendamento == null ? "Novo Agendamento" : "Editar Agendamento", true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));

        // Combobox de clientes
        comboClientes = new JComboBox<>();
        List<Cliente> clientes = clienteController.listarClientes();
        for (Cliente cliente : clientes) {
            comboClientes.addItem(cliente);
        }

        // Combobox de serviços
        comboServicos = new JComboBox<>();
        List<Servico> servicos = servicoController.listarServicos();
        for (Servico servico : servicos) {
            comboServicos.addItem(servico);
        }

        // Campos de data e hora
        txtData = new JFormattedTextField(new SimpleDateFormat("dd/MM/yyyy"));
        txtHora = new JFormattedTextField(new SimpleDateFormat("HH:mm"));
        JTextArea txtObservacoes = new JTextArea();
        JScrollPane scrollObservacoes = new JScrollPane(txtObservacoes);

        // Preenche os campos se estiver editando
        if (agendamento != null) {
            comboClientes.setSelectedItem(agendamento.getCliente());
            comboServicos.setSelectedItem(agendamento.getServico());
            txtData.setValue(agendamento.getDataHora());
            txtHora.setValue(agendamento.getDataHora());
            txtObservacoes.setText(agendamento.getObservacoes());
        } else {
            // Valores padrão para novo agendamento
            txtData.setValue(new Date());
            txtHora.setValue(new Date());
        }

        // Adiciona componentes ao painel
        panel.add(new JLabel("Cliente:"));
        panel.add(comboClientes);
        panel.add(new JLabel("Serviço:"));
        panel.add(comboServicos);
        panel.add(new JLabel("Data:"));
        panel.add(txtData);
        panel.add(new JLabel("Hora:"));
        panel.add(txtHora);
        panel.add(new JLabel("Observações:"));
        panel.add(scrollObservacoes);

        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");

        btnSalvar.addActionListener(e -> {
            try {
                Cliente cliente = (Cliente) comboClientes.getSelectedItem();
                Servico servico = (Servico) comboServicos.getSelectedItem();
                
                // Obtém os valores dos campos
                Date data = (Date) txtData.getValue();
                Date hora = (Date) txtHora.getValue();
                
                // Validações básicas
                if (cliente == null || servico == null || data == null || hora == null) {
                    JOptionPane.showMessageDialog(dialog, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Combina data e hora corretamente usando Calendar
                Calendar calData = Calendar.getInstance();
                calData.setTime(data);
                
                Calendar calHora = Calendar.getInstance();
                calHora.setTime(hora);
                
                Calendar dataHoraCompleta = Calendar.getInstance();
                dataHoraCompleta.set(calData.get(Calendar.YEAR), 
                                    calData.get(Calendar.MONTH),
                                    calData.get(Calendar.DAY_OF_MONTH),
                                    calHora.get(Calendar.HOUR_OF_DAY),
                                    calHora.get(Calendar.MINUTE),
                                    0); // Segundos zerados

                String observacoes = txtObservacoes.getText();

                if (agendamento == null) {
                    // Cria novo agendamento
                    agendamentoController.agendarServico(
                        cliente.getId(), 
                        servico.getId(), 
                        dataHoraCompleta.getTime(), 
                        observacoes
                    );
                    JOptionPane.showMessageDialog(dialog, "Agendamento criado com sucesso!");
                } else {
                    // Atualiza agendamento existente (implementação futura)
                    JOptionPane.showMessageDialog(dialog, "Edição de agendamento ainda não implementada");
                }

                carregarAgendamentos();
                dialog.dispose();
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, 
                    "Erro ao salvar agendamento: " + ex.getMessage(), 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        btnCancelar.addActionListener(e -> dialog.dispose());

        panel.add(btnSalvar);
        panel.add(btnCancelar);

        dialog.add(panel);
        dialog.setVisible(true);
    }
    
}
