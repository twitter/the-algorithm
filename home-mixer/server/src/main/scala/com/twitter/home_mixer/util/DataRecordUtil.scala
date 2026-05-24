package com.twitter.home_mixer.util

import com.twitter.ml.api.DataRecord
import com.twitter.ml.api.FeatureContext
import com.twitter.ml.api.util.SRichDataRecord
import com.twitter.ml.api.Feature
import java.lang.{Double => JDouble}

object DataRecordUtil {
  def applyRename(
    dataRecord: DataRecord,
    featureContext: FeatureContext,
    renamedFeatureContext: FeatureContext,
    featureRenamingMap: Map[Feature[_], Feature[_]]
  ): DataRecord = {
    val richFullDr = new SRichDataRecord(dataRecord, featureContext)
    val richNewDr = new SRichDataRecord(new DataRecord, renamedFeatureContext)
    val featureIterator = featureContext.iterator()
    featureIterator.forEachRemaining { feature =>
      if (richFullDr.hasFeature(feature)) {
        val renamedFeature = featureRenamingMap.getOrElse(feature, feature)

        val typedFeature = feature.asInstanceOf[Feature[JDouble]]
        val typedRenamedFeature = renamedFeature.asInstanceOf[Feature[JDouble]]

        richNewDr.setFeatureValue(typedRenamedFeature, richFullDr.getFeatureValue(typedFeature))
      }
    }
    richNewDr.getRecord
  }
}
