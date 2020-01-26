package com.diana.televizor;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.diana.televizor.Clase.JsonParse;
import com.diana.televizor.Clase.Televizor;
import com.diana.televizor.Database.TelevizorService;
import com.diana.televizor.Network.HttpRequest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int ADAUGA_TV_REQUEST_KEY = 200;
    private static final String myUrl = "https://raw.githubusercontent.com/petrediana/DAM/master/Subiecte/monitor.txt";

    public static final String TRIMITE_LISTA_TV_KEY = "trimiteListaTvs";

    private List<Televizor> televizorList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        veziDateDinDb();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_despre:
                Toast.makeText(this, "Nume_autor_apl", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.menu_lista_televizoare:
                //Toast.makeText(this, "lista", Toast.LENGTH_SHORT).show();
                deschideListaTelevizoare();
                return true;

            case R.id.menu_inregistrare_televizor:
               // Toast.makeText(this, "inregistrare televizor", Toast.LENGTH_SHORT).show();
                deschideInregistrareTv();
                return true;

            case R.id.menu_json:
               // Toast.makeText(this, "json", Toast.LENGTH_SHORT).show();
                citesteDinRetea();
                return true;

            case R.id.menu_raport:
                //Toast.makeText(this, "raport", Toast.LENGTH_SHORT).show();
                deschideRapor();
                return true;

            default: break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deschideInregistrareTv() {
        Intent intent = new Intent(getApplicationContext(), InregistrareTelevizorActivity.class);
        startActivityForResult(intent, ADAUGA_TV_REQUEST_KEY);
    }

    private void deschideListaTelevizoare() {
        Intent intent = new Intent(getApplicationContext(), ListaTelevizoareActivity.class);
        intent.putExtra(TRIMITE_LISTA_TV_KEY, (Serializable) televizorList);
        startActivity(intent);
    }

    private void deschideRapor() {
        Intent intent = new Intent(getApplicationContext(), RaportActivity.class);
        intent.putExtra(TRIMITE_LISTA_TV_KEY, (Serializable) televizorList);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADAUGA_TV_REQUEST_KEY && resultCode == RESULT_OK && data != null) {
            Televizor televizor = (Televizor) data.getSerializableExtra(InregistrareTelevizorActivity.ADAUGA_MODIFICA_TELEVIZOR_KEY);
            //televizorList.add(televizor);
            insertTelevizorDb(televizor);
            Log.i(TAG, televizor.toString());
        }
    }

    private void citesteDinRetea() {
        new HttpRequest() {
            @Override
            protected void onPostExecute(String s) {
                Log.i(TAG, s);

                List<Televizor> tvs = JsonParse.parsareJson(s);
                if(tvs != null) {
                    televizorList.addAll(tvs);
                    Log.i(TAG, televizorList.toString());
                }
            }
        }.execute(myUrl);
    }

    private void veziDateDinDb() {
        new TelevizorService.GetAll(getApplicationContext()) {
            @Override
            protected void onPostExecute(List<Televizor> televizors) {
                Log.i(TAG, televizors.toString());
            }
        }.execute();
    }

    private void insertTelevizorDb(Televizor televizorDeInserat) {
        new TelevizorService.InsertTelevizor(getApplicationContext()) {
            @Override
            protected void onPostExecute(Televizor televizor) {
                if (televizor != null) {
                    televizorList.add(televizor);
                }
            }
        }.execute(televizorDeInserat);
    }

    private void deleteTelevizorDb(Televizor televizorDeSters) {
        new TelevizorService.DeleteTelevizor(getApplicationContext()) {
            @Override
            protected void onPostExecute(Integer integer) {
                Log.i(TAG, "s-a sters: " + integer);
            }
        }.execute(televizorDeSters);
    }
}
