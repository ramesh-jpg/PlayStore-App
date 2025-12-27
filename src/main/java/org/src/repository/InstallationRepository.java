package org.src.repository;

import org.src.model.App;
import java.util.Collection;

public interface InstallationRepository {

    boolean installed( int userId, int appId);

    boolean unInstalled(int userId, int appId);

    boolean isInstalled(int userId, int appId);

    Collection<App> getInstalledApps(int userId);
}
