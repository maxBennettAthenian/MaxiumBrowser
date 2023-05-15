import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TabList extends JPanel implements ActionListener {
    private ArrayList<Tab> list;
    private Maxium main;
    private JButton openTabBtn;
    private static int nextTabId = 1;

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
        Tab newTab = new Tab("New Tab", link, new TabDisplay(link, main, nextTabId), this);
        nextTabId++;
        list.add(newTab);
        remove(openTabBtn);
        add(newTab);
        add(openTabBtn);
        updateUI();
        return newTab;
    }

    public Tab closeTab(Tab t) {
        int index = getIndex(t);
        if (index != -1) {
            System.out.println("found tab to close");
            main.removeDisplay(t.display);
            list.remove(index);
            t.removeAll();
            remove(t);
            updateUI();
        } else System.out.println("COULD NOT FIND TAB TO CLOSE");

        if (list.size() > 0) {
            return list.get(0);
        }
        main.closeWindow();
        return null;
    }

    public void openPreviousTabs() {
        //read a text file or something
        System.out.println("openPreviousTabs");
    }

    public void actionPerformed(ActionEvent e) {
        main.setTab(openTab());//new tab button clicked
    }

    public TabList(Maxium mainObject) {
        super();
        LayoutManager layout = new FlowLayout();//10, 0);
        setLayout(layout);
        setPreferredSize(new Dimension(Maxium.DISPLAY_WIDTH, Maxium.BROWSER_HEIGHT / 2));
        main = mainObject;
        list = new ArrayList<>();

        openTabBtn = new JButton("+");
        openTabBtn.addActionListener(this);
        add(openTabBtn);
    }
}
