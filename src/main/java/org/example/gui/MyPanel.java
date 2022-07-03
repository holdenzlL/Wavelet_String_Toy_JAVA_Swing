package org.example.gui;

import javax.swing.*;
import java.util.Objects;

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

    private int number = 0;

    private final Timer timer;
    private JLabel counter;
    private MyToggleButton myToggleButton;
    Wavelet wavelet;
    private final ArrayBlock arrayBlock_sigs;
    private final ArrayBlock arrayBlock_interscalings;
    private final ArrayBlock arrayBlock_interdetails;
    private final ArrayBlock arrayBlock_scalings;
    private final ArrayBlock arrayBlock_details;
    private final ArrayBlock arrayBlock_reScalings;
    private final ArrayBlock arrayBlock_reDetails;
    private final ArrayBlock arrayBlock_reSigs;

    /**
     * Generate the Panel for displaying wavelet lifting procedure.
     */
    MyPanel(){

        final int _SLIDER_MIN = 200;
        final int _SLIDER_MAX = 500;
        int _SLIDER_INITIAL = 350;

        // a timer
        timer = new Timer(_SLIDER_INITIAL,(e)->{
            number++;
            if(number>wavelet.getLinkedList().getScalings().size()-1)
            {
               Timer timertmp = (Timer)e.getSource();
               timertmp.stop();
               myToggleButton.setSelected(false);
               myToggleButton.setText("Reset");
               number=0;
            }
            counter.setText(String.valueOf(number));
            drawArrays();
        });

        wavelet = new Wavelet(new double[]{1,2,3,4,5,6,7,8,1,2,3,4,5,6,7,8});
        wavelet.reconstruction();

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

        JButton buttonLast = new JButton("Last Step");
        JButton buttonNext = new JButton("Next Step");
        buttonLast.addActionListener(e -> {
            //stop the timer and toggle the button
            timer.stop();
            myToggleButton.setSelected(false);
            if(!Objects.equals(myToggleButton.getText(), "Start"))
                myToggleButton.setText("Resume");
            //set the step counter
            number--;
            if(number<0)
                number = wavelet.getLinkedList().getScalings().size()-1;
            //re-draw
            counter.setText(String.valueOf(number));
            drawArrays();
        });
        buttonNext.addActionListener(e->{
            //stop the timer and toggle the button
            timer.stop();
            myToggleButton.setSelected(false);
            if(!Objects.equals(myToggleButton.getText(), "Start"))
                myToggleButton.setText("Resume");
            //set the step counter
            number++;
            if(number>wavelet.getLinkedList().getScalings().size()-1)
                number = 0;
            //re-draw
            counter.setText(String.valueOf(number));
            drawArrays();
        });
        hBox_down.add(buttonLast);
        hBox_down.add(Box.createHorizontalGlue());
        hBox_down.add(buttonNext);
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
        Box up_vBox_hBox_reInter = Box.createHorizontalBox();
        hBox_up.add(up_vBox);
        arrayBlock_scalings = new ArrayBlock(wavelet.getLinkedList().getScalings().get(number));
        arrayBlock_details = new ArrayBlock(wavelet.getLinkedList().getDetails().get(number));
        arrayBlock_sigs = new ArrayBlock(wavelet.getLinkedList().getSigs().get(number));
        arrayBlock_interscalings = new ArrayBlock(wavelet.getLinkedList().getInterScalings().get(number));
        arrayBlock_interdetails = new ArrayBlock(wavelet.getLinkedList().getInterDetails().get(number));
        arrayBlock_reScalings = new ArrayBlock(wavelet.getLinkedList().getRe_interScalings().get(number));
        arrayBlock_reDetails = new ArrayBlock(wavelet.getLinkedList().getRe_interDetails().get(number));
        arrayBlock_reSigs = new ArrayBlock(wavelet.getLinkedList().getRe_sigs().get(number));

        up_vBox.add(new JLabel("sig"));
        up_vBox.add(arrayBlock_sigs);
        up_vBox.add(Box.createVerticalGlue());
        up_vBox.add(up_vBox_hBox_inter);
        up_vBox.add(Box.createVerticalGlue());
        up_vBox.add(up_vBox_hBox_final);
        up_vBox.add(Box.createVerticalGlue());
        up_vBox.add(up_vBox_hBox_reInter);
        up_vBox.add(Box.createVerticalGlue());
        up_vBox.add(new JLabel("re_sig"));
        up_vBox.add(arrayBlock_reSigs);

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

        up_vBox_hBox_reInter.add(new JLabel("s_re"));
        up_vBox_hBox_reInter.add(arrayBlock_reScalings);
        up_vBox_hBox_reInter.add(Box.createHorizontalGlue());
        up_vBox_hBox_reInter.add(new JLabel("d_re"));
        up_vBox_hBox_reInter.add(arrayBlock_reDetails);
    }
    private void drawArrays()
    {
        arrayBlock_scalings.setArray(wavelet.getLinkedList().getScalings().get(number));
        arrayBlock_details.setArray(wavelet.getLinkedList().getDetails().get(number));
        arrayBlock_sigs.setArray(wavelet.getLinkedList().getSigs().get(number));
        arrayBlock_interdetails.setArray(wavelet.getLinkedList().getInterDetails().get(number));
        arrayBlock_interscalings.setArray(wavelet.getLinkedList().getInterScalings().get(number));
        arrayBlock_reSigs.setArray(wavelet.getLinkedList().getRe_sigs().get(number));
        arrayBlock_reDetails.setArray(wavelet.getLinkedList().getRe_interDetails().get(number));
        arrayBlock_reScalings.setArray(wavelet.getLinkedList().getRe_interScalings().get(number));
    }
}
