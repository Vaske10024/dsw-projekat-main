package raf.draft.dsw.model.structures.Elementi;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.Setter;
import raf.draft.dsw.model.nodes.DraftNode;



@Getter
@Setter
@JsonTypeName("kada")
public class Kada extends RoomElement {



    public Kada(String naziv, DraftNode parent) {
        super(naziv, parent);
    }


    public Kada() {
    }

    @Override
    public RoomElement Clone() {
        System.out.println("Napravljena Kada");
        Kada kada = null;

        try {
            kada= (Kada)  super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        return kada;
    }
}
