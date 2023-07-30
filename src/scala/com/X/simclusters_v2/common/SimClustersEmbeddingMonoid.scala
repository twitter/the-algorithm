package com.X.simclusters_v2.common

import com.X.algebird.Monoid

case class SimClustersEmbeddingMonoid() extends Monoid[SimClustersEmbedding] {

  override val zero: SimClustersEmbedding = SimClustersEmbedding.EmptyEmbedding

  override def plus(x: SimClustersEmbedding, y: SimClustersEmbedding): SimClustersEmbedding = {
    x.sum(y)
  }
}

object SimClustersEmbeddingMonoid {

  val monoid: Monoid[SimClustersEmbedding] = SimClustersEmbeddingMonoid()

}
