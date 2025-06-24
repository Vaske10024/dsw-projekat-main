package raf.draft.dsw.gui.swing.Painter;

import raf.draft.dsw.model.structures.Elementi.RoomElement;
import raf.draft.dsw.model.structures.Elementi.WCSolja;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class WCSoljaPainter extends view.painters.Painter {
    private WCSolja wcSolja;
    private boolean selected;

    public WCSoljaPainter(WCSolja wcSolja) {
        this.wcSolja = wcSolja;
        this.selected = false;
    }

    @Override
    public void paint(Graphics2D g2d) {

        AffineTransform originalTransform = g2d.getTransform();


        int centerX = wcSolja.getLokacijaX() + wcSolja.getWidth() / 2;
        int centerY = wcSolja.getLokacijaY() + wcSolja.getHeight() / 2;


        g2d.rotate(Math.toRadians(wcSolja.getRotacija()), centerX, centerY);

        if (isSelected()) {
            g2d.setColor(Color.BLUE);
        } else {
            g2d.setColor(Color.BLACK);
        }

        g2d.drawArc(
                wcSolja.getLokacijaX(),
                wcSolja.getLokacijaY() + wcSolja.getHeight() / 2,
                wcSolja.getWidth(),
                wcSolja.getHeight(),
                0,
                -180
        );


        g2d.drawLine(
                wcSolja.getLokacijaX(),
                wcSolja.getLokacijaY(),
                wcSolja.getLokacijaX(),
                wcSolja.getLokacijaY() + wcSolja.getHeight()
        );
        g2d.drawLine(
                wcSolja.getLokacijaX() + wcSolja.getWidth(),
                wcSolja.getLokacijaY(),
                wcSolja.getLokacijaX() + wcSolja.getWidth(),
                wcSolja.getLokacijaY() + wcSolja.getHeight()
        );


        g2d.drawRect(
                wcSolja.getLokacijaX(),
                wcSolja.getLokacijaY(),
                wcSolja.getWidth(),
                wcSolja.getHeight() / 4
        );


        int inset = wcSolja.getWidth() / 4;
        int cutoutWidth = wcSolja.getWidth() / 2;
        int cutoutHeight = wcSolja.getHeight() / 2;

        g2d.drawArc(
                wcSolja.getLokacijaX() + inset,
                wcSolja.getLokacijaY() + wcSolja.getHeight() / 2,
                cutoutWidth,
                cutoutHeight,
                0,
                -180
        );


        g2d.drawLine(
                wcSolja.getLokacijaX() + inset,
                wcSolja.getLokacijaY() + wcSolja.getHeight() / 2 + cutoutHeight / 2,
                wcSolja.getLokacijaX() + inset,
                wcSolja.getLokacijaY() + wcSolja.getHeight() / 2
        );
        g2d.drawLine(
                wcSolja.getLokacijaX() + inset + cutoutWidth,
                wcSolja.getLokacijaY() + wcSolja.getHeight() / 2 + cutoutHeight / 2,
                wcSolja.getLokacijaX() + inset + cutoutWidth,
                wcSolja.getLokacijaY() + wcSolja.getHeight() / 2
        );


        g2d.drawLine(
                wcSolja.getLokacijaX() + inset,
                wcSolja.getLokacijaY() + wcSolja.getHeight() / 2,
                wcSolja.getLokacijaX() + inset + cutoutWidth,
                wcSolja.getLokacijaY() + wcSolja.getHeight() / 2
        );


        g2d.setTransform(originalTransform);
    }

    @Override
    public Rectangle getBounds() {

        int width = wcSolja.getWidth();
        int height = wcSolja.getHeight();


        int centerX = wcSolja.getLokacijaX() + width / 2;
        int centerY = wcSolja.getLokacijaY() + height / 2;


        Rectangle bounds = new Rectangle(wcSolja.getLokacijaX(), wcSolja.getLokacijaY(), width, height);


        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(wcSolja.getRotacija()), centerX, centerY);


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
        return wcSolja.getLokacijaX();
    }

    @Override
    public int getY() {
        return wcSolja.getLokacijaY();
    }

    @Override
    public void setY(int newY) {
        wcSolja.setLokacijaY(newY);
    }

    @Override
    public void setX(int newX) {
        wcSolja.setLokacijaX(newX);
    }

    @Override
    public void setWidth(int newWidth) {
        wcSolja.setWidth(newWidth);
    }

    @Override
    public void setHeight(int newHeight) {
        wcSolja.setHeight(newHeight);
    }

    @Override
    public RoomElement getRoomElement() {
        return wcSolja;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
