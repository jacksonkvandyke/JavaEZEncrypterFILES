package RawFiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class EncryptFile {
    
    protected File currentFile = null;
    protected String encryptionKey = null;

    EncryptFile(File currentFile, String encryptionKey){
        this.currentFile = currentFile;
        this.encryptionKey = encryptionKey;

        //Start encryption process
        CreateEncryptionFile();
    }

    protected void CreateEncryptionFile(){
        //Create new encrypted file
        String encryptedName = this.currentFile.getAbsolutePath() + "-EZ";
        File encryptFile = new File(encryptedName);

        //Create the file
        try{
            encryptFile.createNewFile();
            WriteDataToFile(encryptFile);
        }catch (IOException e){
            System.out.print(e);
        }

    }

    protected byte[] EncryptData(byte data[]){
        //This is a custom encryption scheme
        int keyIndex = 0;

        //Loop through entire data, and encrypt based on key
        for (int i = 0; i < data.length; i++){
            //Encryption key value
            int encryptValue = encryptionKey.charAt(keyIndex);

            //Encrypt byte
            int byteData = (data[i] + encryptValue);
            if (byteData > 255){
                byteData = byteData % 255;
            }
            byte newByte = (byte) byteData;
            data[i] = newByte;
            keyIndex += 1;
            if (keyIndex >= encryptionKey.length()){
                keyIndex = 0;
            }
        }
        return data;
    }

    protected void WriteDataToFile(File encryptFile) throws FileNotFoundException, IOException{
        //Create file input stream and output stream
        FileInputStream inputStream = new FileInputStream(currentFile);
        FileOutputStream outputStream = new FileOutputStream(encryptFile);

        //Read all data and add to new encrypted file
        byte readBuffer[] = new byte[1024];
        int currentRead = inputStream.read(readBuffer);

        //Encrypt data
        readBuffer = EncryptData(readBuffer);

        //Check if read data is smaller than buffer
        if ((currentRead > 0) && (currentRead < 1024)){
            byte newBuffer[] = new byte[currentRead];
            
            for (int i = 0; i < currentRead; i++){
                newBuffer[i] = readBuffer[i];
            }
            readBuffer = newBuffer;
        }

        //Write initial data to new file
        if (currentRead > 0){
            outputStream.write(readBuffer);
        }

        //Continue until all data has been read
        while (currentRead > 0){
            //Reset buffer
            readBuffer = new byte[1024];

            //Read
            currentRead = inputStream.read(readBuffer);

            //Encrypt data
            readBuffer = EncryptData(readBuffer);

            //Check if read data is smaller than buffer
            if ((currentRead > 0) && (currentRead < 1024)){
                byte newBuffer[] = new byte[currentRead];
            
                for (int i = 0; i < currentRead; i++){
                    newBuffer[i] = readBuffer[i];
                }
                readBuffer = newBuffer;
            }

            //Write if data to write
            if (currentRead > 0){
                outputStream.write(readBuffer);
            }
        }

        //Close streams
        inputStream.close();
        outputStream.close();
    }
}
