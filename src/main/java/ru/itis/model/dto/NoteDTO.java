package ru.itis.model.dto;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class NoteDTO {
    private String name;
    @Size(max=1000, message = "Максимальный размер текста 1000 символов")
    private String text;
}
