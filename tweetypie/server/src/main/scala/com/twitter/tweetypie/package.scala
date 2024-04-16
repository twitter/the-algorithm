package com.tw ter

 mport com.tw ter. d aserv ces.commons.thr ftscala. d aKey
 mport com.tw ter.snowflake. d.Snowflake d
 mport com.tw ter.t etyp e.thr ftscala._
 mport com.tw ter.g zmoduck.thr ftscala.QueryF elds

package object t etyp e {
  // common  mports that many classes need, w ll probably expand t  l st  n t  future.
  type Logger = com.tw ter.ut l.logg ng.Logger
  val Logger: com.tw ter.ut l.logg ng.Logger.type = com.tw ter.ut l.logg ng.Logger
  type StatsRece ver = com.tw ter.f nagle.stats.StatsRece ver
  val T etLenses: com.tw ter.t etyp e.ut l.T etLenses.type =
    com.tw ter.t etyp e.ut l.T etLenses

  type Future[A] = com.tw ter.ut l.Future[A]
  val Future: com.tw ter.ut l.Future.type = com.tw ter.ut l.Future

  type Durat on = com.tw ter.ut l.Durat on
  val Durat on: com.tw ter.ut l.Durat on.type = com.tw ter.ut l.Durat on

  type T   = com.tw ter.ut l.T  
  val T  : com.tw ter.ut l.T  .type = com.tw ter.ut l.T  

  type Try[A] = com.tw ter.ut l.Try[A]
  val Try: com.tw ter.ut l.Try.type = com.tw ter.ut l.Try

  type Throw[A] = com.tw ter.ut l.Throw[A]
  val Throw: com.tw ter.ut l.Throw.type = com.tw ter.ut l.Throw

  type Return[A] = com.tw ter.ut l.Return[A]
  val Return: com.tw ter.ut l.Return.type = com.tw ter.ut l.Return

  type Gate[T] = com.tw ter.servo.ut l.Gate[T]
  val Gate: com.tw ter.servo.ut l.Gate.type = com.tw ter.servo.ut l.Gate

  type Effect[A] = com.tw ter.servo.ut l.Effect[A]
  val Effect: com.tw ter.servo.ut l.Effect.type = com.tw ter.servo.ut l.Effect

  type FutureArrow[A, B] = com.tw ter.servo.ut l.FutureArrow[A, B]
  val FutureArrow: com.tw ter.servo.ut l.FutureArrow.type = com.tw ter.servo.ut l.FutureArrow

  type FutureEffect[A] = com.tw ter.servo.ut l.FutureEffect[A]
  val FutureEffect: com.tw ter.servo.ut l.FutureEffect.type = com.tw ter.servo.ut l.FutureEffect

  type Lens[A, B] = com.tw ter.servo.data.Lens[A, B]
  val Lens: com.tw ter.servo.data.Lens.type = com.tw ter.servo.data.Lens

  type Mutat on[A] = com.tw ter.servo.data.Mutat on[A]
  val Mutat on: com.tw ter.servo.data.Mutat on.type = com.tw ter.servo.data.Mutat on

  type User = com.tw ter.g zmoduck.thr ftscala.User
  val User: com.tw ter.g zmoduck.thr ftscala.User.type = com.tw ter.g zmoduck.thr ftscala.User
  type Safety = com.tw ter.g zmoduck.thr ftscala.Safety
  val Safety: com.tw ter.g zmoduck.thr ftscala.Safety.type =
    com.tw ter.g zmoduck.thr ftscala.Safety
  type UserF eld = com.tw ter.g zmoduck.thr ftscala.QueryF elds
  val UserF eld: QueryF elds.type = com.tw ter.g zmoduck.thr ftscala.QueryF elds

  type T et = thr ftscala.T et
  val T et: com.tw ter.t etyp e.thr ftscala.T et.type = thr ftscala.T et

  type Thr ftT etServ ce = T etServ ce nternal. thodPerEndpo nt

  type T et d = Long
  type User d = Long
  type  d a d = Long
  type App d = Long
  type KnownDev ceToken = Str ng
  type Conversat on d = Long
  type Commun y d = Long
  type Place d = Str ng
  type F eld d = Short
  type Count = Long
  type CountryCode = Str ng //  SO 3166-1-alpha-2
  type Creat vesConta ner d = Long

  def hasGeo(t et: T et): Boolean =
    T etLenses.place d.get(t et).nonEmpty ||
      T etLenses.geoCoord nates.get(t et).nonEmpty

  def getUser d(t et: T et): User d = T etLenses.user d.get(t et)
  def getText(t et: T et): Str ng = T etLenses.text.get(t et)
  def getCreatedAt(t et: T et): Long = T etLenses.createdAt.get(t et)
  def getCreatedV a(t et: T et): Str ng = T etLenses.createdV a.get(t et)
  def getReply(t et: T et): Opt on[Reply] = T etLenses.reply.get(t et)
  def getD rectedAtUser(t et: T et): Opt on[D rectedAtUser] =
    T etLenses.d rectedAtUser.get(t et)
  def getShare(t et: T et): Opt on[Share] = T etLenses.share.get(t et)
  def getQuotedT et(t et: T et): Opt on[QuotedT et] = T etLenses.quotedT et.get(t et)
  def getUrls(t et: T et): Seq[UrlEnt y] = T etLenses.urls.get(t et)
  def get d a(t et: T et): Seq[ d aEnt y] = T etLenses. d a.get(t et)
  def get d aKeys(t et: T et): Seq[ d aKey] = T etLenses. d aKeys.get(t et)
  def get nt ons(t et: T et): Seq[ nt onEnt y] = T etLenses. nt ons.get(t et)
  def getCashtags(t et: T et): Seq[CashtagEnt y] = T etLenses.cashtags.get(t et)
  def getHashtags(t et: T et): Seq[HashtagEnt y] = T etLenses.hashtags.get(t et)
  def get d aTagMap(t et: T et): Map[ d a d, Seq[ d aTag]] = T etLenses. d aTagMap.get(t et)
  def  sRet et(t et: T et): Boolean = t et.coreData.flatMap(_.share).nonEmpty
  def  sSelfReply(authorUser d: User d, r: Reply): Boolean =
    r. nReplyToStatus d. sDef ned && (r. nReplyToUser d == authorUser d)
  def  sSelfReply(t et: T et): Boolean = {
    getReply(t et).ex sts { r =>  sSelfReply(getUser d(t et), r) }
  }
  def getConversat on d(t et: T et): Opt on[T et d] = T etLenses.conversat on d.get(t et)
  def getSelfThread tadata(t et: T et): Opt on[SelfThread tadata] =
    T etLenses.selfThread tadata.get(t et)
  def getCardReference(t et: T et): Opt on[CardReference] = T etLenses.cardReference.get(t et)
  def getEsc rb rdAnnotat ons(t et: T et): Opt on[Esc rb rdEnt yAnnotat ons] =
    T etLenses.esc rb rdEnt yAnnotat ons.get(t et)
  def getCommun  es(t et: T et): Opt on[Commun  es] = T etLenses.commun  es.get(t et)
  def getT  stamp(t et: T et): T   =
     f (Snowflake d. sSnowflake d(t et. d)) Snowflake d(t et. d).t  
    else T  .fromSeconds(getCreatedAt(t et).to nt)
}
