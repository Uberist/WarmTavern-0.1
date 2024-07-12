package org.example.warmtavern.Entity;

import lombok.Data;

@Data
public class Voice {
    int id;
    String voice_first_name;
    String voice_last_name;
    String voice_middle_name;

    public Voice(int id, String voice_first_name, String voice_last_name, String voice_middle_name) {
        this.id = id;
        this.voice_first_name = voice_first_name;
        this.voice_last_name = voice_last_name;
        this.voice_middle_name = voice_middle_name;
    }
    public Voice() {}
}
