package command;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Stack;

public class CommandRegistry {

	private final ObservableList<Command> commandStack = FXCollections.observableArrayList();
	private final Stack<Command> undoneCommandStack = new Stack<>();

	public void executeCommand(Command command) {
		command.execute();
		commandStack.add(command);
		undoneCommandStack.clear();
	}

	public void redo() {
		if (undoneCommandStack.isEmpty()) return;
		Command lastUndoneCommand = undoneCommandStack.pop();
		lastUndoneCommand.redo();
		commandStack.add(lastUndoneCommand);
	}

	public void undo() {
		if (commandStack.isEmpty()) return;
		Command lastDoneCommand = commandStack.get(commandStack.size() - 1);
		lastDoneCommand.undo();
		undoneCommandStack.push(lastDoneCommand);
	}

	public ObservableList<Command> getCommandStack() {
		return commandStack;
	}
}
