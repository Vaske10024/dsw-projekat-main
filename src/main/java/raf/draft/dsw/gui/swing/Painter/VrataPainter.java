package raf.draft.dsw.gui.swing.Painter;

import raf.draft.dsw.model.structures.Elementi.RoomElement;
import raf.draft.dsw.model.structures.Elementi.Vrata;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class VrataPainter extends view.painters.Painter {
    private Vrata vrata;
    private boolean selected;

    public VrataPainter(Vrata vrata) {
        this.vrata = vrata;
        this.selected = false;
    }

    @Override
    public void paint(Graphics2D g2d) {
        if (isSelected()) {
            g2d.setColor(Color.BLUE);
        } else {
            g2d.setColor(Color.BLACK);
        }


        AffineTransform originalTransform = g2d.getTransform();


        g2d.translate(vrata.getLokacijaX(), vrata.getLokacijaY());


        int centerX = vrata.getWidth() / 2;
        int centerY = vrata.getHeight() / 2;


        g2d.translate(centerX, centerY);
        g2d.rotate(Math.toRadians(vrata.getRotacija()));
        g2d.translate(-centerX, -centerY);

        int arcHeight = vrata.getHeight() / 2;
        g2d.drawArc(0, 0, vrata.getWidth(), arcHeight, 0, 180);
        g2d.drawLine(0, arcHeight / 2, 0, vrata.getHeight());
        g2d.drawLine(vrata.getWidth(), arcHeight / 2, vrata.getWidth(), vrata.getHeight());


        g2d.setTransform(originalTransform);
    }

    @Override
    public Rectangle getBounds() {

        int width = vrata.getWidth();
        int height = vrata.getHeight();


        int centerX = vrata.getLokacijaX() + width / 2;
        int centerY = vrata.getLokacijaY() + height / 2;


        Rectangle bounds = new Rectangle(vrata.getLokacijaX(), vrata.getLokacijaY(), width, height);


        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(vrata.getRotacija()), centerX, centerY);


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
        return vrata.getLokacijaX();
    }

    @Override
    public int getY() {
        return vrata.getLokacijaY();
    }

    @Override
    public void setY(int newY) {
        vrata.setLokacijaY(newY);
    }

    @Override
    public void setX(int newY) {
        vrata.setLokacijaX(newY);
    }

    @Override
    public void setWidth(int newWidth) {
        vrata.setWidth(newWidth);
    }

    @Override
    public void setHeight(int newHeight) {
        vrata.setHeight(newHeight);
    }

    @Override
    public RoomElement getRoomElement() {
        return vrata;
    }
}
