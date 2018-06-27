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
    private static final Random r = new Random();
    private final List<Point> pointList;
    private int shapeSize;
    private int wid, hei;
    private int count = 0;
    private char keyChar;

    private SnakeV(int shapeSize, int wid, int hei) {
        this.shapeSize = shapeSize;
        this.wid = wid;
        this.hei = hei;
        pointList = new ArrayList<>();
    }

    public static void main(String[] args) {
        JFrame jFrame = new JFrame();
        //shapeSize为方块大小，wid为游戏界面宽，hei为高
        SnakeV mvShape = new SnakeV(25, 10, 20);
        Point food = mvShape.new Point(r.nextInt(mvShape.wid), r.nextInt(mvShape.hei));
        Point point = mvShape.new Point(0, 0);
        jFrame.setResizable(true);
        jFrame.getContentPane().add(mvShape);
        jFrame.setDefaultCloseOperation(3);
        jFrame.setSize(mvShape.wid * mvShape.shapeSize,
                mvShape.hei * mvShape.shapeSize + 28);
        jFrame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char e0 = e.getKeyChar();
                if (!(e0 == 'W' || e0 == 'A' ||
                        e0 == 'S' || e0 == 'D')) return;
                mvShape.keyChar = e.getKeyChar();
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        //开一个线程来处理自动往前行走
        new Thread(() -> {
            while (true) {
                mvShape.keyTyped(mvShape.keyChar, point, mvShape, food);
                //游戏结束判定
                if (mvShape.testOver(point))
                    mvShape.gameOver(mvShape.getGraphics(), point, food);
                try {
                    //调整蛇前进的速度，即游戏难度
                    Thread.sleep(120);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        jFrame.setVisible(true);
    }

    /**
     * @Description: keyTyped
     * 监听键盘输入的字符
     * W S A D 分别控制上下左右
     */
    private void keyTyped(char e, Point point, SnakeV mvShape, Point food) {
        if (!(e == 'W' || e == 'A' ||
                e == 'S' || e == 'D')) return;
        switch (e) {
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
        //返回boolean判定是否吃到食物来决定胜负生成新的食物
        boolean control = mvShape.control(point.x, point.y, food.x, food.y);
        if (control) {
            food.x = r.nextInt(mvShape.wid);
            food.y = r.nextInt(mvShape.hei);
        }
    }

    /**
     * @Description: 蛇身体数据的维护
     * 判定吃到食物身体count++，
     * 根据前进方向生成新的蛇身体坐标
     */
    private boolean control(int a, int b, int x, int y) {
        boolean flag = false;
        //在wid和hie范围内生成随机的目标食物方块
        Point food = new Point(x, y);
        if (a == food.x && b == food.y) {
            count++;
            flag = true;
        }
        //在俩次反转list中添加下次方块移动的位置。来实现往list开头添加新坐标
        Collections.reverse(pointList);
        pointList.add(new Point(a, b));
        Collections.reverse(pointList);
        //每次移动时减去末尾的方格来向新加的坐标移动
        if (pointList.size() > count + 1) {
            pointList.remove(pointList.size() - 1);
        }
        //更新蛇的位置，把list所在的坐标都填满
        moveList(getGraphics());
        //打印蛇身体的坐标
        System.out.println(pointList);
        Graphics g = getGraphics();
        g.setColor(Color.cyan);
        //生成食物方块
        g.fillRect(shapeSize * food.x, shapeSize * food.y,
                shapeSize, shapeSize);
        return flag;
    }

    @Override
    public void paint(Graphics g0) {
        super.paint(g0);
        g0.setColor(Color.pink);
        System.out.println("running.....");
        for (int j = 0; j < hei; j++) {
            for (int i = 0; i < wid; i++) {
                g0.drawRect(shapeSize * i,
                        shapeSize * j, shapeSize, shapeSize);
            }
        }
    }

    //游戏重启动画
    private void paint0(Graphics g0) {
        super.paint(g0);
        g0.setColor(Color.pink);
        System.out.println("running.....");
        for (int j = 0; j < hei; j++) {
            for (int i = 0; i < wid; i++) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                g0.drawRect(shapeSize * i,
                        shapeSize * j, shapeSize, shapeSize);
            }
        }
    }

    //GameOver动画
    private void gameOver(Graphics g, Point point, Point food) {
        System.out.println("Game Over");
        Graphics g0 = g;
        this.keyChar = '0';
        g0.setColor(Color.pink);
        for (int j = hei - 1; j >= 0; j--) {
            for (int i = 0; i < wid; i++) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                g0.fillRect(shapeSize * i,
                        shapeSize * j, shapeSize, shapeSize);
            }

        }
        this.paint0(g);
        //重启游戏状态
        reStart(point, this, food);
    }

    private boolean testOver(Point point) {
        boolean over;
        //判定是否移动到边界
        over = point.x < 0 || point.x == this.wid ||
                point.y < 0 || point.y == this.hei;
        //判定蛇头是否接触到身体
        for (int i = pointList.size() - 2; i > 0; i--) {
            Point p = pointList.get(i);
            if (p.x == point.x && p.y == point.y) over = true;
        }
        return over;
    }

    // 游戏结束后重启游戏状态
    private void reStart(Point point, SnakeV mvShape, Point food) {
        point.x = 0;
        point.y = 0;
        mvShape.keyChar = '0';
        mvShape.pointList.clear();
        mvShape.count = 0;
        food = mvShape.new Point(r.nextInt(mvShape.wid), r.nextInt(mvShape.hei));
    }

    //更新蛇的位置，把list所在的坐标都填满
    private void moveList(Graphics g) {
        this.paint(g);
        Point target;
        for (int i = 0; i < wid; i++) {
            for (int j = 0; j < hei; j++) {
                for (int k = 0; k < pointList.size(); k++) {
                    target = pointList.get(k);
                    g.setColor(Color.lightGray);
                    if (i == target.x && j == target.y) {
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

        @Override
        public String toString() {
            String sb = "{" + "x=" + x + ", y=" + y + '}';
            return sb;
        }
    }
}
