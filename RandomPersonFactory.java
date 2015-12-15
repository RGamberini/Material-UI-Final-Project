package sample;

import javafx.scene.image.Image;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Nick on 12/11/2015.
 */
public class RandomPersonFactory {
    private static final String[] RANDOMFILES = {"firstname.txt", "lastname.txt", "address.txt"};
    private static Map<String, String[]> propertiesFromFile;
    private static Image[] profileImages;
    protected static Random rng;

    static {
        propertiesFromFile = new HashMap<>();
        rng = new Random();
        for (String strin: RANDOMFILES) {
            try {
                BufferedReader in = new BufferedReader(new FileReader(new File(RandomPersonFactory.class.getResource(strin).toURI())));
                ArrayList<String> temp = new ArrayList<>();
                String line;
                while ((line = in.readLine()) != null) {
                    temp.add(line);
                }
                propertiesFromFile.put(strin.replace(".txt", ""), temp.toArray(new String[]{}));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.out.println("COULD NOT FIND " + strin);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        File folder = null;
        try {
            folder = new File(RandomPersonFactory.class.getResource("/resources/avatars/Person").toURI());
            ArrayList<Image> temp = new ArrayList<>();
            for (File file: folder.listFiles()) {
                temp.add(new Image(file.toURL().toExternalForm()));
            }
            profileImages = temp.toArray(new Image[]{});

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    protected static <T> T randomElement(T[] arr) {
        return arr[rng.nextInt(arr.length)];
    }

    protected static int randomIntRange(int min, int max) {
        return rng.nextInt(max - min) + min;
    }

    private static String randomPhoneNumber() {
        return "(" + randomIntRange(100, 999) + ")" + randomIntRange(100, 999) + "-" + randomIntRange(1000, 9999);
    }

    public static Image randomProfilePicture() {
        return randomElement(profileImages);
    }

    public static Person randomPerson() {
        String firstName = randomElement(propertiesFromFile.get("firstname"));
        String lastName = randomElement(propertiesFromFile.get("lastname"));
        String address = randomElement(propertiesFromFile.get("address"));
        Image profileImage = randomProfilePicture();
        return new Person(firstName, lastName, randomPhoneNumber(), address, profileImage);
    }
}
