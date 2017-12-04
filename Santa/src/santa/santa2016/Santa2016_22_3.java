package santa.santa2016;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import file.FileReaderImpl;

public class Santa2016_22_3 {
	
	public static final Pattern PATTERN = Pattern.compile("(/dev/grid/node-x(\\d*)-y(\\d*)) *(\\d*)T *(\\d*)T *(\\d*)T *(\\d*)%");
	public static final int GRID_X = 37;
	public static final int GRID_Y = 25;
	
	private static Row parseRow(String row) {
		Matcher matcher = PATTERN.matcher(row);
		if (matcher.find()) {
			Row roww = new Row(matcher.group(1), Integer.parseInt(matcher.group(4)), Integer.parseInt(matcher.group(5)),
					Integer.parseInt(matcher.group(6)), Integer.parseInt(matcher.group(2)),
					Integer.parseInt(matcher.group(3)));
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
			Row row = parseRow(input);
			grid[row.x][row.y] = row;
        }
		//setLarge(grid);
		grid[0][0].destination = true;
		grid[GRID_X-1][0].goalData = true;
		// start at G
		getGoalToEnd(GRID_X - 1, 0, "", grid, true);
		System.out.println("Shortest: " + shortestPathGoal);
		System.out.println("Shortest length: " + shortestPathGoal.length());
		//771
		System.out.println("Time: " + (System.currentTimeMillis() - startTime));
	}
	
	static String shortestPath = "";
	static String shortestPathGoal = "";
	static Row[][] gridAfterSpaceMove;
	
    private static void getGoalToEnd(int x, int y, String path, Row[][] grid, boolean firstRun) {
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
        	getSpacePathAndMoveGoal(x, y, x, y-1, grid, path, "U", firstRun);
        }
        
        if (canBeMoved(grid[x][y].used,x, y + 1, grid)) { //down
        	getSpacePathAndMoveGoal(x, y, x, y + 1, grid, path, "D", firstRun);
        }

        if (canBeMoved(grid[x][y].used,x - 1, y, grid)) { //left
        	getSpacePathAndMoveGoal(x, y, x - 1, y, grid, path, "L", firstRun);
      	}

        if (canBeMoved(grid[x][y].used,x + 1, y, grid)) { //right
        	getSpacePathAndMoveGoal(x, y, x + 1, y, grid, path, "R", firstRun);
        }
        
        
    }
   
    private static void getSpacePathAndMoveGoal(int x, int y, int nextX, int nextY, Row[][] grid, 
    		String path, String direction, boolean firstRun) {
    	shortestPath = "";
    	Row zero = getZero(grid);
    	if (firstRun) {
        	getSpacePathToGoal(zero.x, zero.y, "", copyGridForSpace(grid), nextX, nextY, 0, 0, GRID_X, GRID_Y, false);
    		firstRun = false;
    	} else {
        	getSpacePathToGoal(zero.x, zero.y, "", copyGridForSpace(grid), nextX, nextY,
        				zero.x - 2, zero.y - 2, zero.x + 2, zero.y + 2, true);
    	}
    	if (shortestPath.length() > 0) {
    		System.out.println("---"+shortestPath);
    		System.out.println("---"+path + shortestPath + direction);
    		//grid = gridAfterSpaceMove;
    		Row[][] newGrid = copyGridAndMoveGoal(gridAfterSpaceMove, x, y, nextX, nextY);
    		getGoalToEnd(nextX, nextY, path + shortestPath + direction, newGrid, false);
    	}
    }
	
    private static void getSpacePathToGoal(int x, int y, String path, Row[][] grid, int endX, int endY,
    		int minX, int minY, int maxX, int maxY, boolean findAll) {
    	grid[x][y].visitedSpace = true;
   

    /*	if (!findAll && shortestPath.length() > 0) {
    		return;
    	}*/
    	
      	if (path.length() >= 52) {
      		System.out.println(path);
    		return;
    	}
      	
    	if (shortestPath.length() > 0 && path.length() >= shortestPath.length()) {
    		return;
    	}
    	
		if (x == endX && y == endY) {
    		if (shortestPath.length() == 0 || path.length() < shortestPath.length()) {
    			shortestPath = path;
    			gridAfterSpaceMove = grid;
    		}  
    		   System.out.println("found end");
    		return;
    	}
    	
    	// grid[x][y] aktualni mezera
        if (canSpaceGoTo(grid[x][y].size, x, y-1, grid, minX, minY, maxX, maxY)) { //up
        	Row[][] newGrid = copyGridAndMove(grid, x, y, x, y - 1);
        	getSpacePathToGoal(x, y - 1, path + "U", newGrid, endX, endY, minX, minY, maxX, maxY, findAll);
      	}

        if (canSpaceGoTo(grid[x][y].size,x + 1, y, grid, minX, minY, maxX, maxY)) { //right
        	Row[][] newGrid = copyGridAndMove(grid, x, y, x+1, y);        	
        	getSpacePathToGoal(x + 1, y, path + "R", newGrid, endX, endY, minX, minY, maxX, maxY, findAll);
        }
        
        if (canSpaceGoTo(grid[x][y].size,x, y + 1, grid, minX, minY, maxX, maxY)) { //down
        	Row[][] newGrid = copyGridAndMove(grid, x, y, x, y+1);  
        	getSpacePathToGoal(x, y + 1, path + "D", newGrid, endX, endY, minX, minY, maxX, maxY, findAll);
        }
    

	    if (canSpaceGoTo(grid[x][y].size,x - 1, y, grid, minX, minY, maxX, maxY)) { //left
	    	Row[][] newGrid = copyGridAndMove(grid, x, y, x - 1, y); 
	    	getSpacePathToGoal(x - 1, y, path + "L", newGrid, endX, endY, minX, minY, maxX, maxY, findAll);
	    }
        
    }
    
    private static Row[][] copyGridAndMoveGoal(Row[][] grid, int x, int y, int newX, int newY) {
    	Row[][] newGrid = copyGridAll(grid);
    	newGrid[newX][newY].used = newGrid[x][y].used;
    	newGrid[x][y].used = 0;
    	newGrid[newX][newY].goalData = true;
    	newGrid[x][y].goalData = false;
    	return newGrid;
    }
    
    private static Row[][] copyGridAndMove(Row[][] grid, int x, int y, int newX, int newY) {
    	Row[][] newGrid = copyGridAll(grid);
    	newGrid[x][y].used = newGrid[newX][newY].used;
    	newGrid[newX][newY].used = 0;
    	return newGrid;
    }
    
	private static boolean canSpaceGoTo(int size, int whereX, int whereY, Row[][] grid, int minX, int minY, int maxX, int maxY) {
		if (whereX > maxX || whereY > maxY
				||	whereX < minX || whereY < minY) {
			return false;
		}
		if (whereX >= 0 && whereX < GRID_X && whereY >= 0 && whereY < GRID_Y) {
			if (grid[whereX][whereY].goalData || grid[whereX][whereY].visitedSpace
			        || grid[whereX][whereY].large) {
				return false;
			}
			if (grid[whereX][whereY].used <= size) {
				return true;
			}
		}
		return false;
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


    
    private static Row[][] copyGridForSpace(Row[][] grid) {
    	Row[][] newGrid = new Row[GRID_X][GRID_Y];
    	for (int i = 0; i < GRID_X; i++) {
			for (int j = 0; j < GRID_Y; j++) {
				newGrid[i][j] = new Row(grid[i][j].node, grid[i][j].size, grid[i][j].used, grid[i][j].avail,
								grid[i][j].x, grid[i][j].y, grid[i][j].large, grid[i][j].goalData,
								grid[i][j].destination, grid[i][j].visited, false);
			}
		}
    	return newGrid;
    }
    
    private static Row[][] copyGridAll(Row[][] grid) {
    	Row[][] newGrid = new Row[GRID_X][GRID_Y];
    	for (int i = 0; i < GRID_X; i++) {
			for (int j = 0; j < GRID_Y; j++) {
				newGrid[i][j] = new Row(grid[i][j].node, grid[i][j].size, grid[i][j].used, grid[i][j].avail,
								grid[i][j].x, grid[i][j].y, grid[i][j].large, grid[i][j].goalData,
								grid[i][j].destination, grid[i][j].visited, grid[i][j].visitedSpace);
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
				if (grid[j][i].large) {
					System.out.print("  #  ");
				} else {
					System.out.print(grid[j][i].used + "/" + grid[j][i].size + "   ");
				}
			}
			System.out.println();
		}
	}
	
	private static class Row {
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
	    boolean visitedSpace;
	    
        public Row(String node, int size, int used, int avail, int x, int y) {
            this.node = node;
            this.size = size;
            this.used = used;
            this.avail = avail;
            this.x = x;
            this.y = y;
        }

       
        
        public Row(String node, int size, int used, int avail, int x, int y, boolean large, boolean goalData,
				boolean destination, boolean visited, boolean visitedSpace) {
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
			this.visitedSpace = visitedSpace;
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
			if (avail != other.avail)
				return false;
			if (node == null) {
				if (other.node != null)
					return false;
			} else if (!node.equals(other.node))
				return false;
			if (size != other.size)
				return false;
			if (used != other.used)
				return false;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}



		@Override
		public String toString() {
			return "Row [used=" + used + "goalData=" + goalData + "visited="
					+ visited + ", visitedSpace=" + visitedSpace + "]";
		}

      
        
	}
}
