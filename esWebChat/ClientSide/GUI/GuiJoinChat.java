package ClientSide.GUI;

import ClientSide.Client;

import javax.swing.*;

public class GuiJoinChat extends JFrame{
	private JTextField nameTextField;
	private JPanel rootPanel;
	private JButton joinBtn;
	private Client client;

	public GuiJoinChat() {
		client = new Client();
		add(rootPanel);
		setSize(500, 300);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		joinBtn.addActionListener(e -> {
			if (client.setUsername(nameTextField.getText())) {
				System.out.println(client.getUsername());
				new GuiChat(client).setVisible(true);
				dispose();
			}else {
				JOptionPane.showMessageDialog(null, "Username già utilizzato");
			}
		});
	}
}
