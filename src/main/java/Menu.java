import java.io.IOException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    private static Scanner input = new Scanner(System.in);
    private static final String title = "ASCII Art";
    private static final String[] options = {
            "1 - Carregar a imagem",
            "2 - Mostrar propriedades da imagem",
            "3 - Converter imagem (método 1)",
            "4 - Converter imagem (método 2)",
            "5 - Informação sobre os métodos de conversão",
            "6 - Mudar a largura da imagem resultante",
            "7 - Mudar a altura da imagem resultante"
    };

    private static final int initialSpaces = 6;
    private static final int totalSpaces = Math.max(Arrays.stream(options)
           .mapToInt(String::length)
           .max()
           .orElse(0)+initialSpaces, 60);

    public static void main(String[] args) {
        String titleFormat = ("\n%" + (totalSpaces - title.length()) / 2 + "s%s");
        String optionFormat = ("\n%" + (initialSpaces) + "s%s");
        int chosenOption;
        ImageConverter imageHandler = new ImageConverter();

        while (true) {
            System.out.printf("\n%s", "*".repeat(totalSpaces));
            System.out.printf(titleFormat, "", title);
            System.out.printf("\n%s", "*".repeat(totalSpaces));

            for (var option : options)
                System.out.printf(optionFormat, "", option);

            System.out.printf("\n%s", "*".repeat(totalSpaces));
            System.out.printf("\nEntre com a opção desejada: ");

            try {
                chosenOption = input.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("\nDigite valores inteiros!");
                keyPress();
                continue;
            }

            switch (chosenOption) {
                case 1 -> {
                    System.out.printf("\nDigite o caminho da imagem");
                    System.out.printf("\nExemplo: ");
                    System.out.printf("\n(Linux)    /home/username/Desktop/filename.jpg");
                    System.out.printf("\n(Windows)  C:\\Users\\username\\Desktop\\filename.jpg");
                    System.out.printf("\nCaminho da imagem: ");

                    input.skip("\\R?");
                    String filePath = input.nextLine();
                    imageHandler.loadImage(filePath);
                }
                case 2 -> {
                    imageHandler.viewImageProperties();
                }
                case 3 -> {
                    String ASCIIArt = imageHandler.convertToASCFirstMethod();
                    System.out.printf("\n%s", ASCIIArt);
                }
                case 4 -> {
                    String ASCIIArt = imageHandler.convertToASCSecondMethod();
                    System.out.printf("\n%s", ASCIIArt);
                }
                case 5 -> {
                    System.out.printf("\n\nMétodo 1:");
                    System.out.printf("\nMantem a proporção(altura x largura) da imagem, \n" +
                            "mas aparenta distorcer as dimensões, \n" +
                            "isso ocorre devido a correspondencia de 1 caractere para 1 pixel, \n" +
                            "o espaço de 1 pixel é quadrado mas o espaço de 1 caractere é um retangulo com \n" +
                            "altura maior que a largura.");
                    System.out.printf("\n\nMétodo 2:");
                    System.out.printf("\nAfim de corrigir a distorção causada pelo método 1, \n" +
                            "este método sempre duplica a largura configurada para imagem feita em ASCII e \n" +
                            "em seguida realiza a conversão. Após a conversão e renderização da \n" +
                            "imagem o valor da largura retorna a configuração anterior.");
                }
                case 6 -> {
                    System.out.printf("\nDigite a largura da imagem resultante: ");
                    input.skip("\\R?");
                    int asciiImageWidth = input.nextInt();
                    imageHandler.changeAsciiImageWidth(asciiImageWidth);
                }
                case 7 -> {
                    System.out.printf("\nDigite a altura da imagem resultante: ");
                    input.skip("\\R?");
                    int asciiImageHeight = input.nextInt();
                    imageHandler.changeAsciiImageHeight(asciiImageHeight);
                }
                default -> {

                }
            }
            keyPress();
        }
    }

    public static void keyPress() {
        try {
            System.out.println("\n\nPressione Enter para Continuar...");
            System.in.read();

        } catch (IOException e) {
            System.out.println("Você pressionou uma tecla diferente de enter!");
        }
    }
}
