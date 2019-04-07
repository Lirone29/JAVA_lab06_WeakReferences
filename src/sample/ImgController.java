package sample;

import com.sun.javafx.util.WeakReferenceQueue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class ImgController {

    DirectoryChooser directoryChooser;

    int currentTopPicture = 0;
    int currentBottomPicture = 1;
    boolean changeTurn = false;


    ArrayList<String> nameList;
    ReferenceQueue<Image> imageViewReferenceQueue = new ReferenceQueue<Image>();

    WeakReference<Image> topSoftReference;
    WeakReference<Image> bottomSoftReference;

    @FXML
    private Button FileChooserButton;

    @FXML
    private ImageView bottomImageView;

    @FXML
    private Button UpButton;

    @FXML
    private Button DownButton;

    @FXML
    private Label ImagaChooserLabel;

    @FXML
    private ImageView topImageView;



    public void ImgController()
    {
        bottomImageView = new ImageView();
        bottomImageView.maxWidth(300);
        bottomImageView.maxHeight(200);
        bottomImageView.minHeight(300);
        bottomImageView.minWidth(200);
        topImageView = new ImageView();
        topImageView.maxHeight(300);
        topImageView.maxWidth(200);
    }


    @FXML
    void handleFileChooserButton(ActionEvent event) throws FileNotFoundException {
        nameList = new ArrayList<String>();
        //cacheImage = new WeakHashMap();

        File initialDirectory = new File("C:\\Users\\Eliza\\Desktop\\PWR\\Sem\\JAVA\\lab06");

        directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(initialDirectory);
        File selectedFile = directoryChooser.showDialog(null);

        if (selectedFile.isDirectory()){
            Path tempPath = selectedFile.toPath();
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(tempPath)) {
               for (Path filePath: stream) {

                   String url = filePath.getParent().toString() +"\\"+ filePath.getFileName().toString() ;
                   nameList.add(url);
                 //  cacheImage.put(counter,tmpImage);

               }


            } catch (IOException | DirectoryIteratorException x) {
            }

        } else {
            System.out.println("Error! Directory wasn't selected");
        }

        System.out.println(nameList.get(0).toString());

        topSoftReference = new WeakReference<Image>(new Image(new FileInputStream(nameList.get(0).toString())));
        bottomSoftReference  = new WeakReference<Image>( new Image(new FileInputStream(nameList.get(1).toString())));

        SetUpPictures();

    }

    void SetUpPictures(){
        topImageView.setImage((Image)topSoftReference.get());
        bottomImageView.setImage((Image)bottomSoftReference.get());
    }

    @FXML
    void handleUpButton() throws FileNotFoundException {
        if(currentTopPicture==0){
            currentTopPicture =nameList.size()-1;
        }else  currentTopPicture--;
        if (currentBottomPicture==0){
            currentBottomPicture=nameList.size()-1;
        }else currentBottomPicture--;

       // topSoftReference.enqueue();

        bottomSoftReference =null;
        bottomSoftReference = topSoftReference;
        //Image tmpImage =(new Image(new FileInputStream(nameList.get(currentTopPicture))));
        topSoftReference = new WeakReference<Image>(new Image(new FileInputStream(nameList.get(currentTopPicture))),imageViewReferenceQueue);
        SetUpPictures();

}


    @FXML
    void handleDownButton(ActionEvent event) throws FileNotFoundException {

        if(currentTopPicture==(nameList.size()-1)){
            currentTopPicture =0;
        }else  currentTopPicture++;
        if (currentBottomPicture==(nameList.size()-1)){
            currentBottomPicture=0;
        }else currentBottomPicture++;

        topSoftReference = null;
        topSoftReference = bottomSoftReference;
        bottomSoftReference = new WeakReference<Image>(new Image(new FileInputStream(nameList.get(currentBottomPicture))),imageViewReferenceQueue);
        SetUpPictures();

    }

}

