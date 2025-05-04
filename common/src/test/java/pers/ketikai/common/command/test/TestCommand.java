/*
 * Copyright 2025 ketikai
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pers.ketikai.common.command.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import pers.ketikai.common.command.CommandContext;
import pers.ketikai.common.command.CommandLine;
import pers.ketikai.common.command.CommandResult;
import pers.ketikai.common.command.CommandSender;
import pers.ketikai.common.command.example.ExampleCommand;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class TestCommand {


    @Test
    public void test() {
        TestCommandSender sender = new TestCommandSender(UUID.randomUUID(), true, Collections.emptySet());
        CommandLine commandLine = CommandLine.of(getClass().getSimpleName(), new ExampleCommand());
        CommandResult result;
        result = execute(commandLine, sender, "reload");
        System.out.println(result.isSuccess() + " " + result.getMessage());
        System.out.println(complete(commandLine, sender, ""));
        System.out.println(complete(commandLine, sender, "re"));
        System.out.println(complete(commandLine, sender, "$"));

        result = execute(commandLine, sender, "reload a");
        System.out.println(result.isSuccess() + " " + result.getMessage());
        System.out.println(complete(commandLine, sender, "reload "));
        System.out.println(complete(commandLine, sender, "reload a"));
        System.out.println(complete(commandLine, sender, "reload $"));

        result = execute(commandLine, sender, "reload a b");
        System.out.println(result.isSuccess() + " " + result.getMessage());
        System.out.println(complete(commandLine, sender, "reload a b"));

        result = execute(commandLine, sender, "fuck");
        System.out.println(result.isSuccess() + " " + result.getMessage());

        result = execute(commandLine, sender, "fuck you");
        System.out.println(result.isSuccess() + " " + result.getMessage());

        result = execute(commandLine, sender, "");
        System.out.println(result.isSuccess() + " " + result.getMessage());

        result = execute(commandLine, sender, "test");
        System.out.println(result.isSuccess() + " " + result.getMessage());
    }

    private CommandResult execute(CommandLine commandLine, CommandSender sender, String command) {
        String[] arguments = command.split(" ", -1);
        CommandContext context = CommandContext.of(sender);
        return commandLine.execute(context, arguments);
    }

    private List<String> complete(CommandLine commandLine, CommandSender sender, String command) {
        String[] arguments = command.split(" ", -1);
        CommandContext context = CommandContext.of(sender);
        return commandLine.complete(context, arguments);
    }

    @Data
    @AllArgsConstructor
    public static final class TestCommandSender implements CommandSender {
        @NonNull
        private final UUID uniqueId;
        private boolean administrator;
        @NonNull
        private Set<String> permissions;

        @Override
        public boolean hasPermission(@NotNull String permission) {
            return permissions.contains(permission);
        }
    }
}
