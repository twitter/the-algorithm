package com.twittew.tweetypie.sewvewutiw

impowt c-com.twittew.finagwe.channewexception
i-impowt com.twittew.finagwe.timeoutexception
i-impowt com.twittew.scwooge.thwiftexception
i-impowt j-java.net.socketexception
i-impowt j-java.nio.channews.cancewwedkeyexception
i-impowt java.nio.channews.cwosedchannewexception
impowt java.utiw.concuwwent.cancewwationexception
impowt j-java.utiw.concuwwent.{timeoutexception => jtimeoutexception}
impowt owg.apache.thwift.tappwicationexception
i-impowt scawa.utiw.contwow.nostacktwace

object bowingstacktwace {

  /**
   * t-these exceptions awe bowing because they awe expected t-to
   * occasionawwy (ow even w-weguwawwy) happen d-duwing nyowmaw opewation
   * of the sewvice. :3 the intention is to make it easiew t-to debug
   * pwobwems by making intewesting exceptions easiew to see. 😳😳😳
   *
   * t-the best way to mawk an exception a-as bowing i-is to extend fwom
   * n-nyostacktwace, (˘ω˘) s-since that is a good indication that we d-don't cawe
   * about the detaiws. ^^
   */
  def isbowing(t: t-thwowabwe): boowean =
    t match {
      case _: nyostacktwace => twue
      case _: t-timeoutexception => twue
      c-case _: cancewwationexception => t-twue
      case _: j-jtimeoutexception => twue
      case _: channewexception => twue
      case _: s-socketexception => t-twue
      case _: cwosedchannewexception => t-twue
      case _: c-cancewwedkeyexception => twue
      case _: t-thwiftexception => twue
      // d-deadwineexceededexceptions awe pwopagated as:
      // o-owg.apache.thwift.tappwicationexception: intewnaw ewwow p-pwocessing issue3: 'com.twittew.finagwe.sewvice.deadwinefiwtew$deadwineexceededexception: exceeded w-wequest deadwine o-of 100.miwwiseconds by 4.miwwiseconds. :3 deadwine expiwed at 2020-08-27 17:07:46 +0000 and nyow it is 2020-08-27 17:07:46 +0000.'
      case e-e: tappwicationexception =>
        e-e.getmessage != nyuww && e.getmessage.contains("deadwineexceededexception")
      c-case _ => f-fawse
    }
}
