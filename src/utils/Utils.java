package utils;

import classi.Giocatore;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by alessandro on 04/03/15.
 */
public class Utils {

    //cast char --> string
    public String passwordString(char[] a) {
        return new String(a);
    }

    public void csvQuotazioni(String pathFile, String aCapo, String csvSpliBy){
        BufferedReader br = null;
        String csvFile = pathFile;
        String line = aCapo;
        String csvSplitBy = csvSpliBy;

        Giocatore[] giocatores = null;
        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] country = line.split(csvSplitBy);

                System.out.println(country[0]+"-"+country[1]+"-"+country[2]);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("Done");


    }
}
