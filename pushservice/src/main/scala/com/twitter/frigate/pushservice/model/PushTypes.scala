package com.twitter.frigate.pushservice.model

import com.twitter.frigate.common.base._
import com.twitter.frigate.common.candidate.UserLanguage
import com.twitter.frigate.common.candidate._
import com.twitter.frigate.data_pipeline.features_common.RequestContetwittertForFeatureStore
import com.twitter.frigate.pushservice.model.candidate.CopyInfo
import com.twitter.frigate.pushservice.model.candidate.MLScores
import com.twitter.frigate.pushservice.model.candidate.QualityScribing
import com.twitter.frigate.pushservice.model.candidate.Scriber
import com.twitter.frigate.pushservice.model.ibis.Ibis2HydratorForCandidate
import com.twitter.frigate.pushservice.model.ntab.NTabRequest
import com.twitter.frigate.pushservice.take.ChannelForCandidate
import com.twitter.frigate.pushservice.target._
import com.twitter.util.Time

object PushTypes {

  trait Target
      etwittertends TargetUser
      with UserDetails
      with TargetWithPushContetwittert
      with TargetDecider
      with TargetABDecider
      with FrigateHistory
      with PushTargeting
      with TargetScoringDetails
      with TweetImpressionHistory
      with CustomConfigForEtwitterpt
      with CaretFeedbackHistory
      with NotificationFeedbackHistory
      with PromptFeedbackHistory
      with HTLVisitHistory
      with MatwitterTweetAge
      with NewUserDetails
      with ResurrectedUserDetails
      with TargetWithSeedUsers
      with MagicFanoutHistory
      with OptOutUserInterests
      with RequestContetwittertForFeatureStore
      with TargetAppPermissions
      with UserLanguage
      with InlineActionHistory
      with TargetPlaces

  trait RawCandidate etwittertends Candidate with TargetInfo[PushTypes.Target] with RecommendationType {

    val createdAt: Time = Time.now
  }

  trait PushCandidate
      etwittertends RawCandidate
      with CandidateScoringDetails
      with MLScores
      with QualityScribing
      with CopyInfo
      with Scriber
      with Ibis2HydratorForCandidate
      with NTabRequest
      with ChannelForCandidate
}
