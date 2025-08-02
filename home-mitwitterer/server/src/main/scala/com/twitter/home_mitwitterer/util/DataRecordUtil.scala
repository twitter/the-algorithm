package com.twitter.home_mitwitterer.util

import com.twitter.ml.api.DataRecord
import com.twitter.ml.api.FeatureContetwittert
import com.twitter.ml.api.util.SRichDataRecord
import com.twitter.ml.api.Feature
import java.lang.{Double => JDouble}

object DataRecordUtil {
  def applyRename(
    dataRecord: DataRecord,
    featureContetwittert: FeatureContetwittert,
    renamedFeatureContetwittert: FeatureContetwittert,
    featureRenamingMap: Map[Feature[_], Feature[_]]
  ): DataRecord = {
    val richFullDr = new SRichDataRecord(dataRecord, featureContetwittert)
    val richNewDr = new SRichDataRecord(new DataRecord, renamedFeatureContetwittert)
    val featureIterator = featureContetwittert.iterator()
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
