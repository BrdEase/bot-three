package programas.murilo.bot.principal;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class HelloCommand extends Command {

	public HelloCommand() {
		this.name = "teste";
	}

	@Override
	protected void execute(CommandEvent event) {
		System.out.println(event.getGuild().getMemberByTag("cadeirante eh xingamento SIM#3017").getId());
	}

}
