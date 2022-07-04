package org.example.gui.grid;

import lombok.Getter;
import lombok.Setter;
import org.example.gui.grid.pojo.GridData;
import org.example.gui.grid.pojo.Orientation;

import javax.swing.*;
import java.awt.*;

@Setter
@Getter
public class GridArray extends JPanel {
    private int sizeArray;
    private Orientation orientation;
    private Dimension gridSize;
    private Grid[] gridArray;
    private int borderWidth;
    private int gapSize;

    GridArray(int size, Dimension gridSize){
        this.sizeArray = size;
        this.gridSize = gridSize;
        orientation = Orientation.Horizontal;
        borderWidth = 1;
        gapSize = 0;

        gridArray = new Grid[sizeArray];
        for (int i = 0; i < sizeArray; i++) {
            gridArray[i] = new Grids(null);
        }
    }

    public void draw(){
        this.setLayout(new BoxLayout(this,orientation == Orientation.Horizontal?BoxLayout.X_AXIS:BoxLayout.Y_AXIS));
//        for (int i = 0; i < sizeArray; i++) {
//            gridArray[i] = new Grids(null);
//            this.add(gridArray[i]);
//            if(i != sizeArray -1)
//                this.add(orientation == Orientation.Horizontal?
//                    Box.createHorizontalStrut(gapSize==0?-borderWidth:gapSize)
//                    :Box.createVerticalStrut(gapSize==0?-borderWidth:gapSize));
//            gridArray[i].draw();
//        }
        for (int i = 0; i < sizeArray; i++) {
            this.add(gridArray[i]);
            if(i != sizeArray -1)
                this.add(orientation == Orientation.Horizontal?
                        Box.createHorizontalStrut(gapSize==0?-borderWidth:gapSize)
                        :Box.createVerticalStrut(gapSize==0?-borderWidth:gapSize));
            gridArray[i].draw();
        }
    }
    private class Grids extends Grid{
        Grids(GridData gridData) {
            super(gridData);
            setBorderWidth(borderWidth);
        }
    }

    public static void main(String[] args) {
        JFrame jFrame = new JFrame("Grid test");
        JPanel jPanel = new JPanel();
        jFrame.setContentPane(jPanel);
        jFrame.setSize(800,800);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);

        GridArray gridArray = new GridArray(3,new Dimension(50,50));
        jPanel.add(gridArray);
        gridArray.draw();
        gridArray.getGridArray()[2].setBackgroundColor(Color.cyan);
        gridArray.getGridArray()[2].draw();
    }
}
