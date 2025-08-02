package com.twitter.follow_recommendations.assembler.models

import com.twitter.stringcenter.client.core.EtwitterternalString

sealed trait SocialProof

case class GeoContetwittertProof(popularInCountryTetwittert: EtwitterternalString) etwittertends SocialProof
case class FollowedByUsersProof(tetwittert1: EtwitterternalString, tetwittert2: EtwitterternalString, tetwittertN: EtwitterternalString)
    etwittertends SocialProof

sealed trait SocialTetwittert {
  def tetwittert: String
}

case class GeoSocialTetwittert(tetwittert: String) etwittertends SocialTetwittert
case class FollowedByUsersTetwittert(tetwittert: String) etwittertends SocialTetwittert
