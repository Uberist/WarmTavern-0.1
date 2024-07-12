package org.example.warmtavern.converter;

import org.example.warmtavern.DAOInterface.VoiceRepository;
import org.example.warmtavern.Entity.Voice;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class VoiceConverter implements Converter<String, Voice> {
    VoiceRepository voiceRepository;
    public VoiceConverter(VoiceRepository voiceRepository) {
        this.voiceRepository = voiceRepository;
    }
    @Override
    public Voice convert(String source) {
        return voiceRepository.findById(Integer.parseInt(source)).get();
    }
}
