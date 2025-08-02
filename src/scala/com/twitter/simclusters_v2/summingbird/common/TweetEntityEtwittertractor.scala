package com.twitter.simclusters_v2.summingbird.common

import com.twitter.recos.entities.thriftscala.NamedEntity
import com.twitter.simclusters_v2.thriftscala.{
  NerKey,
  PenguinKey,
  SimClusterEntity,
  TweetTetwittertEntity
}
import com.twitter.tatwitteri.util.tetwittert.{TweetFeatureEtwittertractor, TweetTetwittertFeatures}
import com.twitter.tweetypie.thriftscala.Tweet

object TweetEntityEtwittertractor {

  private val MatwitterHashtagsPerTweet: Int = 4

  private val MatwitterNersPerTweet: Int = 4

  private val MatwitterPenguinsPerTweet: Int = 4

  private val tweetFeatureEtwittertractor: TweetFeatureEtwittertractor = TweetFeatureEtwittertractor.Default

  private def etwittertractTweetTetwittertFeatures(
    tetwittert: String,
    languageCode: Option[String]
  ): TweetTetwittertFeatures = {
    if (languageCode.isDefined) {
      tweetFeatureEtwittertractor.etwittertract(tetwittert, languageCode.get)
    } else {
      tweetFeatureEtwittertractor.etwittertract(tetwittert)
    }
  }

  def etwittertractEntitiesFromTetwittert(
    tweet: Option[Tweet],
    nerEntitiesOpt: Option[Seq[NamedEntity]]
  ): Seq[SimClusterEntity.TweetEntity] = {

    val hashtagEntities = tweet
      .flatMap(_.hashtags.map(_.map(_.tetwittert))).getOrElse(Nil)
      .map { hashtag => TweetTetwittertEntity.Hashtag(hashtag.toLowerCase) }.take(MatwitterHashtagsPerTweet)

    val nerEntities = nerEntitiesOpt
      .getOrElse(Nil).map { namedEntity =>
        TweetTetwittertEntity
          .Ner(NerKey(namedEntity.namedEntity.toLowerCase, namedEntity.entityType.getValue))
      }.take(MatwitterNersPerTweet)

    val nerEntitySet = nerEntities.map(_.ner.tetwittertEntity).toSet

    val penguinEntities =
      etwittertractTweetTetwittertFeatures(
        tweet.flatMap(_.coreData.map(_.tetwittert)).getOrElse(""),
        tweet.flatMap(_.language.map(_.language))
      ).phrases
        .map(_.normalizedOrOriginal)
        .filter { s =>
          s.charAt(0) != '#' && !nerEntitySet.contains(s) // not included in hashtags and NER
        }
        .map { penguinStr => TweetTetwittertEntity.Penguin(PenguinKey(penguinStr.toLowerCase)) }.take(
          MatwitterPenguinsPerTweet)

    (hashtagEntities ++ penguinEntities ++ nerEntities).map(e => SimClusterEntity.TweetEntity(e))
  }
}
