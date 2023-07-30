package com.X.ann.dataflow.offline

import com.X.beam.schemas.SchemaFieldName

case class FlatEmbeddingData(
  @SchemaFieldName("entityId") entityId: Option[Long],
  @SchemaFieldName("embedding") embedding: Seq[Double])
    extends BaseEmbeddingData
