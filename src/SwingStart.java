import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class SwingStart implements ActionListener {

    private JFrame mainFrame;
    private JPanel controlPanel;
    private JLabel statusLabel;
    private JTextField F1, F2;
    private JButton searchButton;
    private JTextArea bigTextArea;

    public SwingStart() {
        prepareGUI();
    }

    public static void main(String[] args) {
        SwingStart swingControlDemo = new SwingStart();
    }

    private void prepareGUI() {
        mainFrame = new JFrame("Search Box");
        mainFrame.setBounds(0, 0, 400, 600);
        mainFrame.setLayout(new BorderLayout());

        JPanel textPanel = new JPanel(new GridLayout(4, 1));
        JLabel linkLabel = new JLabel("Enter link below:");
        JLabel searchTermLabel = new JLabel("Enter search term above:");
        F1 = new JTextField();
        F2 = new JTextField();
        textPanel.add(linkLabel);
        textPanel.add(F1);
        textPanel.add(F2);
        textPanel.add(searchTermLabel);


        searchButton = new JButton("Search");

        searchButton.setActionCommand("OK");
        searchButton.addActionListener(new ButtonClickListener());

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(textPanel, BorderLayout.CENTER);
        inputPanel.add(searchButton, BorderLayout.SOUTH);

        bigTextArea = new JTextArea();
        bigTextArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(bigTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


        mainFrame.add(inputPanel, BorderLayout.NORTH);
        mainFrame.add(scrollPane, BorderLayout.CENTER);
        mainFrame.setVisible(true);
    }



    public void htmlRead() {
        try {
            bigTextArea.setText("");
            URL url = new URL(F1.getText());
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            boolean hasPrinted = false;
            while ((line = reader.readLine()) != null) {
                if (line.contains("href=") && line.contains("http")) {
                    hasPrinted = true;
                    int start = line.indexOf("http");
                    int end = line.indexOf("\"", start + 4);
                    if (end == -1) {
                        end = line.indexOf("'", start + 4);
                    }

                    String L = line.substring(start, end);


                    if (line.substring(start, end).contains(F2.getText())) {
                        System.out.println(line.substring(start, end));

                        bigTextArea.append(line.substring(start, end) + "\n");
                    }
                }
            }
            if(!hasPrinted){
                while((line = reader.readLine()) != null){
                    int start = line.indexOf("http");
                    int end = line.indexOf("\"", start + 4);
                    if (end == -1) {
                        end = line.indexOf("'", start + 4);
                    }
                    if (line.contains("href=") && line.contains("http")) {
                        System.out.println(line.substring(start,end));
                    }

                }

            }
            reader.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    private class ButtonClickListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if(command.equals("OK")){
                htmlRead();
            }
        }
    }
}
