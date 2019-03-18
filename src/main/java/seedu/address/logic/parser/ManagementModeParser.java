package seedu.address.logic.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.management.AddLessonCommand;
import seedu.address.logic.commands.management.ExitCommand;
import seedu.address.logic.commands.management.HelpCommand;
import seedu.address.logic.commands.management.HistoryCommand;
import seedu.address.logic.commands.management.ListLessonsCommand;
import seedu.address.logic.commands.quiz.QuizStartCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class ManagementModeParser implements Parser<Command> {
    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parse(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
<<<<<<< HEAD:src/main/java/seedu/address/logic/parser/BrainTrainParser.java
=======

>>>>>>> 9796a678da6bc293ec34cf45dac1be7d8be3ce1b:src/main/java/seedu/address/logic/parser/ManagementModeParser.java
        switch (commandWord) {

        //        case FindCommand.COMMAND_WORD:
        //            return new FindCommandParser().parse(arguments);
        // TODO use parser here
<<<<<<< HEAD:src/main/java/seedu/address/logic/parser/BrainTrainParser.java
        case StartCommand.COMMAND_WORD:
            return new StartCommandParser().parse(arguments);
=======
        case QuizStartCommand.COMMAND_WORD:
            return new QuizStartCommand();
>>>>>>> 9796a678da6bc293ec34cf45dac1be7d8be3ce1b:src/main/java/seedu/address/logic/parser/ManagementModeParser.java

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case AddLessonCommand.COMMAND_WORD:
            return new AddLessonParser().parse(arguments);

        case ListLessonsCommand.COMMAND_WORD:
            return new ListLessonsCommand();

        //        case UndoCommand.COMMAND_WORD:
        //            return new UndoCommand();
        //
        //        case RedoCommand.COMMAND_WORD:
        //            return new RedoCommand();

        default:
            throw new ParseException(Messages.MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
