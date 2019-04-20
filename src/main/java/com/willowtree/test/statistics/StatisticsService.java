package com.willowtree.test.statistics;

import com.willowtree.test.statistics.data.Event;
import com.willowtree.test.statistics.data.UserStatistics;
import com.willowtree.test.statistics.repository.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatisticsService {

    Logger log = LoggerFactory.getLogger(StatisticsService.class);
    @Autowired
    EventRepository repository;
    @Value("${namegame-statistics.default.countOfLeaders}")
    private int numOfLeaders;

    /**
     * Fetches the statistics for a user.
     *
     * @param userId
     * @return user statistics
     */
    public UserStatistics getStatisticByUser(String userId) {
        log.debug("Fetching statics for user {}", userId);
        List<Event> events = repository.findByUserId(userId);
        return extractStatisticsFromEvents(events, userId);
    }

    /**
     * Fetches the leader board entries.
     *
     * @param count number of top users to be shown; it's optional
     * @return leader board entries
     */
    public List<UserStatistics> getLeaderBoard(Integer count) {
        List<Event> events = repository.findAll();
        Map<String, List<Event>> eventsByUserIdMap = new HashMap<>();
        for (Event event : events) {
            if (eventsByUserIdMap.containsKey(event.getUserId())) {
                eventsByUserIdMap.get(event.getUserId()).add(event);
            } else {
                List<Event> eventList = new ArrayList<>();
                eventList.add(event);
                eventsByUserIdMap.put(event.getUserId(), eventList);
            }
        }
        List<UserStatistics> allUserStatistics = new ArrayList<>();
        for (Map.Entry<String, List<Event>> eventEntry : eventsByUserIdMap.entrySet()) {
            allUserStatistics.add(
                    extractStatisticsFromEvents(eventEntry.getValue(), eventEntry.getKey()));
        }
        if (count != null) {
            numOfLeaders = count;
        }
        return allUserStatistics.stream().sorted(
                Comparator.comparing(
                        UserStatistics::getAvgTimePerQuestion)).limit(numOfLeaders).collect(Collectors.toList());
    }


    /**
     *
     * Helper method to populate @{@link UserStatistics} from collection of @{@link Event}.
     *
     * @param events collection of events
     * @param userId user id
     * @return user statistics
     */
    private UserStatistics extractStatisticsFromEvents(List<Event> events, String userId) {
        Map<String, List<Event>> eventsByQuestionIdMap = new HashMap<>();
        for(Event event : events) {
            if(eventsByQuestionIdMap.containsKey(event.getQuestionId())) {
                eventsByQuestionIdMap.get(event.getQuestionId()).add(event);
            } else {
                List<Event> eventList = new ArrayList<>();
                eventList.add(event);
                eventsByQuestionIdMap.put(event.getQuestionId(), eventList);
            }
        }
        UserStatistics statistics = new UserStatistics(userId);
        statistics.setNumOfQuestions(eventsByQuestionIdMap.keySet().size());
        long correctAttempts = 0;
        long incorrectAttempts = 0;
        long totalTime = 0;
        for (Map.Entry<String, List<Event>> eventEntry : eventsByQuestionIdMap.entrySet()) {
            long correctAttempt = eventEntry.getValue().stream().filter(e -> (e.isCorrect())).count();
            correctAttempts += correctAttempt;
            incorrectAttempts += eventEntry.getValue().stream().filter(e -> (!e.isCorrect())).count();
            if (correctAttempt > 0) {
                Date maxTime = eventEntry.getValue().stream().map(
                        e -> e.getOccuredAt()).max(Comparator.comparing( Date ::getTime )).get();
                Date minTime = eventEntry.getValue().stream().map(
                        e -> e.getOccuredAt()).min(Comparator.comparing( Date ::getTime )).get();
                totalTime += maxTime.getTime() - minTime.getTime();
            }
        }
        statistics.setNumOfCorrectAttempts(correctAttempts);
        statistics.setNumOfIncorrectAttempts(incorrectAttempts);
        statistics.setAvgTimePerQuestion((totalTime/(statistics.getNumOfQuestions() * 1000)));
        return statistics;
    }
}