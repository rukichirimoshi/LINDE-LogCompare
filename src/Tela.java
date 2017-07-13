import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;

public class Tela extends JFrame {

    private JTextField tfFile1;
    private JTextField tfFile2;

    private JTextArea tfAreaFile1;
    private JTextArea tfAreaFile2;
    private JTextArea tfAreaResult;

    private JFileChooser fc = new JFileChooser();
    private FileReaderUtil myReader = new FileReaderUtil();
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");

    private File file1;
    private File file2;
    private List<File> filesLINDE = new ArrayList<File>();
    private List<File> filesFTP = new ArrayList<File>();
    private JTextField tfTotal;
    private JTextField tfTotalProcessed;
    private JTextField tfTotalEntries1;
    private JTextField tfTotalEntries2;

    private TelaLog log = new TelaLog();

    private Set<String> wrongFiles = new LinkedHashSet<String>();

    public Tela() {
        setSize(new Dimension(800, 800));
        setTitle("LINDE - Log Compare");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        fc.setMultiSelectionEnabled(true);
        fc.setCurrentDirectory(new File(System.getProperty("user.home") + System.getProperty("file.separator") + "Desktop"));
        //fc.setCurrentDirectory(new File(".")); Local atual do projeto

        JPanel pnFiles = new JPanel();
        getContentPane().add(pnFiles, BorderLayout.CENTER);
        GridBagLayout gbl_pnFiles = new GridBagLayout();
        gbl_pnFiles.columnWidths = new int[] { 0, 0, 0 };
        gbl_pnFiles.rowHeights = new int[] { 0, 0 };
        gbl_pnFiles.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
        gbl_pnFiles.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
        pnFiles.setLayout(gbl_pnFiles);

        JPanel pnFile1 = new JPanel();
        GridBagConstraints gbc_pnFile1 = new GridBagConstraints();
        gbc_pnFile1.insets = new Insets(0, 0, 0, 5);
        gbc_pnFile1.fill = GridBagConstraints.BOTH;
        gbc_pnFile1.gridx = 0;
        gbc_pnFile1.gridy = 0;
        pnFiles.add(pnFile1, gbc_pnFile1);
        pnFile1.setLayout(new BorderLayout(0, 0));

        JScrollPane scrollPaneFile1 = new JScrollPane();
        pnFile1.add(scrollPaneFile1, BorderLayout.CENTER);

        tfAreaFile1 = new JTextArea();
        tfAreaFile1.setEditable(false);
        scrollPaneFile1.setViewportView(tfAreaFile1);

        JPanel pnFile1Log = new JPanel();
        pnFile1.add(pnFile1Log, BorderLayout.SOUTH);
        GridBagLayout gbl_pnFile1Log = new GridBagLayout();
        gbl_pnFile1Log.columnWidths = new int[] { 0, 60, 0, 0, 0, 0, 0 };
        gbl_pnFile1Log.rowHeights = new int[] { 0, 0 };
        gbl_pnFile1Log.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
        gbl_pnFile1Log.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
        pnFile1Log.setLayout(gbl_pnFile1Log);

        JLabel lblTotalEntries1 = new JLabel("Total Entries:");
        GridBagConstraints gbc_lblTotalEntries1 = new GridBagConstraints();
        gbc_lblTotalEntries1.anchor = GridBagConstraints.EAST;
        gbc_lblTotalEntries1.insets = new Insets(0, 0, 0, 5);
        gbc_lblTotalEntries1.gridx = 0;
        gbc_lblTotalEntries1.gridy = 0;
        pnFile1Log.add(lblTotalEntries1, gbc_lblTotalEntries1);

        tfTotalEntries1 = new JTextField();
        tfTotalEntries1.setEditable(false);
        GridBagConstraints gbc_tfTotalEntries1 = new GridBagConstraints();
        gbc_tfTotalEntries1.insets = new Insets(0, 0, 0, 5);
        gbc_tfTotalEntries1.fill = GridBagConstraints.HORIZONTAL;
        gbc_tfTotalEntries1.gridx = 1;
        gbc_tfTotalEntries1.gridy = 0;
        pnFile1Log.add(tfTotalEntries1, gbc_tfTotalEntries1);
        tfTotalEntries1.setColumns(6);

        JLabel lblFiles1 = new JLabel("Files:");
        GridBagConstraints gbc_lblFiles1 = new GridBagConstraints();
        gbc_lblFiles1.insets = new Insets(0, 0, 0, 5);
        gbc_lblFiles1.anchor = GridBagConstraints.EAST;
        gbc_lblFiles1.gridx = 2;
        gbc_lblFiles1.gridy = 0;
        pnFile1Log.add(lblFiles1, gbc_lblFiles1);

        tfFile1 = new JTextField();
        tfFile1.setEditable(false);
        GridBagConstraints gbc_tfFile1 = new GridBagConstraints();
        gbc_tfFile1.insets = new Insets(0, 0, 0, 5);
        gbc_tfFile1.fill = GridBagConstraints.HORIZONTAL;
        gbc_tfFile1.gridx = 3;
        gbc_tfFile1.gridy = 0;
        pnFile1Log.add(tfFile1, gbc_tfFile1);
        tfFile1.setColumns(10);

        JButton btnFile1 = new JButton("Open");
        btnFile1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                loadFile1();
            }
        });
        GridBagConstraints gbc_btnFile1 = new GridBagConstraints();
        gbc_btnFile1.gridx = 5;
        gbc_btnFile1.gridy = 0;
        pnFile1Log.add(btnFile1, gbc_btnFile1);

        JPanel pnTitleFile1 = new JPanel();
        FlowLayout flowLayout = (FlowLayout) pnTitleFile1.getLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);
        pnFile1.add(pnTitleFile1, BorderLayout.NORTH);

        JLabel lblLindeArchivesLogfile = new JLabel("LINDE Archives Logfile List:");
        lblLindeArchivesLogfile.setFont(new Font("Arial", Font.PLAIN, 18));
        pnTitleFile1.add(lblLindeArchivesLogfile);

        JPanel pnFile2 = new JPanel();
        GridBagConstraints gbc_pnFile2 = new GridBagConstraints();
        gbc_pnFile2.fill = GridBagConstraints.BOTH;
        gbc_pnFile2.gridx = 1;
        gbc_pnFile2.gridy = 0;
        pnFiles.add(pnFile2, gbc_pnFile2);
        pnFile2.setLayout(new BorderLayout(0, 0));

        JScrollPane scrollPaneFile2 = new JScrollPane();
        pnFile2.add(scrollPaneFile2, BorderLayout.CENTER);

        tfAreaFile2 = new JTextArea();
        tfAreaFile2.setEditable(false);
        scrollPaneFile2.setViewportView(tfAreaFile2);

        JPanel pnFile2Log = new JPanel();
        pnFile2.add(pnFile2Log, BorderLayout.SOUTH);
        GridBagLayout gbl_pnFile2Log = new GridBagLayout();
        gbl_pnFile2Log.columnWidths = new int[] { 0, 60, 0, 0, 0, 0, 0 };
        gbl_pnFile2Log.rowHeights = new int[] { 0, 0 };
        gbl_pnFile2Log.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
        gbl_pnFile2Log.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
        pnFile2Log.setLayout(gbl_pnFile2Log);

        JLabel lblTotalEntries2 = new JLabel("Total Entries:");
        GridBagConstraints gbc_lblTotalEntries2 = new GridBagConstraints();
        gbc_lblTotalEntries2.anchor = GridBagConstraints.EAST;
        gbc_lblTotalEntries2.insets = new Insets(0, 0, 0, 5);
        gbc_lblTotalEntries2.gridx = 0;
        gbc_lblTotalEntries2.gridy = 0;
        pnFile2Log.add(lblTotalEntries2, gbc_lblTotalEntries2);

        tfTotalEntries2 = new JTextField();
        tfTotalEntries2.setEditable(false);
        GridBagConstraints gbc_tfTotalEntries2 = new GridBagConstraints();
        gbc_tfTotalEntries2.insets = new Insets(0, 0, 0, 5);
        gbc_tfTotalEntries2.fill = GridBagConstraints.HORIZONTAL;
        gbc_tfTotalEntries2.gridx = 1;
        gbc_tfTotalEntries2.gridy = 0;
        pnFile2Log.add(tfTotalEntries2, gbc_tfTotalEntries2);
        tfTotalEntries2.setColumns(6);

        JLabel lblFiles2 = new JLabel("Files:");
        GridBagConstraints gbc_lblFiles2 = new GridBagConstraints();
        gbc_lblFiles2.insets = new Insets(0, 0, 0, 5);
        gbc_lblFiles2.anchor = GridBagConstraints.EAST;
        gbc_lblFiles2.gridx = 2;
        gbc_lblFiles2.gridy = 0;
        pnFile2Log.add(lblFiles2, gbc_lblFiles2);

        tfFile2 = new JTextField();
        tfFile2.setEditable(false);
        GridBagConstraints gbc_tfFile2 = new GridBagConstraints();
        gbc_tfFile2.insets = new Insets(0, 0, 0, 5);
        gbc_tfFile2.fill = GridBagConstraints.HORIZONTAL;
        gbc_tfFile2.gridx = 3;
        gbc_tfFile2.gridy = 0;
        pnFile2Log.add(tfFile2, gbc_tfFile2);
        tfFile2.setColumns(10);

        JButton btnFile2 = new JButton("Open");
        btnFile2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                loadFile2();
            }
        });
        GridBagConstraints gbc_btnFile2 = new GridBagConstraints();
        gbc_btnFile2.gridx = 5;
        gbc_btnFile2.gridy = 0;
        pnFile2Log.add(btnFile2, gbc_btnFile2);

        JPanel pnTitleFile2 = new JPanel();
        FlowLayout flowLayout_1 = (FlowLayout) pnTitleFile2.getLayout();
        flowLayout_1.setAlignment(FlowLayout.LEFT);
        pnFile2.add(pnTitleFile2, BorderLayout.NORTH);

        JLabel lblFtpArchivesLogfiles = new JLabel("FTP Archives Logfiles List:");
        lblFtpArchivesLogfiles.setFont(new Font("Arial", Font.PLAIN, 18));
        pnTitleFile2.add(lblFtpArchivesLogfiles);

        JPanel pnResult = new JPanel();
        pnResult.setPreferredSize(new Dimension(10, 350));
        pnResult.setMinimumSize(new Dimension(10, 350));
        getContentPane().add(pnResult, BorderLayout.SOUTH);
        pnResult.setLayout(new BorderLayout(0, 0));

        JScrollPane scrollPaneResult = new JScrollPane();
        pnResult.add(scrollPaneResult);

        tfAreaResult = new JTextArea();
        tfAreaResult.setEditable(false);
        scrollPaneResult.setViewportView(tfAreaResult);

        JPanel pnRun = new JPanel();
        pnResult.add(pnRun, BorderLayout.SOUTH);
        GridBagLayout gbl_pnRun = new GridBagLayout();
        gbl_pnRun.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0 };
        gbl_pnRun.rowHeights = new int[] { 23, 0 };
        gbl_pnRun.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
        gbl_pnRun.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
        pnRun.setLayout(gbl_pnRun);

        JLabel lblTotal = new JLabel("Total NOT Processed:");
        GridBagConstraints gbc_lblTotal = new GridBagConstraints();
        gbc_lblTotal.anchor = GridBagConstraints.EAST;
        gbc_lblTotal.insets = new Insets(0, 0, 0, 5);
        gbc_lblTotal.gridx = 0;
        gbc_lblTotal.gridy = 0;
        pnRun.add(lblTotal, gbc_lblTotal);

        tfTotal = new JTextField();
        tfTotal.setEditable(false);
        GridBagConstraints gbc_tfTotal = new GridBagConstraints();
        gbc_tfTotal.insets = new Insets(0, 0, 0, 5);
        gbc_tfTotal.fill = GridBagConstraints.HORIZONTAL;
        gbc_tfTotal.gridx = 1;
        gbc_tfTotal.gridy = 0;
        pnRun.add(tfTotal, gbc_tfTotal);
        tfTotal.setColumns(10);

        JLabel lblTotalProcessed = new JLabel("Total Processed:");
        GridBagConstraints gbc_lblTotalProcessed = new GridBagConstraints();
        gbc_lblTotalProcessed.anchor = GridBagConstraints.EAST;
        gbc_lblTotalProcessed.insets = new Insets(0, 0, 0, 5);
        gbc_lblTotalProcessed.gridx = 3;
        gbc_lblTotalProcessed.gridy = 0;
        pnRun.add(lblTotalProcessed, gbc_lblTotalProcessed);

        tfTotalProcessed = new JTextField();
        tfTotalProcessed.setEditable(false);
        GridBagConstraints gbc_tfTotalProcessed = new GridBagConstraints();
        gbc_tfTotalProcessed.insets = new Insets(0, 0, 0, 5);
        gbc_tfTotalProcessed.fill = GridBagConstraints.HORIZONTAL;
        gbc_tfTotalProcessed.gridx = 4;
        gbc_tfTotalProcessed.gridy = 0;
        pnRun.add(tfTotalProcessed, gbc_tfTotalProcessed);
        tfTotalProcessed.setColumns(10);

        JPanel pnTitleResult = new JPanel();
        FlowLayout flowLayout_2 = (FlowLayout) pnTitleResult.getLayout();
        flowLayout_2.setAlignment(FlowLayout.LEFT);
        pnResult.add(pnTitleResult, BorderLayout.NORTH);

        JLabel lblFilesThatWere = new JLabel("Files That Were NOT Processed (Are in FTP list, but not on the other):");
        lblFilesThatWere.setFont(new Font("Arial", Font.PLAIN, 16));
        pnTitleResult.add(lblFilesThatWere);

        log.setVisible(true);
    }

    protected void runComparison() {
        if (filesLINDE.size() > 0 && filesFTP.size() > 0) {
            log.addLog("[" + sdf.format(new Date()) + "] Starting LINDE files processing!");
            Set<String> lista1 = processFile1();
            log.addLog("[" + sdf.format(new Date()) + "] Finished LINDE files processing!");

            log.addLog("[" + sdf.format(new Date()) + "] Starting FTP files processing!");
            Set<String> lista2 = processFile2();
            log.addLog("[" + sdf.format(new Date()) + "] Finished FTP files processing!");

            Set<String> hs1 = new LinkedHashSet<String>(lista1);
            Set<String> hs2 = new LinkedHashSet<String>(lista2);
            Set<String> hsTemp = new LinkedHashSet<String>(hs2);
            hsTemp.removeAll(hs1);

            tfTotal.setText(String.valueOf(hsTemp.size()));
            tfTotalProcessed.setText(String.valueOf(hs2.size() - hsTemp.size()));
            String result = Arrays.toString(hsTemp.toArray()).replaceAll("\\]|\\[", "");
            result = result.replace(", ", "\n");
            tfAreaResult.setText(result);

            highlightWrongFiles();
            log.addLog("[" + sdf.format(new Date()) + "] Finished!");
        }
    }

    private void highlightWrongFiles() {
        if (wrongFiles.size() > 0) {
            Highlighter highlighter = tfAreaResult.getHighlighter();
            HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.red);
            
            log.addLog("[" + sdf.format(new Date()) + "] There is wrong files on the FTP!");
            JOptionPane.showMessageDialog(null, "There is wrong files on the FTP!", "Wrong Files on the FTP!", JOptionPane.ERROR_MESSAGE);
            
            for (String s : wrongFiles) {
                int p0 = tfAreaResult.getText().indexOf(s);
                int p1 = p0 + s.length();
                try {
                    highlighter.addHighlight(p0, p1, painter);
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Set<String> processFile1() {
        Set<String> arquivosList = new LinkedHashSet<String>();

        String str1 = "";
        for (File f : filesLINDE) {
            str1 += loadStrings(f);
            str1 += "\n";
        }

        String arr_log1[] = str1.split("\n");
        for (String linhaLog1 : arr_log1) {
            linhaLog1 = linhaLog1.trim();
            if (!linhaLog1.isEmpty()) {
                if (!linhaLog1.equalsIgnoreCase("R3BC|barcode")) {
                    String str = linhaLog1.substring(linhaLog1.indexOf(";") + 1, linhaLog1.indexOf(";") + 11);
                    arquivosList.add(str);
                }
            }
        }

        tfTotalEntries1.setText(String.valueOf(arquivosList.size()));

        return arquivosList;
    }

    private Set<String> processFile2() {
        wrongFiles = new LinkedHashSet<String>();
        Set<String> arquivosList = new LinkedHashSet<String>();

        String str2 = "";
        for (File f : filesFTP) {
            str2 += loadStrings(f);
            str2 += "\n";
        }

        String arr_log2[] = str2.split("\n");
        for (String linhaLog2 : arr_log2) {
            if (linhaLog2.toLowerCase().contains("pdf")) {
                linhaLog2 = linhaLog2.trim();
                //Tratamento para documento com o padrão incorreto
                String str = "";
                if (linhaLog2.contains(" (")) {
                    str = linhaLog2.substring(linhaLog2.length() - 18, linhaLog2.length() - 4);
                    wrongFiles.add(str);
                } else {
                    str = linhaLog2.substring(linhaLog2.length() - 14, linhaLog2.length() - 4);
                }
                arquivosList.add(str);
            }
        }

        tfTotalEntries2.setText(String.valueOf(arquivosList.size()));

        return arquivosList;
    }

    protected void loadFile1() {
        int returnVal = fc.showDialog(this, "Select the LINDE files");
        filesLINDE = new ArrayList<File>();
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            loadFiles(returnVal, false);
            String str = "";
            String strFiles = "";
            for (File f : filesLINDE) {
                str += loadStrings(f);
                strFiles += f.getName() + " ";
                str += "\n";
            }
            tfFile1.setText(strFiles);
            log.addLog("[" + sdf.format(new Date()) + "] Strings from 1st file list loaded!");
            tfAreaFile1.setText(str.trim());
            log.addLog("[" + sdf.format(new Date()) + "] Strings from 1st file list loaded in text area!");
        }

        runComparison();
    }

    protected void loadFile2() {
        int returnVal = fc.showDialog(this, "Select the FTP files");
        filesFTP = new ArrayList<File>();
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            loadFiles(returnVal, true);
            String str = "";
            String strFiles = "";
            for (File f : filesFTP) {
                str += loadStrings(f);
                strFiles += f.getName() + " ";
                str += "\n";
            }
            tfFile2.setText(strFiles);
            log.addLog("[" + sdf.format(new Date()) + "] Strings from 2nd file list loaded!");
            tfAreaFile2.setText(str.trim());
            log.addLog("[" + sdf.format(new Date()) + "] Strings from 2nd file list loaded in text area!");
        }

        runComparison();
    }

    private String loadStrings(File f) {
        try {
            return myReader.readFile(f.getAbsolutePath(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void loadFiles(int returnVal, boolean FTP) {
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File[] files = fc.getSelectedFiles();
            for (File f : files) {
                if (FTP) {
                    filesFTP.add(f);
                } else {
                    filesLINDE.add(f);
                }
                log.addLog("[" + sdf.format(new Date()) + "] Opening: " + f.getName() + ".");
            }
        }
    }

    public File getFile1() {
        return file1;
    }

    public File getFile2() {
        return file2;
    }
}
