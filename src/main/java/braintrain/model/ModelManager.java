package braintrain.model;

import static braintrain.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;

import braintrain.commons.core.GuiSettings;
import braintrain.commons.core.LogsCenter;
import braintrain.model.lesson.Lesson;

/**
 * Represents the in-memory model of BrainTrain data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Lessons lessons;
    private final UserPrefs userPrefs;
    /**
     * Initializes a ModelManager with the given userPrefs.
     */
    public ModelManager(ReadOnlyUserPrefs userPrefs, Lessons lessons) {
        super();
        requireAllNonNull(userPrefs);

        logger.fine("Initializing with user prefs " + userPrefs);

        this.userPrefs = new UserPrefs(userPrefs);
        this.lessons = new Lessons();
    }

    public ModelManager() {
        this(new UserPrefs(), new Lessons());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    //=========== Lessons ==================================================================================

    @Override
    public Lesson getLesson(int index) {
        Lesson lesson = lessons.getLessons().get(index);
        return lesson;
    }

    @Override
    public List<Lesson> getLessons() {
        return lessons.getLessons();
    }

    @Override
    public void setLesson(int index, Lesson updatedLesson) {
        lessons.setLesson(index, updatedLesson);
    }

    @Override
    public void deleteLesson(int index) {
        deleteLesson(index);
    }

    //=========== Undo/Redo =================================================================================

    //    @Override
    //    public boolean canUndoAddressBook() {
    //        return versionedAddressBook.canUndo();
    //    }
    //
    //    @Override
    //    public boolean canRedoAddressBook() {
    //        return versionedAddressBook.canRedo();
    //    }
    //
    //    @Override
    //    public void undoAddressBook() {
    //        versionedAddressBook.undo();
    //    }
    //
    //    @Override
    //    public void redoAddressBook() {
    //        versionedAddressBook.redo();
    //    }
    //
    //    @Override
    //    public void commitAddressBook() {
    //        versionedAddressBook.commit();
    //    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return userPrefs.equals(other.userPrefs);
    }

}
