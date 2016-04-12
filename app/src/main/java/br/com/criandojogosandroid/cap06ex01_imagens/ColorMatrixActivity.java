package br.com.criandojogosandroid.cap06ex01_imagens;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;

public class ColorMatrixActivity extends AppCompatActivity {

    private Tela tela;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tela = new Tela(this);
        setContentView(tela);
    }

    private class Tela extends View {

        private Bitmap bird;
        private int bWidth, bHeight;
        private Paint paint = new Paint();

        public Tela(Context context) {
            super(context);
            AssetManager assetManager = context.getAssets();
            try {
                InputStream inputStream = assetManager.open("bird.png");
                bird = BitmapFactory.decodeStream(inputStream);
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            bWidth = bird.getWidth();
            bHeight = bird.getHeight();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            float xLeft = canvas.getWidth() / 4 - bWidth / 2;
            float xCenter = canvas.getWidth() / 2 - bWidth / 2;
            float xRight = (canvas.getWidth() / 4) * 3 - bWidth / 2;
            float yTop = canvas.getHeight() / 5 - bHeight / 2;
            float xMiddle = canvas.getHeight() / 2 - bHeight / 2;
            float yBottom = (canvas.getHeight() / 3.7f) * 3 - bHeight / 2;

            paint.setColorFilter(createFilterGrayscale());
            canvas.drawBitmap(bird, xLeft, yTop, paint);

            paint.setColorFilter(createFilterSephia());
            canvas.drawBitmap(bird, xRight, yTop, paint);

            paint.setColorFilter(createFilterNegative());
            canvas.drawBitmap(bird, xLeft, yBottom, paint);

            paint.setColorFilter(createFilterThreshold());
            canvas.drawBitmap(bird, xRight, yBottom, paint);

            paint.setColorFilter(createFilterRed());
            canvas.drawBitmap(bird, xCenter, xMiddle, paint);
        }

        ColorMatrixColorFilter createFilterGrayscale() {
            ColorMatrix colorMatrix = new ColorMatrix();
            colorMatrix.setSaturation(0);
            ColorMatrixColorFilter colorFilter = new ColorMatrixColorFilter(colorMatrix);

            return colorFilter;
        }

        ColorMatrixColorFilter createFilterSephia() {
            float colors[] = {
                    0.393f, 0.769f, 0.189f, 0, 0,
                    0.349f, 0.686f, 0.168f, 0, 0,
                    0.272f, 0.534f, 0.131f, 0, 0,
                    0, 0, 0, 1, 0
            };
            ColorMatrix colorMatrix = new ColorMatrix();
            colorMatrix.set(colors);
            ColorMatrixColorFilter colorFilter = new ColorMatrixColorFilter(colorMatrix);

            return colorFilter;
        }

        ColorMatrixColorFilter createFilterNegative() {
            float colors[] = {
                    -1, 0, 0, 0, 255,
                    0, -1, 0, 0, 255,
                    0, 0, -1, 0, 255,
                    0, 0, 0, 1, 0
            };
            ColorMatrix colorMatrix = new ColorMatrix();
            colorMatrix.set(colors);
            ColorMatrixColorFilter colorFilter = new ColorMatrixColorFilter(colorMatrix);

            return colorFilter;
        }

        ColorMatrixColorFilter createFilterRed() {
            float colors[] = {
                    1, 0, 0, 0, 0,
                    0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0,
                    0, 0, 0, 1, 0
            };
            ColorMatrix colorMatrix = new ColorMatrix();
            colorMatrix.set(colors);
            ColorMatrixColorFilter colorFilter = new ColorMatrixColorFilter(colorMatrix);

            return colorFilter;
        }

        ColorMatrixColorFilter createFilterThreshold() {
            ColorMatrix colorMatrix = new ColorMatrix();
            colorMatrix.setSaturation(0);

            float m = 255f;
            float t = -255*128f;
            float colors[] = {
                    m, 0, 0, 1, t,
                    0, m, 0, 1, t,
                    0, 0, m, 1, t,
                    0, 0, 0, 1, 0
            };
            //R = 100*255 + 255 - 255*128 = 255(100+1-128)
            //B =
            ColorMatrix threshold = new ColorMatrix(colors);
            colorMatrix.postConcat(threshold);
            ColorMatrixColorFilter colorFilter = new ColorMatrixColorFilter(colorMatrix);

            return colorFilter;
        }
    }
}
