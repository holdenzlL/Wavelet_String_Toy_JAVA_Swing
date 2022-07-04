package org.example.gui.grid;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.example.gui.grid.pojo.BorderType;
import org.example.gui.grid.pojo.GridData;

import javax.swing.*;
import java.awt.*;

@Setter
@Getter
public class Grid extends JPanel{
    private GridData gridData;
    private BorderType borderType;
    private Dimension gridDimension;
    private Boolean showData;
    private Color borderColor;
    private Color backgroundColor;
    private int borderWidth;

    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private Boolean opaque;

    Grid(GridData gridData){
        backgroundColor = null;
        showData = false;
        this.gridData = gridData;
        borderType = BorderType.solidLine;
        borderWidth = 1;
        borderColor = Color.BLACK;
        gridDimension = new Dimension(100,100);
        opaque = false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(opaque)
        {
            g.setColor(backgroundColor);
            g.fillRect(borderWidth,borderWidth,getWidth()-2*borderWidth,getHeight()-2*borderWidth);
        }
    }

    private void drawText(){
        this.setLayout(new BorderLayout());
        JLabel jLabel = new JLabel(gridData.showData());
        jLabel.setVerticalAlignment(SwingConstants.CENTER);
        jLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(jLabel);
    }
    public void draw(){
        // set size
        this.setPreferredSize(gridDimension);
        this.setMaximumSize(gridDimension);
        this.setMinimumSize(gridDimension);

        // draw border
        switch (borderType){
            case dashedLine:
                this.setBorder(BorderFactory.createDashedBorder(borderColor,3,3));
                break;
            case dottedLine:
                this.setBorder(BorderFactory.createDashedBorder(borderColor,1,5));
                break;
            case solidLine:
            default:
                this.setBorder(BorderFactory.createLineBorder(borderColor,borderWidth));
        }

        opaque = backgroundColor != null;
        repaint();
        // text
        if(showData)
            drawText();
    }


    private static class data01 extends GridData{

        double data;

        @Override
        public String showData() {
            return "hello";
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
        data01 data01_ = new data01();

        Grid grid = new Grid(data01_);
        grid.setBorderType(BorderType.dottedLine);


        Box hbox = Box.createHorizontalBox();
        jPanel.add(hbox);


        hbox.add(grid);
        grid.setShowData(true);
        grid.draw();
    }
}
