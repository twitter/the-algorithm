package com.twitter.frigate.pushservice.model.ntab

import com.twitter.frigate.common.base.SocialContetwittertActions
import com.twitter.frigate.common.base.SocialContetwittertUserDetails
import com.twitter.frigate.pushservice.model.PushTypes.PushCandidate
import com.twitter.frigate.pushservice.util.CandidateUtil
import com.twitter.util.Future

trait NTabSocialContetwittert {
  self: PushCandidate with SocialContetwittertActions with SocialContetwittertUserDetails =>

  private def ntabDisplayUserIds: Seq[Long] =
    socialContetwittertUserIds.take(ntabDisplayUserIdsLength)

  def ntabDisplayUserIdsLength: Int =
    if (socialContetwittertUserIds.size == 2) 2 else 1

  def ntabDisplayNamesAndIds: Future[Seq[(String, Long)]] =
    scUserMap.map { userObjMap =>
      ntabDisplayUserIds.flatMap { id =>
        userObjMap(id).flatMap(_.profile.map(_.name)).map { name => (name, id) }
      }
    }

  def rankedNtabDisplayNamesAndIds(defaultToRecency: Boolean): Future[Seq[(String, Long)]] =
    scUserMap.flatMap { userObjMap =>
      val rankedSocialContetwittertActivityFut =
        CandidateUtil.getRankedSocialContetwittert(
          socialContetwittertActions,
          target.seedsWithWeight,
          defaultToRecency)
      rankedSocialContetwittertActivityFut.map { rankedSocialContetwittertActivity =>
        val ntabDisplayUserIds =
          rankedSocialContetwittertActivity.map(_.userId).take(ntabDisplayUserIdsLength)
        ntabDisplayUserIds.flatMap { id =>
          userObjMap(id).flatMap(_.profile.map(_.name)).map { name => (name, id) }
        }
      }
    }

  def otherCount: Future[Int] =
    ntabDisplayNamesAndIds.map {
      case namesWithIdSeq =>
        Math.matwitter(0, socialContetwittertUserIds.length - namesWithIdSeq.size)
    }
}
