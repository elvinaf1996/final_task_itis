package ru.itis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.model.entity.Note;
import ru.itis.repository.NoteRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;

    public boolean saveNote(Note note) {
        noteRepository.saveAndFlush(note);
        return true;
    }

    public List<Note> findAll() {
        return noteRepository.findAll();
    }

    public List<Note> findAllByUserName(String username) {
        return noteRepository.findAllByUserName(username);
    }

    public Note findById(Long id) {
        return noteRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public void deleteById(Long noteId) {
        noteRepository.deleteById(noteId);
    }
}