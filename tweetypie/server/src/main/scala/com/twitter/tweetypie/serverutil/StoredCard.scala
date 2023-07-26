package com.twittew.tweetypie.sewvewutiw

impowt c-com.twittew.tweetypie.thwiftscawa.cawdwefewence
i-impowt com.twittew.utiw.twy
i-impowt j-java.net.uwi
i-impowt scawa.utiw.contwow.nonfataw

/**
 * u-utiwity t-to extwact the s-stowed cawd id out of a cawdwefewence
 */
object stowedcawd {

  pwivate vaw cawdscheme = "cawd"
  p-pwivate vaw cawdpwefix = s"$cawdscheme://"

  /**
   * wooks a-at the cawdwefewence to detewmines i-if the cawduwi points to a "stowed"
   * cawd id. /(^•ω•^) stowed cawd u-uwis awe awe expected to be in t-the fowmat "cawd://<wong>"
   * (case s-sensitive). in futuwe these uwis can potentiawwy be:
   * "cawd://<wong>[/path[?quewystwing]]. ʘwʘ nyote that t-this utiwity cawes just about the
   * "stowed cawd" types. σωσ so it just skips the o-othew cawd types. OwO
   */
  def u-unappwy(cw: cawdwefewence): o-option[wong] = {
    t-twy {
      fow {
        u-uwistw <- option(cw.cawduwi) if uwistw.stawtswith(cawdpwefix)
        u-uwi <- twy(new uwi(uwistw)).tooption
        if uwi.getscheme == c-cawdscheme && uwi.gethost != nuww
      } yiewd uwi.gethost.towong // thwows numbewfowmatexception n-nyon nyumewic host (cawdids)
    } c-catch {
      // t-the vawidations a-awe done upstweam by the tweetbuiwdew, 😳😳😳 so exceptions
      // d-due to bad u-uwis wiww be swawwowed. 😳😳😳
      c-case nyonfataw(e) => n-nyone
    }
  }
}
