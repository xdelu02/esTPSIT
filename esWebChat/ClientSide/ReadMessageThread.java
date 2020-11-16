package ClientSide;


import ClientSide.GUI.GuiChat;


public class ReadMessageThread implements Runnable {
	private Client clientChat;
	private GuiChat guiChat;

	public ReadMessageThread(Client clientChat, GuiChat guiChat) {
		this.clientChat = clientChat;
		this.guiChat = guiChat;
	}


	@Override
	public void run() {
		String msg = "";
		int size = 0;
		while (true) {
			msg = clientChat.readMessage();
			if (msg.equals("@ls@")) {
				size = Integer.parseInt(clientChat.readMessage());
				for (int i = 0; i < size; i++) {
					guiChat.addUserToList(clientChat.readMessage());
				}
			} else {
				guiChat.showMessage(msg);
			}
		}
	}
}
