package raf.draft.dsw.gui.swing;

import raf.draft.dsw.controller.Command.CommandHistory;
import raf.draft.dsw.gui.swing.Painter.*;
import raf.draft.dsw.gui.swing.state.Mediator;
import raf.draft.dsw.gui.swing.state.concreteStates.ZoomState;
import raf.draft.dsw.model.nodes.DraftNode;
import raf.draft.dsw.model.structures.Elementi.*;
import raf.draft.dsw.model.structures.Room;
import view.painters.Painter;

import javax.swing.*;
import java.awt.*;

import java.awt.event.*;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class RoomView extends JPanel implements MouseListener, MouseMotionListener,MouseWheelListener,KeyListener {
    //treba da se implementira interfejs sa listeneri koji ce da prate
    // sta se dogadja na tabli i to se salje u mediator gde se povezuje sa state
    // treba da implementira subscriber za publishera koji je room da se
    // slikaju elementi u sobi

    /// Mozemo da pomocne metode prabacimo u Pomocnu klasu, ali sad za sad neka ih ovako.
    private Room room;
    private double scaleFactor = 1.0;
    private JLabel zoomLabel;
    private JPanel drawingPanel;
    private ArrayList<view.painters.Painter> painters= new ArrayList<>();
    private Mediator mediator;
    private Point offsetSobe;
    private String elementKojiHocemoDaDodamo;
    private Point startingPoint=new Point(0,0);
    private Point endingPoint=new Point(0,0);
    private Rectangle selectionRectangle;
    private final double MAX_ZOOM = 3.0; // Gornja granica
    private final double MIN_ZOOM = 0.5; // Donja granica
    private CommandHistory commandHistory;


    public RoomView(Room room, Mediator mediator) {
        this.mediator=mediator;
        this.room = room;
        this.offsetSobe=room.getRoomOffset();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startingPoint = e.getPoint();
                mediator.startAction(e);
                requestFocusInWindow();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                selectionRectangle = null;
                mediator.startAction(e);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                mediator.startAction(e);
                requestFocusInWindow();
            }



        });
        addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (mediator.getStateMenager().getCurretState() instanceof ZoomState) {
                    mediator.getStateMenager().getCurretState().naZoom(RoomView.this, e.getWheelRotation());
                    double zoomStep = 0.1;
                    Point mousePosition = e.getPoint();
                    double mouseLogicalX = (mousePosition.x - offsetSobe.x) / scaleFactor;
                    double mouseLogicalY = (mousePosition.y - offsetSobe.y) / scaleFactor;
                    if (e.getWheelRotation() < 0) {
                        scaleFactor = Math.min(MAX_ZOOM, scaleFactor + zoomStep);
                    } else {
                        scaleFactor = Math.max(MIN_ZOOM, scaleFactor - zoomStep);
                    }
                    offsetSobe.x = (int) (mousePosition.x - mouseLogicalX * scaleFactor);
                    offsetSobe.y = (int) (mousePosition.y - mouseLogicalY * scaleFactor);
                    setOffsetSobe(new Point(offsetSobe.x,offsetSobe.y));
                    updateZoomLabel();
                    revalidate();
                    repaint();
                }


            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("Key pressed: " + e.getKeyCode());
                if (e.getKeyCode() == KeyEvent.VK_V && (e.isControlDown() || e.isMetaDown())) {
                    System.out.println("PogRoomView");
                    mediator.getStateMenager().getCurretState().naPretisnutoDugme(RoomView.this, "Ctrl+V");
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                mediator.startAction(e);
                requestFocusInWindow();
            }
        });
        addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if(mediator.getStateMenager().getCurretState() instanceof ZoomState) {
                    mediator.getStateMenager().getCurretState().naZoom(RoomView.this, e.getWheelRotation());
                }

            }
        });

        setFocusable(true);    ///Novo
        requestFocusInWindow();   ///Novo
        updatePainters();
        setLayout(new BorderLayout());
        addZoomLabel();
        initializeDrawingPanel();
        fitToPanel();
    }

    public void switchToRoom(Room newRoom) {
        this.room = newRoom;
        // 2. Ažuriramo listu paintera na osnovu nove sobe
        updatePainters();
        revalidate();  // Osvežavamo raspored komponenata (ako je potrebno)
        repaint();     // Ponovo iscrtavamo celu sobu i njene elemente
    }

    private void addZoomLabel() {
        zoomLabel = new JLabel();
        updateZoomLabel();
        zoomLabel.setOpaque(true);
        zoomLabel.setBackground(Color.LIGHT_GRAY);
        zoomLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(zoomLabel, BorderLayout.SOUTH);
    }
    private void drawSelectionRectangle(Graphics2D g2d, Point roomOffset) {
        if (selectionRectangle != null) {
            int scaledX = (int) (selectionRectangle.x * scaleFactor);
            int scaledY = (int) (selectionRectangle.y * scaleFactor);
            int scaledWidth = (int) (selectionRectangle.width * scaleFactor);
            int scaledHeight = (int) (selectionRectangle.height * scaleFactor);

            g2d.drawRect((int) ((scaledX + roomOffset.x)/scaleFactor), (int) ((scaledY + roomOffset.y)/scaleFactor), (int) (scaledWidth/scaleFactor), (int) (scaledHeight/scaleFactor));
        }
    }
    private void updatePainters() {
        painters.clear();
        for (DraftNode element : room.getChildren()) {
            if (element instanceof Bojler) {
                painters.add(new BojlerPainter((Bojler) element));
            }
            else if (element instanceof Lavabo) {
                painters.add(new LavaboPainter((Lavabo) element));
            }
            else if(element instanceof Kada){
                painters.add(new KadaPainter((Kada) element));
            }
            else if(element instanceof Krevet){
                painters.add(new KrevetPainter((Krevet) element));
            }
            else if(element instanceof Sto){
                painters.add(new StoPainter((Sto) element));
            }
            else if(element instanceof VesMasina){
                painters.add(new VesMasinaPainter((VesMasina) element));
            }
            else if(element instanceof Vrata){
                painters.add(new VrataPainter((Vrata) element));
            }
            else if(element instanceof WCSolja){
                painters.add(new WCSoljaPainter((WCSolja) element));
            }
            else if(element instanceof Ormar){
                painters.add(new OrmarPainter((Ormar) element));
            }
        }
    }


    public void updateZoomLabel() {
        zoomLabel.setText(String.format("Zoom: %.0f%%", scaleFactor * 100));
    }
    int i =0;

    private void initializeDrawingPanel() {

        drawingPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                if(i==0){
                    fitToPanel();
                    i++;
                }
                Graphics2D g2d = (Graphics2D) g;
                drawGrid(g2d);
                Point roomOffset = drawRoom(g2d);
                offsetSobe = roomOffset;
                AffineTransform originalTransform = g2d.getTransform();
                g2d.scale(scaleFactor, scaleFactor);
                for (view.painters.Painter painter : painters) {
                    g2d.translate(roomOffset.x / scaleFactor, roomOffset.y / scaleFactor);
                    if (mediator.getSelectedNodes().contains(painter)) {
                        Rectangle bounds = painter.getBounds();
                        g2d.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
                    }
                    painter.paint(g2d);
                    g2d.translate(-roomOffset.x / scaleFactor, -roomOffset.y / scaleFactor);
                }
                 drawSelectionRectangle(g2d,offsetSobe);
                g2d.setTransform(originalTransform);
            }

            @Override
            public Dimension getPreferredSize() {
                return getSize();
            }
        };

        add(drawingPanel, BorderLayout.CENTER);
    }

    private void drawGrid(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.LIGHT_GRAY);
        int gridSize = (int) (20 * scaleFactor);
        int width = drawingPanel.getWidth();
        int height = drawingPanel.getHeight();
        for (int x = 0; x < width; x += gridSize) {
            g2d.drawLine(x, 0, x, height);
        }
        for (int y = 0; y < height; y += gridSize) {
            g2d.drawLine(0, y, width, y);
        }
    }

    public Point drawRoom(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        if (room != null && room.getWidth() > 0 && room.getHeight() > 0) {
            int scaledWidth = (int) (room.getWidth() * scaleFactor);
            int scaledHeight = (int) (room.getHeight() * scaleFactor);

            int xOffset = offsetSobe.x;
            int yOffset = offsetSobe.y;

            g2d.setColor(room.getColor() != null ? room.getColor() : Color.WHITE);
            g2d.fillRect(xOffset, yOffset, scaledWidth, scaledHeight);

            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke((float) (3 * scaleFactor)));
            g2d.drawRect(xOffset, yOffset, scaledWidth, scaledHeight);

            return new Point(xOffset, yOffset); // Vraćamo offset sobe
        }
        return new Point(0, 0); // Ako nema sobe
    }



    public void fitToPanel() {
        if (room != null && room.getWidth() > 0 && room.getHeight() > 0) {
            Dimension panelSize = drawingPanel.getSize();
            double roomWidth = room.getWidth();
            double roomHeight = room.getHeight();

            // Proračunavanje odgovarajućeg zoom-a
            double widthRatio = panelSize.width / roomWidth;
            double heightRatio = panelSize.height / roomHeight;
            scaleFactor = Math.min(Math.min(widthRatio, heightRatio), MAX_ZOOM);
            scaleFactor = Math.max(scaleFactor, MIN_ZOOM);

            // Centriranje sobe
            int xOffset = (int) ((panelSize.width - roomWidth * scaleFactor) / 2);
            int yOffset = (int) ((panelSize.height - roomHeight * scaleFactor) / 2);
            offsetSobe = new Point(xOffset, yOffset);

            updateZoomLabel();
            revalidate();
            repaint();
        }
    }



    public void addPainter(view.painters.Painter painter) {
        painters.add(painter);
        repaint();
    }

    /*
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        double zoomStep = 0.1;

        // Zoom in or out depending on the wheel rotation
        if (e.getWheelRotation() < 0) {
            scaleFactor += zoomStep;
        } else {
            scaleFactor = Math.max(0.1, scaleFactor - zoomStep);
        }

        // Repaint to reflect the changes
        updateZoomLabel();
        revalidate();
        repaint();
    }
*/

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public CommandHistory getCommandHistory() {
        return commandHistory;
    }

    public void setCommandHistory(CommandHistory commandHistory) {
        this.commandHistory = commandHistory;
    }

    public void setOffsetSobe(Point offsetSobe) {
        this.offsetSobe = offsetSobe;
        room.setRoomOffset(offsetSobe);
        revalidate();
        repaint();
    }

    public Rectangle getRoomBounds() {
        return new Rectangle(getRoomOffset().x, getRoomOffset().y, getWidth(), getHeight());
    }
    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public double getScaleFactor() {
        return scaleFactor;
    }

    public void setScaleFactor(double scaleFactor) {
        this.scaleFactor = scaleFactor;
    }

    public JLabel getZoomLabel() {
        return zoomLabel;
    }

    public void setZoomLabel(JLabel zoomLabel) {
        this.zoomLabel = zoomLabel;
    }

    public JPanel getDrawingPanel() {
        return drawingPanel;
    }

    public void setDrawingPanel(JPanel drawingPanel) {
        this.drawingPanel = drawingPanel;
    }

    public ArrayList<Painter> getPainters() {
        return painters;
    }

    public void setPainters(ArrayList<Painter> painters) {
        this.painters = painters;
    }

    public Mediator getMediator() {
        return mediator;
    }

    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    public String getElementKojiHocemoDaDodamo() {
        return elementKojiHocemoDaDodamo;
    }

    public void setElementKojiHocemoDaDodamo(String elementKojiHocemoDaDodamo) {
        this.elementKojiHocemoDaDodamo = elementKojiHocemoDaDodamo;
    }

    public Point getRoomOffset() {
        return offsetSobe;
    }




    public Point getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(Point startingPoint) {
        this.startingPoint = startingPoint;
    }

    public Point getEndingPoint() {
        return endingPoint;
    }

    public void setEndingPoint(Point endingPoint) {
        this.endingPoint = endingPoint;
    }
    public Rectangle getSelectionRectangle() {
        return selectionRectangle;
    }

    public void setSelectionRectangle(Rectangle selectionRectangle) {
        this.selectionRectangle = selectionRectangle;
    }


    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

    }
}