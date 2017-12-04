package santa.santa2015;

import java.util.ArrayList;
import java.util.List;

public class Santa16 {

	private static class Aunt {
		int id = -1;
		int children = -1;
		int cats = -1;
		int samoyeds = -1;
		int pomeranians = -1;
		int akitas = -1;
		int vizslas = -1;
		int goldfish = -1;
		int trees = -1;
		int cars = -1;
		int perfumes = -1;
	}

	public static void main(String[] args) {

		List<String> data = FileReader.read("F:\\temp\\santa_16.txt");

		List<Aunt> aunts = new ArrayList<Aunt>();

		for (String string : data) {
			Aunt a = new Aunt();
			aunts.add(a);
			string = string.replace(",", "");
			string = string.replace(":", "");
			String[] auntData = string.split(" ");
			a.id = Integer.parseInt(auntData[1]);
			for (int i = 2; i < auntData.length - 1; i++) {
				switch (auntData[i]) {
				case "children":
					a.children = Integer.parseInt(auntData[i + 1]);
					break;
				case "cats":
					a.cats = Integer.parseInt(auntData[i + 1]);
					break;
				case "samoyeds":
					a.samoyeds = Integer.parseInt(auntData[i + 1]);
					break;
				case "pomeranians":
					a.pomeranians = Integer.parseInt(auntData[i + 1]);
					break;
				case "akitas":
					a.akitas = Integer.parseInt(auntData[i + 1]);
					break;
				case "vizslas":
					a.vizslas = Integer.parseInt(auntData[i + 1]);
					break;
				case "goldfish":
					a.goldfish = Integer.parseInt(auntData[i + 1]);
					break;
				case "trees":
					a.trees = Integer.parseInt(auntData[i + 1]);
					break;
				case "cars":
					a.cars = Integer.parseInt(auntData[i + 1]);
					break;
				case "perfumes":
					a.perfumes = Integer.parseInt(auntData[i + 1]);
					break;
				default:
					break;
				}
			}
		}
		for (Aunt aunt : aunts) {
			int sameCharacteriscs = 0;
			if (aunt.children == 3) {
				sameCharacteriscs++;
			}
			if (aunt.cats > 7) {
				sameCharacteriscs++;
			}
			if (aunt.samoyeds == 2) {
				sameCharacteriscs++;
			}
			if (aunt.pomeranians >= 0 && aunt.pomeranians < 3) {
				sameCharacteriscs++;
			}
			if (aunt.akitas == 0) {
				sameCharacteriscs++;
			}
			if (aunt.vizslas == 0) {
				sameCharacteriscs++;
			}
			if (aunt.trees > 3) {
				sameCharacteriscs++;
			}
			if (aunt.goldfish >= 0 && aunt.goldfish < 5) {
				sameCharacteriscs++;
			}
			if (aunt.cars == 2) {
				sameCharacteriscs++;
			}
			if (aunt.perfumes == 1) {
				sameCharacteriscs++;
			}
			if (sameCharacteriscs == 3) {
				System.out.println(aunt.id);
			}
		}
	}
}
