package raf.draft.dsw.gui.swing.RoomElementFactory;

import raf.draft.dsw.gui.swing.Painter.*;
import raf.draft.dsw.model.structures.Elementi.*;

import java.util.HashMap;
import java.util.Map;

public class RoomElementFactory {
    private Map<String, RoomElement> prototypes = new HashMap<>();


    public void registerPrototype(String key, RoomElement prototype) {
        prototypes.put(key, prototype);
    }


    public RoomElement createClone(String key) {
        if (key == null || !prototypes.containsKey(key)) {
            throw new IllegalArgumentException("Prototype not found for key: " + key);
        }
        return prototypes.get(key).Clone();
    }


    public view.painters.Painter createPainterForClone(RoomElement element) {
        if (element instanceof Bojler) {
            return new BojlerPainter((Bojler) element);
        } else if (element instanceof Lavabo) {
            return new LavaboPainter((Lavabo) element);
        }
        else if(element instanceof Kada){
            return new KadaPainter((Kada) element);
        }
        else if(element instanceof Krevet){
            return new KrevetPainter((Krevet) element);
        }
        else if(element instanceof Sto){
            return new StoPainter((Sto) element);
        }
        else if(element instanceof VesMasina){
            return new VesMasinaPainter((VesMasina) element);
        }
        else if(element instanceof Vrata){
            return new VrataPainter((Vrata) element);
        }
        else if(element instanceof WCSolja){
            return new WCSoljaPainter((WCSolja) element);
        }
        else if(element instanceof Ormar){
            return new OrmarPainter((Ormar) element);
        }
        return null;
    }
}

