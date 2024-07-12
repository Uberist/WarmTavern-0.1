package org.example.warmtavern.DAO;

import org.example.warmtavern.DAOInterface.VoiceRepository;
import org.example.warmtavern.Entity.Book;
import org.example.warmtavern.Entity.Voice;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcVoiceRepository implements VoiceRepository {
    JdbcTemplate jdbcTemplate;
    public JdbcVoiceRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public Iterable<Voice> findAll() {
        return jdbcTemplate.query(
                "select * from voices",
                this::mapRowToVoice
                );
    }
    @Override
    public Optional<Voice> findById(int id) {
        List<Voice> voices = jdbcTemplate.query(
                "select * from voices where id = ?",
                this::mapRowToVoice,
                id
        );
        return voices.isEmpty() ? Optional.empty() : Optional.of(voices.get(0));
    }

    @Override
    public List<Voice> findByBook(int bookId) {
        List<Integer> voiceIds = jdbcTemplate.query(
                "select * from book_voice where book_id = ?",
                this::mapRowToVoiceId,
                bookId
        );
        List<Voice> voices = new ArrayList<>();
        for (Integer voiceId : voiceIds) {
            voices.add(this.findById(voiceId.intValue()).get());
        }
        return voices.isEmpty() ? null : voices;
    }

    @Override
    public Voice save(Voice voice) {
        jdbcTemplate.update(
                "insert into voices (voice_first_name, voice_last_name, voice_middle_name) values(?, ?, ?)",
                voice.getVoice_first_name(),
                voice.getVoice_last_name(),
                voice.getVoice_middle_name()
        );
        return voice;
    }
    private Integer mapRowToVoiceId(ResultSet rs, int rowNum) throws SQLException {
        return rs.getInt("voice_id");
    }
    private Voice mapRowToVoice(ResultSet row, int rowNum) throws SQLException {
        return new Voice(
                Integer.parseInt(row.getString("id")),
                row.getString("voice_first_name"),
                row.getString("voice_last_name"),
                row.getString("voice_middle_name")
        );
    }
}
