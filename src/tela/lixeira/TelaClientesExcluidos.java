/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package tela.lixeira;

import enumerations.EstadosBrazil;
import exceptions.InvalidInputException;
import exceptions.SystemErrorException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelos.PessoaFisica;
import modelos.PessoaJuridica;
import modelos.auxiliares.Endereco;
import persistencia.ManipulaBancoPessoaFisica;
import persistencia.ManipulaBancoPessoaJuridica;
import persistencia.ManipulaBancoVeiculo;
import tela.TelaListaCliente.operacaoBusca;

/**
 *
 * @author tanak
 */
public class TelaClientesExcluidos extends javax.swing.JInternalFrame {

    /**
     * Creates new form TelaClientesExcluidos
     */
    public TelaClientesExcluidos() {
        initComponents();
        bloquearTela();
        loadCbEstados();
        loadTableClientes();
        controlaTela("Pessoa Fisica");
    }

    private void bloquearTela() {
        jFormattedTextFieldCep.setEnabled(false);
        jFormattedTextFieldCnpj.setEnabled(false);
        jFormattedTextFieldCpf.setEnabled(false);
        jFormattedTextFieldDataNascimento.setEnabled(false);
        jFormattedTextFieldTelefoneCelular.setEnabled(false);
        jFormattedTextFieldTelefoneComercial.setEnabled(false);
        jFormattedTextFieldTelefoneResidencial.setEnabled(false);
        tField_Bairro.setEnabled(false);
        tField_Cidade.setEnabled(false);
        tField_Complemento.setEnabled(false);
        tField_Logradouro.setEnabled(false);
        tField_Numero.setEnabled(false);
        tField_TipoLogradouro.setEnabled(false);
        tField_email.setEnabled(false);
        tField_nomeFantasia.setEnabled(false);
        tField_nome_razaoSocial.setEnabled(false);
        jRadioButton_PessoaFisica.setEnabled(false);
        cb_Estado.setEnabled(false);
        jRadioButton_PessoaJuridica.setEnabled(false);
    }

    private void controlaTela(String estado) {
        switch (estado) {
            case "Pessoa Fisica":
                jFormattedTextFieldCnpj.setVisible(false);
                tField_nomeFantasia.setVisible(false);
                jLabelDataNasc_NomeFantasia.setText("Data de nascimento");
                jLabelNome_razaoSocial.setText("Nome:");
                jLabel_CPF_CNPJ.setText("CPF:");
                jFormattedTextFieldCpf.setVisible(true);
                jFormattedTextFieldDataNascimento.setVisible(true);
                break;
            case "Pessoa Juridica":
                jFormattedTextFieldCnpj.setVisible(true);
                tField_nomeFantasia.setVisible(true);
                jLabelDataNasc_NomeFantasia.setText("Nome fantasia");
                jLabelNome_razaoSocial.setText("Razão social:");
                jLabel_CPF_CNPJ.setText("CNPJ:");
                jFormattedTextFieldCpf.setVisible(false);
                jFormattedTextFieldDataNascimento.setVisible(false);
                break;
            default:
                throw new IllegalStateException("Tentando mudar a configuração da tela para a configuração \"" + estado + "\", que é inválida");
        }
    }

    private void loadCbEstados() {
        cb_Estado.setModel(new DefaultComboBoxModel<>(EstadosBrazil.values()));
    }

    private void loadTableClientes() {
        try {
            DefaultTableModel table = (DefaultTableModel) jTableClientes.getModel();
            table.setRowCount(0);// * apagando dados da tabela para não duplicar as linhas
            ArrayList<PessoaFisica> listaPessoas = new ManipulaBancoPessoaFisica().buscarTodosRemovidos();
            ArrayList<PessoaJuridica> listaPessoasJuridicas = new ManipulaBancoPessoaJuridica().buscarTodosRemovidos();
            if (listaPessoas != null && !listaPessoas.isEmpty()) {//    * não tentar se nãoo tiver dados para serem carregados
                for (PessoaFisica p : listaPessoas) {
                    String[] dados = new String[jTableClientes.getColumnCount()];
                    dados[0] = p.getNome();
                    dados[1] = p.getCpf();
                    dados[2] = p.getTelefone()[0];
                    dados[3] = p.getTelefone()[1];
                    dados[4] = p.getTelefone()[2];
                    dados[5] = new SimpleDateFormat("dd/MM/yyyy").format(p.getDataNascimento());
                    dados[6] = p.getEmail();
//                    dados[7] = "" + new ManipulaBancoVeiculo().getQuantidadeVeiculos(new ManipulaBancoPessoaFisica().buscar(p));

                    table.addRow(dados);
                }
            }
            if (listaPessoasJuridicas != null && !listaPessoasJuridicas.isEmpty()) {
                for (PessoaJuridica p : listaPessoasJuridicas) {
                    String[] dados = new String[jTableClientes.getColumnCount()];
                    dados[0] = p.getRazaoSocial();
                    dados[1] = p.getCnpj();
                    dados[2] = p.getTelefone()[0];
                    dados[3] = p.getTelefone()[1];
                    dados[4] = p.getTelefone()[2];
                    dados[5] = p.getNomeFantasia();
                    dados[6] = p.getEmail();
                    dados[7] = "" + new ManipulaBancoVeiculo().getQuantidadeVeiculos(new ManipulaBancoPessoaJuridica().buscar(p));

                    table.addRow(dados);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }

    private void loadTableClientes(String filtro, operacaoBusca op) {
        try {
            switch (op) {
                case NOME:
                    filtraTabelaNome(filtro);
                    break;
                case RAZAO_SOCIAL:
                    filtraTabelaRazaoSocial(filtro);
                    break;
                case CNPJ:
                    filtraTabelaCnpj(filtro);
                    break;
                case CPF:
                    filtraTabelaCpf(filtro);
                    break;
                case PESSOA_FISICA:
                    filtraTabelaPessoaFisica();
                    break;
                case PESSOA_JURIDICA:
                    filtraTabelaPessoajuridica();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }

    private void filtraTabelaNome(String nome) {
        try {
            DefaultTableModel table = (DefaultTableModel) jTableClientes.getModel();
            table.setRowCount(0);// * apagando dados da tabela para não duplicar as linhas
            ArrayList<PessoaFisica> listaPessoas = new ManipulaBancoPessoaFisica().buscarTodosRemovidos();
            if (listaPessoas != null) {
                for (PessoaFisica p : listaPessoas) {
                    if (p.getNome().toUpperCase().contains(nome.toUpperCase())) {
                        String[] dados = new String[jTableClientes.getColumnCount()];
                        dados[0] = p.getNome();
                        dados[1] = p.getCpf();
                        dados[2] = p.getTelefone()[0];
                        dados[3] = p.getTelefone()[1];
                        dados[4] = p.getTelefone()[2];
                        dados[5] = new SimpleDateFormat("dd/MM/yyyy").format(p.getDataNascimento());
                        dados[6] = p.getEmail();
                        dados[7] = "" + new ManipulaBancoVeiculo().getQuantidadeVeiculos(new ManipulaBancoPessoaFisica().buscar(p));

                        table.addRow(dados);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }

    private void filtraTabelaRazaoSocial(String razaoSocial) {
        try {
            DefaultTableModel table = (DefaultTableModel) jTableClientes.getModel();
            table.setRowCount(0);// * apagando dados da tabela para não duplicar as linhas
            ArrayList<PessoaJuridica> listaPessoasJuridicas = new ManipulaBancoPessoaJuridica().buscarTodosRemovidos();
            if (listaPessoasJuridicas == null) {
                return;
            }
            for (PessoaJuridica p : listaPessoasJuridicas) {
                if (p.getRazaoSocial().toUpperCase().contains(razaoSocial.toUpperCase())) {

                    String[] dados = new String[jTableClientes.getColumnCount()];
                    dados[0] = p.getRazaoSocial();
                    dados[1] = p.getCnpj();
                    dados[2] = p.getTelefone()[0];
                    dados[3] = p.getTelefone()[1];
                    dados[4] = p.getTelefone()[2];
                    dados[5] = p.getNomeFantasia();
                    dados[6] = p.getEmail();
                    dados[7] = "" + new ManipulaBancoVeiculo().getQuantidadeVeiculos(new ManipulaBancoPessoaJuridica().buscar(p));

                    table.addRow(dados);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }

    private void filtraTabelaCpf(String cpf) {
        try {
            DefaultTableModel table = (DefaultTableModel) jTableClientes.getModel();
            table.setRowCount(0);// * apagando dados da tabela para não duplicar as linhas
            ArrayList<PessoaFisica> listaPessoas = new ManipulaBancoPessoaFisica().buscarTodosRemovidos();
            if (listaPessoas != null) {
                for (PessoaFisica p : listaPessoas) {
                    if (p.getCpf().toUpperCase().contains(cpf)) {
                        String[] dados = new String[jTableClientes.getColumnCount()];
                        dados[0] = p.getNome();
                        dados[1] = p.getCpf();
                        dados[2] = p.getTelefone()[0];
                        dados[3] = p.getTelefone()[1];
                        dados[4] = p.getTelefone()[2];
                        dados[5] = new SimpleDateFormat("dd/MM/yyyy").format(p.getDataNascimento());
                        dados[6] = p.getEmail();
                        dados[7] = "" + new ManipulaBancoVeiculo().getQuantidadeVeiculos(new ManipulaBancoPessoaFisica().buscar(p));

                        table.addRow(dados);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }

    private void filtraTabelaCnpj(String cnpj) {
        try {
            System.out.println(cnpj.replace(".", "").replace(" ", "").replace("/", "").replace("-", ""));
            DefaultTableModel table = (DefaultTableModel) jTableClientes.getModel();
            table.setRowCount(0);// * apagando dados da tabela para não duplicar as linhas
            ArrayList<PessoaJuridica> listaPessoasJuridicas = new ManipulaBancoPessoaJuridica().buscarTodosRemovidos();
            if (listaPessoasJuridicas == null) {
                return;
            }
            for (PessoaJuridica p : listaPessoasJuridicas) {
                if (p.getCnpj().contains(cnpj)) {

                    String[] dados = new String[jTableClientes.getColumnCount()];
                    dados[0] = p.getRazaoSocial();
                    dados[1] = p.getCnpj();
                    dados[2] = p.getTelefone()[0];
                    dados[3] = p.getTelefone()[1];
                    dados[4] = p.getTelefone()[2];
                    dados[5] = p.getNomeFantasia();
                    dados[6] = p.getEmail();
                    dados[7] = "" + new ManipulaBancoVeiculo().getQuantidadeVeiculos(new ManipulaBancoPessoaJuridica().buscar(p));

                    table.addRow(dados);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }

    private void filtraTabelaPessoaFisica() {
        try {
            DefaultTableModel table = (DefaultTableModel) jTableClientes.getModel();
            table.setRowCount(0);// * apagando dados da tabela para não duplicar as linhas
            ArrayList<PessoaFisica> listaPessoas = new ManipulaBancoPessoaFisica().buscarTodosRemovidos();
            if (listaPessoas != null) {
                for (PessoaFisica p : listaPessoas) {
                    String[] dados = new String[jTableClientes.getColumnCount()];
                    dados[0] = p.getNome();
                    dados[1] = p.getCpf();
                    dados[2] = p.getTelefone()[0];
                    dados[3] = p.getTelefone()[1];
                    dados[4] = p.getTelefone()[2];
                    dados[5] = new SimpleDateFormat("dd/MM/yyyy").format(p.getDataNascimento());
                    dados[6] = p.getEmail();
                    dados[7] = "" + new ManipulaBancoVeiculo().getQuantidadeVeiculos(new ManipulaBancoPessoaFisica().buscar(p));

                    table.addRow(dados);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }

    private void filtraTabelaPessoajuridica() {
        try {
            DefaultTableModel table = (DefaultTableModel) jTableClientes.getModel();
            table.setRowCount(0);// * apagando dados da tabela para não duplicar as linhas
            ArrayList<PessoaJuridica> listaPessoasJuridicas = new ManipulaBancoPessoaJuridica().buscarTodosRemovidos();
            if (listaPessoasJuridicas == null) {
                return;
            }
            for (PessoaJuridica p : listaPessoasJuridicas) {

                String[] dados = new String[jTableClientes.getColumnCount()];
                dados[0] = p.getRazaoSocial();
                dados[1] = p.getCnpj();
                dados[2] = p.getTelefone()[0];
                dados[3] = p.getTelefone()[1];
                dados[4] = p.getTelefone()[2];
                dados[5] = p.getNomeFantasia();
                dados[6] = p.getEmail();
                dados[7] = "" + new ManipulaBancoVeiculo().getQuantidadeVeiculos(new ManipulaBancoPessoaJuridica().buscar(p));

                table.addRow(dados);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup_TipoPessoa = new javax.swing.ButtonGroup();
        jLabel9 = new javax.swing.JLabel();
        jLabelDataNasc_NomeFantasia = new javax.swing.JLabel();
        tField_nome_razaoSocial = new javax.swing.JTextField();
        tField_Complemento = new javax.swing.JTextField();
        tField_TipoLogradouro = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableClientes = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        tField_Bairro = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        tField_Cidade = new javax.swing.JTextField();
        tField_email = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        tField_nomeFantasia = new javax.swing.JTextField();
        cb_Estado = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jFormattedTextFieldCpf = new javax.swing.JFormattedTextField();
        tField_Logradouro = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jFormattedTextFieldCnpj = new javax.swing.JFormattedTextField();
        tField_Numero = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jFormattedTextFieldDataNascimento = new javax.swing.JFormattedTextField();
        jLabelNome_razaoSocial = new javax.swing.JLabel();
        jFormattedTextFieldCep = new javax.swing.JFormattedTextField();
        jLabel15 = new javax.swing.JLabel();
        jFormattedTextFieldTelefoneResidencial = new javax.swing.JFormattedTextField();
        jRadioButton_PessoaFisica = new javax.swing.JRadioButton();
        jFormattedTextFieldTelefoneComercial = new javax.swing.JFormattedTextField();
        jLabel_CPF_CNPJ = new javax.swing.JLabel();
        jFormattedTextFieldTelefoneCelular = new javax.swing.JFormattedTextField();
        jRadioButton_PessoaJuridica = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        jButton_restaurar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();

        jLabel9.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel9.setText("Complemento:");

        jLabelDataNasc_NomeFantasia.setText("Data nascimento");

        jLabel10.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel10.setText("Bairro:");

        jTableClientes.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jTableClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nome/Razão social", "CPF/CNPJ", "Celular", "Telefone Comercial", "Telefone fixo", "Data de Nascimento/Nome social", "E-mail", "Quantidade de veiculos"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTableClientesMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTableClientes);

        jLabel11.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel11.setText("Cidade:");

        jLabel5.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel5.setText("Email:");

        jLabel12.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel12.setText("Estado:");

        cb_Estado.setAutoscrolls(true);
        cb_Estado.setPreferredSize(new java.awt.Dimension(7, 22));

        jLabel7.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel7.setText("Logradouro");

        try {
            jFormattedTextFieldCpf.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel13.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel13.setText("CEP:");

        try {
            jFormattedTextFieldCnpj.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##.###.###/####-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel14.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel14.setText("Residencial:");

        jLabel8.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel8.setText("Nº:");

        try {
            jFormattedTextFieldDataNascimento.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabelNome_razaoSocial.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabelNome_razaoSocial.setText("Nome: ");

        try {
            jFormattedTextFieldCep.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#####-###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel15.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel15.setText("Comercial:");

        try {
            jFormattedTextFieldTelefoneResidencial.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) #####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        buttonGroup_TipoPessoa.add(jRadioButton_PessoaFisica);
        jRadioButton_PessoaFisica.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jRadioButton_PessoaFisica.setText("Pessoa Fisica");
        jRadioButton_PessoaFisica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton_PessoaFisicaActionPerformed(evt);
            }
        });

        try {
            jFormattedTextFieldTelefoneComercial.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) #####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel_CPF_CNPJ.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel_CPF_CNPJ.setText("CPF: ");

        try {
            jFormattedTextFieldTelefoneCelular.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) #####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        buttonGroup_TipoPessoa.add(jRadioButton_PessoaJuridica);
        jRadioButton_PessoaJuridica.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jRadioButton_PessoaJuridica.setText("Pessoa Juridica");
        jRadioButton_PessoaJuridica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton_PessoaJuridicaActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel3.setText("Celular:");

        jButton_restaurar.setBackground(new java.awt.Color(0, 204, 255));
        jButton_restaurar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jButton_restaurar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-mais-2-matemática-verde-30.png"))); // NOI18N
        jButton_restaurar.setText("Restaurar");
        jButton_restaurar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton_restaurar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_restaurarActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel4.setText("Tipo Logradouro:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1210, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelNome_razaoSocial, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(22, 22, 22)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tField_nome_razaoSocial, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jFormattedTextFieldTelefoneCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(5, 5, 5)
                                        .addComponent(jLabel14)
                                        .addGap(5, 5, 5)
                                        .addComponent(jFormattedTextFieldTelefoneResidencial, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(33, 33, 33)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel_CPF_CNPJ, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel15))
                                .addGap(25, 25, 25)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jFormattedTextFieldCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jFormattedTextFieldCnpj, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jFormattedTextFieldTelefoneComercial, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabelDataNasc_NomeFantasia)
                                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(5, 5, 5)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(399, 399, 399)
                                                .addComponent(tField_email, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(tField_Cidade, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(12, 12, 12)
                                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(46, 46, 46)
                                                .addComponent(cb_Estado, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(15, 15, 15)
                                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(5, 5, 5)
                                                .addComponent(tField_Bairro, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(320, 320, 320)
                                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(636, 636, 636)
                                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(708, 708, 708)
                                                .addComponent(jFormattedTextFieldCep, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(tField_nomeFantasia, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(102, 102, 102)
                                        .addComponent(jFormattedTextFieldDataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(24, 24, 24)
                                .addComponent(tField_Numero, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(5, 5, 5)
                                .addComponent(tField_TipoLogradouro, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(5, 5, 5)
                                .addComponent(tField_Logradouro, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(81, 81, 81)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(41, 41, 41)
                                .addComponent(tField_Complemento, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(221, 221, 221)
                                .addComponent(jRadioButton_PessoaFisica, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(283, 283, 283)
                                .addComponent(jRadioButton_PessoaJuridica, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(275, 275, 275)
                        .addComponent(jButton_restaurar, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButton_PessoaFisica)
                    .addComponent(jRadioButton_PessoaJuridica))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabelNome_razaoSocial, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(tField_nome_razaoSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jFormattedTextFieldTelefoneCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jFormattedTextFieldTelefoneResidencial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel_CPF_CNPJ, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jFormattedTextFieldCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jFormattedTextFieldCnpj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)
                        .addComponent(jFormattedTextFieldTelefoneComercial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabelDataNasc_NomeFantasia, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(jLabel11))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tField_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tField_Cidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(jLabel12))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(cb_Estado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel10))
                            .addComponent(tField_Bairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(jLabel8))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabel5))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel13))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jFormattedTextFieldCep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(tField_Numero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(tField_nomeFantasia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jFormattedTextFieldDataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(tField_TipoLogradouro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel7))
                    .addComponent(tField_Logradouro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabel9))
                    .addComponent(tField_Complemento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46)
                .addComponent(jButton_restaurar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTableClientesMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableClientesMouseReleased
        try {
            int indexSelecionado = jTableClientes.getSelectedRow();
            if (indexSelecionado >= 0) {//   * o clique foi válido
                String cpfCnpjClienteEscolhido = String.valueOf(jTableClientes.getValueAt(indexSelecionado, 1));
                PessoaFisica pessoaFisica = new ManipulaBancoPessoaFisica().buscarNosExcluidos(new ManipulaBancoPessoaFisica().buscarNosExcluidos(cpfCnpjClienteEscolhido));

                if (pessoaFisica != null) {//   * é uma pessoa fisica
                    tField_nome_razaoSocial.setText(pessoaFisica.getNome());
                    tField_email.setText(pessoaFisica.getEmail());
                    jFormattedTextFieldTelefoneCelular.setText(pessoaFisica.getTelefone()[0]);
                    jFormattedTextFieldTelefoneComercial.setText(pessoaFisica.getTelefone()[1]);
                    jFormattedTextFieldTelefoneResidencial.setText(pessoaFisica.getTelefone()[2]);
                    jFormattedTextFieldDataNascimento.setText(new SimpleDateFormat("dd/MM/yyyy").format(pessoaFisica.getDataNascimento()));
                    jFormattedTextFieldCpf.setText(pessoaFisica.getCpf());

                    Endereco e = pessoaFisica.getEndereco();
                    tField_Bairro.setText(e.getBairro());
                    jFormattedTextFieldCep.setText(e.getCEP());
                    tField_Cidade.setText(e.getCidade());
                    tField_Complemento.setText(e.getComplemento());
                    tField_Logradouro.setText(e.getLogradouro());
                    tField_Numero.setText(e.getNumero());
                    tField_TipoLogradouro.setText(e.getTipoLogradouro());
                    cb_Estado.setSelectedItem(e.getEstado());

                    jRadioButton_PessoaFisica.setSelected(true);
                    jRadioButton_PessoaFisicaActionPerformed(null);
                } else {//    * é uma pessoa jurídica
                    PessoaJuridica pessoaJuridica = new ManipulaBancoPessoaJuridica().buscarNosExcluidos(new ManipulaBancoPessoaJuridica().buscarNosExcluidos(cpfCnpjClienteEscolhido));
                    if (pessoaJuridica != null) {
                        tField_nome_razaoSocial.setText(pessoaJuridica.getRazaoSocial());
                        tField_email.setText(pessoaJuridica.getEmail());
                        jFormattedTextFieldTelefoneCelular.setText(pessoaJuridica.getTelefone()[0]);
                        jFormattedTextFieldTelefoneComercial.setText(pessoaJuridica.getTelefone()[1]);
                        jFormattedTextFieldTelefoneResidencial.setText(pessoaJuridica.getTelefone()[2]);
                        tField_nomeFantasia.setText(pessoaJuridica.getNomeFantasia());
                        jFormattedTextFieldCnpj.setText(pessoaJuridica.getCnpj());

                        Endereco e = pessoaJuridica.getEndereco();
                        tField_Bairro.setText(e.getBairro());
                        jFormattedTextFieldCep.setText(e.getCEP());
                        tField_Cidade.setText(e.getCidade());
                        tField_Complemento.setText(e.getComplemento());
                        tField_Logradouro.setText(e.getLogradouro());
                        tField_Numero.setText(e.getNumero());
                        tField_TipoLogradouro.setText(e.getTipoLogradouro());
                        cb_Estado.setSelectedItem(e.getEstado());

                        jRadioButton_PessoaJuridicaActionPerformed(null);
                        jRadioButton_PessoaJuridica.setSelected(true);
                    } else {//    * não existe no banco
                        throw new IllegalStateException("O clliente\"" + cpfCnpjClienteEscolhido + "\", não existe nem no banco de dados de pessoa física nem de pessoa jurídica");
                    }
                }
            }
        } catch (InvalidInputException | SystemErrorException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }//GEN-LAST:event_jTableClientesMouseReleased

    private void jRadioButton_PessoaFisicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton_PessoaFisicaActionPerformed
        controlaTela("Pessoa Fisica");
    }//GEN-LAST:event_jRadioButton_PessoaFisicaActionPerformed

    private void jRadioButton_PessoaJuridicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton_PessoaJuridicaActionPerformed
        controlaTela("Pessoa Juridica");
    }//GEN-LAST:event_jRadioButton_PessoaJuridicaActionPerformed

    private void jButton_restaurarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_restaurarActionPerformed
        try {
            int indexSelecionado = jTableClientes.getSelectedRow();
            if (indexSelecionado < 0) {//    * clique inválido
                return;
            }
            String nome = "" + jTableClientes.getValueAt(indexSelecionado, 0);
            if (JOptionPane.showConfirmDialog(rootPane, "Restaurar cliente: " + nome) == 0) {
                String cpfCnpjClienteEscolhido = String.valueOf(jTableClientes.getValueAt(indexSelecionado, 1));
                PessoaFisica pessoaFisica = new ManipulaBancoPessoaFisica().buscarNosExcluidos(new ManipulaBancoPessoaFisica().buscarNosExcluidos(cpfCnpjClienteEscolhido));
                if (pessoaFisica != null) {
                    pessoaFisica.setCadastroAtivo(true);
                    new ManipulaBancoPessoaFisica().incluir(pessoaFisica);
                } else {//    * é pessoa jurídica
                    PessoaJuridica pessoaJuridica = new ManipulaBancoPessoaJuridica().buscarNosExcluidos(new ManipulaBancoPessoaJuridica().buscarNosExcluidos(cpfCnpjClienteEscolhido));
                    if (pessoaJuridica == null) {
                        throw new IllegalStateException("O cliente: \"" + nome + "\" não existe nem no banco de dados de Pessoa física nem no banco de pessoa jurídica");
                    }
                    pessoaJuridica.setCadastroAtivo(true);
                    new ManipulaBancoPessoaJuridica().incluir(pessoaJuridica);
                }
            }
        } catch (InvalidInputException | SystemErrorException erro) {
            erro.printStackTrace();
            JOptionPane.showMessageDialog(this, erro.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Falha completa do sistema, nem eu sei o que aconteceu");

        }
    }//GEN-LAST:event_jButton_restaurarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup_TipoPessoa;
    private javax.swing.JComboBox<EstadosBrazil> cb_Estado;
    private javax.swing.JButton jButton_restaurar;
    private javax.swing.JFormattedTextField jFormattedTextFieldCep;
    private javax.swing.JFormattedTextField jFormattedTextFieldCnpj;
    private javax.swing.JFormattedTextField jFormattedTextFieldCpf;
    private javax.swing.JFormattedTextField jFormattedTextFieldDataNascimento;
    private javax.swing.JFormattedTextField jFormattedTextFieldTelefoneCelular;
    private javax.swing.JFormattedTextField jFormattedTextFieldTelefoneComercial;
    private javax.swing.JFormattedTextField jFormattedTextFieldTelefoneResidencial;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelDataNasc_NomeFantasia;
    private javax.swing.JLabel jLabelNome_razaoSocial;
    private javax.swing.JLabel jLabel_CPF_CNPJ;
    private javax.swing.JRadioButton jRadioButton_PessoaFisica;
    private javax.swing.JRadioButton jRadioButton_PessoaJuridica;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableClientes;
    private javax.swing.JTextField tField_Bairro;
    private javax.swing.JTextField tField_Cidade;
    private javax.swing.JTextField tField_Complemento;
    private javax.swing.JTextField tField_Logradouro;
    private javax.swing.JTextField tField_Numero;
    private javax.swing.JTextField tField_TipoLogradouro;
    private javax.swing.JTextField tField_email;
    private javax.swing.JTextField tField_nomeFantasia;
    private javax.swing.JTextField tField_nome_razaoSocial;
    // End of variables declaration//GEN-END:variables
}
