import java.io.IOException;
import java.util.*;

public class Menu {
    private static Scanner input = new Scanner(System.in);
    private static final String title = "ASCII Art";
    private static final List<String> optionsList = Arrays.asList(
            "1 - Carregar a imagem",
            "2 - Mostra exemplos de imagens convertidas",
            "3 - Mostrar propriedades da imagem",
            "4 - Converter imagem (método 1)",
            "5 - Converter imagem (método 2)",
            "6 - Informação sobre os métodos de conversão",
            "7 - Mudar a largura da imagem resultante",
            "8 - Mudar a altura da imagem resultante");

    private static final int initialSpaces = 6;
    private static final int totalSpaces = Math.max(optionsList.stream()
           .mapToInt(String::length)
           .max()
           .orElse(0)+initialSpaces, 60);
    private static final String titleFormat = ("\n%" + (totalSpaces - title.length()) / 2 + "s%s");
    private static final String optionFormat = ("\n%" + (initialSpaces) + "s%s");

    public static void main(String[] args) {
        final int LIMIT_OPTIONS_FIRST_STAGE = 2;
        final int LIMIT_OPTIONS_SECOND_STAGE = optionsList.size();

        ImageConverter imageHandler = new ImageConverter();
        Optional<Integer> chosenOption;

        Boolean loadedImage = false;
        while (!loadedImage) {
            displayMenu(LIMIT_OPTIONS_FIRST_STAGE);

            chosenOption = Optional.ofNullable(getUserOptionInput());
            if (chosenOption.isEmpty()) {
                input.nextLine();
                continue;
            }
            if (chosenOption.get() > LIMIT_OPTIONS_FIRST_STAGE) {
                System.out.printf("\nOpção invalida!");
                keyPress();
                continue;
            }

            runOption(chosenOption.get(), imageHandler);
            loadedImage = imageHandler.checkImageLoaded();
        }

        while (true) {
            displayMenu(LIMIT_OPTIONS_SECOND_STAGE);
            chosenOption = Optional.ofNullable(getUserOptionInput());
            if (chosenOption.isEmpty()) {
                input.nextLine();
                continue;
            }

            runOption(chosenOption.get(), imageHandler);
        }
    }

    public static void displayMenu(int limitOptions) {
        System.out.printf("\n%s", "*".repeat(totalSpaces));
        System.out.printf(titleFormat, "", title);
        System.out.printf("\n%s", "*".repeat(totalSpaces));

        optionsList.stream()
                .limit(limitOptions)
                .forEach(option -> System.out.printf(optionFormat, "", option));

        System.out.printf("\n%s", "*".repeat(totalSpaces));
        System.out.printf("\nEntre com a opção desejada: ");
    }

    public static Integer getUserOptionInput() {
        Integer chosenOption = null;
        try {
            chosenOption = input.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("\nDigite valores inteiros!");
//            input.skip("\\R?");
            keyPress();
        }
        return chosenOption;
    }

    public static void runOption(int chosenOption, ImageConverter imageHandler) {
        switch (chosenOption) {
            case 1 -> {
                System.out.printf("\nDigite o caminho da imagem");
                System.out.printf("\nExemplo: ");
                System.out.printf("\n(Linux)    /home/username/Desktop/filename.jpg");
                System.out.printf("\n(Windows)  C:\\Users\\username\\Desktop\\filename.jpg");
                System.out.printf("\n\nCaminho da imagem: ");

                input.skip("\\R?");
                String filePath = input.nextLine();
                imageHandler.loadImage(filePath);
            }
            case 2 -> {
                ImageConverter exampleImgHandler = new ImageConverter();
                String imgPath;
                for (int i = 1; i <= 3; i++) {
                    imgPath = String.format("./src/main/resources/example_images/example%d.jpg", i);
                    System.out.printf("\nCarregando imagem de: %s", imgPath);

                    exampleImgHandler.loadImage(imgPath);
                    String asciiArt = exampleImgHandler.convertToASCSecondMethod();
                    System.out.printf("\n%s", asciiArt);
                }
            }
            case 3 -> {
                imageHandler.viewImageProperties();
            }
            case 4 -> {
                String ASCIIArt = imageHandler.convertToASCFirstMethod();
                System.out.printf("\n%s", ASCIIArt);
            }
            case 5 -> {
                String ASCIIArt = imageHandler.convertToASCSecondMethod();
                System.out.printf("\n%s", ASCIIArt);
            }
            case 6 -> {
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
            case 7 -> {
                System.out.printf("\nDigite a largura da imagem resultante: ");
                input.skip("\\R?");
                int asciiImageWidth = input.nextInt();
                imageHandler.changeAsciiImageWidth(asciiImageWidth);
            }
            case 8 -> {
                System.out.printf("\nDigite a altura da imagem resultante: ");
                input.skip("\\R?");
                int asciiImageHeight = input.nextInt();
                imageHandler.changeAsciiImageHeight(asciiImageHeight);
            }
            default -> {
                System.out.printf("\nOpção invalida!");
            }
        }
        keyPress();
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
