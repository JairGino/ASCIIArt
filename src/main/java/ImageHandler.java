import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

//  https://docs.oracle.com/javase/tutorial/2d/images/index.html
//  https://docs.oracle.com/javase/8/docs/api/constant-values.html#java.awt.image.BufferedImage.TYPE_3BYTE_BGR

public abstract class ImageHandler {
    protected BufferedImage image = null;
    private static final String[] imageTypes = {"TYPE_CUSTOM", "TYPE_INT_RGB",
            "TYPE_INT_ARGB", "TYPE_INT_ARGB_PRE", "TYPE_INT_BGR",
            "TYPE_3BYTE_BGR", "TYPE_4BYTE_ABGR", "TYPE_4BYTE_ABGR_PRE",
            "TYPE_USHORT_565_RGB", "TYPE_USHORT_555_RGB", "TYPE_BYTE_GRAY",
            "TYPE_USHORT_GRAY", "TYPE_BYTE_BINARY", "TYPE_BYTE_INDEXED"};

    public ImageHandler() {}

    public Boolean loadImage(String filePath) {
        try {
//            this.image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
            File sampleFile = new File(filePath);
            this.image = ImageIO.read(sampleFile);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean checkImageLoaded() {
        return (this.image == null) ? false : true;
    }

    public void viewImageProperties() {
//        System.out.println(this.image);

        System.out.printf("\nProperties: ");
        System.out.printf("\n\tProperty names recognized: %s", this.image.getPropertyNames());
        System.out.printf("\n\tHeight: %d", image.getHeight());
        System.out.printf("\n\tWidth: %d", image.getWidth());
        System.out.printf("\n\tMinTileX: %d", image.getMinTileX());
        System.out.printf("\n\tMinTileY: %d", image.getMinTileY());
        System.out.printf("\n\tMinX: %d", image.getMinX());
        System.out.printf("\n\tMinY: %d", image.getMinY());
        System.out.printf("\n\tNumXTiles: %d", image.getNumXTiles());
        System.out.printf("\n\tNumYTiles: %d", image.getNumYTiles());
        System.out.printf("\n\tRGB(TYPE_INT_ARGB) x=0, y=0: %d", image.getRGB(0,0));
        System.out.printf("\n\tTileHeight: %d", image.getTileHeight());
        System.out.printf("\n\tTileWidth: %d", image.getTileWidth());
        System.out.printf("\n\tType: %d", image.getType());
        System.out.printf("\n\tType: %s", imageTypes[image.getType()]);
    }

    public BufferedImage getImage() {
        return image;
    }
}
