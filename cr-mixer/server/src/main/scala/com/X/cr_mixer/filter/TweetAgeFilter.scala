package com.X.cr_mixer.filter

import com.X.cr_mixer.model.CandidateGeneratorQuery
import com.X.cr_mixer.model.InitialCandidate
import com.X.cr_mixer.param.GlobalParams
import com.X.snowflake.id.SnowflakeId
import com.X.util.Duration
import com.X.util.Future
import com.X.util.Time
import javax.inject.Singleton
import com.X.conversions.DurationOps._

@Singleton
case class TweetAgeFilter() extends FilterBase {
  override val name: String = this.getClass.getCanonicalName

  override type ConfigType = Duration

  override def filter(
    candidates: Seq[Seq[InitialCandidate]],
    maxTweetAge: Duration
  ): Future[Seq[Seq[InitialCandidate]]] = {
    if (maxTweetAge >= 720.hours) {
      Future.value(candidates)
    } else {
      // Tweet IDs are approximately chronological (see http://go/snowflake),
      // so we are building the earliest tweet id once,
      // and pass that as the value to filter candidates for each CandidateGenerationModel.
      val earliestTweetId = SnowflakeId.firstIdFor(Time.now - maxTweetAge)
      Future.value(candidates.map(_.filter(_.tweetId >= earliestTweetId)))
    }
  }

  override def requestToConfig[CGQueryType <: CandidateGeneratorQuery](
    query: CGQueryType
  ): Duration = {
    query.params(GlobalParams.MaxTweetAgeHoursParam)
  }
}
