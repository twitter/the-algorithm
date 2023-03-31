package com.twitter.ann.dataflow.offline

import com.twitter.beam.schemas.SchemaFieldName

case class FlatEmbeddingData(
  @SchemaFieldName("entityId") entityId: Option[Long],
  @SchemaFieldName("embedding") embedding: Seq[Double])
    extends BaseEmbeddingData
