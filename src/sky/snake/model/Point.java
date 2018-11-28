package sky.snake.model;

/**
 * @program: SnakeV
 * @description:
 * @author: Zhaoziqi
 * @create: 2018-07-11 18:34
 **/
public class Point {
    public int x;
    public int y;

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