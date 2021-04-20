/*
 * Copyright (c) 2020 Dzikoysk
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

package org.panda_lang.reposilite.error;

import org.panda_lang.reposilite.Reposilite;
import org.panda_lang.utilities.commons.ArrayUtils;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class FailureService {

    private final Set<String> exceptions = ConcurrentHashMap.newKeySet();

    public void throwException(String id, Throwable throwable) {
        Reposilite.getLogger().error(id, throwable);

        exceptions.add(String.join(System.lineSeparator(),
                "failure " + id,
                "  by " + throwable.getClass().getSimpleName() + ": " + throwable.getMessage(),
                "  at " + ArrayUtils.get(throwable.getStackTrace(), 0).map(StackTraceElement::toString).orElseGet("<unknown stacktrace>")
        ));
    }

    public boolean hasFailures() {
        return !exceptions.isEmpty();
    }

    public Collection<? extends String> getFailures() {
        return exceptions;
    }

}
