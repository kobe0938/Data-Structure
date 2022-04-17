package capers;

import java.io.File;
import java.io.Serializable;
import java.util.HashSet;

/** Represents a dog that can be serialized.
 * @author Sean Dooher
*/
public class Dog implements Serializable{ // FIXME

    /** Folder that dogs live in. */
    static final File DOG_FOLDER = Utils.join(Main.CAPERS_FOLDER,"dogs");

    /** Age of dog. */
    private int age;
    /** Breed of dog. */
    private String breed;
    /** Name of dog. */
    private String name;

    /**
     * Creates a dog object with the specified parameters.
     * @param name Name of dog
     * @param breed Breed of dog
     * @param age Age of dog
     */
    public Dog(String name, String breed, int age) {
        this.age = age;
        this.breed = breed;
        this.name = name;
    }

    /**
     * Reads in and deserializes a dog from a file with name NAME in DOG_FOLDER.
     *
     * @param name Name of dog to load
     * @return Dog read from file
     */
    public static Dog fromFile(String name) {
        //Dog returndog = Utils.readObject(name, Dog.class);
        Dog returndog = Utils.readObject(Utils.join(DOG_FOLDER,name), Dog.class);
        return returndog;
        //Utils.readContentsAsString(DOG_FOLDER);
    }

    /**
     * Increases a dog's age and celebrates!
     */
    public void haveBirthday() {
        age += 1;
        System.out.println(toString());
        System.out.println("Happy birthday! Woof! Woof!");
    }

    /**
     * Saves a dog to a file for future use.
     */
    public void saveDog(){
        File newDog = Utils.join(DOG_FOLDER,this.name);
        Utils.writeObject(newDog,this);
//        HashSet<String> aNewDog = new HashSet<>();
//        aNewDog = Utils.readObject(DOG_FOLDER,aNewDog);
//        aNewDog.add(this.name);

//        Utils.writeObject(aNewDog,this);
//        File newDog = new File("DOG_FOLDER");
//        Utils.writeObject(newDog,this);
    }

    @Override
    public String toString() {
        return String.format(
            "Woof! My name is %s and I am a %s! I am %d years old! Woof!",
            name, breed, age);
    }
}
