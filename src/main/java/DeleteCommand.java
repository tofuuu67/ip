public class DeleteCommand extends Command {
    private final int index;

    public DeleteCommand(String arg) throws HeimerdingerException {
        if (isNumeric(arg)) {
            this.index = Integer.parseInt(arg) - 1;
        } else {
            throw new HeimerdingerException("My precise machinations require a number!");
        }
    }

    public void execute(TaskList tasks, Ui ui, Storage storage) throws HeimerdingerException{
        if (index < 0 || index >= tasks.size()) {
            throw new HeimerdingerException("My calculations indicate that your number is not accessible!");
        }
        Task deleted = tasks.remove(index);
        ui.showDeleted(deleted, tasks.size());
        storage.save(tasks.getAll());
    }
}
