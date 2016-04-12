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
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;

public class BlurActivity extends AppCompatActivity {

    private Tela tela;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tela = new Tela(this);
        setContentView(tela);
    }

    private class Tela extends View {

        private Bitmap bird, birdBlur;
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

            birdBlur = createBlurBitmap(bird, 5);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            float xCenter = canvas.getWidth() / 2 - bWidth / 2;
            float yTop = canvas.getHeight() / 4 - bHeight / 2;
            float yBottom = (canvas.getHeight() / 4) * 3 - bHeight / 2;

            canvas.drawBitmap(bird, xCenter, yTop, null);
            canvas.drawBitmap(birdBlur, xCenter, yBottom, null);
        }

        private Bitmap createBlurBitmap(Bitmap original, float radius) {

            Bitmap bitmap = createNewBitmap(original);

            RenderScript rs = RenderScript.create(context);
            Allocation allocationIn = Allocation.createFromBitmap(rs, original);
            Allocation allocationOut = Allocation.createFromBitmap(rs, bitmap);

            ScriptIntrinsicBlur scriptIntrinsicBlur =
                    ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
            scriptIntrinsicBlur.setInput(allocationIn);
            scriptIntrinsicBlur.forEach(allocationOut);
            scriptIntrinsicBlur.setRadius(radius);

            allocationOut.copyTo(bitmap);
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
