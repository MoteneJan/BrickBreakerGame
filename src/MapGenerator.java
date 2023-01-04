import java.awt.*;

public class MapGenerator {
    public int map[][];
    public int brickWidth;
    public int brickHeight;
    public MapGenerator(int row, int col){
        map = new int[row][col];
        for(int x = 0; x < map.length; x++){
            for (int y = 0; y < map[0].length; y++){
                map[x][y] = 1;
            }
        }
        brickWidth = 540/col;
        brickHeight = 150/row;
    }
    public void draw(Graphics2D g){
        for(int x = 0; x < map.length; x++){
            for (int y = 0; y < map[0].length; y++){
                if(map[x][y] > 0){
                    g.setColor(Color.WHITE);
                    g.fillRect(y * brickWidth + 80, x * brickHeight + 50, brickWidth,brickHeight);

                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.BLACK);
                    g.drawRect(y * brickWidth + 80, x * brickHeight + 50, brickWidth,brickHeight);
               }
            }
        }
    }
    public void setBrickValue(int value, int row, int col){ 
        map[row][col] = value;
    }

}
