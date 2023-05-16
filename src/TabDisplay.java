import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;
import java.awt.*;

public class TabDisplay extends JPanel implements HyperlinkListener {
    private JEditorPane pane;
    private Maxium main;
    private final String id;

    public TabDisplay(Maxium mainObject, int id) {
        super();

        this.id = Integer.toString(id);
        this.main = mainObject;

        setBackground(Maxium.THEME.Accent);
        pane = new JEditorPane();
        pane.setEditable(false);
        pane.addHyperlinkListener(this);

        Dimension size = new Dimension(Maxium.DISPLAY_WIDTH - 20, Maxium.DISPLAY_HEIGHT - 20);

        pane.setPreferredSize(size);
        add(new JScrollPane(pane));
        setPreferredSize(size);
        main.addToDisplay(this);
    }

    public JEditorPane getPane() { return pane; }

    public String getId() { return id; }

    public void hyperlinkUpdate(HyperlinkEvent evt) {
        System.out.println("hyper link update");
        if (evt.getEventType() != HyperlinkEvent.EventType.ACTIVATED) {
            return;
        }
        if (evt instanceof HTMLFrameHyperlinkEvent) {
            HTMLDocument doc = (HTMLDocument)pane.getDocument();
            doc.processHTMLFrameHyperlinkEvent((HTMLFrameHyperlinkEvent)evt);
        } else {
            String url = evt.getURL().toString();
            try {
                pane.setPage(url);
                main.setAddress(url);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }
}
