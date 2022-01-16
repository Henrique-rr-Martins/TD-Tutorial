package scenes;

import main.Game;

import java.awt.*;

public class Playing extends GameScene implements SceneMethods{
    public Playing(Game game) {
        super(game);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(0, 0, 640, 640);
//        for(int y = 0; y < 20; y++) {
//            for(int x = 0; x < 20; x++){
//                g.drawImage(sprites.get(GRASS), x * 32, y * 32, null);
//            }
//        }
//        g.drawImage(sprites.get(ORC), 100, 100, null);
//        g.drawImage(sprites.get(BAT), 200, 200, null);
    }
}
