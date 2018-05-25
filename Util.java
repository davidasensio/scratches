import android.util.Base64;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//import com.hofmann.hofmann.createproduct.lfao.CCreateLFAOActivity;

class Util {
    enum TEXTCASE {UPPERCASE, LOWERCASE, PASCALCASE, CAMELCASE, UNDERSCORED, INVERT_UNDERSCORED}

    public static void main(String[] args) throws Exception {

        //Generates setup fonts statements
        //generateSetUpFonts();

        //Converse findViewByIds to Butterknife anotations
        //converseFindViewByIdToBindView();

        //Drawable ranaming
        //renameDrawablesBlock();

        //Drawable PNG optimizing
        //optimizePNGDrawables();

        final String targetDir = "/Users/davidasensio/Library/Preferences/AndroidStudio3.0/scratches/catalogs/ios_trans/";
        renameDirectoryFilenames(targetDir);
        iosCatalogPlistsToAndroid(targetDir);

        /*final String targetDir = "/Users/davidasensio/Library/Preferences/AndroidStudio2.3/scratches/catalogs/drawables/";
        String[] newnames = new String[]{ "rendevoo.png", "hiyalife.png", "readbug.png", "gow.png", "articheck.png", "aquaservice.png",
                "catchapp.png", "lg.png", "disqovr.png", "kidaround.png", "freshdeal.png", "alcanzia.png", "mybae.png", "ergondesk.png", "seqnc.png", "snaply.png","fermax.png"};
        renameFilenamesInDirectory(targetDir, newnames);*/

        //Extract a drawable with all densities to /Downloads/extractedDrawable/ folder
//        String resourcesFolder = "/Users/davidasensio/fiestapp/fiesta-android/app/src/main/res/";
//        extractDrawableResource(resourcesFolder, "button_close.png");

        //Shuffle array
        //shuffleString();

    }




    //------------------------------------------------------------------------------
    //----------------------------------- Fonts ------------------------------------
    //------------------------------------------------------------------------------
    private static void generateSetUpFonts() {
        //Replace with Target Class!!!!
        //Class activityClass = com.blaskay.fiesta.modules.messages.MessagesActivity.class;
        Class activityClass = java.lang.String.class; //CCreateLFAOActivity.class;

        //Options config:    fontsGeneration
        Boolean options[] = {true};
        String output = String.format("Working with class %s...\n", activityClass.getSimpleName());

        //Fonts generation
        if (options[0]) {
            String fontName = "Bariol_Regular";
            String fontNameCamelCase = getAsTextCase(fontName, TEXTCASE.CAMELCASE);
            java.lang.reflect.Field[] declaredFields = activityClass.getDeclaredFields();

            output += String.format("\nTypeface %sTypeface = Typeface.createFromAsset(getAssets(), \"fonts/%s.ttf\");", fontNameCamelCase, fontName);
            for (java.lang.reflect.Field field : declaredFields) {
                if (TextView.class.equals(field.getType())) {
                    output += String.format("\n%s.setTypeface(%sTypeface);", field.getName(), fontNameCamelCase);
                }
            }
            print(output);
        }
    }

    //------------------------------------------------------------------------------
    //-------------------------------- Butterknife ---------------------------------
    //------------------------------------------------------------------------------
    private static void converseFindViewByIdToBindView() {
        String targetFile = "/Users/davidasensio/hofmann/hofmannapp/hofmannPhone/src/main/java/com/hofmann/hofmann/login/CLoginActivity.java";
        String findRegex = "^\\W+(\\w+)\\W+=\\W+(\\w+)\\W+findViewById\\((.*)\\).*$";
        String replRegex = "@BindView($3) $2 $1;";
        String findRegex2 = "^.*\\W+(\\w+)\\W+findViewById\\((.*)\\).*$";
        String replRegex2 = "@BindView($2) $1 $1;";

        StringBuilder input = new StringBuilder();
        StringBuilder output = new StringBuilder();
        ArrayList<String> lines = new ArrayList<>(readFile(targetFile));

        for (String line : lines) {
            if (line.matches(findRegex)) {
                input.append(line).append("\n");
                output.append(line.replaceAll(findRegex, replRegex)).append("\n");

            } else if (line.matches(findRegex2)) {
                input.append(line).append("\n");
                output.append(line.replaceAll(findRegex2, replRegex2)).append("\n");
            }
        }

        System.out.println("");
        System.out.println("Butterknife converter");
        System.out.println("=====================");
        System.out.println("\nInput matched:\n\n" + input.toString());
        System.out.println("\nOutput generated:\n\n" + output.toString());
    }

    //------------------------------------------------------------------------------
    //------------------------------ Drawable Rename -------------------------------
    //------------------------------------------------------------------------------
    private static void renameDrawablesBlock() {
        final String path = "/Users/davidasensio/Downloads/rem/vivoiloft/ok/";
//        renameDrawables(path, "group_2.png", "device_selector_iloft_background.png");
//        renameDrawables(path, "group_4.png", "device_selector_vivo_background.png");
//        renameDrawables(path, "group_9.png", "device_selector_iloft.png");
//        renameDrawables(path, "group_10.png", "device_selector_vivo.png");
//        renameDrawables(path, "line.png", "device_selector_separator.png");
    }

    private static void renameDrawables(String drawablePath, String drawableName, String drawableNewName) {
        String[] resolutions = new String[]{"drawable-mdpi", "drawable-hdpi", "drawable-xhdpi", "drawable-xxhdpi", "drawable-xxxhdpi"};

        for (String resolution : resolutions) {
            File fileToRename = new File(drawablePath + resolution + "/" + drawableName);
            File newFile = new File(drawablePath + resolution + "/" + drawableNewName);

            boolean isRenamed = fileToRename.renameTo(newFile);
            if (!isRenamed) {
                System.out.println(String.format("Error ocurred renaming file: %s", fileToRename.getPath()));
            } else {
                System.out.println(String.format("File renamed successfully: %s to %s", fileToRename.getName(), newFile.getName()));
            }
        }
    }

    //------------------------------------------------------------------------------
    //------------------------- Drawable PNG Optimization --------------------------
    //------------------------------------------------------------------------------

    /**
     * Utility method to optimize PNG files
     * <p>
     * This is the script used. It call the pngquant utility available at: https://pngquant.org/
     * {param $1 - Path of pngquant}
     * {param $2 - Path of PNG files}
     * <p>
     * <p>
     * <code>
     * #!/bin/bash
     * # Optimize PNG files of directory
     * # Sample usage: /Users/davidasensio/davidmac/programs/pngquant/pngquant --force --ext _optimized.png 256 /Users/davidasensio/hofmann/hofmannapp_copy/hofmannPhone/src/main/res/drawable-xxhdpi/*.png
     * <p>
     * echo “Optimizing PNGs…”
     * <p>
     * $1 --force --ext .png 256 $2*.png
     * </code>
     */
    private static void optimizePNGDrawables() {
        String script = "/Users/davidasensio/Desktop/optimizepngs.sh";
        String pngquantPath = "/Users/davidasensio/davidmac/programs/pngquant/pngquant";

        String drawablePath = "/Users/davidasensio/hofmann/hofmannapp_copy/hofmannPhone/src/main/res/";
        String[] resolutions = new String[]{"drawable-mdpi", "drawable-hdpi", "drawable-xhdpi", "drawable-xxhdpi", "drawable-xxxhdpi"};

        for (String resolution : resolutions) {
            String targetDirectory = drawablePath + resolution + "/";

            ArrayList<String> command = new ArrayList<>();
            command.add("sh");
            command.add(script);
            command.add(pngquantPath);
            command.add(targetDirectory);

            //Ask for confirmation
            Scanner reader = new Scanner(System.in);
            print(String.format("Are you sure you want to optimize all PNGs in %s - (y / n): ", targetDirectory));
            String answer = reader.nextLine();

            if ("y".equals(answer) || "Y".equals(answer)) {
                execScript(command);
            } else {
                print("Aborted by user");
            }
        }
    }

    //------------------------------------------------------------------------------
    //---------------------------- Drawable Extraction -----------------------------
    //------------------------------------------------------------------------------
    private static void extractDrawableResource(String resourcesFolder, String drawableName) {
        String[] densities = new String[]{"drawable-mdpi", "drawable-hdpi", "drawable-xhdpi", "drawable-xxhdpi", "drawable-xxxhdpi"};
        String targetDirectory = "/Users/davidasensio/Downloads/extractedDrawable/";

        for (String density : densities) {
            Path source = Paths.get(resourcesFolder + density + "/" + drawableName);

            File destinationFile = new File(targetDirectory + density + "/" + drawableName);
            destinationFile.getParentFile().mkdirs();
            Path destination = Paths.get(targetDirectory + density + "/" + drawableName);

            try {
                CopyOption[] options = new CopyOption[]{
                        StandardCopyOption.REPLACE_EXISTING,
                        StandardCopyOption.COPY_ATTRIBUTES
                };

                Files.copy(source,destination, options);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //------------------------------------------------------------------------------
    //------------------------------ Execute commands ------------------------------
    //------------------------------------------------------------------------------
    public static void execScript(List command) {
        try {

            print(String.format("Executing %s:\n", command.toString()));

            ProcessBuilder pb = new ProcessBuilder(command);
            Process p = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader readerError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            String line;

            print("\nStandard output of the command:\n");
            while ((line = reader.readLine()) != null) {
                print(line);
            }

            print("\nStandard error of the command (if any):\n");
            while ((line = readerError.readLine()) != null) {
                print(line);
            }
        } catch (Exception e) {
            print("Exception happened - here's what I know: ");
            e.printStackTrace();
            System.exit(-1);
        }
    }

    static void renameDirectoryFilenames(String targetDir) {
        //Replacing
        List<String> files = fileList(targetDir);
        for (String filename : files) {
            File file = new File(filename);
            if (file.exists() && !file.isHidden() && !file.isDirectory()) {
                String newFilename = file.getName();
                newFilename = renameFileExtension(newFilename, ".plist", ".xml");
                newFilename = newFilename.replaceAll("iPhone", "Iphone");
                newFilename = getAsTextCase(newFilename, TEXTCASE.CAMELCASE);
                newFilename = getAsTextCase(newFilename, TEXTCASE.UNDERSCORED);
//                newFilename = getAsTextCase(newFilename, TEXTCASE.INVERT_UNDERSCORED);

                file.renameTo(new File(file.getParent() + "/" + newFilename));
            }
        }
    }

    static String renameFileExtension(String file, String fileExtension, String newFileExtension) {
        return file.replaceAll(fileExtension, newFileExtension);
    }

    static void iosCatalogPlistsToAndroid(String targetDir) throws Exception {
        String[] findArray = {"navigationBarButtons", "navigationBars", "navigationBackItems", "sectionsLabels", "labels", "collectionViews", "collectionCells", "collectionReusableHeaders", "collectionReusableFooters", "tableViews", "tableCells", "textFields", "tableViewProduct", "_iPad_", "DVAM", "MainUI", "HomeUI", "InfoUI", "ContactUI", "ConfigurationUI", "ProductDetailUI", "ShopsInfoUI", "OrderListsUI", "OrderDetailUI"};
        String[] replaceArray = {"navigation_items", "toolbars", "menu_items", "sectionstexts", "texts", "recycler_views", "cell_views", "cell_headers", "cell_footers", "recycler_views", "cell_views", "text_fields", "collectionProducts", "_Tablet_", "", "Main_UI", "Home_UI", "Info_UI", "Contact_UI", "Configuration_UI", "Product_Detail_UI", "Shops_Info_UI", "Order_Lists_UI", "Order_Detail_UI"};
        if (findArray.length != replaceArray.length) {
            throw new Exception("Different array lengths!!");
        }

        //Replacing
        List<String> files = fileList(targetDir);
        for (String file : files) {
            replaceInFile(Paths.get(file), findArray, replaceArray);
        }

        System.out.println("Replacing done!");
    }

    static void renameFilenamesInDirectory(String targetDir, String[] newnames) {

        //Replacing
        List<String> files = fileList(targetDir);
        Arrays.sort(
                files.toArray(new String[files.size()]), new Comparator<String>() {
                    public int compare(String f1, String f2) {
                        return f1.compareTo(f2);
                    }
                }
        );

        for (int i = 0; i < files.size(); i++) {
            if (i < newnames.length) {
                String filename = files.get(i);
                File file = new File(filename);
                if (file.exists() && !file.isHidden() && !file.isDirectory()) {
                    String newFilename = newnames[i];
                    file.renameTo(new File(file.getParent() + "/" + newFilename));
                }
            }
        }
    }

    static List<String> fileList(String directory) {
        List<String> fileNames = new ArrayList<>();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(directory))) {
            for (Path path : directoryStream) {
                boolean isDirectory = new File(path.toString()).isDirectory();
                if (!isDirectory) {
                    fileNames.add(path.toString());
                }
            }
        } catch (IOException ex) {
        }
        return fileNames;
    }

    static void replaceInFile(Path path, String[] findArray, String[] replaceArray) throws Exception {

        Charset charset = StandardCharsets.UTF_8;
        String content = new String(Files.readAllBytes(path), charset);
        for (int i = 0; i < findArray.length; i++) {
            String wordToMatch = findArray[i];
            String wordToReplace = replaceArray[i];
            content = content.replaceAll(wordToMatch, wordToReplace);
        }
        Files.write(path, content.getBytes(charset));
    }

    static String shuffleString() {
        String letters = "abcdefghijklmnopqrstuvwyz";
        char[] lettersArray = letters.toCharArray();
        List<Character> charList = new ArrayList<>();
        for (char c : lettersArray) {
            charList.add(c);
        }
        Collections.shuffle(charList);

        StringBuilder result = new StringBuilder(charList.size());
        for (Character c : charList) {
            result.append(c);
        }
        System.out.println(result.toString());

        return result.toString();
    }

    //------------------------------------------------------------------------------
    //-------------------------- General Purpose Methods ---------------------------
    //------------------------------------------------------------------------------
    static List<String> readFile(String targetFile) {
        List<String> lines = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(targetFile))) {

            lines = stream.collect(Collectors.<String>toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    static String getAsTextCase(String text, TEXTCASE textcase) {
        String result = text;

        switch (textcase) {
            case UPPERCASE:
                return text.toUpperCase();
            case LOWERCASE:
                return text.toLowerCase();
            case PASCALCASE:
                return Character.toUpperCase(text.charAt(0)) + text.substring(1);
            case CAMELCASE:
                return Character.toLowerCase(text.charAt(0)) + text.substring(1);
            case UNDERSCORED:
                return text.replaceAll("_", "").replaceAll("([A-Z])", "_$1").toLowerCase();
            case INVERT_UNDERSCORED:
                Matcher m = Pattern.compile("_([a-zA-Z])").matcher(text);
                StringBuffer sb = new StringBuffer();
                while (m.find()) {
                    m.appendReplacement(sb, m.group().replaceAll("_", "").toUpperCase());
                }
                m.appendTail(sb);
                return sb.toString();
        }
        return text;
    }

    static void print(String text) {
        System.out.println(String.format("%s", text));
    }

    static void debug(String text) {
        System.out.println(String.format("%s --> %s", "Utilities", text));
    }
}