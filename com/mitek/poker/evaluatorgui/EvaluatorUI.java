package com.mitek.poker.evaluatorgui;


import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class EvaluatorUI extends JFrame implements WindowListener {

    private JLabel lblCard = null;

    public EvaluatorUI(String title) {
        super(title);
        this.setBounds(500, 300, 800, 600);

        lblCard = new JLabel("babajan");

        BufferedImage myPicture = null;
        try {
            myPicture = ImageIO.read(new File(".\\resources"));
        } catch (IOException e) {
            System.err.println("ERROR");
            e.printStackTrace();
        }
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        add(picLabel);

        this.add(lblCard);

        addWindowListener(this);
        // this.setVisible(true);
    }

    public void loadPic(String filename) {

    }

    @Override
    public void windowClosed(WindowEvent e) {
        System.out.println("windowClosed");

    }

    @Override
    public void windowActivated(WindowEvent e) {
        System.out.println("windowActivated");

    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.out.println("windowClosing");
        this.dispose();

    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        System.out.println("windowDeactivated");

    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        System.out.println("windowDeiconified");

    }

    @Override
    public void windowIconified(WindowEvent e) {
        System.out.println("");

    }

    @Override
    public void windowOpened(WindowEvent e) {
        System.out.println("");

    }

}
