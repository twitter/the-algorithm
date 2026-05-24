package com.twitter.follow_recommendations.modules

import com.google.inject.Provides
import com.google.inject.Singleton
import com.twitter.inject.TwitterModule
import com.twitter.relevance.ep_model.common.CommonConstants
import com.twitter.relevance.ep_model.scorer.EPScorer
import com.twitter.relevance.ep_model.scorer.EPScorerBuilder
import java.io.File
import java.io.FileOutputStream
import scala.language.postfixOps

object ScorerModule extends TwitterModule {
  private val STPScorerPath = "/quality/stp_models/20141223"

  private def fileFromResource(resource: String): File = {
    val inputStream = getClass.getResourceAsStream(resource)
    val file = File.createTempFile(resource, "temp")
    val fos = new FileOutputStream(file)
    Iterator
      .continually(inputStream.read)
      .takeWhile(-1 !=)
      .foreach(fos.write)
    file
  }

  @Provides
  @Singleton
  def provideEpScorer: EPScorer = {
    val modelPath =
      fileFromResource(STPScorerPath + "/" + CommonConstants.EP_MODEL_FILE_NAME).getAbsolutePath
    val trainingConfigPath =
      fileFromResource(STPScorerPath + "/" + CommonConstants.TRAINING_CONFIG).getAbsolutePath
    val epScorer = new EPScorerBuilder
    epScorer
      .withModelPath(modelPath)
      .withTrainingConfig(trainingConfigPath)
      .build()
  }
}
