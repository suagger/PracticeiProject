package com.example.modulea.data;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.modulea.data.Densti;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MyView extends View {
    public MyView(Context context) {
        super(context);
        init();
    }
    public MyView(Context context, AttributeSet attr) {
        super(context,attr);
        init();
    }

    public static class PieEntry {
        private float number;//数值
        private int colorRes;//颜色资源
        private boolean selected;//是否选中
        private float startC;//扇形起始角度
        private float endC;//扇形结束角度

        public PieEntry(float number,int colorRes,boolean selected){
            this.colorRes = colorRes;
            this.number = number;
            this.selected = selected;
        }

        public float getEndC() {
            return endC;
        }

        public void setEndC(float endC) {
            this.endC = endC;
        }

        public float getStartC() {
            return startC;
        }

        public void setStartC(float startC) {
            this.startC = startC;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public int getColorRes() {
            return colorRes;
        }

        public void setColorRes(int colorRes) {
            this.colorRes = colorRes;
        }

        public float getNumber() {
            return number;
        }

        public void setNumber(float number) {
            this.number = number;
        }
    }

    private OnItemClickListener listener;//点击事件的回调

    private Paint paint;
    private List<PieEntry> pieEntryList;
    private float centerX;
    private float centerY;
    private float radius;//未选中的状态半经
    private float sRadius;//选中的状态半经

    public void setOnItemClickListener( OnItemClickListener listener){
        this.listener = listener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int total = 0;//总数
        for (int i = 0; i < pieEntryList.size(); i ++){
            total += pieEntryList.get(i).getNumber();
        }

        centerX = getPivotX();
        centerY = getPivotY();

        if(sRadius == 0 ){//没有通过setxx方法时，为最小边的一半
            sRadius = (getWidth() > getHeight() ? getHeight() / 2 : getWidth() / 2);
        }
//        计算两个状态的半经，相差5dp
        radius = sRadius - Densti.dp2px(getContext(),5);

        float startC = 0;
//        遍历list画扇形

        for(int i = 0; i < pieEntryList.size(); i ++){
//            扇形走过的角度
            float sweep = 360 * (pieEntryList.get(i).getNumber() / total) ;
//            设置当前颜色
            paint.setColor(getResources().getColor(pieEntryList.get(i).colorRes));

//            判断当前扇形是否被选中
            float radiusT;
            if(pieEntryList.get(i).isSelected()){
                radiusT = sRadius;
            }else{
                radiusT = radius;
            }
            RectF rectF = new RectF(centerX - radiusT,centerY- radiusT,centerX + radiusT,centerY + radiusT);
            canvas.drawArc(rectF,startC,sweep - 3,true,paint);
//            外围的短线和百分比
            float arcCenterC = startC + sweep / 2;
//            当前扇形的中间点,作为短线的起始点
            float arcCenterX = 0;
            float arcCenterY = 0;
//            扇形的结束点
            float arcCenterX2 = 0;
            float arcCenterY2 = 0;

//            百分比数字
            DecimalFormat numberFormat = new DecimalFormat("00.00");
            paint.setColor(Color.BLACK);

//            分象限，求出结束点和起始点，并画出短线
            if(arcCenterC >=0 && arcCenterC < 90){
                arcCenterX = (float) (centerX + radiusT * Math.cos(arcCenterC * Math.PI / 180));
                arcCenterY = (float) (centerY + radiusT * Math.sin(arcCenterC * Math.PI / 180));
                arcCenterX2 = (float) (arcCenterX + Densti.dp2px(getContext(),10) * Math.cos(arcCenterC * Math.PI / 180));
                arcCenterY2 = (float) (arcCenterY + Densti.dp2px(getContext(),10) * Math.sin(arcCenterC * Math.PI / 180));
                canvas.drawLine(arcCenterX,arcCenterY,arcCenterX2,arcCenterY2,paint);
                canvas.drawText(numberFormat.format(pieEntryList.get(i).getNumber() / total * 100) + "%",arcCenterX2,arcCenterY2 + paint.getTextSize() / 2,paint);
            }else if(arcCenterC >= 90 && arcCenterC <180){
                arcCenterC = 180 - arcCenterC;
                arcCenterX = (float) (centerX - radiusT * Math.cos(arcCenterC * Math.PI / 180));
                arcCenterY = (float) (centerY + radiusT * Math.sin(arcCenterC * Math.PI / 180));
                arcCenterX2 = (float) (arcCenterX - Densti.dp2px(getContext(),10) * Math.cos(arcCenterC * Math.PI / 180));
                arcCenterY2 = (float) (arcCenterY + Densti.dp2px(getContext(),10) * Math.sin(arcCenterC * Math.PI / 180));
                canvas.drawLine(arcCenterX,arcCenterY,arcCenterX2,arcCenterY2,paint);
                canvas.drawText(numberFormat.format(pieEntryList.get(i).getNumber() / total * 100) + "%",(float) (arcCenterX2 - paint.getTextSize() * 3.5),arcCenterY2 + paint.getTextSize() / 2,paint);
            }else if(arcCenterC >= 180 && arcCenterC < 270){
                arcCenterC = 270 - arcCenterC;
                arcCenterX = (float) (centerX - radiusT * Math.sin(arcCenterC * Math.PI / 180));
                arcCenterY = (float) (centerY - radiusT * Math.cos(arcCenterC * Math.PI / 180));
                arcCenterX2 = (float) (arcCenterX - Densti.dp2px(getContext(),10) * Math.sin(arcCenterC * Math.PI / 180));
                arcCenterY2 = (float) (arcCenterY - Densti.dp2px(getContext(),10) * Math.cos(arcCenterC * Math.PI / 180));
                canvas.drawLine(arcCenterX,arcCenterY,arcCenterX2,arcCenterY2,paint);
                canvas.drawText(numberFormat.format(pieEntryList.get(i).getNumber() / total * 100) + "%",(float)(arcCenterX2 - paint.getTextSize() * 3.5),arcCenterY2,paint);
            }else if(arcCenterC >= 270 && arcCenterC < 360){
                arcCenterC = 360 - arcCenterC;
                arcCenterX = (float) (centerX + radiusT * Math.cos(arcCenterC * Math.PI / 180));
                arcCenterY = (float) (centerY - radiusT * Math.sin(arcCenterC * Math.PI / 180));
                arcCenterX2 = (float) (arcCenterX + Densti.dp2px(getContext(),10) * Math.cos(arcCenterC * Math.PI / 180));
                arcCenterY2 = (float) (arcCenterY - Densti.dp2px(getContext(),10) * Math.sin(arcCenterC * Math.PI / 180));
                canvas.drawLine(arcCenterX,arcCenterY,arcCenterX2,arcCenterY2,paint);
                canvas.drawText(numberFormat.format(pieEntryList.get(i).getNumber() / total * 100 ) + "%",arcCenterX2,arcCenterY2,paint);
            }
//          将每个扇形的起始角度 和 结束角度放入对应的对象
            pieEntryList.get(i).setStartC(startC);
            pieEntryList.get(i).setEndC(startC + sweep);
            startC += sweep;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX;
        float touchY;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                touchX = event.getX();
                touchY = event.getY();
//                判断touch点到圆心的距离是否小于半经
                if(Math.pow(touchX - centerX,2) + Math.pow(touchY - centerY,2) <= Math.pow(radius,2)){
//                   计算touch点和圆心的连线与x轴正方向的夹角
                    float touchC = getSweep(touchX,touchY);
//                    判断touch点在哪个扇形中
                    for(int i = 0; i < pieEntryList.size(); i ++){
                        if(touchC >= pieEntryList.get(i).getStartC() && touchC < pieEntryList.get(i).getEndC()){
                            if(pieEntryList.get(i).selected == true){
                                pieEntryList.get(i).setSelected(false);
                            }else {
                                pieEntryList.get(i).setSelected(true);
                            }
                            if(listener != null){
                                listener.onItemClick(i);
                            }else{
                                pieEntryList.get(i).setSelected(false);
                            }
                        }
                        invalidate();
                    }

                }
                break;
        }
        return super.onTouchEvent(event);
    }

    private float getSweep(float touchX,float touchY){
        float xZ = touchX - centerX;
        float yZ = touchY - centerY;
        float a = Math.abs(xZ);
        float b = Math.abs(yZ);
        double c = Math.toDegrees(Math.atan(b / a));
        if(xZ >= 0 && yZ >= 0){
            return (float) c;
        }else if(xZ <= 0 && yZ >= 0){
            return 180 - (float) c;
        }else if(xZ <= 0 && yZ >= 0){
            return (float) c + 180;
        }else {
            return 360 - (float) c;
        }
    }


    public interface OnItemClickListener{
        public void onItemClick(int position);
    }

    public void setRadius(float radius){
        this.sRadius = radius;
    }

//    设置数据并刷新
    public void setPieEntry(List<PieEntry> pieEntries){
        this.pieEntryList = pieEntries;
        invalidate();
    }

    private void init(){
        pieEntryList = new ArrayList<>();
        paint = new Paint();
        paint.setTextSize(Densti.sp2px(getContext(),15));
        paint.setAntiAlias(true);
    }
}
