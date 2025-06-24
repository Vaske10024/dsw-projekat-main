package raf.draft.dsw.gui.swing.JTree.view;

import lombok.NoArgsConstructor;
import raf.draft.dsw.gui.swing.JTree.model.DraftTreeItem;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.structures.Building;
import raf.draft.dsw.model.structures.Elementi.*;
import raf.draft.dsw.model.structures.Project;
import raf.draft.dsw.model.structures.ProjectExplorer;
import raf.draft.dsw.model.structures.Room;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.net.URL;


@NoArgsConstructor
public class DraftTreeCellRenderer extends DefaultTreeCellRenderer {

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

        URL imageURL = null;

        if (value instanceof DraftTreeItem) {
            DraftTreeItem draftTreeItem = (DraftTreeItem) value;
            DraftNode node = draftTreeItem.getDraftNode();

            // Provera tipa čvora i izbor ikone
            if (node instanceof ProjectExplorer) {
                imageURL = getClass().getResource("/images/projectexplorer.jpg");
            } else if (node instanceof Project) {
                imageURL = getClass().getResource("/images/project.jpg");
            } else if (node instanceof Building) {
                imageURL = getClass().getResource("/images/building.png");
            } else if (node instanceof Room) {
                imageURL = getClass().getResource("/images/room.jpg");
            } else if(node instanceof Krevet){
                imageURL = getClass().getResource("/images/bed.png");
            }
            else if(node instanceof Bojler){
                imageURL = getClass().getResource("/images/boiler.jpg");
            }
            else if(node instanceof VesMasina){
                imageURL = getClass().getResource("/images/vesmasina5.jpg");
            }
            else if(node instanceof Lavabo){
                imageURL = getClass().getResource("/images/lavabo.jpg");
            }
            else if(node instanceof WCSolja){
                imageURL = getClass().getResource("/images/wcsolja2.png");
            }
            else if(node instanceof Sto){
                imageURL = getClass().getResource("/images/sto.png");
            }
            else if(node instanceof Ormar){
                imageURL = getClass().getResource("/images/ormar4.png");
            }
            else if(node instanceof Vrata){
                imageURL = getClass().getResource("/images/door.png");
            }
            else if(node instanceof Kada){
                imageURL = getClass().getResource("/images/kada.png");
            }
        }
        if (imageURL != null) {
            setIcon(new ImageIcon(imageURL));
        }

        return this;
    }
}
