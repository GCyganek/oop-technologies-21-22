package command;

import model.Account;
import model.Transaction;

import java.util.List;

public class RemoveTransactionCommand implements Command {

    private final List<Transaction> transactionsToDelete;
    private final Account account;

    public RemoveTransactionCommand(List<Transaction> transactionsToDelete, Account account) {
        this.transactionsToDelete = transactionsToDelete;
        this.account = account;
    }

    @Override
    public void execute() {
        for (Transaction transactionToDelete: transactionsToDelete) {
            account.removeTransaction(transactionToDelete);
        }
    }

    @Override
    public String getName() {
        return transactionsToDelete.size() + " transactions removed.";
    }

    @Override
    public void undo() {
        for (Transaction transactionToDelete: transactionsToDelete) {
            account.addTransaction(transactionToDelete);
        }
    }

    @Override
    public void redo() {
        execute();
    }
}
