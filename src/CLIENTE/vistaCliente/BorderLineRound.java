package CLIENTE.vistaCliente;

import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;

public class BorderLineRound extends AbstractBorder {

    private Color lineColor;
    private boolean roundedCorner;
    //me crea bordes suaves
    RenderingHints antialiasing = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    public BorderLineRound(Color lineColor, boolean roundedCorner){
        this.lineColor = lineColor;
        this.roundedCorner = roundedCorner;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g;
        Shape outer;
        Shape inner;
        //lineas
        if(roundedCorner){
            int offs = 1;
            int size = offs + offs;
            float arc = .2f * offs;
            g2d.setColor(lineColor);
            outer = new RoundRectangle2D.Float(x+1, y+1, width - 2, height-2, offs* 30, offs* height);
            inner = new RoundRectangle2D.Float(x+offs, y+offs-2, width - size+4, height-size+4, arc, arc);
            Path2D path = new Path2D.Float(Path2D.WIND_EVEN_ODD);
            g2d.addRenderingHints(antialiasing);
            path.append(outer, false);
            path.append(inner,false);
            g2d.fill(path);
        }

        //elimina el borde restante
        Color oldColor = c.getParent().getBackground();
        g2d.setColor(oldColor);

        int offs = 1;
        int size = offs+ offs;
        float arc = .2f * offs;
        outer = new RoundRectangle2D.Float(x, y, width, height, offs*30, offs* height);
        inner = new RoundRectangle2D.Float(x+offs-2, y+offs-2, width - size+4, height-size+4, arc, arc);
        Path2D path = new Path2D.Float(Path2D.WIND_EVEN_ODD);
        g2d.addRenderingHints(antialiasing);
        path.append(outer, false);
        path.append(inner,false);
        g2d.fill(path);
    }
}
