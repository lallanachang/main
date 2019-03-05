package braintrain.logic.parser;

//import static braintrain.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static braintrain.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import braintrain.logic.commands.Command;
import braintrain.logic.commands.ExitCommand;
import braintrain.logic.commands.HelpCommand;
import braintrain.logic.commands.HistoryCommand;
import braintrain.logic.commands.StartCommand;
import braintrain.logic.parser.exceptions.ParseException;
import braintrain.model.card.exceptions.MissingCoreException;

/**
 * Parses user input.
 */
public class BrainTrainParser {

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
    public Command parseCommand(String userInput) throws ParseException, MissingCoreException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        //if (!matcher.matches()) {
        //    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        //}

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        //        case FindCommand.COMMAND_WORD:
        //            return new FindCommandParser().parse(arguments);
        // TODO use parser here
        case StartCommand.COMMAND_WORD:
            return new StartCommandParser().parse(arguments);

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        //    case UndoCommand.COMMAND_WORD:
        //        return new UndoCommand();
        //
        //    case RedoCommand.COMMAND_WORD:
        //        return new RedoCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
