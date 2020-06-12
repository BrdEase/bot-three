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
	}

	@Override
	protected void execute(CommandEvent event) {
		String id = event.getMember().getId();

		List<String> ids = new ArrayList<String>();

		BotAposta.readCSV().get(1).forEach(s -> ids.add(s));

		if (ids.contains(id)) {
			EmbedBuilder eb = new EmbedBuilder();
			eb.setTitle("Você já está no jogo!");
			eb.setDescription("Não da pra entrar duas vezes");
			eb.setFooter("durr...");
			eb.setColor(new Color(230, 25, 46));
			event.reply(eb.build());
		} else {
			ids.add(id);
			BotAposta.writeCSV(1, ids);
			event.reply("Foi!");
		}

	}

}
