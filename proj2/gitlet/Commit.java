package gitlet;


import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Represents a gitlet commit object.
 * does at a high level.
 *
 * @Hongxian Wu & Xiaokun Chen
 */
public class Commit implements Serializable {
    public static final String initDateString = "Wed Dec 31 16:00:00 1969 -0800";
    /**
     * <p>
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message'
     */
    //The time of the commit.
    public SimpleDateFormat intermidDate; //00:00:00 UTC, Thursday, 1 January 1970
    public String date;
    //The sha1 of the parent.
    public String Parent;
    HashMap<String, String> blobs;//Key is filename, value is sha.
    /**
     * The message of this Commit.
     */
    private String message;

    // Commit constructor used to create inital commit.
    public Commit() {
        Parent = null;
        message = "initial commit";
        blobs = new HashMap<>();//SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
        //testing date
        date = initDateString;
    }


    public Commit(String mess, Commit parent1) {
        this.blobs = new HashMap<>(parent1.blobs);
        Stage staging = new Stage();
        if (Stage.stagePath.exists()) {
            staging = (Utils.readObject(Stage.stagePath, Stage.class));
        }
        // Remove from commit if staged for remove.
        for (String removeKey : staging.removingArea.keySet()) {
            blobs.remove(removeKey);

        }
        for (String currentKey : staging.stagingArea.keySet()) {
            blobs.put(currentKey, staging.stagingArea.get(currentKey));
        }
        staging = new Stage();
        Utils.writeObject(Stage.stagePath, staging);
        Parent = Utils.readContentsAsString(Repository.getHeadPointer());
        intermidDate = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z");//should be Date: Thu Nov 9 17:01:33 2017 -0800,

        date = intermidDate.format(new Date());

        message = mess;
    }

    public static void persistToCommit(String commitSha, Commit file) {
        File commit = Utils.join(Repository.commit_location, commitSha);
        Utils.writeObject(commit, file);
    }

    //return the sha of the blob
    public String getfile(String filename) {
        if (!this.blobs.containsKey(filename)) {
            System.out.println("File does not exist in that commit.");
            return null;
        } else {
            return this.blobs.get(filename);
        }
    }

    public String getParent() {
        return this.Parent;
    }

    public String getMessage() {
        return this.message;
    }

    public boolean checkExistInBlob(String key) {
        return this.blobs.containsKey(key);

    }

    public void printRegularCommit(String header) {
        System.out.println("===");
        System.out.println("commit " + header);

        System.out.println("Date: " + this.date);
        System.out.println(this.getMessage());
        System.out.println();
    }

}

