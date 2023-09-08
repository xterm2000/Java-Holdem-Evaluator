package com.mitek.poker.evaluatorgui;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.mitek.poker.evaluator.ListArray;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class EvaluatorUI extends JFrame implements WindowListener {

    public ArrayList<Image> cards = new ArrayList<Image>();

    public EvaluatorUI(String title) {
        super(title);
        this.setBounds(500, 300, 800, 600);
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 55));
        loadPic(".\\resources\\cards\\png\\");
        for (Image i : cards) {
            JLabel lbl = new JLabel(i.toString().substring(1, 30), new ImageIcon(i), JLabel.TRAILING);

            lbl.addMouseListener(new MouseListener() {
                @Override
                public void mouseReleased(java.awt.event.MouseEvent e) {
                }

                @Override
                public void mousePressed(java.awt.event.MouseEvent e) {
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                }

                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    JLabel l = (JLabel)e.getSource();
                    System.out.println("entered" + l.getText());

                }

                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                }
            });
            this.add(lbl);
        }

        addWindowListener(this);

    }

    public void loadPic(String dirName) {
        File dir = new File(dirName);
        File[] files = dir.listFiles();
        for (File f : files) {
            if (f.isFile() && f.getName().endsWith(".png")) {
                try {
                    BufferedImage img = ImageIO.read(f);
                    cards.add(img.getScaledInstance(50, 72, 0));
                } catch (IOException e) {
                    System.err.println("ERROR");
                    e.printStackTrace();
                }
            }

        }
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
