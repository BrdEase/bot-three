package programas.murilo.bot.comandos;

import java.awt.Color;
import java.util.Random;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.api.EmbedBuilder;
import programas.murilo.bot.util.CoinUtils;

public class BancoCommand extends Command {

	public BancoCommand() {
		this.name = "banco";
		this.aliases = new String[] { "extrato", "dindin" };
		this.help = "Mostra a quantidade de moedas que você tem";
		this.guildOnly = true;
	}

	@Override
	protected void execute(CommandEvent event) {
		int valor = new Random().nextInt(101);
		// 720784291117793291 - id do emote manu

		if (valor == 55 || valor == 78 || valor == 33 || valor == 7 || valor == 2) {
			EmbedBuilder eb = new EmbedBuilder();
			eb.setTitle("Do parque!");
			eb.setColor(Color.PINK);
			event.reply(eb.build());
		} else {

			if (CoinUtils.estaDentro(event.getMember())) {
				EmbedBuilder eb = new EmbedBuilder();
				eb.setTitle("Seu extrato esta disponível!");
				eb.setDescription(event.getMember().getAsMention() + " tem **"
						+ CoinUtils.getDinheiros(event.getMember()) + " $ugas**");
				eb.setFooter("aposte...");
				eb.setColor(Color.GREEN);
				event.reply(eb.build());
			} else {
				EmbedBuilder eb = new EmbedBuilder();
				eb.setTitle("Você não está no jogo!");
				eb.setDescription("Use `OwO entrar` para entrar!");
				eb.setFooter("durr...");
				eb.setColor(Color.RED);
				event.reply(eb.build());
			}

		}

	}

}
