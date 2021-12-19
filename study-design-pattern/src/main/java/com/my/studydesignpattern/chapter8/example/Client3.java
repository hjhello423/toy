package com.my.studydesignpattern.chapter8.example;

import java.util.Stack;

public class Client3 {

    public static void main(String[] args) {
    }

    private abstract class Command {

        private Application application;
        private Editor editor;
        private String backup;

        public Command(Application application) {
            this.application = application;
        }

        public void save() {
            backup = editor.text;
        }

        public void undo() {
            editor.text = backup;
        }

        public abstract void execute();

    }

    private class SomethingCommand extends Command {

        private Editor editor;

        public SomethingCommand(Application application) {
            super(application);
        }

        @Override
        public void execute() {
            save();
            editor.doA();
        }

    }

    private class UndoCommand extends Command {

        private Editor editor;

        public UndoCommand(Application application) {
            super(application);
        }

        @Override
        public void execute() {
            undo();
            editor.doB();
        }

    }

    private static class Editor {

        private String text;

        public void doA() {
        }

        public void doB() {
        }

    }

    private static class History {

        private Stack<Command> stack;

        public void push(Command c) {
            stack.push(c);
        }

        public void pop() {
            stack.pop();
        }

    }

    private static class Application {

        private Editor editor;
        private History history;

        public void operateA() {
        }

        public void operateB() {
        }

    }

}

