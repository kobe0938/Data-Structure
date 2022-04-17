package gitlet;

import java.io.Serializable;

public class Blob implements Serializable {
    String content;
    String filename;

    public Blob(String file) {
        this.content = Utils.readContentsAsString(Utils.join(Repository.CWD, file));
        this.filename = file;
    }
}
