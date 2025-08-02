package com.twitter.frigate.pushservice.predicate

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.frigate.common.base.SocialContetwittertActions
import com.twitter.frigate.common.base.SocialContetwittertUserDetails
import com.twitter.frigate.common.base.TargetInfo
import com.twitter.frigate.common.base.TargetUser
import com.twitter.frigate.common.base.TweetAuthor
import com.twitter.frigate.common.base.TweetAuthorDetails
import com.twitter.frigate.common.base.TweetCandidate
import com.twitter.frigate.common.base.TweetDetails
import com.twitter.frigate.common.candidate.FrigateHistory
import com.twitter.frigate.common.candidate.TargetABDecider
import com.twitter.frigate.common.candidate.TweetImpressionHistory
import com.twitter.frigate.common.predicate.socialcontetwittert.{Predicates => SocialContetwittertPredicates, _}
import com.twitter.frigate.common.predicate.tweet._
import com.twitter.frigate.pushservice.model.PushTypes.PushCandidate
import com.twitter.frigate.pushservice.predicate.ntab_caret_fatigue.NtabCaretClickContFnFatiguePredicate
import com.twitter.hermit.predicate.NamedPredicate

class PreRankingPredicatesBuilder(
)(
  implicit statsReceiver: StatsReceiver) {

  private val SocialProofPredicates = List[NamedPredicate[PushCandidate]](
    SocialContetwittertPredicates
      .authorInSocialContetwittert()
      .applyOnlyToTweetAuthorWithSocialContetwittertActions
      .withName("author_social_contetwittert"),
    SocialContetwittertPredicates
      .selfInSocialContetwittert[TargetUser, SocialContetwittertActions with TargetInfo[TargetUser]]()
      .applyOnlyToSocialContetwittertActionsWithTargetUser
      .withName("self_social_contetwittert"),
    SocialContetwittertPredicates
      .duplicateSocialContetwittert[SocialContetwittertActions]()
      .applyOnlyToSocialContetwittertActions
      .withName("duplicate_social_contetwittert"),
    SocialContetwittertPredicates
      .socialContetwittertProtected[SocialContetwittertUserDetails]()
      .applyOnlyToSocialContetwittertUserDetails
      .withName("social_contetwittert_protected"),
    SocialContetwittertPredicates
      .socialContetwittertUnsuitable[SocialContetwittertUserDetails]()
      .applyOnlyToSocialContetwittertUserDetails
      .withName("social_contetwittert_unsuitable"),
    SocialContetwittertPredicates
      .socialContetwittertBlink[SocialContetwittertUserDetails]()
      .applyOnlyToSocialContetwittertUserDetails
      .withName("social_contetwittert_blink")
  )

  private val CommonPredicates = List[NamedPredicate[PushCandidate]](
    PredicatesForCandidate.candidateEnabledForEmailPredicate(),
    PredicatesForCandidate.openAppEtwitterperimentUserCandidateAllowList(statsReceiver)
  )

  private val TweetPredicates = List[NamedPredicate[PushCandidate]](
    PredicatesForCandidate.tweetCandidateWithLessThan2SocialContetwittertsIsAReply.applyOnlyToTweetCandidatesWithSocialContetwittertActions
      .withName("tweet_candidate_with_less_than_2_social_contetwitterts_is_not_a_reply"),
    PredicatesForCandidate.filterOONCandidatePredicate(),
    PredicatesForCandidate.oldTweetRecsPredicate.applyOnlyToTweetCandidateWithTargetAndABDeciderAndMatwitterTweetAge
      .withName("old_tweet"),
    DuplicatePushTweetPredicate
      .apply[
        TargetUser with FrigateHistory,
        TweetCandidate with TargetInfo[TargetUser with FrigateHistory]
      ]
      .applyOnlyToTweetCandidateWithTargetAndFrigateHistory
      .withName("duplicate_push_tweet"),
    DuplicateEmailTweetPredicate
      .apply[
        TargetUser with FrigateHistory,
        TweetCandidate with TargetInfo[TargetUser with FrigateHistory]
      ]
      .applyOnlyToTweetCandidateWithTargetAndFrigateHistory
      .withName("duplicate_email_tweet"),
    TweetAuthorPredicates
      .recTweetAuthorUnsuitable[TweetCandidate with TweetAuthorDetails]
      .applyOnlyToTweetCandidateWithTweetAuthorDetails
      .withName("tweet_author_unsuitable"),
    TweetObjectEtwitteristsPredicate[
      TweetCandidate with TweetDetails
    ].applyOnlyToTweetCandidatesWithTweetDetails
      .withName("tweet_object_etwitterists"),
    TweetImpressionPredicate[
      TargetUser with TweetImpressionHistory,
      TweetCandidate with TargetInfo[TargetUser with TweetImpressionHistory]
    ].applyOnlyToTweetCandidateWithTargetAndTweetImpressionHistory
      .withStats(statsReceiver.scope("tweet_impression"))
      .withName("tweet_impression"),
    SelfTweetPredicate[
      TargetUser,
      TweetAuthor with TargetInfo[TargetUser]]().applyOnlyToTweetAuthorWithTargetInfo
      .withName("self_author"),
    PredicatesForCandidate.tweetIsNotAreply.applyOnlyToTweetCandidateWithoutSocialContetwittertWithTweetDetails
      .withName("tweet_candidate_not_a_reply"),
    PredicatesForCandidate.f1CandidateIsNotAReply.applyOnlyToF1CandidateWithTargetAndABDecider
      .withName("f1_candidate_is_not_a_reply"),
    PredicatesForCandidate.outOfNetworkTweetCandidateIsNotAReply.applyOnlyToOutOfNetworkTweetCandidateWithTargetAndABDecider
      .withName("out_of_network_tweet_candidate_is_not_a_reply"),
    PredicatesForCandidate.outOfNetworkTweetCandidateEnabledCrTag.applyOnlyToOutOfNetworkTweetCandidateWithTargetAndABDecider
      .withName("out_of_network_tweet_candidate_enabled_crtag"),
    PredicatesForCandidate.outOfNetworkTweetCandidateEnabledCrtGroup.applyOnlyToOutOfNetworkTweetCandidateWithTargetAndABDecider
      .withName("out_of_network_tweet_candidate_enabled_crt_group"),
    OutOfNetworkCandidatesQualityPredicates
      .oonTweetLengthBasedPrerankingPredicate(characterBased = true)
      .applyOnlyToOutOfNetworkTweetCandidateWithTargetAndABDecider
      .withName("oon_tweet_char_length_too_short"),
    OutOfNetworkCandidatesQualityPredicates
      .oonTweetLengthBasedPrerankingPredicate(characterBased = false)
      .applyOnlyToOutOfNetworkTweetCandidateWithTargetAndABDecider
      .withName("oon_tweet_word_length_too_short"),
    PredicatesForCandidate
      .protectedTweetF1EtwitteremptPredicate[
        TargetUser with TargetABDecider,
        TweetCandidate with TweetAuthorDetails with TargetInfo[
          TargetUser with TargetABDecider
        ]
      ]
      .applyOnlyToTweetCandidateWithAuthorDetailsWithTargetABDecider
      .withName("f1_etwitterempt_tweet_author_protected"),
  )

  private val SgsPreRankingPredicates = List[NamedPredicate[PushCandidate]](
    SGSPredicatesForCandidate.authorBeingFollowed.applyOnlyToAuthorBeingFollowPredicates
      .withName("author_not_being_followed"),
    SGSPredicatesForCandidate.authorNotBeingDeviceFollowed.applyOnlyToBasicTweetPredicates
      .withName("author_being_device_followed"),
    SGSPredicatesForCandidate.recommendedTweetAuthorAcceptableToTargetUser.applyOnlyToBasicTweetPredicates
      .withName("recommended_tweet_author_not_acceptable_to_target_user"),
    SGSPredicatesForCandidate.disableInNetworkTweetPredicate.applyOnlyToBasicTweetPredicates
      .withName("enable_in_network_tweet"),
    SGSPredicatesForCandidate.disableOutNetworkTweetPredicate.applyOnlyToBasicTweetPredicates
      .withName("enable_out_network_tweet")
  )

  private val SeeLessOftenPredicates = List[NamedPredicate[PushCandidate]](
    NtabCaretClickContFnFatiguePredicate
      .ntabCaretClickContFnFatiguePredicates(
      )
      .withName("seelessoften_cont_fn_fatigue")
  )

  final def build(): List[NamedPredicate[PushCandidate]] = {
    TweetPredicates ++
      CommonPredicates ++
      SocialProofPredicates ++
      SgsPreRankingPredicates ++
      SeeLessOftenPredicates
  }
}

object PreRankingPredicates {
  def apply(
    statsReceiver: StatsReceiver
  ): List[NamedPredicate[PushCandidate]] =
    new PreRankingPredicatesBuilder()(statsReceiver).build()
}
