package com.twitter.frigate.pushservice.model.ibis

import com.twitter.frigate.pushservice.model.ListRecommendationPushCandidate
import com.twitter.util.Future

trait ListIbis2Hydrator extends Ibis2HydratorForCandidate {
  self: ListRecommendationPushCandidate =>

  override lazy val senderId: Option[Long] = Some(0L)

  override lazy val modelValues: Future[Map[String, String]] =
    Future.join(listName, listOwnerId).map {
      case (nameOpt, authorId) =>
        Map(
          "list" -> listId.toString,
          "list_name" -> nameOpt
            .getOrElse(""),
          "list_author" -> s"${authorId.getOrElse(0L)}"
        )
    }
}
