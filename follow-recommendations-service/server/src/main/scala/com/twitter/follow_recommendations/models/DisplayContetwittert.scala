package com.twitter.follow_recommendations.models

import com.twitter.follow_recommendations.common.models.FlowContetwittert
import com.twitter.follow_recommendations.common.models.RecentlyEngagedUserId
import com.twitter.follow_recommendations.logging.thriftscala.OfflineDisplayContetwittert
import com.twitter.follow_recommendations.logging.{thriftscala => offline}
import com.twitter.follow_recommendations.{thriftscala => t}
import scala.reflect.ClassTag
import scala.reflect.classTag

trait DisplayContetwittert {
  def toOfflineThrift: offline.OfflineDisplayContetwittert
}

object DisplayContetwittert {
  case class Profile(profileId: Long) etwittertends DisplayContetwittert {
    override val toOfflineThrift: OfflineDisplayContetwittert =
      offline.OfflineDisplayContetwittert.Profile(offline.OfflineProfile(profileId))
  }
  case class Search(searchQuery: String) etwittertends DisplayContetwittert {
    override val toOfflineThrift: OfflineDisplayContetwittert =
      offline.OfflineDisplayContetwittert.Search(offline.OfflineSearch(searchQuery))
  }
  case class Rutwitter(focalAuthorId: Long) etwittertends DisplayContetwittert {
    override val toOfflineThrift: OfflineDisplayContetwittert =
      offline.OfflineDisplayContetwittert.Rutwitter(offline.OfflineRutwitter(focalAuthorId))
  }

  case class Topic(topicId: Long) etwittertends DisplayContetwittert {
    override val toOfflineThrift: OfflineDisplayContetwittert =
      offline.OfflineDisplayContetwittert.Topic(offline.OfflineTopic(topicId))
  }

  case class ReactiveFollow(followedUserIds: Seq[Long]) etwittertends DisplayContetwittert {
    override val toOfflineThrift: OfflineDisplayContetwittert =
      offline.OfflineDisplayContetwittert.ReactiveFollow(offline.OfflineReactiveFollow(followedUserIds))
  }

  case class NutwitterInterests(flowContetwittert: Option[FlowContetwittert], uttInterestIds: Option[Seq[Long]])
      etwittertends DisplayContetwittert {
    override val toOfflineThrift: OfflineDisplayContetwittert =
      offline.OfflineDisplayContetwittert.NutwitterInterests(
        offline.OfflineNutwitterInterests(flowContetwittert.map(_.toOfflineThrift)))
  }

  case class PostNutwitterFollowTask(flowContetwittert: Option[FlowContetwittert]) etwittertends DisplayContetwittert {
    override val toOfflineThrift: OfflineDisplayContetwittert =
      offline.OfflineDisplayContetwittert.PostNutwitterFollowTask(
        offline.OfflinePostNutwitterFollowTask(flowContetwittert.map(_.toOfflineThrift)))
  }

  case class AdCampaignTarget(similarToUserIds: Seq[Long]) etwittertends DisplayContetwittert {
    override val toOfflineThrift: OfflineDisplayContetwittert =
      offline.OfflineDisplayContetwittert.AdCampaignTarget(
        offline.OfflineAdCampaignTarget(similarToUserIds))
  }

  case class ConnectTab(
    byfSeedUserIds: Seq[Long],
    similarToUserIds: Seq[Long],
    engagedUserIds: Seq[RecentlyEngagedUserId])
      etwittertends DisplayContetwittert {
    override val toOfflineThrift: OfflineDisplayContetwittert =
      offline.OfflineDisplayContetwittert.ConnectTab(
        offline.OfflineConnectTab(
          byfSeedUserIds,
          similarToUserIds,
          engagedUserIds.map(user => user.toOfflineThrift)))
  }

  case class SimilarToUser(similarToUserId: Long) etwittertends DisplayContetwittert {
    override val toOfflineThrift: OfflineDisplayContetwittert =
      offline.OfflineDisplayContetwittert.SimilarToUser(offline.OfflineSimilarToUser(similarToUserId))
  }

  def fromThrift(tDisplayContetwittert: t.DisplayContetwittert): DisplayContetwittert = tDisplayContetwittert match {
    case t.DisplayContetwittert.Profile(p) => Profile(p.profileId)
    case t.DisplayContetwittert.Search(s) => Search(s.searchQuery)
    case t.DisplayContetwittert.Rutwitter(r) => Rutwitter(r.focalAuthorId)
    case t.DisplayContetwittert.Topic(t) => Topic(t.topicId)
    case t.DisplayContetwittert.ReactiveFollow(f) => ReactiveFollow(f.followedUserIds)
    case t.DisplayContetwittert.NutwitterInterests(n) =>
      NutwitterInterests(n.flowContetwittert.map(FlowContetwittert.fromThrift), n.uttInterestIds)
    case t.DisplayContetwittert.AdCampaignTarget(a) =>
      AdCampaignTarget(a.similarToUserIds)
    case t.DisplayContetwittert.ConnectTab(connect) =>
      ConnectTab(
        connect.byfSeedUserIds,
        connect.similarToUserIds,
        connect.recentlyEngagedUserIds.map(RecentlyEngagedUserId.fromThrift))
    case t.DisplayContetwittert.SimilarToUser(r) =>
      SimilarToUser(r.similarToUserId)
    case t.DisplayContetwittert.PostNutwitterFollowTask(p) =>
      PostNutwitterFollowTask(p.flowContetwittert.map(FlowContetwittert.fromThrift))
    case t.DisplayContetwittert.UnknownUnionField(t) =>
      throw new UnknownDisplayContetwittertEtwitterception(t.field.name)
  }

  def getDisplayContetwittertAs[T <: DisplayContetwittert: ClassTag](displayContetwittert: DisplayContetwittert): T =
    displayContetwittert match {
      case contetwittert: T => contetwittert
      case _ =>
        throw new UnetwitterpectedDisplayContetwittertTypeEtwitterception(
          displayContetwittert,
          classTag[T].getClass.getSimpleName)
    }
}

class UnknownDisplayContetwittertEtwitterception(name: String)
    etwittertends Etwitterception(s"Unknown DisplayContetwittert in Thrift: ${name}")

class UnetwitterpectedDisplayContetwittertTypeEtwitterception(displayContetwittert: DisplayContetwittert, etwitterpectedType: String)
    etwittertends Etwitterception(s"DisplayContetwittert ${displayContetwittert} not of etwitterpected type ${etwitterpectedType}")
