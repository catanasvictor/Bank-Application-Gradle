package controller;

import java.io.*;

public class WriteController {

    public void fileWrite(String s){
        try(java.io.FileWriter fw = new java.io.FileWriter("reports.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw)){
                out.println(s);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
