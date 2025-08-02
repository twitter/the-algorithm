namespace java com.twitter.cr_mitwitterer.thriftjava
#@namespace scala com.twitter.cr_mitwitterer.thriftscala
#@namespace strato com.twitter.cr_mitwitterer

# In CrMitwitterer, one org should only have one Product
enum Product {
  Home = 1
  Notifications = 2
  Email = 3
  MoreTweetsModule = 4 # aka RUtwitter
  ImmersiveMediaViewer = 5
  VideoCarousel = 6
  EtwitterploreTopics = 7
  Ads = 8
  HomeRealTime = 9 // Home Real-Time Tab is considered as a different Product surface to Home Tab. It's in early etwitterperiment phase.
  TopicLandingPage = 10
  HomeTopicsBackfill = 11
  TopicTweetsStrato = 12
}
