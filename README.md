# SnakeV
基于JAVA的Swing ArrayList和二维数组的贪吃蛇简单实现

使用说明:
- 执行main后按大写D开始
- 大写锁定下: W S A D 分别为上下左右的控制

未来更新计划:
- 实现游戏难度选择与难度递增功能
- 计划重新用javascript实现的在线版本
- 实现游戏分数统计与线上排名功能



更新日志

v1.1 2018/6/27
- 新增了简单的游戏结束和重启动画
- 创建新线程实现了蛇身重复前一个指令行走的问题
- 添加了蛇身后退和头部接触到身体时的游戏结束判断
- 简化代码改进二维数组元素的为一个内部类Point
- 添加了一些代码注释

v1.0 2018/6/22

目前未解决的问题 
- GameOver游戏结束没有做
- 蛇自动行走的功能
- 食物方块可能随机到蛇身上

