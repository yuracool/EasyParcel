package com.yuracool.easyparcel;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yura on 28.02.2015.
 */
public class ValueGenerator {
    List<String> dictionary = new ArrayList<>(200000);

    public ValueGenerator(){
        try {
            BufferedReader br = new BufferedReader(new FileReader("generator/assets/dictionary.txt"));

            String line;
            while((line = br.readLine()) != null){
                dictionary.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getInt(){
        return (int) (Math.random() * (isSign() ? Integer.MIN_VALUE : Integer.MAX_VALUE));
    }

    public byte getByte(){
        return (byte) (Math.random() * (isSign() ? Byte.MIN_VALUE : Byte.MAX_VALUE));
    }

    public long getLong(){
        return (long) (Math.random() * (isSign() ? Long.MIN_VALUE : Long.MAX_VALUE));
    }

    public short getShort(){
        return (short) (Math.random() * (isSign() ? Short.MIN_VALUE : Short.MAX_VALUE));
    }

    public float getFloat(){
        return (float) Math.random() * (isSign() ? -1 : 1);
    }

    public double getDouble(){
        return Math.random() * (isSign() ? -1 : 1);
    }

    public boolean getBoolean(){
        return isSign();
    }

    public char getChar(){
        return dictionary.get(getIndex()).charAt(0);
    }

    public String getString(){
        return dictionary.get(getIndex());
    }

    private boolean isSign(){
        return Math.random() > 0.5;
    }

    private int getIndex(){
        return (int) (Math.random() * dictionary.size() - 1);
    }
}
