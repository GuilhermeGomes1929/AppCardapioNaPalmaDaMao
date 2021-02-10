package com.example.aplicativonutricao;

import android.os.Environment;
import android.widget.ArrayAdapter;

import java.io.File;
import java.util.ArrayList;

public class Dir {



    public ArrayList<String> listDirectory(){


        try{


            File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/WhatsApp/Media/WhatsApp Documents");
            String arqs[] = dir.list();
            ArrayList<String> list = new ArrayList<>();


            if(arqs == null){


                File dirSent = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/WhatsApp/Media/WhatsApp Documents/sent");
                String arqsSent[] = dirSent.list();
                ArrayList<String> listSent = new ArrayList<>();


                if (arqsSent == null){

                    return listSent;

                }else{

                    for (int i=0; i < arqsSent.length; i++){

                        if (arqsSent[i].endsWith(".db")){

                            listSent.add(arqsSent[i]);
                        }
                    }
                    if (listSent.contains("foodlist.db")){
                        listSent.remove("foodlist.db");
                    }
                    return listSent;
                }


            }else{


                for(int i =0; i < arqs.length; i++){

                    if (arqs[i].endsWith(".db")){

                        list.add(arqs[i]);

                    }

                }

                if (list.contains("foodlist.db")){
                    list.remove("foodlist.db");
                }

                return list;

            }

        }catch(Exception e){

            ArrayList<String> list = new ArrayList<>();
            return list;

        }
    }
}
