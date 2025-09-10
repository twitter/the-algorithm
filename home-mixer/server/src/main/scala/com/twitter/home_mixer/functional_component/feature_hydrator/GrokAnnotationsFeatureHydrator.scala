package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.home_mixer.model.GrokTopics.GrokCategoryIdToNameMap
import com.twitter.home_mixer.model.HomeFeatures.DebugStringFeature
import com.twitter.home_mixer.model.HomeFeatures.GrokAnnotationsFeature
import com.twitter.home_mixer.model.HomeFeatures.GrokCategoryDataRecordFeature
import com.twitter.home_mixer.model.HomeFeatures.GrokTopCategoryFeature
import com.twitter.home_mixer.model.HomeFeatures.GrokIsGoreFeature
import com.twitter.home_mixer.model.HomeFeatures.GrokIsLowQualityFeature
import com.twitter.home_mixer.model.HomeFeatures.GrokIsNsfwFeature
import com.twitter.home_mixer.model.HomeFeatures.GrokIsOcrFeature
import com.twitter.home_mixer.model.HomeFeatures.GrokIsSpamFeature
import com.twitter.home_mixer.model.HomeFeatures.GrokIsViolentFeature
import com.twitter.home_mixer.model.HomeFeatures.GrokPoliticalInclinationFeature
import com.twitter.home_mixer.model.HomeFeatures.GrokSlopScoreFeature
import com.twitter.home_mixer.model.HomeFeatures.GrokSunnyScoreFeature
import com.twitter.home_mixer.model.HomeFeatures.GrokTagsFeature
import com.twitter.home_mixer.param.HomeGlobalParams.EnableGrokAnnotations
import com.twitter.home_mixer.util.CandidatesUtil
import com.twitter.home_mixer.{thriftscala => hmt}
import com.twitter.home_mixer_features.{thriftscala => hmf}
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.Conditionally
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.util.OffloadFuturePools
import com.twitter.stitch.Stitch
import com.twitter.util.Future
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GrokAnnotationsFeatureHydrator @Inject() (
  homeMixerFeatureService: hmf.HomeMixerFeatures.MethodPerEndpoint)
    extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate]
    with Conditionally[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("GrokAnnotations")

  override val features: Set[Feature[_, _]] =
    Set(
      GrokAnnotationsFeature,
      GrokCategoryDataRecordFeature,
      GrokTopCategoryFeature,
      GrokTagsFeature,
      GrokIsGoreFeature,
      GrokIsNsfwFeature,
      GrokIsSpamFeature,
      GrokIsViolentFeature,
      GrokIsLowQualityFeature,
      GrokIsOcrFeature,
      GrokSunnyScoreFeature,
      GrokPoliticalInclinationFeature,
      GrokSlopScoreFeature,
      DebugStringFeature
    )

  override def onlyIf(query: PipelineQuery): Boolean = query.params(EnableGrokAnnotations)

  private val batchSize = 64

  private def getGrokAnnotationsFromHMF(
    tweetIdsToHydrate: Seq[Long],
  ): Future[Seq[Option[hmt.GrokAnnotations]]] = {
    val keySerialized = tweetIdsToHydrate.map(_.toString)
    val request = hmf.HomeMixerFeaturesRequest(
      keySerialized,
      hmf.Cache.GrokPostAnnotations
    )

    val responseFut = homeMixerFeatureService.getHomeMixerFeatures(request)
    responseFut
      .map { response =>
        response.homeMixerFeatures
          .map { homeMixerFeaturesOpt =>
            homeMixerFeaturesOpt.homeMixerFeaturesType.map {
              case hmf.HomeMixerFeaturesType.GrokPostAnnotations(data) =>
                val metadata = data.annotations.tweetBoolMetadata.map { metadata =>
                  hmt.GrokMetadata(
                    isNsfw = metadata.isNsfw.getOrElse(false),
                    isGore = metadata.isGore.getOrElse(false),
                    isViolent = metadata.isViolent.getOrElse(false),
                    isSpam = metadata.isSpam.getOrElse(false),
                    isSoftNsfw = metadata.isSoftNsfw.getOrElse(false),
                    isLowQuality = metadata.isHighQuality.exists(!_),
                    isOcr = metadata.isOcr.getOrElse(false),
                  )
                }
                val categoryScoreMap =
                  data.annotations.entities // This gives Option[List[EntityWithMetadata]]
                    .map(
                      _.map(entity =>
                        entity.qualifiedId._2.toString -> entity.score
                          .getOrElse(0.0) // Convert each entity to (String, Double)
                      ).toMap
                    ) // Convert List[(String, Double)] to Map[String, Double]

                hmt.GrokAnnotations(
                  topics = data.topics,
                  tags = data.annotations.tags.getOrElse(Seq.empty).map(_.tag),
                  metadata = metadata,
                  categoryScores = categoryScoreMap,
                  sunnyScore = data.annotations.sunnyScore,
                  politicalInclination = data.annotations.politicalInclination.flatMap {
                    inclination =>
                      scala.util
                        .Try(hmt.PoliticalInclination.valueOf(inclination.name)).toOption.flatten
                  },
                  slopScore = data.annotations.slopScore,
                )
              case _ => throw new Exception("Unknown type returned")
            }
          }
      }.handle { case _ => Seq.fill(tweetIdsToHydrate.size)(None) }
  }

  def getFeatureMaps(
    candidates: Seq[CandidateWithFeatures[TweetCandidate]],
  ): Future[Seq[FeatureMap]] = {

    val tweetIdsToHydrate = candidates.map(CandidatesUtil.getOriginalTweetId)
    val debugStringFeatures =
      candidates.map(_.features.getOrElse(DebugStringFeature, None).getOrElse(""))

    val responseMap = getGrokAnnotationsFromHMF(tweetIdsToHydrate)
    responseMap.map { result =>
      result.zip(debugStringFeatures).map {
        case (annotations, debugStringFeature) =>
          val categoryScoreMap: Option[Map[String, Double]] =
            annotations.flatMap(_.categoryScores.map(_.toMap))
          val tags: Set[String] = annotations.map(_.tags).getOrElse(Seq.empty).toSet
          val grokSlopFeature = annotations.flatMap(_.slopScore.map(_.toLong))

          FeatureMapBuilder()
            .add(GrokAnnotationsFeature, annotations)
            .add(GrokCategoryDataRecordFeature, categoryScoreMap)
            .add(
              GrokTopCategoryFeature,
              annotations
                .flatMap(_.categoryScores)
                .flatMap { scores =>
                  val validCategories = scores.collect {
                    case (category, score)
                        if category.forall(_.isDigit) && GrokCategoryIdToNameMap.contains(category.toLong) =>
                      (category.toLong, score)
                  }
                  if (validCategories.nonEmpty) {
                    Some(validCategories.maxBy(_._2)._1)
                  } else {
                    None
                  }
                }
            )
            .add(GrokTagsFeature, tags.map(_.toLowerCase))
            .add(GrokIsGoreFeature, annotations.flatMap(_.metadata.map(_.isGore)))
            .add(GrokIsNsfwFeature, annotations.flatMap(_.metadata.map(_.isNsfw)))
            .add(GrokIsSpamFeature, annotations.flatMap(_.metadata.map(_.isSpam)))
            .add(GrokIsViolentFeature, annotations.flatMap(_.metadata.map(_.isViolent)))
            .add(GrokIsLowQualityFeature, annotations.flatMap(_.metadata.map(_.isLowQuality)))
            .add(GrokIsOcrFeature, annotations.flatMap(_.metadata.map(_.isOcr)))
            .add(GrokSunnyScoreFeature, annotations.flatMap(_.sunnyScore))
            // Used only for metrics tracking. Does not affect the recommendations.
            .add(GrokPoliticalInclinationFeature, annotations.flatMap(_.politicalInclination))
            .add(GrokSlopScoreFeature, grokSlopFeature)
            .add(
              DebugStringFeature,
              Some("%s GrokSlop:%s"
                .format(debugStringFeature, grokSlopFeature.map(_.toString).getOrElse("None"))))
            .build()
        case _ =>
          FeatureMapBuilder()
            .add(GrokAnnotationsFeature, None)
            .add(GrokCategoryDataRecordFeature, None)
            .add(GrokTopCategoryFeature, None)
            .add(GrokTagsFeature, Set.empty[String])
            .add(GrokIsGoreFeature, None)
            .add(GrokIsNsfwFeature, None)
            .add(GrokIsSpamFeature, None)
            .add(GrokIsViolentFeature, None)
            .add(GrokIsLowQualityFeature, None)
            .add(GrokIsOcrFeature, None)
            .add(GrokSunnyScoreFeature, None)
            .add(GrokPoliticalInclinationFeature, None)
            .add(GrokSlopScoreFeature, None)
            .add(DebugStringFeature, None)
            .build()
      }
    }
  }

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = OffloadFuturePools.offloadFuture {
    OffloadFuturePools.offloadBatchSeqToFutureSeq(
      candidates,
      getFeatureMaps,
      batchSize
    )
  }
}
