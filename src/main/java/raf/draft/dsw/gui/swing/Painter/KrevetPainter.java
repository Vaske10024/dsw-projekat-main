package raf.draft.dsw.gui.swing.Painter;

import raf.draft.dsw.model.structures.Elementi.Krevet;
import raf.draft.dsw.model.structures.Elementi.RoomElement;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class KrevetPainter extends view.painters.Painter {
    private Krevet krevet;
    private boolean selected;

    public KrevetPainter(Krevet krevet) {
        this.krevet = krevet;
        this.selected = false;
    }

    @Override
    public void paint(Graphics2D g2d) {

        AffineTransform originalTransform = g2d.getTransform();

        int width = krevet.getWidth();
        int height = krevet.getHeight();


        int centerX = krevet.getLokacijaX() + width / 2;
        int centerY = krevet.getLokacijaY() + height / 2;

        g2d.rotate(Math.toRadians(krevet.getRotacija()), centerX, centerY);


        if (isSelected()) {
            g2d.setColor(Color.BLUE);
        } else {
            g2d.setColor(Color.BLACK);
        }


        int x = krevet.getLokacijaX();
        int y = krevet.getLokacijaY();


        g2d.drawRect(x, y, width, height);


        int padding = 10;
        int pillowWidth = width - 2 * padding;
        int pillowHeight = height / 4;


        g2d.drawRect(x + padding, y + padding, pillowWidth, pillowHeight);


        g2d.setTransform(originalTransform);
    }

    @Override
    public Rectangle getBounds() {
        // Dimensions of the bed
        int width = krevet.getWidth();
        int height = krevet.getHeight();

        // Center of the bed
        int centerX = krevet.getLokacijaX() + width / 2;
        int centerY = krevet.getLokacijaY() + height / 2;


        Rectangle bounds = new Rectangle(krevet.getLokacijaX(), krevet.getLokacijaY(), width, height);


        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(krevet.getRotacija()), centerX, centerY);


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
        return krevet.getLokacijaX();
    }

    @Override
    public int getY() {
        return krevet.getLokacijaY();
    }

    @Override
    public void setY(int newY) {
        krevet.setLokacijaY(newY);
    }

    @Override
    public void setX(int newX) {
        krevet.setLokacijaX(newX);
    }

    @Override
    public void setWidth(int newWidth) {
        krevet.setWidth(newWidth);
    }

    @Override
    public void setHeight(int newHeight) {
        krevet.setHeight(newHeight);
    }

    @Override
    public RoomElement getRoomElement() {
        return krevet;
    }
}
