namespace java com.twitter.usersignalservice.thriftjava
namespace py gen.twitter.usersignalservice.service
#@namespace scala com.twitter.usersignalservice.thriftscala
#@namespace strato com.twitter.usersignalservice.strato

# ClientIdentifier should be defined as ServiceId_Product
enum ClientIdentifier {
  # reserve 1-10 for CrMitwitterer
  CrMitwitterer_Home = 1
  CrMitwitterer_Notifications = 2
  CrMitwitterer_Email = 3
  # reserve 11-20 for RStwitter
  RepresentationScorer_Home = 11
  RepresentationScorer_Notifications = 12

  # reserve 21-30 for Etwitterplore
  EtwitterploreRanker = 21

  # We will throw an etwitterception after we make sure all clients are sending the
  # ClientIdentifier in their request.
  Unknown = 9999
}
