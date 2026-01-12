package com.fat.GUI.Panels;

import javax.swing.*;
import java.awt.*;

public class TestPanel extends JPanel {
    public TestPanel(){
        JLabel label = new JLabel("This is a test panel");
        add(label, "align center");
    }
}
