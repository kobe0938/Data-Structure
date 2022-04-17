package gitlet;

/**
 * Driver class for Gitlet, a subset of the Git version-control system.
 *
 * @Hongxian Wu & Xiaokun Chen
 */
public class Main {

    /**
     * Usage: java gitlet.Main ARGS, where ARGS contains
     * <COMMAND> <OPERAND1> <OPERAND2> ...
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please enter a command.");
            return;
        }

        String firstArg = args[0];
        if (!firstArg.equals("init") && !Repository.gitlet_folder.exists()) {
            System.out.println("Not in an initialized Gitlet directory.");
            return;
        }
        switch (firstArg) {
            case "init":
                Main.init();
                break;
            case "add":
                Main.add(args[1]);
                break;
            case "commit":
                if (args.length == 1 || args[1].equals("")) {
                    System.out.println("Please enter a commit message.");
                    return;
                }
                Main.commit(args[1]);
                break;
            case "checkout":
                Main.checkout(args);
                break;
            case "log":
                Main.log();
                break;
            case "rm":
                Main.rm(args[1]);
                break;
            case "global-log":
                Main.glog();
                break;
            case "find":
                Main.find(args[1]);
                break;
            case "status":
                Main.status();
                break;
            case "branch":
                Main.branch(args[1]);
                break;
            case "rm-branch":
                Main.removeBranch(args[1]);
                break;
            case "reset":
                Main.reset(args[1]);
                break;

            default:
                System.out.println("No command with that name exists.");
                return;
        }
    }
//    public static String checkInitiated(){
//        if(!Repository.gitlet_folder.exists()){
//            System.out.println("Not in an initialized Gitlet directory.");
//            return null;
//        }
//    }

    public static void init() {
        if (Repository.gitlet_folder.exists()) {
            System.out.println("A Gitlet version-control system already exists in the current directory.");
        }
        Repository.init();

    }

    public static void add(String file) {
        Stage.add(file);
    }

    public static void commit(String message) {
        Repository.commit(message);
    }

    public static void checkout(String[] args) {
        if (args.length == 3) {//java gitlet.Main checkout -- [file name]
            if (!args[1].equals("--")) {
                System.out.println("Incorrect operands.");
                return;
            }
            Repository.checkout3(args[2]);
        } else if (args.length == 4) {//java gitlet.Main checkout [commit id] -- [file name]
            if (!args[2].equals("--")) {
                System.out.println("Incorrect operands.");
                return;
            }
            Repository.checkout4(args[1], args[3]);
        } else {                  //java gitlet.Main checkout [branch name]
            Repository.checkout2(args[1]);
        }
    }

    public static void log() {
        Repository.log();
    }

    public static void glog() {
        Repository.global_log();
    }

    public static void rm(String filename) {
        Stage.remove(filename);
    }

    public static void find(String message) {
        Repository.find(message);
    }

    public static void status() {
        Repository.status();
    }

    public static void branch(String branchName) {
        Repository.branch(branchName);
    }

    public static void removeBranch(String branch) {
        Repository.removeBranch(branch);
    }

    public static void reset(String commitID) {
        Repository.reset(commitID);
    }
}
