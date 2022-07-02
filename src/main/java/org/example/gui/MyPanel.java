package org.example.gui;

import javax.swing.*;

/**
 * A component for integrating and wrapping other classes to provide a handy panel.
 * Box and Grid layouts are mainly used.
 * It contains a timer for speed control, a toggleButton for play/pause, a JLabel as a counter, several ArrayBlocks for
 * drawing blocks and the class Wavelet as the main functional component.
 * &#064;Auther  Zhilun LI
 * &#064;Date 02.07.2022
 * &#064;Version  Prototype
 */
public class MyPanel extends JPanel {

    private final int _PANEL_WIDTH = 500;
    private final int _PANEL_HEIGHT = 500;
//    private final int _SLIDER_WIDTH = 300;
//    private final int _SLIDER_HEIGHT = 400;
    private final int _SLIDER_MIN = 200;
    private final int _SLIDER_MAX = 500;
    private int _SLIDER_INITIAL = 350;
    private int number = 0;

    private Timer timer;
    private JLabel counter;
    private MyToggleButton myToggleButton;
    Wavelet wavelet;
    private ArrayBlock arrayBlock_sigs;
    private ArrayBlock arrayBlock_interscalings;
    private ArrayBlock arrayBlock_interdetails;
    private ArrayBlock arrayBlock_scalings;
    private ArrayBlock arrayBlock_details;

    /**
     * Generate the Panel for displaying wavelet lifting procedure.
     */
    MyPanel(){

        // a timer
        timer = new Timer(_SLIDER_INITIAL,(e)->{
            number = number+1;
//            if(number>wavelet.getArray().getScalings().length-1)
            if(number>wavelet.getLinkedList().getScalings().size()-1)
            {
               Timer timertmp = (Timer)e.getSource();
               timertmp.stop();
               myToggleButton.setSelected(false);
               myToggleButton.setText("Reset");
               number=0;
               counter.setText(String.valueOf(number));
//               arrayBlock_scalings.setArray(wavelet.getArray().getScalings()[number]);
//               arrayBlock_details.setArray(wavelet.getArray().getDetails()[number]);
//               arrayBlock_sigs.setArray(wavelet.getArray().getSigs()[number]);
//               arrayBlock_interdetails.setArray(wavelet.getArray().getInterDetails()[number]);
//               arrayBlock_interscalings.setArray(wavelet.getArray().getInterScalings()[number]);
                arrayBlock_scalings.setArray(wavelet.getLinkedList().getScalings().get(number));
                arrayBlock_details.setArray(wavelet.getLinkedList().getDetails().get(number));
                arrayBlock_sigs.setArray(wavelet.getLinkedList().getSigs().get(number));
                arrayBlock_interdetails.setArray(wavelet.getLinkedList().getInterDetails().get(number));
                arrayBlock_interscalings.setArray(wavelet.getLinkedList().getInterScalings().get(number));
            }
            else {
                counter.setText(String.valueOf(number));
//               arrayBlock_scalings.setArray(wavelet.getArray().getScalings()[number]);
//               arrayBlock_details.setArray(wavelet.getArray().getDetails()[number]);
//               arrayBlock_sigs.setArray(wavelet.getArray().getSigs()[number]);
//               arrayBlock_interdetails.setArray(wavelet.getArray().getInterDetails()[number]);
//               arrayBlock_interscalings.setArray(wavelet.getArray().getInterScalings()[number]);
                arrayBlock_scalings.setArray(wavelet.getLinkedList().getScalings().get(number));
                arrayBlock_details.setArray(wavelet.getLinkedList().getDetails().get(number));
                arrayBlock_sigs.setArray(wavelet.getLinkedList().getSigs().get(number));
                arrayBlock_interdetails.setArray(wavelet.getLinkedList().getInterDetails().get(number));
                arrayBlock_interscalings.setArray(wavelet.getLinkedList().getInterScalings().get(number));
            }
        });

        wavelet = new Wavelet(new double[]{1,2,3,4,5,6,7,8,1,2,3,4,5,6,7,8});

        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        Box hBox_up = Box.createHorizontalBox();
        Box hBox_down = Box.createHorizontalBox();
        this.add(hBox_up);
        this.add(Box.createVerticalGlue());
        this.add(hBox_down);
        hBox_down.add(Box.createVerticalStrut(70));

        //hBox_down For control
        hBox_down.add(Box.createHorizontalGlue());
        myToggleButton = new MyToggleButton("Start",timer);
        hBox_down.add(myToggleButton);
        hBox_down.add(Box.createHorizontalGlue());
        counter = new JLabel(String.valueOf(number));
        hBox_down.add(counter);
        hBox_down.add(Box.createHorizontalGlue());
        hBox_down.add(new MySlider(_SLIDER_MIN,_SLIDER_MAX, _SLIDER_INITIAL,timer));
        hBox_down.add(Box.createHorizontalGlue());

        //hBox_up for Display
        Box up_vBox = Box.createVerticalBox();
        Box up_vBox_hBox_inter = Box.createHorizontalBox();
        Box up_vBox_hBox_final = Box.createHorizontalBox();
        hBox_up.add(up_vBox);
//        arrayBlock_scalings = new ArrayBlock(wavelet.getArray().getScalings()[number]);
//        arrayBlock_details = new ArrayBlock(wavelet.getArray().getDetails()[number]);
//        arrayBlock_sigs = new ArrayBlock(wavelet.getArray().getSigs()[number]);
//        arrayBlock_interscalings = new ArrayBlock(wavelet.getArray().getInterScalings()[number]);
//        arrayBlock_interdetails = new ArrayBlock(wavelet.getArray().getInterDetails()[number]);
        arrayBlock_scalings = new ArrayBlock(wavelet.getLinkedList().getScalings().get(number));
        arrayBlock_details = new ArrayBlock(wavelet.getLinkedList().getDetails().get(number));
        arrayBlock_sigs = new ArrayBlock(wavelet.getLinkedList().getSigs().get(number));
        arrayBlock_interscalings = new ArrayBlock(wavelet.getLinkedList().getInterScalings().get(number));
        arrayBlock_interdetails = new ArrayBlock(wavelet.getLinkedList().getInterDetails().get(number));

        up_vBox.add(new JLabel("sig"));
        up_vBox.add(arrayBlock_sigs);
        up_vBox.add(Box.createVerticalGlue());
        up_vBox.add(up_vBox_hBox_inter);
        up_vBox.add(Box.createVerticalGlue());
        up_vBox.add(up_vBox_hBox_final);

        up_vBox_hBox_inter.add(new JLabel("s_inter"));
        up_vBox_hBox_inter.add(arrayBlock_interscalings);
        up_vBox_hBox_inter.add(Box.createHorizontalGlue());
        up_vBox_hBox_inter.add(new JLabel("d_inter"));
        up_vBox_hBox_inter.add(arrayBlock_interdetails);

        up_vBox_hBox_final.add(new JLabel("s_final"));
        up_vBox_hBox_final.add(arrayBlock_scalings);
        up_vBox_hBox_final.add(Box.createHorizontalGlue());
        up_vBox_hBox_final.add(new JLabel("d_final"));
        up_vBox_hBox_final.add(arrayBlock_details);
    }
}
