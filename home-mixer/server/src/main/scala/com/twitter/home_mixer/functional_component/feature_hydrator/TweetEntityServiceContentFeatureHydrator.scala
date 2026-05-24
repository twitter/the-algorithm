package com.twitter.home_mixer.functional_component.feature_hydrator

import com.knuddels.jtokkit.Encodings
import com.knuddels.jtokkit.api.Encoding
import com.knuddels.jtokkit.api.ModelType
import com.twitter.escherbird.{thriftscala => esb}
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.functional_component.feature_hydrator.adapters.content.ContentFeatureAdapter
import com.twitter.home_mixer.model.HomeFeatures.HasImageFeature
import com.twitter.home_mixer.model.HomeFeatures.HasMultipleMedia
import com.twitter.home_mixer.model.HomeFeatures.HasVideoFeature
import com.twitter.home_mixer.model.HomeFeatures.IsSelfThreadFeature
import com.twitter.home_mixer.model.HomeFeatures.MediaCategoryFeature
import com.twitter.home_mixer.model.HomeFeatures.MediaIdFeature
import com.twitter.home_mixer.model.HomeFeatures.MediaUnderstandingAnnotationIdsFeature
import com.twitter.home_mixer.model.HomeFeatures.SemanticAnnotationIdsFeature
import com.twitter.home_mixer.model.HomeFeatures.TweetLanguageFromTweetypieFeature
import com.twitter.home_mixer.model.HomeFeatures.TweetTextFeature
import com.twitter.home_mixer.model.HomeFeatures.TweetTextTokensFeature
import com.twitter.home_mixer.model.HomeFeatures.VideoAspectRatioFeature
import com.twitter.home_mixer.model.HomeFeatures.VideoDurationMsFeature
import com.twitter.home_mixer.model.HomeFeatures.VideoHeightFeature
import com.twitter.home_mixer.model.HomeFeatures.VideoWidthFeature
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableTweetypieContentFeaturesDeciderParam
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableTweetypieContentFeaturesParam
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableTweetypieContentMediaEntityFeaturesParam
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.EnableContentFeatureFromTesService
import com.twitter.home_mixer.util.CandidatesUtil
import com.twitter.home_mixer.util.ObservedKeyValueResultHandler
import com.twitter.home_mixer.util.tweetypie.content.FeatureExtractionHelper
import com.twitter.home_mixer_features.{thriftscala => hmf}
import com.twitter.mediaservices.commons.thriftscala.MediaCategory
import com.twitter.ml.api.DataRecord
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.feature.datarecord.DataRecordInAFeature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.Conditionally
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.util.OffloadFuturePools
import com.twitter.stitch.Stitch
import com.twitter.timelines.prediction.common.util.MediaUnderstandingAnnotations
import com.twitter.tweetypie.{thriftscala => tp}
import com.twitter.util.Future
import javax.inject.Inject
import javax.inject.Singleton
import scala.collection.JavaConverters._

object TweetypieContentDataRecordFeature
    extends DataRecordInAFeature[TweetCandidate]
    with FeatureWithDefaultOnFailure[TweetCandidate, DataRecord] {
  override def defaultValue: DataRecord = new DataRecord()
}

case class TweetContentExtractionResult(
  annotations: Seq[esb.TweetEntityAnnotation] = Seq.empty,
  contentDataRecord: DataRecord = new DataRecord(),
  videoDurationMs: Option[Int] = None,
  tweetLanguage: Option[String] = None,
  tweetText: Option[String] = None,
  tweetTextTokens: Option[Seq[Int]] = None,
  aspectRatio: Option[Float] = None,
  height: Option[Short] = None,
  width: Option[Short] = None,
  isSelfThread: Boolean = false,
  mediaId: Option[Long] = None,
  mediaCategory: Option[MediaCategory] = None,
  hasMultipleMedia: Option[Boolean] = None,
  hasImage: Option[Boolean] = None,
  hasVideo: Option[Boolean] = None)

@Singleton
class TweetEntityServiceContentFeatureHydrator @Inject() (
  homeMixerFeatureService: hmf.HomeMixerFeatures.MethodPerEndpoint,
  override val statsReceiver: StatsReceiver)
    extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate]
    with ObservedKeyValueResultHandler
    with Conditionally[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier(
    "TweetEntityServiceContent")

  override val features: Set[Feature[_, _]] = Set(
    MediaUnderstandingAnnotationIdsFeature,
    SemanticAnnotationIdsFeature,
    TweetypieContentDataRecordFeature,
    VideoDurationMsFeature,
    TweetLanguageFromTweetypieFeature,
    TweetTextFeature,
    TweetTextTokensFeature,
    VideoAspectRatioFeature,
    VideoHeightFeature,
    VideoWidthFeature,
    IsSelfThreadFeature,
    MediaIdFeature,
    MediaCategoryFeature,
    HasMultipleMedia,
    HasImageFeature,
    HasVideoFeature
  )

  override def onlyIf(
    query: PipelineQuery
  ): Boolean = query.params(EnableTweetypieContentFeaturesDeciderParam) &&
    query.params(EnableTweetypieContentFeaturesParam)

  override val statScope: String = identifier.toString
  private val batchSize = 64
  val tokenizer: Encoding =
    Encodings.newLazyEncodingRegistry().getEncodingForModel(ModelType.GPT_4)

  private def getContentFeaturesFromHMF(
    tweetIdsToHydrate: Seq[Long],
    getFromTES: Boolean = false
  ): Future[Seq[Option[tp.Tweet]]] = {
    val keysSerialized = tweetIdsToHydrate.map(_.toString)
    val request = hmf.HomeMixerFeaturesRequest(
      keysSerialized,
      hmf.Cache.TweetypieContent,
      Some(
        hmf.HomeMixerFeaturesRequestContext.ContentFeatureRequestContext(
          hmf.ContentFeatureRequestContext(Some(getFromTES))
        ))
    )
    val responseFut =
      homeMixerFeatureService.getHomeMixerFeatures(request)
    responseFut
      .map { response =>
        response.homeMixerFeatures
          .map { homeMixerFeatureOpt =>
            homeMixerFeatureOpt.homeMixerFeaturesType.map {
              case hmf.HomeMixerFeaturesType.TweetypieContent(homeMixerFeature) =>
                homeMixerFeature
              case _ => throw new Exception("Unknown type returned")
            }
          }
      }.handle { case _ => Seq.fill(tweetIdsToHydrate.size)(None) }
  }

  private def getFeatureMaps(
    candidates: Seq[CandidateWithFeatures[TweetCandidate]],
    query: PipelineQuery,
    getFromTes: Boolean
  ): Future[Seq[FeatureMap]] = {
    val tweetIdsToHydrate = candidates.map(CandidatesUtil.getOriginalTweetId)

    val isExtractMediaEntities = query.params(EnableTweetypieContentMediaEntityFeaturesParam)

    val responseMap = getContentFeaturesFromHMF(tweetIdsToHydrate, getFromTes)

    responseMap.map { result =>
      result.map { tweetContent =>
        val transformed = postTransformer(tweetContent, isExtractMediaEntities)
        val annotationIds = transformed.annotations.map(_.entityId)
        val mediaUnderstandingAnnotationIds =
          getNonSensitiveHighRecallMediaUnderstandingAnnotationEntityIds(transformed.annotations)
        FeatureMapBuilder(sizeHint = 13)
          .add(MediaUnderstandingAnnotationIdsFeature, mediaUnderstandingAnnotationIds)
          .add(SemanticAnnotationIdsFeature, annotationIds)
          .add(TweetypieContentDataRecordFeature, transformed.contentDataRecord)
          .add(VideoDurationMsFeature, transformed.videoDurationMs)
          .add(TweetLanguageFromTweetypieFeature, transformed.tweetLanguage)
          .add(TweetTextFeature, transformed.tweetText)
          .add(TweetTextTokensFeature, transformed.tweetTextTokens)
          .add(VideoAspectRatioFeature, transformed.aspectRatio)
          .add(VideoHeightFeature, transformed.height)
          .add(VideoWidthFeature, transformed.width)
          .add(IsSelfThreadFeature, transformed.isSelfThread)
          .add(MediaIdFeature, transformed.mediaId)
          .add(MediaCategoryFeature, transformed.mediaCategory)
          .add(HasMultipleMedia, transformed.hasMultipleMedia.getOrElse(false))
          .add(HasImageFeature, transformed.hasImage.getOrElse(false))
          .add(HasVideoFeature, transformed.hasVideo.getOrElse(false))
          .build()
      }
    }
  }

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = OffloadFuturePools.offloadFuture {
    val getFromTes = query.params(EnableContentFeatureFromTesService)
    OffloadFuturePools.offloadBatchSeqToFutureSeq(
      candidates,
      getFeatureMaps(_, query, getFromTes),
      batchSize)
  }

  private def postTransformer(
    result: Option[tp.Tweet],
    isExtractMediaEntities: Boolean = true
  ): TweetContentExtractionResult = {
    val transformedValue =
      result.map(FeatureExtractionHelper.extractFeatures(_, isExtractMediaEntities))
    val semanticAnnotations =
      transformedValue.flatMap { _.semanticCoreAnnotations }.getOrElse(Seq.empty)
    val dataRecord = ContentFeatureAdapter.adaptToDataRecords(transformedValue).asScala.head
    val videoDurationMs = transformedValue.flatMap { _.videoDurationMs }

    val mediaId = transformedValue.flatMap { _.media.flatMap(_.headOption).map(_.mediaId) }
    val hasMultipleMedia =
      Some(transformedValue.map(_.media.map(_.size > 1).getOrElse(false)).getOrElse(false))
    val mediaCategory = transformedValue.flatMap {
      _.media.flatMap(_.headOption).flatMap(_.mediaKey).map(_.mediaCategory)
    }
    val tweetLanguage = result.flatMap { _.language.map(_.language) }
    val tweetText = result.flatMap { _.coreData.map(_.text) }
    val tweetTextTokens = tweetText.map { text =>
      tokenizer.encodeOrdinary(text, 1024).getTokens.toArray.toSeq
    }
    val aspectRatioNum = transformedValue.flatMap { _.aspectRatioNum }
    val aspectRatioDen = transformedValue.flatMap { _.aspectRatioDen }
    val aspectRatio = aspectRatioNum
      .zip(aspectRatioDen).map {
        case (num, den) =>
          if (den != 0) num.toFloat / den.toFloat
          else -1
      }.find(_ > 0)
    val mediaHeight = transformedValue.flatMap { _.heights.flatMap(_.headOption) }
    val mediaWidth = transformedValue.flatMap { _.widths.flatMap(_.headOption) }
    val isSelfThread = transformedValue.exists(_.selfThreadMetadata.nonEmpty)
    val hasImage = transformedValue.flatMap(_.hasImage)
    val hasVideo = transformedValue.flatMap(_.hasVideo)
    TweetContentExtractionResult(
      semanticAnnotations,
      dataRecord,
      videoDurationMs,
      tweetLanguage,
      tweetText,
      tweetTextTokens,
      aspectRatio,
      mediaHeight,
      mediaWidth,
      isSelfThread,
      mediaId,
      mediaCategory,
      hasMultipleMedia,
      hasImage,
      hasVideo
    )
  }

  private def getNonSensitiveHighRecallMediaUnderstandingAnnotationEntityIds(
    semanticCoreAnnotations: Seq[esb.TweetEntityAnnotation]
  ): Seq[Long] = semanticCoreAnnotations
    .filter(MediaUnderstandingAnnotations.isEligibleNonSensitiveHighRecallMUAnnotation)
    .map(_.entityId)
}
