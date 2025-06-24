package raf.draft.dsw.model.structures.Elementi;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.Setter;
import raf.draft.dsw.model.nodes.DraftNode;



@Getter
@Setter
@JsonTypeName("sto")
public class Sto extends RoomElement {
    public Sto(String naziv, DraftNode parent) {
        super(naziv, parent);
    }


    public Sto() {
    }

    @Override
    public RoomElement Clone() {
        System.out.println("Napravljen Ormar");
        Sto  sto   = null;

        try {
            sto= (Sto)  super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        return sto;
    }
}
