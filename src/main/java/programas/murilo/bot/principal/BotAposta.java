package programas.murilo.bot.principal;

import java.awt.Color;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.jagrosh.jdautilities.examples.command.AboutCommand;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import programas.murilo.bot.comandos.ApostarCommand;
import programas.murilo.bot.comandos.BancoCommand;
import programas.murilo.bot.comandos.DarCommand;
import programas.murilo.bot.comandos.EntrarCommand;
import programas.murilo.bot.comandos.JogandoCommand;

public class BotAposta {

	public static void main(String[] args) throws IOException, LoginException {

		String token = readCSV().get(0).get(0);

		// the second is the bot's owner's id
//		String ownerId = readCSV().get(0).get(1);
//		String ownerId = "251135383797104640"; do lucas

		// define an eventwaiter, dont forget to add this to the JDABuilder!
		EventWaiter waiter = new EventWaiter();

		// define a command client
		CommandClientBuilder client = new CommandClientBuilder();

		// The default is "Type !!help" (or whatver prefix you set)
		client.useDefaultGame();

		// sets the owner of the bot
		client.setOwnerId(readCSV().get(0).get(1));

		// sets emojis used throughout the bot on successes, warnings, and failures
//		client.setEmojis("\u1F346", "\uD83D\uDE2E", "\uD83D\uDE26");

		// sets the bot prefix
		client.setPrefix(readCSV().get(0).get(2) + " ");

		// adds commands
		client.addCommands(

				// command to show information about the bot
				new AboutCommand(Color.BLUE, "an example bot",
						new String[] { "Cool commands", "Nice examples", "Lots of fun!" },
						new Permission[] { Permission.ADMINISTRATOR }),

				new EntrarCommand(),

				new DarCommand(),

				new BancoCommand(),

				new JogandoCommand(),

//				new RodarCommand(waiter),

//				new HellouCommand(waiter),

				new ApostarCommand());

//				new HelloCommand());

		new JDABuilder(AccountType.BOT)
				// set the token
				.setToken(token)

				// set the game for when the bot is loading
				.setStatus(OnlineStatus.DO_NOT_DISTURB).setActivity(Activity.playing("carregando..."))

				// add the listeners
				.addEventListeners(waiter, client.build())

				// start it up!
				.build();

	}

	public static void writeCSV(int indexLinha, List<String> lista) {
		try {
			List<CSVRecord> read = readCSV();
			FileWriter a = new FileWriter("/home/murilo/bot/config.csv");
			CSVPrinter p = new CSVPrinter(a, CSVFormat.EXCEL);

			List<List<String>> gravar = new ArrayList<>();

			for (int i = 0; i < read.size(); i++) {
				if (indexLinha == i) {
					gravar.add(lista);
				} else {
					List<String> l = new ArrayList<>();
					read.get(i).forEach(c -> l.add(c));
					gravar.add(l);
				}
			}

			for (List<String> list : gravar) {
				for (String s : list) {
					p.print(s);
				}
				p.println();
			}

			a.close();
			p.close();
		} catch (IOException e) {
			System.out.println("Erro escrevendo CSV");
			e.printStackTrace();
		}

	}

	public static List<CSVRecord> readCSV() {
		List<CSVRecord> records = new ArrayList<>();
		try {
			FileReader r = new FileReader("/home/murilo/bot/config.csv");
			CSVParser a = new CSVParser(r, CSVFormat.EXCEL);
			records = a.getRecords();
			r.close();
			a.close();
		} catch (IOException e) {
			System.out.println("Erro lendo CSV");
			e.printStackTrace();
		}

		return records;

	}

}
