package raf.draft.dsw.gui.swing.Painter;

import raf.draft.dsw.model.structures.Elementi.Lavabo;
import raf.draft.dsw.model.structures.Elementi.RoomElement;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class LavaboPainter extends view.painters.Painter {
    private Lavabo lavabo;
    private boolean selected;

    public LavaboPainter(Lavabo lavabo) {
        this.lavabo = lavabo;
        this.selected = false;
    }

    @Override
    public void paint(Graphics2D g2d) {

        AffineTransform originalTransform = g2d.getTransform();


        int width = lavabo.getWidth();
        int height = lavabo.getHeight();

        int centerX = lavabo.getLokacijaX() + width / 2;
        int centerY = lavabo.getLokacijaY() + height / 2;


        g2d.rotate(Math.toRadians(lavabo.getRotacija()), centerX, centerY);


        if (isSelected()) {
            g2d.setColor(Color.BLUE);
        } else {
            g2d.setColor(Color.BLACK);
        }


        int[] xPoints = {
                lavabo.getLokacijaX(),
                lavabo.getLokacijaX() + width,
                centerX
        };
        int[] yPoints = {
                lavabo.getLokacijaY(),
                lavabo.getLokacijaY(),
                lavabo.getLokacijaY() + height
        };


        g2d.drawPolygon(xPoints, yPoints, 3);


        int circleDiameter = Math.min(width, height) / 4;
        int circleX = centerX - circleDiameter / 2;
        int circleY = centerY - circleDiameter / 2;
        g2d.fillOval(circleX, circleY, circleDiameter, circleDiameter);


        g2d.setTransform(originalTransform);
    }

    @Override
    public Rectangle getBounds() {

        int width = lavabo.getWidth();
        int height = lavabo.getHeight();


        int centerX = lavabo.getLokacijaX() + width / 2;
        int centerY = lavabo.getLokacijaY() + height / 2;


        Rectangle bounds = new Rectangle(lavabo.getLokacijaX(), lavabo.getLokacijaY(), width, height);


        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(lavabo.getRotacija()), centerX, centerY);

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
        return lavabo.getLokacijaX();
    }

    @Override
    public int getY() {
        return lavabo.getLokacijaY();
    }

    @Override
    public void setY(int newY) {
        lavabo.setLokacijaY(newY);
    }

    @Override
    public void setX(int newY) {
        lavabo.setLokacijaX(newY);
    }

    @Override
    public void setWidth(int newWidth) {
        lavabo.setWidth(newWidth);
    }

    @Override
    public void setHeight(int newHeight) {
        lavabo.setHeight(newHeight);
    }

    @Override
    public RoomElement getRoomElement() {
        return lavabo;
    }
}
