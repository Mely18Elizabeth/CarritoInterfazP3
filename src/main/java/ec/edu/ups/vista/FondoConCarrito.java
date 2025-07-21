package ec.edu.ups.vista;

import javax.swing.*;
import java.awt.*;

public class FondoConCarrito extends JDesktopPane {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D fondo = (Graphics2D) g;

        fondo.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        fondo.setColor(new Color(255, 204, 204));
        fondo.fillRect(0, 0, getWidth(), getHeight());

        int ejeX = getWidth() / 2;
        int ejeY = getHeight() / 2;

        drawBag(fondo, ejeX - 140, ejeY - 100, 80, 100, new Color(255, 230, 230));
        drawBag(fondo, ejeX - 30, ejeY - 120, 90, 110, new Color(255, 220, 220));
        drawBag(fondo, ejeX + 90, ejeY - 90, 75, 95, new Color(255, 240, 240));
    }

    private void drawBag(Graphics2D g2, int x, int y, int width, int height, Color fillColor) {
        g2.setColor(fillColor);
        g2.fillRoundRect(x, y, width, height, 10, 10);

        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(x, y, width, height, 10, 10);

        int dibuj1 = 25;
        int dibuj2 = 25;
        g2.drawArc(x + 10, y - 20, dibuj1, dibuj2, 0, 180);
        g2.drawArc(x + width - dibuj1 - 10, y - 20, dibuj1, dibuj2, 0, 180);

        g2.fillOval(x + 18, y, 5, 5);
        g2.fillOval(x + width - 23, y, 5, 5);
    }
}
