package com.my.studydesignpattern.chapter6.practice.practice_2;

import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class TicketBox {

    private static final int MAX_TICKET_COUNT = 10;
    private static int ticketCount = 0;

    public static TicketBox getInstance() {
        return TicketBoxClazz.INSTANCE;
    }

    public synchronized Ticket getTicket() {
        validateTicketSize();
        ticketCount++;
        return new Ticket();
    }

    private void validateTicketSize() {
        if (ticketCount > MAX_TICKET_COUNT) {
            throw new RuntimeException("티켓 발급 가능 개수 초과");
        }
    }

    private static class TicketBoxClazz {

        private static final TicketBox INSTANCE = new TicketBox();

    }

}
