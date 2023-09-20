import java.util.Scanner;

public class Input {

    private Scanner scan;
    private Player player;

    public Input(Player player) {
        scan = new Scanner(System.in);
        this.player = player;
    }

    void inputMessage(GameState state){
        if (state == GameState.Fill){
            System.out.println("Format: a-j,1-10;v/h;1-4   q - exit");
        }else if (state == GameState.Battle){
            System.out.println("attack by coord x,y");
        }
    }

    public void process(GameState state){
        inputMessage(state);
        String in = scan.nextLine();
        boolean quitGame = in.equals("q");
        if (!quitGame){
            if (state == GameState.Fill){
                fillProcess(in);
            } else if (state == GameState.Battle) {
                battleProcess(in);
            }
        }else {
            player.quitGame();
        }
    }

    private Point parseCoords(String in) {
        //a-j,1-10
        String [] coords = in.split(",");
        int x = -1;
        int y = -1;
        if( coords.length >= 2 ) {
            char symbol = Character.toLowerCase( coords[0].charAt(0) );
            x = symbol - 'a'; // a - 55
            y = Integer.parseInt( coords[1] ) - 1;
        }
        return new Point(x, y);
    }

    private void fillProcess(String in){
        //a-j,1-10;H-V;1-4 - ввод
        String [] chunks = in.split(";");

        if (chunks.length >= 3){
            Point c = parseCoords(chunks[0]);

            int deck = Integer.parseInt(chunks[2]);
            Orientation orient = Orientation.None;

            if ("h".equals(chunks[1])){
                orient = Orientation.Horizontal;
            } else if ("v".equals(chunks[1])){
                orient = Orientation.Vertical;
            }
            if (orient != Orientation.None){
                player.addShip(c.x, c.y, deck, orient);
            }
        }
    }

    private void battleProcess( String in ) {
        //a-j,1-10
        Point c = parseCoords(in);
        if( c.x >= 0 && c.y >= 0 ) {
            player.attack(c.x, c.y);
        }
    }
}
