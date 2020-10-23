package util;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * author      : yyc
 * create_time : 2020-10-16 15:01:45
 * dest        : 根据给定的始点坐标经纬度（startLon, startLat），以及两点之间最大距离（maxDist，以公里为单位）,生成下一个随机的GPS点坐标（nextLon, nextLat）
 * <p>
 * 算法：
 * 1、将经度、纬度转换为弧度
 * 2、rand1和rand2是在 0到1.0 范围内生成的唯一随机数
 * 3、给定初始值 startLon, startLon和 maxDist（maxDist，以公里为单位）
 * 4、平均地球半径： radiusEarth = 6372.796924 km
 * 5、将最大距离转换为弧度： maxDist = maxDist / radiusEarth
 * 6、计算从 0到 maxDist 缩放的随机距离，以使得较大圆上的点比较小圆上的点具有更大的选择概率：
 * dist = acos (rand1 * (cos (maxDist) - 1) + 1)
 * 7、计算从0到2*PI弧度（0到360度）的随机方位角，所有方位角的选择概率均等
 * brg = 2 * PI * rand2
 * 8、使用起点，随机距离和随机方位来计算最终随机点的坐标
 * nextLat = asin (sin (startLat) * cos (dist) + cos (startLat) * sin (dist) * cos (brg))
 * nextLon = startLon + atan2(sin(brg) * sin(dist)*cos(startLat), cos(dist) - sin(startLat)*sin(nextLat))
 * 9、如果nextLon 小于 -PI，则
 * nextLon = nextLon + 2 * PI
 * 如果nextLon 大于 PI，则
 * nextLon = nextLon - 2 * PI
 **/
public class RandomGpsGenerate {
    private static final double radiusEarth = 6372.796924;    //平均地球半径，单位公里 km

    // GPS列表，第一个是经度，第二个是纬度，以此依次存入。调用方在此获取GPS点
    public static CopyOnWriteArrayList<Double> gpsList = new CopyOnWriteArrayList<>();

    // test
    public static void main(String[] args) {
        generateRandomGps(112.12345678, 22.12345678, 0.01);
    }

    /**
     * @param startLon 初始精度
     * @param startLat 初始纬度
     * @param maxDist  最大距离(以此画圆的半径长度， 单位 km)
     */
    public static void generateRandomGps(double startLon, double startLat, double maxDist) {
        // gps点转弧度
        startLat = startLat / 360 * 2 * Math.PI;
        startLon = startLon / 360 * 2 * Math.PI;

        //2、rand1和rand2是在 0到1.0 范围内生成的唯一随机数
        double rand1 = Math.random();
        double rand2 = Math.random();

        //5、将最大距离转换为弧度
        maxDist = maxDist / radiusEarth;

        //6、计算从 0到 maxDist 缩放的随机距离
        double dist = Math.acos(rand1 * (Math.cos(maxDist) - 1) + 1);

        //7、计算从0到2*PI弧度（0到360度）的随机方位角
        double brg = 2 * Math.PI * rand2;

        //8、使用起点，随机距离和随机方位来计算最终随机点的坐标
        double nextLat = Math.asin((Math.sin(startLat) * Math.cos(dist) + Math.cos(startLat) * Math.sin(dist) * Math.cos(brg)));
        double nextLon = startLon + Math.atan2(Math.sin(brg) * Math.sin(dist) * Math.cos(startLat), Math.cos(dist) - Math.sin(startLat) * Math.sin(nextLat));

        if (nextLon < -Math.PI) {
            nextLon = nextLon + 2 * Math.PI;
        }

        if (nextLon > Math.PI) {
            nextLon = nextLon - 2 * Math.PI;
        }

        //将经、纬度放入CopyOnWriteArrayList 当中
        gpsList.add(nextLon * 360 / (2 * Math.PI));
        gpsList.add(nextLat * 360 / (2 * Math.PI));
    }
}
