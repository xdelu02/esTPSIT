package ClientSide.GUI;

import ClientSide.Client;
import ClientSide.ReadMessageThread;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class GuiChat extends JFrame {
	private JTextArea chatArea;
	private JPanel rootPanel;
	private JButton sendMsgBtn;
	private JTextField msgTextField;
	private JList GUIusersList;
	private JButton ViewUsersBtn;
	private Client client;
	private ArrayList<String> userList;
	private DefaultListModel userListModel;

	public GuiChat(Client client) {
		this.client = client;
		Thread t = new Thread(new ReadMessageThread(client, this));
		t.start();
		client.sendMessage("@ls@");

		add(rootPanel);
		setSize(600, 450);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		userList = new ArrayList<>();
		userListModel = new DefaultListModel();
		GUIusersList.setModel(userListModel);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				client.sendMessage("@close@");
				System.exit(0);
			}
		});
		
		GUIusersList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {

			}
		});
		ViewUsersBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				client.sendMessage("@ls@");
				userList.clear();
			}
		});
		sendMsgBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sendMsgBtnHandler();
			}
		});
	}

	public void showMessage(String msg) {
		chatArea.append("                  "+msg + '\n');
	}

	public void refreshUserList(){
		userListModel.removeAllElements();
		for (String s : userList) {
			userListModel.addElement(s);
		}
	}

	public void addUserToList(String user) {
		userList.add(user);
		refreshUserList();
	}

	private void sendMsgBtnHandler() {
		client.sendMessage(msgTextField.getText());
		msgTextField.setText("");
	}
}

