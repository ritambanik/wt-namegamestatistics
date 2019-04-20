package com.willowtree.test.statistics;

import com.willowtree.test.statistics.data.UserStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/statistics")
public class UserStatisticsController {

    @Autowired
    private StatisticsService service;

    @GetMapping("/statistics/{user}")
    public ResponseEntity<UserStatistics> getUserStatistics(@PathVariable("user") String user) {
            return new ResponseEntity<>(service.getStatisticByUser(user), HttpStatus.OK);
    }

    @GetMapping("/statistics/leaderboard")
    public ResponseEntity<List<UserStatistics>> getLeaderBoard(
            @RequestParam(value = "count", required = false) Integer count) {
            return new ResponseEntity<>(service.getLeaderBoard(count), HttpStatus.OK);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleError(RuntimeException ex) {
        return new ResponseEntity<>(
                "Request failed with message: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
