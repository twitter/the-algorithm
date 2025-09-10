package com.twitter.tweet_mixer.candidate_source.ndr_ann

case class DRMultipleANNQuery(
  annKeys: Seq[DRANNKey],
  enableCache: Boolean)

case class EmbeddingMultipleANNQuery(
  annKeys: Seq[EmbeddingANNKey],
  enableCache: Boolean)

case class ContentEmbeddingMultipleUserANNQuery(
  annKeys: Seq[ContentEmbeddingUserANNKey],
  enableCache: Boolean)
