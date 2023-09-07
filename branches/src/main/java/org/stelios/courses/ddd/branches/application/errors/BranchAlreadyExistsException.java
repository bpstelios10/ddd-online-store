package org.stelios.courses.ddd.branches.application.errors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BranchAlreadyExistsException extends Exception {

    private static final String MESSAGE = "Trying to save existing branch";

    public BranchAlreadyExistsException() {
        super(MESSAGE);
        log.error(MESSAGE);
    }
}
