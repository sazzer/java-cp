package uk.co.grahamcox.spring.repl;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Mechanism to parse Command Definitions out of a Bean
 */
public class CommandBeanParser {
    /**
     * Parse the given bean to determine all of the command definitions
     * @param bean the bean to parse
     * @return the parsed definitions
     */
    public Collection<CommandDefinition> parse(Object bean) {
        return Arrays.asList(bean.getClass().getMethods()).stream()
            .filter(m -> m.isAnnotationPresent(Command.class))
            .map(m -> {
                return new CommandDefinition();
            })
            .collect(Collectors.toList());
    }
}
