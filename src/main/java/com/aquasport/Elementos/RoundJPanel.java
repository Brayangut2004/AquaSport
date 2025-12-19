package com.aquasport.Elementos;

import java.awt.*;
import java.awt.geom.Path2D; 
import javax.swing.JPanel;

public class RoundJPanel extends JPanel {

    private int cornerRadius = 25;

    public RoundJPanel() {
        super();
        setOpaque(false); 
    }

    public RoundJPanel(int radius) {
        this();
        this.cornerRadius = radius;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int w = getWidth() - 1;
        int h = getHeight() - 1;
        int r = cornerRadius;

        Path2D.Float path = new Path2D.Float();
        
        path.moveTo(0, h);
        path.lineTo(0, r);
        path.quadTo(0, 0, r, 0);
        path.lineTo(w - r, 0);
        path.quadTo(w, 0, w, r);
        path.lineTo(w, h);
        path.closePath();
        
        g2.setColor(getBackground());
        g2.fill(path);
        
        g2.dispose();
        
        super.paintComponent(g); 
    }
}