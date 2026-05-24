/**
 * This file contains definitions for transient, passthrough structured data.
 *
 * If you need to add structured data that Tweetypie accepts in a request
 * and passes the data through to one or more backends (eg. EventBus), this
 * is the place to put it. Tweetypie may or may not inspect the data and
 * alter the behavior based on it, but it won't change it.
 */

namespace java com.twitter.tweetypie.thriftjava
#@namespace scala com.twitter.tweetypie.thriftscala
#@namespace strato com.twitter.tweetypie
namespace py gen.twitter.tweetypie.transient_context
namespace rb TweetyPie
namespace go tweetypie

include "com/twitter/tweetypie/tweet.thrift"

enum BatchComposeMode {
  /**
   * This is the first Tweet in a batch.
   */
  BATCH_FIRST = 1

  /**
   * This is any of the subsequent Tweets in a batch.
   */
  BATCH_SUBSEQUENT = 2
}

/**
 * Data supplied at Tweet creation time that is not served by Tweetypie, but
 * is passed through to consumers of the tweet_events eventbus stream as part
 * of TweetCreateEvent.
 * This is different from additional_context in that Tweetypie
 * inspects this data as well, and we prefer structs over strings.
 * If adding a new field that will be passed through to eventbus, prefer this
 * over additional_context.
 */
struct TransientCreateContext {
  /**
   * Indicates whether a Tweet was created using a batch composer, and if so
   * position of a Tweet within the batch.
   *
   * A value of 'None' indicates that the tweet was not created in a batch.
   *
   * More info: https://docs.google.com/document/d/1dJ9K0KzXPzhk0V-Nsekt0CAdOvyVI8sH9ESEiA2eDW4/edit
   */
  1: optional BatchComposeMode batch_compose

  /**
   * Indicates if the tweet contains a live Periscope streaming video.
   *
   * This enables Periscope LiveFollow.
   */
  2: optional bool periscope_is_live

  /**
   * Indicates the userId of the live Periscope streaming video.
   *
   * This enables Periscope LiveFollow.
   */
  3: optional i64 periscope_creator_id (personalDataType='UserId')
}(persisted='true', hasPersonalData='true')
