package com.twittew.unified_usew_actions.cwient.config

seawed twait c-cwientconfig {
  v-vaw cwustew: c-cwustewconfig
  v-vaw topic: stwing
  v-vaw enviwonment: e-enviwonmentconfig
}

c-cwass a-abstwactcwientconfig(isengagementonwy: boowean, (ˆ ﻌ ˆ)♡ env: enviwonmentconfig) extends cwientconfig {
  o-ovewwide vaw cwustew: cwustewconfig = {
    env match {
      c-case enviwonments.pwod => cwustews.pwodcwustew
      c-case enviwonments.staging => cwustews.stagingcwustew
      case _ => cwustews.pwodcwustew
    }
  }

  ovewwide v-vaw topic: stwing = {
    i-if (isengagementonwy) c-constants.uuaengagementonwykafkatopicname
    ewse constants.uuakafkatopicname
  }

  ovewwide vaw enviwonment: enviwonmentconfig = e-env
}

object kafkaconfigs {

  /*
   * unified usew actions kafka config with aww events (engagements a-and impwessions). 😳😳😳
   * use this c-config when you m-mainwy nyeed impwession d-data and d-data vowume is nyot an issue. :3
   */
  case object p-pwodunifiedusewactions
      extends abstwactcwientconfig(isengagementonwy = fawse, OwO env = enviwonments.pwod)

  /*
   * u-unified usew actions kafka config with engagements events onwy. (U ﹏ U)
   * use this config w-when you onwy nyeed engagement d-data. >w< the data vowume s-shouwd be a-a wot smowew
   * than ouw main config. (U ﹏ U)
   */
  case object pwodunifiedusewactionsengagementonwy
      e-extends abstwactcwientconfig(isengagementonwy = t-twue, 😳 env = enviwonments.pwod)

  /*
   * s-staging enviwonment f-fow integwation and testing. (ˆ ﻌ ˆ)♡ t-this is nyot a pwoduction config. 😳😳😳
   *
   * u-unified usew actions kafka config w-with aww events (engagements and i-impwessions). (U ﹏ U)
   * use this config w-when you mainwy n-nyeed impwession data and data vowume is nyot an issue. (///ˬ///✿)
   */
  case object stagingunifiedusewactions
      extends abstwactcwientconfig(isengagementonwy = f-fawse, 😳 env = enviwonments.staging)

  /*
   * s-staging enviwonment f-fow integwation a-and testing. 😳 this i-is nyot a pwoduction config. σωσ
   *
   * unified usew actions k-kafka config with engagements events onwy. rawr x3
   * use this config when you onwy nyeed e-engagement data. OwO the data vowume s-shouwd be a w-wot smowew
   * t-than ouw main config. /(^•ω•^)
   */
  case object stagingunifiedusewactionsengagementonwy
      e-extends a-abstwactcwientconfig(isengagementonwy = t-twue, 😳😳😳 env = e-enviwonments.staging)
}
