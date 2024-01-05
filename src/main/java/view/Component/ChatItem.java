// Reference: https://github.com/DJ-Raven/java-swing-ui-chat-messenger
package view.Component;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import net.miginfocom.swing.MigLayout;

public class ChatItem extends JComponent {
    private static final DateFormat DF = new SimpleDateFormat("hh:mm dd/MM/yyyy");
    public final boolean is_left;
    public String msg;
    public Date date;
    public String username;

    public ChatItem(boolean is_left, String text, Date date, String username) {
        this.is_left = is_left;
        this.msg = text;
        this.date = date;
        this.username = username;
        init();
    }

    private void init() {
        initBox();
    }

    private void initBox() {
        String rightToLeft = is_left ? "": ",rtl";
        setLayout(new MigLayout("insets 5" + rightToLeft, "[0]0[]", "[top]"));
        JTextPane text = new JTextPane();
        text.setEditorKit(new AutoWrapText());
        text.setFont(new Font(text.getFont().getName(), Font.BOLD, 18));
        text.setText(this.msg);
        text.setBackground(new Color(0, 0, 0, 0));
        text.setForeground(new Color(255, 255, 255));
        text.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        text.setOpaque(false);
        text.setEditable(false);
        JLabel labelDate = new JLabel(this.username + " | " + DF.format(this.date));
        labelDate.setForeground(new Color(127, 127, 127));
        add(new JLabel(), "height 40,width 40");
        add(text, "gapy 20, wrap");
        add(labelDate, "gapx 20,span 2");
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int width = getWidth();
        int height = getHeight();
        Area area = new Area(new RoundRectangle2D.Double(0, 25, width, height - 25 - 16 - 10, 5, 5));
        g2.setPaint(new GradientPaint(0, 0, Color.decode("#1CB5E0"), width, 0, Color.decode("#000046")));
        g2.fill(area);
        g2.dispose();
        super.paintComponent(g);
    }
}