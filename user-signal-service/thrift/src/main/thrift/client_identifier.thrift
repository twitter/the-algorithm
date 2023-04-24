namespace java com.twitter.usersignalservice.thriftjava
namespace py gen.twitter.usersignalservice.service
#@namespace scala com.twitter.usersignalservice.thriftscala
#@namespace strato com.twitter.usersignalservice.strato

# ClientIdentifier should be defined as ServiceId_Product
enum ClientIdentifier {
  # reserve 1-10 for CrMixer
  CrMixer_Home = 1
  CrMixer_Notifications = 2
  CrMixer_Email = 3
  # reserve 11-20 for RSX
  RepresentationScorer_Home = 11
  RepresentationScorer_Notifications = 12

  # reserve 21-30 for Explore
  ExploreRanker = 21

  # We will throw an exception after we make sure all clients are sending the
  # ClientIdentifier in their request.
  Unknown = 9999
}
