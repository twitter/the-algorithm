package com.twitter.frigate.pushservice.model.ibis

import com.twitter.frigate.common.base.SocialContetwittertAction
import com.twitter.frigate.common.base.SocialContetwittertActions
import com.twitter.frigate.pushservice.model.PushTypes.PushCandidate
import com.twitter.frigate.pushservice.util.CandidateUtil
import com.twitter.frigate.pushservice.util.PushIbisUtil
import com.twitter.util.Future

trait RankedSocialContetwittertIbis2Hydrator {
  self: PushCandidate with SocialContetwittertActions =>

  lazy val socialContetwittertModelValues: Future[Map[String, String]] =
    rankedSocialContetwittertActionsFut.map(rankedSocialContetwittertActions =>
      PushIbisUtil.getSocialContetwittertModelValues(rankedSocialContetwittertActions.map(_.userId)))

  lazy val rankedSocialContetwittertActionsFut: Future[Seq[SocialContetwittertAction]] =
    CandidateUtil.getRankedSocialContetwittert(
      socialContetwittertActions,
      target.seedsWithWeight,
      defaultToRecency = false)
}
