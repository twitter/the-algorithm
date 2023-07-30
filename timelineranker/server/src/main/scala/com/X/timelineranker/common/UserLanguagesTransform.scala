package com.X.timelineranker.common

import com.X.search.common.constants.thriftscala.ThriftLanguage
import com.X.servo.util.FutureArrow
import com.X.timelineranker.model.RecapQuery
import com.X.timelines.clients.manhattan.LanguageUtils
import com.X.timelines.clients.manhattan.UserMetadataClient
import com.X.timelines.util.FailOpenHandler
import com.X.util.Future
import com.X.service.metastore.gen.thriftscala.UserLanguages

object UserLanguagesTransform {
  val EmptyUserLanguagesFuture: Future[UserLanguages] =
    Future.value(UserMetadataClient.EmptyUserLanguages)
}

/**
 * FutureArrow which fetches user languages
 * It should be run in parallel with the main pipeline which fetches and hydrates CandidateTweets
 */
class UserLanguagesTransform(handler: FailOpenHandler, userMetadataClient: UserMetadataClient)
    extends FutureArrow[RecapQuery, Seq[ThriftLanguage]] {
  override def apply(request: RecapQuery): Future[Seq[ThriftLanguage]] = {
    import UserLanguagesTransform._

    handler {
      userMetadataClient.getUserLanguages(request.userId)
    } { _: Throwable => EmptyUserLanguagesFuture }
  }.map(LanguageUtils.computeLanguages(_))
}
