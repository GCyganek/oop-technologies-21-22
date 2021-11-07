package pl.edu.agh.school.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import pl.edu.agh.school.persistence.PersistenceManager;
import pl.edu.agh.school.persistence.SerializablePersistenceManager;

import javax.inject.Named;

public class SchoolModule extends AbstractModule {
    @Provides
    static PersistenceManager providesPersistenceManager(SerializablePersistenceManager persistenceManager) {
        return persistenceManager;
    }

    @Provides
    @Named("classesFileName")
    static String providesClassesFileName() {
        return "guice-classes.dat";
    }

    @Provides
    @Named("teachersFileName")
    static String providesTeachersFileName() {
        return "guice-teachers.dat";
    }

    @Provides
    @Named("fileMessageSerializerFilename")
    static String providesFileMessageSerializerFilename() {
        return "persistence.log";
    }
}
