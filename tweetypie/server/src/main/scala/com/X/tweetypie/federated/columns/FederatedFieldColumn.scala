package com.X.tweetypie
package federated.columns

import com.X.io.Buf
import com.X.scrooge.TFieldBlob
import com.X.stitch.Stitch
import com.X.strato.access.Access
import com.X.strato.catalog.OpMetadata
import com.X.strato.config.AllowAll
import com.X.strato.config.ContactInfo
import com.X.strato.config.Policy
import com.X.strato.data.Conv
import com.X.strato.data.Description.PlainText
import com.X.strato.data.Lifecycle.Production
import com.X.strato.data.Type
import com.X.strato.data.Val
import com.X.strato.fed.StratoFed
import com.X.strato.opcontext.OpContext
import com.X.strato.serialization.MVal
import com.X.strato.serialization.Thrift
import com.X.strato.util.Strings
import com.X.tweetypie.thriftscala.GetTweetFieldsResult
import com.X.tweetypie.thriftscala.SetAdditionalFieldsRequest
import com.X.tweetypie.thriftscala.Tweet
import com.X.tweetypie.thriftscala.TweetFieldsResultState.Found
import com.X.util.Future
import org.apache.thrift.protocol.TField

/**
 * Federated strato column to return tweet fields
 * @param federatedFieldsGroup Group to be used for Stitch batching.
 *         This is a function that takes a GroupOptions and returns a FederatedFieldGroup.
 *         Using a function that accepts a GroupOptions allows for Stitch to handle a new group for distinct GroupOptions.
 * @param setAdditionalFields Handler to set additional fields on tweets.
 * @param stratoValueType Type to be returned by the strato column.
 * @param tfield Tweet thrift field to be stored
 * @param pathName Path to be used in the strato catalog
 */
class FederatedFieldColumn(
  federatedFieldsGroup: FederatedFieldGroupBuilder.Type,
  setAdditionalFields: SetAdditionalFieldsRequest => Future[Unit],
  stratoValueType: Type,
  tfield: TField,
  pathOverride: Option[String] = None)
    extends StratoFed.Column(pathOverride.getOrElse(FederatedFieldColumn.makeColumnPath(tfield)))
    with StratoFed.Fetch.StitchWithContext
    with StratoFed.Put.Stitch {

  type Key = Long
  type View = Unit
  type Value = Val.T

  override val keyConv: Conv[Key] = Conv.ofType
  override val viewConv: Conv[View] = Conv.ofType
  override val valueConv: Conv[Value] = Conv(stratoValueType, identity, identity)

  override val policy: Policy = AllowAll

  /*
   * A fetch that proxies GetTweetFieldsColumn.fetch but only requests and
   * returns one specific field.
   */
  override def fetch(tweetId: Key, view: View, opContext: OpContext): Stitch[Result[Value]] = {

    val XUserId: Option[UserId] = Access.getXUserId match {
      // Access.getXUserId should return a value when request is made on behalf of a user
      // and will not return a value otherwise
      case Some(XUser) => Some(XUser.id)
      case None => None
    }

    val stitchGroup = federatedFieldsGroup(GroupOptions(XUserId))

    Stitch
      .call(FederatedFieldReq(tweetId, tfield.id), stitchGroup).map {
        result: GetTweetFieldsResult =>
          result.tweetResult match {
            case Found(f) =>
              f.tweet.getFieldBlob(tfield.id) match {
                case Some(v: TFieldBlob) =>
                  found(blobToVal(v))
                case None => missing
              }
            case _ => missing
          }
      }

  }

  /*
   * A strato put interface for writing a single additional field to a tweet
   */
  override def put(tweetId: Key, value: Val.T): Stitch[Unit] = {
    val tweet: Tweet = Tweet(id = tweetId).setField(valToBlob(value))
    val request: SetAdditionalFieldsRequest = SetAdditionalFieldsRequest(tweet)
    Stitch.callFuture(setAdditionalFields(request))
  }

  val mval: Thrift.Codec = MVal.codec(stratoValueType).thrift(4)

  def valToBlob(value: Val.T): TFieldBlob =
    TFieldBlob(tfield, mval.write[Buf](value, Thrift.compactProto))

  def blobToVal(thriftFieldBlob: TFieldBlob): Val.T =
    mval.read(thriftFieldBlob.content, Thrift.compactProto)

  override val contactInfo: ContactInfo = TweetypieContactInfo
  override val metadata: OpMetadata = OpMetadata(
    lifecycle = Some(Production),
    description = Some(PlainText(s"A federated column for the field tweet.$stratoValueType"))
  )
}

object FederatedFieldColumn {
  val idAllowlist: Seq[Short] = Seq(
    Tweet.CoreDataField.id,
    Tweet.LanguageField.id,
    Tweet.ConversationMutedField.id
  )
  val ID_START = 157
  val ID_END = 32000

  private val MigrationFields: Seq[Short] = Seq(157)

  def isFederatedField(id: Short) = id >= ID_START && id < ID_END || idAllowlist.contains(id)

  def isMigrationFederatedField(tField: TField): Boolean = MigrationFields.contains(tField.id)

  /* federated field column strato configs must conform to this
   * path name scheme for tweetypie to pick them up
   */
  def makeColumnPath(tField: TField) = {
    val columnName = Strings.toCamelCase(tField.name.stripSuffix("id"))
    s"tweetypie/fields/${columnName}.Tweet"
  }

  def makeV1ColumnPath(tField: TField): String = {
    val columnName = Strings.toCamelCase(tField.name.stripSuffix("id"))
    s"tweetypie/fields/$columnName-V1.Tweet"
  }
}
