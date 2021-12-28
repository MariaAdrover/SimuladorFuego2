/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simuladorfuego2;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;

public class Fuego extends BufferedImage {

    private Color[] colores;
    private int[] alpha = new int[256];
    private int[][] temperatura;
    private byte[] datos;

    public Fuego(int width, int height, int colorModel) {
        super(width, height, colorModel);

        this.colores = new Color[256];
        this.temperatura = new int[this.getHeight()][this.getWidth()];

        this.getBytesArray();
        this.crearColores();
    }

    public void nextFrame() {
        this.crearBrasas();
        this.actualizarTemperatura();
        this.setColor();
    }

    private void actualizarTemperatura() {
        for (int fila = temperatura.length - 2; fila >= 0; fila--) {
            for (int col = 1; col < temperatura[0].length - 1; col++) {
                this.temperatura[fila][col] = (int) (((this.temperatura[fila][col]
                        + this.temperatura[fila + 1][col - 1]
                        + this.temperatura[fila + 1][col]
                        + this.temperatura[fila + 1][col + 1])
                        / 3.90)
                        - 1.17);

                // Corrección
                if (this.temperatura[fila][col] > 255) {
                    this.temperatura[fila][col] = 255;
                }
                if (this.temperatura[fila][col] < 0) {
                    this.temperatura[fila][col] = 0;
                }
            }
        }
    }

    private void crearBrasas() {
        // Apagamos todas las brasas        
        for (int col = 0; col < temperatura[0].length; col++) {
            this.temperatura[this.temperatura.length - 1][col] = 0;
        }

        // Hay un 20 % de posibilidades de que se generen brasas        
        for (int col = 1; col < (this.temperatura[0].length - 1); col++) {
            if (Math.random() > 0.80) {
                this.temperatura[this.temperatura.length - 1][col] = 255;
            }
        }
    }

    private void crearColores() {
        // Generar la paleta de colores
        for (int x = 0; x < 256; x++) {
            float saturation = x > 96 ? 0.0F : 1.0F - x / 128.0F;
            this.colores[x] = new Color(Color.HSBtoRGB(x / 576.0F, saturation, Math.min(1.0F, x / 48.0F)));
            System.out.println("index=" + x + "  r:" + this.colores[x].getRed() + "  g:" + this.colores[x].getGreen() + "  b:" + this.colores[x].getBlue());
        }

        // Generar la paleta de transparencia
        // MODIFICAR
        // 255 opaco?
        // 0 transparente?
        
        //alpha[0] = 0; // k kk no va esto y no se x k
        
        for (int i = 0; i < 96; i++) {
            //alpha[i] = i * 4;

            alpha[i] = 128;
            if (alpha[i] > 255) {
                alpha[i] = 0;
            }
        }

        for (int i = 96; i < 256; i++) {
            alpha[i] = 255;
        }
    }

    private void getBytesArray() {
        Raster raster = this.getRaster();
        this.datos = ((DataBufferByte) raster.getDataBuffer()).getData();
    }

    private void setColor() {
        for (int i = 0; i < this.temperatura.length; i++) {
            for (int j = 0; j < temperatura[0].length; j++) {
                int index = this.temperatura[i][j];
                Color color = this.colores[index];

                //int a;
                int a = this.alpha[index];
                int b = color.getBlue();
                int g = color.getGreen();
                int r = color.getRed();
                /*
                if (index < 200) {
                    a = 100;
                } else {
                    a = 255; // transparente
                }
                 */
                this.datos[(i * this.getWidth() * 4) + j * 4] = (byte) a;
                this.datos[((i * this.getWidth() * 4) + j * 4) + 1] = (byte) b;
                this.datos[((i * this.getWidth() * 4) + j * 4) + 2] = (byte) g;
                this.datos[((i * this.getWidth() * 4) + j * 4) + 3] = (byte) r;
            }
        }
    }
}
