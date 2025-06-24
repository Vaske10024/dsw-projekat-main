package raf.draft.dsw.gui.swing.Painter;

import raf.draft.dsw.model.structures.Elementi.Bojler;
import raf.draft.dsw.model.structures.Elementi.RoomElement;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class BojlerPainter extends view.painters.Painter {

    private Bojler bojler;
    private boolean selected;

    public BojlerPainter(Bojler bojler) {
        this.bojler = bojler;
        this.selected = false;
    }

    @Override
    public void paint(Graphics2D g2d) {
        AffineTransform originalTransform = g2d.getTransform();
        int centerX = bojler.getLokacijaX() + bojler.getWidth() / 2;
        int centerY = bojler.getLokacijaY() + bojler.getHeight() / 2;

        g2d.rotate(Math.toRadians(bojler.getRotacija()), centerX, centerY);

        if (isSelected()) {
            g2d.setColor(Color.BLUE);
        } else {
            g2d.setColor(Color.BLACK);
        }
        g2d.drawOval(bojler.getLokacijaX(), bojler.getLokacijaY(), bojler.getWidth(), bojler.getHeight());

        int x1 = bojler.getLokacijaX() + bojler.getWidth() / 4;
        int y1 = bojler.getLokacijaY() + bojler.getHeight() / 4;
        int x2 = bojler.getLokacijaX() + bojler.getWidth() * 3 / 4;
        int y2 = bojler.getLokacijaY() + bojler.getHeight() * 3 / 4;

        g2d.drawLine(x1, y1, x2, y2);
        g2d.drawLine(x1, y2, x2, y1);

        g2d.setTransform(originalTransform);
    }

    @Override
    public Rectangle getBounds() {
        int width = bojler.getWidth();
        int height = bojler.getHeight();

        int centerX = bojler.getLokacijaX() + width / 2;
        int centerY = bojler.getLokacijaY() + height / 2;

        Rectangle bounds = new Rectangle(centerX - width / 2, centerY - height / 2, width, height);

        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(bojler.getRotacija()), centerX, centerY);

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
        return bojler.getLokacijaX();
    }

    @Override
    public int getY() {
        return bojler.getLokacijaY();
    }

    @Override
    public void setY(int newY) {
        bojler.setLokacijaY(newY);
    }

    @Override
    public void setX(int newY) {
        bojler.setLokacijaX(newY);
    }

    @Override
    public void setWidth(int newWidth) {
        bojler.setWidth(newWidth);
    }

    @Override
    public void setHeight(int newHeight) {
        bojler.setHeight(newHeight);
    }

    @Override
    public RoomElement getRoomElement() {
        return bojler;
    }
}
