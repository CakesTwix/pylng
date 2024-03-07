package com.example.myapplication3;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

// Code for Android Java Project
public class LngEditor {
    private int header;
    private String[] langStrings;
    // private int b;
    private int stringsCount;

    public void readFile(InputStream inputStream) throws IOException {
        int read;

        DataInputStream langFileDataStream = new DataInputStream(inputStream);
        header = read(langFileDataStream);
        read = langFileDataStream.read();
        //   b = read;

        if (read > 2) {
            langFileDataStream.close();
            throw new IOException("wrong lng file");
        }
        int a2 = read(langFileDataStream);
        stringsCount = a2;
        int[] iArr = new int[stringsCount + 1];
        langStrings = new String[stringsCount];
        for (int i = 0; i < iArr.length; i++) {
            iArr[i] = read(langFileDataStream);
        }
        for (int index = 0; index < iArr.length - 1; index++) {
            int stringSize = iArr[index + 1] - iArr[index];
            byte[] stringByte = new byte[stringSize];
            langFileDataStream.read(stringByte, 0, stringByte.length);
            langStrings[index] = new String(stringByte, "UTF-8");
        }
        langFileDataStream.close();
//        for (int count = 0; count < langStrings.length; count++) {
//            System.out.println(langStrings[count]);
//        }
    }

    private void write(int i, DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.write(i & 255);
        dataOutputStream.write((i >> 8) & 255);
    }

    private int read(DataInputStream dataInputStream) throws IOException {
        return (dataInputStream.read() & 255) | ((dataInputStream.read() & 255) << 8);
    }

    public void writeFile(OutputStream outputSrteam, String[] langStrings) throws IOException {

        DataOutputStream f2a = new DataOutputStream(outputSrteam);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(header);
        int length = langStrings.length;
        int[] iArr = new int[length + 1];
        iArr[0] = ((length + 1) << 1) + 3;
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            byte[] bytes = langStrings[i2].getBytes("UTF-8");
            i += bytes.length;
            iArr[i2 + 1] = iArr[i2] + bytes.length;
            byteArrayOutputStream.write(bytes);
        }
        byteArrayOutputStream.close();
        int i3 = iArr[0] + 2 + i;
        header = i3;
        write(i3, f2a);
        f2a.write(2);
        write(length, f2a);
        for (int i4 : iArr) {
            write(i4, f2a);
        }
        f2a.write(byteArrayOutputStream.toByteArray(), 0, byteArrayOutputStream.size());
        f2a.close();
        outputSrteam.close();
    }

    /*
    public final void commandAction(Command command, Displayable displayable) {
        if (command == Main.b) {
            Main.a.setCurrent(b.a);
        }
        if (command == Main.a) {
            f4a.set(f4a.getSelectedIndex(), d.a.getString(), Main.b);
            langStrings[f4a.getSelectedIndex()] = d.a.getString();
            Main.a.setCurrent(f4a);
        }
        if (command == Main.d) {
            Main.a.setCurrent(f4a);
        }
        if (command == List.SELECT_COMMAND) {
            d.a(f4a, this);
        }
        if (command == Main.c) {
            try {
                FileConnection open = Connector.open(new StringBuffer().append("file:///").append(b.a).append(b.b).toString());
                langFile = open;
                if (open.exists()) {
                    langFile.delete();
                }
                langFile.create();
                f2a = langFile.openDataOutputStream();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(a);
                int length = langStrings.length;
                int[] iArr = new int[length + 1];
                iArr[0] = ((length + 1) << 1) + 3;
                int i = 0;
                for (int i2 = 0; i2 < length; i2++) {
                    byte[] bytes = langStrings[i2].getBytes("UTF-8");
                    i += bytes.length;
                    iArr[i2 + 1] = iArr[i2] + bytes.length;
                    byteArrayOutputStream.write(bytes);
                }
                byteArrayOutputStream.close();
                int i3 = iArr[0] + 2 + i;
                header = i3;
                a(i3, f2a);
                f2a.write(2);
                a(length, f2a);
                for (int i4 : iArr) {
                    a(i4, f2a);
                }
                f2a.write(byteArrayOutputStream.toByteArray(), 0, byteArrayOutputStream.size());
                f2a.close();
                langFile.close();
                Main.Complite();
            } catch (Exception e) {
                try {
                    langFile.close();
                    Main.Error(e.toString());
                } catch (IOException e2) {
                }
            }
        }
        if (command == Main.j) {
            d.a.setString("");
        }
    }
    */

    public String[] getLangStrings() {
        return this.langStrings;
    }
}
