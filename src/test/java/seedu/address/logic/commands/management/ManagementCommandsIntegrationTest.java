package seedu.address.logic.commands.management;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static seedu.address.commons.core.Messages.MESSAGE_NO_OPENED_LESSON;
import static seedu.address.logic.commands.management.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.management.ListLessonsCommand.MESSAGE_NO_LESSONS;

import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.card.Card;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.modelmanager.ManagementModelManager;
import seedu.address.testutil.LessonBuilder;

/**
 * Integration tests for the {@link AddLessonCommand}, {@link DeleteLessonCommand}, {@link ListLessonsCommand},
 * {@link OpenLessonCommand}, {@link ListCardsCommand} and {@link CloseLessonCommand}
 * which are executed using an actual {@link ManagementModelManager}.
 */
public class ManagementCommandsIntegrationTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private CommandHistory commandHistory = new CommandHistory();
    private ManagementModelManager model = new ManagementModelManager();
    private Lesson validLesson = new LessonBuilder().build();
    private List<Card> validCards = validLesson.getCards();

    /**
     * Tests {@link AddLessonCommand}, {@link DeleteLessonCommand} and {@link ListLessonsCommand} with
     * {@link ManagementModelManager}.
     *
     * <br><br>You should be able to run {@link ListLessonsCommand} at any point in
     * time and the output generated by it should be updated when lessons are added and deleted by
     * {@link AddLessonCommand} and {@link DeleteLessonCommand}.
     */
    @Test
    public void execute_listListAddDeleteLesson_allSuccessful() throws Exception {
        // Step 1: listLessons
        // list lessons when there are no lessons -> command successful with feedback that there are no lessons
        assertCommandSuccess(new ListLessonsCommand(), model,
                commandHistory, MESSAGE_NO_LESSONS, model);

        // Step 2: listCards
        // lists cards when no lesson is opened -> list nothing
        try {
            new ListCardsCommand().execute(model, commandHistory);
            // Given that there are no cards to list
            throw new AssertionError("Command should throw CommandException");
        } catch (CommandException e) {
            assertEquals(e.getMessage(), MESSAGE_NO_OPENED_LESSON);
        }

        // Step 3: addLesson
        // add valid lesson -> lesson added successfully
        CommandResult commandResult = new AddLessonCommand(validLesson).execute(model, commandHistory);

        // lesson added successfully -> success feedback
        assertEquals(String.format(AddLessonCommand.MESSAGE_SUCCESS, validLesson),
                commandResult.getFeedbackToUser());

        // lesson added successfully -> lesson in lessonList
        assertEquals(Collections.singletonList(validLesson), model.getLessons());
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);

        // Step 4: listLessons
        // list lessons when there are lessons -> list 1 lesson
        assertCommandSuccess(new ListLessonsCommand(), model, commandHistory, new ListLessonsCommand()
                .buildList(Collections.singletonList(validLesson)), model);

        // Step 5: deleteLesson
        // delete valid lesson -> lesson deleted successfully
        Index toDeleteIndex = Index.fromZeroBased(0);
        commandResult = new DeleteLessonCommand(toDeleteIndex).execute(model, commandHistory);

        // lesson deleted successfully -> success feedback
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);

        // Step 6: listLessons
        // list lessons when there are no lessons -> command successful with feedback that there are no lessons
        assertCommandSuccess(new ListLessonsCommand(), model,
                commandHistory, MESSAGE_NO_LESSONS, model);
    }

    /**
     * Tests {@link AddLessonCommand}, {@link DeleteLessonCommand}, {@link OpenLessonCommand} and
     * {@link CloseLessonCommand} with {@link ManagementModelManager}.
     *
     * <br><br>Opening and closing a lesson with {@link OpenLessonCommand} and {@link CloseLessonCommand}
     * should not prevent deletion of lesson by using {@link DeleteLessonCommand}.
     */
    @Test
    public void execute_addOpenListCloseDelete_allSuccessful() throws Exception {
        // Step 1: addLesson
        // add valid lesson -> lesson added successfully
        // Tested in execute_listAddDeleteLesson_allSuccessful
        new AddLessonCommand(validLesson).execute(model, commandHistory);

        // Step 2: openLesson
        // opens valid lesson -> lesson opened successfully
        Index toOpenIndex = Index.fromZeroBased(0);
        new OpenLessonCommand(toOpenIndex).execute(model, commandHistory);

        // lesson opened successfully -> success feedback
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);

        // get opened lesson which was added -> same as lesson which was added
        assertEquals(model.getOpenedLesson(), validLesson);

        // Step 3: listCards
        // lists cards of opened lesson -> list successfully
        assertCommandSuccess(new ListCardsCommand(), model, commandHistory,
                new ListCardsCommand().buildList(
                        validLesson.getCoreHeaders(),
                        validLesson.getOptionalHeaders(),
                        validCards), model);

        // Step 4: closeLesson
        // close opened lesson -> lesson closed successfully
        new CloseLessonCommand().execute(model, commandHistory);

        // openedLesson is now null
        assertNull(model.getOpenedLesson());

        // Step 5: listCards
        // lists cards when no lesson is opened -> list nothing
        try {
            new ListCardsCommand().execute(model, commandHistory);
            // Given that there are no cards to list
            throw new AssertionError("Command should throw CommandException");
        } catch (CommandException e) {
            assertEquals(e.getMessage(), MESSAGE_NO_OPENED_LESSON);
        }

        // Step 6: deleteLesson
        // delete valid lesson -> lesson deleted successfully
        Index toDeleteIndex = Index.fromZeroBased(0);
        CommandResult commandResult = new DeleteLessonCommand(toDeleteIndex).execute(model, commandHistory);

        // lesson deleted successfully -> success feedback
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }
}