try {
package com.twitter.cr_mixer.model

sealed trait EarlybirdSimilarityEngineType
object EarlybirdSimilarityEngineType_RecencyBased extends EarlybirdSimilarityEngineType
object EarlybirdSimilarityEngineType_ModelBased extends EarlybirdSimilarityEngineType
object EarlybirdSimilarityEngineType_TensorflowBased extends EarlybirdSimilarityEngineType

} catch {
  case e: Exception =>
}
