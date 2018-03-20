package com.springrestapi.controller;

import com.springrestapi.exception.ResourceNotFoundException;
import com.springrestapi.model.Tweet;
import com.springrestapi.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RestTweetController {

    @Autowired
    TweetRepository tweetRepository;

    @GetMapping("/tweets")
    public List<Tweet> fetchTweets() {
        return tweetRepository.findAll();
    }

    @PostMapping("/tweets")
    public Tweet createTweet(@Valid @RequestBody Tweet tweet) {
        tweet.setTweet(tweet.getTweet());

        Tweet newTweet = tweetRepository.save(tweet);
        return newTweet;
    }

    @GetMapping("/tweets/{id}")
    public Tweet fetchTweetById(@PathVariable(value = "id") Long tweetId) {
        return tweetRepository.findById(tweetId)
                .orElseThrow(() -> new ResourceNotFoundException("Tweet", "id", tweetId));
    }

    @DeleteMapping("/tweets/{id}")
    public ResponseEntity<?> deleteTweet(@PathVariable(value = "id") Long tweetId) {
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new ResourceNotFoundException("Tweet", "id", tweetId));

        tweetRepository.delete(tweet);

        return ResponseEntity.ok().build();
    }

}
