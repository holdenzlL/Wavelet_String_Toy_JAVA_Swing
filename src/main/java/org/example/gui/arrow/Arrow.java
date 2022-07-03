package org.example.gui.arrow;

import lombok.Setter;
import org.example.gui.arrow.pojo.Orientation;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

@Setter
public class Arrow {
    // Two points determines a line
    private final Point2D point2D_start;
    private final Point2D point2D_end;

    // One angle and its two adjacent sides determine a triangle
    private double angle;           // It should be in (0,90)
//    private double sideAlongLine;   // The length of the side which is along with the line of the arrow
    private double side_hypotenuse; // The length of the other side

    // The coordinates of two points determining a line
    Arrow(double x_start, double y_start, double x_end, double y_end){
        point2D_start = new Point2D.Double(x_start,y_start);
        point2D_end = new Point2D.Double(x_end,y_end);
        angle = 50;
//        sideAlongLine = 40;
        side_hypotenuse = 10;
    }

    private void draw_line(Graphics2D g2d){
        // g2d.setColor(Color.BLACK);
        // g2d.setStroke(lineStroke);
        g2d.draw(new Line2D.Double(point2D_start, point2D_end));
    }

    private double[] get_triangle(){
        double x1 = point2D_start.getX();
        double x2 = point2D_end.getX();
        double y1 = point2D_start.getY();
        double y2 = point2D_end.getY();

        Orientation orientation;
        if (point2D_start.getX() > point2D_end.getX()){
            if(point2D_start.getY() > point2D_end.getY())
                orientation = Orientation.upperLeft;
            else
                orientation = Orientation.lowerLeft;
        }
        else{
            if(point2D_start.getY() > point2D_end.getY())
                orientation = Orientation.upperRight;
            else
                orientation = Orientation.lowerRight;
        }
        // angle between line and x axis
        double lineAngle = Math.abs(Math.toDegrees(Math.atan(
                (y1 - y2) / (x2 - x1)
        )));

        if (Double.isNaN(lineAngle)) {
            if (y1 < y2) {
                lineAngle = 270;
            } else {
                lineAngle = 90;
            }
            orientation = Orientation.vertical;
        }
        else if (lineAngle == 0) {
            if (x1 < x2) {
                lineAngle = 180;
            } else {
                lineAngle = 0;
            }
            orientation = Orientation.horizontal;
        }

        double xAngle = lineAngle - angle;
        double dy0 = side_hypotenuse * Math.sin(Math.toRadians(xAngle));
        double dx0 = side_hypotenuse * Math.cos(Math.toRadians(xAngle));

        double yAngle = 90 - lineAngle - angle;
        double dx1 = side_hypotenuse * Math.sin(Math.toRadians(yAngle));
        double dy1 = side_hypotenuse * Math.cos(Math.toRadians(yAngle));

        switch (orientation){
            case upperRight:
                dx0 = -dx0;
                dx1 = -dx1;
                break;
            case upperLeft:
            case horizontal:
            case vertical:
                break;
            case lowerRight:
                dy0 = -dy0;
                dx0 = -dx0;
                dx1 = -dx1;
                dy1 = -dy1;
                break;
            case lowerLeft:
                dy0 = -dy0;
                dy1 = -dy1;
                break;
        }

        return new double[]{dx0,dy0,dx1,dy1};
    }

    private void draw_triangle(Graphics2D g2d, Point2D point){
        //{dx0,dy0,dx1,dy1}
        double[] tri_d = get_triangle();

        // Three points determine one triangle
        GeneralPath triangle = new GeneralPath();
        triangle.moveTo(point.getX(),point.getY());
        triangle.lineTo(point.getX()+tri_d[0],point.getY()+tri_d[1]);
        triangle.lineTo(point.getX()+tri_d[2],point.getY()+tri_d[3]);
        triangle.closePath();
        //g2d.setColor(arrow.attributes.color);
        g2d.fill(triangle);
    }

    private void draw_tri_line(Graphics2D g2d, Point2D point){
        //{dx0,dy0,dx1,dy1}
        double[] tri_d = get_triangle();
        g2d.draw(new Line2D.Double(point,new Point2D.Double(point.getX()+tri_d[0],point.getY()+tri_d[1])));
        g2d.draw(new Line2D.Double(point,new Point2D.Double(point.getX()+tri_d[2],point.getY()+tri_d[3])));
    }

    private void draw_bullet(Graphics2D g2d, Point2D point){
        double d = 10;
        Shape circleShape = new Ellipse2D.Double(point.getX()-d/2, point.getY()-d/2, d, d);
        g2d.fill(circleShape);
    }

    public void draw(Graphics2D g2d){
        draw_line(g2d);
        draw_triangle(g2d,point2D_end);
//        draw_tri_line(g2d,point2D_start);
//        draw_bullet(g2d,point2D_end);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("绘制箭头");
        frame.setSize(new Dimension(800, 600));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        frame.setContentPane(new DrawPanel());

        frame.setVisible(true);
    }

    public static class DrawPanel extends JPanel{
        @Override
        protected void paintComponent(Graphics g) {
            Arrow arrow = new Arrow(300,300,300,300);
            arrow.draw((Graphics2D) g);
        }
    }

}
