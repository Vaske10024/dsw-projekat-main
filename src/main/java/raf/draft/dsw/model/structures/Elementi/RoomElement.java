package raf.draft.dsw.model.structures.Elementi;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import raf.draft.dsw.controller.Serialization.RoomElementDeserializer;
import raf.draft.dsw.controller.Serialization.RoomElementSerializer;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.structures.Building;
import raf.draft.dsw.model.structures.Project;
import raf.draft.dsw.model.structures.ProjectExplorer;
import raf.draft.dsw.model.structures.Room;

@Getter
@Setter

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "@class")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Bojler.class, name = "bojler"),
        @JsonSubTypes.Type(value = Kada.class, name = "kada"),
        @JsonSubTypes.Type(value = Krevet.class, name = "krevet"),
        @JsonSubTypes.Type(value = Lavabo.class, name = "lavabo"),
        @JsonSubTypes.Type(value = Ormar.class, name = "ormar"),
        @JsonSubTypes.Type(value = Sto.class, name = "sto"),
        @JsonSubTypes.Type(value = VesMasina.class, name = "vesMasina"),
        @JsonSubTypes.Type(value = Vrata.class, name = "vrata"),
        @JsonSubTypes.Type(value = WCSolja.class, name = "wcSolja"),
})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

public abstract class RoomElement extends DraftNode implements RoomElementPrototype {
    private int lokacijaX;
    private int lokacijaY;
    private int width;
    private int height;
    private int rotacija=0;


    public RoomElement() {
        super();
    }

    public RoomElement(String naziv, DraftNode parent) {
        super(naziv, parent);
    }


    public int getLokacijaX() {
        return lokacijaX;
    }

    public void setLokacijaX(int lokacijaX) {
        this.lokacijaX = lokacijaX;
    }

    public int getLokacijaY() {
        return lokacijaY;
    }

    public void setLokacijaY(int lokacijaY) {
        this.lokacijaY = lokacijaY;
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

    public int getRotacija() {
        return rotacija;
    }

    public void setRotacija(int rotacija) {
        this.rotacija = rotacija;
    }

    public boolean overlaps(RoomElement other) {
        return !(this.lokacijaX + this.width <= other.lokacijaX || // desna strana check
                this.lokacijaX >= other.lokacijaX + other.width || // leva strana check
                this.lokacijaY + this.height <= other.lokacijaY || // donja strana check
                this.lokacijaY >= other.lokacijaY + other.height); // gornja strana check
    }
}
