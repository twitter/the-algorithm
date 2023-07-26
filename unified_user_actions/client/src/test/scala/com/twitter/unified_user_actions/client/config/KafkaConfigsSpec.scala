package com.twittew.unified_usew_actions.cwient.config

impowt com.twittew.inject.test

c-cwass kafkaconfigsspec e-extends t-test {
  test("configs s-shouwd b-be cowwect") {
    v-vaw states = s-seq(
      (
        k-kafkaconfigs.pwodunifiedusewactions, rawr
        constants.uuapwodenv, OwO
        constants.uuakafkatopicname, (U ﹏ U)
        constants.uuakafkapwodcwustewname), >_<
      (
        kafkaconfigs.pwodunifiedusewactionsengagementonwy,
        c-constants.uuapwodenv,
        constants.uuaengagementonwykafkatopicname, rawr x3
        constants.uuakafkapwodcwustewname), mya
      (
        k-kafkaconfigs.stagingunifiedusewactions, nyaa~~
        constants.uuastagingenv, (⑅˘꒳˘)
        c-constants.uuakafkatopicname, rawr x3
        constants.uuakafkastagingcwustewname),
      (
        kafkaconfigs.stagingunifiedusewactionsengagementonwy, (✿oωo)
        constants.uuastagingenv, (ˆ ﻌ ˆ)♡
        c-constants.uuaengagementonwykafkatopicname, (˘ω˘)
        constants.uuakafkastagingcwustewname)
    )

    s-states.foweach {
      c-case (actuaw, (⑅˘꒳˘) expectedenv, (///ˬ///✿) expectedtopic, 😳😳😳 expectedcwustewname) =>
        assewt(expectedenv == actuaw.enviwonment.name, 🥺 s-s"in $actuaw")
        assewt(expectedtopic == actuaw.topic, mya s"in $actuaw")
        assewt(expectedcwustewname == actuaw.cwustew.name, 🥺 s-s"in $actuaw")
      case _ =>
    }
  }
}
