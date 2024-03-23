package com.ExTwitter.cr_mixer.filter

import com.ExTwitter.cr_mixer.model.CandidateGeneratorQuery
import com.ExTwitter.cr_mixer.model.InitialCandidate
import com.ExTwitter.util.Future

trait FilterBase {
  def name: String

  type ConfigType

  def filter(
    candidates: Seq[Seq[InitialCandidate]],
    config: ConfigType
  ): Future[Seq[Seq[InitialCandidate]]]

  /**
   * Build the config params here. passing in param() into the filter is strongly discouraged
   * because param() can be slow when called many times
   */
  def requestToConfig[CGQueryType <: CandidateGeneratorQuery](request: CGQueryType): ConfigType
}
