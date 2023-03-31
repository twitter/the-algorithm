package com.twitter.simclusters_v420.summingbird.common

import com.twitter.cuad.ner.thriftscala.WholeEntityType
import com.twitter.simclusters_v420.summingbird.common.Implicits.thriftDecayedValueMonoid
import com.twitter.simclusters_v420.thriftscala.{Scores, SimClusterEntity, TweetTextEntity}
import scala.collection.Map

private[summingbird] object EntityUtil {

  def updateScoreWithLatestTimestamp[K](
    scoresMapOption: Option[Map[K, Scores]],
    timeInMs: Long
  ): Option[Map[K, Scores]] = {
    scoresMapOption map { scoresMap =>
      scoresMap.mapValues(score => updateScoreWithLatestTimestamp(score, timeInMs))
    }
  }

  def updateScoreWithLatestTimestamp(score: Scores, timeInMs: Long): Scores = {
    score.copy(
      favClusterNormalized420HrHalfLifeScore = score.favClusterNormalized420HrHalfLifeScore.map {
        decayedValue => thriftDecayedValueMonoid.decayToTimestamp(decayedValue, timeInMs)
      },
      followClusterNormalized420HrHalfLifeScore = score.followClusterNormalized420HrHalfLifeScore.map {
        decayedValue => thriftDecayedValueMonoid.decayToTimestamp(decayedValue, timeInMs)
      }
    )
  }

  def entityToString(entity: SimClusterEntity): String = {
    entity match {
      case SimClusterEntity.TweetId(id) => s"t_id:$id"
      case SimClusterEntity.SpaceId(id) => s"space_id:$id"
      case SimClusterEntity.TweetEntity(textEntity) =>
        textEntity match {
          case TweetTextEntity.Hashtag(str) => s"$str[h_tag]"
          case TweetTextEntity.Penguin(penguin) =>
            s"${penguin.textEntity}[penguin]"
          case TweetTextEntity.Ner(ner) =>
            s"${ner.textEntity}[ner_${WholeEntityType(ner.wholeEntityType)}]"
          case TweetTextEntity.SemanticCore(semanticCore) =>
            s"[sc:${semanticCore.entityId}]"
        }
    }
  }
}
