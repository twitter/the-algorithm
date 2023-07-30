package com.X.frigate.pushservice.model.ibis

import com.X.frigate.pushservice.model.PushTypes.PushCandidate
import com.X.frigate.pushservice.model.MagicFanoutEventHydratedCandidate
import com.X.frigate.pushservice.params.PushConstants
import com.X.frigate.pushservice.params.PushFeatureSwitchParams
import com.X.frigate.pushservice.predicate.magic_fanout.MagicFanoutPredicatesUtil
import com.X.frigate.pushservice.util.PushIbisUtil._
import com.X.util.Future

trait MagicFanoutNewsEventIbis2Hydrator extends Ibis2HydratorForCandidate {
  self: PushCandidate with MagicFanoutEventHydratedCandidate =>

  override lazy val senderId: Option[Long] = {
    val isUgmMoment = self.semanticCoreEntityTags.values.flatten.toSet
      .contains(MagicFanoutPredicatesUtil.UgmMomentTag)

    owningXUserIds.headOption match {
      case Some(owningXUserId)
          if isUgmMoment && target.params(
            PushFeatureSwitchParams.MagicFanoutNewsUserGeneratedEventsEnable) =>
        Some(owningXUserId)
      case _ => None
    }
  }

  lazy val stats = self.statsReceiver.scope("MagicFanout")
  lazy val defaultImageCounter = stats.counter("default_image")
  lazy val requestImageCounter = stats.counter("request_num")
  lazy val noneImageCounter = stats.counter("none_num")

  private def getModelValueMediaUrl(
    urlOpt: Option[String],
    mapKey: String
  ): Option[(String, String)] = {
    requestImageCounter.incr()
    urlOpt match {
      case Some(PushConstants.DefaultEventMediaUrl) =>
        defaultImageCounter.incr()
        None
      case Some(url) => Some(mapKey -> url)
      case None =>
        noneImageCounter.incr()
        None
    }
  }

  private lazy val eventModelValuesFut: Future[Map[String, String]] = {
    for {
      title <- eventTitleFut
      squareImageUrl <- squareImageUrlFut
      primaryImageUrl <- primaryImageUrlFut
      eventDescriptionOpt <- eventDescriptionFut
    } yield {

      val authorId = owningXUserIds.headOption match {
        case Some(author)
            if target.params(PushFeatureSwitchParams.MagicFanoutNewsUserGeneratedEventsEnable) =>
          Some("author" -> author.toString)
        case _ => None
      }

      val eventDescription = eventDescriptionOpt match {
        case Some(description)
            if target.params(PushFeatureSwitchParams.MagicFanoutNewsEnableDescriptionCopy) =>
          Some("event_description" -> description)
        case _ =>
          None
      }

      Map(
        "event_id" -> s"$eventId",
        "event_title" -> title
      ) ++
        getModelValueMediaUrl(squareImageUrl, "square_media_url") ++
        getModelValueMediaUrl(primaryImageUrl, "media_url") ++
        authorId ++
        eventDescription
    }
  }

  private lazy val topicValuesFut: Future[Map[String, String]] = {
    if (target.params(PushFeatureSwitchParams.EnableTopicCopyForMF)) {
      followedTopicLocalizedEntities.map(_.headOption).flatMap {
        case Some(localizedEntity) =>
          Future.value(Map("topic_name" -> localizedEntity.localizedNameForDisplay))
        case _ =>
          ergLocalizedEntities.map(_.headOption).map {
            case Some(localizedEntity)
                if target.params(PushFeatureSwitchParams.EnableTopicCopyForImplicitTopics) =>
              Map("topic_name" -> localizedEntity.localizedNameForDisplay)
            case _ => Map.empty[String, String]
          }
      }
    } else {
      Future.value(Map.empty[String, String])
    }
  }

  override lazy val modelValues: Future[Map[String, String]] =
    mergeFutModelValues(super.modelValues, mergeFutModelValues(eventModelValuesFut, topicValuesFut))

}
