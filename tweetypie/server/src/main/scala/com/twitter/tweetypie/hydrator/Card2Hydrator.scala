package com.twitter.tweetypie
package hydrator

import com.twitter.etwitterpandodo.thriftscala.Card2
import com.twitter.etwitterpandodo.thriftscala.Card2RequestOptions
import com.twitter.featureswitches.v2.FeatureSwitchResults
import com.twitter.stitch.Stitch
import com.twitter.tweetypie.core.CardReferenceUriEtwittertractor
import com.twitter.tweetypie.core.NonTombstone
import com.twitter.tweetypie.core.ValueState
import com.twitter.tweetypie.repository._
import com.twitter.tweetypie.thriftscala._

object Card2Hydrator {
  type Type = ValueHydrator[Option[Card2], Cttwitter]

  case class Cttwitter(
    urlEntities: Seq[UrlEntity],
    mediaEntities: Seq[MediaEntity],
    cardReference: Option[CardReference],
    underlyingTweetCttwitter: TweetCttwitter,
    featureSwitchResults: Option[FeatureSwitchResults])
      etwittertends TweetCttwitter.Protwittery

  val hydratedField: FieldByPath = fieldByPath(Tweet.Card2Field)
  val hydrationUrlBlockListKey = "card_hydration_blocklist"

  def apply(repo: Card2Repository.Type): ValueHydrator[Option[Card2], Cttwitter] =
    ValueHydrator[Option[Card2], Cttwitter] { (_, cttwitter) =>
      val repoCttwitter = requestOptions(cttwitter)
      val filterURLs = cttwitter.featureSwitchResults
        .flatMap(_.getStringArray(hydrationUrlBlockListKey, false))
        .getOrElse(Seq())

      val requests =
        cttwitter.cardReference match {
          case Some(CardReferenceUriEtwittertractor(cardUri)) =>
            cardUri match {
              case NonTombstone(uri) if !filterURLs.contains(uri) =>
                Seq((UrlCard2Key(uri), repoCttwitter))
              case _ => Nil
            }
          case _ =>
            cttwitter.urlEntities
              .filterNot(e => e.etwitterpanded.etwitterists(filterURLs.contains))
              .map(e => (UrlCard2Key(e.url), repoCttwitter))
        }

      Stitch
        .traverse(requests) {
          case (key, opts) => repo(key, opts).liftNotFoundToOption
        }.liftToTry.map {
          case Return(results) =>
            results.flatten.lastOption match {
              case None => ValueState.UnmodifiedNone
              case res => ValueState.modified(res)
            }
          case Throw(_) => ValueState.partial(None, hydratedField)
        }
    }.onlyIf { (curr, cttwitter) =>
      curr.isEmpty &&
      cttwitter.tweetFieldRequested(Tweet.Card2Field) &&
      cttwitter.opts.cardsPlatformKey.nonEmpty &&
      !cttwitter.isRetweet &&
      cttwitter.mediaEntities.isEmpty &&
      (cttwitter.cardReference.nonEmpty || cttwitter.urlEntities.nonEmpty)
    }

  private[this] def requestOptions(cttwitter: Cttwitter) =
    Card2RequestOptions(
      platformKey = cttwitter.opts.cardsPlatformKey.get,
      perspectiveUserId = cttwitter.opts.forUserId,
      allowNonTcoUrls = cttwitter.cardReference.nonEmpty,
      languageTag = Some(cttwitter.opts.languageTag)
    )
}
