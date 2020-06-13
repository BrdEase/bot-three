package programas.murilo.bot.comandos;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVRecord;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import programas.murilo.bot.principal.BotAposta;

public class JogandoCommand extends Command {

	public JogandoCommand() {
		this.name = "jogando";
		this.help = "Veja a lista de quem está jogando";
		this.aliases = new String[] { "lista", "jogadores" };
		this.guildOnly = true;
	}

	@Override
	protected void execute(CommandEvent event) {
		CSVRecord cvsRecord = BotAposta.readCSV().get(1);

		List<String> ids = new ArrayList<>();
		cvsRecord.forEach(id -> ids.add(id));

		List<Member> membros = event.getGuild().getMembers();

		List<Member> jogando = membros.parallelStream().filter(m -> ids.contains(m.getId()))
				.collect(Collectors.toList());

		String desc = "Todos os membros abaixo estão jogando";
		for (Member m : jogando) {
			desc = desc.concat("\n" + m.getAsMention());
		}

		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle("Lista obtida!");
		eb.setDescription(desc);
		eb.setFooter("bando de viciados...");
		eb.setColor(Color.GREEN);
		event.reply(eb.build());

	}

}
