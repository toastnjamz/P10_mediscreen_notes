package com.mediscreen.notes.controller;

import com.mediscreen.notes.domain.Note;
import com.mediscreen.notes.domain.Patient;
import com.mediscreen.notes.service.NoteService;
import com.mediscreen.notes.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RestController
public class NoteController {

    @Autowired
    private NoteService noteService;

    @Autowired
    PatientService patientService;

    private static final Logger log = LoggerFactory.getLogger(NoteController.class);

    @GetMapping("/note/list/{patientId}")
    public ModelAndView showAllNotesByPatientId(@PathVariable("patientId") Integer patientId, Model model) {
        ModelAndView mav = new ModelAndView();
        Patient patient = patientService.findPatientInList(patientId);
        if (patient != null) {
            model.addAttribute("noteList", noteService.findAllNotesByPatientId(patientId));
            model.addAttribute("patientId", patientId);
            mav.setViewName("note/list");
        }
        log.info("GET request received for showAllNotesByPatientId()");
        return mav;
    }

    @GetMapping("/note/add/{patientId}")
    public ModelAndView addNote(@PathVariable("patientId") int patientId, Model model) {
        ModelAndView mav = new ModelAndView();
        Patient patient = patientService.findPatientInList(patientId);
        if (patient != null) {
            Note note = new Note();
            note.setPatientId(patientId);
            model.addAttribute("note", note);
            mav.setViewName("note/add");
        }
        log.info("GET request received for addNote()");
        return mav;
    }

    @PostMapping(value = "/note/validate/{patientId}")
    public ModelAndView validate(@PathVariable("patientId") int patientId, @Valid Note note, BindingResult result,
                                 Model model) {
        ModelAndView mav = new ModelAndView();
        Patient patient = patientService.findPatientInList(patientId);
        if (patient != null) {
            if (!result.hasErrors()) {
                noteService.createNote(note);
                model.addAttribute("noteList", noteService.findAllNotesByPatientId(patientId));
                mav.setViewName("redirect:/note/list");
                log.info("Add Note " + note.toString());
                return mav;
            }
        }
        mav.setViewName("note/add/{patientId}");
        return mav;
    }

    @GetMapping("/note/update/{id}")
    public ModelAndView showUpdateForm(@PathVariable("id") String id, Model model) {
        ModelAndView mav = new ModelAndView();
        Note note = noteService.findById(id);
        if (note != null) {
            model.addAttribute("note", note);
            mav.setViewName("note/update");
            log.info("GET request received for showUpdateForm()");
            return mav;
        }
        return mav;
    }

    @PostMapping("/note/update/{id}")
    public ModelAndView updateNote(@PathVariable("id") String id, @Valid Note note,
                                      BindingResult result, Model model) {
        ModelAndView mav = new ModelAndView();
        if (result.hasErrors()) {
            mav.setViewName("note/update");
            return mav;
        }
        note.setId(id);
        noteService.updateNote(note);
        model.addAttribute("note", noteService.findAllNotesByPatientId(note.getPatientId()));
        mav.setViewName("redirect:/note/list");
        log.info("Update Note " + note.toString());
        return mav;
    }

    @GetMapping("/note/delete/{id}")
    public ModelAndView deleteNote(@PathVariable("id") String id, Model model) {
        ModelAndView mav = new ModelAndView();
        Note note = noteService.findById(id);
        if (note != null) {
            noteService.deleteNote(id);
            model.addAttribute("note", noteService.findAllNotesByPatientId(note.getPatientId()));
            mav.setViewName("redirect:/note/list");
            log.info("Delete Note " + note.toString());
            return mav;
        }
        return mav;
    }

}
