import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class Game {

    private static Screen screen;

    private Hero hero;
    private Arena arena;
    
    public Game() throws IOException {
        Terminal terminal = new DefaultTerminalFactory().setInitialTerminalSize(new TerminalSize(40,40)).createTerminal();
        screen = new TerminalScreen(terminal);
        screen.setCursorPosition(null);
        screen.startScreen();
        screen.doResizeIfNecessary();
        TerminalSize terminalsize = new TerminalSize(40, 40);
        hero = new Hero(new Position(10, 10));
    }


    private void draw() throws IOException {
            screen.clear();
            hero.draw(screen);
            screen.refresh();

        }

    public void run() throws IOException{
        while (true) {
            draw();
            KeyStroke key = screen.readInput();
            if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'q')
                screen.close();
            if (key.getKeyType() == KeyType.EOF) {
                break;
            }
            processKey(key);
        }
    }

    private void processKey(KeyStroke key){
        if (key.getKeyType() == KeyType.ArrowUp)
            moveHero(hero.moveUp());
        if (key.getKeyType() == KeyType.ArrowDown)
            moveHero(hero.moveDown());
        if (key.getKeyType() == KeyType.ArrowRight)
            moveHero(hero.moveRight());
        if (key.getKeyType() == KeyType.ArrowLeft)
            moveHero(hero.moveLeft());

        System.out.println(key);
    }

    private void moveHero(Position position){
        hero.setPosition(position);
    }
}

