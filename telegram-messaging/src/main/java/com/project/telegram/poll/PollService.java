package com.project.telegram.poll;

import com.project.telegram.exception.BusinessException;
import com.project.telegram.exception.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PollService {
    private final PollRepository pollRepository;

    public Poll createPoll(Poll poll) {
        return pollRepository.save(poll);
    }

    public List<Poll> getAllPolls() {
        return pollRepository.findAll();
    }

    public Poll getPollById(Long id) {
        return pollRepository.findById(id)
                .orElseThrow(() -> BusinessException.of(ExceptionType.POLL_NOT_EXISTS));
    }

    public void vote(Long pollId, int optionIndex) {
        // Get Poll from DB
        var poll = getPollById(pollId);

        // Get All Options
        var options = poll.getOptions();

        // If Index for vote is not valid, throw error
        if (optionIndex < 0 || optionIndex >= options.size()) {
            throw BusinessException.of(ExceptionType.INVALID_OPTION_INDEX);
        }

        // Get selected option
        OptionVote selectedOption = options.get(optionIndex);

        // Increment vote for selected option
        selectedOption.setVoteCount(selectedOption.getVoteCount() + 1
        );

        // Save incremented vote option into the database
        pollRepository.save(poll);
    }
}
