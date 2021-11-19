package main;

import java.util.List;

import enums.Operation;
import model.AvlTree;
import utils.Input;
import utils.Printer;

public class Menu {
	AvlTree tree;

	public void executeMenu() {
		tree = new AvlTree();
		boolean execute = true;
		while (execute) {
			this.showMenu();
			Operation option = this.askOption();
			if (option == Operation.CLOSE) {
				execute = false;
				System.out.println("Ate Logo !!!");
			} else {
				Integer value = null;
				if (option != Operation.REFERRALS && option != Operation.CLEAN) {
					value = this.askValue(option);
				}
				if (value != null || option == Operation.REFERRALS ||option == Operation.CLEAN ) {
					executeOperation(option, value);
				}
			}
		}
	}

	private void showMenu() {
		System.out.println("||=============================|| MENU ||============================||");
		Printer.printMenuMessage("I - Inserir");
		Printer.printMenuMessage("B - Buscar");
		Printer.printMenuMessage("R - Remover");
		Printer.printMenuMessage("E - Encaminhamentos");
		Printer.printMenuMessage("L - Limpar");
		Printer.printMenuMessage("S - Sair");
		System.out.println("||===================================================================||");
	}

	private Operation askOption() {
		boolean ask = true;
		Operation value = null;

		while (ask) {
			System.out.println("Digite a opcao desejada: ");
			value = Operation.fromString(Input.getInstance().nextLine());
			if (value != null) {
				ask = false;
			} else {
				System.out.println("Valor Invalido!");
			}
		}
		return value;
	}

	private Integer askValue(Operation option) {
		boolean ask = true;
		String value = null;
		Integer intValue = null;
		while (ask) {
			System.out.println(askValueMessage(option));
			value = Input.getInstance().nextLine();
			if (isBack(value)) {
				ask = false;
			}
			intValue = convertNumber(value);
			if(intValue != null) {
				ask = false;
			}
		}
		return intValue;
	}

	private Integer convertNumber(String str) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
			System.out.println("Valor Invalido!");
			return null;
		}
	}

	private boolean isBlank(String value) {
		if (value == null || value.isEmpty()) {
			return true;
		}
		return false;
	}

	private boolean isBack(String value) {
		if (!isBlank(value) && value.toLowerCase().equals("v")) {
			return true;
		}
		return false;
	}

	private String askValueMessage(Operation option) {
		switch (option) {
		case INSERT:
			return "Qual valor deseja adicionar? (V - Voltar)";
		case SEARCH:
			return "Por qual valor deseja buscar? (V - Voltar)";
		case REMOVE:
			return "Qual valor deseja remover? (V - Voltar)";
		default:
			return "";
		}
	}

	private void executeOperation(Operation op, Integer key) {
		switch (op) {
		case INSERT:
			this.tree.insert(key);
			Printer.print(tree);
			break;
		case SEARCH:
			this.executeSearch(key);
			break;
		case REFERRALS:
			this.executeRoutes();
			break;
		case REMOVE:
			this.tree.delete(key);
			Printer.print(tree);
			break;
		case CLEAN:
			this.tree = new AvlTree();
			break;
		default:
			break;
		}
	}

	private void executeSearch(int key) {
		boolean exists = this.tree.exists(key);
		System.out.println("||============================|| BUSCA ||============================||");
		if (exists) {
			Printer.printMenuMessage("Numero " + key + " foi encontrado com sucesso!");
		} else {
			Printer.printMenuMessage("Numero " + key + " nao foi encontrado.");
		}
		this.printList(this.tree.getSearch(), "Busca");
	}

	private void executeRoutes() {
		tree.executeRoutes();
		System.out.println("||=======================|| ENCAMINHAMENTOS ||=======================||");
		this.printList(this.tree.getPreOrder(), "Pre-Ordem");
		this.printList(this.tree.getPostOrder(), "Pos-Ordem");
		this.printList(this.tree.getInOrder(), "Em-Ordem ");
	}

	private void printList(List<Integer> list, String title) {
		StringBuilder builder = new StringBuilder();
		builder.append(title).append(": ");
		int i = 0;
		for (Integer it : list) {
			if (i != 0) {
				builder.append(" - ");
			}
			builder.append(it);
			i++;
		}
		Printer.printMenuMessage(builder.toString());
	}
}
