package programas.murilo.bot.comandos;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import programas.murilo.bot.comandos.util.Rodador;
import programas.murilo.bot.util.CoinUtils;

public class RodarCommand extends Command {

	private int prob;
	private final EventWaiter waiter;

	public RodarCommand(EventWaiter waiter) {
		this.waiter = waiter;
		this.name = "rodar";
		this.prob = 1;
		this.help = "Jogue no caça níquel. " + this.prob + "% de chance de ganhar 5x o apostado";
		this.aliases = new String[] { "caça-níquel", "caça" };
		this.arguments = "<valor> ou deixa vazio pra tudo";
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

		boolean perdeu;

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
			perdeu = false;
		} else {
			perdeu = true;
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
		if (perdeu) {// perdeu
			valor = (long) (Math.random() * (((conta / 2) - (conta / 8)) + 1)) + (conta / 8);
		} else {
			valor = (long) (Math.random() * (((5 * conta) - (2 * conta)) + 1)) + (2 * conta);
		}

		// caça
		event.reply("cu");

//		this.waiter.waitForEvent(MessageReceivedEvent.class,
//				// make sure it's by the same user, and in the same channel, and for safety, a
//				// different message
//				e -> e.getAuthor().equals(event.getAuthor()) && e.getChannel().equals(event.getChannel())
//						&& !e.getMessage().equals(event.getMessage()),
//				// respond, inserting the name they listed into the response
//				e -> event.reply("Hello, `" + e.getMessage().getContentRaw() + "`! I'm `"
//						+ e.getJDA().getSelfUser().getName() + "`!"),
//				// if the user takes more than a minute, time out
//				1, TimeUnit.MINUTES, () -> event.reply("Sorry, you took too long."));

//		return;

		Rodador rod = iludir(event, this.waiter, perdeu);

		while (!rod.taParado()) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		if (CoinUtils.mexaValor(event.getMember(), valor, perdeu)) {

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
				eb.setDescription("Você **provavelmente** não está no jogo\nUse `OwO entrar` para entrar!");
				eb.setFooter("durr...");
				eb.setColor(Color.RED);
				event.reply(eb.build());
			}

		}

	}

	private Rodador iludir(CommandEvent event, EventWaiter waiter2, boolean perdeu) {
		EmbedBuilder eb = new EmbedBuilder();
		eb.setAuthor("Caça-níquel");
		eb.setTitle("Mande `parar` para parar de rodar!");
		eb.setFooter("viciado né...");
		eb.setColor(Color.YELLOW);

		MessageAction sent = event.getChannel().sendMessage("nada");
		Message msg = sent.complete();

		event.reply(eb.build());
		System.out.println("ok");
		msg.editMessage("aaaaaaa");

		Rodador rodador = new Rodador(msg, perdeu);

		new Thread(rodador).start();

		Predicate<MessageReceivedEvent> predicado = new Predicate<MessageReceivedEvent>() {

			@Override
			public boolean test(MessageReceivedEvent e) {
				System.out.println(e.getAuthor().equals(event.getAuthor()));
				System.out.println(e.getChannel().equals(event.getChannel()));
				System.out.println(e.getMessage().getContentRaw().contentEquals("parar"));
				System.out.println("mensagem: " + e.getMessage().getContentRaw());

				if (e.getAuthor().equals(event.getAuthor()) && e.getChannel().equals(event.getChannel())
						&& e.getMessage().getContentRaw().contentEquals("parar")) {
					return true;
				}
				return false;
			}
		};

//		this.waiter.waitForEvent(MessageReceivedEvent.class, e ->
//
//		e.getAuthor().equals(event.getAuthor()) && e.getChannel().equals(event.getChannel())
//				&& e.getMessage().getContentRaw().contentEquals("parar")
//
//				, e -> rodador.parar(), 100, TimeUnit.SECONDS, () -> {
//					event.reply("lerdo que só um caralho");
//					rodador.parar();
//				});

		this.waiter.waitForEvent(MessageReceivedEvent.class,
				// make sure it's by the same user, and in the same channel, and for safety, a
				// different message
				e -> e.getAuthor().equals(event.getAuthor()) && e.getChannel().equals(event.getChannel())
						&& !e.getMessage().equals(event.getMessage()),
				// respond, inserting the name they listed into the response
				e -> event.reply("Hello, `" + e.getMessage().getContentRaw() + "`! I'm `"
						+ e.getJDA().getSelfUser().getName() + "`!"),
				// if the user takes more than a minute, time out
				1, TimeUnit.MINUTES, () -> event.reply("Sorry, you took too long."));

		return rodador;

	}

}
