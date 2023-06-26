package UTP5_2;

public class StringTask implements Runnable {
    private final String stringToMultiply;
    private final int times;
    private TaskState state;
    private String result;
    private Thread thread;

    public StringTask(String stringToMultiply, int times) {
        this.stringToMultiply = stringToMultiply;
        this.times = times;
        this.state = TaskState.CREATED;
        this.result = "";
    }

    @Override
    public void run() {
        this.state = TaskState.RUNNING;
        try {
            for (int i = 0; i < times; i++) {
                if (Thread.currentThread().isInterrupted()) {
                    this.state = TaskState.ABORTED;
                    return;
                }
                this.result += this.stringToMultiply;
            }
        } finally {
            if (!Thread.currentThread().isInterrupted()) {
                this.state = TaskState.READY;
            }
        }
    }

    public String getResult() {
        return this.result;
    }

    public TaskState getState() {
        return this.state;
    }

    public void start() {
        if (this.state == TaskState.CREATED) {
            this.thread = new Thread(this);
            this.thread.start();
        }
    }

    public void abort() {
        if (this.state == TaskState.RUNNING) {
            this.thread.interrupt();
        }
    }

    public boolean isDone() {
        return this.state == TaskState.ABORTED || this.state == TaskState.READY;
    }
}

