package raf.draft.dsw.model.structures;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.Setter;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.nodes.DraftNodeComposite;

import java.util.ArrayList;
import java.util.UUID;

@Getter
@Setter
@JsonTypeName("project")
public class Project extends DraftNodeComposite {

    private String projectSpecificField;
    private boolean changed = false;
    private String autor;

    public Project() {
        super();
    }

    public Project(String naziv, DraftNode parent) {
        super(naziv, parent);
    }

    @Override
    public void addChild(DraftNode child) {
        super.addChild(child);
    }

    @JsonIgnore
    public ArrayList<Room> getAllRooms() {
        ArrayList<Room> rooms = new ArrayList<>();
        for (DraftNode child : this.getChildren()) {
            if (child instanceof Room) {
                rooms.add((Room) child);
            }
            if (child instanceof Building) {
                rooms.addAll(getRoomsFromBuilding((Building) child));
            }
        }
        return rooms;
    }
    @JsonIgnore
    private ArrayList<Room> getRoomsFromBuilding(Building building) {
        ArrayList<Room> rooms = new ArrayList<>();
        for (DraftNode child : building.getChildren()) {
            if (child instanceof Room) {
                rooms.add((Room) child);
            } else if (child instanceof Building) {
                rooms.addAll(getRoomsFromBuilding((Building) child));
            }
        }
        return rooms;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public String getProjectSpecificField() {
        return projectSpecificField;
    }

    public void setProjectSpecificField(String projectSpecificField) {
        this.projectSpecificField = projectSpecificField;
    }

}
