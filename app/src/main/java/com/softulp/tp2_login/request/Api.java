package com.softulp.tp2_login.request;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.softulp.tp2_login.modelo.Usuario;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class Api {
    public static void guardarUsuario(Context context, Usuario usuario){

        File archivo=new File(context.getFilesDir(),"fichero.dat");

        if(archivo.length()==0){
            try {
                FileOutputStream fos=new FileOutputStream(archivo,false);
                BufferedOutputStream bos=new BufferedOutputStream(fos);
                ObjectOutputStream oos=new ObjectOutputStream(bos);
                oos.writeObject(usuario);

                bos.flush();
                fos.close();
                usuario=null;

                Toast.makeText(context,"Dato guardado",Toast.LENGTH_LONG).show();

            } catch (FileNotFoundException e) {
                Toast.makeText(context,"Error al acceder al archivo",Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Toast.makeText(context,"Error al acceder al archivo",Toast.LENGTH_LONG).show();
            }

        }else{

            try {
                FileOutputStream fos=new FileOutputStream(archivo,true);
                BufferedOutputStream bos=new BufferedOutputStream(fos);
                MyObjectOutputStream oos=new MyObjectOutputStream(bos);
                oos.writeObject(usuario);

                bos.flush();
                fos.close();
                usuario=null;

                Toast.makeText(context,"Dato guardado",Toast.LENGTH_LONG).show();
            } catch (FileNotFoundException e) {
                Toast.makeText(context,"Error al acceder al archivo",Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Toast.makeText(context,"Error al acceder al archivo",Toast.LENGTH_LONG).show();
            }
        }
    }


    static class MyObjectOutputStream extends ObjectOutputStream {
        MyObjectOutputStream() throws IOException
        {
            super();
        }
        MyObjectOutputStream(OutputStream o) throws IOException
        {
            super(o);
        }
        public void writeStreamHeader() throws IOException
        {
            return;
        }
    }


    public static Usuario login(Context context, String mail, String pass){
        StringBuilder sb=new StringBuilder();
        File archivo=new File(context.getFilesDir(),"fichero.dat");
        //Nodo
        Usuario usuario = null;
        try {
            FileInputStream fis=new FileInputStream(archivo);
            BufferedInputStream bis=new BufferedInputStream(fis);
            ObjectInputStream ois=new ObjectInputStream(bis);

            usuario = (Usuario) ois.readObject();

            String email = usuario.getMail();
            String password = usuario.getPassword();

            if(!mail.equals(email) && !password.equals(pass)){
                usuario = null;
            }
        } catch (FileNotFoundException e) {
            Toast.makeText(context,"Error de File",Toast.LENGTH_LONG).show();
            Log.d("salida ",e.getMessage());
        } catch (IOException e) {
            Toast.makeText(context,"Error de E/s",Toast.LENGTH_LONG).show();
            Log.d("salida ",e.toString());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            Toast.makeText(context,"Error al recuperar datos",Toast.LENGTH_LONG).show();
        }
        return usuario;
    }
}


