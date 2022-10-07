import com.googlecode.lanterna.graphics.TextGraphics;

abstract class Element {

    protected Position position;

    public Element(Position position) {
        position = new Position(position.getX(), position.getY());
    }

    Element() {
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    abstract void draw(TextGraphics graphics);
}