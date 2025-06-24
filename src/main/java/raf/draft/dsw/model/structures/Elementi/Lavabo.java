package raf.draft.dsw.model.structures.Elementi;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.Setter;
import raf.draft.dsw.model.nodes.DraftNode;



@Getter
@Setter
@JsonTypeName("lavabo")
public class Lavabo extends RoomElement {

    public Lavabo() {
    }

    public Lavabo(String naziv, DraftNode parent) {
        super(naziv, parent);
    }

    @Override
    public RoomElement Clone() {
        System.out.println("Napravljen Lavabo");
        Lavabo lavabo = null;

        try {
            lavabo= (Lavabo)  super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        return lavabo;
    }
}
