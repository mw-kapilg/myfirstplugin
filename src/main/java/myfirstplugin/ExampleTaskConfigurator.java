package myfirstplugin;

import com.atlassian.bamboo.build.logger.BuildLogger;
import com.atlassian.bamboo.task.TaskContext;
import com.atlassian.bamboo.task.TaskException;
import com.atlassian.bamboo.task.TaskResult;
import com.atlassian.bamboo.task.TaskResultBuilder;
import com.atlassian.bamboo.task.TaskType;

import com.atlassian.bamboo.collections.ActionParametersMap;
import com.atlassian.bamboo.task.AbstractTaskConfigurator;
import com.atlassian.bamboo.task.TaskDefinition;
import com.atlassian.bamboo.utils.error.ErrorCollection;
import com.atlassian.struts.TextProvider;

import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;

public class ExampleTaskConfigurator extends AbstractTaskConfigurator{
    private TextProvider textProvider;

    public Map<String, String> generateTaskConfigMap(@NotNull final ActionParametersMap params, @Nullable final TaskDefinition previousTaskDefinition)
    {
        final Map<String, String> config = super.generateTaskConfigMap(params, previousTaskDefinition);

        config.put(ExampleConfig.CFG_MY_TEXT, params.getString(ExampleConfig.CFG_MY_TEXT));

        return config;
    }

    @Override
    public void populateContextForCreate(@NotNull final Map<String, Object> context)
    {
        super.populateContextForCreate(context);

        context.put(ExampleConfig.CFG_MY_TEXT, "(default) Hello, World!");
    }

    @Override
    public void populateContextForEdit(@NotNull final Map<String, Object> context, @NotNull final TaskDefinition taskDefinition)
    {
        super.populateContextForEdit(context, taskDefinition);

        context.put(ExampleConfig.CFG_MY_TEXT, taskDefinition.getConfiguration().get(ExampleConfig.CFG_MY_TEXT));
    }

    public void validate(@NotNull final ActionParametersMap params, @NotNull final ErrorCollection errorCollection)
    {
        super.validate(params, errorCollection);

        final String myTextValue = params.getString(ExampleConfig.CFG_MY_TEXT);
        if (StringUtils.isEmpty(myTextValue))
        {
            errorCollection.addError(ExampleConfig.CFG_MY_TEXT, textProvider.getText("example.myText.error"));
        }
    }
}
