package pl.edu.agh.school;

import java.util.Collections;
import java.util.List;

import pl.edu.agh.logger.Logger;
import pl.edu.agh.school.persistence.PersistenceManager;
import pl.edu.agh.school.persistence.SerializablePersistenceManager;

import javax.inject.Inject;

public class SchoolDAO {

    public Logger logger;

    private final List<Teacher> teachers;

    private final List<SchoolClass> classes;

    private final PersistenceManager manager;

    @Inject
    public SchoolDAO(PersistenceManager persistenceManager, Logger logger) {
        this.logger = logger;
        manager = persistenceManager;
        teachers = manager.loadTeachers();
        classes = manager.loadClasses();
    }

    public void addTeacher(Teacher teacher) {
        if (!teachers.contains(teacher)) {
            teachers.add(teacher);
            manager.saveTeachers(teachers);
            logger.log("Added " + teacher.toString());
        }
    }

    public void addClass(SchoolClass newClass) {
        if (!classes.contains(newClass)) {
            classes.add(newClass);
            manager.saveClasses(classes);
            logger.log("Added " + newClass.toString());
        }
    }

    public List<SchoolClass> getClasses() {
        return Collections.unmodifiableList(classes);
    }

    public List<Teacher> getTeachers() {
        return Collections.unmodifiableList(teachers);
    }
}
