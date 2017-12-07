package santa.santa2017;

import java.util.ArrayList;
import java.util.List;

import santa.SantaIssue;

public class Santa2017_7 implements SantaIssue {

	@Override
	public void solvePart1(String data, List<String> dataLines) {
		List<String> leftNames = new ArrayList<>();
		List<String> rightNames = new ArrayList<>();
		for (String line : dataLines) {
			String[] names = line.split(" ");
			leftNames.add(names[0]);
			for (int i = 3; i < names.length; i++) {
				rightNames.add(names[i].replaceAll(",", ""));
			}
		}
		for (String name : leftNames) {
			if (!rightNames.contains(name)) {
				System.out.println(name);
				break;
			}
		}
	}

	@Override
	public void solvePart2(String data, List<String> dataLines) {
		List<SubTower> tower = new ArrayList<>();
		for (String line : dataLines) {
			String[] names = line.split(" ");

			int weigth = Integer.valueOf(names[1].replace("(", "").replace(")", ""));
			SubTower subTower = new SubTower(names[0], weigth);
			if (tower.contains(subTower)) {
				subTower = tower.get(tower.indexOf(subTower));
				subTower.setWeigth(weigth);
			} else {
				tower.add(subTower);
			}

			for (int i = 3; i < names.length; i++) {
				SubTower subt = new SubTower(names[i].replaceAll(",", ""));
				if (tower.contains(subt)) {
					subt = tower.get(tower.indexOf(subt));
				} else {
					tower.add(subt);
				}
				subTower.addSubTower(subt);
			}
		}
		int difference = 0;
		SubTower wrongTower = new SubTower("fake", Integer.MAX_VALUE);
		
		for (SubTower subTower : tower) {
			if (subTower.getSubTowers().size() > 0) {
				int sum = 0;
				int differentSum = 0;
				int sumCount = 0;
				int differentSumCount = 0;
				SubTower towerWithOneSum = null;
				SubTower towerWithDifferentSum = null;
				for (SubTower subT : subTower.getSubTowers()) {
					int sumTemp = subT.getSumOfWeigths();
					if (sum == 0) {
						sum = sumTemp;
						sumCount++;
						towerWithOneSum = subT;
					} else if (sumTemp != sum) {
						towerWithDifferentSum = subT;
						differentSum = sumTemp;
						differentSumCount++;
					} else {
						sumCount++;
					}
				}
				if (towerWithDifferentSum != null) {
					if (differentSumCount < sumCount) {
						difference = differentSum - sum;
						if (wrongTower.getSumOfWeigths() > differentSum) {
							wrongTower = towerWithDifferentSum;
						}
						
					} else {
						difference = sum - differentSum;
						if (wrongTower.getSumOfWeigths() > sum) {
							wrongTower = towerWithOneSum;
						}
					}
				}
			}
		}

		System.out.println("Result: " + (wrongTower.weigth - difference));

	}

	private class SubTower {
		String name;
		int weigth;
		List<SubTower> subTowers = new ArrayList<>();

		public SubTower(String name) {
			this.name = name;
		}

		public SubTower(String name, int weigth) {
			this.name = name;
			this.weigth = weigth;
		}

		public int getSumOfWeigths() {
			int sum = weigth;
			for (SubTower subTower : subTowers) {
				sum += subTower.getSumOfWeigths();
			}
			return sum;
		}

		public void addSubTower(SubTower subTower) {
			subTowers.add(subTower);
		}

		public void setWeigth(int weigth) {
			this.weigth = weigth;
		}

		public List<SubTower> getSubTowers() {
			return subTowers;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = prime + ((name == null) ? 0 : name.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			SubTower other = (SubTower) obj;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			return true;
		}

		@Override
		public String toString() {
			String print = "SubTower [name=" + name + ", weigth=" + weigth + "]";
			print += "subTowers={" + subTowers.size() + "}\n";
			return print;
		}
	}
}
