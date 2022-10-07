import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;

public class Arena {
    private int width;
    private int height;
    private Arena arena;
    private Hero hero;

    public Arena(int width) {
        this.width = width;
        this.height = height;
    }

    private void processKey(KeyStroke key){
        arena.processKey(key);
    }

    public void draw(Screen screen) throws IOException{
        TextGraphics graphics = screen.newTextGraphics();
        graphics.setBackgroundColor(TextColor.Factory.fromString("#336699"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width,height), ' ');
        screen.clear();
        arena.draw(screen);
        screen.refresh();
    }

    public void moveHero(Position position){
        if (canHeroMove(position))
            hero.setPosition(position);
    }

    private boolean canHeroMove(Position position){
        if (hero.getPosition().getX() == position.getX() && hero.getPosition().getY() == position.getY())
            return true;
        return false;

    }
}
