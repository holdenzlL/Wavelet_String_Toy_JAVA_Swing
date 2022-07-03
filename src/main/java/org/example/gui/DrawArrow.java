package org.example.gui;


import javax.swing.*;
import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 * 绘制箭头
 *
 * @author xiangqian
 * @date 16:00 2019/10/31
 */
@Deprecated
public class DrawArrow {

    /**
     * 绘制面板
     *
     * @author xiangqian
     * @date 15:38 2019/11/03
     */
    public static class DrawPanel extends JPanel {

        private BasicStroke lineStroke;

        public DrawPanel() {
            lineStroke = new BasicStroke(2.0f);
        }

        @Override
        protected void paintComponent(Graphics g) {
            draw((Graphics2D) g);
        }

        /**
         * 绘制
         *
         * @param g2d
         */
        private void draw(Graphics2D g2d) {

            Line2D.Double line2D = null;
            Arrow.Attributes arrowAttributes = null;

            // 绘制线的“方向1”箭头
            line2D = new Line2D.Double(120, 100, 300, 300);
            arrowAttributes = new Arrow.Attributes();
            arrowAttributes.angle = 120;
            arrowAttributes.height = 10;
            drawLineArrowDirection1(g2d, arrowAttributes, line2D);

//            // 绘制线的“方向2”箭头
//            line2D = new Line2D.Double(110, 330, 300, 330);
//            arrowAttributes = new Arrow.Attributes();
//            arrowAttributes.angle = 45;
//            arrowAttributes.height = 80;
//            drawLineArrowDirection2(g2d, arrowAttributes, line2D);
//
//            // 绘制线的“双向”箭头
//            line2D = new Line2D.Double(500, 200, 260, 200);
//            arrowAttributes = new Arrow.Attributes();
//            arrowAttributes.angle = 30;
//            arrowAttributes.height = 40;
//            drawLineArrowDirectionAll(g2d, arrowAttributes, line2D);
        }


        /**
         * 绘制线的“方向1”箭头
         *
         * @param g2d
         * @param arrowAttributes 箭头属性
         * @param line2D          线
         */
        private void drawLineArrowDirection1(Graphics2D g2d, Arrow.Attributes arrowAttributes, Line2D.Double line2D) {
            drawLine(g2d, line2D);
            drawArrow(g2d, arrowAttributes, line2D.getP1(), line2D.getP2());
        }

        /**
         * 绘制线的“方向2”箭头
         *
         * @param g2d
         * @param arrowAttributes 箭头属性
         * @param line2D          线
         */
        private void drawLineArrowDirection2(Graphics2D g2d, Arrow.Attributes arrowAttributes, Line2D.Double line2D) {
            drawLine(g2d, line2D);
            drawArrow(g2d, arrowAttributes, line2D.getP2(), line2D.getP1());
        }

        /**
         * 绘制线的“双向”箭头
         *
         * @param g2d
         * @param arrowAttributes 箭头属性
         * @param line2D          线
         */
        private void drawLineArrowDirectionAll(Graphics2D g2d, Arrow.Attributes arrowAttributes, Line2D.Double line2D) {
            drawLine(g2d, line2D);
            drawArrow(g2d, arrowAttributes, line2D.getP1(), line2D.getP2());
            drawArrow(g2d, arrowAttributes, line2D.getP2(), line2D.getP1());
        }

        /**
         * 绘制线
         *
         * @param g2d
         * @param line2D
         */
        private void drawLine(Graphics2D g2d, Line2D.Double line2D) {
            g2d.setColor(Color.BLACK);
            g2d.setStroke(lineStroke);
            g2d.draw(line2D);
        }

        /**
         * 绘制箭头
         *
         * @param g2d
         * @param arrowAttributes 箭头属性
         * @param point1          线的第一个点
         * @param point2          线的第二个点
         */
        private void drawArrow(Graphics2D g2d, Arrow.Attributes arrowAttributes, Point2D point1, Point2D point2) {
            // 获取Arrow实例
            Arrow arrow = getArrow(arrowAttributes, point1, point2);

            // 构建GeneralPath
            GeneralPath arrow2D = new GeneralPath();
            arrow2D.moveTo(arrow.point1.x, arrow.point1.y);
            arrow2D.lineTo(arrow.point2.x, arrow.point2.y);
            arrow2D.lineTo(arrow.point3.x, arrow.point3.y);
            arrow2D.closePath();

            // 绘制
            g2d.setColor(arrow.attributes.color);
            g2d.fill(arrow2D);
        }

        /**
         * 获取箭头实体类
         *
         * @param arrowAttributes 箭头属性
         * @param point1          线的第一个点
         * @param point2          线的第二个点
         * @return
         */
        private Arrow getArrow(Arrow.Attributes arrowAttributes, Point2D point1, Point2D point2) {
            Arrow arrow = new Arrow(arrowAttributes);

            // 计算斜边
            double hypotenuse = arrow.attributes.height / Math.cos(Math.toRadians(arrow.attributes.angle / 2));

            // 计算当前线所在的象限
            int quadrant = -1;
            if (point1.getX() > point2.getX() && point1.getY() < point2.getY()) {
                quadrant = 1;
            } else if (point1.getX() < point2.getX() && point1.getY() < point2.getY()) {
                quadrant = 2;
            } else if (point1.getX() < point2.getX() && point1.getY() > point2.getY()) {
                quadrant = 3;
            } else if (point1.getX() > point2.getX() && point1.getY() > point2.getY()) {
                quadrant = 4;
            }

            // 计算线的夹角
            double linAngle = getLineAngle(point1.getX(), point1.getY(), point2.getX(), point2.getY());
            if (Double.isNaN(linAngle)) {
                // 线与x轴垂直
                if (point1.getX() == point2.getX()) {
                    if (point1.getY() < point2.getY()) {
                        linAngle = 90;
                    } else {
                        linAngle = 270;
                    }
                    quadrant = 2;
                }
            }
            // 线与y轴垂直
            else if (linAngle == 0) {
                if (point1.getY() == point2.getY()) {
                    if (point1.getX() < point2.getX()) {
                        linAngle = 0;
                    } else {
                        linAngle = 180;
                    }
                    quadrant = 2;
                }
            }

            // 上侧一半箭头
            double xAngle = linAngle - arrow.attributes.angle / 2; // 与x轴夹角
            double py0 = hypotenuse * Math.sin(Math.toRadians(xAngle)); // 计算y方向增量
            double px0 = hypotenuse * Math.cos(Math.toRadians(xAngle)); // 计算x方向增量

            // 下侧一半箭头
            double yAngle = 90 - linAngle - arrow.attributes.angle / 2; // 与y轴夹角
            double px1 = hypotenuse * Math.sin(Math.toRadians(yAngle));
            double py1 = hypotenuse * Math.cos(Math.toRadians(yAngle));

            // 第一象限
            if (quadrant == 1) {
                px0 = -px0;
                px1 = -px1;

            } else if (quadrant == 2) {
                // do nothing
            } else if (quadrant == 3) {
                py0 = -py0;
                py1 = -py1;

            } else if (quadrant == 4) {
                py0 = -py0;
                px0 = -px0;

                px1 = -px1;
                py1 = -py1;
            }

            // build
            arrow.point1 = new Point2D.Double();
            arrow.point1.x = point1.getX();
            arrow.point1.y = point1.getY();

            arrow.point2 = new Point2D.Double();
            arrow.point2.x = point1.getX() + px0;
            arrow.point2.y = point1.getY() + py0;

            arrow.point3 = new Point2D.Double();
            arrow.point3.x = point1.getX() + px1;
            arrow.point3.y = point1.getY() + py1;

            return arrow;
        }

        /**
         * 获取线与X轴的夹角
         *
         * @param x1
         * @param y1
         * @param x2
         * @param y2
         * @return
         */
        protected double getLineAngle(double x1, double y1, double x2, double y2) {
            double k1 = (y2 - y1) / (x2 - x1);
            double k2 = 0;
            return Math.abs(Math.toDegrees(Math.atan((k2 - k1) / (1 + k1 * k2))));
        }
    }


    /**
     * 箭头实体类
     *
     * @author xiangqian
     * @date 16:06 2019/10/31
     */
    public static class Arrow {
        // attribute 指定三角形的一个顶角的角度和该角的对边的长度
        Attributes attributes;

        //箭头的三个顶点，就是画一个三角形
        Point2D.Double point1;
        Point2D.Double point2;
        Point2D.Double point3;

        // constructor中指定角度和边长
        public Arrow(Attributes attributes) {
            this.attributes = attributes;
        }

        /**
         * 箭头属性
         *
         * @author xiangqian
         * @date 15:41 2019/11/03
         */
        public static class Attributes {
            double height; // 箭头的高度
            double angle; // 箭头角度
            Color color; // 箭头颜色

            //默认的边长的角度
            public Attributes() {
                this.height = 60;
                this.angle = 30;
                this.color = Color.BLACK;
            }
        }
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

}