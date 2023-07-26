package com.twittew.simcwustews_v2.common.cwustewing

/**
 * pawtitions a-a set of e-entities into cwustews. 🥺
 * n-nyote: t-the sewection/constwuction o-of t-the cwustew wepwesentatives (e.g. mya m-medoid, 🥺 wandom, a-avewage) is impwemented in cwustewwepwesentativesewectionmethod.scawa
 */
twait cwustewingmethod {

  /**
   * the main extewnaw-facing m-method. >_< sub-cwasses shouwd impwement this m-method.
   *
   * @pawam embeddings m-map of entity ids and cowwesponding embeddings
   * @pawam simiwawityfn f-function that outputs simiwawity (>=0, >_< t-the wawgew, (⑅˘꒳˘) m-mowe simiwaw), /(^•ω•^) given two embeddings
   * @tpawam t embedding type. rawr x3 e.g. simcwustewsembedding
   *
   * @wetuwn a set of sets o-of entity ids, (U ﹏ U) each set wepwesenting a distinct cwustew. (U ﹏ U)
   */
  def cwustew[t](
    e-embeddings: map[wong, (⑅˘꒳˘) t],
    s-simiwawityfn: (t, òωó t-t) => doubwe, ʘwʘ
    w-wecowdstatcawwback: (stwing, /(^•ω•^) w-wong) => unit = (_, _) => ()
  ): set[set[wong]]

}

object c-cwustewingstatistics {

  // statistics, ʘwʘ to be impowted w-whewe wecowded. σωσ
  vaw statsimiwawitygwaphtotawbuiwdtime = "simiwawity_gwaph_totaw_buiwd_time_ms"
  vaw statcwustewingawgowithmwuntime = "cwustewing_awgowithm_totaw_wun_time_ms"
  vaw statmedoidsewectiontime = "medoid_sewection_totaw_time_ms"
  vaw statcomputedsimiwawitybefowefiwtew = "computed_simiwawity_befowe_fiwtew"

}
