package org.example.gui;

import org.example.gui.pojo.DataGrids;

import javax.swing.*;
import java.awt.*;

/**
 * As the core component, this class is used for drawing arrays in the GUI.
 * It will draw everything passed to it in the format of DataGrids.
 * &#064;Auther  Zhilun LI
 * &#064;Date 02.07.2022
 * &#064;Version  Prototype
 */
public class ArrayBlock extends JPanel {
    /**
     * Draw grids in GUI based on the input.
     * @param input
     */
    ArrayBlock(DataGrids input){
        super();

        final int N = input.getDataGrids().length;

        this.setLayout(new GridLayout(1, N));
        for (int i = 0; i < N; i++) {
            this.add(new ColorLabel(String.valueOf(input.getDataGrids()[i].getData()),input.getDataGrids()[i].isAddressed()?Color.PINK:Color.white));
        }
        this.setPreferredSize(new Dimension(55*N,55));
    }

    /**
     * Update the draing based on the input.
     * @param input
     */
    public void setArray(DataGrids input){
        final int N = input.getDataGrids().length;
        for (int i = 0; i < N; i++) {
            ColorLabel colorLabel = (ColorLabel) this.getComponent(i);
            colorLabel.setText(String.valueOf(input.getDataGrids()[i].getData()));
            colorLabel.setBackground(input.getDataGrids()[i].isAddressed()?Color.PINK:Color.white);
        }
    }

    /**
     * An encapsulation of JLabel.
     * Used to draw colored grids with borders.
     * &#064;Auther  Zhilun LI
     * &#064;Date 02.07.2022
     * &#064;Version  Prototype
     */
    private static class ColorLabel extends JLabel{
        /**
         * Generate a colored JLabel.
         * @param text
         * @param color
         */
        public ColorLabel(String text, Color color){
            this.setText(text);
            this.setOpaque(true);
            this.setBackground(color);
            this.setMaximumSize(new Dimension(55,55));
            this.setHorizontalAlignment(SwingConstants.CENTER);
            this.setBorder(BorderFactory.createLineBorder(Color.black));
        }
    }
}
