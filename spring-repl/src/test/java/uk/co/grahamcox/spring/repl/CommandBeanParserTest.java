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
        Object cmd = new NoCommands();

        Collection<CommandDefinition> commandDefinitions = commandBeanParser.parse(cmd);
        Assert.assertTrue(commandDefinitions.isEmpty());
    }

    /**
     * Test parsing an object that has a command with no args
     */
    @Test
    public void testNoArgs() {
        CommandBeanParser commandBeanParser = new CommandBeanParser();
        Object cmd = new NoArgsCommands();

        Collection<CommandDefinition> commandDefinitions = commandBeanParser.parse(cmd);
        Assert.assertEquals(1, commandDefinitions.size());
    }

    /**
     * Test parsing an object that a command with arguments
     */
    @Test
    public void testArgs() {
        CommandBeanParser commandBeanParser = new CommandBeanParser();
        Object cmd = new ArgsCommands();

        Collection<CommandDefinition> commandDefinitions = commandBeanParser.parse(cmd);
        Assert.assertEquals(1, commandDefinitions.size());
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