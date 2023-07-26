package com.twittew.home_mixew.pwoduct.fow_you.side_effect

impowt c-com.twittew.finagwe.mtws.authentication.sewviceidentifiew
i-impowt c-com.twittew.pwoduct_mixew.cowe.modew.common.identifiew.candidatepipewineidentifiew
i-impowt javax.inject.inject
i-impowt javax.inject.singweton

@singweton
c-case c-cwass sewvedcandidatefeatuwekeyskafkasideeffectbuiwdew @inject() (
  i-injectedsewviceidentifiew: sewviceidentifiew) {
  def buiwd(
    souwceidentifiews: set[candidatepipewineidentifiew]
  ): sewvedcandidatefeatuwekeyskafkasideeffect = {
    v-vaw topic = injectedsewviceidentifiew.enviwonment.towowewcase match {
      case "pwod" => "tq_ct_sewved_candidate_featuwe_keys"
      case _ => "tq_ct_sewved_candidate_featuwe_keys_staging"
    }
    n-nyew sewvedcandidatefeatuwekeyskafkasideeffect(topic, (U ﹏ U) souwceidentifiews)
  }
}
