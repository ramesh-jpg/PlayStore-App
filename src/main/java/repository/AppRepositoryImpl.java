package repository;

import model.App;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * This Class implementation of AppRepository
 * Use the Arraylist for storage
 * Create a Constructor for Storing the 5 default app in PlayStore
 */

public class AppRepositoryImpl implements AppRepository {

    private final Collection<App> storage = new ArrayList<>();

    public AppRepositoryImpl() {
        storage.add(new App(101, "WhatsApp", "Whatsapp","Secure Call and Message",3.5, Arrays.asList("Call and Message" )));
        storage.add(new App(102, "Facebook","Meta","Connect with friends ",1.0, Arrays.asList("Video and Message" )));
        storage.add(new App(103, "Instagram", "Meta","Capture and share moments",2.1, Arrays.asList("Reels and Message" )));
        storage.add(new App(104, "Google pay","Google", "Simple way to pay money in others",5.0, Arrays.asList("Money Transfer" )));
        storage.add(new App(105, "Spotify","Spotify", "Music for everyone",4.8, Arrays.asList("Playing Music" )));
    }

    @Override
    public void save(final App app) {
        storage.add(app);
    }

    @Override
    public App findById(final int id) {
        return storage.stream()
                .filter(app -> app.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void update(final App app) {
        final App existingApp = findById(app.getId());

        if (existingApp == null) {
            return;
        }

        existingApp.setName(app.getName());
        existingApp.setVersion(app.getVersion());
        existingApp.setFeatures(app.getFeatures());
    }

    @Override
    public boolean delete(final int id) {
        return storage.removeIf(app -> app.getId() == id);
    }

    @Override
    public Collection <App> getAll() {
        return storage;
    }
}
