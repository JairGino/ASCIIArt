import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageConverter extends ImageHandler {
    private int width;          //Original Image Width
    private int height;         //Original Image Height

    private String asciiChars = " .:-=+*#%@";
//    private String asciiChars = " _.,-=+:;abc!?0123456789$W#@Ñ";
    private int asciiWidth;    //AscII Image Width
    private int asciiHeight;   //AscII Image Height
    private Boolean addBorderToImage = true;

    public ImageConverter() {
        super();
    }

    @Override
    public Boolean loadImage(String filePath) {
        Boolean imageLoaded = super.loadImage(filePath);
        if (imageLoaded) {
            this.width = super.image.getWidth();
            this.height = super.image.getHeight();
            this.asciiWidth = 120;
            this.asciiHeight = calculateAsciiHeight(asciiWidth);
        }
        return imageLoaded;
    }

    public String convertToASCFirstMethod() {
        if (super.image == null) {
            System.out.println("Image not loaded.");
            return null;
        }

        StringBuilder asciiArt = new StringBuilder();

        // Calculate the scaling factors for width and height
        double widthRatio = (double) width / asciiWidth;
        double heightRatio = (double) height / asciiHeight;

        for (int y = 0; y < asciiHeight; y++) {
            for (int x = 0; x < asciiWidth; x++) {
                char asciiChar = convertPixelToAscii(x, y, widthRatio, heightRatio);
                asciiArt.append(asciiChar);

            }
            asciiArt.append("\n");
        }

        String asciiArtResult = asciiArt.toString();
        return (this.addBorderToImage) ? drawBorder(asciiArtResult) : asciiArtResult;
    }

    public String convertToASCSecondMethod() {
        if (super.image == null) {
            System.out.println("Image not loaded.");
            return null;
        }
        this.asciiWidth = this.asciiWidth*2; //change result ASCII image width

        StringBuilder asciiArt = new StringBuilder();

        // Calculate the scaling factors for width and height
        double widthRatio = (double) width / asciiWidth;
        double heightRatio = (double) height / asciiHeight;


        for (int y = 0; y < asciiHeight; y++) {
            for (int x = 0; x < asciiWidth; x++) {
                char asciiChar = convertPixelToAscii(x, y, widthRatio, heightRatio);
                asciiArt.append(asciiChar);
            }
            asciiArt.append("\n");
        }

        String asciiArtResult = asciiArt.toString();
        asciiArtResult = (this.addBorderToImage) ? drawBorder(asciiArtResult) : asciiArtResult;
        this.asciiWidth = this.asciiWidth/2; //change result ASCII image width
        return asciiArtResult;
    }

    public String drawBorder(String asciiArt) {
        StringBuilder asciiArtWithBorder = new StringBuilder();

        asciiArtWithBorder.append("┌");
        asciiArtWithBorder.append("─".repeat(asciiWidth));
        asciiArtWithBorder.append("┐\n");

        String[] lines = asciiArt.split("\n");
        for (String line : lines) {
            asciiArtWithBorder.append("│");
            asciiArtWithBorder.append(line);
            asciiArtWithBorder.append("│\n");
        }

        asciiArtWithBorder.append("└");
        asciiArtWithBorder.append("─".repeat(asciiWidth));
        asciiArtWithBorder.append("┘\n");
        return asciiArtWithBorder.toString();
    }

    public void changeAsciiImageHeight(int asciiHeight) {
        this.asciiHeight = asciiHeight;
        this.asciiWidth = calculateAsciiWidth(asciiHeight);
    }

    public void changeAsciiImageWidth(int asciiWidth) {
        this.asciiWidth = asciiWidth;
        this.asciiHeight = calculateAsciiHeight(asciiWidth);
    }


    private int calculateAsciiHeight(int asciiWidth) {
        int width = super.image.getWidth();
        int height = super.image.getHeight();
        return (int) ((double) height / width * asciiWidth);
    }

    private int calculateAsciiWidth(int asciiHeight) {
        int width = super.image.getWidth();
        int height = super.image.getHeight();
        return (int) ((double) width / height * asciiHeight);
    }

    private char convertPixelToAscii(int x, int y, double widthRatio, double heightRatio) {

        // Calculate the corresponding pixel position in the original image
        int pixelX = (int) (x * widthRatio);
        int pixelY = (int) (y * heightRatio);

        // Get the color of the pixel (TYPE_INT_ARGB)
        int rgb = super.image.getRGB(pixelX, pixelY);

        int grayscale = calculateGrayscale(rgb);

        // Map the grayscale value to an ASCII character
//        double grayscaleFactor = (double) (this.asciiChars.length() - 1) / 255;
        int index = (int) (grayscale * (this.asciiChars.length() - 1) / 255);
        return this.asciiChars.charAt(index);
    }

    // Calculate the grayscale value (average of RGB values)
    private int calculateGrayscale(int rgb) {
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;
        return (int) (0.3*red + 0.59*green + 0.11*blue);
    }
}
