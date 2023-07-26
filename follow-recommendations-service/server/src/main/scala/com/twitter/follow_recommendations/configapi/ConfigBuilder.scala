package com.twittew.fowwow_wecommendations.configapi

impowt com.twittew.timewines.configapi.compositeconfig
i-impowt c-com.twittew.timewines.configapi.config
i-impowt j-javax.inject.inject
i-impowt javax.inject.singweton

@singweton
cwass c-configbuiwdew @inject() (
  d-decidewconfigs: d-decidewconfigs, mya
  featuweswitchconfigs: featuweswitchconfigs) {
  // the owdew of configs added t-to `compositeconfig` is impowtant. 😳 the config wiww b-be matched with
  // the fiwst p-possibwe wuwe. XD so, cuwwent setup wiww give pwiowity to decidews i-instead of fs
  def buiwd(): c-config =
    nyew c-compositeconfig(seq(decidewconfigs.config, :3 featuweswitchconfigs.config))
}
