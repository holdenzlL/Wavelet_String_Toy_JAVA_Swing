package org.example.gui.arrow;

import lombok.Setter;
import org.example.gui.arrow.pojo.Arrowhead;
import org.example.gui.arrow.pojo.Linestyle;

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
    private Arrowhead arrowhead_end;
    private Arrowhead arrowhead_start;
    private Color color;
    private float lineWidth;
    private Linestyle linestyle;
    // One angle and its two adjacent sides determine a triangle
    private double triangle_angle;           // It should be in (0,90)
    private double triangle_size; // The length of the other side
    private double bullet_size;
    private Boolean antiAliasing;

    // The coordinates of two points determining a line
    Arrow(double x_start, double y_start, double x_end, double y_end){
        point2D_start = new Point2D.Double(x_start,y_start);
        point2D_end = new Point2D.Double(x_end,y_end);
        arrowhead_end = Arrowhead.triangle;
        arrowhead_start = Arrowhead.no_head;
        color = Color.BLACK;
        lineWidth = 2;
        linestyle = Linestyle.solid;
        antiAliasing = true;

        triangle_angle = 45;
        triangle_size = 10; //side_hypotenuse
        bullet_size = 10;
    }
    private float[] get_linestyle(){
        switch (linestyle){
            case dashed:
                return new float[]{8,4};
            case dotted:
                return new float[]{2,4};
            case dash_dotted:
                return new float[]{2,4,8,4};
            case solid:
            default:
                return new float[]{8,0};
        }
    }

    private void draw_line(Graphics2D g2d){
        Stroke stroke = g2d.getStroke();
        g2d.setStroke(new BasicStroke(
                lineWidth,
                BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_ROUND,
                1,
                get_linestyle(),
                0));
        g2d.draw(new Line2D.Double(point2D_start, point2D_end));
        g2d.setStroke(stroke);
    }

    private double[] get_triangle(Point2D start,Point2D end){
        double x1 = start.getX();
        double x2 = end.getX();
        double y1 = start.getY();
        double y2 = end.getY();

        Orientation orientation;
        if (start.getX() > end.getX()){
            if(start.getY() > end.getY())
                orientation = Orientation.upperLeft;
            else
                orientation = Orientation.lowerLeft;
        }
        else{
            if(start.getY() > end.getY())
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

        double xAngle = lineAngle - triangle_angle;
        double dy0 = triangle_size * Math.sin(Math.toRadians(xAngle));
        double dx0 = triangle_size * Math.cos(Math.toRadians(xAngle));

        double yAngle = 90 - lineAngle - triangle_angle;
        double dx1 = triangle_size * Math.sin(Math.toRadians(yAngle));
        double dy1 = triangle_size * Math.cos(Math.toRadians(yAngle));

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

    private void draw_triangle(Graphics2D g2d, Point2D pointStart, Point2D pointEnd){
        double[] tri_d = get_triangle(pointStart,pointEnd);

        // Three points determine one triangle
        GeneralPath triangle = new GeneralPath();
        triangle.moveTo(pointEnd.getX(),pointEnd.getY());
        triangle.lineTo(pointEnd.getX()+tri_d[0],pointEnd.getY()+tri_d[1]);
        triangle.lineTo(pointEnd.getX()+tri_d[2],pointEnd.getY()+tri_d[3]);
        triangle.closePath();
        g2d.fill(triangle);
    }

    private void draw_tri_line(Graphics2D g2d, Point2D pointStart, Point2D pointEnd){
        double[] tri_d = get_triangle(pointStart,pointEnd);
        Stroke stroke = g2d.getStroke();
        g2d.setStroke(new BasicStroke(
                lineWidth,
                BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_ROUND,
                1,
                new float[]{8,0},
                0));
        g2d.draw(new Line2D.Double(pointEnd,new Point2D.Double(pointEnd.getX()+tri_d[0],pointEnd.getY()+tri_d[1])));
        g2d.draw(new Line2D.Double(pointEnd,new Point2D.Double(pointEnd.getX()+tri_d[2],pointEnd.getY()+tri_d[3])));
        g2d.setStroke(stroke);
    }

    private void draw_bullet(Graphics2D g2d, Point2D point){
        Shape circleShape = new Ellipse2D.Double(point.getX()-bullet_size/2, point.getY()-bullet_size/2, bullet_size, bullet_size);
        g2d.fill(circleShape);
    }
    private void draw_arrowhead(Graphics2D g2d, Arrowhead arrowhead_style) {
        switch (arrowhead_style){
            case no_head:
                break;
            case triangle:
                draw_triangle(g2d,point2D_start,point2D_end);
                break;
            case line:
                draw_tri_line(g2d,point2D_start,point2D_end);
                break;
            case bullet:
                draw_bullet(g2d,point2D_end);
                break;
        }
    }
    public void draw(Graphics2D g2d){
        Object renderingHint = g2d.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
        Paint color_origin = g2d.getPaint();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setPaint(color);
        // draw line
        draw_line(g2d);
        // draw one side
        draw_arrowhead(g2d, arrowhead_end);
        // draw the other side
        draw_arrowhead(g2d, arrowhead_start);
        g2d.setPaint(color_origin);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,renderingHint);
    }
    private enum Orientation {
        lowerRight, // First Quadrant: x > 0, y > 0
        lowerLeft,  // Second Quadrant: x < 0, y > 0
        upperLeft,  // Third Quadrant: x < 0, y < 0
        upperRight, // Forth Quadrant: x > 0, y < 0
        vertical,
        horizontal;
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
            Arrow arrow = new Arrow(200,300,400,321);
            arrow.setArrowhead_end(Arrowhead.line);
            arrow.setLinestyle(Linestyle.dashed);
            arrow.setLineWidth(4);
            arrow.setTriangle_size(15);
            arrow.draw((Graphics2D) g);
        }
    }

}
