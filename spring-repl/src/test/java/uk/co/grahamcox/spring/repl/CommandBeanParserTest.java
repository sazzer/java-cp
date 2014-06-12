package uk.co.grahamcox.spring.repl;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;

/**
 * Unit tests for the Command Bean Parser
 */
public class CommandBeanParserTest {
    /**
     * Test parsing an object that doesn't have any commands
     */
    @Test
    public void testNoCommands() {
        CommandBeanParser commandBeanParser = new CommandBeanParser();
        NoCommands noCommands = new NoCommands();

        Collection<CommandDefinition> commandDefinitions = commandBeanParser.parse(noCommands);
        Assert.assertTrue(commandDefinitions.isEmpty());
    }

    /**
     * Object with no commands
     */
    public static class NoCommands {

    }

    /**
     * Object with a command that takes no arguments
     */
    public static class NoArgsCommands {
        /**
         * The command
         * @return a string
         */
        @Command("command")
        public String command() {
            return "";
        }
    }

    /**
     * Object with a command that takes arguments
     */
    public static class ArgsCommands {
        /**
         * The command
         * @return a string
         */
        @Command("command")
        public String command(@Argument("a") String a, @Argument("b") Integer b) {
            return "";
        }
    }
}