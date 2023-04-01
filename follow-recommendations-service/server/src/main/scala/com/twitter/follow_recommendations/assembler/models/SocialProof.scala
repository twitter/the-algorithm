package com.twitter.follow_recommendations.assembler.models

import com.twitter.stringcenter.client.core.ExternalString

sealed trait SocialProof

case class GeoContextProof(popularInCountryText: ExternalString) extends SocialProof
case class FollowedByUsersProof(text1: ExternalString, text2: ExternalString, textN: ExternalString)
    extends SocialProof

sealed trait SocialText {
  def text: String
}

case class GeoSocialText(text: String) extends SocialText
case class FollowedByUsersText(text: String) extends SocialText
