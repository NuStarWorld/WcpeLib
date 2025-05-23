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

package pers.ketikai.common.command;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public interface CommandContext extends Map<String, Object> {

    @NotNull
    static CommandContext of(@NotNull CommandSender sender) {
        return of(sender, Collections.emptyMap());
    }

    @NotNull
    static CommandContext of(@NotNull CommandSender sender, @NotNull Map<String, Object> map) {
        Objects.requireNonNull(sender, "sender must not be null.");
        Objects.requireNonNull(map, "map must not be null.");
        return new SimpleCommandContext(sender, map);
    }

    @NotNull
    CommandSender getSender();
}
