package GameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SettingsDialog extends JDialog {

    private List<JTextField> textFields = new ArrayList<>();
    public SettingsDialog() {
        JPanel panel = new JPanel();
        this.setTitle("Engine beállítások");
        this.setSize(800, 400);
        this.add(panel);
        List<JLabel> labels = new ArrayList<>();
        int[] values = new ConfigManager().read();
        panel.setLayout(new GridLayout(5,1));
        labels.add(new JLabel("Add meg a hashtábla méretét (MB):"));
        textFields.add(new JTextField(5));
        labels.add(new JLabel("Add meg a használandó Threadek számát:"));
        textFields.add(new JTextField(5));
        labels.add(new JLabel("Add meg az engine erősségi szintjét (1-20):"));
        textFields.add(new JTextField(5));
        labels.add(new JLabel("Add meg, hogy milyen mélységig számoljon az engine:"));
        textFields.add(new JTextField(5));
        for (int i =0; i<labels.size();i++){
            JPanel tmpp = new JPanel();
            tmpp.setLayout( new FlowLayout());
            tmpp.add(labels.get(i));
            tmpp.add(textFields.get(i));
            textFields.get(i).setText(Integer.toString(values[i]));
            panel.add(tmpp);
            tmpp.setVisible(true);
        }
        JButton submit = new JButton("Mentés");
        panel.add(submit);
        submit.addActionListener(new SubmitListener());
        this.setVisible(true);
        panel.setVisible(true);
    }
    private class SubmitListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int values[] = new int[4];
            for (int i = 0; i < textFields.size() ; i++) {
                values[i] = Integer.parseInt(textFields.get(i).getText());
            }
            new ConfigManager().write(values);
        }
    }
}
