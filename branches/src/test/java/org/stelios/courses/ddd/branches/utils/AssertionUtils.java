package org.stelios.courses.ddd.branches.utils;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.assertj.core.groups.Tuple;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class AssertionUtils {

    private AssertionUtils() {
    }

    public static ListAppender<ILoggingEvent> getListAppenderForClass(Class<?> clazz) {
        Logger logger = (Logger) LoggerFactory.getLogger(clazz);
        ListAppender<ILoggingEvent> loggingEventListAppender = new ListAppender<>();
        loggingEventListAppender.start();
        logger.addAppender(loggingEventListAppender);
        return loggingEventListAppender;
    }

    public static void assertContainsInLogs(ListAppender<ILoggingEvent> appender, String message, Level level) {
        assertThat(appender.list)
                .extracting(ILoggingEvent::getFormattedMessage, ILoggingEvent::getLevel)
                .contains(Tuple.tuple(message, level));
    }

    public static void assertContainsOnlyInLogs(ListAppender<ILoggingEvent> appender, String message, Level level) {
        assertThat(appender.list)
                .extracting(ILoggingEvent::getFormattedMessage, ILoggingEvent::getLevel)
                .containsOnly(Tuple.tuple(message, level));
    }
}
