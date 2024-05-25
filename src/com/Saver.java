package com;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import com.data.Path;

public class Saver {
    public static void save(SaveData s)
    {
        try(ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(Path.save))){
            stream.writeObject(s);
        }catch(Exception e){
            System.out.println("save error");
        }
    }
    public static SaveData load()
    {
        try(ObjectInputStream stream = new ObjectInputStream(new FileInputStream(Path.save))) {
            return (SaveData)stream.readObject();
        } catch (Exception e) {
            System.out.println("load error");
            return null;
        }
    }
}
