package com.twitter.tweet_mixer.candidate_source.text_embedding_ann

case class TextEmbeddingQuery(
  vector: Seq[Double],
  limit: Int)
