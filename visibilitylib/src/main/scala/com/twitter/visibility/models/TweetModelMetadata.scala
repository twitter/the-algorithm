package com.twitter.visibility.models

import com.twitter.spam.rtf.{thriftscala => s}

case class TweetModelMetadata(
  version: Option[Int] = None,
  calibratedLanguage: Option[String] = None)

object TweetModelMetadata {

  def fromThrift(metadata: s.ModelMetadata): Option[TweetModelMetadata] = {
    metadata match {
      case s.ModelMetadata.ModelMetadataV1(s.ModelMetadataV1(version, calibratedLanguage)) =>
        Some(TweetModelMetadata(version, calibratedLanguage))
      case _ => None
    }
  }

  def toThrift(metadata: TweetModelMetadata): s.ModelMetadata = {
    s.ModelMetadata.ModelMetadataV1(
      s.ModelMetadataV1(metadata.version, metadata.calibratedLanguage))
  }
}
