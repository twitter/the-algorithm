package com.twitter.home_mixer.product.for_you.side_effect

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.model.HomeFeatures.HasVideoFeature
import com.twitter.home_mixer.model.HomeFeatures.InNetworkFeature
import com.twitter.home_mixer.model.HomeFeatures.VideoDurationMsFeature
import com.twitter.home_mixer.model.HomeFeatures.ViralContentCreatorFeature
import com.twitter.home_mixer.service.HomeMixerAlertConfig
import com.twitter.home_mixer.util.CandidatesUtil
import com.twitter.product_mixer.core.functional_component.side_effect.PipelineResultSideEffect
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.model.common.identifier.SideEffectIdentifier
import com.twitter.product_mixer.core.model.common.presentation.ItemCandidateWithDetails
import com.twitter.product_mixer.core.model.marshalling.response.urt.Timeline
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

case class VideoServedStatsSideEffect(
  candidatePipelines: Set[CandidatePipelineIdentifier],
  statsReceiver: StatsReceiver)
    extends PipelineResultSideEffect[PipelineQuery, Timeline] {

  override val identifier: SideEffectIdentifier = SideEffectIdentifier("VideoServedStats")
  private val baseStatsReceiver = statsReceiver.scope(identifier.toString)
  private val videoStatsReceiver = baseStatsReceiver.scope("Video")

  override def apply(
    inputs: PipelineResultSideEffect.Inputs[PipelineQuery, Timeline]
  ): Stitch[Unit] = {
    val tweetCandidates = CandidatesUtil
      .getItemCandidates(inputs.selectedCandidates)
      .filter(candidate => candidatePipelines.contains(candidate.source))

    val clientId = inputs.query.clientContext.appId.getOrElse(0L).toString

    // Filter candidates to include only those with video and valid duration
    val videoCandidates = tweetCandidates.filter { candidate =>
      candidate.features.getOrElse(HasVideoFeature, false) &&
      candidate.features.get(VideoDurationMsFeature).exists(_ > 0)
    }
    statsReceiver
      .scope(clientId).counter("HasVideo")
      .incr(videoCandidates.size)

    recordVideoDurationStats(videoCandidates, videoStatsReceiver, clientId)
    recordViralContentStats(videoCandidates, videoStatsReceiver, clientId)
    Stitch.Unit
  }

  def recordViralContentStats(
    candidates: Seq[ItemCandidateWithDetails],
    statsReceiver: StatsReceiver,
    clientId: String
  ): Unit = {

    val viralContentCount = candidates.count { candidate =>
      candidate.features.getOrElse(ViralContentCreatorFeature, false)
    }
    val viralContentInNetworkCount = candidates.count { candidate =>
      candidate.features.getOrElse(ViralContentCreatorFeature, false) &&
      candidate.features.getOrElse(InNetworkFeature, true)
    }
    val viralContentOutOfNetworkCount = candidates.count { candidate =>
      candidate.features.getOrElse(ViralContentCreatorFeature, false) &&
      !candidate.features.getOrElse(InNetworkFeature, true)
    }

    statsReceiver
      .scope(clientId).counter("ViralContent")
      .incr(viralContentCount)
    statsReceiver
      .scope(clientId)
      .counter("ViralContentInNetwork")
      .incr(viralContentInNetworkCount)
    statsReceiver
      .scope(clientId)
      .counter("ViralContentOutOfNetwork")
      .incr(viralContentOutOfNetworkCount)

  }

  def recordVideoDurationStats(
    candidates: Seq[ItemCandidateWithDetails],
    statsReceiver: StatsReceiver,
    clientId: String
  ): Unit = {

    val lte10Sec = candidates.count(_.features.get(VideoDurationMsFeature).exists(_ <= 10000))
    val gt60Sec = candidates.count(_.features.get(VideoDurationMsFeature).exists(_ > 60000))
    val bt10And60Sec = candidates.count { candidate =>
      candidate.features.get(VideoDurationMsFeature).exists { duration =>
        duration > 10000 && duration <= 60000
      }
    }
    val bt60And120Sec = candidates.count { candidate =>
      candidate.features.get(VideoDurationMsFeature).exists { duration =>
        duration > 60000 && duration <= 120000
      }
    }
    val bt120And180Sec = candidates.count { candidate =>
      candidate.features.get(VideoDurationMsFeature).exists { duration =>
        duration > 120000 && duration <= 180000
      }
    }
    val gt180Sec = candidates.count(_.features.get(VideoDurationMsFeature).exists(_ > 180000))

    statsReceiver
      .scope(clientId).counter("VideoLte10Sec").incr(lte10Sec)
    statsReceiver
      .scope(clientId).counter("VideoGt60Sec").incr(gt60Sec)
    statsReceiver
      .scope(clientId).counter("VideoBt10And60Sec")
      .incr(bt10And60Sec)
    statsReceiver
      .scope(clientId).counter("VideoBt60And120Sec")
      .incr(bt60And120Sec)
    statsReceiver
      .scope(clientId).counter("VideoBt120And180Sec")
      .incr(bt120And180Sec)
    statsReceiver
      .scope(clientId).counter("VideoGt180Sec").incr(gt180Sec)
  }
  override val alerts = Seq(HomeMixerAlertConfig.BusinessHours.defaultSuccessRateAlert())
}
