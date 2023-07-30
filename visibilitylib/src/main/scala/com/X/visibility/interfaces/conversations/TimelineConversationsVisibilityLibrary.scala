package com.X.visibility.interfaces.conversations

import com.X.decider.Decider
import com.X.finagle.stats.NullStatsReceiver
import com.X.finagle.stats.StatsReceiver
import com.X.gizmoduck.thriftscala.Label
import com.X.servo.repository.KeyValueResult
import com.X.servo.util.Gate
import com.X.spam.rtf.thriftscala.SafetyLabel
import com.X.spam.rtf.thriftscala.SafetyLabelType
import com.X.spam.rtf.thriftscala.SafetyLabelValue
import com.X.stitch.Stitch
import com.X.util.Future
import com.X.util.Return
import com.X.util.Stopwatch
import com.X.util.Try
import com.X.visibility.VisibilityLibrary
import com.X.visibility.builder.tweets.TweetIdFeatures
import com.X.visibility.builder.FeatureMapBuilder
import com.X.visibility.builder.VerdictLogger
import com.X.visibility.builder.VisibilityResult
import com.X.visibility.builder.tweets.FosnrPefetchedLabelsRelationshipFeatures
import com.X.visibility.builder.users.AuthorFeatures
import com.X.visibility.common.UserRelationshipSource
import com.X.visibility.common.UserSource
import com.X.visibility.configapi.configs.VisibilityDeciderGates
import com.X.visibility.features.AuthorUserLabels
import com.X.visibility.features.ConversationRootAuthorIsVerified
import com.X.visibility.features.FeatureMap
import com.X.visibility.features.HasInnerCircleOfFriendsRelationship
import com.X.visibility.features.TweetConversationId
import com.X.visibility.features.TweetParentId
import com.X.visibility.logging.thriftscala.VFLibType
import com.X.visibility.models.ContentId.TweetId
import com.X.visibility.models.SafetyLevel.TimelineConversationsDownranking
import com.X.visibility.models.SafetyLevel.TimelineConversationsDownrankingMinimal
import com.X.visibility.models.SafetyLevel.toThrift
import com.X.visibility.models.ContentId
import com.X.visibility.models.SafetyLevel
import com.X.visibility.models.TweetSafetyLabel
import com.X.visibility.models.UnitOfDiversion

object TimelineConversationsVisibilityLibrary {
  type Type =
    TimelineConversationsVisibilityRequest => Stitch[TimelineConversationsVisibilityResponse]

  def apply(
    visibilityLibrary: VisibilityLibrary,
    batchSafetyLabelRepository: BatchSafetyLabelRepository,
    decider: Decider,
    userRelationshipSource: UserRelationshipSource = UserRelationshipSource.empty,
    userSource: UserSource = UserSource.empty
  ): Type = {
    val libraryStatsReceiver = visibilityLibrary.statsReceiver
    val tweetIdFeatures = new TweetIdFeatures(
      statsReceiver = libraryStatsReceiver,
      enableStitchProfiling = Gate.False
    )
    val tweetIdFeaturesMinimal = new TweetIdFeatures(
      statsReceiver = libraryStatsReceiver,
      enableStitchProfiling = Gate.False
    )
    val vfLatencyOverallStat = libraryStatsReceiver.stat("vf_latency_overall")
    val vfLatencyStitchBuildStat = libraryStatsReceiver.stat("vf_latency_stitch_build")
    val vfLatencyStitchRunStat = libraryStatsReceiver.stat("vf_latency_stitch_run")

    val visibilityDeciderGates = VisibilityDeciderGates(decider)
    val verdictLogger =
      createVerdictLogger(
        visibilityDeciderGates.enableVerdictLoggerTCVL,
        decider,
        libraryStatsReceiver)

    request: TimelineConversationsVisibilityRequest =>
      val elapsed = Stopwatch.start()
      var runStitchStartMs = 0L

      val future = request.prefetchedSafetyLabels match {
        case Some(labels) => Future.value(labels)
        case _ =>
          batchSafetyLabelRepository((request.conversationId, request.tweetIds))
      }

      val fosnrPefetchedLabelsRelationshipFeatures =
        new FosnrPefetchedLabelsRelationshipFeatures(
          userRelationshipSource = userRelationshipSource,
          statsReceiver = libraryStatsReceiver)

      val authorFeatures = new AuthorFeatures(userSource, libraryStatsReceiver)

      Stitch.callFuture(future).flatMap {
        kvr: KeyValueResult[Long, scala.collection.Map[SafetyLabelType, SafetyLabel]] =>
          val featureMapProvider: (ContentId, SafetyLevel) => FeatureMap = {
            case (TweetId(tweetId), safetyLevel) =>
              val constantTweetSafetyLabels: Seq[TweetSafetyLabel] =
                kvr.found.getOrElse(tweetId, Map.empty).toSeq.map {
                  case (safetyLabelType, safetyLabel) =>
                    TweetSafetyLabel.fromThrift(SafetyLabelValue(safetyLabelType, safetyLabel))
                }

              val replyAuthor = request.tweetAuthors.flatMap {
                _(tweetId) match {
                  case Return(Some(userId)) => Some(userId)
                  case _ => None
                }
              }

              val fosnrPefetchedLabelsRelationshipFeatureConf = replyAuthor match {
                case Some(authorId) if visibilityLibrary.isReleaseCandidateEnabled =>
                  fosnrPefetchedLabelsRelationshipFeatures
                    .forTweetWithSafetyLabelsAndAuthorId(
                      safetyLabels = constantTweetSafetyLabels,
                      authorId = authorId,
                      viewerId = request.viewerContext.userId)
                case _ => fosnrPefetchedLabelsRelationshipFeatures.forNonFosnr()
              }

              val authorFeatureConf = replyAuthor match {
                case Some(authorId) if visibilityLibrary.isReleaseCandidateEnabled =>
                  authorFeatures.forAuthorId(authorId)
                case _ => authorFeatures.forNoAuthor()
              }

              val baseBuilderArguments = (safetyLevel match {
                case TimelineConversationsDownranking =>
                  Seq(tweetIdFeatures.forTweetId(tweetId, constantTweetSafetyLabels))
                case TimelineConversationsDownrankingMinimal =>
                  Seq(tweetIdFeaturesMinimal.forTweetId(tweetId, constantTweetSafetyLabels))
                case _ => Nil
              }) :+ fosnrPefetchedLabelsRelationshipFeatureConf :+ authorFeatureConf

              val tweetAuthorUserLabels: Option[Seq[Label]] =
                request.prefetchedTweetAuthorUserLabels.flatMap {
                  _.apply(tweetId) match {
                    case Return(Some(labelMap)) =>
                      Some(labelMap.values.toSeq)
                    case _ =>
                      None
                  }
                }

              val hasInnerCircleOfFriendsRelationship: Boolean =
                request.innerCircleOfFriendsRelationships match {
                  case Some(keyValueResult) =>
                    keyValueResult(tweetId) match {
                      case Return(Some(true)) => true
                      case _ => false
                    }
                  case None => false
                }

              val builderArguments: Seq[FeatureMapBuilder => FeatureMapBuilder] =
                tweetAuthorUserLabels match {
                  case Some(labels) =>
                    baseBuilderArguments :+ { (fmb: FeatureMapBuilder) =>
                      fmb.withConstantFeature(AuthorUserLabels, labels)
                    }

                  case None =>
                    baseBuilderArguments :+ { (fmb: FeatureMapBuilder) =>
                      fmb.withConstantFeature(AuthorUserLabels, Seq.empty)
                    }
                  case _ =>
                    baseBuilderArguments
                }

              val tweetParentIdOpt: Option[Long] =
                request.tweetParentIdMap.flatMap(tweetParentIdMap => tweetParentIdMap(tweetId))

              visibilityLibrary.featureMapBuilder(builderArguments :+ { (fmb: FeatureMapBuilder) =>
                fmb.withConstantFeature(
                  HasInnerCircleOfFriendsRelationship,
                  hasInnerCircleOfFriendsRelationship)
                fmb.withConstantFeature(TweetConversationId, request.conversationId)
                fmb.withConstantFeature(TweetParentId, tweetParentIdOpt)
                fmb.withConstantFeature(
                  ConversationRootAuthorIsVerified,
                  request.rootAuthorIsVerified)
              })
            case _ =>
              visibilityLibrary.featureMapBuilder(Nil)
          }
          val safetyLevel =
            if (request.minimalSectioningOnly) TimelineConversationsDownrankingMinimal
            else TimelineConversationsDownranking

          val evaluationContextBuilder = visibilityLibrary
            .evaluationContextBuilder(request.viewerContext)
            .withUnitOfDiversion(UnitOfDiversion.ConversationId(request.conversationId))

          visibilityLibrary
            .runRuleEngineBatch(
              request.tweetIds.map(TweetId),
              featureMapProvider,
              evaluationContextBuilder,
              safetyLevel
            )
            .map { results: Seq[Try[VisibilityResult]] =>
              val (succeededRequests, _) = results.partition(_.exists(_.finished))
              val visibilityResultMap = succeededRequests.flatMap {
                case Return(result) =>
                  scribeVisibilityVerdict(
                    result,
                    visibilityDeciderGates.enableVerdictScribingTCVL,
                    verdictLogger,
                    request.viewerContext.userId,
                    safetyLevel)
                  result.contentId match {
                    case TweetId(id) => Some((id, result))
                    case _ => None
                  }
                case _ => None
              }.toMap
              val failedTweetIds = request.tweetIds diff visibilityResultMap.keys.toSeq
              val response = TimelineConversationsVisibilityResponse(
                visibilityResults = visibilityResultMap,
                failedTweetIds = failedTweetIds
              )

              runStitchStartMs = elapsed().inMilliseconds
              val buildStitchStatMs = elapsed().inMilliseconds
              vfLatencyStitchBuildStat.add(buildStitchStatMs)

              response
            }
            .onSuccess(_ => {
              val overallStatMs = elapsed().inMilliseconds
              vfLatencyOverallStat.add(overallStatMs)
              val runStitchEndMs = elapsed().inMilliseconds
              vfLatencyStitchRunStat.add(runStitchEndMs - runStitchStartMs)
            })
      }
  }

  def scribeVisibilityVerdict(
    visibilityResult: VisibilityResult,
    enableVerdictScribing: Gate[Unit],
    verdictLogger: VerdictLogger,
    viewerId: Option[Long],
    safetyLevel: SafetyLevel
  ): Unit = if (enableVerdictScribing()) {
    verdictLogger.scribeVerdict(
      visibilityResult = visibilityResult,
      viewerId = viewerId,
      safetyLevel = toThrift(safetyLevel),
      vfLibType = VFLibType.TimelineConversationsVisibilityLibrary)
  }

  def createVerdictLogger(
    enableVerdictLogger: Gate[Unit],
    decider: Decider,
    statsReceiver: StatsReceiver
  ): VerdictLogger = {
    if (enableVerdictLogger()) {
      VerdictLogger(statsReceiver, decider)
    } else {
      VerdictLogger.Empty
    }
  }
}
