// LancamentoNota.java
package main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
// As importações de AWT e iText foram ajustadas para evitar conflitos
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.awt.Desktop;
import java.util.ArrayList;
import java.util.List;
// iText imports
import com.itextpdf.text.BaseColor; // <-- FIX 2: Importado a classe de cor correta do iText
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font; // Importa a fonte do iText por padrão
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class LancamentoNota extends JFrame {
    // --- Componentes da Interface ---
    private JRadioButton rbtEntrada = new JRadioButton("Entrada", true);
    private JRadioButton rbtSaida = new JRadioButton("Saída");

    private JComboBox<fornecedores> cbxFornecedor;
    private JComboBox<clientes> cbxCliente;

    // Campos para inserção de itens
    private JTextField txtCodigo = new JTextField(15);
    private JLabel lblProdutoInfo = new JLabel("Produto: (Aguardando leitura do código)");
    private JTextField txtQuantidade = new JTextField(5);
    
    // Tabela de itens
    private DefaultTableModel tableModel;
    private JTable tblItens;

    // Rodapé
    private JButton btnFinalizar = new JButton("Finalizar Nota");
    private JLabel lblTotalNota = new JLabel("Total da Nota: R$ 0.00");
    private BigDecimal valorTotalNota = BigDecimal.ZERO;

    // --- DAOs e controle ---
    private fornecedoresDAO fornecedorDAO = new fornecedoresDAO();
    private clientesDAO clienteDAO = new clientesDAO();
    private produtoDAO produtoDAO = new produtoDAO();
    private notaDAO notaDAO = new notaDAO();

    // Variável para guardar o produto encontrado pelo código de barras
    private produtos produtoAtual = null;

    public LancamentoNota() {
        super("Lançamento de Notas");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        initTopo();
        initCentro();
        initRodape();
        
        setVisible(true);
        txtCodigo.requestFocusInWindow(); // Foco inicial no código de barras
    }

    private void initTopo() {
        // --- Painel Superior (Seleção de Tipo, Cliente/Fornecedor) ---
        JPanel pnlSelecao = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        
        ButtonGroup grupo = new ButtonGroup();
        grupo.add(rbtEntrada);
        grupo.add(rbtSaida);
        pnlSelecao.add(rbtEntrada);
        pnlSelecao.add(rbtSaida);

        cbxFornecedor = new JComboBox<>(fornecedorDAO.listarFornecedores().toArray(new fornecedores[0]));
        cbxCliente = new JComboBox<>(clienteDAO.listarClientes().toArray(new clientes[0]));
        cbxCliente.setEnabled(false);

        pnlSelecao.add(new JLabel("Fornecedor:"));
        pnlSelecao.add(cbxFornecedor);
        pnlSelecao.add(new JLabel("Cliente:"));
        pnlSelecao.add(cbxCliente);

        rbtEntrada.addActionListener(e -> {
            cbxFornecedor.setEnabled(true);
            cbxCliente.setEnabled(false);
        });
        rbtSaida.addActionListener(e -> {
            cbxFornecedor.setEnabled(false);
            cbxCliente.setEnabled(true);
        });

        // --- Painel de Inserção de Itens (a parte vermelha da sua imagem) ---
        JPanel pnlInsercao = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        pnlInsercao.setBorder(BorderFactory.createTitledBorder("Adicionar Item"));

        pnlInsercao.add(new JLabel("Código de Barras:"));
        pnlInsercao.add(txtCodigo);
        pnlInsercao.add(lblProdutoInfo);
        pnlInsercao.add(new JLabel("Quantidade:"));
        pnlInsercao.add(txtQuantidade);

        // --- Container para os painéis do topo ---
        JPanel topo = new JPanel(new BorderLayout());
        topo.add(pnlSelecao, BorderLayout.NORTH);
        topo.add(pnlInsercao, BorderLayout.CENTER);
        
        add(topo, BorderLayout.NORTH);

        // --- Lógica dos Eventos ---
        // Pressionar ENTER no Código de Barras
        txtCodigo.addActionListener(e -> buscarProduto());
        
        // Pressionar ENTER na Quantidade
        txtQuantidade.addActionListener(e -> adicionarItemNaTabela());
    }
    
    private void buscarProduto() {
        String codigo = txtCodigo.getText().trim();
        if (codigo.isEmpty()) {
            return; // Não faz nada se o campo estiver vazio
        }
        
        produtoAtual = produtoDAO.buscarPorCodigo(codigo);
        
        if (produtoAtual != null) {
            lblProdutoInfo.setText(String.format("Produto: %s | Preço: R$ %.2f", produtoAtual.GetNome(), produtoAtual.GetPreco()));
            lblProdutoInfo.setForeground(Color.BLUE);
            txtQuantidade.setText("1"); // Sugere a quantidade 1
            txtQuantidade.requestFocusInWindow();
            txtQuantidade.selectAll();
        } else {
            JOptionPane.showMessageDialog(this, "Produto não encontrado com este código de barras.", "Erro", JOptionPane.ERROR_MESSAGE);
            produtoAtual = null;
            lblProdutoInfo.setText("Produto: (Aguardando leitura do código)");
            lblProdutoInfo.setForeground(Color.RED);
            txtCodigo.setText("");
            txtCodigo.requestFocusInWindow();
        }
    }

    private void adicionarItemNaTabela() {
        if (produtoAtual == null) {
            JOptionPane.showMessageDialog(this, "Primeiro, leia o código de barras de um produto válido.", "Atenção", JOptionPane.WARNING_MESSAGE);
            txtCodigo.requestFocusInWindow();
            return;
        }

        try {
            int qtd = Integer.parseInt(txtQuantidade.getText().trim());
            if (qtd <= 0) {
                JOptionPane.showMessageDialog(this, "A quantidade deve ser um número positivo.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            BigDecimal precoUnit = new BigDecimal(Float.toString(produtoAtual.GetPreco()));
            BigDecimal subtotal = precoUnit.multiply(BigDecimal.valueOf(qtd));

            // Adiciona a linha na tabela
            tableModel.addRow(new Object[]{
                produtoAtual.getId(),       // Coluna 0 (oculta)
                produtoAtual.GetNome(),     // Coluna 1
                qtd,                        // Coluna 2
                precoUnit,                  // Coluna 3
                subtotal                    // Coluna 4
            });

            // Atualiza o valor total da nota
            valorTotalNota = valorTotalNota.add(subtotal);
            lblTotalNota.setText(String.format("Total da Nota: R$ %.2f", valorTotalNota));

            // Reseta os campos para o próximo item
            resetarCamposItem();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Quantidade inválida. Por favor, insira um número.", "Erro", JOptionPane.ERROR_MESSAGE);
            txtQuantidade.requestFocusInWindow();
            txtQuantidade.selectAll();
        }
    }
    
    private void resetarCamposItem() {
        produtoAtual = null;
        txtCodigo.setText("");
        lblProdutoInfo.setText("Produto: (Aguardando leitura do código)");
        lblProdutoInfo.setForeground(Color.BLACK);
        txtQuantidade.setText("");
        txtCodigo.requestFocusInWindow();
    }

    private void initCentro() {
        // Colunas: ID (oculto), Produto, Qtde, Valor Unit., Subtotal
        tableModel = new DefaultTableModel(
            new String[]{ "ID", "Produto", "Qtde", "Valor Unit.", "Subtotal" }, 0
        ) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
            // Define o tipo de cada coluna para ordenação correta
            @Override public Class<?> getColumnClass(int columnIndex) {
                switch(columnIndex) {
                    case 0: return Integer.class;
                    case 1: return String.class;
                    case 2: return Integer.class;
                    case 3: return BigDecimal.class;
                    case 4: return BigDecimal.class;
                    default: return Object.class;
                }
            }
        };
        tblItens = new JTable(tableModel);
        
        // --- Ocultar a coluna de ID ---
        TableColumn idColumn = tblItens.getColumnModel().getColumn(0);
        idColumn.setMinWidth(0);
        idColumn.setMaxWidth(0);
        idColumn.setPreferredWidth(0);

        add(new JScrollPane(tblItens), BorderLayout.CENTER);
    }

    private void initRodape() {
        btnFinalizar.addActionListener(e -> finalizarNota());
        
        JPanel rodape = new JPanel(new BorderLayout(10, 10));
        // FIX 1: Usando o nome completo da classe java.awt.Font para evitar conflito com a fonte do iText
        lblTotalNota.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 16));
        lblTotalNota.setHorizontalAlignment(SwingConstants.LEFT);

        rodape.add(lblTotalNota, BorderLayout.CENTER);
        rodape.add(btnFinalizar, BorderLayout.EAST);
        rodape.setBorder(BorderFactory.createEmptyBorder(5,10,5,10)); // Margem
        add(rodape, BorderLayout.SOUTH);
    }

    private void finalizarNota() {
        if (tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "A nota está vazia. Adicione pelo menos um item.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            nota n = new nota();
            n.setData(LocalDateTime.now());
            n.setTipo(rbtEntrada.isSelected() ? 'E' : 'S');
            
            if (rbtEntrada.isSelected()) {
                n.setFornecedorId(((fornecedores)cbxFornecedor.getSelectedItem()).GetId());
                n.setClienteId(null);
            } else {
                n.setClienteId(((clientes)cbxCliente.getSelectedItem()).getId());
                n.setFornecedorId(null);
            }

            List<notaitem> itens = new ArrayList<>();
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                notaitem it = new notaitem();
                it.setProdutoId((Integer) tableModel.getValueAt(i, 0)); // Pega o ID da coluna oculta
                it.setQuantidade((Integer) tableModel.getValueAt(i, 2));
                it.setValorUnitario((BigDecimal) tableModel.getValueAt(i, 3));
                itens.add(it);
            }
            n.setValorTotal(valorTotalNota); // Usa o total já calculado
            n.setItens(itens);

            int idNota = notaDAO.inserirNota(n);
            if (idNota > 0) {
                n.setId(idNota); // Define o ID retornado na nota
                notaDAO.inserirItens(idNota, itens);
                gerarPdf(n); // Passa a nota completa para o PDF

                JOptionPane.showMessageDialog(this, "Nota #" + idNota + " lançada com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                 JOptionPane.showMessageDialog(this, "Falha ao gravar a nota no banco de dados.", "Erro Crítico", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao finalizar a nota: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void gerarPdf(nota n) throws Exception {
        String file = "nota_" + n.getId() + ".pdf";
        Document doc = new Document(PageSize.A4, 36, 36, 64, 36);
        PdfWriter.getInstance(doc, new FileOutputStream(file));
        doc.open();
        
        Font fTitle = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
        Font fHead = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
        Font fText = new Font(Font.FontFamily.HELVETICA, 10);
        Font fTotal = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);

        Paragraph title = new Paragraph("NOTA FISCAL DE " + (n.getTipo() == 'E' ? "ENTRADA" : "SAÍDA"), fTitle);
        title.setAlignment(Element.ALIGN_CENTER);
        doc.add(title);
        doc.add(Chunk.NEWLINE);
        
        // --- Cabeçalho da Nota ---
        PdfPTable tblHead = new PdfPTable(2);
        tblHead.setWidthPercentage(100f);
        tblHead.setWidths(new float[]{1f, 3f});
        
        tblHead.addCell(new PdfPCell(new Phrase("Nota Nº:", fHead)));
        tblHead.addCell(new PdfPCell(new Phrase(String.valueOf(n.getId()), fText)));
        tblHead.addCell(new PdfPCell(new Phrase("Data de Emissão:", fHead)));
        tblHead.addCell(new PdfPCell(new Phrase(n.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")), fText)));
        
        if (n.getTipo() == 'E') {
            tblHead.addCell(new PdfPCell(new Phrase("Fornecedor:", fHead)));
            fornecedores f = (fornecedores) cbxFornecedor.getSelectedItem();
            tblHead.addCell(new PdfPCell(new Phrase(f.GetNomeFantasia(), fText)));
        } else {
            tblHead.addCell(new PdfPCell(new Phrase("Cliente:", fHead)));
            clientes c = (clientes) cbxCliente.getSelectedItem();
            tblHead.addCell(new PdfPCell(new Phrase(c.GetNome(), fText)));
        }
        doc.add(tblHead);
        doc.add(Chunk.NEWLINE);

        // --- Itens da Nota ---
        doc.add(new Paragraph("ITENS DA NOTA", fHead));
        PdfPTable tblPdf = new PdfPTable(4);
        tblPdf.setWidthPercentage(100f);
        tblPdf.setWidths(new float[]{4f, 1f, 2f, 2f});
        String[] hdr = {"Produto", "Qtde", "Valor Unit.", "Subtotal"};
        for (String h : hdr) {
            PdfPCell cell = new PdfPCell(new Phrase(h, fHead));
            // FIX 2: Usando BaseColor do iText em vez de Color do AWT
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tblPdf.addCell(cell);
        }

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            tblPdf.addCell(new Phrase((String) tableModel.getValueAt(i, 1), fText)); // Nome
            tblPdf.addCell(new Phrase(tableModel.getValueAt(i, 2).toString(), fText)); // Qtde
            tblPdf.addCell(new Phrase(String.format("R$ %.2f", (BigDecimal)tableModel.getValueAt(i, 3)), fText)); // Unit
            tblPdf.addCell(new Phrase(String.format("R$ %.2f", (BigDecimal)tableModel.getValueAt(i, 4)), fText)); // Sub
        }
        doc.add(tblPdf);
        doc.add(Chunk.NEWLINE);
        
        // --- Total ---
        Paragraph pTotal = new Paragraph(String.format("VALOR TOTAL DA NOTA: R$ %.2f", n.getValorTotal()), fTotal);
        pTotal.setAlignment(Element.ALIGN_RIGHT);
        doc.add(pTotal);

        doc.close();
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(new java.io.File(file));
        }
    }

    public static void main(String[] args) {
        // Define o Look and Feel do sistema para uma aparência mais moderna
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(LancamentoNota::new);
    }
}