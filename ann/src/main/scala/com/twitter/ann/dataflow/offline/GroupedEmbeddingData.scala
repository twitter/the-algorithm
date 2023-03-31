package com.twitter.ann.dataflow.offline

import com.twitter.beam.schemas.SchemaFieldName

case class GroupedEmbeddingData(
  @SchemaFieldName("entityId") entityId: Option[Long],
  @SchemaFieldName("embedding") embedding: Seq[Double],
  @SchemaFieldName("groupId") groupId: Option[String],
) extends BaseEmbeddingData
