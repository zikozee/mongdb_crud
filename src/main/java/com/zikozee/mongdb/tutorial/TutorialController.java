package com.zikozee.mongdb.tutorial;

import com.zikozee.mongdb.tutorial.dto.TutorialDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/mongo/api")
@RequiredArgsConstructor
public class TutorialController {

    private final TutorialRepository tutorialRepository;
    private final ModelMapper modelMapper;

    @GetMapping("/tutorials")
    public ResponseEntity<List<Tutorial>> getAllTutorials(@RequestParam(required = false) String title) {

        if(ObjectUtils.isEmpty(title)) return new ResponseEntity<>(tutorialRepository.findAll(), HttpStatus.OK);

        List<Tutorial> tutorials = tutorialRepository.findByTitleContaining(title);

        if(tutorials.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);

       return new ResponseEntity<>(tutorials, HttpStatus.OK);
    }

    @GetMapping("/tutorials/{id}")
    public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id") String id) {

        Optional<Tutorial> tutorial = tutorialRepository.findById(id);

        return tutorial.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/tutorials")
    public ResponseEntity<Tutorial> createTutorial(@RequestBody TutorialDTO tutorialDTO) {
        tutorialDTO.setId(null);
        Tutorial tutorial = modelMapper.map(tutorialDTO, Tutorial.class);
        Tutorial persistedTutorial =  tutorialRepository.insert(tutorial);

        return new ResponseEntity<>(persistedTutorial, HttpStatus.CREATED);
    }

    @PutMapping("/tutorials")
    public ResponseEntity<Tutorial> updateTutorial(@RequestBody TutorialDTO tutorialDTO) {
        Optional<Tutorial> tutorial = tutorialRepository.findById(tutorialDTO.getId());

        if(tutorial.isPresent()){
            Tutorial tutorial1 = modelMapper.map(tutorialDTO, Tutorial.class);
            return  new ResponseEntity<>(tutorialRepository.save(tutorial1), HttpStatus.OK);
        }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/tutorials/{id}")
    public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") String id) {
        Optional<Tutorial> tutorial = tutorialRepository.findById(id);
        if(tutorial.isPresent()){
            tutorialRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/tutorials")
    public ResponseEntity<HttpStatus> deleteAllTutorials() {
        tutorialRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/tutorials/published")
    public ResponseEntity<List<Tutorial>> findByPublished() {
        List<Tutorial> tutorials = tutorialRepository.findByPublished(true);
        if (tutorials.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(tutorials, HttpStatus.OK);
    }

}
