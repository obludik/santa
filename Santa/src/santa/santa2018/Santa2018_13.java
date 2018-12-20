package santa.santa2018;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import santa.SantaIssue;
import santa.common.objects.TwoDimCoordinates;

public class Santa2018_13 implements SantaIssue {
	
	private enum Direction {
		LEFT, UP, DOWN, RIGHT
	}
	
	private enum CrossroadDirection {
		LEFT, RIGHT, STRAIGHT
	}
	
	@Override
	public void solveBothParts(String data, List<String> dataLines) {
		char[][] grid = new char[160][160];
		List<Cart> carts = new ArrayList<>();
		int id = 0;
		for (int i = 0; i < dataLines.size(); i++) {
			String[] chars = dataLines.get(i).split("");
			for (int j = 0; j < chars.length; j++) {
				switch (chars[j]) {
				case "<":
					carts.add(new Cart(i, j, Direction.LEFT, id++));
					grid[i][j] = '-';
					break; 
				case "v":	
					carts.add(new Cart(i, j, Direction.DOWN, id++));
					grid[i][j] = '|';
					break;
				case "^":
					carts.add(new Cart(i, j, Direction.UP, id++));
					grid[i][j] = '|';
					break;
				case ">":
					carts.add(new Cart(i, j, Direction.RIGHT, id++));
					grid[i][j] = '-';
					break;					
				default:
					grid[i][j] = chars[j].charAt(0);
					break;
				}
			}
		}
		
		
		boolean found = false;
		Cart firstCollisionCart = null;
		
		while (!found) {
			Collections.sort(carts);
			
			List<Cart> crashed = new ArrayList<>();
			for (Cart cart : carts) {				
				switch (cart.getDirection()) {
				case DOWN:
					cart.setX(cart.getX() + 1);
					break;
				case UP:
					cart.setX(cart.getX() - 1);
					break;
				case LEFT:
					cart.setY(cart.getY() - 1);
					break;
				case RIGHT:
					cart.setY(cart.getY() + 1);
					break;
				default:
					break;
				}
				
				switch (grid[(int)cart.getX()][(int)cart.getY()]) {
				case '\\':
					switch (cart.getDirection()) {
					case RIGHT:
						cart.setDirection(Direction.DOWN);
						break;
					case LEFT:
						cart.setDirection(Direction.UP);
						break;
					case UP:
						cart.setDirection(Direction.LEFT);
						break;
					case DOWN:
						cart.setDirection(Direction.RIGHT);
						break;
					default:
						break;
					}
					break;
				case '/':
					switch (cart.getDirection()) {
					case RIGHT:
						cart.setDirection(Direction.UP);
						break;
					case LEFT:
						cart.setDirection(Direction.DOWN);
						break;
					case UP:
						cart.setDirection(Direction.RIGHT);
						break;
					case DOWN:
						cart.setDirection(Direction.LEFT);
						break;
					default:
						break;
					}
					break;		
				case '+': //it turns left the first time, goes straight the second time, turns right
					if (cart.getLastCrossroad() == CrossroadDirection.RIGHT) {
						cart.setLastCrossroad(CrossroadDirection.LEFT);
						switch (cart.getDirection()) {
						case LEFT:
							cart.setDirection(Direction.DOWN);
							break;
						case UP:
							cart.setDirection(Direction.LEFT);
							break;
						case DOWN:
							cart.setDirection(Direction.RIGHT);
							break;
						case RIGHT:
							cart.setDirection(Direction.UP);
							break;
						default:
							break;
						}
						break;
					} else if  (cart.getLastCrossroad() == CrossroadDirection.LEFT) {
						cart.setLastCrossroad(CrossroadDirection.STRAIGHT);
					} else {
						cart.setLastCrossroad(CrossroadDirection.RIGHT);
						switch (cart.getDirection()) {
						case LEFT:
							cart.setDirection(Direction.UP);
							break;
						case UP:
							cart.setDirection(Direction.RIGHT);
							break;
						case DOWN:
							cart.setDirection(Direction.LEFT);
							break;
						case RIGHT:
							cart.setDirection(Direction.DOWN);
							break;
						default:
							break;
						}
					}
					
					break;		
				default:
					break;
				}
				
				for (Cart cart2 : carts) {
					if (crashed.contains(cart2)) {
						continue;
					}
					if (cart.getId() != cart2.getId() && cart.getX() == cart2.getX() && cart.getY() == cart2.getY()) {
						if (firstCollisionCart == null) {
							firstCollisionCart = cart;
						}					
						crashed.add(cart2);
						crashed.add(cart);
					}
				}
			}
		
			for (Cart cart : crashed) {
				carts.remove(cart);
			}
			
			if (carts.size() == 1) {
				System.out.println("Part 2: " + carts.get(0).getY() + "," + carts.get(0).getX());
				found = true;
			}
		}
		
		System.out.println("Part 1: " + firstCollisionCart.getY() + "," + firstCollisionCart.getX());	
		
	}
	
	private void printState (char[][] grid, List<Cart> carts) {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid.length; j++) {
				boolean found = false;
				for (Cart cart : carts) {
					if (cart.getX() == i && cart.getY() == j) {
						switch (cart.getDirection()) {
						case LEFT:
							System.out.print('<');
							found = true;
							break;
						case UP:
							System.out.print('^');
							found = true;
							break;
						case DOWN:
							System.out.print('v');
							found = true;
							break;
						case RIGHT:
							System.out.print('>');
							found = true;
							break;
						default:
							break;
						}
					}
				}
				if (!found) {
					System.out.print(grid[i][j]);
				}
			}
			System.out.println();
		}
	}
	
	private class Cart extends TwoDimCoordinates implements Comparable<Cart> {
		
		private int id;
		private Direction direction;
		private CrossroadDirection lastCrossroad = CrossroadDirection.RIGHT;

		public Cart(long x, long y, Direction direction, int id) {
			super(x, y);
			this.direction = direction;
			this.id = id;
		}
		
		public Cart(long x, long y) {
			super(x, y);
		}

		public Direction getDirection() {
			return direction;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public void setDirection(Direction direction) {
			this.direction = direction;
		}
		
		public CrossroadDirection getLastCrossroad() {
			return lastCrossroad;
		}

		public void setLastCrossroad(CrossroadDirection lastCrossroad) {
			this.lastCrossroad = lastCrossroad;
		}

		@Override
		public int compareTo(Cart o) {
			int result = o.getX() > getX() ? -1 : 1;
			if (o.getX() == getX()) {
				result =  o.getY() > getY() ? -1 : 1;
			}
			return result;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = super.hashCode();
			result = prime * result + getOuterType().hashCode();
			result = prime * result + id;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (!super.equals(obj))
				return false;
			if (getClass() != obj.getClass())
				return false;
			Cart other = (Cart) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (id != other.id)
				return false;
			return true;
		}

		private Santa2018_13 getOuterType() {
			return Santa2018_13.this;
		}
		
		
	}
}
