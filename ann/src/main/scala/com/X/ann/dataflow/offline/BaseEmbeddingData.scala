package com.X.ann.dataflow.offline

trait BaseEmbeddingData {
  val entityId: Option[Long]
  val embedding: Seq[Double]
}
