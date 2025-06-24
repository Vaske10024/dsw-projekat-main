package raf.draft.dsw.model.structures;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import raf.draft.dsw.controller.Serialization.ColorDeserializer;
import raf.draft.dsw.controller.Serialization.ColorSerializer;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.nodes.DraftNodeComposite;
import raf.draft.dsw.model.structures.Elementi.RoomElement;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;



@Getter
@Setter
@JsonTypeName("room")
public class Room extends DraftNodeComposite {

    private Color color;
    private int width;
    private int height;
    private Point roomOffset = new Point(0, 0);

    //Potrebna lista elemenata koji se nalaze u sobi.




    public Room() {
    }

    public boolean addChild(RoomElement newElement) {
        // Proveravamo da li se novi element preklapa sa postojećim elementima
        for (DraftNode element : this.getChildren()) {
            RoomElement element1 = (RoomElement) element;
            if (element1.overlaps(newElement)) {
                return false; // Ne možemo dodati element, jer se preklapa
            }
        }

        // Ako nema preklapanja, dodajemo element
        this.getChildren().add(newElement);
        return true;
    }
    public boolean doesOverlap(RoomElement newElement) {
        for (DraftNode child : getChildren()) {
            if (child instanceof RoomElement) {
                RoomElement existingElement = (RoomElement) child;

                int existingLeft = existingElement.getLokacijaX();
                int existingRight = existingLeft + existingElement.getWidth();
                int existingTop = existingElement.getLokacijaY();
                int existingBottom = existingTop + existingElement.getHeight();

                int newLeft = newElement.getLokacijaX();
                int newRight = newLeft + newElement.getWidth();
                int newTop = newElement.getLokacijaY();
                int newBottom = newTop + newElement.getHeight();

                boolean overlap = !(newRight <= existingLeft || newLeft >= existingRight ||
                        newBottom <= existingTop || newTop >= existingBottom);

                if (overlap) {
                    return true;
                }
            }
        }
        return false;
    }
    public Room(String naziv, DraftNode parent) {
        super(naziv, parent);
    }
    public void copyFrom(Room other) {
        this.naziv = other.naziv;
        setChildren(new ArrayList<>(other.getChildren())); // Pretpostavljamo da ima listu elemenata
        this.setRoomOffset(other.getRoomOffset());
        this.setWidth(other.getWidth());
        this.setHeight(other.getHeight());
    }



    public Point getRoomOffset() {
        return roomOffset;
    }

    public void setRoomOffset(Point roomOffset) {
        this.roomOffset = roomOffset;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
