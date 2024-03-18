package RawFiles;

import java.awt.Container;
import java.awt.Font;
import java.awt.Label;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;

public class RenderHandler {
    //This class handles communication between the Render and Encryption/Decryption files
    Render render = null;
    File currentFile = new File("");

    RenderHandler(Render render){
        this.render = render;
    }

    public boolean ChooseFile(){
        //Allow the user to select a file
        JFileChooser chooser = new JFileChooser();

        //Open file chooser
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.showOpenDialog(render.currentFrame);

        //Set file if user selected a file
        currentFile = chooser.getSelectedFile();

        //Continue to next steps if file was chosen
        if (currentFile != null){
            //Add file name to all windows
            for (int i = 1; i < render.frames.length; i++){
                JLabel fileLabel = new JLabel();
                Font fileFont = new Font("Arial", Font.PLAIN, 14);
                fileLabel.setText("Current File: " + currentFile.getName());
                fileLabel.setFont(fileFont);
                fileLabel.setAlignmentX(Container.CENTER_ALIGNMENT);
                render.frames[i].add(fileLabel);
            }
            
            return true;
        }
        return false;
    }

    public void EncryptFile(String encryptionKey, boolean deleteChecked){
        //Start file encryption
        if (encryptionKey != null){
            new EncryptFile(currentFile, encryptionKey);

            //Successful encryption message
            JDialog successDiag = new JDialog(render.currentFrame, "EZ File Encrypter");
            successDiag.add(new Label("File successfully encrypted."));
            successDiag.setSize(200, 75);
            successDiag.setLocationRelativeTo(null);
            successDiag.setVisible(true);

            //Delete original file if checked
            if (deleteChecked){
                try{
                    currentFile.delete();
                }catch (SecurityException e){
                    System.out.print(e);
                }
            }
        }
    }

    public void DecryptFile(String decryptionKey, boolean deleteChecked){
        //Start file encryption
        if (decryptionKey != null){
            new DecryptFile(currentFile, decryptionKey);

            //Successful decryption message
            JDialog successDiag = new JDialog(render.currentFrame, "EZ File Encrypter");
            successDiag.add(new Label("File successfully decrypted."));
            successDiag.setSize(200, 75);
            successDiag.setLocationRelativeTo(null);
            successDiag.setVisible(true);

            //Delete original file if checked
            if (deleteChecked){
                try{
                    currentFile.delete();
                }catch (SecurityException e){
                    System.out.print(e);
                }
            }
        }
    }
}
