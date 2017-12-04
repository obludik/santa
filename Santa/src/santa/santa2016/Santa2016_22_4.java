package santa.santa2016;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import algorithms.shortestPath.Node;
import algorithms.shortestPath.ShortPathWithHeuristic;
import file.FileReaderImpl;

public class Santa2016_22_4 {
	
	public static final Pattern PATTERN = Pattern.compile("(/dev/grid/node-x(\\d*)-y(\\d*)) *(\\d*)T *(\\d*)T *(\\d*)T *(\\d*)%");
	public static final int GRID_X = 35;
	public static final int GRID_Y = 29;
	
	
	private static Row parseRow(String row, Row[][] grid) {
		Matcher matcher = PATTERN.matcher(row);
		if (matcher.find()) {
			Row roww = new Row(matcher.group(1), Integer.parseInt(matcher.group(4)), Integer.parseInt(matcher.group(5)),
					Integer.parseInt(matcher.group(6)), Integer.parseInt(matcher.group(2)),
					Integer.parseInt(matcher.group(3)), grid);
			return roww;
		} else {
			throw new RuntimeException("Parsing error:" + row);
		}
	}
	
	public static void main(String[] args) {
		file.FileReader reader = new FileReaderImpl();
		List<String> data = reader.readFileByLines(new File("src\\santa\\input\\santa_22").getAbsolutePath());
		long startTime = System.currentTimeMillis();		
		
		Row[][] grid = new Row[GRID_X][GRID_Y];
		
		for (String input : data) {
			Row row = parseRow(input, grid);
			grid[row.x][row.y] = row;
        }
		setLarge(grid);
		grid[0][0].destination = true;
		grid[GRID_X-1][0].goalData = true;
		// start at G
		
		printGrid(grid);
			
		/*ShortPathWithHeuristic pathss = new Santa22ShortestPath();
    	List<Node> path = pathss.run(grid[7][17], grid[35][0]);
    	System.out.println(path.size());*/
    	
    /*	ShortPathWithHeuristic pathss = new ShortPathWithHeuristic();
    	List<Node> path = pathss.run(getZero(grid), grid[GRID_X-1][0]);
    	System.out.println(path.size());
    	
    	for (Node node : path) {
    		grid[node.getX()][node.getY()].finalPath = true;
		}
    	printGrid(grid);*/
		getGoalToEnd(GRID_X - 1, 0, "", grid);
		System.out.println("Shortest: " + shortestPathGoal);
		System.out.println("Shortest length: " + shortestPathGoal.length());
		//771
		System.out.println("Time: " + (System.currentTimeMillis() - startTime));
	}
	
	static String shortestPath = "";
	static String shortestPathGoal = "";
	static Row[][] gridAfterSpaceMove;
	
    private static void getGoalToEnd(int x, int y, String path, Row[][] grid) {
    	grid[x][y].visited = true;
    	
    	printGrid(grid);
    	    
    	/*if (shortestPathGoal.length() > 0) {
            return;
        }*/
    	
    	if (shortestPathGoal.length() > 0 && path.length() >= shortestPathGoal.length()) {
            return;
        }
        
    	
    	if (grid[x][y].destination) {
    		System.out.println(path);
    		if (shortestPathGoal.length() == 0 || path.length() < shortestPathGoal.length()) {
    			shortestPathGoal = path;
    		}    		
    		return;
    	}
    	
        if (canBeMoved(grid[x][y].used, x, y-1, grid)) { //up
        	getSpacePathAndMoveGoal(x, y, x, y-1, grid, path, "U");
        }
        
        if (canBeMoved(grid[x][y].used,x, y + 1, grid)) { //down
        	getSpacePathAndMoveGoal(x, y, x, y + 1, grid, path, "D");
        }

        if (canBeMoved(grid[x][y].used,x - 1, y, grid)) { //left
        	getSpacePathAndMoveGoal(x, y, x - 1, y, grid, path, "L");
      	}

        if (canBeMoved(grid[x][y].used,x + 1, y, grid)) { //right
        	getSpacePathAndMoveGoal(x, y, x + 1, y, grid, path, "R");
        }
        
        
    }
   
    private static void getSpacePathAndMoveGoal(int x, int y, int nextX, int nextY, Row[][] grid, 
    		String path, String direction) {
    	shortestPath = "";
    	Row zero = getZero(grid);
    	getSpacePathToGoal(zero.x, zero.y, "", copyGridAll(grid), nextX, nextY);
    	if (shortestPath.length() > 0) {
    		System.out.println("---"+shortestPath);
    		System.out.println("---"+path + shortestPath + direction);
    		//grid = gridAfterSpaceMove;
    		Row[][] newGrid = copyGridAndMoveGoal(grid, x, y, nextX, nextY);
    		newGrid[zero.x][zero.y].used = 1;
    		getGoalToEnd(nextX, nextY, path + shortestPath + direction, newGrid);
    	}
    }
	
    private static void getSpacePathToGoal(int x, int y, String path, Row[][] grid, int endX, int endY) {
    	
    	ShortPathWithHeuristic pathAlg = new Santa22ShortestPath();
    	List<Node> sPath = pathAlg.run(grid[x][y], grid[endX][endY]);

    	StringBuilder pa = new StringBuilder();
    	for (int i = 0; i < sPath.size() -1; i++) {
    		pa.append("S");
		}
    	shortestPath = pa.toString();
    }
    
    
    private static Row[][] copyGridAndMoveGoal(Row[][] grid, int x, int y, int newX, int newY) {
    	Row[][] newGrid = copyGridAll(grid);
    	
    	newGrid[newX][newY].used = newGrid[x][y].used;
    	newGrid[x][y].used = 0;
    	
    	newGrid[newX][newY].goalData = true;
    	newGrid[x][y].goalData = false;
    	
    	return newGrid;
    }
	
	private static boolean canBeMoved(int howMuch, int whereX, int whereY, Row[][] grid) {
		if (whereY >= 1) {
			 return false;
		}
		if (whereX >= 0 && whereX < GRID_X && whereY >= 0 && whereY < GRID_Y) {
		    if (grid[whereX][whereY].large) {
                return false;
            }
			if (grid[whereX][whereY].visited) {
				return false;
			}
			if (grid[whereX][whereY].used == 0) {
				return false;
			}
			if (grid[whereX][whereY].size >= howMuch) {
				return true;
			}
		}
		return false;
	}
    
    private static Row[][] copyGridAll(Row[][] grid) {
    	Row[][] newGrid = new Row[GRID_X][GRID_Y];
    	for (int i = 0; i < GRID_X; i++) {
			for (int j = 0; j < GRID_Y; j++) {
				newGrid[i][j] = new Row(grid[i][j].node, grid[i][j].size, grid[i][j].used, grid[i][j].avail,
								grid[i][j].x, grid[i][j].y, grid[i][j].large, grid[i][j].goalData,
								grid[i][j].destination, grid[i][j].visited, newGrid);
			}
		}
    	return newGrid;
    }
    
    private static Row getZero(Row[][] grid) {
    	for (int i = 0; i < GRID_X; i++) {
			for (int j = 0; j < GRID_Y; j++) {
				if (grid[i][j].used == 0) {
					return grid[i][j];
				}
			}
		}
    	throw new IllegalArgumentException("No zero found");
    }
    
	private static void setLarge(Row[][] grid) {
		
		int min = Integer.MAX_VALUE;
		int max = 0;
		for (int i = 0; i < GRID_X; i++) {
			for (int j = 0; j < GRID_Y; j++) {
				if (grid[i][j].size < min) {
					min = grid[i][j].size;
				}
				if (grid[i][j].used < 400 && grid[i][j].used > max) {
					max = grid[i][j].used;
				}
				if (grid[i][j].used > 0) {
				    if (grid[i][j].size > 500) {
				        grid[i][j].large = true;
				        continue;
				    }
					if (isPossible(grid[i][j].used, i + 1, j, grid)
							|| isPossible(grid[i][j].used, i - 1, j, grid)
							|| isPossible(grid[i][j].used, i, j + 1, grid)
							|| isPossible(grid[i][j].used, i, j - 1, grid)) {
						continue;
					} else {
						grid[i][j].large = true;
					}
				}
			}
		}
		System.out.println(min + " " + max);
	}

	private static boolean isPossible(int howMuch, int whereX, int whereY, Row[][] grid) {
		if (whereX >= 0 && whereX < GRID_X && whereY >= 0 && whereY < GRID_Y) {
		    if (grid[whereX][whereY].large) {
                return false;
            }
			if (grid[whereX][whereY].visited) {
				return false;
			}
			if (grid[whereX][whereY].used == 0) {
				return false;
			}
			if (grid[whereX][whereY].size >= howMuch) {
				return true;
			}
		}
		return false;
	}
	
	private static void printGrid(Row[][] grid) {
		System.out.println("---------------");
		for (int i = 0; i < GRID_Y; i++) {
			for (int j = 0; j < GRID_X; j++) { 
				if (grid[j][i].finalPath) {
					System.out.print(" P ");
				} else if (grid[j][i].used == 0) {
					System.out.print(" _ ");
				} else if (grid[j][i].lowestVal) {
					System.out.print(" L ");					
				} else if (grid[j][i].visited) {
					System.out.print(" * ");
				} else if (grid[j][i].large) {
					System.out.print(" # ");
				} else if (grid[j][i].goalData) {
					System.out.print(" G ");
				}  else {
					System.out.print(" . ");
					// System.out.print(grid[j][i].used + "/" + grid[j][i].size
					// + " ");
				}
			}
			System.out.println();
		}
	}
	
	private static boolean canBeChanged(int fromX, int fromY, int toX, int toY, Row[][] grid) {
		if (toX >= 0 && toX < GRID_X && toY >= 0 && toY < GRID_Y) {
			if (grid[toX][toY].size >= grid[fromX][fromY].used) {
				if (grid[toX][toY].used <= grid[fromX][fromY].size) {
					if (!grid[toX][toY].goalData) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private static class Row extends Node {
	    String node;
	    int size;
	    int used;
	    int avail;
	    int x;
	    int y;
	    boolean large;
	    boolean goalData;
	    boolean destination;
	    boolean visited;
	    boolean finalPath;
	    boolean lowestVal;
	    Row[][] grid;
	    
	    @Override
		public void setVisited(boolean visited) {
			this.lowestVal = visited;
		}
	    


		@Override
		public void setLowestVal(boolean visited) {
			this.lowestVal = visited;
			
		}
		
		@Override
		public List<Node> getNeighbors() {
			List<Node> list = new ArrayList<>();
			if (canBeChanged(x, y, x, y-1, grid)) {
				list.add(grid[x][y-1]);
			}
			if (canBeChanged(x, y, x, y+1, grid)) {
				list.add(grid[x][y+1]);
			}
			if (canBeChanged(x, y, x-1, y, grid)) {
				list.add(grid[x-1][y]);
			}
			if (canBeChanged(x, y, x+1, y, grid)) {
				list.add(grid[x+1][y]);
			}
			return list;
		}

      
		
        public Row(String node, int size, int used, int avail, int x, int y, Row[][] grid) {
            this.node = node;
            this.size = size;
            this.used = used;
            this.avail = avail;
            this.x = x;
            this.y = y;
            this.grid = grid;
        }

       
        
        public Row(String node, int size, int used, int avail, int x, int y, boolean large, boolean goalData,
				boolean destination, boolean visited, Row[][] grid) {
			this.node = node;
			this.size = size;
			this.used = used;
			this.avail = avail;
			this.x = x;
			this.y = y;
			this.large = large;
			this.goalData = goalData;
			this.destination = destination;
			this.visited = visited;
			this.grid = grid;
		}



		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + avail;
			result = prime * result + ((node == null) ? 0 : node.hashCode());
			result = prime * result + size;
			result = prime * result + used;
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
			Row other = (Row) obj;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}



		@Override
		public String toString() {
			return "Row [used=" + used + ", goalData=" + goalData + ", visited="
					+ visited + "]";
		}



		@Override
		public void printState() {
			printGrid(grid);
			
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
        
	}
}
