package com.tw ter.t etyp e
package handler

 mport com.tw ter.context.thr ftscala.FeatureContext
 mport com.tw ter.t etyp e.backends.L m erServ ce
 mport com.tw ter.t etyp e.core._
 mport com.tw ter.t etyp e.serverut l.Except onCounter
 mport com.tw ter.t etyp e.store. nsertT et
 mport com.tw ter.t etyp e.thr ftscala._
 mport com.tw ter.t etyp e.ut l.T etCreat onLock.{Key => T etCreat onLockKey}

object PostT et {
  type Type[R] = FutureArrow[R, PostT etResult]

  /**
   * A type-class to abstract over t et creat on requests.
   */
  tra  RequestV ew[R] {
    def  sDark(req: R): Boolean
    def s ceT et d(req: R): Opt on[T et d]
    def opt ons(req: R): Opt on[Wr ePathHydrat onOpt ons]
    def user d(req: R): User d
    def un queness d(req: R): Opt on[Long]
    def returnSuccessOnDupl cate(req: R): Boolean
    def returnDupl cateT et(req: R): Boolean =
      returnSuccessOnDupl cate(req) || un queness d(req).nonEmpty
    def lockKey(req: R): T etCreat onLockKey
    def geo(req: R): Opt on[T etCreateGeo]
    def featureContext(req: R): Opt on[FeatureContext]
    def add  onalContext(req: R): Opt on[collect on.Map[T etCreateContextKey, Str ng]]
    def trans entContext(req: R): Opt on[Trans entCreateContext]
    def add  onalF elds(req: R): Opt on[T et]
    def dupl cateState: T etCreateState
    def scope: Str ng
    def  sNullcast(req: R): Boolean
    def creat vesConta ner d(req: R): Opt on[Creat vesConta ner d]
    def noteT et nt onedUser ds(req: R): Opt on[Seq[Long]]
  }

  /**
   * An  mple ntat on of `RequestV ew` for `PostT etRequest`.
   */
   mpl c  object PostT etRequestV ew extends RequestV ew[PostT etRequest] {
    def  sDark(req: PostT etRequest): Boolean = req.dark
    def s ceT et d(req: PostT etRequest): None.type = None
    def opt ons(req: PostT etRequest): Opt on[Wr ePathHydrat onOpt ons] = req.hydrat onOpt ons
    def user d(req: PostT etRequest): User d = req.user d
    def un queness d(req: PostT etRequest): Opt on[Long] = req.un queness d
    def returnSuccessOnDupl cate(req: PostT etRequest) = false
    def lockKey(req: PostT etRequest): T etCreat onLockKey = T etCreat onLockKey.byRequest(req)
    def geo(req: PostT etRequest): Opt on[T etCreateGeo] = req.geo
    def featureContext(req: PostT etRequest): Opt on[FeatureContext] = req.featureContext
    def add  onalContext(
      req: PostT etRequest
    ): Opt on[collect on.Map[T etCreateContextKey, Str ng]] = req.add  onalContext
    def trans entContext(req: PostT etRequest): Opt on[Trans entCreateContext] =
      req.trans entContext
    def add  onalF elds(req: PostT etRequest): Opt on[T et] = req.add  onalF elds
    def dupl cateState: T etCreateState.Dupl cate.type = T etCreateState.Dupl cate
    def scope = "t et"
    def  sNullcast(req: PostT etRequest): Boolean = req.nullcast
    def creat vesConta ner d(req: PostT etRequest): Opt on[Creat vesConta ner d] =
      req.underly ngCreat vesConta ner d
    def noteT et nt onedUser ds(req: PostT etRequest): Opt on[Seq[Long]] =
      req.noteT etOpt ons match {
        case So (noteT etOpt ons) => noteT etOpt ons. nt onedUser ds
        case _ => None
      }
  }

  /**
   * An  mple ntat on of `RequestV ew` for `Ret etRequest`.
   */
   mpl c  object Ret etRequestV ew extends RequestV ew[Ret etRequest] {
    def  sDark(req: Ret etRequest): Boolean = req.dark
    def s ceT et d(req: Ret etRequest): None.type = None
    def opt ons(req: Ret etRequest): Opt on[Wr ePathHydrat onOpt ons] = req.hydrat onOpt ons
    def user d(req: Ret etRequest): User d = req.user d
    def un queness d(req: Ret etRequest): Opt on[Long] = req.un queness d
    def returnSuccessOnDupl cate(req: Ret etRequest): Boolean = req.returnSuccessOnDupl cate
    def lockKey(req: Ret etRequest): T etCreat onLockKey =
      req.un queness d match {
        case So ( d) => T etCreat onLockKey.byUn queness d(req.user d,  d)
        case None => T etCreat onLockKey.byS ceT et d(req.user d, req.s ceStatus d)
      }
    def geo(req: Ret etRequest): None.type = None
    def featureContext(req: Ret etRequest): Opt on[FeatureContext] = req.featureContext
    def add  onalContext(req: Ret etRequest): None.type = None
    def trans entContext(req: Ret etRequest): None.type = None
    def add  onalF elds(req: Ret etRequest): Opt on[T et] = req.add  onalF elds
    def dupl cateState: T etCreateState.AlreadyRet eted.type = T etCreateState.AlreadyRet eted
    def scope = "ret et"
    def  sNullcast(req: Ret etRequest): Boolean = req.nullcast
    def creat vesConta ner d(req: Ret etRequest): Opt on[Creat vesConta ner d] = None
    def noteT et nt onedUser ds(req: Ret etRequest): Opt on[Seq[Long]] = None
  }

  /**
   * A `F lter`  s used to decorate a `FutureArrow` that has a known return type
   * and an  nput type for wh ch t re  s a `RequestV ew` type-class  nstance.
   */
  tra  F lter[Res] { self =>
    type T[Req] = FutureArrow[Req, Res]

    /**
     * Wraps a base arrow w h add  onal behav or.
     */
    def apply[Req: RequestV ew](base: T[Req]): T[Req]

    /**
     * Composes two f lter.  T  result ng f lter  self composes FutureArrows.
     */
    def andT n(next: F lter[Res]): F lter[Res] =
      new F lter[Res] {
        def apply[Req: RequestV ew](base: T[Req]): T[Req] =
          next(self(base))
      }
  }

  /**
   * T  f lter attempts to prevent so  race-cond  on related dupl cate t et creat ons,
   * v a use of a `T etCreateLock`.  W n a dupl cate  s detected, t  f lter can synt s ze
   * a successful `PostT etResult`  f appl cable, or return t  appropr ate coded response.
   */
  object Dupl cateHandler {
    def apply(
      t etCreat onLock: T etCreat onLock,
      getT ets: GetT etsHandler.Type,
      stats: StatsRece ver
    ): F lter[PostT etResult] =
      new F lter[PostT etResult] {
        def apply[R: RequestV ew](base: T[R]): T[R] = {
          val v ew =  mpl c ly[RequestV ew[R]]
          val notFoundCount = stats.counter(v ew.scope, "not_found")
          val foundCounter = stats.counter(v ew.scope, "found")

          FutureArrow.rec[R, PostT etResult] { self => req =>
            val dupl cateKey = v ew.lockKey(req)

            // attempts to f nd t  dupl cate t et.
            //
            //  f `returnDupT et`  s true and   f nd t  t et, t n   return a
            // successful `PostT etResult` w h that t et.   f   don't f nd t 
            // t et,   throw an ` nternalServerError`.
            //
            //  f `returnDupT et`  s false and   f nd t  t et, t n   return
            // t  appropr ate dupl cate state.   f   don't f nd t  t et, t n
            //   unlock t  dupl cate key and try aga n.
            def dupl cate(t et d: T et d, returnDupT et: Boolean) =
              f ndDupl cate(t et d, req).flatMap {
                case So (postT etResult) =>
                  foundCounter. ncr()
                   f (returnDupT et) Future.value(postT etResult)
                  else Future.value(PostT etResult(state = v ew.dupl cateState))

                case None =>
                  notFoundCount. ncr()
                   f (returnDupT et) {
                    //  f   fa led to load t  t et, but   know that  
                    // should ex st, t n return an  nternalServerError, so that
                    // t  cl ent treats   as a fa led t et creat on req.
                    Future.except on(
                       nternalServerError("Fa led to load dupl cate ex st ng t et: " + t et d)
                    )
                  } else {
                    // Assu  t  lock  s stale  f   can't load t  t et.  's
                    // poss ble that t  lock  s not stale, but t  t et  s not
                    // yet ava lable, wh ch requ res that   not be present  n
                    // cac  and not yet ava lable from t  backend. T   ans
                    // that t  fa lure mode  s to allow t et ng  f   can't
                    // determ ne t  state, but   should be rare that   can't
                    // determ ne  .
                    t etCreat onLock.unlock(dupl cateKey).before(self(req))
                  }
              }

            t etCreat onLock(dupl cateKey, v ew. sDark(req), v ew. sNullcast(req)) {
              base(req)
            }.rescue {
              case T etCreat on nProgress =>
                Future.value(PostT etResult(state = T etCreateState.Dupl cate))

              //  f t etCreat onLock detected a dupl cate, look up t  dupl cate
              // and return t  appropr ate result
              case Dupl cateT etCreat on(t et d) =>
                dupl cate(t et d, v ew.returnDupl cateT et(req))

              //  's poss ble that t etCreat onLock d dn't f nd a dupl cate for a
              // ret et attempt, but `Ret etBu lder` d d.
              case T etCreateFa lure.AlreadyRet eted(t et d)  f v ew.returnDupl cateT et(req) =>
                dupl cate(t et d, true)
            }
          }
        }

        pr vate def f ndDupl cate[R: RequestV ew](
          t et d: T et d,
          req: R
        ): Future[Opt on[PostT etResult]] = {
          val v ew =  mpl c ly[RequestV ew[R]]
          val readRequest =
            GetT etsRequest(
              t et ds = Seq(t et d),
              // Assu  that t  defaults are OK for all of t  hydrat on
              // opt ons except t  ones that are expl c ly set  n t 
              // req.
              opt ons = So (
                GetT etOpt ons(
                  forUser d = So (v ew.user d(req)),
                   ncludePerspect vals = true,
                   ncludeCards = v ew.opt ons(req).ex sts(_. ncludeCards),
                  cardsPlatformKey = v ew.opt ons(req).flatMap(_.cardsPlatformKey)
                )
              )
            )

          getT ets(readRequest).map {
            case Seq(result) =>
               f (result.t etState == StatusState.Found) {
                //  f t  t et was successfully found, t n convert t 
                // read result  nto a successful wr e result.
                So (
                  PostT etResult(
                    T etCreateState.Ok,
                    result.t et,
                    //  f t  ret et  s really old, t  ret et perspect ve m ght no longer
                    // be ava lable, but   want to ma nta n t   nvar ant that t  `postRet et`
                    // endpo nt always returns a s ce t et w h t  correct perspect ve.
                    result.s ceT et.map { srcT et =>
                      T etLenses.perspect ve
                        .update(_.map(_.copy(ret eted = true, ret et d = So (t et d))))
                        .apply(srcT et)
                    },
                    result.quotedT et
                  )
                )
              } else {
                None
              }
          }
        }
      }
  }

  /**
   * A `F lter` that appl es rate l m  ng to fa l ng requests.
   */
  object RateL m Fa lures {
    def apply(
      val dateL m : RateL m C cker.Val date,
       ncre ntSuccess: L m erServ ce. ncre ntByOne,
       ncre ntFa lure: L m erServ ce. ncre ntByOne
    ): F lter[T etBu lderResult] =
      new F lter[T etBu lderResult] {
        def apply[R: RequestV ew](base: T[R]): T[R] = {
          val v ew =  mpl c ly[RequestV ew[R]]

          FutureArrow[R, T etBu lderResult] { req =>
            val user d = v ew.user d(req)
            val dark = v ew. sDark(req)
            val contr butorUser d: Opt on[User d] = getContr butor(user d).map(_.user d)

            val dateL m ((user d, dark))
              .before {
                base(req).onFa lure { _ =>
                  //   don't  ncre nt t  fa lure rate l m   f t  fa lure
                  // was from t  fa lure rate l m  so that t  user can't
                  // get  n a loop w re t et creat on  s never attempted.  
                  // don't  ncre nt    f t  creat on  s dark because t re
                  //  s no way to perform a dark t et creat on through t 
                  // AP , so  's most l key so  k nd of test traff c l ke
                  // tap-compare.
                   f (!dark)  ncre ntFa lure(user d, contr butorUser d)
                }
              }
              .onSuccess { resp =>
                //  f   return a s lent fa lure, t n   want to
                //  ncre nt t  rate l m  as  f t  t et was fully
                // created, because   want   to appear that way to t 
                // user whose creat on s lently fa led.
                 f (resp. sS lentFa l)  ncre ntSuccess(user d, contr butorUser d)
              }
          }
        }
      }
  }

  /**
   * A `F lter` for count ng non-`T etCreateFa lure` fa lures.
   */
  object CountFa lures {
    def apply[Res](stats: StatsRece ver, scopeSuff x: Str ng = "_bu lder"): F lter[Res] =
      new F lter[Res] {
        def apply[R: RequestV ew](base: T[R]): T[R] = {
          val v ew =  mpl c ly[RequestV ew[R]]
          val except onCounter = Except onCounter(stats.scope(v ew.scope + scopeSuff x))
          base.onFa lure {
            case (_, _: T etCreateFa lure) =>
            case (_, ex) => except onCounter(ex)
          }
        }
      }
  }

  /**
   * A `F lter` for logg ng fa lures.
   */
  object LogFa lures extends F lter[PostT etResult] {
    pr vate[t ] val fa ledT etCreat onsLogger = Logger(
      "com.tw ter.t etyp e.Fa ledT etCreat ons"
    )

    def apply[R: RequestV ew](base: T[R]): T[R] =
      FutureArrow[R, PostT etResult] { req =>
        base(req).onFa lure {
          case fa lure => fa ledT etCreat onsLogger. nfo(s"request: $req\nfa lure: $fa lure")
        }
      }
  }

  /**
   * A `F lter` for convert ng a thrown `T etCreateFa lure`  nto a `PostT etResult`.
   */
  object RescueT etCreateFa lure extends F lter[PostT etResult] {
    def apply[R: RequestV ew](base: T[R]): T[R] =
      FutureArrow[R, PostT etResult] { req =>
        base(req).rescue {
          case fa lure: T etCreateFa lure => Future.value(fa lure.toPostT etResult)
        }
      }
  }

  /**
   * Bu lds a base handler for `PostT etRequest` and `Ret etRequest`.  T  handler
   * calls an underly ng t et bu lder, creates a ` nsertT et.Event`, hydrates
   * that, passes   to `t etStore`, and t n converts   to a `PostT etResult`.
   */
  object Handler {
    def apply[R: RequestV ew](
      t etBu lder: FutureArrow[R, T etBu lderResult],
      hydrate nsertEvent: FutureArrow[ nsertT et.Event,  nsertT et.Event],
      t etStore:  nsertT et.Store,
    ): Type[R] = {
      FutureArrow { req =>
        for {
          bldrRes <- t etBu lder(req)
          event <- hydrate nsertEvent(to nsertT etEvent(req, bldrRes))
          _ <- Future.w n(!event.dark)(t etStore. nsertT et(event))
        } y eld toPostT etResult(event)
      }
    }

    /**
     * Converts a request/`T etBu lderResult` pa r  nto an ` nsertT et.Event`.
     */
    def to nsertT etEvent[R: RequestV ew](
      req: R,
      bldrRes: T etBu lderResult
    ):  nsertT et.Event = {
      val v ew =  mpl c ly[RequestV ew[R]]
       nsertT et.Event(
        t et = bldrRes.t et,
        user = bldrRes.user,
        s ceT et = bldrRes.s ceT et,
        s ceUser = bldrRes.s ceUser,
        parentUser d = bldrRes.parentUser d,
        t  stamp = bldrRes.createdAt,
        dark = v ew. sDark(req) || bldrRes. sS lentFa l,
        hydrateOpt ons = v ew.opt ons(req).getOrElse(Wr ePathHydrat onOpt ons()),
        featureContext = v ew.featureContext(req),
         n  alT etUpdateRequest = bldrRes. n  alT etUpdateRequest,
        geoSearchRequest d = for {
          geo <- v ew.geo(req)
          searchRequest D <- geo.geoSearchRequest d
        } y eld {
          GeoSearchRequest d(request D = searchRequest D. d)
        },
        add  onalContext = v ew.add  onalContext(req),
        trans entContext = v ew.trans entContext(req),
        noteT et nt onedUser ds = v ew.noteT et nt onedUser ds(req)
      )
    }

    /**
     * Converts an ` nsertT et.Event`  nto a successful `PostT etResult`.
     */
    def toPostT etResult(event:  nsertT et.Event): PostT etResult =
      PostT etResult(
        T etCreateState.Ok,
        So (event.t et),
        s ceT et = event.s ceT et,
        quotedT et = event.quotedT et
      )
  }
}
