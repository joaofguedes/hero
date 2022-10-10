import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Arena {

    private int width;
    private int height;
    private Arena arena;
    private Hero hero;
    private List<Wall> walls;
    private List<Coins> coins;
    private List<Monster> monsters;

    public Arena(int width, int height) {
        this.width = width;
        this.height = height;
        this.walls = createWalls();
        this.coins = createCoins();
        this.monsters = createMonsters();
        hero = new Hero(new Position(10, 10));
    }

    private List<Wall> createWalls() {
        List<Wall> walls = new ArrayList<>();
        for (int c = 0; c < width; c++) {
            walls.add(new Wall(c, 0));
            walls.add(new Wall(c, height - 1));
        }
        for (int r = 1; r < height - 1; r++) {
            walls.add(new Wall(0, r));
            walls.add(new Wall(width - 1, r));
        }
        return walls;

    }


    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#0000FF"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');
        hero.draw(graphics);
        for (Wall wall : walls)
            wall.draw(graphics);
        for (Coins coin : coins)
            coin.draw(graphics);
        for(Monster monster : monsters)
            monster.draw(graphics);
    }


    private boolean canHeroMove(Position position) {
        if (position.getY() > height || position.getY() < 0) {
            return false;
        }
        if (position.getX() > width || position.getX() < 0) {
            return false;
        }
        for (Wall wall : walls) {
            if (wall.getPosition().equals(position)) {
                return false;
            }
        }
        return true;

    }

    public void processKey(KeyStroke key) {
        if (key.getKeyType() == KeyType.ArrowUp) {
            moveHero(hero.moveUp());
        }
        if (key.getKeyType() == KeyType.ArrowDown) {
            moveHero(hero.moveDown());
        }
        if (key.getKeyType() == KeyType.ArrowLeft) {
            moveHero(hero.moveLeft());
        }
        if (key.getKeyType() == KeyType.ArrowRight) {
            moveHero(hero.moveRight());
        }
        System.out.println(key);
    }

    public void moveHero(Position position) {
        if (canHeroMove(position))
            hero.setPosition(position);
        retrieveCoins(position);
    }

    private List<Coins> createCoins() {
        Random random = new Random();
        ArrayList<Coins> coins = new ArrayList<>();
        for (int i = 0; i < 5; i++)
            coins.add(new Coins(random.nextInt(width - 2) + 1,
                    random.nextInt(height - 2) + 1));
        return coins;
    }

    private List<Monster> createMonsters() {
        Random random = new Random();
        ArrayList<Monster> monsters = new ArrayList<>();
        for(int i=0; i<5; i++){
            monsters.add(new Monster(random.nextInt(width - 2) + 1,
                    random.nextInt(height - 2) + 1));
        }
        return monsters;
    }

    private void retrieveCoins(Position position) {
        for (Coins coin : coins)
            if (hero.getPosition().equals(coin.getPosition())) {
                coins.remove(coin);
                break;
            }

    }
    public void moveMonsters(){
        for(Monster monster : monsters){
            monster.setPosition(monster.move(this));
        }
    }

    public boolean verifyMonsterCollisions(){
        for(Monster monster : monsters){
            if(monster.getPosition().equals(hero.getPosition())){
                System.out.println("Death.");
                return true;
            }
        }
        return false;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

}

