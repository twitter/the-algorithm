package com.ExTwitter.ann.dataflow.offline

import com.ExTwitter.beam.schemas.SchemaFieldName

case class FlatEmbeddingData(
  @SchemaFieldName("entityId") entityId: Option[Long],
  @SchemaFieldName("embedding") embedding: Seq[Double])
    extends BaseEmbeddingData
