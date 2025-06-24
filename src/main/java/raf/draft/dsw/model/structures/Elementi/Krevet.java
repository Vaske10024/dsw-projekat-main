package raf.draft.dsw.model.structures.Elementi;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.Setter;
import raf.draft.dsw.model.nodes.DraftNode;

@Getter
@Setter
@JsonTypeName("krevet")
public class Krevet extends RoomElement {
    public Krevet(String naziv, DraftNode parent) {
        super(naziv, parent);
    }

    public Krevet() {

    }

    @Override
    public RoomElement Clone() {
        System.out.println("Napravljen Krevet");
        Krevet krevet = null;

        try {
            krevet= (Krevet)  super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        return krevet;
    }

}
