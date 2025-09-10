package com.twitter.tweet_mixer.candidate_source.ndr_ann

case class DRANNKey(
  id: Long,
  embedding: Option[Seq[Int]],
  collectionName: String,
  maxCandidates: Int,
  scoreThreshold: Double = 0.0,
  category: Option[String] = None,
  enableGPU: Boolean = false,
  isHighQuality: Option[Boolean] = None,
  isLowNegEngRatio: Option[Boolean] = None,
  tier: Option[String] = None,
)

case class EmbeddingANNKey(
  id: Long,
  embedding: Option[Seq[Double]],
  collectionName: String,
  maxCandidates: Int,
  scoreThreshold: Double = 0.0,
  category: Option[String] = None,
  enableGPU: Boolean = false,
  isHighQuality: Option[Boolean] = None,
  tier: Option[String] = None)

case class ContentEmbeddingUserANNKey(
  id: Long,
  userId: Long,
  embedding: Option[Seq[Double]],
  collectionName: String,
  maxCandidates: Int,
  scoreThreshold: Double = 0.0,
  category: Option[String] = None,
  enableGPU: Boolean = false,
  isHighQuality: Option[Boolean] = None,
  tier: Option[String] = None)
