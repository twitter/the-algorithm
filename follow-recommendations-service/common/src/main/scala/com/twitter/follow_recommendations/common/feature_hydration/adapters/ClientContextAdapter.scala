package com.tw ter.follow_recom ndat ons.common.feature_hydrat on.adapters

 mport com.tw ter.follow_recom ndat ons.common.models.D splayLocat on
 mport com.tw ter.ml.ap .Feature.B nary
 mport com.tw ter.ml.ap .Feature.Cont nuous
 mport com.tw ter.ml.ap .Feature.D screte
 mport com.tw ter.ml.ap .Feature.Text
 mport com.tw ter.ml.ap .ut l.FDsl._
 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.ml.ap .FeatureContext
 mport com.tw ter.ml.ap . RecordOneToOneAdapter
 mport com.tw ter.onboard ng.relevance.ut l. tadata.LanguageUt l
 mport com.tw ter.product_m xer.core.model.marshall ng.request.Cl entContext
 mport com.tw ter.snowflake. d.Snowflake d

object Cl entContextAdapter extends  RecordOneToOneAdapter[(Cl entContext, D splayLocat on)] {

  //   na  features w h `user.account` for relat vely stat c user-related features
  val USER_COUNTRY: Text = new Text("user.account.country")
  val USER_LANGUAGE: Text = new Text("user.account.language")
  //   na  features w h `user.context` for more dynam c user-related features
  val USER_LANGUAGE_PREF X: Text = new Text("user.context.language_pref x")
  val USER_CL ENT: D screte = new D screte("user.context.cl ent")
  val USER_AGE: Cont nuous = new Cont nuous("user.context.age")
  val USER_ S_RECENT: B nary = new B nary("user. s.recent")
  //   na  features w h ` ta` for  ta  nfo about t  WTF recom ndat on request
  val META_D SPLAY_LOCAT ON: Text = new Text(" ta.d splay_locat on")
  val META_POS T ON: D screte = new D screte(" ta.pos  on")
  // T   nd cates w t r a data po nt  s from a random serv ng pol cy
  val META_ S_RANDOM: B nary = new B nary("pred ct on.eng ne. s_random")

  val RECENT_W N_ N_DAYS:  nt = 30
  val GOAL_META_POS T ON: Long = 1L
  val GOAL_META_ S_RANDOM: Boolean = true

  overr de val getFeatureContext: FeatureContext = new FeatureContext(
    USER_COUNTRY,
    USER_LANGUAGE,
    USER_AGE,
    USER_LANGUAGE_PREF X,
    USER_CL ENT,
    USER_ S_RECENT,
    META_D SPLAY_LOCAT ON,
    META_POS T ON,
    META_ S_RANDOM
  )

  /**
   *   only want to set t  relevant f elds  ff t y ex st to el m nate redundant  nformat on
   *   do so  s mple normal zat on on t  language code
   *   set META_POS T ON to 1 always
   *   set META_ S_RANDOM to true always to s mulate a random serv ng d str but on
   * @param record Cl entContext and D splayLocat on from t  request
   */
  overr de def adaptToDataRecord(target: (Cl entContext, D splayLocat on)): DataRecord = {
    val dr = new DataRecord()
    val cc = target._1
    val dl = target._2
    cc.countryCode.foreach(countryCode => dr.setFeatureValue(USER_COUNTRY, countryCode))
    cc.languageCode.foreach(rawLanguageCode => {
      val userLanguage = LanguageUt l.s mpl fyLanguage(rawLanguageCode)
      val userLanguagePref x = userLanguage.take(2)
      dr.setFeatureValue(USER_LANGUAGE, userLanguage)
      dr.setFeatureValue(USER_LANGUAGE_PREF X, userLanguagePref x)
    })
    cc.app d.foreach(app d => dr.setFeatureValue(USER_CL ENT, app d))
    cc.user d.foreach( d =>
      Snowflake d.t  From dOpt( d).map { s gnupT   =>
        val userAge = s gnupT  .unt lNow. nM ll s.toDouble
        dr.setFeatureValue(USER_AGE, userAge)
        dr.setFeatureValue(USER_ S_RECENT, s gnupT  .unt lNow. nDays <= RECENT_W N_ N_DAYS)
        s gnupT  .unt lNow. nDays
      })
    dr.setFeatureValue(META_D SPLAY_LOCAT ON, dl.toFsNa )
    dr.setFeatureValue(META_POS T ON, GOAL_META_POS T ON)
    dr.setFeatureValue(META_ S_RANDOM, GOAL_META_ S_RANDOM)
    dr
  }
}
