public class Map {
    public static final int Width = 10;
    public static final int Height = 10;
    public static final int NeighborCells = 8;

    private char [] head;
    private char [][] cells;

    private Deck [][] decks;

    Point[] neighbors;

    private char [][] missPoints;

    public Map() {
        head = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J' };
        cells = new char[Height][Width];
        decks = new Deck[Height][Width];
        missPoints = new char[Height][Width];
        for(  int y = 0; y < Height; y++ ) {
            for( int x = 0; x < Width; x++ ) {
                cells[x][y] = ' ';
            }
        }

        neighbors = new Point[NeighborCells];
        neighbors[0] = new Point( -1, -1 );
        neighbors[1] = new Point( -1, 0 );
        neighbors[2] = new Point( -1, 1 );
        neighbors[3] = new Point( 0, 1 );
        neighbors[4] = new Point( 1, 1 );
        neighbors[5] = new Point( 1, 0 );
        neighbors[6] = new Point( 1, -1 );
        neighbors[7] = new Point( 0, -1 );
    }

    public void clean() {
        for(  int y = 0; y < Height; y++ ) {
            for( int x = 0; x < Width; x++ ) {
                cells[x][y] = ' ';
            }
        }
    }

    private void update() {
        clean();
        for(int y = 0; y < Height; y++) {
            for( int x = 0; x < Width; x++ ) {
                Deck deck = decks[y][x];
                if( deck != null ) {
                    Point position = deck.getPosition();
                    cells[position.x][position.y] = deck.getView();
                }
                char missPoint = missPoints[y][x];//
                if (missPoint == '*'){
                    cells[x][y] = '*';
                }
            }
        }
    }

    public void drawMap() {
        update();

        System.out.print(" ");
        for(int x = 0; x < Width; x++ ) {
            System.out.print(' ');
            System.out.print(head[x]);
        }
        System.out.println();
        for(int y = 0; y < Height; y++){
            System.out.print(y+1);
            for(int x = 0; x < Width; x++){
                if (!(x==0 && y ==9)){
                    System.out.print(' ');
                }
                System.out.print(cells[x][y]);
            }
            System.out.println('|');
        }
    }

    public void addObject(Deck deck){
        Point position = deck.getPosition();
        if ( isValidCoord(position) ) {
            decks[position.y][position.x] = deck;
        }
    }

    public void addMissPoint(Point position){
        if ( isValidCoord(position) ) {
            missPoints[position.y][position.x] = '*';
        }
    }

    public boolean isValidCoord( Point point ) {
        return point.x >= 0 && point.x < Width && point.y >= 0 && point.y < Height;
    }

    public boolean isCollide(Point position){
        return decks[position.y][position.x] != null;
    }

    public boolean hasNeighbors( Point position ) {
        boolean result = false;
        for( Point p : neighbors ) {
            int k = position.x + p.x;
            int l = position.y + p.y;
            Point neighbor = new Point( k, l );
            boolean isValidCoor = isValidCoord(neighbor);
            if( isValidCoor && decks[neighbor.y][neighbor.x] != null ) {
                result = true;
                break;
            }
        }
        return result;
    }

    public Deck[][] getDecks(){
        return decks;
    }

}
