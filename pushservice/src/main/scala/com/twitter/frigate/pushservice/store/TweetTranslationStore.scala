package com.tw ter.fr gate.pushserv ce.store

 mport com.tw ter.context.Tw terContext
 mport com.tw ter.context.thr ftscala.V e r
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.Tw terContextPerm 
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.Target
 mport com.tw ter.fr gate.pushserv ce.params.PushFeatureSw chParams
 mport com.tw ter.fr gate.pushserv ce.params.PushParams
 mport com.tw ter.fr gate.thr ftscala.CommonRecom ndat onType
 mport com.tw ter.kujaku.doma n.thr ftscala.Cac UsageType
 mport com.tw ter.kujaku.doma n.thr ftscala.Mach neTranslat on
 mport com.tw ter.kujaku.doma n.thr ftscala.Mach neTranslat onResponse
 mport com.tw ter.kujaku.doma n.thr ftscala.Translat onS ce
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.strato.generated.cl ent.translat on.serv ce. sT etTranslatableCl entColumn
 mport com.tw ter.strato.generated.cl ent.translat on.serv ce.platform.Mach neTranslateT etCl entColumn
 mport com.tw ter.t etyp e.thr ftscala.T et
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.logg ng.Logg ng

object T etTranslat onStore {
  case class Key(
    target: Target,
    t et d: Long,
    t et: Opt on[T et],
    crt: CommonRecom ndat onType)

  case class Value(
    translatedT etText: Str ng,
    local zedS ceLanguage: Str ng)

  val allo dCRTs = Set[CommonRecom ndat onType](
    CommonRecom ndat onType.Tw stlyT et
  )
}

case class T etTranslat onStore(
  translateT etStore: ReadableStore[
    Mach neTranslateT etCl entColumn.Key,
    Mach neTranslat onResponse
  ],
   sT etTranslatableStore: ReadableStore[ sT etTranslatableCl entColumn.Key, Boolean],
  statsRece ver: StatsRece ver)
    extends ReadableStore[T etTranslat onStore.Key, T etTranslat onStore.Value]
    w h Logg ng {

  pr vate val stats = statsRece ver.scope("t etTranslat onStore")
  pr vate val  sTranslatableCounter = stats.counter("t et sTranslatable")
  pr vate val notTranslatableCounter = stats.counter("t et sNotTranslatable")
  pr vate val protectedUserCounter = stats.counter("protectedUser")
  pr vate val notProtectedUserCounter = stats.counter("notProtectedUser")
  pr vate val val dLanguageCounter = stats.counter("val dT etLanguage")
  pr vate val  nval dLanguageCounter = stats.counter(" nval dT etLanguage")
  pr vate val val dCrtCounter = stats.counter("val dCrt")
  pr vate val  nval dCrtCounter = stats.counter(" nval dCrt")
  pr vate val paramEnabledCounter = stats.counter("paramEnabled")
  pr vate val paramD sabledCounter = stats.counter("paramD sabled")

  pr vate val tw terContext = Tw terContext(Tw terContextPerm )

  overr de def get(k: T etTranslat onStore.Key): Future[Opt on[T etTranslat onStore.Value]] = {
    k.target. nferredUserDev ceLanguage.flatMap {
      case So (dev ceLanguage) =>
        setTw terContext(k.target, dev ceLanguage) {
          translateT et(
            target = k.target,
            t et d = k.t et d,
            t et = k.t et,
            crt = k.crt,
            dev ceLanguage = dev ceLanguage).map { responseOpt =>
            responseOpt.flatMap { response =>
              response.translatorLocal zedS ceLanguage
                .map { local zedS ceLanguage =>
                  T etTranslat onStore.Value(
                    translatedT etText = response.translat on,
                    local zedS ceLanguage = local zedS ceLanguage
                  )
                }.f lter { _ =>
                  response.translat onS ce == Translat onS ce.Google
                }
            }
          }
        }
      case None => Future.None
    }

  }

  // Don't sent protected t ets to external AP  for translat on
  pr vate def c ckProtectedUser(target: Target): Future[Boolean] = {
    target.targetUser.map(_.flatMap(_.safety).forall(_. sProtected)).onSuccess {
      case true => protectedUserCounter. ncr()
      case false => notProtectedUserCounter. ncr()
    }
  }

  pr vate def  sT etTranslatable(
    target: Target,
    t et d: Long,
    t et: Opt on[T et],
    crt: CommonRecom ndat onType,
    dev ceLanguage: Str ng
  ): Future[Boolean] = {
    val t etLangOpt = t et.flatMap(_.language)
    val  sVal dLanguage = t etLangOpt.ex sts { t etLang =>
      t etLang.conf dence > 0.5 &&
      t etLang.language != dev ceLanguage
    }

     f ( sVal dLanguage) {
      val dLanguageCounter. ncr()
    } else {
       nval dLanguageCounter. ncr()
    }

    val  sVal dCrt = T etTranslat onStore.allo dCRTs.conta ns(crt)
     f ( sVal dCrt) {
      val dCrtCounter. ncr()
    } else {
       nval dCrtCounter. ncr()
    }

     f ( sVal dCrt &&  sVal dLanguage && target.params(PushParams.Enable sT etTranslatableC ck)) {
      c ckProtectedUser(target).flatMap {
        case false =>
          val  sT etTranslatableKey =  sT etTranslatableCl entColumn.Key(
            t et d = t et d,
            dest nat onLanguage = So (dev ceLanguage),
            translat onS ce = So (Translat onS ce.Google.na ),
            excludePreferredLanguages = So (true)
          )
           sT etTranslatableStore
            .get( sT etTranslatableKey).map { resultOpt =>
              resultOpt.getOrElse(false)
            }.onSuccess {
              case true =>  sTranslatableCounter. ncr()
              case false => notTranslatableCounter. ncr()
            }
        case true =>
          Future.False
      }
    } else {
      Future.False
    }
  }

  pr vate def translateT et(
    t et d: Long,
    dev ceLanguage: Str ng
  ): Future[Opt on[Mach neTranslat on]] = {
    val translateKey = Mach neTranslateT etCl entColumn.Key(
      t et d = t et d,
      dest nat onLanguage = dev ceLanguage,
      translat onS ce = Translat onS ce.Google,
      translatableEnt yTypes = Seq(),
      onlyCac d = false,
      cac UsageType = Cac UsageType.Default
    )
    translateT etStore.get(translateKey).map {
      _.collect {
        case Mach neTranslat onResponse.Result(result) => result
      }
    }
  }

  pr vate def translateT et(
    target: Target,
    t et d: Long,
    t et: Opt on[T et],
    crt: CommonRecom ndat onType,
    dev ceLanguage: Str ng
  ): Future[Opt on[Mach neTranslat on]] = {
     sT etTranslatable(target, t et d, t et, crt, dev ceLanguage).flatMap {
      case true =>
        val  sEnabledByParam = target.params(PushFeatureSw chParams.EnableT etTranslat on)
         f ( sEnabledByParam) {
          paramEnabledCounter. ncr()
          translateT et(t et d, dev ceLanguage)
        } else {
          paramD sabledCounter. ncr()
          Future.None
        }
      case false =>
        Future.None
    }
  }

  pr vate def setTw terContext[Rep](
    target: Target,
    dev ceLanguage: Str ng
  )(
    f: => Future[Rep]
  ): Future[Rep] = {
    tw terContext() match {
      case So (v e r)  f v e r.user d.nonEmpty && v e r.aut nt catedUser d.nonEmpty =>
        //  f t  context  s already setup w h a user  D just use  
        f
      case _ =>
        //  f not, create a new context conta n ng t  v e r user  d
        tw terContext.let(
          V e r(
            user d = So (target.target d),
            requestLanguageCode = So (dev ceLanguage),
            aut nt catedUser d = So (target.target d)
          )) {
          f
        }
    }
  }
}
