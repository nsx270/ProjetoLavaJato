/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lavajato.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import lavajato.controller.ServicoController;
import lavajato.model.entities.Servico;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ServicoView extends JFrame {
    private ServicoController controller;
    private JTable tabelaServicos;
    private DefaultTableModel tableModel;

    public ServicoView() {
        controller = new ServicoController();
        initComponents();
        carregarServicos();
    }

    private void initComponents() {
        setTitle("Gerenciamento de Serviços");
        setSize(700, 500);
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

        // Tabela de serviços
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Descrição", "Preço (R$)"}, 0);
        tabelaServicos = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabelaServicos);

        panel.add(panelBotoes, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Ações dos botões
        btnAdicionar.addActionListener(e -> abrirFormularioServico(null));
        btnEditar.addActionListener(e -> {
            int linhaSelecionada = tabelaServicos.getSelectedRow();
            if (linhaSelecionada >= 0) {
                int id = (int) tableModel.getValueAt(linhaSelecionada, 0);
                Servico servico = controller.buscarServicoPorId(id);
                abrirFormularioServico(servico);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um serviço para editar");
            }
        });

        btnExcluir.addActionListener(e -> {
            int linhaSelecionada = tabelaServicos.getSelectedRow();
            if (linhaSelecionada >= 0) {
                int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir este serviço?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    int id = (int) tableModel.getValueAt(linhaSelecionada, 0);
                    controller.excluirServico(id);
                    carregarServicos();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um serviço para excluir");
            }
        });

        add(panel);
    }

    private void carregarServicos() {
        tableModel.setRowCount(0);
        List<Servico> servicos = controller.listarServicos();
        for (Servico servico : servicos) {
            tableModel.addRow(new Object[]{
                servico.getId(),
                servico.getNome(),
                servico.getDescricao(),
                String.format("R$ %.2f", servico.getPreco())
            });
        }
    }

    private void abrirFormularioServico(Servico servico) {
        JDialog dialog = new JDialog(this, servico == null ? "Novo Serviço" : "Editar Serviço", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));

        JTextField txtNome = new JTextField();
        JTextArea txtDescricao = new JTextArea();
        JScrollPane scrollDescricao = new JScrollPane(txtDescricao);
        JTextField txtPreco = new JTextField();

        if (servico != null) {
            txtNome.setText(servico.getNome());
            txtDescricao.setText(servico.getDescricao());
            txtPreco.setText(String.valueOf(servico.getPreco()));
        }

        panel.add(new JLabel("Nome:"));
        panel.add(txtNome);
        panel.add(new JLabel("Descrição:"));
        panel.add(scrollDescricao);
        panel.add(new JLabel("Preço (R$):"));
        panel.add(txtPreco);

        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");

        btnSalvar.addActionListener(e -> {
            try {
                String nome = txtNome.getText();
                String descricao = txtDescricao.getText();
                double preco = Double.parseDouble(txtPreco.getText());

                Servico novoServico = new Servico(0, nome, descricao, preco);

                if (servico == null) {
                    controller.cadastrarServico(nome, descricao, preco);
                } else {
                    novoServico.setId(servico.getId());
                    controller.atualizarServico(novoServico);
                }

                carregarServicos();
                dialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Preço inválido! Digite um valor numérico.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancelar.addActionListener(e -> dialog.dispose());

        panel.add(btnSalvar);
        panel.add(btnCancelar);

        dialog.add(panel);
        dialog.setVisible(true);
    }
}