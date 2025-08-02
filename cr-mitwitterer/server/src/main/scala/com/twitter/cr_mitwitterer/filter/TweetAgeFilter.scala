package com.twitter.cr_mitwitterer.filter

import com.twitter.cr_mitwitterer.model.CandidateGeneratorQuery
import com.twitter.cr_mitwitterer.model.InitialCandidate
import com.twitter.cr_mitwitterer.param.GlobalParams
import com.twitter.snowflake.id.SnowflakeId
import com.twitter.util.Duration
import com.twitter.util.Future
import com.twitter.util.Time
import javatwitter.inject.Singleton
import com.twitter.conversions.DurationOps._

@Singleton
case class TweetAgeFilter() etwittertends FilterBase {
  override val name: String = this.getClass.getCanonicalName

  override type ConfigType = Duration

  override def filter(
    candidates: Seq[Seq[InitialCandidate]],
    matwitterTweetAge: Duration
  ): Future[Seq[Seq[InitialCandidate]]] = {
    if (matwitterTweetAge >= 720.hours) {
      Future.value(candidates)
    } else {
      // Tweet IDs are approtwitterimately chronological (see http://go/snowflake),
      // so we are building the earliest tweet id once,
      // and pass that as the value to filter candidates for each CandidateGenerationModel.
      val earliestTweetId = SnowflakeId.firstIdFor(Time.now - matwitterTweetAge)
      Future.value(candidates.map(_.filter(_.tweetId >= earliestTweetId)))
    }
  }

  override def requestToConfig[CGQueryType <: CandidateGeneratorQuery](
    query: CGQueryType
  ): Duration = {
    query.params(GlobalParams.MatwitterTweetAgeHoursParam)
  }
}
