case class Tweet(text: String, author: String, timestamp: Long)
case class User(interests: List[String])

def getRelevance(tweet: Tweet, user: User): Double = {
  // TODO: Implement relevance scoring algorithm
  0.0
}

def sortTweetsByRelevance(tweets: List[Tweet], user: User): List[Tweet] = {
  tweets.sortBy(tweet => -getRelevance(tweet, user))
}

// Example usage:
val tweets = List(
  Tweet("I love dogs", "Alice", 1648777200),
  Tweet("Just ate a delicious pizza", "Bob", 1648777300),
  Tweet("Breaking news: Earthquake in Japan", "CNN", 1648777400),
  Tweet("Check out my new blog post about gardening", "Alice", 1648777500)
)
val user = User(List("dogs", "gardening"))
val relevantTweets = sortTweetsByRelevance(tweets, user)
println(relevantTweets)
