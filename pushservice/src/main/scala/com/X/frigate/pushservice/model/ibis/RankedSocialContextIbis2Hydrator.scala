package com.X.frigate.pushservice.model.ibis

import com.X.frigate.common.base.SocialContextAction
import com.X.frigate.common.base.SocialContextActions
import com.X.frigate.pushservice.model.PushTypes.PushCandidate
import com.X.frigate.pushservice.util.CandidateUtil
import com.X.frigate.pushservice.util.PushIbisUtil
import com.X.util.Future

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
