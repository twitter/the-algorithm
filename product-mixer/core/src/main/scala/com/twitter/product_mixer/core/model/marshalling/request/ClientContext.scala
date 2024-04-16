package com.tw ter.product_m xer.core.model.marshall ng.request

 mport com.fasterxml.jackson.annotat on.Json gnore
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.BadRequest
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure

/**
 * Cl entContext conta ns f elds related to t  cl ent mak ng t  request.
 */
case class Cl entContext(
  user d: Opt on[Long],
  guest d: Opt on[Long],
  guest dAds: Opt on[Long],
  guest dMarket ng: Opt on[Long],
  app d: Opt on[Long],
   pAddress: Opt on[Str ng],
  userAgent: Opt on[Str ng],
  countryCode: Opt on[Str ng],
  languageCode: Opt on[Str ng],
   sTwoff ce: Opt on[Boolean],
  userRoles: Opt on[Set[Str ng]],
  dev ce d: Opt on[Str ng],
  mob leDev ce d: Opt on[Str ng],
  mob leDev ceAd d: Opt on[Str ng],
  l m AdTrack ng: Opt on[Boolean])

object Cl entContext {
  val empty: Cl entContext = Cl entContext(
    user d = None,
    guest d = None,
    guest dAds = None,
    guest dMarket ng = None,
    app d = None,
     pAddress = None,
    userAgent = None,
    countryCode = None,
    languageCode = None,
     sTwoff ce = None,
    userRoles = None,
    dev ce d = None,
    mob leDev ce d = None,
    mob leDev ceAd d = None,
    l m AdTrack ng = None
  )
}

/**
 * HasCl entContext  nd cates that a request has [[Cl entContext]] and adds  lper funct ons for
 * access ng [[Cl entContext]] f elds.
 */
tra  HasCl entContext {
  def cl entContext: Cl entContext

  /**
   * getRequ redUser d returns a user d and throw  f  's m ss ng.
   *
   * @note logged out requests are d sabled by default so t   s safe for most products
   */
  @Json gnore /** Jackson tr es to ser al ze t   thod, throw ng an except on for guest products */
  def getRequ redUser d: Long = cl entContext.user d.getOrElse(
    throw P pel neFa lure(BadRequest, "M ss ng requ red f eld: user d"))

  /**
   * getOpt onalUser d returns a user d  f one  s set
   */
  def getOpt onalUser d: Opt on[Long] = cl entContext.user d

  /**
   * getUser dLoggedOutSupport returns a user d and falls back to 0  f none  s set
   */
  def getUser dLoggedOutSupport: Long = cl entContext.user d.getOrElse(0L)

  /**
   * getUserOrGuest d returns a user d or a guest d  f no user d has been set
   */
  def getUserOrGuest d: Opt on[Long] = cl entContext.user d.orElse(cl entContext.guest d)

  /**
   * getCountryCode returns a country code  f one  s set
   */
  def getCountryCode: Opt on[Str ng] = cl entContext.countryCode

  /**
   * getLanguageCode returns a language code  f one  s set
   */
  def getLanguageCode: Opt on[Str ng] = cl entContext.languageCode

  /**
   *  sLoggedOut returns true  f t  user  s logged out (no user d present).
   *
   * @note t  can be useful  n conjunct on w h [[getUser dLoggedOutSupport]]
   */
  def  sLoggedOut: Boolean = cl entContext.user d. sEmpty
}
