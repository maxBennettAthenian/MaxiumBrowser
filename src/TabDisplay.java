import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;
import java.awt.*;

public class TabDisplay extends JPanel implements HyperlinkListener {
    private JEditorPane pane;
    private Maxium main;
    public TabDisplay(String link, Maxium mainObject) {
        super();
        setBackground(Maxium.THEME.Accent);
        this.main = mainObject;
        pane = new JEditorPane();
        pane.setEditable(false);
        pane.addHyperlinkListener(this);

        Dimension size = new Dimension(Maxium.DISPLAY_WIDTH, Maxium.DISPLAY_HEIGHT);

        pane.setPreferredSize(size);
        add(new JScrollPane(pane));
        setPreferredSize(size);
        main.addToDisplay(this);
    }

    public JEditorPane getPane() { return pane; }

    public void hyperlinkUpdate(HyperlinkEvent evt) {
        if (evt.getEventType() != HyperlinkEvent.EventType.ACTIVATED) {
            return;
        }
//        JEditorPane srcPane = (JEditorPane)evt.getSource();
        if (evt instanceof HTMLFrameHyperlinkEvent) {
            HTMLDocument doc = (HTMLDocument)pane.getDocument();
            doc.processHTMLFrameHyperlinkEvent((HTMLFrameHyperlinkEvent)evt);
        } else {
            String url = evt.getURL().toString();
            main.setAddress(url);
            try {
                pane.setPage(url);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }
}
