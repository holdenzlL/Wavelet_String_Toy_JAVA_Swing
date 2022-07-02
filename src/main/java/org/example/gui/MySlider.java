package org.example.gui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.Hashtable;

/**
 * An encapsulation of JSlider.
 * Set Labels, Ticks and other properties.
 * Used to control the speed/delay of the timer.
 * &#064;Auther  Zhilun LI
 * &#064;Date 02.07.2022
 * &#064;Version  Prototype
 */
public class MySlider extends JSlider{

    /**
     * Generate a Slider to control the speed of the input timer.
     * @param slider_min_delay
     * @param slider_max_delay
     * @param slider_initial
     * @param timer
     */
    MySlider(int slider_min_delay, int slider_max_delay, int slider_initial, Timer timer){

        super(slider_min_delay,slider_max_delay,slider_initial);

        Hashtable<Integer, JComponent> hashtable = new Hashtable<Integer, JComponent>();
        hashtable.put(slider_min_delay, new JLabel("Fast"));
        hashtable.put(slider_max_delay, new JLabel("Slow"));

        this.setPaintTicks(true);
        this.setMinorTickSpacing(10);
        this.setMajorTickSpacing(50);
        this.setPaintLabels(true);
        this.setLabelTable(hashtable);
        this.setOrientation(SwingConstants.HORIZONTAL);
        this.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider slider = (JSlider) e.getSource();
                timer.setDelay(slider.getValue());

                // change when stop
//                JSlider slider = (JSlider) e.getSource();
//                if (!slider.getValueIsAdjusting()) {
//                    int value = slider.getValue();
//                    timer.setDelay(value);
//                }
            }
        });
    }
}
