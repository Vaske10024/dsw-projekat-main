package view.painters;

import raf.draft.dsw.model.structures.Elementi.RoomElement;

import java.awt.*;


public abstract class Painter {

    public abstract void paint(Graphics2D g2d);
    public abstract Rectangle getBounds();
    protected boolean selected = false;

    public abstract int getX();
    public abstract int getY();

    public int getRotacija(){
        return getRoomElement().getRotacija();
    }
    public abstract void setY(int newY);
    public abstract void setX(int newY);
    public abstract void setWidth(int newWidth);
    public abstract void setHeight(int newHeight);
    public abstract RoomElement getRoomElement();

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    @Override public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass())
            return false; Painter painter = (Painter) obj;
            return getRoomElement().equals(painter.getRoomElement());
    }
    @Override public int hashCode() {
        return getRoomElement().hashCode();
    }
}
