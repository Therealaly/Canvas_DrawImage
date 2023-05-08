package com.example.drawimage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private Canvas mCanvas;
    private Paint mPaint = new Paint(); // gambar objek
    private Paint mPaintText = new Paint(Paint.UNDERLINE_TEXT_FLAG); //gambar tulisannya
    private Bitmap mBitmap;
    private ImageView mImageView;
    private Rect mRect = new Rect();
    private Rect mBounds = new Rect();

    private static final int OFFSET = 120;
    private int mOffset = OFFSET; // mengatur posisi dari objek
    private static final int MULTIPLIER = 100; // mengatur warna agar beruba2 scr otomatis

    private int mColorBackground;
    private int mColorRectangle;
    private int mColorAccent;
    private int mColorDoor;
    private int mColorWindow;
    private int mColorRoof;
    private int mColorChimney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mColorBackground = ResourcesCompat.getColor(getResources(),
                R.color.colorBackground, null);
        mColorAccent = ResourcesCompat.getColor(getResources(),
                R.color.colorAccent, null);
        mColorRectangle = ResourcesCompat.getColor(getResources(),
                R.color.colorRectangle, null);
        mColorDoor = ResourcesCompat.getColor(getResources(),
                R.color.colorDoor, null);
        mColorWindow = ResourcesCompat.getColor(getResources(),
                R.color.colorWindow, null);
        mColorRoof = ResourcesCompat.getColor(getResources(),
                R.color.colorRoof, null);
        mColorChimney = ResourcesCompat.getColor(getResources(),
                R.color.colorChimney, null);

        // mewarnai BG
        mPaint. setColor(mColorBackground);
        mPaintText.setColor(ResourcesCompat.getColor(getResources(), R.color.black, null));
        mPaintText.setTextSize(70);

        mImageView = findViewById(R.id.myimageview);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawSomething(view);
            }
        });
    }

    public void drawSomething(View view) {
        // panjang dan lebar ImageView
        int vWidth = view.getWidth();
        int vHeight = view.getHeight();
        int halfWidth = vWidth/2;
        int halfHeight = vHeight/2;

        if (mOffset == OFFSET) { //mOffset = 120
            mBitmap = Bitmap.createBitmap(vWidth, vHeight, Bitmap.Config.ARGB_8888);
            mImageView.setImageBitmap(mBitmap);
            mCanvas = new Canvas(mBitmap);
            mCanvas.drawColor(mColorBackground);
            mCanvas.drawText(getString(R.string.keep_tapping), 100, 100, mPaintText);
            mOffset += OFFSET;
        }
        else {
            // buat persegi panjang
            if (mOffset < 250) { //mOffset = 240
                mPaint.setColor(mColorRectangle - MULTIPLIER * mOffset);

                //koordinat 4 sudut.
                mRect.set(200, 700, 900, 1400);
                mCanvas.drawRect(mRect, mPaint);
                mOffset += OFFSET;
            } else if (mOffset < 370) { //mOffset = 360
                mPaint.setColor(mColorWindow);

                mRect.set(250, 750, 450, 1000);
                mCanvas.drawRect(mRect, mPaint);
                mOffset += OFFSET;
            } else if (mOffset < 490) { //mOffset = 480
                mPaint.setColor(mColorWindow);

                mRect.set(650, 750, 850, 1000);
                mCanvas.drawRect(mRect, mPaint);
                mOffset += OFFSET;
            } else if (mOffset < 610) { //mOffset = 600
                mPaint.setColor(mColorDoor);

                mRect.set(450, 1075, 650, 1400);
                mCanvas.drawRect(mRect, mPaint);
                mOffset += OFFSET;
            } else if (mOffset < 730)  { //mOffset = 720
                mPaint.setColor(mColorChimney);

                mRect.set(250, 350, 375, 600);
                mCanvas.drawRect(mRect, mPaint);
                mOffset += OFFSET;
            }
            else {
                //mPaint.setColor(mColorAccent - MULTIPLIER * mOffset);
                //mCanvas.drawCircle(halfWidth, halfHeight, halfWidth/5, mPaint);
                mOffset += OFFSET;

                // membut titik segitiga
                Point a = new Point(halfWidth - 400, halfHeight - 290);
                Point b = new Point(halfWidth + 400, halfHeight - 290);
                Point c = new Point(halfWidth, halfHeight - 750);

                //harus digambar utk buat segitiga
                Path path = new Path();
                path.setFillType(Path.FillType.EVEN_ODD); // even_odd memberi color didalam path, inverse_odd memberi color dilur path
                path.lineTo(a.x, a.y);
                path.lineTo(b.x, b.y);
                path.lineTo(c.x, c.y);
                path.lineTo(a.x, a.y); // harus ketitik awal lagi agar garis nyambung
                path.close();

                mPaint.setColor(mColorRoof);
                mCanvas.drawPath(path, mPaint);
                // gambar teks
                String text = getString(R.string.house);
                // untuk mendapat posisi tengah dari teks. mnggunakan mBounds
                mPaintText.getTextBounds(text, 0, text.length(), mBounds);
                int x = halfWidth - mBounds.centerX();
                int y = 1600;
                mCanvas.drawText(text, x, y, mPaintText);


            }
        }
        // agar canvas dapat dibuat, harus menggunakan invalidate
        view.invalidate();
    }
}