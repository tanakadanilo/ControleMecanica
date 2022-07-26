/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package tela;

import enumerations.EstadosBrazil;
import exceptions.InvalidInputException;
import exceptions.SystemErrorException;
import java.awt.HeadlessException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import modelos.Funcionario;
import modelos.auxiliares.Endereco;
import persistencia.ManipulaBancoFuncionario;

/**
 *
 * @author tanak
 */
public class TelaListaFuncionario extends javax.swing.JInternalFrame {

    /**
     * Creates new form TelaListaFuncionario
     */
    public TelaListaFuncionario() {
        initComponents();
        loadComboBox();
        loadTableFuncionarios();

        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(jTableFuncionarios.getModel());
        jTableFuncionarios.setRowSorter(sorter);
    }

    private void loadComboBox() {
        cb_Estado.setModel(new DefaultComboBoxModel<>(EstadosBrazil.values()));
    }

    private void loadTableFuncionarios() {
        try {
            ArrayList<Funcionario> listaFuncionarios = new ManipulaBancoFuncionario().buscarTodos();
            DefaultTableModel table = (DefaultTableModel) jTableFuncionarios.getModel();
            table.setRowCount(0);// * apagando linhas para não duplicar os dados da tabela
            if (listaFuncionarios == null || listaFuncionarios.isEmpty()) {
                return;
            }
            for (Funcionario f : listaFuncionarios) {
                String[] dados = new String[jTableFuncionarios.getColumnCount()];
                dados[0] = "" + f.getMatricula();
                dados[1] = f.getNome();
                dados[2] = f.getCpf();
                dados[3] = f.getEspecialidade();
                table.addRow(dados);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }

    private void loadTableFuncionarios(ArrayList<Funcionario> listaFunc) {
        try {
            DefaultTableModel table = (DefaultTableModel) jTableFuncionarios.getModel();
            table.setRowCount(0);// * apagando linhas para não duplicar os listaFunc da tabela

            for (Funcionario f : listaFunc) {
                String[] dados = new String[jTableFuncionarios.getColumnCount()];
                dados[0] = "" + f.getMatricula();
                dados[1] = f.getNome();
                dados[2] = f.getCpf();
                dados[3] = f.getEspecialidade();
                table.addRow(dados);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }

    private ArrayList<Funcionario> filtrarPorNome(ArrayList<Funcionario> listaCompleta, String filtro) {

        ArrayList<Funcionario> listaFuncFiltrada = new ArrayList<>();
        for (Funcionario f : listaCompleta) {
            if (f.getNome().toUpperCase().contains(filtro.toUpperCase())) {//   * ignorando maiusculo e minusculo
                listaFuncFiltrada.add(f);
            }
        }
        return listaFuncFiltrada;
    }

    private ArrayList<Funcionario> filtrarPorCPF(ArrayList<Funcionario> listaCompleta, String filtro) {

        ArrayList<Funcionario> listaFuncFiltrada = new ArrayList<>();
        for (Funcionario f : listaCompleta) {
            if (f.getCpf().toUpperCase().contains(filtro.toUpperCase())) {//   * ignorando maiusculo e minusculo
                listaFuncFiltrada.add(f);
            }
        }
        return listaFuncFiltrada;
    }

    private ArrayList<Funcionario> filtrarPorMatricula(ArrayList<Funcionario> listaCompleta, String filtro) {

        ArrayList<Funcionario> listaFuncFiltrada = new ArrayList<>();
        for (Funcionario f : listaCompleta) {
            if (("" + f.getMatricula()).toUpperCase().contains(filtro.toUpperCase())) {//   * ignorando maiusculo e minusculo
                listaFuncFiltrada.add(f);
            }
        }
        return listaFuncFiltrada;
    }

    private ArrayList<Funcionario> filtrarPorEspecialidade(ArrayList<Funcionario> listaCompleta, String filtro) {

        ArrayList<Funcionario> listaFuncFiltrada = new ArrayList<>();
        for (Funcionario f : listaCompleta) {
            if (f.getEspecialidade().toUpperCase().contains(filtro.toUpperCase())) {//   * ignorando maiusculo e minusculo
                listaFuncFiltrada.add(f);
            }
        }
        return listaFuncFiltrada;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        tField_Logradouro = new javax.swing.JTextField();
        cb_Estado = new javax.swing.JComboBox<>();
        tField_Numero = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jFormattedTextFieldMatricula = new javax.swing.JFormattedTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        tField_Complemento = new javax.swing.JTextField();
        tField_TipoLogradouro = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        tField_Bairro = new javax.swing.JTextField();
        jTextFieldEspecialidade = new javax.swing.JTextField();
        jButtonBuscar = new javax.swing.JButton();
        jComboBoxTipoBuscar = new javax.swing.JComboBox<>();
        tField_cpf = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        tField_Cidade = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jButtonEditar = new javax.swing.JButton();
        jFormattedTextFieldSalarioMes = new javax.swing.JFormattedTextField();
        jButtonRemover = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableFuncionarios = new javax.swing.JTable();
        jFormattedTextFieldSalarioHora = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        tField_email = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jButtonAdicionar = new javax.swing.JButton();
        tField_nome = new javax.swing.JTextField();
        jFormattedTextFieldDataNascimento = new javax.swing.JFormattedTextField();
        jFormattedTextFieldTelefone = new javax.swing.JFormattedTextField();
        jFormatedField_cep = new javax.swing.JFormattedTextField();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setText("Matricula: ");

        jLabel9.setText("Numero:");

        jFormattedTextFieldMatricula.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));

        jLabel10.setText("Complemento:");

        jLabel16.setText("CEP:");

        jLabel1.setText("Especialidade: ");

        jLabel4.setText("CPF:");

        jTextFieldEspecialidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldEspecialidadeActionPerformed(evt);
            }
        });

        jButtonBuscar.setText("Buscar");
        jButtonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarActionPerformed(evt);
            }
        });

        jComboBoxTipoBuscar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nome", "CPF", "Matricula", "Especialidade" }));

        jLabel2.setText("Salario/hora: ");

        jLabel14.setText("Estado:");

        jButtonEditar.setBackground(new java.awt.Color(0, 204, 255));
        jButtonEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-lápis-30.png"))); // NOI18N
        jButtonEditar.setText("Editar");
        jButtonEditar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditarActionPerformed(evt);
            }
        });

        jFormattedTextFieldSalarioMes.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));

        jButtonRemover.setBackground(new java.awt.Color(0, 204, 255));
        jButtonRemover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-excluir-30.png"))); // NOI18N
        jButtonRemover.setText("Remover");
        jButtonRemover.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRemoverActionPerformed(evt);
            }
        });

        jLabel15.setText("Telefone: ");

        jLabel7.setText("Data de Nascimento:");

        jTableFuncionarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Matricula", "Nome", "CPF", "Especialidade"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableFuncionarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTableFuncionariosMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTableFuncionarios);

        jFormattedTextFieldSalarioHora.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jFormattedTextFieldSalarioHora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextFieldSalarioHoraActionPerformed(evt);
            }
        });

        jLabel8.setText("Logradouro");

        jLabel6.setText("Email:");

        jLabel3.setText("Salario/mês:");

        jLabel11.setText("Bairro:");

        jLabel17.setText("Tipo Logradouro:");

        jLabel12.setText("Nome: ");

        jLabel13.setText("Cidade:");

        jButtonAdicionar.setBackground(new java.awt.Color(0, 204, 255));
        jButtonAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-mais-2-matemática-verde-30.png"))); // NOI18N
        jButtonAdicionar.setText("Adicionar");
        jButtonAdicionar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAdicionarActionPerformed(evt);
            }
        });

        try {
            jFormattedTextFieldDataNascimento.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        try {
            jFormattedTextFieldTelefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) #####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        try {
            jFormatedField_cep.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#####-###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(tField_Bairro)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel8)
                        .addGap(10, 10, 10)
                        .addComponent(tField_Logradouro, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(42, 42, 42)
                        .addComponent(tField_nome)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBoxTipoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(tField_Cidade, javax.swing.GroupLayout.PREFERRED_SIZE, 434, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(16, 16, 16)
                                .addComponent(cb_Estado, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(43, 43, 43)
                                .addComponent(tField_cpf, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(jLabel7)
                                .addGap(18, 18, 18)
                                .addComponent(jFormattedTextFieldDataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jFormattedTextFieldTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(68, 68, 68)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tField_email)
                            .addComponent(tField_TipoLogradouro, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButtonAdicionar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(132, 132, 132)
                                .addComponent(jButtonEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel16))
                                .addGap(24, 24, 24)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jFormatedField_cep, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(tField_Numero, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jFormattedTextFieldMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jFormattedTextFieldSalarioHora, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(61, 61, 61)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jFormattedTextFieldSalarioMes)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextFieldEspecialidade))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel10)
                                    .addGap(1, 1, 1)
                                    .addComponent(tField_Complemento, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jButtonRemover, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tField_nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxTipoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonBuscar)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tField_cpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4))
                    .addComponent(jLabel7)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jFormattedTextFieldDataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jFormattedTextFieldTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel6)
                    .addComponent(tField_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(tField_TipoLogradouro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cb_Estado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel14)
                        .addComponent(tField_Cidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel13))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tField_Bairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(tField_Logradouro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel11)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tField_Numero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)
                            .addComponent(tField_Complemento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addComponent(jFormatedField_cep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldEspecialidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedTextFieldMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(jFormattedTextFieldSalarioMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jFormattedTextFieldSalarioHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonRemover, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditarActionPerformed
        try {
            int indexSelecionado = jTableFuncionarios.getSelectedRow();
            if (indexSelecionado < 0) {
                throw new InvalidInputException("Selecione, na tabela, qual funcionario deseja editar");
            }
            String matricula = "" + jTableFuncionarios.getValueAt(jTableFuncionarios.getSelectedRow(), 0);
            int id = new ManipulaBancoFuncionario().buscar(matricula);

            String nome = tField_nome.getText();
            String cpf = tField_cpf.getText();
            String[] telefone = new String[3];
            telefone[0] = jFormattedTextFieldTelefone.getText();
            telefone[1] = jFormattedTextFieldTelefone.getText();
            telefone[2] = jFormattedTextFieldTelefone.getText();
            String tipoLogradouro = tField_TipoLogradouro.getText();
            String logradoro = tField_Logradouro.getText();
            String numero = tField_Numero.getText();
            String complemento = tField_Complemento.getText();
            String bairro = tField_Bairro.getText();
            String cidade = tField_Cidade.getText();
            String estado = cb_Estado.getSelectedItem().toString();
            String CEP = jFormatedField_cep.getText();
            String auxDataNascimento = jFormattedTextFieldDataNascimento.getText();
            Date dataNascimento = new SimpleDateFormat("dd/MM/yyyy").parse(auxDataNascimento);
            String email = tField_email.getText();

            EstadosBrazil eb = Enum.valueOf(EstadosBrazil.class,
                    estado);

            Endereco endereco = new Endereco(tipoLogradouro, logradoro, numero, complemento, bairro, cidade, eb, CEP);

            String especialidade = jTextFieldEspecialidade.getText();
            double salarioHora = Double.parseDouble(jFormattedTextFieldSalarioHora.getText().replace(",", "."));//  * substituindo virgula por ponto
            double salarioMes = Double.parseDouble(jFormattedTextFieldSalarioMes.getText().replace(",", "."));  //  * substituindo virgula por ponto
            Funcionario f = new Funcionario(especialidade, salarioMes, salarioHora, Integer.parseInt(matricula), nome, cpf, dataNascimento, email, endereco, telefone);

            new ManipulaBancoFuncionario().editar(id, f);
            loadTableFuncionarios();
            JOptionPane.showMessageDialog(rootPane, "Editado");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }//GEN-LAST:event_jButtonEditarActionPerformed

    private void jButtonRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRemoverActionPerformed
        try {
            int indexSelecionado = jTableFuncionarios.getSelectedRow();
            if (indexSelecionado < 0) {
                throw new Exception("Selecione, na tabela, qual Funcionario deseja excluir");
            }
            String matricula = "" + jTableFuncionarios.getValueAt(jTableFuncionarios.getSelectedRow(), 0);
            int id = new ManipulaBancoFuncionario().buscar(matricula);
            new ManipulaBancoFuncionario().remover(id);
            loadTableFuncionarios();
            JOptionPane.showMessageDialog(rootPane, "Funcionario: \"" + matricula + "\" removido com sucesso");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }//GEN-LAST:event_jButtonRemoverActionPerformed

    private void jFormattedTextFieldSalarioHoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextFieldSalarioHoraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextFieldSalarioHoraActionPerformed

    private void jButtonAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAdicionarActionPerformed

        try {
            String nome = tField_nome.getText();
            String cpf = tField_cpf.getText();
            String[] telefone = new String[3];
            telefone[0] = jFormattedTextFieldTelefone.getText();
            telefone[1] = jFormattedTextFieldTelefone.getText();
            telefone[2] = jFormattedTextFieldTelefone.getText();
            String tipoLogradouro = tField_TipoLogradouro.getText();
            String logradoro = tField_Logradouro.getText();
            String numero = tField_Numero.getText();
            String complemento = tField_Complemento.getText();
            String bairro = tField_Bairro.getText();
            String cidade = tField_Cidade.getText();
            String estado = cb_Estado.getSelectedItem().toString();
            String CEP = jFormatedField_cep.getText();
            String auxDataNascimento = jFormattedTextFieldDataNascimento.getText();
            Date dataNascimento = new SimpleDateFormat("dd/MM/yyyy").parse(auxDataNascimento);
            String email = tField_email.getText();

            EstadosBrazil eb = Enum.valueOf(EstadosBrazil.class,
                    estado);

            Endereco endereco = new Endereco(tipoLogradouro, logradoro, numero, complemento, bairro, cidade, eb, CEP);

            String especialidade = jTextFieldEspecialidade.getText();
            String salarioHoraString = jFormattedTextFieldSalarioHora.getText().replace(".", "").replace(",", ".");//  * apagando ponto e substituindo virgula por ponto
            String salarioMesString = jFormattedTextFieldSalarioMes.getText().replace(".", "").replace(",", ".");//  * apagando ponto e substituindo virgula por ponto
            double salarioHora = Double.parseDouble(salarioHoraString);
            double salarioMes = Double.parseDouble(salarioMesString);
            int matricula = Integer.parseInt(jFormattedTextFieldMatricula.getText().replace(".", ""));
            Funcionario f = new Funcionario(especialidade, salarioMes, salarioHora, matricula, nome, cpf, dataNascimento, email, endereco, telefone);

            new ManipulaBancoFuncionario().incluir(f);
            loadTableFuncionarios();
            JOptionPane.showMessageDialog(rootPane, "Cadastrado com sucesso!");
        } catch (InvalidInputException | SystemErrorException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (NumberFormatException | ParseException e) {
            JOptionPane.showMessageDialog(this, "Informe apenas numeros, sem letras nem simbolos");
        }
    }//GEN-LAST:event_jButtonAdicionarActionPerformed

    private void jTextFieldEspecialidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldEspecialidadeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldEspecialidadeActionPerformed

    private void jButtonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarActionPerformed
        try {
            ArrayList<Funcionario> listaFunc = new ManipulaBancoFuncionario().buscarTodos();
            ArrayList<Funcionario> listaFiltrada = null;
            if (listaFunc != null && !listaFunc.isEmpty()) {
                switch (jComboBoxTipoBuscar.getSelectedIndex()) {
                    case 0://   * nome
                        listaFiltrada = filtrarPorNome(listaFunc, tField_nome.getText());
                        break;
                    case 1://   * matricula
                        listaFiltrada = filtrarPorCPF(listaFunc, tField_cpf.getText());
                        break;
                    case 2://   * matricula
                        listaFiltrada = filtrarPorMatricula(listaFunc, jFormattedTextFieldMatricula.getText().replace(".", ""));
                        break;
                    case 3://   * especialidade
                        listaFiltrada = filtrarPorEspecialidade(listaFunc, jTextFieldEspecialidade.getText());
                        break;
                }
                loadTableFuncionarios(listaFiltrada);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }//GEN-LAST:event_jButtonBuscarActionPerformed

    private void jTableFuncionariosMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableFuncionariosMouseReleased
        try {
            int index = jTableFuncionarios.getSelectedRow();
            if (index < 0) {///  * cllique inválido
                return;
            }
            String matricula = "" + jTableFuncionarios.getValueAt(index, 0);
            ManipulaBancoFuncionario mb = new ManipulaBancoFuncionario();
            int id = mb.buscar(matricula);
            Funcionario f = mb.buscar(id);

            if (f == null) {
                System.out.println(index);
                System.out.println(id);
                System.out.println(matricula);
                throw new Exception("Funcionário não encontrado");
            }
            Endereco e = f.getEndereco();
            if (e == null) {
                throw new Exception("Endereço do Funcionário não encontrado");
            }
            //  * settando dados de endereco
            tField_Bairro.setText(e.getBairro());
            jFormatedField_cep.setText(e.getCEP());
            tField_Cidade.setText(e.getCidade());
            tField_Complemento.setText(e.getComplemento());
            tField_Logradouro.setText(e.getLogradouro());
            tField_Numero.setText(e.getNumero());
            tField_TipoLogradouro.setText(e.getTipoLogradouro());
            cb_Estado.setSelectedItem(e.getEstado());

            //  * settando dados do funcionario
            tField_cpf.setText(f.getCpf());
            jFormattedTextFieldDataNascimento.setText(new SimpleDateFormat("dd/MM/yyyy").format(f.getDataNascimento()));
            tField_email.setText(f.getEmail());
            tField_nome.setText(f.getNome());
            jFormattedTextFieldTelefone.setText(f.getTelefone()[0]);
            jTextFieldEspecialidade.setText(f.getEspecialidade());
            jFormattedTextFieldMatricula.setText(matricula);
            jFormattedTextFieldSalarioHora.setText(String.format("%.2f", f.getSalariohora()));
            jFormattedTextFieldSalarioMes.setText(String.format("%.2f", f.getSalarioMensal()));

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }//GEN-LAST:event_jTableFuncionariosMouseReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<EstadosBrazil> cb_Estado;
    private javax.swing.JButton jButtonAdicionar;
    private javax.swing.JButton jButtonBuscar;
    private javax.swing.JButton jButtonEditar;
    private javax.swing.JButton jButtonRemover;
    private javax.swing.JComboBox<String> jComboBoxTipoBuscar;
    private javax.swing.JFormattedTextField jFormatedField_cep;
    private javax.swing.JFormattedTextField jFormattedTextFieldDataNascimento;
    private javax.swing.JFormattedTextField jFormattedTextFieldMatricula;
    private javax.swing.JFormattedTextField jFormattedTextFieldSalarioHora;
    private javax.swing.JFormattedTextField jFormattedTextFieldSalarioMes;
    private javax.swing.JFormattedTextField jFormattedTextFieldTelefone;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableFuncionarios;
    private javax.swing.JTextField jTextFieldEspecialidade;
    private javax.swing.JTextField tField_Bairro;
    private javax.swing.JTextField tField_Cidade;
    private javax.swing.JTextField tField_Complemento;
    private javax.swing.JTextField tField_Logradouro;
    private javax.swing.JTextField tField_Numero;
    private javax.swing.JTextField tField_TipoLogradouro;
    private javax.swing.JTextField tField_cpf;
    private javax.swing.JTextField tField_email;
    private javax.swing.JTextField tField_nome;
    // End of variables declaration//GEN-END:variables
}
