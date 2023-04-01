package com.twitter.simclusters_v2.score

import com.twitter.simclusters_v2.thriftscala.{Score => ThriftScore}

/**
 * A uniform value type for all kinds of Calculation Score.
 **/
case class Score(score: Double) {

  implicit lazy val toThrift: ThriftScore = {
    ThriftScore(score)
  }
}

object Score {

  /**
   * Only support Double Type Thrift score
   */
  implicit val fromThriftScore: ThriftScore => Score = { thriftScore => Score(thriftScore.score) }

}
