package jokes;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteService {
    
    @Autowired
    private VoteRepository voteRepository;
    
    public Vote findByJokeId(Long jokeId) {
        if (voteRepository.findByJokeId(jokeId) == null) {
            Vote v = new Vote(jokeId, 0, 0);
            voteRepository.save(v);
        }
        return voteRepository.findByJokeId(jokeId);
    }
    
    @Transactional
    public void vote(Long id, String value) {
        Vote vote = this.voteRepository.findByJokeId(id);
        if (vote == null) {
            vote = new Vote(id, 0, 0);
        }
        if ("up".equals(value)) {
            vote.setUpVotes(vote.getUpVotes() + 1);
        } else if ("down".equals(value)) {
            vote.setDownVotes(vote.getDownVotes() + 1);
        }
        voteRepository.save(vote);
    }
}
