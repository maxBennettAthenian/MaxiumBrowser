//SOURCES:
// SwingHTMLBrowser - https://gist.github.com/masnagam/ec6fd335b75bbe87aea7
// CardLayout Tutorial - https://docs.oracle.com/javase/tutorial/uiswing/layout/card.html
// Color Palette - https://colorhunt.co/palette/f1f6f9394867212a3e9ba4b5

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Arrays;

interface ColorTheme {
    Color Icon = Color.WHITE;
    Color Accent = new Color(155, 164, 181);
    Color Foreground = new Color(241, 246, 249);
    Color Selected = new Color(57, 72, 103);
    Color Background = new Color(33, 42, 62);
}

public class Maxium extends JFrame implements ActionListener {
    static final int BROWSER_HEIGHT = 60;
    static final int DISPLAY_WIDTH = 800;
    static final int DISPLAY_HEIGHT = 450;

    static final ColorTheme THEME = new ColorTheme() {};

    private TabList tabs;
    private JPanel browserPanel, functionPanel, displayPanel;
    private Tab currentTab;
    private JTextField addressBar;
    private JButton previous, refresh, closeButton;

    public void setAddress(String url) {
        addressBar.setText(url);
        currentTab.setNewLink(url);
    }

    public void addressChanged(String url) {
        try {
            currentTab.display.getPane().setPage(url);
            setAddress(url);
            currentTab.display.updateUI();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Address") &&
                !currentTab.link.equals(((JTextField) e.getSource()).getText())) {
            addressChanged(addressBar.getText());
            setTab(currentTab);
        } else if (e.getActionCommand().equals("◀") && currentTab.hasPreviousLinks()) {
            System.out.println("previous link clicked");
            addressChanged(currentTab.getPreviousLink());
            setTab(currentTab); //update if there are previous links
        } else if (e.getActionCommand().equals("🔄️")) {
            System.out.println("refresh");
            setTab(currentTab);
        } else if (e.getActionCommand().equals("X")) {
            System.out.println("close tab");
            setTab(tabs.closeTab(currentTab));
        }
    }

    public void addToDisplay(TabDisplay object) {
        displayPanel.getLayout().addLayoutComponent(object.getId(), object);
        displayPanel.add(object);
        object.setVisible(true);
    }

    public void removeDisplay(TabDisplay object) {
        displayPanel.getLayout().removeLayoutComponent(object);
        displayPanel.remove(object);
    }

    public void setTab(Tab t) {
        if (t == null) {
            return;
        }

        ((CardLayout) displayPanel.getLayout()).show(displayPanel, t.display.getId());
        if (currentTab != null) {
            currentTab.setSelected(false);
        }

        currentTab = t;
        t.setSelected(true);
        t.display.updateUI();
        addressChanged(t.link);

        //check if previous links
        previous.setForeground(t.hasPreviousLinks() ? THEME.Icon : THEME.Accent);
    }

    public void closeWindow() {
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    public Maxium(boolean loadTabs) {
        super("Maxium");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(DISPLAY_WIDTH, BROWSER_HEIGHT + DISPLAY_HEIGHT));

        //html display
        displayPanel = new JPanel();
        displayPanel.setPreferredSize(new Dimension(DISPLAY_WIDTH - 20, DISPLAY_HEIGHT - 20));
        displayPanel.setLayout(new CardLayout());

        //tabs
        browserPanel = new JPanel();
        browserPanel.setPreferredSize(new Dimension(DISPLAY_WIDTH - 20, BROWSER_HEIGHT));
        browserPanel.setBackground(THEME.Background);

        tabs = new TabList(this);
        tabs.setBackground(THEME.Background);

        //address bar + buttons
        functionPanel = new JPanel();
        functionPanel.setBackground(THEME.Selected);
        functionPanel.setLayout(new BoxLayout(functionPanel, BoxLayout.X_AXIS));
        functionPanel.setPreferredSize(new Dimension(DISPLAY_WIDTH - 20, BROWSER_HEIGHT / 2));

//        Dimension btnSize = new Dimension(BROWSER_HEIGHT / 2, BROWSER_HEIGHT / 2);
        previous = new JButton("◀");
        previous.setBackground(THEME.Selected);
        previous.setForeground(THEME.Icon);
        previous.addActionListener(this);
//        previous.setPreferredSize(btnSize);

        refresh = new JButton("🔄️");
        refresh.setBackground(THEME.Selected);
        refresh.setForeground(THEME.Icon);
        refresh.addActionListener(this);
//        refresh.setPreferredSize(btnSize);

        closeButton = new JButton("X");
        closeButton.setBackground(THEME.Selected);
        closeButton.setForeground(THEME.Icon);
        closeButton.addActionListener(this);

        addressBar = new JTextField();
        addressBar.setActionCommand("Address");
        addressBar.setBackground(THEME.Selected);
        addressBar.setForeground(THEME.Icon);
        addressBar.addActionListener(this);
        addressBar.setBorder(null);
        addressBar.setPreferredSize(new Dimension(
                DISPLAY_WIDTH - 10,BROWSER_HEIGHT / 2 - 10));

        functionPanel.add(previous);
        functionPanel.add(refresh);
        functionPanel.add(closeButton);
        functionPanel.add(addressBar);

        browserPanel.add(tabs, BorderLayout.NORTH);
        browserPanel.add(functionPanel, BorderLayout.SOUTH);

        add(browserPanel, BorderLayout.NORTH);
        add(displayPanel, BorderLayout.CENTER);

        if (loadTabs) {
//            tabs.openPreviousTabs();
        } else {
//            tabs.openTab();
//            tabs.openTab();
            setTab(tabs.openTab());
        }
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        new Maxium(false);
    }
}