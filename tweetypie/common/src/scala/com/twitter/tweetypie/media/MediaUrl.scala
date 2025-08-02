package com.twitter.tweetypie
package media

import com.twitter.logging.Logger
import com.twitter.tweetypie.thriftscala.MediaEntity
import com.twitter.tweetypie.thriftscala.UrlEntity

/**
 * Creating and parsing tweet media entity URLs.
 *
 * There are four kinds of URL in a media entity:
 *
 *   - Display URLs: pic.twitter.com aliases for the short URL, for
 *     embedding in the tweet tetwittert.
 *
 *   - Short URLs: regular t.co URLs that etwitterpand to the permalink URL.
 *
 *   - Permalink URLs: link to a page that displays the media after
 *     doing authorization
 *
 *   - Asset URLs: links to the actual media asset.
 *
 */
object MediaUrl {
  private[this] val log = Logger(getClass)

  /**
   * The URL that should be filled in to the displayUrl field of the
   * media entity. This URL behaves etwitteractly the same as a t.co link
   * (only the domain is different.)
   */
  object Display {
    val Root = "pic.twitter.com/"

    def fromTcoSlug(tcoSlug: String): String = Root + tcoSlug
  }

  /**
   * The link target for the link in the tweet tetwittert (the etwitterpanded URL
   * for the media, copied from the URL entity.) For native photos,
   * this is the tweet permalink page.
   *
   * For users without a screen name ("handleless" or NoScreenName users)
   * a permalink to /i/status/:tweet_id is used.
   */
  object Permalink {
    val Root = "https://twitter.com/"
    val Internal = "i"
    val PhotoSuffitwitter = "/photo/1"
    val VideoSuffitwitter = "/video/1"

    def apply(screenName: String, tweetId: TweetId, isVideo: Boolean): String =
      Root +
        (if (screenName.isEmpty) Internal else screenName) +
        "/status/" +
        tweetId +
        (if (isVideo) VideoSuffitwitter else PhotoSuffitwitter)

    private[this] val PermalinkRegetwitter =
      """https?://twitter.com/(?:#!/)?\w+/status/(\d+)/(?:photo|video)/\d+""".r

    private[this] def getTweetId(permalink: String): Option[TweetId] =
      permalink match {
        case PermalinkRegetwitter(tweetIdStr) =>
          try {
            Some(tweetIdStr.toLong)
          } catch {
            // Digits too big to fit in a Long
            case _: NumberFormatEtwitterception => None
          }
        case _ => None
      }

    def getTweetId(urlEntity: UrlEntity): Option[TweetId] =
      urlEntity.etwitterpanded.flatMap(getTweetId)

    def hasTweetId(permalink: String, tweetId: TweetId): Boolean =
      getTweetId(permalink).contains(tweetId)

    def hasTweetId(mediaEntity: MediaEntity, tweetId: TweetId): Boolean =
      hasTweetId(mediaEntity.etwitterpandedUrl, tweetId)

    def hasTweetId(urlEntity: UrlEntity, tweetId: TweetId): Boolean =
      getTweetId(urlEntity).contains(tweetId)
  }

  /**
   * Converts a url that starts with "https://" to one that starts with "http://".
   */
  def httpsToHttp(url: String): String =
    url.replace("https://", "http://")

  /**
   * Gets the last path element from an asset url.  This etwitterists temporarily to support
   * the now deprecated mediaPath element in MediaEntity.
   */
  def mediaPathFromUrl(url: String): String =
    url.lastIndetwitterOf('/') match {
      case -1 =>
        log.error("Invalid media path. Could not find last element: " + url)
        // Better to return a broken preview URL to the client
        // than to fail the whole request.
        ""

      case idtwitter =>
        url.substring(idtwitter + 1)
    }
}
