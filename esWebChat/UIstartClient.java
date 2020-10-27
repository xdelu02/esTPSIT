import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class UIstartClient extends JFrame {

    UIstartClient() {
        super("Chat");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JPanel panel = new JPanel();
        Container c = this.getContentPane();
        JLabel jl1 = new JLabel("BENVENUTO NELLA CHAT!");
        JLabel jl2 = new JLabel("Inserisci il tuo username:");
        JTextField input = new JTextField();
        JButton joinBtn = new JButton("Entra nella chat");
        Ascoltatore listener = new Ascoltatore(input);

        panel.setLayout(null);
        joinBtn.addActionListener(listener);

        jl1.setBounds(180,30,200,20);
        jl2.setBounds(60,80,200,30);
        input.setBounds(60,110,200,20);
        joinBtn.setBounds(320,110, 140,22);

        panel.add(jl1);
        panel.add(jl2);
        panel.add(input);
        panel.add(joinBtn);
        c.add(panel);

        this.setSize(500, 300);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);

    }
}

class Ascoltatore implements ActionListener {
    private  JTextField inputUsername;

    Ascoltatore(JTextField inputUsername){
        this.inputUsername = inputUsername;
    }

    public void actionPerformed(ActionEvent event) {
        new UIclientChat(inputUsername.getText());
        inputUsername.setText("");
    }
}

class app {
    public static void main(String[] args) {
        new UIstartClient();
    }
}