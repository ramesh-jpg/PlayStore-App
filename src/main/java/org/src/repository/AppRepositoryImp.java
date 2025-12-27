/*package repository;

import model.App;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * This Class implementation of AppRepository
 * Use the Arraylist for storage
 * Create a Constructor for Storing the 5 default app in PlayStore
 */
/*public class AppRepositoryImp implements AppRepository {

    private final Collection<App> storage = new ArrayList<>();

    public AppRepositoryImpl() {

    }

    @Override
    public void save(final App app) {
        storage.add(app);
    }

    @Override
    public App findById(final int id) {
        return storage.stream()
                .filter(app -> app.getAppId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void update(final App app) {
        final App existingApp = findById(app.getAppId());

        if (existingApp == null) {
            return;
        }

        existingApp.setName(app.getName());
        existingApp.setVersion(app.getVersion());
        existingApp.setFeatures(app.getFeatures());
    }

    @Override
    public boolean delete(final int id) {
        return storage.removeIf(app -> app.getAppId() == id);
    }

    @Override
    public Collection <App> getAll() {
        return storage;
    }
}



 */