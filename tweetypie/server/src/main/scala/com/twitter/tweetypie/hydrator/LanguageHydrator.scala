package com.twitter.tweetypie
package hydrator

import com.twitter.tweetypie.core._
import com.twitter.tweetypie.repository._
import com.twitter.tweetypie.thriftscala._

object LanguageHydrator {
  type Type = ValueHydrator[Option[Language], TweetCtx]

  val hydratedField: FieldByPath = fieldByPath(Tweet.LanguageField)

  private[this] def isApplicable(curr: Option[Language], ctx: TweetCtx) =
    ctx.tweetFieldRequested(Tweet.LanguageField) && !ctx.isRetweet && curr.isEmpty

  def apply(repo: LanguageRepository.Type): Type =
    ValueHydrator[Option[Language], TweetCtx] { (langOpt, ctx) =>
      repo(ctx.text).liftToTry.map {
        case Return(Some(l)) => ValueState.modified(Some(l))
        case Return(None) => ValueState.unmodified(langOpt)
        case Throw(_) => ValueState.partial(None, hydratedField)
      }
    }.onlyIf((curr, ctx) => isApplicable(curr, ctx))
}
