import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TabList extends JPanel {
    private ArrayList<Tab> list;
    private Maxium main;

    public int getIndex(Tab tab) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(tab)) {
                return i;
            }
        }
        return -1;
    }

    public void tabClicked(Tab tab) {
        main.setTab(tab);
    }

    public Tab openTab() { return openTab("http://google.com"); }

    public Tab openTab(String link) {
        Tab newTab = new Tab("New Tab", link, new TabDisplay(link, main), this);
        list.add(newTab);
        add(newTab);
        updateUI();
        return newTab;
    }

    public void closeTab(Tab t) {
        int index = getIndex(t);
        if (index != -1) {
            if (list.size() == 0) {
                main.setTab(openTab());
            }
            list.remove(index);
            t.removeAll();
        }
    }

    public void openPreviousTabs() {
        //read a text file or something
        System.out.println("openPreviousTabs");
    }

    public TabList(Maxium mainObject, boolean loadTabs) {
        super();
        setLayout(new CardLayout(10, 0));
        setPreferredSize(new Dimension(Maxium.DISPLAY_WIDTH, Maxium.BROWSER_HEIGHT / 2));
        main = mainObject;
        list = new ArrayList<>();

        if (loadTabs) {
            openPreviousTabs();
        }
    }
}
