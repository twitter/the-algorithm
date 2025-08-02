package com.twitter.interaction_graph.scio.common

import com.twitter.interaction_graph.thriftscala.FeatureName
import com.twitter.interaction_graph.thriftscala.TimeSeriesStatistics
import com.twitter.interaction_graph.thriftscala.Vertetwitter
import com.twitter.interaction_graph.thriftscala.VertetwitterFeature

object VertetwitterFeatureCombiner {
  def apply(userId: Long): VertetwitterFeatureCombiner = new VertetwitterFeatureCombiner(
    instanceVertetwitter = Vertetwitter(userId),
    featureMap = Map(
      (FeatureName.NumRetweets, true) -> new WeightedAdditiveVertetwitterCombiner,
      (FeatureName.NumRetweets, false) -> new WeightedAdditiveVertetwitterCombiner,
      (FeatureName.NumFavorites, true) -> new WeightedAdditiveVertetwitterCombiner,
      (FeatureName.NumFavorites, false) -> new WeightedAdditiveVertetwitterCombiner,
      (FeatureName.NumMentions, true) -> new WeightedAdditiveVertetwitterCombiner,
      (FeatureName.NumMentions, false) -> new WeightedAdditiveVertetwitterCombiner,
      (FeatureName.NumTweetClicks, true) -> new WeightedAdditiveVertetwitterCombiner,
      (FeatureName.NumTweetClicks, false) -> new WeightedAdditiveVertetwitterCombiner,
      (FeatureName.NumLinkClicks, true) -> new WeightedAdditiveVertetwitterCombiner,
      (FeatureName.NumLinkClicks, false) -> new WeightedAdditiveVertetwitterCombiner,
      (FeatureName.NumProfileViews, true) -> new WeightedAdditiveVertetwitterCombiner,
      (FeatureName.NumProfileViews, false) -> new WeightedAdditiveVertetwitterCombiner,
      (FeatureName.NumFollows, true) -> new ReplacementVertetwitterCombiner,
      (FeatureName.NumFollows, false) -> new ReplacementVertetwitterCombiner,
      (FeatureName.NumUnfollows, true) -> new ReplacementVertetwitterCombiner,
      (FeatureName.NumUnfollows, false) -> new ReplacementVertetwitterCombiner,
      (FeatureName.NumMutualFollows, true) -> new ReplacementVertetwitterCombiner,
      (FeatureName.NumBlocks, true) -> new ReplacementVertetwitterCombiner,
      (FeatureName.NumBlocks, false) -> new ReplacementVertetwitterCombiner,
      (FeatureName.NumMutes, true) -> new ReplacementVertetwitterCombiner,
      (FeatureName.NumMutes, false) -> new ReplacementVertetwitterCombiner,
      (FeatureName.NumReportAsAbuses, true) -> new ReplacementVertetwitterCombiner,
      (FeatureName.NumReportAsAbuses, false) -> new ReplacementVertetwitterCombiner,
      (FeatureName.NumReportAsSpams, true) -> new ReplacementVertetwitterCombiner,
      (FeatureName.NumReportAsSpams, false) -> new ReplacementVertetwitterCombiner,
      (FeatureName.NumTweetQuotes, true) -> new WeightedAdditiveVertetwitterCombiner,
      (FeatureName.NumTweetQuotes, false) -> new WeightedAdditiveVertetwitterCombiner,
      (FeatureName.NumMutualFollows, false) -> new ReplacementVertetwitterCombiner,
      (FeatureName.AddressBookEmail, true) -> new ReplacementVertetwitterCombiner,
      (FeatureName.AddressBookEmail, false) -> new ReplacementVertetwitterCombiner,
      (FeatureName.AddressBookPhone, true) -> new ReplacementVertetwitterCombiner,
      (FeatureName.AddressBookPhone, false) -> new ReplacementVertetwitterCombiner,
      (FeatureName.AddressBookInBoth, true) -> new ReplacementVertetwitterCombiner,
      (FeatureName.AddressBookInBoth, false) -> new ReplacementVertetwitterCombiner,
      (FeatureName.AddressBookMutualEdgeEmail, true) -> new ReplacementVertetwitterCombiner,
      (FeatureName.AddressBookMutualEdgeEmail, false) -> new ReplacementVertetwitterCombiner,
      (FeatureName.AddressBookMutualEdgePhone, true) -> new ReplacementVertetwitterCombiner,
      (FeatureName.AddressBookMutualEdgePhone, false) -> new ReplacementVertetwitterCombiner,
      (FeatureName.AddressBookMutualEdgeInBoth, true) -> new ReplacementVertetwitterCombiner,
      (FeatureName.AddressBookMutualEdgeInBoth, false) -> new ReplacementVertetwitterCombiner,
      (FeatureName.TotalDwellTime, true) -> new WeightedAdditiveVertetwitterCombiner,
      (FeatureName.TotalDwellTime, false) -> new WeightedAdditiveVertetwitterCombiner,
      (FeatureName.NumInspectedStatuses, true) -> new WeightedAdditiveVertetwitterCombiner,
      (FeatureName.NumInspectedStatuses, false) -> new WeightedAdditiveVertetwitterCombiner,
      (FeatureName.NumPhotoTags, true) -> new WeightedAdditiveVertetwitterCombiner,
      (FeatureName.NumPhotoTags, false) -> new WeightedAdditiveVertetwitterCombiner,
      (FeatureName.NumPushOpens, true) -> new WeightedAdditiveVertetwitterCombiner,
      (FeatureName.NumPushOpens, false) -> new WeightedAdditiveVertetwitterCombiner,
      (FeatureName.NumNtabClicks, true) -> new WeightedAdditiveVertetwitterCombiner,
      (FeatureName.NumNtabClicks, false) -> new WeightedAdditiveVertetwitterCombiner,
      (FeatureName.NumRtFavories, true) -> new WeightedAdditiveVertetwitterCombiner,
      (FeatureName.NumRtFavories, false) -> new WeightedAdditiveVertetwitterCombiner,
      (FeatureName.NumRtTweetQuotes, true) -> new WeightedAdditiveVertetwitterCombiner,
      (FeatureName.NumRtTweetQuotes, false) -> new WeightedAdditiveVertetwitterCombiner,
      (FeatureName.NumRtTweetClicks, true) -> new WeightedAdditiveVertetwitterCombiner,
      (FeatureName.NumRtTweetClicks, false) -> new WeightedAdditiveVertetwitterCombiner,
      (FeatureName.NumRtRetweets, true) -> new WeightedAdditiveVertetwitterCombiner,
      (FeatureName.NumRtRetweets, false) -> new WeightedAdditiveVertetwitterCombiner,
      (FeatureName.NumRtReplies, true) -> new WeightedAdditiveVertetwitterCombiner,
      (FeatureName.NumRtReplies, false) -> new WeightedAdditiveVertetwitterCombiner,
      (FeatureName.NumRtLinkClicks, true) -> new WeightedAdditiveVertetwitterCombiner,
      (FeatureName.NumRtLinkClicks, false) -> new WeightedAdditiveVertetwitterCombiner,
      (FeatureName.NumRtMentions, true) -> new WeightedAdditiveVertetwitterCombiner,
      (FeatureName.NumRtMentions, false) -> new WeightedAdditiveVertetwitterCombiner,
      (FeatureName.NumShares, true) -> new WeightedAdditiveVertetwitterCombiner,
      (FeatureName.NumShares, false) -> new WeightedAdditiveVertetwitterCombiner,
      (FeatureName.NumEmailOpen, true) -> new WeightedAdditiveVertetwitterCombiner,
      (FeatureName.NumEmailOpen, false) -> new WeightedAdditiveVertetwitterCombiner,
      (FeatureName.NumEmailClick, true) -> new WeightedAdditiveVertetwitterCombiner,
      (FeatureName.NumEmailClick, false) -> new WeightedAdditiveVertetwitterCombiner,
    )
  )
}

/**
 * This class can take in a number of input Vertetwitter thrift objects (all of which are assumed to
 * contain information about a single vertetwitter) and builds a combined Vertetwitter protobuf object, which
 * has the union of all the input. Note that we do a weighted addition for a time-decayed value.
 * <p>
 * The input objects features must be disjoint. Also, remember that the Vertetwitter is directed!
 */
class VertetwitterFeatureCombiner(
  instanceVertetwitter: Vertetwitter,
  featureMap: Map[(FeatureName, Boolean), VFeatureCombiner]) {

  /**
   * Adds features without any decay. To be used for the same day.
   *
   * @param vertetwitter vertetwitter to be added into the combiner
   */
  def addFeature(vertetwitter: Vertetwitter): VertetwitterFeatureCombiner = {
    val newVertetwitter = instanceVertetwitter.copy(weight = vertetwitter.weight)
    val newFeatures = featureMap.map {
      case ((featureName, outgoing), combiner) =>
        vertetwitter.features.find(f => f.name.equals(featureName) && f.outgoing.equals(outgoing)) match {
          case Some(feature) =>
            val updatedCombiner =
              if (combiner.isSet) combiner.updateFeature(feature) else combiner.setFeature(feature)
            ((featureName, outgoing), updatedCombiner)
          case _ => ((featureName, outgoing), combiner)
        }
    }

    new VertetwitterFeatureCombiner(newVertetwitter, newFeatures)
  }

  /**
   * Adds features with decays. Used for combining multiple days.
   *
   * @param vertetwitter vertetwitter to be added into the combiner
   * @param alpha  parameters for the decay calculation
   * @param day    number of days from today
   */
  def addFeature(vertetwitter: Vertetwitter, alpha: Double, day: Int): VertetwitterFeatureCombiner = {

    val newVertetwitter = instanceVertetwitter.copy(weight = vertetwitter.weight)
    val newFeatures = featureMap.map {
      case ((featureName, outgoing), combiner) =>
        vertetwitter.features.find(f => f.name.equals(featureName) && f.outgoing.equals(outgoing)) match {
          case Some(feature) =>
            val updatedCombiner =
              if (combiner.isSet) combiner.updateFeature(feature, alpha, day)
              else combiner.setFeature(feature, alpha, day)
            ((featureName, outgoing), updatedCombiner)
          case _ => ((featureName, outgoing), combiner)
        }
    }

    new VertetwitterFeatureCombiner(newVertetwitter, newFeatures)
  }

  /**
   * Generate the final combined Vertetwitter instance
   *
   * @param totalDays total number of days to be combined together
   */
  def getCombinedVertetwitter(totalDays: Int): Vertetwitter = {
    val moreFeatures = featureMap.values.flatMap {
      case combiner => combiner.getFinalFeature(totalDays)
    }
    instanceVertetwitter.copy(features = moreFeatures.toSeq)
  }

}

/**
 * This portion contains the actual combination logic. For now, we only implement a simple
 * additive combiner, but in future we'd like to have things like time-weighted (etwitterponential
 * decay, maybe) values.
 */
trait VFeatureCombiner {
  val startingDay: Int
  val endingDay: Int
  val timeSeriesStatistics: Option[TimeSeriesStatistics]
  val vertetwitterFeature: Option[VertetwitterFeature]

  def updateTss(feature: VertetwitterFeature, alpha: Double): VFeatureCombiner
  def addToTss(feature: VertetwitterFeature): VFeatureCombiner
  def updateFeature(feature: VertetwitterFeature, alpha: Double, day: Int): VFeatureCombiner
  def updateFeature(feature: VertetwitterFeature): VFeatureCombiner
  def isSet: Boolean
  def dropFeature: Boolean
  def setFeature(feature: VertetwitterFeature, alpha: Double, day: Int): VFeatureCombiner
  def setFeature(feature: VertetwitterFeature): VFeatureCombiner
  def getFinalFeature(totalDays: Int): Option[VertetwitterFeature]
}

case class WeightedAdditiveVertetwitterCombiner(
  override val vertetwitterFeature: Option[VertetwitterFeature] = None,
  override val startingDay: Int = Integer.MAtwitter_VALUE,
  override val endingDay: Int = Integer.MIN_VALUE,
  override val timeSeriesStatistics: Option[TimeSeriesStatistics] = None)
    etwittertends VFeatureCombiner {
  override def updateTss(
    feature: VertetwitterFeature,
    alpha: Double
  ): WeightedAdditiveVertetwitterCombiner = copy(timeSeriesStatistics = timeSeriesStatistics.map(tss =>
    InteractionGraphUtils.updateTimeSeriesStatistics(tss, feature.tss.mean, alpha)))

  override def addToTss(feature: VertetwitterFeature): WeightedAdditiveVertetwitterCombiner =
    copy(timeSeriesStatistics = timeSeriesStatistics.map(tss =>
      InteractionGraphUtils.addToTimeSeriesStatistics(tss, feature.tss.mean)))

  override def updateFeature(feature: VertetwitterFeature, alpha: Double, day: Int): VFeatureCombiner = {
    updateTss(feature, alpha).copy(
      vertetwitterFeature,
      startingDay = startingDay,
      endingDay = Math.matwitter(endingDay, day)
    )
  }

  override def updateFeature(feature: VertetwitterFeature): VFeatureCombiner =
    addToTss(feature)

  override def setFeature(feature: VertetwitterFeature, alpha: Double, day: Int): VFeatureCombiner = {
    val newStartingDay = Math.min(startingDay, day)
    val newEndingDay = Math.matwitter(endingDay, day)

    val numDaysSinceLast =
      if (feature.tss.numDaysSinceLast.etwitterists(_ > 0))
        feature.tss.numDaysSinceLast
      else Some(feature.tss.numElapsedDays - feature.tss.numNonZeroDays + 1)

    val tss = feature.tss.copy(numDaysSinceLast = numDaysSinceLast)

    val newFeature = VertetwitterFeature(
      name = feature.name,
      outgoing = feature.outgoing,
      tss = tss
    )

    WeightedAdditiveVertetwitterCombiner(
      Some(newFeature),
      newStartingDay,
      newEndingDay,
      Some(tss)
    )
  }

  def getFinalFeature(totalDays: Int): Option[VertetwitterFeature] = {
    if (vertetwitterFeature.isEmpty || dropFeature) return None

    val newTss = if (totalDays > 0) {
      val elapsed =
        timeSeriesStatistics.map(tss => tss.numElapsedDays + totalDays - 1 - startingDay)
      val latest =
        if (endingDay > 0) Some(totalDays - endingDay)
        else timeSeriesStatistics.map(tss => tss.numDaysSinceLast.get + totalDays - 1)

      timeSeriesStatistics.map(tss =>
        tss.copy(
          numElapsedDays = elapsed.get,
          numDaysSinceLast = latest
        ))
    } else timeSeriesStatistics

    vertetwitterFeature.map(vf => vf.copy(tss = newTss.get))
  }

  override def setFeature(feature: VertetwitterFeature): VFeatureCombiner = setFeature(feature, 1.0, 0)
  override def isSet: Boolean = vertetwitterFeature.isDefined
  override def dropFeature: Boolean =
    timeSeriesStatistics.etwitterists(tss =>
      tss.numDaysSinceLast.etwitterists(_ > InteractionGraphUtils.MAtwitter_DAYS_RETENTION) &&
        tss.ewma < InteractionGraphUtils.MIN_FEATURE_VALUE)
}

/**
 * This combiner always replaces the old value with the current. Ignores time-decays.
 */
case class ReplacementVertetwitterCombiner(
  override val vertetwitterFeature: Option[VertetwitterFeature] = None,
  override val startingDay: Int = Integer.MAtwitter_VALUE,
  override val endingDay: Int = Integer.MIN_VALUE,
  override val timeSeriesStatistics: Option[TimeSeriesStatistics] = None)
    etwittertends VFeatureCombiner {
  override def updateTss(
    feature: VertetwitterFeature,
    alpha: Double
  ): ReplacementVertetwitterCombiner = setFeature(feature, 1.0, 0)

  override def addToTss(feature: VertetwitterFeature): ReplacementVertetwitterCombiner =
    setFeature(feature, 1.0, 0)

  override def updateFeature(
    feature: VertetwitterFeature,
    alpha: Double,
    day: Int
  ): ReplacementVertetwitterCombiner = updateTss(feature, alpha).copy(
    vertetwitterFeature,
    startingDay = startingDay,
    endingDay = Math.matwitter(endingDay, day)
  )

  override def updateFeature(feature: VertetwitterFeature): ReplacementVertetwitterCombiner =
    addToTss(feature)

  override def setFeature(
    feature: VertetwitterFeature,
    alpha: Double,
    day: Int
  ): ReplacementVertetwitterCombiner = {
    val newStartingDay = Math.min(startingDay, day)
    val newEndingDay = Math.matwitter(endingDay, day)

    val numDaysSinceLast =
      if (feature.tss.numDaysSinceLast.etwitterists(_ > 0))
        feature.tss.numDaysSinceLast
      else Some(feature.tss.numElapsedDays - feature.tss.numNonZeroDays + 1)

    val tss = feature.tss.copy(numDaysSinceLast = numDaysSinceLast)

    val newFeature = VertetwitterFeature(
      name = feature.name,
      outgoing = feature.outgoing,
      tss = tss
    )

    ReplacementVertetwitterCombiner(
      Some(newFeature),
      newStartingDay,
      newEndingDay,
      Some(tss)
    )
  }

  override def getFinalFeature(totalDays: Int): Option[VertetwitterFeature] = {
    if (vertetwitterFeature.isEmpty || dropFeature) return None
    if (timeSeriesStatistics.etwitterists(tss => tss.ewma < 1.0)) return None
    val newTss = if (totalDays > 0) {
      val latest =
        if (endingDay > 0) totalDays - endingDay
        else timeSeriesStatistics.get.numDaysSinceLast.get + totalDays - 1

      timeSeriesStatistics.map(tss =>
        tss.copy(
          numElapsedDays = 1,
          numDaysSinceLast = Some(latest)
        ))
    } else timeSeriesStatistics

    vertetwitterFeature.map(vf => vf.copy(tss = newTss.get))
  }

  override def setFeature(feature: VertetwitterFeature): VFeatureCombiner = setFeature(feature, 1.0, 0)
  override def isSet: Boolean = vertetwitterFeature.isDefined
  override def dropFeature: Boolean =
    timeSeriesStatistics.etwitterists(tss =>
      tss.numDaysSinceLast.etwitterists(_ > InteractionGraphUtils.MAtwitter_DAYS_RETENTION) &&
        tss.ewma < InteractionGraphUtils.MIN_FEATURE_VALUE)
}
