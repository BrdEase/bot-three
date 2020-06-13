package programas.murilo.bot.comandos;

import java.awt.Color;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import programas.murilo.bot.util.CoinUtils;

public class DarCommand extends Command {

	public DarCommand() {
		this.name = "dar";
		this.help = "Transfere seu dinheiro pra alguém";
		this.arguments = "<valor> <membro>";
		this.aliases = new String[] { "transferir", "transfere" };
		this.guildOnly = true;
	}

	@Override
	protected void execute(CommandEvent event) {

		String[] args = event.getArgs().split(" ");

		if (args[0].isEmpty() || args[1].isEmpty()) {
			erro(event);
			return;
		}

		Member ganha = event.getMessage().getMentionedMembers().get(0);
		if (ganha == null) {
			erro(event);
			return;
		}

		if (CoinUtils.transferir(event.getMember(), ganha, Long.parseLong(args[0]))) {
			EmbedBuilder eb = new EmbedBuilder();
			eb.setTitle("Transferencia feita!");
			eb.setDescription(event.getMember().getAsMention() + " deu **" + args[0] + " $ugas** pro(a) "
					+ ganha.getAsMention() + "\n" + event.getMember().getAsMention() + " ficou com **"
					+ CoinUtils.getDinheiros(event.getMember()) + " $ugas**" + "\n" + ganha.getAsMention()
					+ " ficou com **" + CoinUtils.getDinheiros(ganha) + " $ugas**");
			eb.setFooter("pagando a noitada né...");
			eb.setColor(Color.GREEN);
			event.reply(eb.build());
		} else {
			erro(event);
		}

	}

	private void erro(CommandEvent event) {
		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle("Erro na transferência!");
		eb.setDescription("Muitas coisas podem ter dado errado, olha a lista:"
				+ "\n Você usou o comando errado (use `OwO help`)"
				+ "\n Você não tem dinheiro suficiente (use `OwO banco`)"
				+ "\n Você ou o outro membro não estão no jogo (use `OwO entrar`)"
				+ "\n O valor é invalido (escreve direito asno)");
		eb.setFooter("ops...");
		eb.setColor(Color.RED);
		event.reply(eb.build());
		// usou o comando errado (OwO help)
		// voce nao tem o dinheiro suficiente
		// voce ou o outro membro nao entraram no jogo
		//
	}

}
