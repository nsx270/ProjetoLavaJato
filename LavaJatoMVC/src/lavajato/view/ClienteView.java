/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lavajato.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import lavajato.controller.ClienteController;
import lavajato.model.entities.Cliente;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ClienteView extends JFrame {
    private ClienteController controller;
    private JTable tabelaClientes;
    private DefaultTableModel tableModel;

    public ClienteView() {
        controller = new ClienteController();
        initComponents();
        carregarClientes();
    }

    private void initComponents() {
        setTitle("Gerenciamento de Clientes");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        // Painel superior com botões
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAdicionar = new JButton("Adicionar");
        JButton btnEditar = new JButton("Editar");
        JButton btnExcluir = new JButton("Excluir");
        
        panelBotoes.add(btnAdicionar);
        panelBotoes.add(btnEditar);
        panelBotoes.add(btnExcluir);

        // Tabela de clientes
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "CPF", "Telefone", "Email", "Placa Veículo"}, 0);
        tabelaClientes = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabelaClientes);

        panel.add(panelBotoes, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Ações dos botões
        btnAdicionar.addActionListener(e -> abrirFormularioCliente(null));
        btnEditar.addActionListener(e -> {
            int linhaSelecionada = tabelaClientes.getSelectedRow();
            if (linhaSelecionada >= 0) {
                int id = (int) tableModel.getValueAt(linhaSelecionada, 0);
                Cliente cliente = controller.buscarClientePorCpf((String) tableModel.getValueAt(linhaSelecionada, 2));
                abrirFormularioCliente(cliente);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um cliente para editar");
            }
        });

        btnExcluir.addActionListener(e -> {
            int linhaSelecionada = tabelaClientes.getSelectedRow();
            if (linhaSelecionada >= 0) {
                int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir este cliente?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    int id = (int) tableModel.getValueAt(linhaSelecionada, 0);
                    controller.excluirCliente(id);
                    carregarClientes();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um cliente para excluir");
            }
        });

        add(panel);
    }

    private void carregarClientes() {
        tableModel.setRowCount(0);
        List<Cliente> clientes = controller.listarClientes();
        for (Cliente cliente : clientes) {
            tableModel.addRow(new Object[]{
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getTelefone(),
                cliente.getEmail(),
                cliente.getPlacaVeiculo()
            });
        }
    }

    private void abrirFormularioCliente(Cliente cliente) {
        JDialog dialog = new JDialog(this, cliente == null ? "Novo Cliente" : "Editar Cliente", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));

        JTextField txtNome = new JTextField();
        JTextField txtCpf = new JTextField();
        JTextField txtTelefone = new JTextField();
        JTextField txtEmail = new JTextField();
        JTextField txtPlaca = new JTextField();

        if (cliente != null) {
            txtNome.setText(cliente.getNome());
            txtCpf.setText(cliente.getCpf());
            txtTelefone.setText(cliente.getTelefone());
            txtEmail.setText(cliente.getEmail());
            txtPlaca.setText(cliente.getPlacaVeiculo());
        }

        panel.add(new JLabel("Nome:"));
        panel.add(txtNome);
        panel.add(new JLabel("CPF:"));
        panel.add(txtCpf);
        panel.add(new JLabel("Telefone:"));
        panel.add(txtTelefone);
        panel.add(new JLabel("Email:"));
        panel.add(txtEmail);
        panel.add(new JLabel("Placa do Veículo:"));
        panel.add(txtPlaca);

        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");

        btnSalvar.addActionListener(e -> {
            Cliente novoCliente = new Cliente(
                txtNome.getText(),
                txtCpf.getText(),
                txtTelefone.getText(),
                txtEmail.getText(),
                txtPlaca.getText()
            );

            if (cliente == null) {
                controller.cadastrarCliente(
                    novoCliente.getNome(),
                    novoCliente.getCpf(),
                    novoCliente.getTelefone(),
                    novoCliente.getEmail(),
                    novoCliente.getPlacaVeiculo()
                );
            } else {
                novoCliente.setId(cliente.getId());
                controller.atualizarCliente(novoCliente);
            }

            carregarClientes();
            dialog.dispose();
        });

        btnCancelar.addActionListener(e -> dialog.dispose());

        panel.add(btnSalvar);
        panel.add(btnCancelar);

        dialog.add(panel);
        dialog.setVisible(true);
    }
}
