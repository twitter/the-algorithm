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
import com.X.tweetypie.store.AsyncSetAdditionalFields
import com.X.tweetypie.store.SetAdditionalFields
import com.X.tweetypie.store.TweetStoreEventOrRetry
import com.X.tweetypie.thriftscala.AsyncSetAdditionalFieldsRequest
import com.X.tweetypie.thriftscala.SetAdditionalFieldsRequest

object SetAdditionalFieldsBuilder {
  type Type = SetAdditionalFieldsRequest => Future[SetAdditionalFields.Event]

  val tweetOptions: TweetQuery.Options = TweetQuery.Options(include = GetTweetsHandler.BaseInclude)

  def apply(tweetRepo: TweetRepository.Type): Type = {
    def getTweet(tweetId: TweetId) =
      Stitch.run(
        tweetRepo(tweetId, tweetOptions)
          .rescue(HandlerError.translateNotFoundToClientError(tweetId))
      )

    request => {
      getTweet(request.additionalFields.id).map { tweet =>
        SetAdditionalFields.Event(
          additionalFields = request.additionalFields,
          userId = getUserId(tweet),
          timestamp = Time.now
        )
      }
    }
  }
}

object AsyncSetAdditionalFieldsBuilder {
  type Type = AsyncSetAdditionalFieldsRequest => Future[
    TweetStoreEventOrRetry[AsyncSetAdditionalFields.Event]
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
        AsyncSetAdditionalFields.Event.fromAsyncRequest(request, user)
      }
  }
}
