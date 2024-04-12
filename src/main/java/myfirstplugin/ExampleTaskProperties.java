package myfirstplugin;

import com.atlassian.bamboo.specs.api.model.task.TaskProperties;
import com.atlassian.bamboo.specs.api.codegen.annotations.Builder;
import com.atlassian.bamboo.specs.api.exceptions.PropertiesValidationException;
import com.atlassian.bamboo.specs.api.model.AtlassianModuleProperties;
import com.atlassian.bamboo.specs.api.validators.common.ValidationContext;
import com.atlassian.bamboo.specs.api.model.plan.requirement.RequirementProperties;
import com.atlassian.bamboo.specs.api.model.plan.condition.ConditionProperties;
import org.apache.commons.lang3.StringUtils;
import javax.annotation.concurrent.Immutable;
import java.util.Objects;
import java.util.List;
import java.util.ArrayList;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

@Immutable
@Builder(ExampleTaskBuilder.class)
public final class ExampleTaskProperties extends TaskProperties {
    private static final AtlassianModuleProperties ATLASSIAN_PLUGIN =
            new AtlassianModuleProperties("myfirstplugin.myfirstplugin:exampleTask");
    private static final ValidationContext VALIDATION_CONTEXT = ValidationContext.of("Example task");

    // @Rule
    // public ErrorCollector errorCollector = new ErrorCollector();

    @NotNull private final String myText;

    // for importing
    private ExampleTaskProperties() {
        this.myText = null;
    }

    public ExampleTaskProperties(@Nullable final String description,
                                 final boolean enabled,
                                 @NotNull final String myText,
                                 @NotNull List<RequirementProperties> requirements,
                                 @NotNull List<? extends ConditionProperties> conditions) throws PropertiesValidationException {
        super(description, enabled, requirements, conditions);

        this.myText = myText;

        validate();
    }

    @Override
    public void validate() throws PropertiesValidationException {
        super.validate();

        // errorCollector.checkThat(VALIDATION_CONTEXT, StringUtils.isNotBlank(myText), "myText is not defined");
    }

    @NotNull
    @Override
    public AtlassianModuleProperties getAtlassianPlugin() {
        return ATLASSIAN_PLUGIN;
    }

    public String getMyText() {
        return myText;
    }

    // Equals and hash code
    // ...

    // Getters
    // ...
}