package org.example.gui;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * An encapsulation of JToggleButton.
 * Used to switch on/off the timer.
 * &#064;Auther  Zhilun LI
 * &#064;Date 02.07.2022
 * &#064;Version  Prototype
 */
public class MyToggleButton extends JToggleButton {

    /**
     * Generate a toggleButton to switch on/off the input timer.
     * Set the title to initialize the text of the button.
     * @param title
     * @param timer
     */
    MyToggleButton(String title, Timer timer){
        super(title);

        ItemListener itemListener = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                // event is generated in button
                int state = e.getStateChange();

                if (state == ItemEvent.SELECTED) {
                    setText("Stop");
                    timer.start();
                }
                else {
                    setText("Resume");
                    timer.stop();
                }
            }
        };
        this.addItemListener(itemListener);
    }
}
