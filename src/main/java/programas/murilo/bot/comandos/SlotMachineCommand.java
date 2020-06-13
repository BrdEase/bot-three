package programas.murilo.bot.comandos;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.api.EmbedBuilder;
import programas.murilo.bot.util.CoinUtils;

public class SlotMachineCommand extends Command {

	private int prob;

	public SlotMachineCommand() {
		this.name = "apostar";
		this.help = "Aposte no caça-níquel";
		this.aliases = new String[] { "aposta" };
		this.arguments = "<valor> ou deixa vazio pra tudo";
		this.prob = 25;
		this.guildOnly = true;
	}

	@Override
	protected void execute(CommandEvent event) {
		String atual = CoinUtils.getDinheiros(event.getMember());
		if (atual.contentEquals("erro")) {
			EmbedBuilder eb = new EmbedBuilder();
			eb.setTitle("Você não está no jogo!");
			eb.setDescription("Use `OwO entrar` para entrar!");
			eb.setFooter("durr...");
			eb.setColor(Color.RED);
			event.reply(eb.build());
			return;
		}

		long moedasAntes = Long.parseLong(atual);
		int valorAleatorio = new Random().nextInt(101);

		boolean tirar;

		List<Integer> aleatorio = new ArrayList<>();

		for (int i = 1; i < 101; i++) {
			aleatorio.add(i);
		}

		for (int i = 0; i < 100; i++) {
			Collections.shuffle(aleatorio);
		}

		while (aleatorio.size() > this.prob) {
			aleatorio.remove(0);
		}

		if (aleatorio.contains(valorAleatorio)) {
			tirar = false;
		} else {
			tirar = true;
		}

		String[] args = event.getArgs().split(" ");

		long valor = 0;
		long conta = 0;
		if (args[0].isEmpty()) {
			conta = moedasAntes;
		} else {
			if (Integer.parseInt(args[0]) > moedasAntes) {
				EmbedBuilder eb = new EmbedBuilder();

				eb.setTitle("Erro!");
				eb.setDescription(
						"Você não pode apostar mais do que tem \nUse `OwO banco` pra saber quanto tem");
				eb.setFooter("chama o agiota...");
				eb.setColor(Color.RED);
				event.reply(eb.build());
				return;
			} else if (Integer.parseInt(args[0]) < 1) {
				EmbedBuilder eb = new EmbedBuilder();

				eb.setTitle("Erro!");
				eb.setDescription(
						"Você não pode apostar nada \nUse `OwO banco` pra saber quanto tem e apostar algo");
				eb.setFooter("burro...");
				eb.setColor(Color.RED);
				event.reply(eb.build());
				return;
			} else {
				conta = Long.parseLong(args[0]);
			}
		}
		if (!tirar) {// ganhou
			valor = (long) (Math.random() * (((2 * conta) - conta) + 1)) + conta;
		} else {
			valor = (long) (Math.random() * ((conta - 1) + 1)) + 1;
		}

		if (CoinUtils.mexaValor(event.getMember(), valor, tirar)) {

			int moedasDepois = Integer.parseInt(CoinUtils.getDinheiros(event.getMember()));

			EmbedBuilder eb = new EmbedBuilder();

			if (moedasDepois > moedasAntes) {
				eb.setAuthor("Na sua aposta de " + conta + " $ugas...");
				eb.setTitle("Você se deu bem!");
				eb.setDescription("Você ganhou " + valor + " $ugas!" + "\nAgora "
						+ event.getMember().getAsMention() + " tem **" + moedasDepois + " $ugas**");
				eb.setFooter("denovo...");
				eb.setColor(Color.GREEN);

			} else {
				eb.setAuthor("Na sua aposta de " + conta + " $ugas...");
				eb.setTitle("Você se deu mal!");
				eb.setDescription("Você perdeu " + valor + " $ugas!" + "\nAgora "
						+ event.getMember().getAsMention() + " tem **" + moedasDepois + " $ugas**");
				eb.setFooter("denovo...");
				eb.setColor(Color.RED);
			}

			event.reply(eb.build());

		} else {
			if (Integer.parseInt(CoinUtils.getDinheiros(event.getMember())) == 0) {
				EmbedBuilder eb = new EmbedBuilder();
				eb.setTitle("Você está sem dinheiro!");
				eb.setDescription("Saia do cassino seu fudido!");
				eb.setFooter("help agiota...");
				eb.setColor(Color.RED);
				event.reply(eb.build());
			} else {
				EmbedBuilder eb = new EmbedBuilder();
				eb.setTitle("Erro!");
				eb.setDescription("Você provavelmente não está no jogo\nUse `OwO entrar` para entrar!");
				eb.setFooter("durr...");
				eb.setColor(Color.RED);
				event.reply(eb.build());
			}

		}

	}

}
