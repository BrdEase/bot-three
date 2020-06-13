package programas.murilo.bot.comandos.util;

import net.dv8tion.jda.api.entities.Message;

public class Rodador implements Runnable {

	private Message msg;
	private boolean perdeu;
	private boolean parar;

	public Rodador(Message msg, boolean perdeu) {
		this.msg = msg;
		this.perdeu = perdeu;
		this.parar = false;
	}

	@Override
	public void run() {
		int a = 0;
		while (!parar) {

			this.msg.editMessage(a + "");

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			a++;
		}

		this.msg.editMessage("parei");

	}

	public void parar() {
		this.parar = true;
	}

	public boolean taParado() {
		return this.parar;
	}

}
