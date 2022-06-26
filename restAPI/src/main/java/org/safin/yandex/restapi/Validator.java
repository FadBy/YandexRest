package org.safin.yandex.restapi;

import org.springframework.stereotype.Component;

public interface Validator<T> {
    boolean isValid(T unit);
}
