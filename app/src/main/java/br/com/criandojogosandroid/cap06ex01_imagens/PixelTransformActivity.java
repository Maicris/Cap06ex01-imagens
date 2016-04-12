package br.com.criandojogosandroid.cap06ex01_imagens;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;

public class PixelTransformActivity extends AppCompatActivity {

    private Tela tela;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tela = new Tela(this);
        setContentView(tela);
    }

    private class Tela extends View {

        private Bitmap bird, birdDistort;

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
            birdDistort = createFilterDistort(bird, 10);
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
                    birdDistort,
                    canvas.getWidth() / 2 - birdDistort.getWidth() / 2,
                    (canvas.getHeight() / 4) * 3 - birdDistort.getHeight() / 2,
                    null);
        }

        private Bitmap createNewBitmap(Bitmap bitmap, int factor) {
            Bitmap newBitmap = Bitmap.createBitmap(
                    bitmap.getWidth(),
                    bitmap.getHeight() + factor,
                    bitmap.getConfig()
            );

            return newBitmap;
        }

        private Bitmap createFilterDistort(Bitmap bitmap, int factor) {
            Bitmap m = createNewBitmap(bitmap, factor);
            for (int i = 0; i < bitmap.getWidth(); i ++) {
                for (int j = 0; j < bitmap.getHeight(); j ++) {
                    int color = bitmap.getPixel(i, j);
                    if (i % 2 == 0)
                        m.setPixel(i, j, color);
                    else
                        m.setPixel(i, j + factor, color);
                }
            }

            return m;
        }
    }
}
