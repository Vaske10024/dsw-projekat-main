package raf.draft.dsw.gui.swing.Painter;

import raf.draft.dsw.model.structures.Elementi.RoomElement;
import raf.draft.dsw.model.structures.Elementi.VesMasina;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class VesMasinaPainter extends view.painters.Painter {

    private VesMasina vesMasina;
    private boolean selected;

    public VesMasinaPainter(VesMasina vesMasina) {
        this.vesMasina = vesMasina;
        this.selected = false;
    }

    @Override
    public void paint(Graphics2D g2d) {

        AffineTransform originalTransform = g2d.getTransform();


        int centerX = vesMasina.getLokacijaX() + vesMasina.getWidth() / 2;
        int centerY = vesMasina.getLokacijaY() + vesMasina.getHeight() / 2;


        g2d.rotate(Math.toRadians(vesMasina.getRotacija()), centerX, centerY);


        if (isSelected()) {
            g2d.setColor(Color.BLUE);
        } else {
            g2d.setColor(Color.BLACK);
        }


        g2d.drawRect(
                vesMasina.getLokacijaX(),
                vesMasina.getLokacijaY(),
                vesMasina.getWidth(),
                vesMasina.getHeight()
        );


        int circleDiameter = Math.min(vesMasina.getWidth(), vesMasina.getHeight()) / 2;
        g2d.drawOval(
                centerX - circleDiameter / 2,
                centerY - circleDiameter / 2,
                circleDiameter,
                circleDiameter
        );


        g2d.setTransform(originalTransform);
    }

    @Override
    public Rectangle getBounds() {

        int width = vesMasina.getWidth();
        int height = vesMasina.getHeight();


        int centerX = vesMasina.getLokacijaX() + width / 2;
        int centerY = vesMasina.getLokacijaY() + height / 2;


        Rectangle bounds = new Rectangle(
                vesMasina.getLokacijaX(),
                vesMasina.getLokacijaY(),
                width,
                height
        );


        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(vesMasina.getRotacija()), centerX, centerY);


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
        return vesMasina.getLokacijaX();
    }

    @Override
    public int getY() {
        return vesMasina.getLokacijaY();
    }

    @Override
    public void setY(int newY) {
        vesMasina.setLokacijaY(newY);
    }

    @Override
    public void setX(int newX) {
        vesMasina.setLokacijaX(newX);
    }

    @Override
    public void setWidth(int newWidth) {
        vesMasina.setWidth(newWidth);
    }

    @Override
    public void setHeight(int newHeight) {
        vesMasina.setHeight(newHeight);
    }

    @Override
    public RoomElement getRoomElement() {
        return vesMasina;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
