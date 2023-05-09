import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;
import java.awt.*;

public class TabDisplay extends JPanel implements HyperlinkListener {
    public JEditorPane pane;
    private Maxium main;
    public TabDisplay(String link, Maxium mainObject) {
        super();
        setBackground(new Color(155, 164, 181));
        this.main = mainObject;
        pane = new JEditorPane();
        pane.setEditable(false);
        pane.addHyperlinkListener(this);

        add(new JScrollPane(pane));
        setPreferredSize(new Dimension(Maxium.DISPLAY_WIDTH, Maxium.DISPLAY_HEIGHT));
        main.addToDisplay(this);
    }
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
