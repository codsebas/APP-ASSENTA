package com.umg.biometrics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import com.digitalpersona.uareu.Fid;
import com.digitalpersona.uareu.Fid.Fiv;

public class ImagePanel extends JPanel {
    private static final long serialVersionUID = 5L;
    private BufferedImage m_image;

    public void showImage(Fid image) {
        if (image != null && image.getViews().length > 0) {
            Fiv view = image.getViews()[0];
            m_image = new BufferedImage(view.getWidth(), view.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
            m_image.getRaster().setDataElements(0, 0, view.getWidth(), view.getHeight(), view.getImageData());
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();

        // Fondo blanco
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        if (m_image != null) {
            // Activar alta calidad de escalado
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

            int panelWidth = getWidth();
            int panelHeight = getHeight();
            int imageWidth = m_image.getWidth();
            int imageHeight = m_image.getHeight();

            // Calcular la escala manteniendo la proporción
            double scale = Math.min(
                    (panelWidth * 0.9) / imageWidth,  // 90% del ancho disponible
                    (panelHeight * 0.9) / imageHeight // 90% del alto disponible
            );

            int scaledWidth = (int) (imageWidth * scale);
            int scaledHeight = (int) (imageHeight * scale);

            int x = (panelWidth - scaledWidth) / 2;
            int y = (panelHeight - scaledHeight) / 2;

            // Dibujar la imagen escalada
            g2d.drawImage(m_image, x, y, scaledWidth, scaledHeight, null);
        } else {
            // Mostrar texto si no hay imagen
            g2d.setColor(Color.GRAY);
            g2d.drawString("Sin imagen", getWidth() / 2 - 30, getHeight() / 2);
        }

        // Borde ligero
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);

        g2d.dispose();
    }
}
