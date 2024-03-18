import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class DecryptFile {
    
    protected File currentFile = null;
    protected String decryptionKey = null;

    DecryptFile(File currentFile, String decryptionKey){
        this.currentFile = currentFile;
        this.decryptionKey = decryptionKey;

        //Start encryption process
        CreateDecryptedFile();
    }

    protected void CreateDecryptedFile(){
        //Create new encrypted file
        String fileName = this.currentFile.getAbsolutePath();
        String decryptName = fileName.replace("-EZ", "");

        File decryptFile = new File(decryptName);

        //Create the file
        try{
            decryptFile.createNewFile();
            WriteDataToFile(decryptFile);
        }catch (IOException e){
            System.out.print(e);
        }

    }

    protected byte[] DecryptData(byte data[]){
        //This is a custom decryption scheme
        int keyIndex = 0;

        //Loop through entire data, and encrypt based on key
        for (int i = 0; i < data.length; i++){
            //Decryption key value
            int decryptValue = decryptionKey.charAt(keyIndex);

            //Decrypt byte
            int byteData = (data[i] - decryptValue);
            if (byteData < 0){
                byteData = 255 + byteData + 1;
            }
            byte newByte = (byte) byteData;
            data[i] = newByte;
            keyIndex += 1;
            if (keyIndex >= decryptionKey.length()){
                keyIndex = 0;
            }
        }
        return data;
    }

    protected void WriteDataToFile(File encryptFile) throws FileNotFoundException, IOException{
        //Create file input stream and output stream
        FileInputStream inputStream = new FileInputStream(currentFile);
        FileOutputStream outputStream = new FileOutputStream(encryptFile);

        //Read all data and add to new decrypted file
        byte readBuffer[] = new byte[1024];
        int currentRead = inputStream.read(readBuffer);

        //Decrypt data
        readBuffer = DecryptData(readBuffer);

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
            readBuffer = DecryptData(readBuffer);

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

