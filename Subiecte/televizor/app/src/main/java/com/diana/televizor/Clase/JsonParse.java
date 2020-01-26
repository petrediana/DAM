package com.diana.televizor.Clase;

import com.diana.televizor.InregistrareTelevizorActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class JsonParse {
    private static Televizor parsareObiectTelevizor(JSONObject object) throws JSONException {
        if (object == null) {
            return null;
        } else {
            int nr_inventar = object.getInt("nr_inventar");
            String producator = object.getString("producator");
            int diagonala = object.getInt("diagonala");
            String proprietar = object.getString("proprietar");
            String data_intrarii = object.getString("data_intrarii");

            Date dataI = null;
            try {
                dataI = new SimpleDateFormat(InregistrareTelevizorActivity.DATA_FORMAT, Locale.US).parse(data_intrarii);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return new Televizor(nr_inventar, producator, diagonala, proprietar, dataI);
        }
    }

    private static List<Televizor> parsareListaTelevizoare (JSONArray array) throws JSONException {
        if (array == null) {
            return null;
        } else {
            List<Televizor> list = new ArrayList<>();
            for (int i = 0; i < array.length(); ++i) {
                Televizor televizor = parsareObiectTelevizor(array.getJSONObject(i));

                if (televizor != null)
                    list.add(televizor);
            }

            return list;
        }
    }

    public static List<Televizor> parsareJson (String json) {
        if (json == null) {
            return null;
        } else {
            try {
                JSONArray array = new JSONArray(json);
                return parsareListaTelevizoare(array);
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
