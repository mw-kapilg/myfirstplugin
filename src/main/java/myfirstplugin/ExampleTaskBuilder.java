package myfirstplugin;

import com.atlassian.bamboo.specs.api.builders.task.Task;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import java.util.Objects;

/**
 * Represents a task that executes a command.
 */
public class ExampleTaskBuilder extends Task<ExampleTaskBuilder, ExampleTaskProperties> { 
    @NotNull private String myText;

    public ExampleTaskBuilder myText(@NotNull final String myText) {
        // checkNotEmpty("myText", myText);
        this.myText = myText;
        return this;
    }

    @NotNull
    @Override
    protected ExampleTaskProperties build() {
        return new ExampleTaskProperties(
                description,
                taskEnabled,
                myText,
                requirements,
                conditions);
    }
}