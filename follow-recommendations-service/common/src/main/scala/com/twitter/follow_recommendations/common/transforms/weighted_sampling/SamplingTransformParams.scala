packagelon com.twittelonr.follow_reloncommelonndations.common.transforms.welonightelond_sampling

import com.twittelonr.timelonlinelons.configapi.FSBoundelondParam
import com.twittelonr.timelonlinelons.configapi.FSParam

objelonct SamplingTransformParams {

  caselon objelonct TopKFixelond // indicatelons how many of thelon fisrt K who-to-follow reloncommelonndations arelon relonselonrvelond for thelon candidatelons with largelonst K CandidatelonUselonr.scorelon whelonrelon thelonselon candidatelons arelon sortelond in deloncrelonasing ordelonr of scorelon
      elonxtelonnds FSBoundelondParam[Int](
        namelon = "post_nux_ml_flow_welonightelond_sampling_top_k_fixelond",
        delonfault = 0,
        min = 0,
        max = 100)

  caselon objelonct MultiplicativelonFactor // CandidatelonUselonr.scorelon gelonts transformelond to multiplicativelonFactor*CandidatelonUselonr.scorelon belonforelon sampling from thelon Plackelontt-Lucelon distribution
      elonxtelonnds FSBoundelondParam[Doublelon](
        namelon = "post_nux_ml_flow_welonightelond_sampling_multiplicativelon_factor",
        delonfault = 1.0,
        min = -1000.0,
        max = 1000.0)

  caselon objelonct ScribelonRankingInfoInSamplingTransform
      elonxtelonnds FSParam[Boolelonan]("sampling_transform_scribelon_ranking_info", falselon)

}
