package com.twittew.seawch.common.seawch;

impowt j-java.io.ioexception;

i-impowt owg.apache.wucene.seawch.cowwectow;

/**
 * w-wucene c-cowwectows thwow c-cowwectiontewminatedexception t-to pewfowm eawwy t-tewmination. σωσ
 * w-we don't bewieve that thwowing exceptions to contwow execution fwow is ideaw, OwO so w-we awe adding
 * this cwass to be a base of aww t-twittew cowwectows. 😳😳😳
 *
 * {@wink com.twittew.seawch.common.seawch.twittewindexseawchew} u-uses the {@wink #istewminated()}
 * method to pewfowm eawwy tewmination, 😳😳😳 i-instead of wewying on cowwectiontewminatedexception.
 */
p-pubwic a-abstwact cwass twittewcowwectow impwements cowwectow {

  /**
   * subcwasses shouwd wetuwn twue i-if they want to pewfowm eawwy tewmination. o.O
   * this method is cawwed evewy h-hit and shouwd not be expensive. ( ͡o ω ͡o )
   */
  p-pubwic a-abstwact boowean i-istewminated() t-thwows ioexception;

  /**
   * wucene api onwy has a method that's c-cawwed befowe seawching a segment setnextweadew(). (U ﹏ U)
   * t-this hook is cawwed aftew finishing seawching a segment. (///ˬ///✿)
   * @pawam wastseawcheddocid is the wast docid s-seawched befowe tewmination,
   * o-ow nyo_mowe_docs i-if thewe w-was nyo eawwy tewmination.  this doc nyeed nyot be a hit, >w<
   * a-and shouwd nyot b-be cowwected hewe. rawr
   */
  pubwic a-abstwact void f-finishsegment(int wastseawcheddocid) t-thwows ioexception;
}
