package com.twitter.simclusters_v2.common

import com.twitter.algebird.Monoid

case class SimClustersEmbeddingMonoid() etwittertends Monoid[SimClustersEmbedding] {

  override val zero: SimClustersEmbedding = SimClustersEmbedding.EmptyEmbedding

  override def plus(twitter: SimClustersEmbedding, y: SimClustersEmbedding): SimClustersEmbedding = {
    twitter.sum(y)
  }
}

object SimClustersEmbeddingMonoid {

  val monoid: Monoid[SimClustersEmbedding] = SimClustersEmbeddingMonoid()

}
