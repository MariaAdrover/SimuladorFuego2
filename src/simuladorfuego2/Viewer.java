package simuladorfuego2;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.TYPE_4BYTE_ABGR;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;

public class Viewer extends Canvas {

    private Fuego fuego;
    private BufferedImage background;
    private Image bufferedImage;
    private Graphics dbg;

    public Viewer() {
        this.loadBackgroundImg();
        this.setSize(this.background.getWidth(), this.background.getHeight());
        this.crearFuego();
    }

    private void loadBackgroundImg() {
        try {
            this.background = ImageIO.read(new File("fail.jpg"));
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }

    private void crearFuego() {
        this.fuego = new Fuego(this.getWidth(), this.getHeight(), TYPE_4BYTE_ABGR);
    }

    public void start() {

        try {
            while (true) {
                this.fuego.nextFrame();
                this.paint(this.getGraphics());
                TimeUnit.MILLISECONDS.sleep(20);
            }
        } catch (InterruptedException e) {

        }
    }

    @Override
    public void paint(Graphics g) {
        this.paintComponent();
        g.drawImage(this.bufferedImage, 0, 0, null);

    }

    public void paintComponent() {
        this.bufferedImage = createImage(getWidth(), getHeight());
        this.dbg = this.bufferedImage.getGraphics();
        dbg.drawImage(this.background, 0, 0, this);
        dbg.drawImage(this.fuego, 0, 0, null);
    }
}
