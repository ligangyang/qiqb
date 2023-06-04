package net.qiqb.execution.executor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandGroup {

    private final List<CommandWrapper> commands = new ArrayList<>();

    /**
     * 当前索引
     */
    public int currentIndex = -1;

    public CommandGroup(CommandWrapper... commands) {
        if (commands.length == 0) {
            throw new IllegalStateException("命令组不能为空");
        }
        this.commands.addAll(Arrays.asList(commands));
    }

    public void next() {

        if (this.currentIndex + 1 < commands.size()) {
            this.currentIndex++;
        }
    }


    public CommandWrapper getCurrent() {
        return commands.get(currentIndex);
    }

    public boolean isLastOne() {
        return currentIndex >= commands.size() - 1;
    }

    public boolean isFirstOne() {
        return currentIndex == 0;
    }

    public void addCommand(CommandWrapper command) {
        this.commands.add(command);
    }
}
