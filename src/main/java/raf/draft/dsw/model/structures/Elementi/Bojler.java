package raf.draft.dsw.model.structures.Elementi;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.Setter;
import raf.draft.dsw.model.nodes.DraftNode;

@Getter
@Setter
@JsonTypeName("bojler")
public class Bojler extends RoomElement {


    public Bojler() {
        super();
    }

    public Bojler(String naziv, DraftNode parent) {
        super(naziv, parent);
    }

    @Override
    public RoomElement Clone() {
        System.out.println("Napravljen bojler");
        Bojler bojler = null;

        try {
            bojler= (Bojler)  super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("bb"+bojler);
        setId(this.getId()+1);
        return bojler;
    }
}
