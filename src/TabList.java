import javax.print.DocFlavor;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

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
        Tab newTab = new Tab("New Tab", link, new TabDisplay(main, nextTabId), this);
        nextTabId++;
        list.add(newTab);
        remove(openTabBtn);
        add(newTab);
        add(openTabBtn);
        updateUI();
        return newTab;
    }

    public Tab openTab(String[] history) {
        Tab newTab = new Tab(history, new TabDisplay(main, nextTabId), this);
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

    public Scanner getSavedTabs() {
        try {
            File saved = new File("savedTabs.txt");
            return new Scanner(saved);
        } catch (FileNotFoundException ignored) {
            return new Scanner("");
        }
    }

    public void openPreviousTabs() {
        System.out.println("openPreviousTabs");
        //read a text file or something
        Scanner scan = getSavedTabs();
        Tab lastTab = null;
        while (scan.hasNextLine()) {
            String data = scan.nextLine();
            String[] history = data.split("\t");
            lastTab = openTab(history);
        }
        if (lastTab != null) {
            main.setTab(lastTab);
        }
    }

    public void saveTabs() {
        System.out.println("saveTabs");
        //convert tabs to string and write to file "savedTabs.txt"
        StringBuilder newText = new StringBuilder();
        for (Tab t : list) {
            newText.append(t.encode()).append("\n");
        }
        System.out.println("newText len: " + newText.toString().length());

        if (newText.toString().equals("http://google.com\t\n")) {
            System.out.println("saveTabs cancelled since new tab is the only tab");
            return;
        }

        try {
            FileWriter saved = new FileWriter("savedTabs.txt");
            saved.write(newText.toString());
            saved.close();
            System.out.println("wrote to existing file");
        } catch (FileNotFoundException e) {
            try {
                new File("savedTabs.txt");
                FileWriter saved = new FileWriter("savedTabs.txt");
                saved.write(newText.toString());
                saved.close();
                System.out.println("created and wrote to file");
            } catch (IOException ignored) {
                System.out.println("second ignore");
                //System.exit()??
            }
        } catch (IOException ignored) {
            System.out.println("first ignore");
        }
    }

    public void actionPerformed(ActionEvent e) {
        javax.swing.SwingUtilities.invokeLater(() -> main.setTab(openTab()));
    }

    public TabList(Maxium mainObject) {
        super();
        LayoutManager layout = new FlowLayout();//10, 0);
        setLayout(layout);
        setPreferredSize(new Dimension(Maxium.DISPLAY_WIDTH, Maxium.BROWSER_HEIGHT / 2));
        main = mainObject;
        list = new ArrayList<>();

        openTabBtn = new JButton("+");
        openTabBtn.setBackground(Maxium.THEME.Background);
        openTabBtn.setForeground(Maxium.THEME.Icon);
        openTabBtn.addActionListener(this);
        add(openTabBtn);
    }
}
