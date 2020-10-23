# 生成随机GPS坐标点


      根据给定的始点坐标经纬度（startLon, startLat），以及两点之间最大距离（maxDist，以公里km为单位）,生成下一个随机的GPS点坐标（nextLon, nextLat）


 算法：
 * 1、将经度、纬度转换为弧度
 * 2、rand1和rand2是在 0到1.0 范围内生成的唯一随机数
 * 3、给定初始值 startLon, startLon和 maxDist（maxDist，以公里为单位）
 * 4、平均地球半径： 

       radiusEarth = 6372.796924 km
 * 5、将最大距离转换为弧度： 

      maxDist = maxDist / radiusEarth
 * 6、计算从 0到 maxDist 缩放的随机距离，以使得较大圆上的点比较小圆上的点具有更大的选择概率：

       dist = acos (rand1 * (cos (maxDist) - 1) + 1)
    
 * 7、计算从0到2*PI弧度（0到360度）的随机方位角，所有方位角的选择概率均等
   
       brg = 2 * PI * rand2
 * 8、使用起点，随机距离和随机方位来计算最终随机点的坐标

       nextLat = asin (sin (startLat) * cos (dist) + cos (startLat) * sin (dist) * cos (brg))
       nextLon = startLon + atan2(sin(brg) * sin(dist)*cos(startLat), cos(dist) - sin(startLat)*sin(nextLat))
 * 9、如果nextLon 小于 -PI，则：
   
      nextLon = nextLon + 2 * PI
      
    如果nextLon 大于 PI，则：
    
      nextLon = nextLon - 2 * PI

