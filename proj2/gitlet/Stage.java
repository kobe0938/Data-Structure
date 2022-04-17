package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.HashMap;

public class Stage implements Serializable {

    public static final File stagePath = Utils.join(Repository.stage_location, "Staging");
    /**
     * variables to be added
     */
    public HashMap<String, String> stagingArea;// key is filename, value is sha
    public HashMap<String, String> removingArea; // key is filename, value is sha

    public Stage() {
        stagingArea = new HashMap<String, String>();
        removingArea = new HashMap<String, String>();
    }


    public static void add(String file) {
        File check = Utils.join(Repository.CWD, file);
        if (!check.exists()) {
            System.out.println("File does not exist.");
            return;
        }
        Stage staging = new Stage();
        if (stagePath.exists()) {
            staging = (Utils.readObject(stagePath, Stage.class));
        }
        Blob fileToAdd = new Blob(file);
        byte[] fileToStage = Utils.serialize(fileToAdd);
        String fileSha = Utils.sha1(fileToStage);
        //System.out.println(fileSha);
        staging.stagingArea.put(file, fileSha);
        String addCommit = Utils.readContentsAsString(Repository.getHeadPointer());
        Commit parent = Utils.readObject(Utils.join(Repository.commit_location, addCommit), Commit.class);
        if (staging.removingArea.containsKey(file)) {
            staging.removingArea.remove(file);
        }

        if (parent.checkExistInBlob(file) && parent.blobs.get(file).equals(fileSha)) {
            staging.stagingArea.remove(file);
        } else { //can replace with readContentsAsString and writeContents
            if (Utils.join(Repository.blobs_location, fileSha).exists()) {
                try {
                    Files.delete(Utils.join(Repository.blobs_location, fileSha).toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                Files.copy(Utils.join(Repository.CWD, file).toPath(), Utils.join(Repository.blobs_location, fileSha).toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Utils.writeObject(stagePath, staging);
    }

    public static void remove(String filename) {

        Stage staging = new Stage();
        if (stagePath.exists()) {
            staging = (Utils.readObject(stagePath, Stage.class));
        }
        String headCommit = Utils.readContentsAsString(Repository.getHeadPointer());
        Commit current = Utils.readObject(Utils.join(Repository.commit_location, headCommit), Commit.class);
        // check if staged or in prev commit
        if ((!stagePath.exists() && !staging.stagingArea.containsKey(filename)) && !current.checkExistInBlob(filename)) {
            System.out.println("No reason to remove the file.");
            return;
        }

        //remove from stagingArea if it exist (did not remove from Blobs)
        if (stagePath.exists() && staging.stagingArea.containsKey(filename)) {
            staging = (Utils.readObject(stagePath, Stage.class));
            staging.stagingArea.remove((filename));
        }
        //check if filename is in head commit
        if (current.checkExistInBlob(filename)) {
            staging.removingArea.put(filename, current.getfile(filename));
            Utils.restrictedDelete(filename);
        }
        Utils.writeObject(stagePath, staging);


    }
}
