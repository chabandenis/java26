package ru.otus.listener.homework;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;
import ru.otus.listener.Listener;
import ru.otus.model.Message;

public class HistoryListener implements Listener, HistoryReader {

    private final Deque<Message> stack = new ArrayDeque<>();

    @Override
    public void onUpdated(Message msg) {
        stack.push(msg.copy(msg));
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return stack.stream().filter(x->x.getId() == id).findFirst();
    }
}
