import javax.swing.*;

public class mainForm {
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JTextField textField1;

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
//                tabbedPane1.setVisible(true);
            }
        });
    }

    public void setData(playerTab data) {
    }

    public void getData(playerTab data) {
    }

    public boolean isModified(playerTab data) {
        return false;
    }
}
