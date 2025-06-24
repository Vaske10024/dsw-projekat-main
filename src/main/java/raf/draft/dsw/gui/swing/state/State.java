package raf.draft.dsw.gui.swing.state;

import raf.draft.dsw.gui.swing.RoomView;
import raf.draft.dsw.model.structures.Room;

public interface State {

    //U ovom kontekstu State interface treba da ima nekoliko
    //ponudjenih akcija koje se mogu koristiti unutar ostalih stejtova
    //ne koristi svaki state sve od akcija nego ih jednostavno
    //overriduje tako da ne rade nista.
    void naKlikMisa(RoomView roomView, int x, int y);  // Sta se desava kad kliknemo
    void naMouseDragged(RoomView roomView, int x, int y);  // Sta se desava kad vucemo
    void naPretisnutoDugme(RoomView roomView, String key);     // Reakcija na pritisak tastera
    void naZoom(RoomView roomView, double zoomFactor); // Za zoom

}
