package net.qiqb.execution.executor.config;

import net.qiqb.execution.executor.CommandWrapper;

public interface ExecutorInterceptor {

    default boolean preExecute(CommandWrapper cmd) {

        return true;
    }

    default void postExecute(CommandWrapper cmd) {
    }


    default void afterCompletion(CommandWrapper cmd) {
    }
}
