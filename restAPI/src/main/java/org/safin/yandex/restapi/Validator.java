package org.safin.yandex.restapi;

import org.springframework.stereotype.Component;

/**
 * let validate object
 * @param <T> type of validation object
 */
public interface Validator<T> {
    boolean isValid(T unit);
}
