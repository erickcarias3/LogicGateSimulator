package com.logisim.logiclearning.SaveManager;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.logisim.logiclearning.SandboxViewTools.GridManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class SerialBitmap implements Serializable {
    public Bitmap image;

    public SerialBitmap(Resources res, int image, GridManager grid){
        Bitmap temp = BitmapFactory.decodeResource(res, image);
        this.image = Bitmap.createScaledBitmap(temp,grid.getGridWidth(), grid.getGridHeight(), true);
    }

    private void writeObject(java.io.ObjectOutputStream out) throws IOException{
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG,0,byteStream);
        byte bitmapImageArray[] = byteStream.toByteArray();
        out.write(bitmapImageArray, 0, bitmapImageArray.length);
    }

    private void readObject(java.io.ObjectInputStream in)throws IOException{
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        int b = in.read();
        while(b != -1){
            byteStream.write(b);
            b=in.read();
        }
        byte bitmapImageArray[] = byteStream.toByteArray();
        image = BitmapFactory.decodeByteArray(bitmapImageArray, 0, bitmapImageArray.length);
    }
}
