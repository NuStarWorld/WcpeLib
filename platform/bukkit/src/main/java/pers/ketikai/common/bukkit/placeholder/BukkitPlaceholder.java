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

package pers.ketikai.common.bukkit.placeholder;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.ketikai.common.bukkit.command.BukkitCommandSender;
import pers.ketikai.common.command.CommandContext;
import pers.ketikai.common.command.CommandLine;
import pers.ketikai.common.command.CommandResult;

import java.util.Objects;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class BukkitPlaceholder extends PlaceholderExpansion {

    public static final String HEADER_DELIMITER = ":";
    public static final String ARGUMENTS_DELIMITER = "_";

    public static BukkitPlaceholder of(@NotNull String identifier, @NotNull String author, @NotNull String version, @NotNull Object command) {
        Objects.requireNonNull(command, "command must not be null.");
        return new BukkitPlaceholder(identifier, author, version, CommandLine.of(identifier, command));
    }

    @NonNull
    @Getter
    private final String identifier;
    @NonNull
    @Getter
    private final String author;
    @NonNull
    @Getter
    private final String version;
    @NonNull
    @Getter
    private final CommandLine commandLine;
    private String header = null;
    @NotNull
    private String header() {
        if (header == null) {
            this.header = getIdentifier() + HEADER_DELIMITER;
        }
        return header;
    }

    @Nullable
    private String[] argumentsOf(@NotNull String params) {
        Objects.requireNonNull(params, "params must not be null.");
        String header = header();
        if (params.isEmpty() || !params.toLowerCase().startsWith(header.toLowerCase())) {
            return null;
        }
        params = params.substring(header.length());
        return params.split(ARGUMENTS_DELIMITER, -1);
    }

    @Override
    public String onRequest(OfflinePlayer offlinePlayer, String params) {
        String[] arguments = argumentsOf(params);
        if (arguments == null) {
            return super.onRequest(offlinePlayer, params);
        }
        CommandSender commandSender = null;
        if (offlinePlayer == null) {
            commandSender = Bukkit.getConsoleSender();
        } else if (offlinePlayer.isOnline()) {
            commandSender = offlinePlayer.getPlayer();
        }
        if (commandSender == null) {
            return null;
        }
        BukkitCommandSender sender = BukkitCommandSender.of(commandSender);
        CommandContext context = CommandContext.of(sender);
        CommandResult result = commandLine.execute(context, arguments);
        return result.isSuccess() ? result.getMessage() : null;
    }

    @Override
    public String onPlaceholderRequest(Player player, String params) {
        return super.onPlaceholderRequest(player, params);
    }
}
