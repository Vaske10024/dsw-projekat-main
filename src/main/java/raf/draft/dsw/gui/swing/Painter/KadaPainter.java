package raf.draft.dsw.gui.swing.Painter;

import raf.draft.dsw.model.structures.Elementi.Kada;
import raf.draft.dsw.model.structures.Elementi.RoomElement;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class KadaPainter extends view.painters.Painter {
    private Kada kada;
    private boolean selected;

    public KadaPainter(Kada kada) {
        this.kada = kada;
        this.selected = selected;
    }

    @Override
    public void paint(Graphics2D g2d) {

        AffineTransform originalTransform = g2d.getTransform();


        int width = kada.getWidth();
        int height = kada.getHeight();


        int centerX = kada.getLokacijaX() + width / 2;
        int centerY = kada.getLokacijaY() + height / 2;


        g2d.rotate(Math.toRadians(kada.getRotacija()), centerX, centerY);


        if (isSelected()) {
            g2d.setColor(Color.BLUE);
        } else {
            g2d.setColor(Color.BLACK);
        }


        int x = centerX - width / 2;
        int y = centerY - height / 2;


        g2d.drawRect(x, y, width, height);


        int innerMargin = 10;
        g2d.drawRoundRect(x + innerMargin, y + innerMargin, width - 2 * innerMargin, height - 2 * innerMargin, 20, 20);


        g2d.setTransform(originalTransform);
    }

    @Override
    public Rectangle getBounds() {

        int width = kada.getWidth();
        int height = kada.getHeight();


        int centerX = kada.getLokacijaX() + width / 2;
        int centerY = kada.getLokacijaY() + height / 2;


        Rectangle bounds = new Rectangle(centerX - width / 2, centerY - height / 2, width, height);


        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(kada.getRotacija()), centerX, centerY);


        Shape transformedBounds = transform.createTransformedShape(bounds);
        Rectangle2D rotatedBounds = transformedBounds.getBounds2D();


        return new Rectangle(
                (int) rotatedBounds.getX(),
                (int) rotatedBounds.getY(),
                (int) rotatedBounds.getWidth(),
                (int) rotatedBounds.getHeight()
        );
    }

    @Override
    public int getX() {
        return kada.getLokacijaX();
    }

    @Override
    public int getY() {
        return kada.getLokacijaY();
    }

    @Override
    public void setY(int newY) {
        kada.setLokacijaY(newY);
    }

    @Override
    public void setX(int newX) {
        kada.setLokacijaX(newX);
    }

    @Override
    public void setWidth(int newWidth) {
        kada.setWidth(newWidth);
    }

    @Override
    public void setHeight(int newHeight) {
        kada.setHeight(newHeight);
    }

    @Override
    public RoomElement getRoomElement() {
        return kada;
    }
}
