package com.X.tweetypie.federated.columns

import com.X.stitch.Stitch
import com.X.strato.access.Access.LdapGroup
import com.X.strato.catalog.Fetch
import com.X.strato.catalog.OpMetadata
import com.X.strato.config.AnyOf
import com.X.strato.config.ContactInfo
import com.X.strato.config.FromColumns
import com.X.strato.config.Has
import com.X.strato.config.Path
import com.X.strato.config.Policy
import com.X.strato.data.Conv
import com.X.strato.data.Description.PlainText
import com.X.strato.data.Lifecycle.Production
import com.X.strato.fed.StratoFed
import com.X.strato.response.Err
import com.X.strato.thrift.ScroogeConv
import com.X.tweetypie.UserId
import com.X.tweetypie.thriftscala.federated.GetStoredTweetsByUserView
import com.X.tweetypie.thriftscala.federated.GetStoredTweetsByUserResponse
import com.X.tweetypie.{thriftscala => thrift}
import com.X.util.Future

class GetStoredTweetsByUserColumn(
  handler: thrift.GetStoredTweetsByUserRequest => Future[thrift.GetStoredTweetsByUserResult])
    extends StratoFed.Column(GetStoredTweetsByUserColumn.Path)
    with StratoFed.Fetch.Stitch {

  override val contactInfo: ContactInfo = TweetypieContactInfo
  override val metadata: OpMetadata = OpMetadata(
    lifecycle = Some(Production),
    description =
      Some(PlainText("Fetches hydrated Tweets for a particular User regardless of Tweet state."))
  )
  override val policy: Policy = AnyOf(
    Seq(
      FromColumns(Set(Path("tweetypie/data-provider/storedTweets.User"))),
      Has(LdapGroup("tweetypie-team"))
    ))

  override type Key = UserId
  override type View = GetStoredTweetsByUserView
  override type Value = GetStoredTweetsByUserResponse

  override val keyConv: Conv[Key] = Conv.ofType
  override val viewConv: Conv[View] = ScroogeConv.fromStruct[GetStoredTweetsByUserView]
  override val valueConv: Conv[Value] = ScroogeConv.fromStruct[GetStoredTweetsByUserResponse]

  override def fetch(key: Key, view: View): Stitch[Result[Value]] = {
    val request = thrift.GetStoredTweetsByUserRequest(
      userId = key,
      options = Some(
        thrift.GetStoredTweetsByUserOptions(
          bypassVisibilityFiltering = view.bypassVisibilityFiltering,
          setForUserId = view.setForUserId,
          startTimeMsec = view.startTimeMsec,
          endTimeMsec = view.endTimeMsec,
          cursor = view.cursor,
          startFromOldest = view.startFromOldest,
          additionalFieldIds = view.additionalFieldIds
        ))
    )

    Stitch
      .callFuture(handler(request))
      .map { result =>
        Fetch.Result.found(
          GetStoredTweetsByUserResponse(
            storedTweets = result.storedTweets,
            cursor = result.cursor
          ))
      }
      .rescue {
        case _ => Stitch.exception(Err(Err.Internal))
      }
  }

}

object GetStoredTweetsByUserColumn {
  val Path = "tweetypie/internal/getStoredTweets.User"
}
