package UTP5_1;

import java.util.ArrayList;
import java.util.List;

public class Letters {
    private final List<Thread> threads = new ArrayList<>();

    public Letters(String word) {
        for (char letter : word.toCharArray()) {
            Thread thread = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    System.out.print(letter);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }, "Thread " + letter);
            threads.add(thread);
        }
    }

    public List<Thread> getThreads() {
        return threads;
    }
}

