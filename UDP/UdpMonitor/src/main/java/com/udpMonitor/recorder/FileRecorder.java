package com.udpMonitor.recorder;

import java.io.*;

public class FileRecorder {
    private String fileFullName;
    private File recFile;
    private FileOutputStream fileStream = null;

    public FileRecorder(String fileFullName, long timeStamp) throws IOException {
        if(fileFullName == null) throw new NullPointerException("Parameter Error");

        this.fileFullName = fileFullName + "." + timeStamp;

        this.recFile = createFileWithDirectory(this.fileFullName);
        if(this.recFile == null) {
            System.out.println("Fail to create Record file.");
            return;
        }

        System.out.println("Recording... " + fileFullName);

        try {
            fileStream = new FileOutputStream(recFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static File createFileWithDirectory (String filePath) throws IOException {
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        return file;
    }

    public void writeData (byte[] data) throws IOException {
        if(data == null) return;
        if(fileStream == null) return;
        fileStream.write(data);
        fileStream.flush();
        fileStream.close();
    }

    public boolean closeFile () throws IOException {
        if(fileStream == null) return false;
        fileStream.close();
        fileStream = null;
        return true;
    }
}
