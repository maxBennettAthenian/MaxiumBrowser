import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class Tab extends JButton implements ActionListener {
    public String title, link;
    public TabDisplay display;
    private TabList list;
    private ArrayList<String> links;

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("tab clicked");
        list.tabClicked(this);
    }

    public String getPreviousLink() {
        link = links.get(links.size() - 2);
        links.remove(links.size() - 1);
        return link;
    }

    public void setNewLink(String url) {
        if (!url.equals(link)) {
            links.add(url);
            link = url;
        }
    }

    public boolean hasPreviousLinks() {
        return links.size() > 1;
    }

    public void setSelected(boolean selected) {
        setBackground(selected ? Maxium.THEME.Selected : Maxium.THEME.Background);
    }

    public Tab(String Title, String Link, TabDisplay Display, TabList List) {
        super(Title);
        setSelected(false);
        setForeground(Maxium.THEME.Icon);
        addActionListener(this);

        list = List;
        title = Title;
        link = Link;
        display = Display;
        links = new ArrayList<>();
        links.add(Link);
    }
}