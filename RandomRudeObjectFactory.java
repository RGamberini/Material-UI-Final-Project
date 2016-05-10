package sample;

import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import sun.misc.Launcher;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by Nick on 12/11/2015.
 */
public class RandomRudeObjectFactory {
    private static final String[] RANDOMFILES = {"images.txt", "firstname.txt", "lastname.txt", "address.txt", "textbook.txt", "course.txt", "major.txt", "publisher.txt"};
    private static Map<String, String[]> propertiesFromFile;
    private static Image[] profileImages;
    protected static Random rng;

    public static String makeISBN() {
        String laendercode;
        String bandnr;
        String verlagsnr;
        String checksum;

        // Generate Random Numbers for L1L2-B1B2B3-V1V2
        double L1 = Math.random()*(10);
        double L2 = Math.random()*(10);

        double B1 = Math.random()*(10);
        double B2 = Math.random()*(10);
        double B3 = Math.random()*(10);

        double V1 = Math.random()*(10);
        double V2 = Math.random()*(10);

        // Check that L1L2 > 0
        if((int)L1 == 0 && (int)L2 == 0) {
            L2++;
        }
        // Check that L1B2B3 >= 100
        if((int)B1 == 0) {
            B1++;
        }
        // Check that V1V2 > 0
        if((int)V1 == 0 && (int)V2 == 0) {
            V2++;
        }
        // Compute check digit with hashOp method
        double C = (hashOp((int)L1) +L2 + hashOp((int)B1) +B2 + hashOp((int)B3) +V1 + hashOp((int)V2))%10;

        // Convert the generated numbers to String
        laendercode     = (int)L1+""+(int)L2;
        bandnr          = (int)B1+""+(int)B2+""+(int)B3;
        verlagsnr       = (int)V1+""+(int)V2;
        checksum        = (int)C+"";

        return laendercode + "-" + bandnr + "-" + verlagsnr + "-" + checksum;
    }

    public static int hashOp(int i)
    {
        // used to determine C
        int doubled = 2 * i;
        if ( doubled >= 10 ) {
            doubled = doubled - 9;
        }
        return doubled;
    }

    static {
        propertiesFromFile = new HashMap<>();
        rng = new Random();
        for (String strin : RANDOMFILES) {
            try {
                System.out.println(strin);
//                BufferedReader in = new BufferedReader(new FileReader(new File(RandomRudeObjectFactory.class.getResource(strin).toURI())));
//                BufferedReader in = new BufferedReader(new FileReader(new File()))
                BufferedReader in = new BufferedReader(new InputStreamReader(RandomRudeObjectFactory.class.getResourceAsStream(strin)));
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
            }
        }

        File folder = null;
        try {
            folder = new File(RandomRudeObjectFactory.class.getResource("/resources/avatars/Person").toURI());
            ArrayList<Image> temp = new ArrayList<>();
            for (File file: folder.listFiles()) {
                temp.add(new Image(file.toURL().toExternalForm()));
                System.out.println(file.getName());
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

    public static Course randomCourse() {
        String courseName = randomElement(propertiesFromFile.get("course"));
        String CRNNumber = "" + randomIntRange(10000, 99999);
        String address = randomElement(propertiesFromFile.get("address"));
        String courseNumber = randomIntRange(1, 4) + "0" + randomIntRange(0,4);
        String numberOfCredits = randomIntRange(1, 5) + (rng.nextInt(2) == 1 ? ".5" : ".0");
        return new Course(address, courseNumber, courseName, CRNNumber, numberOfCredits);
    }

    public static Textbook randomTextbook() {
        String title = randomElement(propertiesFromFile.get("textbook"));
        String author = randomElement(propertiesFromFile.get("firstname")) + " " + randomElement(propertiesFromFile.get("lastname"));
        String publisher = randomElement(propertiesFromFile.get("publisher"));
        String ISBN = makeISBN();
        String year = "" + randomIntRange(1990, 2015);
        String price = randomIntRange(60, 199) + ".99";
        return new Textbook(author, ISBN, price, publisher, title, year);
    }

    public static Student randomStudent() {
        String firstName = randomElement(propertiesFromFile.get("firstname"));
        String lastName = randomElement(propertiesFromFile.get("lastname"));
        String address = randomElement(propertiesFromFile.get("address"));
        Image profileImage = randomProfilePicture();
        String major = randomElement(propertiesFromFile.get("major"));
        String gpa = randomIntRange(1, 4) + "." + randomIntRange(1, 4);
        return new Student(firstName, lastName, randomPhoneNumber(), address, profileImage, gpa, major);
    }

    private static String[] titles = new String[]{"Mr", "Mrs", "Dr", "Professor"};

    public static Faculty randomFaculty() {
        String firstName = randomElement(propertiesFromFile.get("firstname"));
        String lastName = randomElement(propertiesFromFile.get("lastname"));
        String address = randomElement(propertiesFromFile.get("address"));
        Image profileImage = randomProfilePicture();
        String department = randomElement(propertiesFromFile.get("major")).split(" ")[0];
        String title = randomElement(titles);
        String payScale = Integer.toString(randomIntRange(0, 12));
        String officeAddress = randomElement(propertiesFromFile.get("address"));
        return new Faculty(firstName, lastName, randomPhoneNumber(), address, profileImage, department, officeAddress, payScale, title);

    }

    public static Map<Class<? extends RudeObject>, ArrayList<RudeObject>> randomSet(int n) {
        Map<Class<? extends RudeObject>, ArrayList<RudeObject>> result = new HashMap<>();
        ArrayList<RudeObject> students = new ArrayList<>(), textbooks = new ArrayList<>(), courses = new ArrayList<>(), faculty = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            textbooks.add(randomTextbook());
            faculty.add(randomFaculty());
        }

        for(int i = 0; i < n; i++) {
            Course course = randomCourse();
            course.setTextbook(textbooks.get(rng.nextInt(n)).mainLabel.getText());
            course.setFaculty(faculty.get(rng.nextInt(n)).mainLabel.getText());
            courses.add(course);
        }

        for (int i = 0; i < n; i++) {
            Student student = randomStudent();
            ArrayList<Integer> usedIndexes = new ArrayList<>();
            for(Map.Entry<String, ObservableList<RudeObject>> entry: student.extraLists.entrySet()) {
                for(int j = 0; j < randomIntRange(2, 5); j++) {
                    int index;
                    do {
                        index = rng.nextInt(n);
                    } while (usedIndexes.contains(index));
                    entry.getValue().add(courses.get(index));
                    usedIndexes.add(index);
                    if (entry.getKey() == "Current Courses") {
                        ((Course) courses.get(index)).students.add(student);
                    }
                }
            }
            students.add(student);
        }
        result.put(Student.class, students);
        result.put(Textbook.class, textbooks);
        result.put(Course.class, courses);
        result.put(Faculty.class, faculty);
        return result;
    }
}
