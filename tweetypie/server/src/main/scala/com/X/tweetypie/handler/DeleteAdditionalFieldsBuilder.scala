package com.X.tweetypie
package handler

import com.X.stitch.NotFound
import com.X.stitch.Stitch
import com.X.tweetypie.repository.TweetQuery
import com.X.tweetypie.repository.TweetRepository
import com.X.tweetypie.repository.UserKey
import com.X.tweetypie.repository.UserQueryOptions
import com.X.tweetypie.repository.UserRepository
import com.X.tweetypie.repository.UserVisibility
import com.X.tweetypie.store.AsyncDeleteAdditionalFields
import com.X.tweetypie.store.DeleteAdditionalFields
import com.X.tweetypie.store.TweetStoreEventOrRetry
import com.X.tweetypie.thriftscala.AsyncDeleteAdditionalFieldsRequest
import com.X.tweetypie.thriftscala.DeleteAdditionalFieldsRequest

object DeleteAdditionalFieldsBuilder {
  type Type = DeleteAdditionalFieldsRequest => Future[Seq[DeleteAdditionalFields.Event]]

  val tweetQueryOptions = TweetQuery.Options(include = GetTweetsHandler.BaseInclude)

  def apply(tweetRepo: TweetRepository.Type): Type = {
    def getTweet(tweetId: TweetId) =
      Stitch.run(
        tweetRepo(tweetId, tweetQueryOptions)
          .rescue(HandlerError.translateNotFoundToClientError(tweetId))
      )

    request => {
      Future.collect(
        request.tweetIds.map { tweetId =>
          getTweet(tweetId).map { tweet =>
            DeleteAdditionalFields.Event(
              tweetId = tweetId,
              fieldIds = request.fieldIds,
              userId = getUserId(tweet),
              timestamp = Time.now
            )
          }
        }
      )
    }
  }
}

object AsyncDeleteAdditionalFieldsBuilder {
  type Type = AsyncDeleteAdditionalFieldsRequest => Future[
    TweetStoreEventOrRetry[AsyncDeleteAdditionalFields.Event]
  ]

  val userQueryOpts: UserQueryOptions = UserQueryOptions(Set(UserField.Safety), UserVisibility.All)

  def apply(userRepo: UserRepository.Type): Type = {
    def getUser(userId: UserId): Future[User] =
      Stitch.run(
        userRepo(UserKey.byId(userId), userQueryOpts)
          .rescue { case NotFound => Stitch.exception(HandlerError.userNotFound(userId)) }
      )

    request =>
      getUser(request.userId).map { user =>
        AsyncDeleteAdditionalFields.Event.fromAsyncRequest(request, user)
      }
  }
}
