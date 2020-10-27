import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


class UIclientChat extends JFrame
{

	UIclientChat(String username) {
		JFrame frame = new JFrame("Chat");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setOpaque(true);
		JTextArea textArea = new JTextArea(15, 50);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		Font f = new Font(Font.DIALOG, Font.PLAIN, 15);
		textArea.setFont(f);
		JScrollPane scroller = new JScrollPane(textArea);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		JPanel inputpanel = new JPanel();
		inputpanel.setLayout(new FlowLayout());
		JTextField input = new JTextField(20);
		JButton button = new JButton("Invia");
		Listener listener = new Listener(input, textArea, username);
		button.addActionListener(listener);
		DefaultCaret caret = (DefaultCaret) textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		panel.add(scroller);
		inputpanel.add(input);
		inputpanel.add(button);
		panel.add(inputpanel);
		frame.getContentPane().add(BorderLayout.CENTER, panel);
		frame.pack();
		frame.setLocationByPlatform(true);
		frame.setVisible(true);
		frame.setResizable(false);
		input.requestFocus();
	}
}

class Listener implements ActionListener {
	private final JTextField input;
	private final JTextArea textArea;
	private final Client client;
	private final String username;

	// readMessage thread
	private Thread readMessage = new Thread(new Runnable() {
		@Override
		public void run() {
			String msg="";
			while (true) {
				msg = client.readMessage();
				if(!msg.equals("@@@NONONLINE###"))
					textArea.append(msg + '\n');
				else textArea.setText("ATTENDI L'ALTRO UTENTE" + '\n');
			}
		}
	});

	public Listener(JTextField input, JTextArea textArea, String username) {
		this.input = input;
		this.textArea = textArea;
		this.client = new Client();
		this.username = username;
		readMessage.start();
		client.sendMessage(username); //invio dello username al Client e poi al Client handler
	}

	public void actionPerformed(ActionEvent event) {
		String msg = input.getText();
		input.setText("");
		textArea.append("Tu : " + msg + '\n');
		client.sendMessage(msg);
	}
}