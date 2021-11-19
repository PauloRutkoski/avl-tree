package utils;

import model.AvlTree;
import model.Node;

public class Printer {
	public static void print(AvlTree tree) {
		System.out.println("||===========================|| AVL TREE ||==========================||");
		StringBuilder builder = new StringBuilder();
		Printer.buildTree(builder, "", tree.getRoot());
		
		String[] lines = builder.toString().split("\\n");
		for(String line : lines) {
			Printer.printMenuMessage(line);
		}

	}


	private static void buildTree(StringBuilder builder, String padding, Node node) {
		if (node == null) {
			return;
		}
		StringBuilder bdPadding = new StringBuilder(padding);
		bdPadding.append("|  ");
	
		builder.append(node.getKey()).append("\n");
		if (node.getRight() != null) {
			builder.append(bdPadding.toString());
			builder.append("|__DIR: ");
			buildTree(builder, bdPadding.toString(), node.getRight());
		}
		if (node.getLeft() != null) {
			builder.append(bdPadding.toString());
			builder.append("|__ESQ: ");
			buildTree(builder, bdPadding.toString(), node.getLeft());
		}
	}
	
	public static void printMenuMessage(String message) {
		System.out.printf("|| %-65s ||\n", message);
	}
}
