import java.util.ArrayList;

public class MyBot2 {
    public static void main(String[] args) throws java.io.IOException {
        InitPackage iPackage = Networking.getInit();
        int myID = iPackage.myID;
        GameMap gameMap = iPackage.map;

        Networking.sendInit("JavaBot2");

        while(true) {
            ArrayList<Move> moves = new ArrayList<Move>();

            Networking.updateFrame(gameMap);

            for(int y = 0; y < gameMap.height; y++) {
                for(int x = 0; x < gameMap.width; x++) {
                	final Location location = gameMap.getLocation(x, y);
                    final Site site = location.getSite();
                   
                    if(site.owner == myID) {
                      
                        boolean movedPiece = false;
                      
                        if(!movedPiece && site.strength == 0) {
                            moves.add(new Move(location, Direction.STILL));
                            movedPiece = true;
                        }
                      
                        if(!movedPiece) {
                            moves.add(new Move(location, Direction.randomDirection()));
                            movedPiece = true;
                        }
                    }
                }
            }
            Networking.sendFrame(moves);
        }
    }
}