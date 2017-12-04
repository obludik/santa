package santa.santa2016;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import file.FileReaderImpl;

public class Santa2016_1 {

    public static class Position {

        public Position(int x, int y, int face) {
            this.x = x;
            this.y = y;
            this.face = face;
        }

        int x;
        int y;
        int face; // 0 - north, 1 - south, 2 - east, 3 - west

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
            Position other = (Position) obj;
            if (x != other.x)
                return false;
            if (y != other.y)
                return false;
            return true;
        }

        @Override
        public String toString() {
            return "Position [x=" + x + ", y=" + y + ", face=" + face + "]";
        }

    }

    static List<Position> positions = new ArrayList<Position>();

    public static void main(String[] args) {
        file.FileReader reader = new FileReaderImpl();
        String data = reader.readSmallTextFile(new File("src\\santa\\input\\santa_1").getAbsolutePath());

        long startTime = System.currentTimeMillis();

        List<Position> positions = new ArrayList<Position>();

        Position current = new Position(0, 0, 0);

        positions.add(new Position(0, 0, 0));

        String[] input = data.split(", ");
        for (int i = 0; i < input.length; i++) {
            String direction = String.valueOf(input[i].charAt(0));
            int len = Integer.parseInt(input[i].substring(1, input[i].length()));
            if (direction.equals("R")) {
                switch (current.face) {
                case 0:
                    current.face = 2;
                    for (int j = 1; j <= len; j++) {
                        current.x += 1;
                        addPosition(new Position(current.x, current.y, current.face));
                    }
                    break;
                case 1:

                    current.face = 3;
                    for (int j = 1; j <= len; j++) {
                        current.x -= 1;
                        addPosition(new Position(current.x, current.y, current.face));
                    }
                    break;
                case 2:

                    current.face = 1;
                    for (int j = 1; j <= len; j++) {
                        current.y -= 1;
                        addPosition(new Position(current.x, current.y, current.face));
                    }
                    break;
                case 3:
                    current.face = 0;
                    for (int j = 1; j <= len; j++) {
                        current.y += 1;
                        addPosition(new Position(current.x, current.y, current.face));
                    }
                    break;
                default:
                    break;
                }

            } else {
                switch (current.face) {
                case 0:

                    current.face = 3;
                    for (int j = 1; j <= len; j++) {
                        current.x -= 1;
                        addPosition(new Position(current.x, current.y, current.face));
                    }
                    break;
                case 1:

                    current.face = 2;
                    for (int j = 1; j <= len; j++) {
                        current.x += 1;
                        addPosition(new Position(current.x, current.y, current.face));
                    }
                    break;
                case 2:
                    current.face = 0;
                    for (int j = 1; j <= len; j++) {
                        current.y += 1;
                        addPosition(new Position(current.x, current.y, current.face));
                    }
                    break;
                case 3:
                    current.face = 1;
                    for (int j = 1; j <= len; j++) {
                        current.y -= 1;
                        addPosition(new Position(current.x, current.y, current.face));
                    }
                    break;
                default:
                    break;
                }
            }

        }
        System.out.println("x: " + current.x);
        System.out.println("y: " + current.y);
        System.out.println("len: " + (Math.abs(current.y) + Math.abs(current.x)));

        System.out.println("Time: " + (System.currentTimeMillis() - startTime));
    }

    private static void addPosition(Position current) {
        Position newPos = new Position(current.x, current.y, current.face);

        if (positions.contains(newPos)) {
            System.out.println("x: " + current.x);
            System.out.println("y: " + current.y);
            System.out.println("len: " + (Math.abs(current.y) + Math.abs(current.x)));
            System.exit(0);
        } else {
            positions.add(newPos);
        }
    }

}
