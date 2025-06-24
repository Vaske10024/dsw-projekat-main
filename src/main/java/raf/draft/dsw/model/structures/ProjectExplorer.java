package raf.draft.dsw.model.structures;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.nodes.DraftNodeComposite;

import java.util.UUID;

@Getter
@Setter
public class ProjectExplorer extends DraftNodeComposite {

    private String ime;
    private String autor;
    private String putanjaDoProjekta;



    public ProjectExplorer() {
        super();
    }

    public ProjectExplorer(String naziv) {
        super(naziv, null);
    }



    @Override
    public void addChild(DraftNode child) {
        if (child != null &&  child instanceof Project){
            Project project = (Project) child;
            if (!this.getChildren().contains(project)){
                this.getChildren().add(project);
            }
        }
    }
}
