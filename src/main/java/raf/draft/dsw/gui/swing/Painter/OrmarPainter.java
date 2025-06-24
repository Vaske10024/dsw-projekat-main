package raf.draft.dsw.gui.swing.Painter;

import raf.draft.dsw.model.structures.Elementi.Ormar;
import raf.draft.dsw.model.structures.Elementi.RoomElement;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class OrmarPainter extends view.painters.Painter {
    Ormar ormar;
    private boolean selected;

    public OrmarPainter(Ormar ormar) {
        this.ormar = ormar;
        this.selected = false;
    }

    @Override
    public void paint(Graphics2D g2d) {

        AffineTransform originalTransform = g2d.getTransform();

        int x = ormar.getLokacijaX();
        int y = ormar.getLokacijaY();
        int width = ormar.getWidth();
        int height = ormar.getHeight();


        int centerX = x + width / 2;
        int centerY = y + height / 2;


        g2d.rotate(Math.toRadians(ormar.getRotacija()), centerX, centerY);

        if (isSelected()) {
            g2d.setColor(Color.BLUE);
        } else {
            g2d.setColor(Color.BLACK);
        }


        g2d.drawRect(x, y, width, height);

        g2d.drawLine(x + width / 2, y, x + width / 2, y + height);


        int handleHeight = 10;
        int handleWidth = 5;


        g2d.drawRect(x + width / 4 - handleWidth / 2, y + height / 2 - handleHeight / 2, handleWidth, handleHeight);


        g2d.drawRect(x + 3 * width / 4 - handleWidth / 2, y + height / 2 - handleHeight / 2, handleWidth, handleHeight);


        g2d.setTransform(originalTransform);
    }

    @Override
    public Rectangle getBounds() {

        int width = ormar.getWidth();
        int height = ormar.getHeight();


        int centerX = ormar.getLokacijaX() + width / 2;
        int centerY = ormar.getLokacijaY() + height / 2;


        Rectangle bounds = new Rectangle(ormar.getLokacijaX(), ormar.getLokacijaY(), width, height);

        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(ormar.getRotacija()), centerX, centerY);

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
        return ormar.getLokacijaX();
    }

    @Override
    public int getY() {
        return ormar.getLokacijaY();
    }

    @Override
    public void setY(int newY) {
        ormar.setLokacijaY(newY);
    }

    @Override
    public void setX(int newX) {
        ormar.setLokacijaX(newX);
    }

    @Override
    public void setWidth(int newWidth) {
        ormar.setWidth(newWidth);
    }

    @Override
    public void setHeight(int newHeight) {
        ormar.setHeight(newHeight);
    }

    @Override
    public RoomElement getRoomElement() {
        return ormar;
    }
}
