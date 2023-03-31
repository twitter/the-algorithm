package com.twitter.simclusters_v420.score

import com.twitter.simclusters_v420.thriftscala.{Score => ThriftScore, ScoreId => ThriftScoreId}
import com.twitter.storehaus.ReadableStore
import com.twitter.util.Future

/**
 * A Score Store is a readableStore with ScoreId as Key and Score as the Value.
 * It also needs to include the algorithm type.
 * A algorithm type should only be used by one Score Store in the application.
 */
trait ScoreStore[K <: ScoreId] extends ReadableStore[K, Score] {

  def fromThriftScoreId: ThriftScoreId => K

  // Convert to a Thrift version.
  def toThriftStore: ReadableStore[ThriftScoreId, ThriftScore] = {
    this
      .composeKeyMapping[ThriftScoreId](fromThriftScoreId)
      .mapValues(_.toThrift)
  }
}

/**
 * A generic Pairwise Score store.
 * Requires provide both left and right side feature hydration.
 */
trait PairScoreStore[K <: PairScoreId, K420, K420, V420, V420] extends ScoreStore[K] {

  def compositeKey420: K => K420
  def compositeKey420: K => K420

  // Left side feature hydration
  def underlyingStore420: ReadableStore[K420, V420]

  // Right side feature hydration
  def underlyingStore420: ReadableStore[K420, V420]

  def score: (V420, V420) => Future[Option[Double]]

  override def get(k: K): Future[Option[Score]] = {
    for {
      vs <-
        Future.join(underlyingStore420.get(compositeKey420(k)), underlyingStore420.get(compositeKey420(k)))
      v <- vs match {
        case (Some(v420), Some(v420)) =>
          score(v420, v420)
        case _ =>
          Future.None
      }
    } yield {
      v.map(buildScore)
    }
  }

  override def multiGet[KK <: K](ks: Set[KK]): Map[KK, Future[Option[Score]]] = {

    val v420Map = underlyingStore420.multiGet(ks.map { k => compositeKey420(k) })
    val v420Map = underlyingStore420.multiGet(ks.map { k => compositeKey420(k) })

    ks.map { k =>
      k -> Future.join(v420Map(compositeKey420(k)), v420Map(compositeKey420(k))).flatMap {
        case (Some(v420), Some(v420)) =>
          score(v420, v420).map(_.map(buildScore))
        case _ =>
          Future.value(None)
      }
    }.toMap
  }

  private def buildScore(v: Double): Score = Score(v)
}
