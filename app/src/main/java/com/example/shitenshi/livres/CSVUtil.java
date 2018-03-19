package com.example.shitenshi.livres;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by shitenshi on 2018/03/19.
 */

public class CSVUtil {

    void sqlite_to_csv(Class<Serializable> Serializable ,String[] keys, List<Serializable> dblist){
        StringBuilder stringBuilder = new StringBuilder();
        String[] column = new String[dblist.size()];
        for (int i = 0; i < dblist.size() - 1; i++) {
            if (i >= 1) {

            }
                ArrayList<String> key_type = new ArrayList<>();
             key_type.addAll(Arrays.asList(keys));
                column[i] = dblist.get(i)+ "," + dblist.get(i) + "," + dblist.get(i) + "," + dblist.get(i) + "\n";
                stringBuilder.append(column[i]);

        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    void csv_to_sqlite(Context context, String path) throws IOException {
        OutgoDbHelper outgoDbHelper = new OutgoDbHelper(context);
        File f = new File(path);
        String[] filenames = f.list();
        boolean isFile = (filenames == null);
        File[] fileslist = Arrays.stream(filenames).map(name -> new File(f,name)).toArray(File[]::new);
        for (File csv:fileslist) {
            String csv_main = read_csv(csv.getPath());
            String[] csv_pre = csv_main.split("\n");
            String[] csv_row = new String[0];
            for (int i = 1; i <= csv_pre.length ; i++) {
               if (i >= 1){
                    csv_row[i-1] = csv_pre[i];
                    String[] csv_for_insert = csv_row[i-1].split(",");
                    outgoDbHelper.insertValues(new DbContainer(csv_for_insert[0],csv_for_insert[1],Integer.valueOf(csv_for_insert[2]),Integer.valueOf(csv_for_insert[3]),Long.valueOf(csv_for_insert[4])));
               }
            }





        }




    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private static String read_csv(final String path) throws IOException {
        return Files.lines(Paths.get(path), Charset.forName("UTF-8")).collect(Collectors.joining(System.getProperty("line.separator")));
    }

}
