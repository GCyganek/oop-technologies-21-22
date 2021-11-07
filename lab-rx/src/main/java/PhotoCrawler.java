import io.reactivex.rxjava3.core.Observable;
import model.Photo;
import util.PhotoDownloader;
import util.PhotoProcessor;
import util.PhotoSerializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PhotoCrawler {

    private static final Logger log = Logger.getLogger(PhotoCrawler.class.getName());

    private final PhotoDownloader photoDownloader;

    private final PhotoSerializer photoSerializer;

    private final PhotoProcessor photoProcessor;

    public PhotoCrawler() throws IOException {
        this.photoDownloader = new PhotoDownloader();
        this.photoSerializer = new PhotoSerializer("./photos");
        this.photoProcessor = new PhotoProcessor();
    }

    public void resetLibrary() throws IOException {
        photoSerializer.deleteLibraryContents();
    }

    public void downloadPhotoExamples() {
        try {
            photoDownloader.getPhotoExamples()
                    .compose(this::processPhotos)
                    .subscribe(photoSerializer::savePhoto);
        } catch (IOException e) {
            log.log(Level.SEVERE, "Downloading photo examples error", e);
        }
    }

    public void downloadPhotosForQuery(String query) throws IOException {
        // TODO Implement me :(
        photoDownloader.searchForPhotos(query)
                .take(10)
                .compose(this::processPhotos)
                .subscribe(photoSerializer::savePhoto, error -> System.out.println("Blad: " + error.getMessage()));

    }

    public void downloadPhotosForMultipleQueries(List<String> topics) {
        photoDownloader.searchForPhotos(topics)
                .compose(this::processPhotos)
                .blockingSubscribe(photoSerializer::savePhoto, error -> System.out.println("Blad: " + error.getMessage()));
    }

    public Observable<Photo> processPhotos(Observable<Photo> observablePhoto) {
        return observablePhoto
                .filter(photoProcessor::isPhotoValid)
                .map(photoProcessor::convertToMiniature);
    }
}
