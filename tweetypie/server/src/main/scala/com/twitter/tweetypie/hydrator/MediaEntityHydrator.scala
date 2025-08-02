package com.twitter.tweetypie
package hydrator

import com.twitter.mediaservices.commons.thriftscala.MediaKey
import com.twitter.tweetypie.repository._
import com.twitter.tweetypie.thriftscala._

object MediaEntitiesHydrator {
  object Cacheable {
    type Cttwitter = MediaEntityHydrator.Cacheable.Cttwitter
    type Type = ValueHydrator[Seq[MediaEntity], Cttwitter]

    def once(h: MediaEntityHydrator.Cacheable.Type): Type =
      TweetHydration.completeOnlyOnce(
        queryFilter = MediaEntityHydrator.queryFilter,
        hydrationType = HydrationType.CacheableMedia,
        dependsOn = Set(HydrationType.Urls),
        hydrator = h.liftSeq
      )
  }

  object Uncacheable {
    type Cttwitter = MediaEntityHydrator.Uncacheable.Cttwitter
    type Type = ValueHydrator[Seq[MediaEntity], Cttwitter]
  }
}

object MediaEntityHydrator {
  val hydratedField: FieldByPath = fieldByPath(Tweet.MediaField)

  object Cacheable {
    type Type = ValueHydrator[MediaEntity, Cttwitter]

    case class Cttwitter(urlEntities: Seq[UrlEntity], underlyingTweetCttwitter: TweetCttwitter) etwittertends TweetCttwitter.Protwittery

    /**
     * Builds a single media-hydrator out of finer-grained hydrators
     * only with cacheable information.
     */
    def apply(hydrateMediaUrls: Type, hydrateMediaIsProtected: Type): Type =
      hydrateMediaUrls.andThen(hydrateMediaIsProtected)
  }

  object Uncacheable {
    type Type = ValueHydrator[MediaEntity, Cttwitter]

    case class Cttwitter(mediaKeys: Option[Seq[MediaKey]], underlyingTweetCttwitter: TweetCttwitter)
        etwittertends TweetCttwitter.Protwittery {

      def includeMediaEntities: Boolean = tweetFieldRequested(Tweet.MediaField)
      def includeAdditionalMetadata: Boolean =
        opts.include.mediaFields.contains(MediaEntity.AdditionalMetadataField.id)
    }

    /**
     * Builds a single media-hydrator out of finer-grained hydrators
     * only with uncacheable information.
     */
    def apply(hydrateMediaKey: Type, hydrateMediaInfo: Type): Type =
      (hydrateMediaKey
        .andThen(hydrateMediaInfo))
        .onlyIf((_, cttwitter) => cttwitter.includeMediaEntities)
  }

  def queryFilter(opts: TweetQuery.Options): Boolean =
    opts.include.tweetFields.contains(Tweet.MediaField.id)
}
