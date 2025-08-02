package com.twitter.frigate.pushservice.refresh_handler.cross

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.frigate.common.base.SocialContetwittertActions
import com.twitter.frigate.pushservice.model.PushTypes.RawCandidate
import com.twitter.hermit.predicate.Predicate

class CopyPredicates(statsReceiver: StatsReceiver) {
  val alwaysTruePredicate = Predicate
    .from { _: CandidateCopyPair =>
      true
    }.withStats(statsReceiver.scope("always_true_copy_predicate"))

  val unrecognizedCandidatePredicate = alwaysTruePredicate.flip
    .withStats(statsReceiver.scope("unrecognized_candidate"))

  val displaySocialContetwittertPredicate = Predicate
    .from { candidateCopyPair: CandidateCopyPair =>
      candidateCopyPair.candidate match {
        case candidateWithScActions: RawCandidate with SocialContetwittertActions =>
          val socialContetwittertUserIds = candidateWithScActions.socialContetwittertActions.map(_.userId)
          val countSocialContetwittert = socialContetwittertUserIds.size
          val pushCopy = candidateCopyPair.pushCopy

          countSocialContetwittert match {
            case 1 => pushCopy.hasOneDisplaySocialContetwittert && !pushCopy.hasOtherSocialContetwittert
            case 2 => pushCopy.hasTwoDisplayContetwittert && !pushCopy.hasOtherSocialContetwittert
            case c if c > 2 =>
              pushCopy.hasOneDisplaySocialContetwittert && pushCopy.hasOtherSocialContetwittert
            case _ => false
          }

        case _ => false
      }
    }.withStats(statsReceiver.scope("display_social_contetwittert_predicate"))
}
