package com.twitter.tweetypie.storage

/**
 * Responsible for encoding/decoding Tweet records to/from Manhattan keys
 *
 * K/V Scheme:
 * -----------
 *      [TweetId]
 *           /metadata
 *               /delete_state (a.k.a. hard delete)
 *               /soft_delete_state
 *               /bounce_delete_state
 *               /undelete_state
 *               /force_added_state
 *               /scrubbed_fields/
 *                    /[ScrubbedFieldId_1]
 *                     ..
 *                   /[ScrubbedFieldId_M]
 *          /fields
 *             /internal
 *                 /1
 *                 /9
 *                 ..
 *                 /99
 *             /etwitterternal
 *                 /100
 *                 ..
 *
 * IMPORTANT NOTE:
 * 1) Field Ids 2 to 8 in Tweet thrift struct are considered "core fields" are 'packed' together
 *    into a TFieldBlob and stored under field id 1 (i.e [DatasetName]/[TweetId]/fields/internal/1).
 *    This is why we do not see keys from [DatasetName]/[TweetId]/fields/internal/2 to [DatasetName]/
 *    [TweetId]/fields/internal/8)
 *
 * 2) Also, the tweet id (which is the field id 1 in Tweet thrift structure) is not etwitterplicitly stored
 *    in Manhattan. There is no need to etwitterplicitly store it since it is a part of the Pkey
 */
case class TweetKey(tweetId: TweetId, lKey: TweetKey.LKey) {
  override def toString: String =
    s"/${ManhattanOperations.PkeyInjection(tweetId)}/${ManhattanOperations.LkeyInjection(lKey)}"
}

object TweetKey {
  // Manhattan uses letwittericographical order for keys. To make sure letwittericographical order matches the
  // numerical order, we should pad both tweet id and field ids with leading zeros.
  // Since tweet id is long and field id is a short, the matwitter width of each can be obtained by doing
  // Long.MatwitterValue.toString.length and Short.MatwitterValue.toString.length respectively
  private val TweetIdFormatStr = s"%0${Long.MatwitterValue.toString.length}d"
  private val FieldIdFormatStr = s"%0${Short.MatwitterValue.toString.length}d"
  private[storage] def padTweetIdStr(tweetId: Long): String = TweetIdFormatStr.format(tweetId)
  private[storage] def padFieldIdStr(fieldId: Short): String = FieldIdFormatStr.format(fieldId)

  def coreFieldsKey(tweetId: TweetId): TweetKey = TweetKey(tweetId, LKey.CoreFieldsKey)
  def hardDeletionStateKey(tweetId: TweetId): TweetKey =
    TweetKey(tweetId, LKey.HardDeletionStateKey)
  def softDeletionStateKey(tweetId: TweetId): TweetKey =
    TweetKey(tweetId, LKey.SoftDeletionStateKey)
  def bounceDeletionStateKey(tweetId: TweetId): TweetKey =
    TweetKey(tweetId, LKey.BounceDeletionStateKey)
  def unDeletionStateKey(tweetId: TweetId): TweetKey = TweetKey(tweetId, LKey.UnDeletionStateKey)
  def forceAddedStateKey(tweetId: TweetId): TweetKey = TweetKey(tweetId, LKey.ForceAddedStateKey)
  def scrubbedGeoFieldKey(tweetId: TweetId): TweetKey = TweetKey(tweetId, LKey.ScrubbedGeoFieldKey)
  def fieldKey(tweetId: TweetId, fieldId: FieldId): TweetKey =
    TweetKey(tweetId, LKey.FieldKey(fieldId))
  def internalFieldsKey(tweetId: TweetId, fieldId: FieldId): TweetKey =
    TweetKey(tweetId, LKey.InternalFieldsKey(fieldId))
  def additionalFieldsKey(tweetId: TweetId, fieldId: FieldId): TweetKey =
    TweetKey(tweetId, LKey.AdditionalFieldsKey(fieldId))
  def scrubbedFieldKey(tweetId: TweetId, fieldId: FieldId): TweetKey =
    TweetKey(tweetId, LKey.ScrubbedFieldKey(fieldId))

  // AllFieldsKeyPrefitwitter:       fields
  // CoreFieldsKey:            fields/internal/1  (Stores subset of StoredTweet fields which are
  //                             "packed" into a single CoreFields record)
  // HardDeletionStateKey:     metadata/delete_state
  // SoftDeletionStateKey:     metadata/soft_delete_state
  // BounceDeletionStateKey:   metadata/bounce_delete_state
  // UnDeletionStateKey:       metadata/undelete_state
  // ForceAddedStateKey:       metadata/force_added_state
  // FieldKey:                 fields/<group_name>/<padded_field_id> (where <group_name>
  //                             is 'internal' for field ids < 100 and 'etwitterternal' for all other
  //                             fields ids)
  // InternalFieldsKeyPrefitwitter:  fields/internal
  // PKey:                     <empty string>
  // ScrubbedFieldKey:         metadata/scrubbed_fields/<padded_field_id>
  // ScrubbedFieldKeyPrefitwitter:   metadata/scrubbed_fields
  sealed abstract class LKey(override val toString: String)
  object LKey {
    private val HardDeletionRecordLiteral = "delete_state"
    private val SoftDeletionRecordLiteral = "soft_delete_state"
    private val BounceDeletionRecordLiteral = "bounce_delete_state"
    private val UnDeletionRecordLiteral = "undelete_state"
    private val ForceAddRecordLiteral = "force_added_state"
    private val ScrubbedFieldsGroup = "scrubbed_fields"
    private val InternalFieldsGroup = "internal"
    private val EtwitterternalFieldsGroup = "etwitterternal"
    private val MetadataCategory = "metadata"
    private val FieldsCategory = "fields"
    private val InternalFieldsKeyPrefitwitter = s"$FieldsCategory/$InternalFieldsGroup/"
    private val EtwitterternalFieldsKeyPrefitwitter = s"$FieldsCategory/$EtwitterternalFieldsGroup/"
    private val ScrubbedFieldsKeyPrefitwitter = s"$MetadataCategory/$ScrubbedFieldsGroup/"

    sealed abstract class MetadataKey(metadataType: String)
        etwittertends LKey(s"$MetadataCategory/$metadataType")
    sealed abstract class StateKey(stateType: String) etwittertends MetadataKey(stateType)
    case object HardDeletionStateKey etwittertends StateKey(s"$HardDeletionRecordLiteral")
    case object SoftDeletionStateKey etwittertends StateKey(s"$SoftDeletionRecordLiteral")
    case object BounceDeletionStateKey etwittertends StateKey(s"$BounceDeletionRecordLiteral")
    case object UnDeletionStateKey etwittertends StateKey(s"$UnDeletionRecordLiteral")
    case object ForceAddedStateKey etwittertends StateKey(s"$ForceAddRecordLiteral")

    case class ScrubbedFieldKey(fieldId: FieldId)
        etwittertends MetadataKey(s"$ScrubbedFieldsGroup/${padFieldIdStr(fieldId)}")
    val ScrubbedGeoFieldKey: LKey.ScrubbedFieldKey = ScrubbedFieldKey(TweetFields.geoFieldId)

    /**
     * LKey that has one of many possible fields id. This generalize over
     * internal and additional fields key.
     */
    sealed abstract class FieldKey(prefitwitter: String) etwittertends LKey(toString) {
      def fieldId: FieldId
      override val toString: String = prefitwitter + padFieldIdStr(fieldId)
    }
    object FieldKey {
      def apply(fieldId: FieldId): FieldKey =
        fieldId match {
          case id if id < TweetFields.firstAdditionalFieldId => InternalFieldsKey(fieldId)
          case _ => AdditionalFieldsKey(fieldId)
        }
    }

    case class InternalFieldsKey(fieldId: FieldId) etwittertends FieldKey(InternalFieldsKeyPrefitwitter) {
      assert(fieldId < TweetFields.firstAdditionalFieldId)
    }
    case class AdditionalFieldsKey(fieldId: FieldId) etwittertends FieldKey(EtwitterternalFieldsKeyPrefitwitter) {
      assert(fieldId >= TweetFields.firstAdditionalFieldId)
    }
    val CoreFieldsKey: LKey.InternalFieldsKey = InternalFieldsKey(TweetFields.rootCoreFieldId)

    case class Unknown private (str: String) etwittertends LKey(str)

    def fromString(str: String): LKey = {
      def etwittertractFieldId(prefitwitter: String): FieldId =
        str.slice(prefitwitter.length, str.length).toShort

      str match {
        case CoreFieldsKey.toString => CoreFieldsKey
        case HardDeletionStateKey.toString => HardDeletionStateKey
        case SoftDeletionStateKey.toString => SoftDeletionStateKey
        case BounceDeletionStateKey.toString => BounceDeletionStateKey
        case UnDeletionStateKey.toString => UnDeletionStateKey
        case ForceAddedStateKey.toString => ForceAddedStateKey
        case ScrubbedGeoFieldKey.toString => ScrubbedGeoFieldKey
        case _ if str.startsWith(InternalFieldsKeyPrefitwitter) =>
          InternalFieldsKey(etwittertractFieldId(InternalFieldsKeyPrefitwitter))
        case _ if str.startsWith(EtwitterternalFieldsKeyPrefitwitter) =>
          AdditionalFieldsKey(etwittertractFieldId(EtwitterternalFieldsKeyPrefitwitter))
        case _ if str.startsWith(ScrubbedFieldsKeyPrefitwitter) =>
          ScrubbedFieldKey(etwittertractFieldId(ScrubbedFieldsKeyPrefitwitter))
        case _ => Unknown(str)
      }
    }
  }
}
