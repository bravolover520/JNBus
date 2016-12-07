package com.purityboy.jnbus.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

import com.purityboy.jnbus.R;
import com.purityboy.jnbus.entity.Bus;
import com.purityboy.jnbus.entity.Station;
import com.purityboy.jnbus.utils.EarthDistance;
import com.purityboy.jnbus.utils.JZLocationConverter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/**
 * Created by John on 2016/11/28.
 */

public class BusStationsView extends View {

    private static final int COUNT_ROW = 5;
    //屏幕的宽高
    private Point screenPoint = new Point();
    //屏幕的宽度
    private int screenWidth;
    //距离左侧间距
    private int paddingLeft;
    //距离右侧的边距
    private int paddingRight;
    //线的长度
    private int lineWidth;

    //一行5个元素
    private int rowCount = COUNT_ROW;
    //线段的画笔
    private Paint linePaint;
    //圆点的画笔
    private Paint circlePaint;
    //文本的画笔
    private Paint textPaint;
    //公交的画笔
    private Paint busTextPaint;
    //图像的画笔
    private Paint bitmapPaint;

    //记录圆点路径的集合
    private Path[] circlePaths;
    //记录线段路径的集合
    private Path[] linePaths;

    //图像
    private Bitmap busBitmap;
    private Bitmap busKBitmap;  //空调
    //图像的宽高
    private int bitmapWidth;
    private int bitmapHeight;
    //要绘画的图像大小
    private Rect bitmapRect;

    //各个圆点中心点的集合
    private Point[] circlePoints;
    //各辆公交点的集合
    private BusPoint[] busPoints;

    //圆点内圆半径
    private int circleInnerRadius;

    //圆点笔触宽度（单位像素）
    private int circleStokeWidth;
    //圆点半径，内圆半径+笔触宽度(单位px)
    private int circleRadius;

    //起点距离整个控件顶部的距离(单位像素)
    private int startCircleMarginTop;

    //圆点的颜色
    private int circleColor;
    //圆点选中的颜色
    private int circleSelectColor;

    //线段的颜色
    private int lineColor;
    //文本的的颜色
    private int textColor;
    //文本选中的颜色
    private int textSelectColor;

    //线段笔触宽度
    private int lineStrokeWidth;

    //文本字体大小
    private int textSize;

    //记录每个条目的区域集合
    private Region[] textRegions;
    //记录每车辆的区域集合
    private Region[] busRegions;

    //手势监听
    //条目点击事件
    //站点顺序
    private List<Station> datas;
    //插桩得值
    private List<Bus> bus;    //公交信息

    public BusStationsView(Context context) {
        super(context);
        initArrts(context, null);
        init();
    }

    public BusStationsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initArrts(context, attrs);
        init();
    }

    public BusStationsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initArrts(context, attrs);
        init();
    }

    private void initArrts(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BusStationsView);
        try {
            circleColor = array.getColor(R.styleable.BusStationsView_circle_color, getResources().getColor(android.R.color.darker_gray));
            lineColor = array.getColor(R.styleable.BusStationsView_line_color, getResources().getColor(android.R.color.darker_gray));
            textColor = array.getColor(R.styleable.BusStationsView_text_color, getResources().getColor(android.R.color.darker_gray));
            circleStokeWidth = array.getDimensionPixelSize(R.styleable.BusStationsView_circle_stroke, 4);
            lineStrokeWidth = array.getDimensionPixelSize(R.styleable.BusStationsView_line_stroke, 4);
            textSize = (int) array.getDimension(R.styleable.BusStationsView_text_textSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
            startCircleMarginTop = array.getDimensionPixelSize(R.styleable.BusStationsView_circle_start_marginTop, 200);
            circleInnerRadius = array.getDimensionPixelSize(R.styleable.BusStationsView_circle_radius, 5);
            circleSelectColor = array.getColor(R.styleable.BusStationsView_circle_select_color, getResources().getColor(android.R.color.holo_orange_dark));
            textSelectColor = array.getColor(R.styleable.BusStationsView_text_select_color, getResources().getColor(android.R.color.holo_orange_dark));
            circleRadius = circleInnerRadius + circleStokeWidth;
        } finally {
            array.recycle();
        }
    }

    private void init() {
        getScreen();
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        circlePaint.setColor(circleColor);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(circleStokeWidth);   //圆的线宽

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        linePaint.setColor(lineColor);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(lineStrokeWidth);      //线的线宽

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        textPaint.setColor(textColor);
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setTextSize(textSize);

        busTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        busTextPaint.setColor(textSelectColor);
        busTextPaint.setStyle(Paint.Style.STROKE);
        busTextPaint.setTextSize(textSize);

        bitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bitmapPaint.setFilterBitmap(true);
        bitmapPaint.setDither(true);

        //得到屏幕宽度
        screenWidth = screenPoint.x;
        paddingLeft = getPaddingLeft();
        paddingRight = getPaddingRight();

        //一行的真实宽度
        int realWidth = screenWidth - paddingLeft - paddingRight;
        //每条线段的宽度
        lineWidth = (realWidth - circleRadius * 2 * rowCount) / (rowCount - 1);   //一行显示5个圆点

        //得到Bitmap
        busBitmap = BitmapFactory.decodeResource(this.getContext().getResources(), R.mipmap.ic_bus_p);
        busKBitmap = BitmapFactory.decodeResource(this.getContext().getResources(), R.mipmap.ic_bus_k);
        bitmapWidth = busBitmap.getWidth();
        bitmapHeight = busBitmap.getHeight();
        bitmapRect = new Rect(0, 0, bitmapWidth, bitmapHeight);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //此处为ScrollView搭配使用，达到滑动的目的
        int viewHeight = getPaddingTop() + getPaddingBottom() + computeMinViewHeight();
        setMeasuredDimension(widthMeasureSpec, viewHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        computeCircleAndLinePath();
        computeBus();
    }

    private int computeMinViewHeight() {
        int viewHeight = 0;
        if (null != datas && !datas.isEmpty()) {
            int counts = datas.size();
            int row = counts % rowCount == 0 ? counts / rowCount : counts / rowCount + 1;
            viewHeight = row * (lineWidth * 2 + circleRadius * 2);
        }
        return viewHeight;
    }

    private void getScreen() {
        WindowManager wm = (WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getSize(screenPoint);
    }

    //计算圆点与线的轨迹
    private void computeCircleAndLinePath() {
        if (null != datas && !datas.isEmpty()) {
            circlePoints = new Point[datas.size()];
            circlePaths = new Path[datas.size()];
            linePaths = new Path[datas.size() - 1];

            //得到行数
            int lineHeight = lineWidth * 2;

            //处理文字显示与圆点显示
            for (int i = 0; i < datas.size(); i++) {
                //得到当前字在第几行
                int offsetY = i / rowCount;     //Y的位置
                int offsetX = offsetY % 2 == 0 ? i % rowCount : rowCount - 1 - i % rowCount;     //X的位置

                Path circlePath = new Path();
                //计算每个圆点的中心点
                int circleCenterX = paddingLeft + circleRadius + (offsetX * (lineWidth + circleRadius * 2));
                int circleCenterY = startCircleMarginTop + circleRadius + (offsetY * (lineHeight + circleRadius * 2));
                circlePoints[i] = new Point(circleCenterX, circleCenterY);

                //计算圆点路径
                circlePath.addCircle(circleCenterX, circleCenterY, circleRadius, Path.Direction.CCW);
                circlePaths[i] = circlePath;
                if (i == datas.size() - 1) {
                    continue;
                }

                //计算线段的路径
                Path linePath = new Path();
                //竖向
                if ((i + 1) % rowCount == 0) {
                    //此条线段为竖向
                    int lineStartY = circleCenterY + circleRadius;
                    int lineEndY = lineStartY + lineHeight;
                    linePath.moveTo(circleCenterX, lineStartY);     //将起始轮廓点移至x，y坐标点，默认情况为0,0点
                    linePath.lineTo(circleCenterX, lineEndY);     //从当前轮廓点绘制一条线段到x，y点
                } else {
                    //词条线段为横向
                    //计算每条线段的起始点(横向的)
                    int lineStartX = offsetY % 2 == 0 ? circleCenterX + circleRadius : circleCenterX - circleRadius;
                    int lineEndX = offsetY % 2 == 0 ? lineStartX + lineWidth : lineStartX - lineWidth;
                    linePath.moveTo(lineStartX, circleCenterY);     //将起始轮廓点移至x，y坐标点，默认情况为0,0点
                    linePath.lineTo(lineEndX, circleCenterY);     //从当前轮廓点绘制一条线段到x，y点
                }
                linePaths[i] = linePath;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (null != datas && !datas.isEmpty()) {
            drawCircleAndLine(canvas);
            drawItemText(canvas);
            if (null != bus && !bus.isEmpty()) {
                drawBus(canvas);
                drawBusText(canvas);
            }
        }
    }

    /**
     * 绘制圆和线段
     */
    private void drawCircleAndLine(Canvas canvas) {
        for (int i = 0; i < circlePaths.length; i++) {
            //改变颜色与风格
            circlePaint.setStyle(Paint.Style.STROKE);
            circlePaint.setColor(circleColor);
            canvas.drawPath(circlePaths[i], circlePaint);
        }
        for (int i = 0; i < linePaths.length; i++) {
            canvas.drawPath(linePaths[i], linePaint);
        }
    }

    /**
     * 绘制车辆
     */
    private void drawBus(Canvas canvas) {
        if (null != busPoints) {
            for (int i = 0; i < busPoints.length; i++) {
                BusPoint bus = busPoints[i];
                if (null != bus) {
                    //left /top /right /bottom
                    //判断车辆颜色
                    Point point = bus.point;
                    Rect rect = new Rect(point.x - bitmapWidth / 2 , point.y - bitmapHeight, point.x + bitmapWidth / 2, point.y);
                    if (null != rect) {
                        if (bus.type == BusPoint.TYPE_AC)
                            canvas.drawBitmap(busKBitmap, bitmapRect, rect, bitmapPaint);
                        else if (bus.type == BusPoint.TYPE_ORDINARY)
                            canvas.drawBitmap(busBitmap, bitmapRect, rect, bitmapPaint);
                    }
                }
            }
        }
    }

    /**
     * 绘制车辆的编号
     */
    private void drawBusText(Canvas canvas) {
        busRegions = new Region[busPoints.length];
        for (int i = 0; i < busPoints.length; i++) {
            Region region = new Region();
            if (null != busPoints && busPoints[i] != null && null != busPoints[i].point) {
                String text = busPoints[i].busId;
                char[] chars = text.toCharArray();
                int itemStartX = busPoints[i].point.x + textSize;      //文字开始绘制位置X
                int itemStartY = busPoints[i].point.y - (bitmapHeight - textSize ) / 2;     //文字开始绘制位置Y
                //每个条目区域的计算
                Rect textArea = new Rect();
                textArea.left = itemStartX;                             //左侧，中心点X
                textArea.right = itemStartX + textSize * 3;             //右侧
                textArea.bottom = itemStartY + textSize / 2;
                textArea.top = itemStartY - textSize / 2;
                for (int j = 0; j < chars.length; j++) {
                    canvas.drawText(String.valueOf(text.charAt(j)), itemStartX + textSize / 2 * j , itemStartY , busTextPaint);
                }
                region.set(textArea);
                busRegions[i] = region;
            }
        }
    }

    /**
     * 绘制文本
     */
    private void drawItemText(Canvas canvas) {
        //记录每个条目区域
        textRegions = new Region[datas.size()];
        for (int i = 0; i < datas.size(); i++) {
            Region region = new Region();
            String text = datas.get(i).getStationName();
            char[] chars = text.toCharArray();
            //显示几行
            int textRow = chars.length % 5 == 0 ? chars.length / 5 : chars.length / 5 + 1;
            int itemStartX = circlePoints[i].x;                                         //文字开始绘制位置X
            int itemStartY = circlePoints[i].y - circleRadius - textRow * textSize;     //文字开始绘制位置Y
            //每个条目区域的计算
            Rect textArea = new Rect();
            textArea.left = itemStartX;                             //左侧，中心点X
            textArea.right = itemStartX + lineWidth;                //右侧，中心点X + 线宽
            textArea.bottom = circlePoints[i].y - circleRadius;     //绘制区域 圆的最上方
            textArea.top = itemStartY;                              //顶部
            for (int j = 0; j < chars.length; j++) {
                //得到当前字在第几行
                int offsetX = j % 5;
                int offsetY = j / 5;
                int tX = itemStartX + offsetX * textSize;
                int tY = itemStartY + offsetY * textSize;
                canvas.drawText(String.valueOf(text.charAt(j)), tX , tY , textPaint);
            }
            region.set(textArea);
            textRegions[i] = region;
        }
    }

    private void computeBus() {
        if (null != this.bus && !this.bus.isEmpty()) {
            if (null == this.datas || this.datas.isEmpty())
                return;

            int size = bus.size();
            busPoints = new BusPoint[size];    //几辆公交几个点
            for (int i = 0; i < size; i++) {
                Bus bus = this.bus.get(i);
                //得到当前公交信息，得到站点和距离
                double bus_lat = bus.getLat();
                double bus_lng = bus.getLng();      //车辆的经纬度，得到的是谷歌地球卫星(也就是真实的地理位置)http://www.gpsspg.com/maps.htm
                int stationSeqNum = bus.getStationSeqNum();       //即将到站
                String busId = bus.getBusId();

                if (stationSeqNum < 2 || stationSeqNum > this.datas.size())
                    return;

                int right_station_index = stationSeqNum - 1;    //即将到站的站点的索引
                int left_station_index = stationSeqNum - 2;     //离开站点的索引

                //从站点列表中得到即将到站的站点
                Station right = this.datas.get(right_station_index);
                double right_station_lat = right.getLat();      //站点的位置都是百度地图标注得到的位置
                double right_station_lng = right.getLng();
                //把百度坐标转换为地理位置真实坐标
                JZLocationConverter.LatLng loc_right =  JZLocationConverter.bd09ToWgs84(new JZLocationConverter.LatLng(right_station_lat, right_station_lng));
                //从站点列表中得到已经驶出的站点
                Station left = this.datas.get(left_station_index);
                double left_station_lat = left.getLat();
                double left_station_lng = left.getLng();
                JZLocationConverter.LatLng loc_left =  JZLocationConverter.bd09ToWgs84(new JZLocationConverter.LatLng(left_station_lat, left_station_lng));

                //得到左右两站点之间的距离
                double distance_station_station = EarthDistance.getDistance(loc_right.getLatitude(), loc_right.getLongitude(), loc_left.getLatitude(), loc_left.getLongitude());

                //得到车与即将到站的距离
                double distance_bus_station = EarthDistance.getDistance(bus_lat, bus_lng, loc_right.getLatitude(), loc_right.getLongitude());

                Point right_circle_point = circlePoints[right_station_index];       //即将到站的圆点
                Point left_circle_point = circlePoints[left_station_index];         //离开的

                //判断这两点是同在x轴上还是y轴上
                if (right_circle_point.y == left_circle_point.y) {
                    //同在x轴上，线段长度为1（判断左右）
                    double x;
                    if (((stationSeqNum - 1) / rowCount) % 2 == 0) {
                        //从左到右
                        x = right_circle_point.x - lineWidth * (distance_bus_station / distance_station_station);
                    } else
                        x = right_circle_point.x + lineWidth * (distance_bus_station / distance_station_station);
                    busPoints[i] = new BusPoint(i, BusPoint.COORDINATE_X, new Point((int) x, right_circle_point.y), busId, busId.contains("k") ? BusPoint.TYPE_AC : BusPoint.TYPE_ORDINARY);
                } else if (right_circle_point.x == left_circle_point.x) {
                    //同在y轴上
                    double y = right_circle_point.y - lineWidth * 2 * (distance_bus_station / distance_station_station);
                    busPoints[i] = new BusPoint(i, BusPoint.COORDINATE_Y, new Point(right_circle_point.x, (int) y), busId, busId.contains("k") ? BusPoint.TYPE_AC : BusPoint.TYPE_ORDINARY);
                }
            }
        }
    }

    public void setDatas(List<Station> datas) {
        this.datas = datas;
    }

    //插入公交数据
    public void setBuses(List<Bus> bus) {
        this.bus = bus;
    }

    static class BusPoint {
        Point point;
        int index;          //索引
        String busId;
        @BusCoordinate
        int coordinate;     //坐标x/y
        @BusType
        int type;

        public BusPoint(int index, int coordinate, Point point, String busId, int type) {
            this.index = index;
            this.coordinate = coordinate;
            this.point = point;
            this.busId = busId;
            this.type = type;
        }

        public static final int COORDINATE_X = 0;       //x轴
        public static final int COORDINATE_Y = 1;       //y轴

        @Retention(RetentionPolicy.SOURCE)
        @IntDef({COORDINATE_X, COORDINATE_Y})
        public @interface BusCoordinate{}

        public static final int TYPE_ORDINARY = 0;  //普通
        public static final int TYPE_AC = 1;    //空调

        @Retention(RetentionPolicy.SOURCE)
        @IntDef({TYPE_ORDINARY, TYPE_AC})
        public @interface BusType{}
    }
}
