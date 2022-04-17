package gitlet;

//import edu.princeton.cs.algs4.StdOut;

import jdk.jshell.execution.Util;

import javax.xml.crypto.dsig.SignatureMethod;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import static gitlet.Utils.*;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Formatter;
import java.util.Date;




/** Represents a gitlet repository.
 *  does at a high level.
 *
 *  @Hongxian Wu & Xiaokun Chen
 */
public class Repository {
    /**
     *
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File gitlet_folder = Utils.join(CWD, ".gitlet");

    static final File commit_location = Utils.join(gitlet_folder, "Commits");
    //path to blobs folder
    static final File blobs_location = Utils.join(gitlet_folder, "Blobs");
    //path to Blobs folder
    static final File stage_location = Utils.join(gitlet_folder, "Stage");

    static final File pointer_location = Utils.join(gitlet_folder, "Pointer");
    //file with master "pointer" (sha1) of the most recent commit
    static File master_pointer = Utils.join(pointer_location,"master");
    //file with the head "pointer" (Name of current) of the current working directory
    static File head_pointer = Utils.join(pointer_location, "HEAD");


    public static void init(){
        gitlet_folder.mkdir();
        commit_location.mkdir();
        blobs_location.mkdir();
        stage_location.mkdir();
        pointer_location.mkdir();
        Commit first = new Commit();
        String initCommitSha = Utils.sha1(Utils.serialize(first));
        Commit.persistToCommit(initCommitSha, first);
        Utils.writeContents(master_pointer, initCommitSha);
        Utils.writeContents(head_pointer, "master");
    }
    public static File getHeadPointer(){ // return head commit pointer (a file containing a commit sha)
        return Utils.join(pointer_location, Utils.readContentsAsString(head_pointer));
    }

    public static String shortCommitID(String shortID){
        int lengthOfShortID = shortID.length();
        for(String commitIDs : Utils.plainFilenamesIn(commit_location)){
            if(commitIDs.substring(0, lengthOfShortID).equals(shortID)){
                return commitIDs;
            }
        }
        return shortID;
    }

    public static void commit(String string1){
        Stage staging = new Stage();
        if (Stage.stagePath.exists()) {
            staging = (Utils.readObject(Stage.stagePath, Stage.class));
        }
        if (staging.stagingArea.isEmpty() && staging.removingArea.isEmpty()){
            System.out.println("No changes added to the commit.");
            return;
        }else {
            String hashsha = Utils.readContentsAsString(Repository.getHeadPointer());
            Commit parent = Utils.readObject(Utils.join(commit_location, hashsha), Commit.class);
            Commit toCommit = new Commit(string1, parent);
            String shaOfCommit = Utils.sha1(Utils.serialize(toCommit));
            Commit.persistToCommit(shaOfCommit, toCommit);
            Utils.writeContents(Repository.getHeadPointer(), shaOfCommit);
        }
    }

    public static void checkout3(String filename){//java gitlet.Main checkout -- [file name]
        Repository.checkout4(Utils.readContentsAsString(Repository.getHeadPointer()),filename);
    }
    public static void checkout4(String commitID, String filename) {//java gitlet.Main checkout [commit id] -- [file name]
        String checkedID = shortCommitID(commitID);
        File location = Utils.join(commit_location, checkedID);
        if (!location.exists()) {
            System.out.println("No commit with that id exists.");
            return;
        } else {
            Commit current = Utils.readObject(location, Commit.class);
            String shaOfBlob = current.getfile(filename); //gets fileSha or throw exception is file doesn't exist
            if(shaOfBlob == null){
                return;
            }
            String contentToWrite = Utils.readContentsAsString(Utils.join(blobs_location, shaOfBlob));
            Utils.writeContents(Utils.join(CWD, filename), contentToWrite);

        }
    }


    public static void checkout2(String branchname){  //java gitlet.Main checkout [branch name]
        if(branchname.equals(Utils.readContentsAsString(head_pointer))){
            System.out.println("No need to checkout the current branch.");
            return;
        }
        int count = 0;
        for(String name : Utils.plainFilenamesIn(pointer_location)){
            if(branchname.equals(name)){
                count++;
            }
        }
        if(count == 0){
            System.out.println("No such branch exists.");
            return;
        }
        //commit of the current head
        String currentCommitSha = Utils.readContentsAsString(getHeadPointer());
        File currentCommit = Utils.join(commit_location, currentCommitSha);
        Commit current = Utils.readObject(currentCommit, Commit.class);
        // Commit of the branch to be checkout
        String checkoutSha = Utils.readContentsAsString(Utils.join(pointer_location, branchname));
        Commit checkoutCommit = Utils.readObject(Utils.join(commit_location, checkoutSha), Commit.class);
        Stage staging = new Stage();
        if (Stage.stagePath.exists()) {
            staging = (Utils.readObject(Stage.stagePath, Stage.class));
        }
        for(String fileInCWD : Utils.plainFilenamesIn(CWD)){
            if(!current.checkExistInBlob(fileInCWD) && !staging.stagingArea.containsKey(fileInCWD)
            && !staging.removingArea.containsKey(fileInCWD) && checkoutCommit.checkExistInBlob(fileInCWD)){ //&& checkoutCommit.checkExistInBlob(fileInCWD)
                System.out.println("There is an untracked file in the way; delete it, or add and commit it first.");
                return;
            }
        }
        for (String fileToChechout : checkoutCommit.blobs.keySet()) {
            checkout4(checkoutSha, fileToChechout);
        }
        for(String fileOfCurrent : current.blobs.keySet()) {
            if (!checkoutCommit.checkExistInBlob(fileOfCurrent)) {
                Utils.restrictedDelete(fileOfCurrent);
            }
        }
        Utils.writeContents(head_pointer, branchname);
        Stage stage = new Stage();
        Utils.writeObject(Stage.stagePath, stage);
    }

    public static void log(){
        String header = Utils.readContentsAsString(Repository.getHeadPointer());
        Commit currentCommit = Utils.readObject(Utils.join(commit_location, header), Commit.class);
        while (currentCommit.getParent() != null){
            currentCommit.printRegularCommit(header);
            header = currentCommit.getParent();
            currentCommit = Utils.readObject(Utils.join(commit_location, header), Commit.class);
        }
        currentCommit.printRegularCommit(header);
    }
    public static void global_log(){
        for(String header : Utils.plainFilenamesIn(commit_location)){
            Commit currentCommit = Utils.readObject(Utils.join(commit_location, header), Commit.class);
            currentCommit.printRegularCommit(header);
        }
    }

    public static void find(String message){
        int count = 0;
        for(String header : Utils.plainFilenamesIn(commit_location)){
            Commit currentCommit = Utils.readObject(Utils.join(commit_location, header), Commit.class);
            if(currentCommit.getMessage().equals(message)){
                System.out.println(header);
                count++;
            }
        }
        if(count == 0){
            System.out.println("Found no commit with that message.");
        }
    }

    public static void branch(String branchName){
        for(String names : Utils.plainFilenamesIn(pointer_location))
        {
            if(names.equals(branchName)){
                System.out.println("A branch with that name already exists.");
                return;
            }
        }
        File new_pointer = Utils.join(pointer_location, branchName);
        Utils.writeContents(new_pointer, Utils.readContentsAsString(getHeadPointer()));
    }

    public static void removeBranch(String branch){
        if(branch.equals(Utils.readContentsAsString(head_pointer))){
            System.out.println("Cannot remove the current branch.");
            return;
        }
        int count = 0;
        for(String branches : Utils.plainFilenamesIn(pointer_location)){
            if(branches.equals(branch)){
                count++;
            }
        }
        if(count == 0){
            System.out.println("A branch with that name does not exist.");
        }
        Utils.join(pointer_location, branch).delete();
    }

    public static void status() {
        System.out.println("=== Branches ===");
        String head = Utils.readContentsAsString(head_pointer);
        System.out.println("*" + head);
        for(String branches : Utils.plainFilenamesIn(pointer_location)) {
            if(!branches.equals(head) && !branches.equals("HEAD")){
                System.out.println(branches);
            }
        }
        System.out.println();

        //getting Stage
        Stage staging = new Stage();
        if (Stage.stagePath.exists()) {
            staging = (Utils.readObject(Stage.stagePath, Stage.class));
        }

        System.out.println("=== Staged Files ===");
        for(String adding : staging.stagingArea.keySet()) {
            System.out.println(adding);
        }
        System.out.println();

        System.out.println("=== Removed Files ===");
        for(String removing : staging.removingArea.keySet()) {
            System.out.println(removing);
        }
        System.out.println();


        // Optional parts
        System.out.println("=== Modifications Not Staged For Commit ===");
        System.out.println();
        System.out.println("=== Untracked Files ===");
        System.out.println();
    }

    public static void reset(String commitID){
        String checkedID = shortCommitID(commitID);
        File location = Utils.join(commit_location, checkedID);
        Commit headCommit = Utils.readObject(Utils.join(commit_location, Utils.readContentsAsString(Repository.getHeadPointer())), Commit.class);
        if (!location.exists()) {
            System.out.println("No commit with that id exists.");
            return;
        }
        String currentHeadpointer = Utils.readContentsAsString(head_pointer);
        File reset = Utils.join(pointer_location, "tempResetPointer");
        Utils.writeContents(reset, checkedID);
        checkout2("tempResetPointer");
        reset.delete();
        Utils.writeContents(head_pointer, currentHeadpointer);
        Utils.writeContents(getHeadPointer(), checkedID);
    }
}


