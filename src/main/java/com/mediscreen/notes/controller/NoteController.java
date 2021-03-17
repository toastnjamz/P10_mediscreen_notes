package com.mediscreen.notes.controller;

import com.mediscreen.notes.domain.note.Note;
import com.mediscreen.notes.domain.patient.Patient;
import com.mediscreen.notes.service.note.NoteService;
import com.mediscreen.notes.service.patient.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@RestController
public class NoteController {

    @Autowired
    private NoteService noteService;

    @Autowired
    PatientService patientService;

    private static final Logger log = LoggerFactory.getLogger(NoteController.class);

    //TODO: Delete test method
    @GetMapping("/getAllNotes/{patientId}")
    public List<Note> getAllNotes(@PathVariable("patientId") Integer patientId) {
        log.info("GET request received for getAllNotes()");
        return noteService.findAllNotesByPatientId(patientId);
    }

    /**
     * HTTP GET request loads a list of notes for the specified patientId
     * @param patientId
     * @param model
     * @return ModelAndView
     */
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

    /**
     * HTTP GET request loads new note entry form for specified patientId
     * @param patientId
     * @param model
     * @return ModelAndView
     */
    @GetMapping("/note/add/{patientId}")
    public ModelAndView addNote(@PathVariable("patientId") int patientId, Model model) {
        ModelAndView mav = new ModelAndView();
        Patient patient = patientService.findPatientInList(patientId);
        if (patient != null) {
            Note note = new Note();
            note.setPatientId(patientId);
            model.addAttribute("note", note);
//            mav.addObject("patientId", patientId);
            mav.setViewName("note/add/{patientId}");
        }
        log.info("GET request received for addNote()");
        return mav;
    }

    /**
     * HTTP POST request validates and creates a new note entry for the specified patientId
     * @param patientId
     * @param note
     * @param result
     * @param model
     * @return ModelAndView
     */
    @PostMapping(value = "/note/validate/{patientId}")
    public ModelAndView validate(@PathVariable("patientId") int patientId, @Valid Note note, BindingResult result,
                                 Model model) {
        ModelAndView mav = new ModelAndView();
        Patient patient = patientService.findPatientInList(patientId);
        if (patient != null) {
            if (!result.hasErrors()) {
                noteService.createNote(note);
                model.addAttribute("noteList", noteService.findAllNotesByPatientId(patientId));
                mav.setViewName("redirect:/note/list/{patientId}");
                log.info("Add Note " + note.toString());
                return mav;
            }
        }
        mav.setViewName("note/add/{patientId}");
        return mav;
    }

    /**
     * HTTP GET request loads an existing note update form
     * @param id
     * @param model
     * @return ModelAndView
     */
    @GetMapping("/note/update/{id}")
    public ModelAndView showUpdateForm(@PathVariable("id") String id, Model model) {
        ModelAndView mav = new ModelAndView();
        Note note = noteService.findById(id);
        if (note != null) {
            model.addAttribute("note", note);
            mav.addObject("id", note.getId());
            mav.addObject("patientId", note.getPatientId());
            mav.setViewName("note/update");
            log.info("GET request received for showUpdateForm()");
            return mav;
        }
        return mav;
    }

    /**
     * HTTP POST request updates the requested note
     * @param id
     * @param note
     * @param result
     * @param model
     * @return ModelAndView
     */
    @PostMapping("/note/update/{id}")
    public ModelAndView updateNote(@PathVariable("id") String id, @Valid Note note,
                                      BindingResult result, Model model) {
        ModelAndView mav = new ModelAndView();
        if (result.hasErrors()) {
            mav.setViewName("note/update");
            return mav;
        }
        note.setId(note.getId());
        noteService.updateNote(note);
        model.addAttribute("noteList", noteService.findAllNotesByPatientId(note.getPatientId()));
        mav.addObject("patientId", note.getPatientId());
        mav.setViewName("redirect:/note/list/{patientId}");
        log.info("Update Note " + note.toString());
        return mav;
    }

    /**
     * HTTP GET request deletes the requested note
     * @param id
     * @param model
     * @return ModelAndView
     */
    @GetMapping("/note/delete/{id}")
    public ModelAndView deleteNote(@PathVariable("id") String id, Model model) {
        ModelAndView mav = new ModelAndView();
        Note note = noteService.findById(id);
        if (note != null) {
            noteService.deleteNote(id);
            mav.addObject("patientId", note.getPatientId());
            model.addAttribute("noteList", noteService.findAllNotesByPatientId(note.getPatientId()));
            mav.setViewName("redirect:/note/list/{patientId}");
            log.info("Delete Note " + note.toString());
            return mav;
        }
        return mav;
    }

}
