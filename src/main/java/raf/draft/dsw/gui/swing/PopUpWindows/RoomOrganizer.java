package raf.draft.dsw.gui.swing.PopUpWindows;

import raf.draft.dsw.gui.swing.JTree.controller.JTreeActions;
import raf.draft.dsw.gui.swing.MainFrame;
import raf.draft.dsw.gui.swing.RoomElementFactory.RoomElementFactory;
import raf.draft.dsw.gui.swing.RoomView;
import raf.draft.dsw.model.structures.Elementi.RoomElement;
import raf.draft.dsw.model.structures.Room;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.Collections;

public class RoomOrganizer extends JFrame {
    private JTextField widthField, heightField;
    private JComboBox<String> elementComboBox;
    private DefaultListModel<String> elementListModel;
    private JList<String> elementList;
    private JButton addButton, deleteAllButton, submitButton;

    private RoomElementFactory roomElementFactory;
    private ArrayList<RoomElement> listaElemenata;
    private JTreeActions jTreeActions;
    private Room room;
    private RoomView roomView;

    public RoomOrganizer() {
        jTreeActions = MainFrame.getUniqueInstance().getDraftTreeImplementation().getjTreeActions();
        listaElemenata = new ArrayList<>();
        roomView = MainFrame.getUniqueInstance().getMediator().getRoomView();
        room = roomView.getRoom();
        roomElementFactory = MainFrame.getUniqueInstance().getMediator().getStateMenager().getAddState().getRoomElementFactory();

        setTitle("Organize My Room");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(0xFAFAFA));
        setLayout(new BorderLayout(10, 10));

        // Style input panel
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        inputPanel.setBackground(new Color(0xFAFAFA));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel widthLabel = new JLabel("Width:");
        widthLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        widthLabel.setForeground(new Color(0x2D2D2D));
        inputPanel.add(widthLabel);

        widthField = new JTextField(5);
        widthField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        widthField.setBackground(new Color(0xFFFFFF));
        widthField.setForeground(new Color(0x2D2D2D));
        widthField.setBorder(BorderFactory.createLineBorder(new Color(0xE0E0E0), 1));
        inputPanel.add(widthField);

        JLabel heightLabel = new JLabel("Height:");
        heightLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        heightLabel.setForeground(new Color(0x2D2D2D));
        inputPanel.add(heightLabel);

        heightField = new JTextField(5);
        heightField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        heightField.setBackground(new Color(0xFFFFFF));
        heightField.setForeground(new Color(0x2D2D2D));
        heightField.setBorder(BorderFactory.createLineBorder(new Color(0xE0E0E0), 1));
        inputPanel.add(heightField);

        elementComboBox = new JComboBox<>(new String[]{"krevet", "sto", "vrata", "ormar", "bojler", "kada", "lavabo", "vesMasina", "wcSolja"});
        elementComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        elementComboBox.setBackground(new Color(0xFFFFFF));
        elementComboBox.setForeground(new Color(0x2D2D2D));
        elementComboBox.setBorder(BorderFactory.createLineBorder(new Color(0xE0E0E0), 1));
        inputPanel.add(elementComboBox);

        add(inputPanel, BorderLayout.NORTH);

        // Style list
        elementListModel = new DefaultListModel<>();
        elementList = new JList<>(elementListModel);
        elementList.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        elementList.setBackground(new Color(0xFFFFFF));
        elementList.setForeground(new Color(0x2D2D2D));
        elementList.setBorder(BorderFactory.createLineBorder(new Color(0xE0E0E0), 1));
        JScrollPane scrollPane = new JScrollPane(elementList);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        // Style button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.setBackground(new Color(0xFAFAFA));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        addButton = new JButton("Dodaj");
        deleteAllButton = new JButton("Obriši sve");
        submitButton = new JButton("Unesi");

        styleButton(addButton);
        styleButton(deleteAllButton);
        styleButton(submitButton);

        buttonPanel.add(addButton);
        buttonPanel.add(deleteAllButton);
        buttonPanel.add(submitButton);

        add(buttonPanel, BorderLayout.SOUTH);

        setupListeners();
    }

    private void styleButton(JButton button) {
        button.setOpaque(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setForeground(new Color(0x2D2D2D));
        button.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0xE8ECEF));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(null);
            }
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0xB0B0B0));
            }
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                button.setBackground(button.getModel().isRollover() ? new Color(0xE8ECEF) : null);
            }
        });

        button.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            protected void paintButtonPressed(Graphics g, AbstractButton b) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(0xB0B0B0));
                g2d.fill(new RoundRectangle2D.Float(0, 0, b.getWidth(), b.getHeight(), 8, 8));
                g2d.dispose();
            }


            protected void paintBackground(Graphics g, AbstractButton b) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (b.getModel().isRollover()) {
                    g2d.setColor(new Color(0xE8ECEF));
                } else {
                    g2d.setColor(new Color(0xFAFAFA));
                }
                g2d.fill(new RoundRectangle2D.Float(0, 0, b.getWidth(), b.getHeight(), 8, 8));
                g2d.dispose();
            }
        });
    }

    private void setupListeners() {
        addButton.addActionListener(e -> {
            String element = (String) elementComboBox.getSelectedItem();
            String widthText = widthField.getText();
            String heightText = heightField.getText();

            if (!isValidNumber(widthText) || !isValidNumber(heightText)) {
                JOptionPane.showMessageDialog(this, "Širina i visina moraju biti pozitivni brojevi!", "Greška", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int width = Integer.parseInt(widthText);
            int height = Integer.parseInt(heightText);

            if (width <= 0 || height <= 0 || width > room.getWidth() || height > room.getHeight()) {
                JOptionPane.showMessageDialog(this, "Širina i visina moraju biti unutar granica sobe!", "Greška", JOptionPane.ERROR_MESSAGE);
                return;
            }

            RoomElement element1 = roomElementFactory.createClone(element);
            element1.setWidth((int) (width * 1.4));
            element1.setHeight((int) (height * 1.4));

            elementListModel.addElement(element + " (" + width + " x " + height + ")");
            listaElemenata.add(element1);
        });

        deleteAllButton.addActionListener(e -> {
            elementListModel.clear();
            listaElemenata.clear();
        });

        submitButton.addActionListener(e -> {
            if (listaElemenata.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nema elemenata za organizaciju!", "Greška", JOptionPane.WARNING_MESSAGE);
                return;
            }

            organizeElements(listaElemenata);

            for (RoomElement roomElement : listaElemenata) {
                if (!room.getChildren().contains(roomElement)) {
                    room.getChildren().add(roomElement);
                    jTreeActions.addRoomElementToTree(room, roomElement);
                }
            }

            for (RoomElement roomElement : listaElemenata) {
                view.painters.Painter painter = roomElementFactory.createPainterForClone(roomElement);
                roomView.getPainters().add(painter);
            }
            roomView.repaint();
        });
    }

    private void organizeElements(ArrayList<RoomElement> listaElemenata) {
        if (listaElemenata.isEmpty()) {
            System.out.println("Lista elemenata je prazna!");
            return;
        }

        int roomHeight = room.getHeight();
        int roomWidth = room.getWidth();
        int roomOffsetX = room.getRoomOffset().x;
        int roomOffsetY = room.getRoomOffset().y;

        int maxWidth = listaElemenata.stream().mapToInt(RoomElement::getWidth).max().orElse(0);
        int maxHeight = listaElemenata.stream().mapToInt(RoomElement::getHeight).max().orElse(0);

        if (maxWidth > roomWidth || maxHeight > roomHeight) {
            JOptionPane.showMessageDialog(this, "Neki elementi su preveliki za sobu!", "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int n = roomHeight / maxHeight;
        int m = roomWidth / maxWidth;

        if (n * m < listaElemenata.size()) {
            JOptionPane.showMessageDialog(this, "Nema dovoljno prostora u sobi za sve elemente!", "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Collections.shuffle(listaElemenata);

        boolean[][] occupied = new boolean[n][m];
        int[] dx = {0, 1, 0, -1};
        int[] dy = {1, 0, -1, 0};
        int x = 0, y = 0, dir = 0;

        for (RoomElement elem : listaElemenata) {
            elem.setLokacijaX(y * maxWidth + roomOffsetX);
            elem.setLokacijaY(x * maxHeight + roomOffsetY);

            occupied[x][y] = true;

            int nextX = x + dx[dir];
            int nextY = y + dy[dir];
            if (nextX < 0 || nextY < 0 || nextX >= n || nextY >= m || occupied[nextX][nextY]) {
                dir = (dir + 1) % 4;
            }
            x += dx[dir];
            y += dy[dir];
        }
    }

    private boolean isValidNumber(String text) {
        return text.matches("\\d+");
    }
}