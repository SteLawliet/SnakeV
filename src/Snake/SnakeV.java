package Snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @program: SnakeV
 * @author: Zhaoziqi
 * @create: 2018-06-22 10:54
 **/

public class SnakeV extends JPanel {
    private int shapeSize;
    private int wid, hei;
    private List<Point> pointList;
    private int count = 0;

    private SnakeV(int shapeSize, int wid, int hei) {
        this.shapeSize = shapeSize;
        this.wid = wid;
        this.hei = hei;
        pointList = new ArrayList<>();
    }

    public static void main(String[] args) {
        int a = 0;
        Random r = new Random();
        JFrame jFrame = new JFrame();
        SnakeV mvShape = new SnakeV(20, 13, 26);
        Point food = mvShape.new Point(r.nextInt(mvShape.wid),r.nextInt(mvShape.hei));
        Point point = mvShape.new Point(-1,0);
        jFrame.setResizable(true);
        jFrame.getContentPane().add(mvShape);
        jFrame.setDefaultCloseOperation(3);
        jFrame.setSize(mvShape.wid * mvShape.shapeSize,
                mvShape.hei * mvShape.shapeSize + 28);
        jFrame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!(e.getKeyChar()=='W'||
                        e.getKeyChar()=='A'||
                        e.getKeyChar()=='S'||
                        e.getKeyChar()=='D')
                        )return;

                switch (e.getKeyChar()) {
                    case 'W':
                        point.y--;
                        break;
                    case 'S':
                        point.y++;
                        break;
                    case 'A':
                        point.x--;
                        break;
                    case 'D':
                        point.x++;
                        break;
                    default:
                }
                boolean control = mvShape.control(point.x,point.y, food.x, food.y);
                if (control){
                    food.x=r.nextInt(mvShape.wid);
                    food.y=r.nextInt(mvShape.hei);
                }
            }
            @Override
            public void keyPressed(KeyEvent e) { }
            @Override
            public void keyReleased(KeyEvent e) { }
        });
        jFrame.setVisible(true);
    }
    private boolean control(int a,int b,int x,int y) {
        boolean flag = false;
        Random r = new Random();
        Point food = new Point(x,y);

//        未实现的自动行走功能，计划新开一个线程来处理自动行走
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }).start();

        //在wid和hie范围内生成随机的目标食物方块
            if (a == food.x && b == food.y) {
                count++;
                flag = true;
            }
            if (a >= this.wid) {
                a = this.wid - 1;
                return flag;
            }
            if (a < 0) {
                a = 0;
                return flag;
            }
            if (b >= this.hei) {
                b = this.hei - 1;
                return flag;
            }
            if (b < 0) {
                b = 0;
                return flag;
            }
            //在俩次反转中添加下次方块的位置。来实现往list开头添加新坐标
            Collections.reverse(pointList);
            pointList.add(new Point(a, b));
            Collections.reverse(pointList);

            if (pointList.size() > count + 1) {
                pointList.remove(pointList.size() - 1);
            }

            moveList(getGraphics());

            System.out.println(pointList);
            Graphics g = getGraphics();
            g.setColor(Color.cyan);
                   g.fillRect(shapeSize *food.x, shapeSize * food.y,
                    shapeSize, shapeSize);

            return flag;
    }
    @Override
    public void paint(Graphics g0) {
        super.paint(g0);
        g0.setColor(Color.pink);
        System.out.println("running.....");
        for (int i = 0; i < wid; i++) {
            for (int j = 0; j < hei; j++) {
                g0.drawRect(shapeSize * i,
                        shapeSize * j, shapeSize, shapeSize);
            }
        }
    }
    private void moveList(Graphics g) {
        this.paint(g);
        Point curPoint;
        for (int i = 0; i < wid; i++) {
            for (int j = 0; j < hei; j++) {
                for (int k = 0; k < pointList.size(); k++) {
                    curPoint = pointList.get(k);
                    g.setColor(Color.lightGray);
                    if (i == curPoint.x && j == curPoint.y) {
                        g.fillRect(shapeSize * i, shapeSize * j,
                                shapeSize, shapeSize);
                    }
                }
            }
        }
    }
    class Point {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public Point() { }
        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("{");
            sb.append("x=").append(x);
            sb.append(", y=").append(y);
            sb.append('}');
            return sb.toString();
        }

    }
}
