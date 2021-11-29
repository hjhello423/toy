package com.my.studydesignpattern.chapter6.practice.practice_2;

public class TicketThread extends Thread {

    public TicketThread(int i) {
    }

    public void run() {
        TicketBox ticketBox = TicketBox.getInstance();
        Ticket ticket = ticketBox.getTicket();
        System.out.println(ticket);
    }

}
