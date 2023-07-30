package com.X.frigate.pushservice.model

import com.X.frigate.common.base._
import com.X.frigate.common.candidate.UserLanguage
import com.X.frigate.common.candidate._
import com.X.frigate.data_pipeline.features_common.RequestContextForFeatureStore
import com.X.frigate.pushservice.model.candidate.CopyInfo
import com.X.frigate.pushservice.model.candidate.MLScores
import com.X.frigate.pushservice.model.candidate.QualityScribing
import com.X.frigate.pushservice.model.candidate.Scriber
import com.X.frigate.pushservice.model.ibis.Ibis2HydratorForCandidate
import com.X.frigate.pushservice.model.ntab.NTabRequest
import com.X.frigate.pushservice.take.ChannelForCandidate
import com.X.frigate.pushservice.target._
import com.X.util.Time

object PushTypes {

  trait Target
      extends TargetUser
      with UserDetails
      with TargetWithPushContext
      with TargetDecider
      with TargetABDecider
      with FrigateHistory
      with PushTargeting
      with TargetScoringDetails
      with TweetImpressionHistory
      with CustomConfigForExpt
      with CaretFeedbackHistory
      with NotificationFeedbackHistory
      with PromptFeedbackHistory
      with HTLVisitHistory
      with MaxTweetAge
      with NewUserDetails
      with ResurrectedUserDetails
      with TargetWithSeedUsers
      with MagicFanoutHistory
      with OptOutUserInterests
      with RequestContextForFeatureStore
      with TargetAppPermissions
      with UserLanguage
      with InlineActionHistory
      with TargetPlaces

  trait RawCandidate extends Candidate with TargetInfo[PushTypes.Target] with RecommendationType {

    val createdAt: Time = Time.now
  }

  trait PushCandidate
      extends RawCandidate
      with CandidateScoringDetails
      with MLScores
      with QualityScribing
      with CopyInfo
      with Scriber
      with Ibis2HydratorForCandidate
      with NTabRequest
      with ChannelForCandidate
}
