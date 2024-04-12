package myfirstplugin;

import com.atlassian.bamboo.build.logger.BuildLogger;
import com.atlassian.bamboo.task.TaskContext;
import com.atlassian.bamboo.task.TaskException;
import com.atlassian.bamboo.task.TaskResult;
import com.atlassian.bamboo.task.TaskResultBuilder;
import com.atlassian.bamboo.task.TaskType;

import org.jetbrains.annotations.NotNull;

public class ExampleTask implements TaskType
{
    @NotNull
    @java.lang.Override
    public TaskResult execute(@NotNull final TaskContext taskContext) throws TaskException
    {
        final BuildLogger buildLogger = taskContext.getBuildLogger();

        final String myText = taskContext.getConfigurationMap().get(ExampleConfig.CFG_MY_TEXT);

        buildLogger.addBuildLogEntry(myText);

        return TaskResultBuilder.create(taskContext).success().build();
    }
}
