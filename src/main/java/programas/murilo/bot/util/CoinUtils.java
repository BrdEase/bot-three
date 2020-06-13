package programas.murilo.bot.util;

import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.api.entities.Member;
import programas.murilo.bot.principal.BotAposta;

public class CoinUtils {

	public static boolean mexaValor(Member m, long valor, boolean tirar) {

		if (!estaDentro(m)) {
			return false;
		}
		int pos = -1;

		List<String> ids = new ArrayList<String>();
		List<String> moedas = new ArrayList<String>();

		BotAposta.readCSV().get(1).forEach(s -> ids.add(s));
		BotAposta.readCSV().get(2).forEach(s -> moedas.add(s));

		for (int i = 0; i < ids.size(); i++) {
			if (ids.get(i).contentEquals(m.getId())) {
				pos = i;
			}
		}

		if (pos == -1) {
			return false;
		}
		long novoValor;
		if (tirar) {
			novoValor = Long.parseLong(moedas.get(pos)) - valor;
		} else {
			novoValor = Long.parseLong(moedas.get(pos)) + valor;
		}

		if (novoValor > Integer.MAX_VALUE) {
			novoValor = Integer.MAX_VALUE;
		}

		if (novoValor < 0) {
			return false;
		}

		List<String> novasMoedas = new ArrayList<>();

		for (int i = 0; i < moedas.size(); i++) {
			if (i == pos) {
				novasMoedas.add(novoValor + "");
			} else {
				novasMoedas.add(moedas.get(i));
			}
		}

		BotAposta.writeCSV(2, novasMoedas);
		return true;

	}

	public static boolean estaDentro(Member m) {
		List<String> ids = new ArrayList<String>();

		BotAposta.readCSV().get(1).forEach(s -> ids.add(s));

		return ids.contains(m.getId());
	}

	public static String getDinheiros(Member m) {
		if (!estaDentro(m)) {
			return "erro";
		}

		int pos = -1;

		List<String> ids = new ArrayList<String>();
		List<String> moedas = new ArrayList<String>();

		BotAposta.readCSV().get(1).forEach(s -> ids.add(s));
		BotAposta.readCSV().get(2).forEach(s -> moedas.add(s));

		for (int i = 0; i < ids.size(); i++) {
			if (ids.get(i).contentEquals(m.getId())) {
				pos = i;
			}
		}

		if (pos == -1) {
			return "erro";
		}

		return moedas.get(pos);
	}

	public static boolean transferir(Member perde, Member ganha, long valor) {
		if (!estaDentro(perde) || !estaDentro(ganha)) {
			return false;
		}

		long[] backUp = new long[] { Long.parseLong(getDinheiros(perde)), Long.parseLong(getDinheiros(ganha)) };

		if (mexaValor(perde, valor, true) && mexaValor(ganha, valor, false)) {
			return true;
		} else {
			setValor(perde, backUp[0]);
			setValor(ganha, backUp[1]);
			return false;
		}

	}

	public static boolean setValor(Member m, long novoValor) {
		if (!estaDentro(m)) {
			return false;
		}
		int pos = -1;

		List<String> ids = new ArrayList<String>();
		List<String> moedas = new ArrayList<String>();

		BotAposta.readCSV().get(1).forEach(s -> ids.add(s));
		BotAposta.readCSV().get(2).forEach(s -> moedas.add(s));

		for (int i = 0; i < ids.size(); i++) {
			if (ids.get(i).contentEquals(m.getId())) {
				pos = i;
			}
		}

		if (pos == -1 || novoValor < 0) {
			return false;
		}

		if (novoValor > Integer.MAX_VALUE) {
			novoValor = Integer.MAX_VALUE;
		}

		List<String> novasMoedas = new ArrayList<>();

		for (int i = 0; i < moedas.size(); i++) {
			if (i == pos) {
				novasMoedas.add(novoValor + "");
			} else {
				novasMoedas.add(moedas.get(i));
			}
		}

		BotAposta.writeCSV(2, novasMoedas);
		return true;
	}

}
