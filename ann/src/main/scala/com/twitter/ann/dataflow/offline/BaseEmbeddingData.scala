package com.twitter.ann.dataflow.offline

trait BaseEmbeddingData {
  val entityId: Option[Long]
  val embedding: Seq[Double]
}
