package com.twitter.tweetypie
package hydrator

import com.twitter.tweetypie.core._
import com.twitter.tweetypie.repository._
import com.twitter.tweetypie.thriftscala._

object LanguageHydrator {
  type Type = ValueHydrator[Option[Language], TweetCttwitter]

  val hydratedField: FieldByPath = fieldByPath(Tweet.LanguageField)

  private[this] def isApplicable(curr: Option[Language], cttwitter: TweetCttwitter) =
    cttwitter.tweetFieldRequested(Tweet.LanguageField) && !cttwitter.isRetweet && curr.isEmpty

  def apply(repo: LanguageRepository.Type): Type =
    ValueHydrator[Option[Language], TweetCttwitter] { (langOpt, cttwitter) =>
      repo(cttwitter.tetwittert).liftToTry.map {
        case Return(Some(l)) => ValueState.modified(Some(l))
        case Return(None) => ValueState.unmodified(langOpt)
        case Throw(_) => ValueState.partial(None, hydratedField)
      }
    }.onlyIf((curr, cttwitter) => isApplicable(curr, cttwitter))
}
