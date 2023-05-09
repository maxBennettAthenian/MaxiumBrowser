//SOURCES:
// SwingHTMLBrowser - https://gist.github.com/masnagam/ec6fd335b75bbe87aea7
// CardLayout Tutorial - https://docs.oracle.com/javase/tutorial/uiswing/layout/card.html
// Color Palette - https://colorhunt.co/palette/f1f6f9394867212a3e9ba4b5

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

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

    public void setAddress(String url) {
        addressBar.setText(url);
        currentTab.link = url;
    }

    public void addressChanged(String url) {
        try {
            setAddress(url);
            currentTab.display.getPane().setPage(url);
            currentTab.display.updateUI();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!currentTab.link.equals(e.getActionCommand())) {

            addressChanged(addressBar.getText());
        }
    }

    public void addToDisplay(TabDisplay object) {
//        displayPanel.add(object, BorderLayout.CENTER);
        object.setVisible(true);
    }

    public void setTab(Tab t) {
        if (currentTab != null) {
            displayPanel.remove(currentTab.display);
//            currentTab.display.setVisible(false);
        }

        currentTab = t;
        addressBar.setText(t.link);
        displayPanel.add(t.display, BorderLayout.CENTER);
//        t.display.setVisible(true);
        displayPanel.updateUI();
    }

    public Maxium(boolean loadTabs) {
        super("Maxium");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(DISPLAY_WIDTH, BROWSER_HEIGHT + DISPLAY_HEIGHT));

        //html display
        displayPanel = new JPanel();
        displayPanel.setSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);
        displayPanel.setLayout(new BorderLayout());

        //tabs
        browserPanel = new JPanel();
        browserPanel.setPreferredSize(new Dimension(DISPLAY_WIDTH, BROWSER_HEIGHT));
        browserPanel.setBackground(THEME.Background);

        tabs = new TabList(this, loadTabs);
        tabs.setBackground(THEME.Background);

        //address bar + buttons
        functionPanel = new JPanel();
        functionPanel.setLayout(new BoxLayout(functionPanel, BoxLayout.X_AXIS));
        functionPanel.setPreferredSize(new Dimension(DISPLAY_WIDTH - 10, BROWSER_HEIGHT / 2));

        addressBar = new JTextField();
        addressBar.setBackground(THEME.Selected);
        addressBar.setForeground(THEME.Icon);
        addressBar.addActionListener(this);
        addressBar.setBorder(null);
        addressBar.setPreferredSize(new Dimension(
                DISPLAY_WIDTH - 100,BROWSER_HEIGHT / 2 - 10));
//        SwingHTMLBrowser browser = new SwingHTMLBrowser();
//        browser.setVisible(true);

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