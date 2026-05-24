package com.twitter.tweet_mixer.service

import com.twitter.product_mixer.core.functional_component.common.access_policy.AccessPolicy
import com.twitter.product_mixer.core.functional_component.common.access_policy.AllowedLdapGroups

object TweetMixerAccessPolicy {

  val DefaultTweetMixerAccessPolicy: Set[AccessPolicy] = 
    Set(AllowedLdapGroups(Set.empty[String]))

  val HomeDebugAccessPolicy: Set[AccessPolicy] =
    Set(AllowedLdapGroups(Set.empty[String]))

  val ExploreDebugAccessPolicy: Set[AccessPolicy] =
    Set(AllowedLdapGroups(Set.empty[String]))
}
