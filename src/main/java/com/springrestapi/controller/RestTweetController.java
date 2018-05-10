package com.springrestapi.controller;

import com.springrestapi.exception.ResourceNotFoundException;
import com.springrestapi.models.Tweet;
import com.springrestapi.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Response;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class RestTweetController {

    @Autowired
    TweetRepository tweetRepository;

    @GetMapping("/tweets")
    public List<Tweet> fetchTweets() {
        System.out.println("Fetching Tweets");
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



    @GetMapping("/retweet/{id}")
    public ResponseEntity<?> retweet(@PathVariable(value = "id") Long tweetId) {
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new ResourceNotFoundException("Tweet", "id", tweetId));

        Tweet retweet = new Tweet();

        retweet.setTweet(tweet.getTweet());
        retweet.setRetweet(1);

        tweetRepository.save(retweet);
        return ResponseEntity.ok().build();
    }

}
