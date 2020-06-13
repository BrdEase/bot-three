package programas.murilo.bot.comandos;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.api.EmbedBuilder;
import programas.murilo.bot.principal.BotAposta;

public class EntrarCommand extends Command {

	public EntrarCommand() {
		this.name = "entrar";
		this.help = "Entrar no jogo";
		this.aliases = new String[] { "participar", "entre" };
		this.guildOnly = true;
	}

	@Override
	protected void execute(CommandEvent event) {
		String id = event.getMember().getId();

		List<String> ids = new ArrayList<String>();
		List<String> moedas = new ArrayList<String>();

		BotAposta.readCSV().get(1).forEach(s -> ids.add(s));
		BotAposta.readCSV().get(2).forEach(m -> moedas.add(m));

		if (ids.contains(id)) {
			EmbedBuilder eb = new EmbedBuilder();
			eb.setTitle("Você já está no jogo!");
			eb.setDescription("Não da pra entrar duas vezes");
			eb.setFooter("durr...");
			eb.setColor(Color.RED);
			event.reply(eb.build());
		} else {
			ids.add(id);
			moedas.add("10000");
			BotAposta.writeCSV(1, ids);
			BotAposta.writeCSV(2, moedas);
			EmbedBuilder eb = new EmbedBuilder();
			eb.setTitle("Bem Vindo!");
			eb.setDescription("Você acaba de entrar no jogo");
			eb.setFooter("so nao perde o cu no truco...");
			eb.setColor(Color.GREEN);
			event.reply(eb.build());
		}

	}

}
