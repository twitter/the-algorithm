package com.twitter.frigate.pushservice.model.ibis

import com.twitter.frigate.common.base.SocialContextAction
import com.twitter.frigate.common.base.SocialContextActions
import com.twitter.frigate.pushservice.model.PushTypes.PushCandidate
import com.twitter.frigate.pushservice.util.CandidateUtil
import com.twitter.frigate.pushservice.util.PushIbisUtil
import com.twitter.util.Future

trait RankedSocialContextIbis2Hydrator {
  self: PushCandidate with SocialContextActions =>

  lazy val socialContextModelValues: Future[Map[String, String]] =
    rankedSocialContextActionsFut.map(rankedSocialContextActions =>
      PushIbisUtil.getSocialContextModelValues(rankedSocialContextActions.map(_.userId)))

  lazy val rankedSocialContextActionsFut: Future[Seq[SocialContextAction]] =
    CandidateUtil.getRankedSocialContext(
      socialContextActions,
      target.seedsWithWeight,
      defaultToRecency = false)
}
