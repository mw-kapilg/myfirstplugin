package myfirstplugin;

import com.atlassian.bamboo.task.export.TaskDefinitionExporter;
import com.atlassian.bamboo.task.TaskContainer;
import com.atlassian.bamboo.specs.api.model.task.TaskProperties;
import com.atlassian.bamboo.task.TaskDefinition;
import com.atlassian.bamboo.specs.api.builders.task.Task;
import com.atlassian.bamboo.ww2.actions.build.admin.create.UIConfigSupport;
import com.atlassian.bamboo.specs.yaml.YamlSpecsValidationException;
import com.atlassian.bamboo.task.export.TaskValidationContext;
import com.atlassian.bamboo.specs.api.validators.common.ValidationContext;
import com.atlassian.bamboo.specs.api.validators.common.ValidationProblem;
import com.atlassian.bamboo.util.Narrow;
import com.atlassian.bamboo.specs.yaml.Node;
import com.atlassian.bamboo.specs.yaml.MapNode;
import com.atlassian.bamboo.specs.yaml.StringNode;
import com.atlassian.bamboo.specs.yaml.BambooYamlParserUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.lang3.StringUtils;
import com.google.common.base.Preconditions; 
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.*;

import myfirstplugin.ExampleTaskBuilder;
// import myfirstplugin.ExampleTaskProperties;

public class ExampleTaskExporter implements TaskDefinitionExporter {
    private static final ValidationContext EXAMPLE_CONTEXT = ValidationContext.of("Example task");

    // @Autowired
    // private UIConfigSupport uiConfigSupport;


    @NotNull
    @Override
    public ExampleTaskBuilder toSpecsEntity(@NotNull final TaskDefinition taskDefinition) {
        final Map<String, String> configuration = taskDefinition.getConfiguration();
        return new ExampleTaskBuilder()
                .myText(configuration.get(ExampleConfig.CFG_MY_TEXT));
    }

    @Override
    public Map<String, String> toTaskConfiguration(@NotNull TaskContainer taskContainer, final TaskProperties taskProperties) {
        final ExampleTaskProperties exampleTaskProperties = Narrow.downTo(taskProperties, ExampleTaskProperties.class);
        Preconditions.checkState(exampleTaskProperties != null, "Don't know how to import task properties of type: " + taskProperties.getClass().getName());

        final Map<String, String> cfg = new HashMap<>();
        cfg.put(ExampleConfig.CFG_MY_TEXT, exampleTaskProperties.getMyText());
        return cfg;
    }

    // @Override
    // public void populateContextForEdit(@NotNull final Map<String, Object> context, @NotNull final TaskDefinition taskDefinition){
    //     super.populateContextForEdit(context, taskDefinition);

    //     context.put(ExampleConfig.CFG_MY_TEXT, taskDefinition.getConfiguration().get(ExampleConfig.CFG_MY_TEXT));
    // }

    // @Nullable
    @Override
    public ExampleTaskBuilder fromYaml(@NotNull Node node, @NotNull TaskValidationContext context) throws YamlSpecsValidationException {
        if (node instanceof MapNode) {
            // LinkedList<Repository> repositories = new LinkedList<>();
            String myText = "";
            MapNode mapNode = (MapNode) node;
            if (mapNode.getOptionalNode("exampleTask").isPresent()) {
                final MapNode yamlConfig = mapNode.getOptionalMap("exampleTask").orElse(null);
                if(yamlConfig.getOptionalString(ExampleConfig.CFG_MY_TEXT).isPresent()) {
                    System.out.println("myText is present");
                    final StringNode textConfig = yamlConfig.getOptionalString(ExampleConfig.CFG_MY_TEXT).orElse(null);
                    myText = textConfig.get();
                    System.out.println("myText: " + myText);
                }
                
                return new ExampleTaskBuilder()
                    .myText(myText);
            }
        }
        return null;
    }

    // @Nullable
    @Override
    public Node toYaml(@NotNull TaskProperties taskProperties) {
        final ExampleTaskProperties exampleTaskProperties = Narrow.downTo(taskProperties, ExampleTaskProperties.class);
        
        final Map<String, String> config = new HashMap<>();
        // config.put(YamlTags.REPOSITORIES, exampleTaskProperties.repositories.stream()
        //         .collect(Collectors.toMap(Repository::getName, Repository::getEnabled)
        //         ));
        config.put(ExampleConfig.CFG_MY_TEXT, exampleTaskProperties.getMyText());
        
        final Map<String, Object> result = new HashMap<>();
        result.put("exampleTask", config);

        return BambooYamlParserUtils.asNode(result, ValidationContext.of("Example task"));
    }

    @Override
    public List<ValidationProblem> validate(@NotNull TaskValidationContext taskValidationContext, @NotNull TaskProperties taskProperties) {
        final List<ValidationProblem> validationProblems = new ArrayList<>();
        final ExampleTaskProperties exampleTaskProperties = Narrow.downTo(taskProperties, ExampleTaskProperties.class);
        if (exampleTaskProperties != null) {
            // final List<String> labels = uiConfigSupport.getExecutableLabels(ExampleConfig.CAPABILITY_SHORT_KEY);
            // final String label = exampleTaskProperties.getExecutable(); 
            
            // if (labels == null || !labels.contains(label)) {
            //     validationProblems.add(new ValidationProblem(
            //             COMMAND_CONTEXT, "Can't find executable by label: '" + label + "'. Available values: " + labels));
            // }
        }
        return validationProblems;
    }
}