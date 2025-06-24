package raf.draft.dsw.model.structures.Elementi;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.Setter;
import raf.draft.dsw.model.nodes.DraftNode;



@Getter
@Setter
@JsonTypeName("vrata")
public class Vrata extends RoomElement {
    public Vrata(String naziv, DraftNode parent) {
        super(naziv, parent);
    }


    public Vrata() {

    }

    @Override
    public RoomElement Clone() {
        System.out.println("Napravljena Vrata");
        Vrata vrata = null;

        try {
            vrata= (Vrata)  super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        return vrata;
    }
}
