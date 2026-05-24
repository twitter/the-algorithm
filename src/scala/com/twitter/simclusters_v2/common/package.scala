package com.twitter.simclusters_v2

package object common {

  type TweetId = Long
  type UserId = Long
  type ClusterId = Int
  type SemanticCoreEntityId = Long // Use TopicId if it's a Topic related project.
  type UTTEntityId = Long
  type Timestamp = Long
  type Language = String
  type Country = String
  type LocaleEntity = (Long, Language)
  type TopicId = Long
  type GroupId = Long
  type SpaceId = String
}
