package UTP5_3;

import UTP5_2.StringTask;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class MainFrame extends JFrame {

    private final ExecutorService executorService;
    private final List<Future<StringTask>> futureTasks;
    private final DefaultListModel<String> listModel;
    private final JList<String> taskList;

    public MainFrame() {
        executorService = Executors.newFixedThreadPool(4);
        futureTasks = new ArrayList<>();
        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);
        JButton startButton = new JButton("Start Task");
        JButton cancelButton = new JButton("Cancel Task");
        JButton resultButton = new JButton("Show Result");
        JButton statusButton = new JButton("Check Status");

        startButton.addActionListener(new StartAction());
        cancelButton.addActionListener(new CancelAction());
        resultButton.addActionListener(new ResultAction());
        statusButton.addActionListener(new StatusAction());

        setLayout(new BorderLayout());
        add(new JScrollPane(taskList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(resultButton);
        buttonPanel.add(statusButton);

        add(buttonPanel, BorderLayout.SOUTH);
        setSize(450, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private class StartAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            StringTask task = new StringTask("A", 70000);
            Future<StringTask> future = executorService.submit(task, task);
            futureTasks.add(future);
            listModel.addElement("Task " + (futureTasks.size() - 1) + ": CREATED");
        }
    }

    private class CancelAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int index = taskList.getSelectedIndex();
            if (index != -1) {
                Future<StringTask> future = futureTasks.get(index);
                future.cancel(true);
                listModel.setElementAt("Task " + index + ": ABORTED", index);
            }
        }
    }

    private class ResultAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int index = taskList.getSelectedIndex();
            if (index != -1) {
                Future<StringTask> future = futureTasks.get(index);
                try {
                    StringTask task = future.get();
                    JOptionPane.showMessageDialog(MainFrame.this, "Result length: " + task.getResult().length(), "Task " + index, JOptionPane.INFORMATION_MESSAGE);
                } catch (InterruptedException | ExecutionException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private class StatusAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int index = taskList.getSelectedIndex();
            if (index != -1) {
                Future<StringTask> future = futureTasks.get(index);
                if (future.isDone()) {
                    listModel.setElementAt("Task " + index + ": READY", index);
                } else if (future.isCancelled()) {
                    listModel.setElementAt("Task " + index + ": ABORTED", index);
                } else {
                    listModel.setElementAt("Task " + index + ": RUNNING", index);
                }
            }
        }
    }
}


