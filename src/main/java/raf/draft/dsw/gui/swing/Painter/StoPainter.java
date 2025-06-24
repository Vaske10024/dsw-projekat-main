package raf.draft.dsw.gui.swing.Painter;

import raf.draft.dsw.model.structures.Elementi.RoomElement;
import raf.draft.dsw.model.structures.Elementi.Sto;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class StoPainter extends view.painters.Painter {
    private Sto sto;
    private boolean selected;

    public StoPainter(Sto sto) {
        this.sto = sto;
        this.selected = false;
    }

    @Override
    public void paint(Graphics2D g2d) {

        AffineTransform originalTransform = g2d.getTransform();

        int x = sto.getLokacijaX();
        int y = sto.getLokacijaY();
        int width = sto.getWidth();
        int height = sto.getHeight();


        int centerX = x + width / 2;
        int centerY = y + height / 2;


        g2d.rotate(Math.toRadians(sto.getRotacija()), centerX, centerY);

        if (isSelected()) {
            g2d.setColor(Color.BLUE);
        } else {
            g2d.setColor(Color.BLACK);
        }


        g2d.drawRect(x, y, width, height);


        int dotSize = 5;


        int[] dotPositions = {
                x + 10, y + 10,
                x + width - 10, y + 10,
                x + 10, y + height - 10,
                x + width - 10, y + height - 10
        };


        for (int i = 0; i < dotPositions.length; i += 2) {
            g2d.fillOval(dotPositions[i], dotPositions[i + 1], dotSize, dotSize);
        }


        g2d.setTransform(originalTransform);
    }

    @Override
    public Rectangle getBounds() {

        int width = sto.getWidth();
        int height = sto.getHeight();


        int centerX = sto.getLokacijaX() + width / 2;
        int centerY = sto.getLokacijaY() + height / 2;


        Rectangle bounds = new Rectangle(sto.getLokacijaX(), sto.getLokacijaY(), width, height);


        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(sto.getRotacija()), centerX, centerY);


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
        return sto.getLokacijaX();
    }

    @Override
    public int getY() {
        return sto.getLokacijaY();
    }

    @Override
    public void setY(int newY) {
        sto.setLokacijaY(newY);
    }

    @Override
    public void setX(int newX) {
        sto.setLokacijaX(newX);
    }

    @Override
    public void setWidth(int newWidth) {
        sto.setWidth(newWidth);
    }

    @Override
    public void setHeight(int newHeight) {
        sto.setHeight(newHeight);
    }

    @Override
    public RoomElement getRoomElement() {
        return sto;
    }
}
