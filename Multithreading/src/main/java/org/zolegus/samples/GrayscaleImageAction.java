package org.zolegus.samples;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class GrayscaleImageAction extends RecursiveAction {
    private static final long serialVersionUID = 1L;
    private int row;
    private BufferedImage bufferedImage;

    public GrayscaleImageAction(int row, BufferedImage bufferedImage) {
        this.row = row;
        this.bufferedImage = bufferedImage;
    }

    @Override
    protected void compute() {
        for (int column = 0; column < bufferedImage.getWidth(); column++) {
            int rgb = bufferedImage.getRGB(column, row);
            int r = (rgb >> 16) & 0xFF;
            int g = (rgb >> 8) & 0xFF;
            int b = (rgb & 0xFF);
            int gray = (int) (0.2126 * (float) r + 0.7152 * (float) g + 0.0722 * (float) b);
            gray = (gray << 16) + (gray << 8) + gray;
            bufferedImage.setRGB(column, row, gray);
        }
    }

    public static void main(String[] args) throws IOException {
        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        BufferedImage bufferedImage = ImageIO.read(new File(args[0]));
        for (int row = 0; row < bufferedImage.getHeight(); row++) {
            GrayscaleImageAction action = new GrayscaleImageAction(row, bufferedImage);
            pool.execute(action);
        }
        pool.shutdown();
        ImageIO.write(bufferedImage, "jpg", new File(args[1]));
    }
}
