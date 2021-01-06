package life.nsu.bangladictionary.models;

import android.net.Uri;

public class Vocabulary {

    private String image;
    private String english;
    private String bangla;

    public Vocabulary(Uri image, String english, String bangla) {
        this.image = image.toString();
        this.english = english;
        this.bangla = bangla;
    }

    public Vocabulary(String english, String bangla) {
        this.english = english;
        this.bangla = bangla;
    }

    public String getImage() {
        return image;
    }

    public String getEnglish() {
        return english;
    }

    public String getBangla() {
        return bangla;
    }
}
