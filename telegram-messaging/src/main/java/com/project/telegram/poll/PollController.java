package com.project.telegram.poll;

import com.project.telegram.dto.GeneralResponse;
import com.project.telegram.poll.request.Vote;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("polls")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PollController {
    private final PollService pollService;

    @PostMapping
    public ResponseEntity<Poll> createPoll(@RequestBody Poll poll) {
        return ResponseEntity.ok(pollService.createPoll(poll));
    }

    @GetMapping
    public ResponseEntity<List<Poll>> getPolls() {
        return ResponseEntity.ok(pollService.getAllPolls());
    }

    @GetMapping("{id}")
    public ResponseEntity<Poll> getPoll(@PathVariable Long id) {
        return ResponseEntity.ok(pollService.getPollById(id));
    }

    @PostMapping("vote")
    public ResponseEntity<GeneralResponse> vote(@RequestBody Vote vote) {
        pollService.vote(vote.getPollId(), vote.getOptionIndex());

        return ResponseEntity.ok(GeneralResponse.success("Voted poll with id: " + vote.getPollId() + " successfully"));
    }
}
