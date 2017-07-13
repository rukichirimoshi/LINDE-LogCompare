import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JTextArea;

public class TelaLog extends JFrame {

    private JTextArea tfAreaLog;
    private boolean firstLog = true;

    public TelaLog() {
        setSize(new Dimension(400, 300));
        setTitle("Logging");
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JScrollPane scrollLog = new JScrollPane();
        getContentPane().add(scrollLog, BorderLayout.CENTER);

        tfAreaLog = new JTextArea();
        scrollLog.setViewportView(tfAreaLog);
    }

    public void addLog(String txt) {
        if (firstLog) {
            tfAreaLog.setText(txt);
            firstLog = false;
        } else {
            tfAreaLog.setText(tfAreaLog.getText() + "\n" + txt);
        }
    }

}
