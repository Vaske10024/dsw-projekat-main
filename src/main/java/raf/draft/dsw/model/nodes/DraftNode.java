package raf.draft.dsw.model.nodes;


import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.Setter;
import raf.draft.dsw.model.structures.Building;
import raf.draft.dsw.model.structures.Project;
import raf.draft.dsw.model.structures.ProjectExplorer;
import raf.draft.dsw.model.structures.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Getter
@Setter





@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public abstract class DraftNode {
    private static final AtomicLong idCounter = new AtomicLong(0);
    private long id;

    protected String naziv;
    protected String putanjaDoProjekta;


    private DraftNode parent;

    public DraftNode() {
        this.id = idCounter.incrementAndGet();
    }

    public DraftNode(String naziv, DraftNode parent) {
        this();
        this.naziv = naziv;
        this.parent = parent;
    }

    public long getId() {
        return id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public DraftNode getParent() {
        return parent;
    }

    public void setParent(DraftNode parent) {
        this.parent = parent;
    }

    public void setPutanjaDoProjekta(String putanjaDoProjekta) {
        this.putanjaDoProjekta = putanjaDoProjekta;
    }

    public String getPutanjaDoProjekta() {
        return putanjaDoProjekta;
    }
}
