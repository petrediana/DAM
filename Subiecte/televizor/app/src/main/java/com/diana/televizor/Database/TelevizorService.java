package com.diana.televizor.Database;

import android.content.Context;
import android.os.AsyncTask;

import com.diana.televizor.Clase.Televizor;
import com.diana.televizor.Database.Dao.TelevizorDao;

import java.util.ArrayList;
import java.util.List;

public class TelevizorService {
    private static TelevizorDao televizorDao;

    public static class GetAll extends AsyncTask<Void, Void, List<Televizor>> {
        public GetAll(Context context) {
            televizorDao = DatabaseModel.getInstance(context).getTelevizorDao();
        }

        @Override
        protected List<Televizor> doInBackground(Void... voids) {
            return televizorDao.getAll();
        }
    }

    public static class InsertTelevizor extends AsyncTask<Televizor, Void, Televizor> {
        public InsertTelevizor(Context context) {
            televizorDao = DatabaseModel.getInstance(context).getTelevizorDao();
        }

        @Override
        protected Televizor doInBackground(Televizor... televizors) {
            if (televizors == null || televizors.length != 1) {
                return null;
            } else {
                Televizor televizor = televizors[0];
                long id = televizorDao.insertTelevizor(televizor);

                if (id != -1) {
                    televizor.setId(id);
                    return televizor;
                }

                return null;
            }
        }
    }

    public static class DeleteTelevizor extends AsyncTask<Televizor, Void, Integer> {
        public DeleteTelevizor(Context context) {
            televizorDao = DatabaseModel.getInstance(context).getTelevizorDao();
        }

        @Override
        protected Integer doInBackground(Televizor... televizors) {
            if (televizors == null && televizors.length != 1) {
                return null;
            } else {
                return televizorDao.deleteTelevizor(televizors[0]);
            }
        }
    }
}
