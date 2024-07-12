package org.example.warmtavern.DAOInterface;

import org.example.warmtavern.Entity.Voice;

import java.util.Optional;

public interface VoiceRepository {
    public Iterable<Voice> findAll();
    public Optional<Voice> findById(int id);
    public Iterable<Voice> findByBook(int bookId);
    public Voice save(Voice voice);
}
