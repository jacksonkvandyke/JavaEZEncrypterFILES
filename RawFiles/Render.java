package RawFiles;

import java.awt.Color;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

//Controls the rendering of all UI
public class Render {

    JFrame frames[] = new JFrame[4];
    JFrame currentFrame = null;

    RenderHandler handler = new RenderHandler(this);

    Render(){
        CreateUI();
        Navigate(frames[0]);
    }

    //Handles navigation between pages
    void Navigate(JFrame frame){
        //Disable other frames
        for (int i = 0; i < frames.length; i++){
            frames[i].setVisible(false);
        }
        currentFrame = frame;
        frame.setVisible(true);
    }

    void CreateUI(){
        //Create all windows and navigate to HomePage
        frames[0] = new HomePage(this).GetFrame();
        frames[1] = new ChooseMethodPage(this).GetFrame();
        frames[2] = new EncryptPage(this).GetFrame();
        frames[3] = new DecryptPage(this).GetFrame();
    }

    public static void main(String[] args){
        new Render();
    }

}

class HomePage {

    Render parent = null;
    JFrame frame = null;

    HomePage(Render parent){
        this.parent = parent;

        //Create frame
        this.frame = new JFrame();
        this.frame.setSize(350, 245);
        this.frame.setLocationRelativeTo(null);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.getContentPane().setLayout(new BoxLayout(this.frame.getContentPane(), BoxLayout.Y_AXIS));

        CreateElements();
        this.frame.validate();
    }

    void CreateElements(){
        //Title
        JLabel windowTitle = new JLabel();
        Font titleFont = new Font("Arial", Font.BOLD, 32);
        windowTitle.setText("EZ File Encrypter");
        windowTitle.setFont(titleFont);
        windowTitle.setAlignmentX(Container.CENTER_ALIGNMENT);
        this.frame.add(windowTitle);

        //Description
        JLabel descriptionLabel = new JLabel();
        Font descriptionFont = new Font("Arial", Font.PLAIN, 16);
        descriptionLabel.setText("EaZily encrypt any file.");
        descriptionLabel.setFont(descriptionFont);
        descriptionLabel.setAlignmentX(Container.CENTER_ALIGNMENT);
        this.frame.add(descriptionLabel);

        //Spacer
        this.frame.add(new JSeparator());

        //Warning label
        JLabel warningLabel = new JLabel();
        Font warningFont = new Font("Arial", Font.BOLD, 14);
        warningLabel.setText("Note: Read the info page before proceeding!");
        warningLabel.setFont(warningFont);
        warningLabel.setForeground(Color.RED);
        warningLabel.setAlignmentX(Container.CENTER_ALIGNMENT);
        this.frame.add(warningLabel);

        ////Buttons
        Font buttonFont = new Font("Arial", Font.PLAIN, 16);

        //Select file button
        JButton selectButton = new JButton();
        selectButton.setText("Select File");
        selectButton.setFont(buttonFont);
        selectButton.setAlignmentX(Container.CENTER_ALIGNMENT);
        selectButton.setMargin(new Insets(15, 49, 15, 49));
        selectButton.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                if (parent.handler.ChooseFile()){
                    parent.Navigate(parent.frames[1]);
                }
            } 
        });
        this.frame.add(selectButton);

        //Read about button
        JButton infoButton = new JButton();
        infoButton.setText("Info");
        infoButton.setFont(buttonFont);
        infoButton.setAlignmentX(Container.CENTER_ALIGNMENT);
        infoButton.setMargin(new Insets(15, 74, 15, 74));
        infoButton.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e){
                String fullPath = System.getProperty("user.dir") + "/RawFiles/InfoPage.html";
                System.out.print(fullPath);
                File infoPage = new File(fullPath);
                try {
                    Desktop.getDesktop().open(infoPage);
                }catch (IOException io){
                    System.out.print(io);
                }
            } 
        });
        this.frame.add(infoButton);
    }

    JFrame GetFrame(){
        return this.frame;
    }

}

class ChooseMethodPage {

    Render parent = null;
    JFrame frame = null;

    ChooseMethodPage(Render parent){
        this.parent = parent;

        //Create frame
        this.frame = new JFrame();
        this.frame.setSize(350, 225);
        this.frame.setLocationRelativeTo(null);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.getContentPane().setLayout(new BoxLayout(this.frame.getContentPane(), BoxLayout.Y_AXIS));

        CreateElements();
        this.frame.validate();
    }

    void CreateElements(){
        //Title
        JLabel windowTitle = new JLabel();
        Font titleFont = new Font("Arial", Font.BOLD, 32);
        windowTitle.setText("EZ File Encrypter");
        windowTitle.setFont(titleFont);
        windowTitle.setAlignmentX(Container.CENTER_ALIGNMENT);
        this.frame.add(windowTitle);

        //Description
        JLabel descriptionLabel = new JLabel();
        Font descriptionFont = new Font("Arial", Font.PLAIN, 16);
        descriptionLabel.setText("EaZily encrypt any file.");
        descriptionLabel.setFont(descriptionFont);
        descriptionLabel.setAlignmentX(Container.CENTER_ALIGNMENT);
        this.frame.add(descriptionLabel);

        //Spacer
        this.frame.add(new JSeparator());

        ////Buttons
        Font buttonFont = new Font("Arial", Font.PLAIN, 16);

        //Encrypt button
        JButton encryptButton = new JButton();
        encryptButton.setText("Encrypt");
        encryptButton.setFont(buttonFont);
        encryptButton.setAlignmentX(Container.CENTER_ALIGNMENT);
        encryptButton.setMargin(new Insets(15, 60, 15, 60));
        encryptButton.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                parent.Navigate(parent.frames[2]);
            } 
        });
        this.frame.add(encryptButton);

        //Decrypt button
        JButton decryptButton = new JButton();
        decryptButton.setText("Decrypt");
        decryptButton.setFont(buttonFont);
        decryptButton.setAlignmentX(Container.CENTER_ALIGNMENT);
        decryptButton.setMargin(new Insets(15, 60, 15, 60));
        decryptButton.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                parent.Navigate(parent.frames[3]);
            } 
        });
        this.frame.add(decryptButton);
    }

    JFrame GetFrame(){
        return this.frame;
    }

}

class EncryptPage {

    Render parent = null;
    JFrame frame = null;

    EncryptPage(Render parent){
        //Create frame
        this.parent = parent;
        this.frame = new JFrame();
        this.frame.setSize(350, 245);
        this.frame.setLocationRelativeTo(null);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.getContentPane().setLayout(new BoxLayout(this.frame.getContentPane(), BoxLayout.Y_AXIS));

        CreateElements();
        this.frame.validate();
    }

    void CreateElements(){
        //Title
        JLabel windowTitle = new JLabel();
        Font titleFont = new Font("Arial", Font.BOLD, 32);
        windowTitle.setText("EZ File Encrypter");
        windowTitle.setFont(titleFont);
        windowTitle.setAlignmentX(Container.CENTER_ALIGNMENT);
        this.frame.add(windowTitle);

        //Spacer
        this.frame.add(new JSeparator());

        //Password Entry Title
        JLabel passwordTitle = new JLabel();
        Font passTitleFont = new Font("Arial", Font.BOLD, 26);
        passwordTitle.setText("File key:");
        passwordTitle.setFont(passTitleFont);
        passwordTitle.setAlignmentX(Container.CENTER_ALIGNMENT);
        this.frame.add(passwordTitle);

        //Password Entry
        JTextField passwordEntry = new JTextField();
        Font entryFont = new Font("Arial", Font.BOLD, 22);
        passwordEntry.setFont(entryFont);
        passwordEntry.setAlignmentX(Container.CENTER_ALIGNMENT);
        this.frame.add(passwordEntry);

        //Delete previous file checkbox
        JCheckBox deleteCheck = new JCheckBox("Delete original file on encrypt?");
        deleteCheck.setAlignmentX(Container.CENTER_ALIGNMENT);
        this.frame.add(deleteCheck);

        //Encrypt button
        JButton encryptButton = new JButton();
        encryptButton.setText("Encrypt");
        encryptButton.setFont(entryFont);
        encryptButton.setAlignmentX(Container.CENTER_ALIGNMENT);
        encryptButton.setMargin(new Insets(15, 30, 15, 30));
        encryptButton.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                parent.handler.EncryptFile(passwordEntry.getText(), deleteCheck.isSelected());
            } 
        });
        this.frame.add(encryptButton);
    }

    JFrame GetFrame(){
        return this.frame;
    }

}

class DecryptPage {

    Render parent = null;
    JFrame frame = null;

    DecryptPage(Render parent){
        //Create frame
        this.parent = parent;
        this.frame = new JFrame();
        this.frame.setSize(350, 245);
        this.frame.setLocationRelativeTo(null);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.getContentPane().setLayout(new BoxLayout(this.frame.getContentPane(), BoxLayout.Y_AXIS));

        CreateElements();
        this.frame.validate();
    }

    void CreateElements(){
        //Title
        JLabel windowTitle = new JLabel();
        Font titleFont = new Font("Arial", Font.BOLD, 32);
        windowTitle.setText("EZ File Encrypter");
        windowTitle.setFont(titleFont);
        windowTitle.setAlignmentX(Container.CENTER_ALIGNMENT);
        this.frame.add(windowTitle);

        //Spacer
        this.frame.add(new JSeparator());

        //Password Entry Title
        JLabel passwordTitle = new JLabel();
        Font passTitleFont = new Font("Arial", Font.BOLD, 26);
        passwordTitle.setText("File key:");
        passwordTitle.setFont(passTitleFont);
        passwordTitle.setAlignmentX(Container.CENTER_ALIGNMENT);
        this.frame.add(passwordTitle);

        //Password Entry
        JTextField passwordEntry = new JTextField();
        Font entryFont = new Font("Arial", Font.BOLD, 22);
        passwordEntry.setFont(entryFont);
        passwordEntry.setAlignmentX(Container.CENTER_ALIGNMENT);
        this.frame.add(passwordEntry);

        //Delete previous file checkbox
        JCheckBox deleteCheck = new JCheckBox("Delete encrypted file on decrypt?");
        deleteCheck.setAlignmentX(Container.CENTER_ALIGNMENT);
        this.frame.add(deleteCheck);

        //Decrypt button
        JButton decryptButton = new JButton();
        decryptButton.setText("Decrypt");
        decryptButton.setFont(entryFont);
        decryptButton.setAlignmentX(Container.CENTER_ALIGNMENT);
        decryptButton.setMargin(new Insets(15, 30, 15, 30));
        decryptButton.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                parent.handler.DecryptFile(passwordEntry.getText(), deleteCheck.isSelected());
            } 
        });
        this.frame.add(decryptButton);
    }

    JFrame GetFrame(){
        return this.frame;
    }

}