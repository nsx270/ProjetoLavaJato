/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lavajato.view;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipal extends JFrame {
    public MenuPrincipal() {
        initComponents();
    }

    private void initComponents() {
    setTitle("Sistema Lava Jato");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(400, 300);
    setLocationRelativeTo(null);

    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

    // Botões com tamanho aumentado e alinhamento centralizado
    JButton btnClientes = new JButton("Gerenciar Clientes");
    JButton btnServicos = new JButton("Gerenciar Serviços");
    JButton btnAgendamentos = new JButton("Gerenciar Agendamentos");
    JButton btnSair = new JButton("Sair");

    // Define um tamanho preferido (largura, altura)
    Dimension buttonSize = new Dimension(200, 100);
    btnClientes.setPreferredSize(buttonSize);
    btnServicos.setPreferredSize(buttonSize);
    btnAgendamentos.setPreferredSize(buttonSize);
    btnSair.setPreferredSize(buttonSize);

    // Alinha os botões ao centro horizontalmente
    btnClientes.setAlignmentX(Component.CENTER_ALIGNMENT);
    btnServicos.setAlignmentX(Component.CENTER_ALIGNMENT);
    btnAgendamentos.setAlignmentX(Component.CENTER_ALIGNMENT);
    btnSair.setAlignmentX(Component.CENTER_ALIGNMENT);

    // Adiciona margens e os botões ao painel
    panel.add(Box.createVerticalStrut(20));
    panel.add(btnClientes);
    panel.add(Box.createVerticalStrut(10));
    panel.add(btnServicos);
    panel.add(Box.createVerticalStrut(10));
    panel.add(btnAgendamentos);
    panel.add(Box.createVerticalStrut(30));
    panel.add(btnSair);

    // Listeners (mantidos como no seu código original)
    btnClientes.addActionListener(e -> new ClienteView().setVisible(true));
    btnServicos.addActionListener(e -> new ServicoView().setVisible(true));
    btnAgendamentos.addActionListener(e -> new AgendamentoView().setVisible(true));
    btnSair.addActionListener(e -> System.exit(0));

    add(panel);
    }
}