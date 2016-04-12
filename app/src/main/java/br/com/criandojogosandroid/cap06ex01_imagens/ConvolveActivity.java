package br.com.criandojogosandroid.cap06ex01_imagens;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicConvolve3x3;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;

public class ConvolveActivity extends AppCompatActivity {

    private Tela tela;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tela = new Tela(this);
        setContentView(tela);
    }

    private class Tela extends View {

        private Bitmap bird, birdEmboss, birdSharpen, birdSmooth;
        private int bWidth, bHeight;
        private Context context;

        public Tela(Context context) {
            super(context);
            this.context = context;
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

            float smooth[] = new float[]{
                    1/9f, 1/9f, 1/9f,
                    1/9f, 1/9f, 1/9f,
                    1/9f, 1/9f, 1/9f};
            birdSmooth = createConvolveBitmap(bird, smooth);

            float sharpen[] = new float[]{
                     0, -1,  0,
                    -1,  5, -1,
                     0, -1,  0};
            birdSharpen = createConvolveBitmap(bird, sharpen);

            float emboss[] = new float[]{
                    -2, -1,  0,
                    -1,  1,  1,
                    0,  1,  2};
            birdEmboss = createConvolveBitmap(bird, emboss);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            float xLeft = canvas.getWidth() / 4 - bWidth / 2;
            float xRight = (canvas.getWidth() / 4) * 3 - bWidth / 2;
            float yTop = canvas.getHeight() / 4 - bHeight / 2;
            float yBottom = (canvas.getHeight() / 4) * 3 - bHeight / 2;

            canvas.drawBitmap(bird, xLeft, yTop, null);
            canvas.drawBitmap(birdSmooth, xRight, yTop, null);
            canvas.drawBitmap(birdSharpen, xLeft, yBottom, null);
            canvas.drawBitmap(birdEmboss, xRight, yBottom, null);
        }

        private Bitmap createConvolveBitmap(Bitmap original, float[] coefficients) {
            Bitmap bitmap = createNewBitmap(original);

            RenderScript rs = RenderScript.create(context);

            Allocation allocIn = Allocation.createFromBitmap(rs, original);
            Allocation allocOut = Allocation.createFromBitmap(rs, bitmap);

            ScriptIntrinsicConvolve3x3 convolution
                    = ScriptIntrinsicConvolve3x3.create(rs, Element.U8_4(rs));
            convolution.setInput(allocIn);
            convolution.setCoefficients(coefficients);
            convolution.forEach(allocOut);

            allocOut.copyTo(bitmap);
            rs.destroy();
            return bitmap;
        }

        private Bitmap createNewBitmap(Bitmap bitmap) {
            Bitmap newBitmap = Bitmap.createBitmap(
                    bitmap.getWidth(),
                    bitmap.getHeight(),
                    bitmap.getConfig()
            );

            return newBitmap;
        }
    }
}
