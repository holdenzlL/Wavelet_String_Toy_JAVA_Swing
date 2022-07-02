package org.example.gui;

import javax.swing.*;
import java.awt.*;

/**
 * The main window for this GUI program.
 * &#064;Auther  Zhilun LI
 * &#064;Date 02.07.2022
 * &#064;Version  Prototype
 */
public class MyFrame extends JFrame {
    MyPanel myPanel;

    /**
     * Generate the main window and set the title based on the input
     * @param title
     */
    public MyFrame(String title){
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(1000,380));

        this.setContentPane(new MyPanel());

        //Show in the middle of screen
//        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
