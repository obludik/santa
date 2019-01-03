package santa.santa2018;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import algorithms.shortestPath.Node;
import algorithms.shortestPath.ShortPathWithHeuristic;
import santa.SantaIssue;

public class Santa2018_15 implements SantaIssue {	

	private enum Type {
		WALL, ELF, GOBLIN, EMPTY
	}
	
	private static Field[][] grid;
	protected static List<Field> elfs = new ArrayList<>();
	protected static List<Field> goblins = new ArrayList<>();
	protected static List<Field> creatures = new ArrayList<>();

	@Override
	public void solveBothParts(String data, List<String> dataLines) {
		solvePart1(dataLines, 3, false);
		solvePart2(dataLines);
	}

	private void fillGrid(List<String> dataLines, int elfAttackPower) {
		elfs = new ArrayList<>();
		goblins = new ArrayList<>();
		creatures = new ArrayList<>();
		grid = new Field[dataLines.get(0).length()][dataLines.size()];

		int j = 0;
		for (String line : dataLines) {
			char[] chars = line.toCharArray();
			for (int i = 0; i < chars.length; i++) {
				switch (chars[i]) {
				case 'G':
					grid[i][j] = new Field(i, j, Type.GOBLIN, 200, 3);
					goblins.add(grid[i][j]);
					break;
				case 'E':
					grid[i][j] = new Field(i, j, Type.ELF, 200, elfAttackPower);
					elfs.add(grid[i][j]);
					break;
				case '.':
					grid[i][j] = new Field(i, j, Type.EMPTY, 0, 0);
					break;
				case '#':
					grid[i][j] = new Field(i, j, Type.WALL, 0, 0);
					break;
				default:
					break;
				}
			}
			j++;
		}
		creatures.addAll(elfs);
		creatures.addAll(goblins);
		//printGrid();
	}

	public void solvePart2(List<String> dataLines) {
		int elfAttackPower = 1;
		fillGrid(dataLines, elfAttackPower++);
		int elfSize = elfs.size();
		while (true) {
			solvePart1(dataLines, elfAttackPower, true);
			if (elfSize == elfs.size()) {
				break;
			}
			fillGrid(dataLines, elfAttackPower++);
		}
		System.out.println(elfAttackPower);
	}

	public Type solvePart1(List<String> dataLines, int elfAttackPower, boolean endAfterElfDead) {

		fillGrid(dataLines, elfAttackPower);
		int round = 0;
		int elfSize = elfs.size();
		
		while (goblins.size() > 0 && elfs.size() > 0) {

			round++;
			Collections.sort(creatures);
			for (Field creature : creatures) {
				if (goblins.size() == 0 || elfs.size() == 0) {
					round--;
					break;
				}
				if (endAfterElfDead && elfSize != elfs.size()) {
					return null;
				}
				if (!creature.isDead()) {
					List<List<Node>> path = new ArrayList<>();
					if (!creature.isEnemyNear()) {
						for (Field enemy : (creature.type == Type.ELF ? goblins : elfs)) {
							ShortPathWithHeuristic pathAlg = new Santa15ShortestPath();
							for (Node creatureNeighbor : creature.getNeighbors()) {
								for (Node enemyNeighbor : enemy.getNeighbors()) {

									List<Node> sPath = pathAlg.run(creatureNeighbor, enemyNeighbor);
									if (sPath != null) {
										sPath.add(0, enemyNeighbor);
										sPath.add(0, enemy);
										path.add(sPath);
									}
								}
							}

						}

						int shortestPathLen = 0;
						List<Node> result = null;
						if (path.size() > 0) {
							shortestPathLen = path.stream().mapToInt(i -> i.size()).min().getAsInt();
							List<List<Node>> allShortest = new ArrayList<>();
							for (List<Node> list : path) {
								if (list.size() == shortestPathLen) {
									allShortest.add(list);
								}
							}

							// find nearest enemy
							Node nearestEnemy = allShortest.get(0).get(0);
							for (List<Node> list : allShortest) {
								if (list.get(0).getY() < nearestEnemy.getY()) {
									nearestEnemy = list.get(0);
								} else if (list.get(0).getY() == nearestEnemy.getY()
										&& list.get(0).getX() < nearestEnemy.getX()) {
									nearestEnemy = list.get(0);
								}
							}

							List<List<Node>> allShortestWithNearestEnemy = new ArrayList<>();
							for (List<Node> list : allShortest) {
								if (list.get(0).equals(nearestEnemy)) {
									allShortestWithNearestEnemy.add(list);
								}
							}

							result = allShortestWithNearestEnemy.get(0);
							for (List<Node> list : allShortestWithNearestEnemy) {

								if (list.get(list.size() - 1).getY() < result.get(result.size() - 1).getY()) {
									result = list;
								} else if (list.get(list.size() - 1).getY() == result.get(result.size() - 1).getY()
										&& list.get(list.size() - 1).getX() < result.get(result.size() - 1).getX()) {
									result = list;
								}
							}
						}

						if (result != null && result.size() > 0) {
							int newX = result.get(result.size() - 1).getX();
							int newY = result.get(result.size() - 1).getY();
							grid[creature.getX()][creature.getY()] = new Field(creature.getX(), creature.getY(),
									Type.EMPTY, 0, 0);
							creature.setX(newX);
							creature.setY(newY);
							grid[creature.getX()][creature.getY()] = creature;
						}
					}
					creature.attack();

				}

			}
			// printGrid();
		}

		Type winType = goblins.size() == 0 ? Type.ELF : Type.GOBLIN;
		int hitpoints = (winType == Type.ELF ? elfs.stream().mapToInt(i -> i.hitPoints).sum()
				: goblins.stream().mapToInt(i -> i.hitPoints).sum());
		System.out.println(round + " " + hitpoints);
		System.out.println(round * hitpoints);
		return winType;
	}

	private static class Field extends Node implements Comparable<Field> {
		int x;
		int y;
		Type type;
		boolean visited;
		boolean dead;
		int hitPoints;
		int attackPower;

		public Field(int x, int y, Type type, int hitPoints, int attackPower) {
			this.x = x;
			this.y = y;
			this.type = type;
			this.hitPoints = hitPoints;
			this.attackPower = attackPower;
		}

		@Override
		public void setVisited(boolean visited) {
			this.visited = visited;
		}

		@Override
		public List<Node> getNeighbors() {
			List<Node> list = new ArrayList<>();
			if (canMoveTo(x, y, x, y - 1)) {
				list.add(grid[x][y - 1]);
			}
			if (canMoveTo(x, y, x - 1, y)) {
				list.add(grid[x - 1][y]);
			}
			if (canMoveTo(x, y, x + 1, y)) {
				list.add(grid[x + 1][y]);
			}
			if (canMoveTo(x, y, x, y + 1)) {
				list.add(grid[x][y + 1]);
			}
			return list;
		}

		public boolean isEnemyNear() {
			Type enemy = (type == Type.GOBLIN ? Type.ELF : Type.GOBLIN);
			if (isType(enemy, x - 1, y) || isType(enemy, x, y - 1) || isType(enemy, x, y + 1)
					|| isType(enemy, x + 1, y)) {
				return true;
			}
			return false;
		}

		private void attack() {
			if (isEnemyNear()) {
				Field minHitPointsEnemy = null;
				minHitPointsEnemy = compareHitpoints(grid[x][y + 1], minHitPointsEnemy);
				minHitPointsEnemy = compareHitpoints(grid[x][y - 1], minHitPointsEnemy);
				minHitPointsEnemy = compareHitpoints(grid[x + 1][y], minHitPointsEnemy);
				minHitPointsEnemy = compareHitpoints(grid[x - 1][y], minHitPointsEnemy);
				minHitPointsEnemy.hitPoints = minHitPointsEnemy.hitPoints - attackPower;
				if (minHitPointsEnemy.hitPoints <= 0) {
					minHitPointsEnemy.setDead(true);
					grid[minHitPointsEnemy.getX()][minHitPointsEnemy.getY()] = new Field(minHitPointsEnemy.getX(),
							minHitPointsEnemy.getY(), Type.EMPTY, 0, 0);
					if (minHitPointsEnemy.type == Type.GOBLIN) {
						goblins.remove(minHitPointsEnemy);
					} else {
						elfs.remove(minHitPointsEnemy);
					}
				}
			}
		}

		private Field compareHitpoints(Field first, Field second) {
			Type enemy = type == Type.GOBLIN ? Type.ELF : Type.GOBLIN;
			if (second == null) {
				if (isType(enemy, first.getX(), first.getY())) {
					return first;
				} else {
					return null;
				}
			}
			if (isType(enemy, first.getX(), first.getY())) {
				if (first.hitPoints < second.hitPoints) {
					return first;
				} else if (second.hitPoints < first.hitPoints) {
					return second;
				} else {
					return first.compareTo(second) == -1 ? first : second;
				}
			}
			return second;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + x;
			result = prime * result + y;
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
			Field other = (Field) obj;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}

		@Override
		public void printState() {
			// printGrid(grid);

		}

		@Override
		public int getX() {
			return x;
		}

		@Override
		public void setX(int x) {
			this.x = x;
		}

		@Override
		public int getY() {
			return y;
		}

		@Override
		public void setY(int y) {
			this.y = y;

		}

		@Override
		public boolean isVisited() {
			return visited;
		}

		@Override
		public void setLowestVal(boolean visited) {
		}

		@Override
		public int compareTo(Field o) {
			int result = o.getY() > getY() ? -1 : 1;
			if (o.getY() == getY()) {
				result = o.getX() > getX() ? -1 : 1;
			}
			return result;
		}

		@Override
		public String toString() {
			return "Field [x=" + x + ", y=" + y + ", type=" + type + "]";
		}

		public boolean isDead() {
			return dead;
		}

		public void setDead(boolean dead) {
			this.dead = dead;
		}
	}

	private static boolean canMoveTo(int fromX, int fromY, int toX, int toY) {
		if (toX >= 0 && toX < grid[0].length && toY >= 0 && toY < grid.length) {
			if (grid[toX][toY].type == Type.EMPTY) {
				return true;
			}
		}
		return false;
	}

	private static boolean isType(Type type, int toX, int toY) {
		if (toX >= 0 && toX < grid[0].length && toY >= 0 && toY < grid.length) {
			if (grid[toX][toY].type == type) {
				return true;
			}
		}
		return false;
	}

	public static void printGrid() {
		for (int j = 0; j < grid[0].length; j++) {
			for (int i = 0; i < grid.length; i++) {
				switch (grid[i][j].type) {
				case WALL:
					System.out.print('#');
					break;
				case ELF:
					System.out.print('E');
					break;
				case EMPTY:
					System.out.print('.');
					break;
				case GOBLIN:
					System.out.print('G');
					break;
				default:
					break;
				}
			}
			System.out.println();
		}

		for (int j = 0; j < grid[0].length; j++) {
			for (int i = 0; i < grid.length; i++) {
				switch (grid[i][j].type) {
				case ELF:
					System.out.print(" E" + grid[i][j].hitPoints);
					break;
				case GOBLIN:
					System.out.print(" G" + grid[i][j].hitPoints);
					break;
				default:
					break;
				}
			}
			System.out.println();
		}
	}
}
