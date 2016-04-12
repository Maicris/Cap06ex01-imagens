package br.com.criandojogosandroid.cap06ex01_imagens;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String items[] = {
                "Carregando Imagens de Assets",
                "Formatos de Imagens",
                "Modificando a Geometria das Imagens",
                "Desenhando Imagens Parciais",
                "Transformação de Imagens",
                "Clareamento de imagem",
                "Efeito de Distorção",
                "Filtros Usando ColorMatrix",
                "Efeito de Blur",
                "Efeitos usando Matriz de Convolução"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        switch (position) {
            case 0:
                startActivity(new Intent(this, SimpleActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, FormatsActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, GeometryActivity.class));
                break;
            case 3:
                startActivity(new Intent(this, RectActivity.class));
                break;
            case 4:
                startActivity(new Intent(this, TransformationActivity.class));
                break;
            case 5:
                startActivity(new Intent(this, ColorTransformActivity.class));
                break;
            case 6:
                startActivity(new Intent(this, PixelTransformActivity.class));
                break;
            case 7:
                startActivity(new Intent(this, ColorMatrixActivity.class));
                break;
            case 8:
                startActivity(new Intent(this, BlurActivity.class));
                break;
            case 9:
                startActivity(new Intent(this, ConvolveActivity.class));
                break;
        }
    }
}
