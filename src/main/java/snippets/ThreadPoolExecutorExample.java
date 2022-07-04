package snippets;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.*;

public class ThreadPoolExecutorExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        example1();
//        example2();
        example3();
    }

    public static void example4() {
    }
    public static void example3() {
        JFrame f = new JFrame("Button Example");
        final JTextField tf = new JTextField();
        tf.setBounds(50, 50, 150, 20);
        JButton b = new JButton("Click Here");
        b.setBounds(50, 100, 95, 30);

        b.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
//              ToDo: this doesn't get displayed
                b.setText("Performing");


                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                tf.setText("Welcome to Javatpoint.");
                b.setText("Performed");

            }
        });
        f.add(b);
        f.add(tf);
        f.setSize(400, 400);
        f.setLayout(null);
        f.setVisible(true);
    }


    public static void example2() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Future<String> future = executorService.submit(() -> "Hello World");
// some operations
        String result = future.get();
    }

    public static void example1() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> System.out.println("Hello World"));
    }


}
