package ru.itis.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itis.model.dto.NoteDTO;
import ru.itis.model.entity.Note;
import ru.itis.service.NoteService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class NotesController {
    private final NoteService noteService;

    @GetMapping("/notes")
    public String home(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        boolean isAdmin = isAdmin(userDetails);

        model.addAttribute("isAdmin", isAdmin);

        if (isAdmin) {
            model.addAttribute("notes", noteService.findAll());
            model.addAttribute("isMine", false);
        } else {
            model.addAttribute("notes", noteService.findAllByUserName(userDetails.getUsername()));
            model.addAttribute("isMine", true);
        }

        return "notes";
    }

    @GetMapping("/notes/add")
    public String addNote() {
        return "add_note";
    }

    @GetMapping("/notes/my")
    public String myNote(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("notes", noteService.findAllByUserName(userDetails.getUsername()));
        model.addAttribute("isAdmin", isAdmin(userDetails));
        model.addAttribute("isMine", true);
        return "notes";
    }

    @PostMapping("/notes/add")
    public String saveNote(@Valid NoteDTO noteDTO, BindingResult bindingResult, @AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Максимальный размер текста 1000 символов");
            return "add_note";
        }
        Note note = new Note();
        note.setName(noteDTO.getName());
        note.setText(noteDTO.getText());
        note.setUserName(userDetails.getUsername());

        noteService.saveNote(note);
        return "redirect:/notes";
    }

    @PostMapping("/notes/delete")
    public String deleteNote(Long noteId) {
        noteService.deleteById(noteId);
        return "redirect:/notes";
    }

    @GetMapping("/notes/update")
    public String getUpdateNote(Long noteId, Model model) {
        model.addAttribute("note", noteService.findById(noteId));
        model.addAttribute("noteId", noteId);
        return "update_note";
    }

    @PostMapping("/notes/update")
    public String updateNote(Long noteId, NoteDTO noteDTO) {
        Note note = noteService.findById(noteId);
        note.setName(noteDTO.getName());
        note.setText(noteDTO.getText());
        noteService.saveNote(note);
        return "redirect:/notes";
    }

    private boolean isAdmin(UserDetails userDetails) {
        boolean isAdmin = false;
        for (GrantedAuthority authority : userDetails.getAuthorities()) {
            if ("ROLE_ADMIN".equals(authority.getAuthority())) {
                isAdmin = true;
                break;
            }
        }

        return isAdmin;
    }
}