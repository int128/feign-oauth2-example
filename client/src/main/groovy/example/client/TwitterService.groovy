package example.client

import groovy.util.logging.Slf4j
import org.springframework.stereotype.Service

import javax.inject.Inject

@Slf4j
@Service
class TwitterService {
    @Inject
    TwitterClient client

    TwitterSearch search(String query) {
        def twitterSearch = client.search(query)
        log.info("Received from Twitter API: $twitterSearch")
        twitterSearch
    }
}
