package raf.draft.dsw.model.structures.Elementi;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.Setter;
import raf.draft.dsw.model.nodes.DraftNode;


@Getter
@Setter
@JsonTypeName("wcSolja")
public class WCSolja extends RoomElement {
    public WCSolja(String naziv, DraftNode parent) {
        super(naziv, parent);
    }


    public WCSolja() {
    }

    @Override
    public RoomElement Clone() {
        System.out.println("Napravljena WCSolja");
        WCSolja wcSolja = null;

        try {
            wcSolja= (WCSolja)  super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        return wcSolja;
    }
}
