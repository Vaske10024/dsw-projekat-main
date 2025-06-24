package raf.draft.dsw.model.structures.Elementi;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.Setter;
import raf.draft.dsw.model.nodes.DraftNode;



@Getter
@Setter
@JsonTypeName("ormar")
public class Ormar extends RoomElement {

    public Ormar() {
    }

    public Ormar(String naziv, DraftNode parent) {
        super(naziv, parent);
    }

    @Override
    public RoomElement Clone() {
        System.out.println("Napravljen Ormar");
        Ormar ormar = null;

        try {
            ormar= (Ormar)  super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        return ormar;
    }

}
