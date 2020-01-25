package com.diana.bilet_monitor.Clase;

import com.diana.bilet_monitor.InregistrareMonitorActivity;
import com.diana.bilet_monitor.Network.HttpResponse;

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
    private static Monitor parsareObiectMonitor(JSONObject object) throws JSONException {
        if (object == null) {
            return null;
        } else {
            Integer nr_inventar = object.getInt("nr_inventar");
            String producator = object.getString("producator");
            Integer diagonala = object.getInt("diagonala");
            String proprietar = object.getString("proprietar");
            String data_intrarii = object.getString("data_intrarii");

            Date data_intr = null;
            try {
                data_intr = new SimpleDateFormat(InregistrareMonitorActivity.DATE_FORMAT, Locale.US).parse(data_intrarii);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return new Monitor(nr_inventar, producator, diagonala, proprietar, data_intr);
        }
    }

    private static List<Monitor> parsareListaMonitoare(JSONArray array) throws JSONException {
        if (array == null) {
            return null;
        } else {
            List<Monitor> monitors = new ArrayList<>();
            for (int i = 0; i < array.length(); ++i) {
                Monitor monitor = parsareObiectMonitor(array.getJSONObject(i));

                if (monitor != null) {
                    monitors.add(monitor);
                }
            }

            return monitors;
        }
    }

    public static HttpResponse parsareJson(String json) {
        if (json == null) {
            return null;
        } else {
            try {
                JSONArray array = new JSONArray(json);
                List<Monitor> monitors = parsareListaMonitoare(array);
                return new HttpResponse(monitors);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
