import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Tab extends JButton implements ActionListener {
    public String title, link;
    public TabDisplay display;
    private TabList list;

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("tab clicked");
        list.tabClicked(this);
    }
    public Tab(String Title, String Link, TabDisplay Display, TabList List) {
        super(Title);
        setBackground(Maxium.THEME.Selected);
        setForeground(Maxium.THEME.Icon);
        addActionListener(this);

        list = List;
        title = Title;
        link = Link;
        display = Display;
    }
}