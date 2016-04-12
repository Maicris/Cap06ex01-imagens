package br.com.criandojogosandroid.cap06ex01_imagens;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;

public class ColorTransformActivity extends AppCompatActivity {

    private Tela tela;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tela = new Tela(this);
        setContentView(tela);
    }

    private class Tela extends View {

        private Bitmap bird, birdBrighten;

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
            birdBrighten = createFilterBrighten(bird, 1.3f);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawBitmap(
                    bird,
                    canvas.getWidth() / 2 - bird.getWidth() / 2,
                    canvas.getHeight() / 4 - bird.getHeight() / 2,
                    null);
            canvas.drawBitmap(
                    birdBrighten,
                    canvas.getWidth() / 2 - birdBrighten.getWidth() / 2,
                    (canvas.getHeight() / 4) * 3 - birdBrighten.getHeight() / 2,
                    null);
        }

        private Bitmap createNewBitmap(Bitmap bitmap) {
            Bitmap newBitmap = Bitmap.createBitmap(
                    bitmap.getWidth(),
                    bitmap.getHeight(),
                    bitmap.getConfig()
            );

            return newBitmap;
        }

        private Bitmap createFilterBrighten(Bitmap bitmap, float factor) {
            Bitmap m = createNewBitmap(bitmap);
            for (int i = 0; i < m.getWidth(); i ++) {
                for (int j = 0; j < m.getHeight(); j ++) {
                    int color = bitmap.getPixel(i, j);
                    int a = Color.alpha(color);
                    int r = (int)(Color.red(color) * factor);
                    r = r > 255 ? 255 : r;
                    int g = (int)(Color.green(color) * factor);
                    g = g > 255 ? 255 : g;
                    int b = (int)(Color.blue(color) * factor);
                    b = b > 255 ? 255 : b;
                    int mColor = Color.argb(a, r, g, b);
                    m.setPixel(i, j, mColor);
                }
            }

            return m;
        }
    }
}
