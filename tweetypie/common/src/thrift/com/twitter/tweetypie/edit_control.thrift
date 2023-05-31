namespace java com.twitter.tweetypie.thriftjava
#@namespace scala com.twitter.tweetypie.thriftscala
#@namespace strato com.twitter.tweetypie
namespace py gen.twitter.tweetypie.edit_control
namespace rb TweetyPie
// Specific namespace to avoid golang circular import
namespace go tweetypie.tweet

/**
 * EditControlInitial is present on all new Tweets. Initially, edit_tweet_ids will only contain the id of the new Tweet.
 * Subsequent edits will append the edited Tweet ids to edit_tweet_ids.
**/
struct EditControlInitial {
 /**
  * A list of all edits of this initial Tweet, including the initial Tweet id,
  * and in ascending time order (the oldest revision first).
  */
  1: required list<i64> edit_tweet_ids = [] (personalDataType = 'TweetId', strato.json.numbers.type = 'string')
 /**
  * Epoch timestamp in milli-seconds (UTC) after which the tweet will no longer be editable.
  */
  2: optional i64 editable_until_msecs (strato.json.numbers.type = 'string')
 /**
  * Number of edits that are available for this Tweet. This starts at 5 and decrements with each edit.
  */
  3: optional i64 edits_remaining (strato.json.numbers.type = 'string')

  /**
   * Specifies whether the Tweet has any intrinsic properties that mean it can't be edited
   * (for example, we have a business rule that poll Tweets can't be edited).
   *
   * If a Tweet edit expires due to time frame or number of edits, this field still is set
   * to true for Tweets that could have been edited.
   */
  4: optional bool is_edit_eligible
}(persisted='true', hasPersonalData = 'true', strato.graphql.typename='EditControlInitial')

/**
 * EditControlEdit is present for any Tweets that are an edit of another Tweet. The full list of edits can be retrieved
 * from the edit_control_initial field, which will always be hydrated.
**/
struct EditControlEdit {
  /**
   * The id of the initial Tweet in an edit chain
   */
  1: required i64 initial_tweet_id (personalDataType = 'TweetId', strato.json.numbers.type = 'string')
  /**
  * This field is only used during hydration to return the EditControl of the initial Tweet for
  * a subsequently edited version.
  */
  2: optional EditControlInitial edit_control_initial
}(persisted='true', hasPersonalData = 'true', strato.graphql.typename='EditControlEdit')


/**
 * Tweet metadata about edits of a Tweet. A list of edits to a Tweet are represented as a chain of
 * Tweets linked to each other using the EditControl field.
 *
 * EditControl can be either EditControlInitial which means that the Tweet is unedited or the first Tweet in
 * an edit chain, or EditControlEdit which means it is a Tweet in the edit chain after the first
 * Tweet.
 */
union EditControl {
  1: EditControlInitial initial
  2: EditControlEdit edit
}(persisted='true', hasPersonalData = 'true', strato.graphql.typename='EditControl')


service FederatedServiceBase {
  EditControl getEditControl(1: required i64 tweetId)
}
