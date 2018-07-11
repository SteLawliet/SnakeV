package sky.snake.model;

import java.util.LinkedList;

/**
 * @program: SnakeV
 * @description: implement SnakeBody by LinkedList
 * @author: Zhaoziqi
 * @create: 2018-07-11 14:42
 **/
public class SnakeBody {

    private LinkedList<Point> bodyList;


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
