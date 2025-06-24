package raf.draft.dsw.model.structures.Elementi;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.Setter;
import raf.draft.dsw.model.nodes.DraftNode;


@Getter
@Setter
@JsonTypeName("vesMasina")
public class VesMasina extends RoomElement {
    public VesMasina(String naziv, DraftNode parent) {
        super(naziv, parent);
    }


    public VesMasina() {

    }

    @Override
    public RoomElement Clone() {
        System.out.println("Napravljen Ormar");
        VesMasina vesMasina = null;

        try {
            vesMasina= (VesMasina)  super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        return vesMasina;
    }
}
