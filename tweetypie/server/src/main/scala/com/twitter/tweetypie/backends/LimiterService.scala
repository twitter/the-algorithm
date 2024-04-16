package com.tw ter.t etyp e
package backends

 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport com.tw ter.l m er.thr ftscala.FeatureRequest
 mport com.tw ter.t etyp e.backends.L m erBackend.GetFeatureUsage
 mport com.tw ter.t etyp e.backends.L m erBackend. ncre ntFeature
 mport com.tw ter.t etyp e.backends.L m erServ ce.Feature

/**
 * Why does L m erServ ce ex st?
 *
 * T  underly ng L m er thr ft serv ce doesn't support batch ng. T  tra  and  mple ntat on
 * bas cally ex st to allow a batch l ke  nterface to t  L m er. T  keeps us from hav ng to
 * spread batch ng throughout   code base.
 *
 * Why  s L m erServ ce  n t  backends package?
 *
 *  n so  ways    s l ke a backend  f t  backend supports batch ng. T re  s a modest amount of
 * bus ness log c L m erServ ce, but that log c ex sts  re to allow eas er consumpt on throughout
 * t  t etyp e code base.   d d look at mov ng L m erServ ce to anot r package, but all l kely
 * cand dates (serv ce, serverut l) caused c rcular dependenc es.
 *
 * W n   need to add funct onal y, should   add   to L m erBackend or L m erServ ce?
 *
 * L m erBackend  s used as a s mple wrapper around t  L m er thr ft cl ent. T  L m erBackend
 * should be kept as dumb as poss ble.   w ll most l kely want to add t  funct onal y  n
 * L m erServ ce.
 */
object L m erServ ce {
  type M nRema n ng = (User d, Opt on[User d]) => Future[ nt]
  type HasRema n ng = (User d, Opt on[User d]) => Future[Boolean]
  type  ncre nt = (User d, Opt on[User d],  nt) => Future[Un ]
  type  ncre ntByOne = (User d, Opt on[User d]) => Future[Un ]

  sealed abstract class Feature(val na : Str ng, val hasPerApp: Boolean = false) {
    def forUser(user d: User d): FeatureRequest = FeatureRequest(na , user d = So (user d))
    def forApp(app d: App d): Opt on[FeatureRequest] =
       f (hasPerApp) {
        So (
          FeatureRequest(
            s"${na }_per_app",
            appl cat on d = So (app d),
             dent f er = So (app d.toStr ng)
          )
        )
      } else {
        None
      }
  }
  object Feature {
    case object Updates extends Feature("updates", hasPerApp = true)
    case object  d aTagCreate extends Feature(" d a_tag_create")
    case object T etCreateFa lure extends Feature("t et_creat on_fa lure")
  }

  def fromBackend(
     ncre ntFeature:  ncre ntFeature,
    getFeatureUsage: GetFeatureUsage,
    getApp d: => Opt on[
      App d
    ], // t  call-by-na   re to  nvoke per request to get t  current request's app  d
    stats: StatsRece ver = NullStatsRece ver
  ): L m erServ ce =
    new L m erServ ce {
      def  ncre nt(
        feature: Feature
      )(
        user d: User d,
        contr butorUser d: Opt on[User d],
        amount:  nt
      ): Future[Un ] = {
        Future.w n(amount > 0) {
          def  ncre nt(req: FeatureRequest): Future[Un ] =  ncre ntFeature((req, amount))

          val  ncre ntUser: Opt on[Future[Un ]] =
            So ( ncre nt(feature.forUser(user d)))

          val  ncre ntContr butor: Opt on[Future[Un ]] =
            for {
               d <- contr butorUser d
               f  d != user d
            } y eld  ncre nt(feature.forUser( d))

          val  ncre ntPerApp: Opt on[Future[Un ]] =
            for {
              app d <- getApp d
              req <- feature.forApp(app d)
            } y eld  ncre nt(req)

          Future.collect(Seq( ncre ntUser,  ncre ntContr butor,  ncre ntPerApp).flatten)
        }
      }

      def m nRema n ng(
        feature: Feature
      )(
        user d: User d,
        contr butorUser d: Opt on[User d]
      ): Future[ nt] = {
        def getRema n ng(req: FeatureRequest): Future[ nt] = getFeatureUsage(req).map(_.rema n ng)

        val getUserRema n ng: Opt on[Future[ nt]] =
          So (getRema n ng(feature.forUser(user d)))

        val getContr butorRema n ng: Opt on[Future[ nt]] =
          contr butorUser d.map( d => getRema n ng(feature.forUser( d)))

        val getPerAppRema n ng: Opt on[Future[ nt]] =
          for {
            app d <- getApp d
            req <- feature.forApp(app d)
          } y eld getRema n ng(req)

        Future
          .collect(Seq(getUserRema n ng, getContr butorRema n ng, getPerAppRema n ng).flatten)
          .map(_.m n)
      }
    }
}

tra  L m erServ ce {

  /**
   *  ncre nt t  feature count for both t  user and t  contr butor.  f e  r  ncre nt fa ls,
   * t  result ng future w ll be t  f rst except on encountered.
   *
   * @param feature T  feature that  s  ncre nted
   * @param user d T  current user t ed to t  current request
   * @param contr butorUser d T  contr butor,  f one ex sts, t ed to t  current request
   * @param amount T  amount that each feature should be  ncre nted.
   */
  def  ncre nt(
    feature: Feature
  )(
    user d: User d,
    contr butorUser d: Opt on[User d],
    amount:  nt
  ): Future[Un ]

  /**
   *  ncre nt t  feature count, by one, for both t  user and t  contr butor.  f e  r
   *  ncre nt fa ls, t  result ng future w ll be t  f rst except on encountered.
   *
   * @param feature T  feature that  s  ncre nted
   * @param user d T  current user t ed to t  current request
   * @param contr butorUser d T  contr butor,  f one ex sts, t ed to t  current request
   *
   * @see [[ ncre nt]]  f   want to  ncre nt a feature by a spec f ed amount
   */
  def  ncre ntByOne(
    feature: Feature
  )(
    user d: User d,
    contr butorUser d: Opt on[User d]
  ): Future[Un ] =
     ncre nt(feature)(user d, contr butorUser d, 1)

  /**
   * T  m n mum rema n ng l m  bet en t  user and contr butor.  f an except on occurs, t n t 
   * result ng Future w ll be t  f rst except on encountered.
   *
   * @param feature T  feature that  s quer ed
   * @param user d T  current user t ed to t  current request
   * @param contr butorUser d T  contr butor,  f one ex sts, t ed to t  current request
   *
   * @return a `Future[ nt]` w h t  m n mum l m  left bet en t  user and contr butor
   */
  def m nRema n ng(feature: Feature)(user d: User d, contr butorUser d: Opt on[User d]): Future[ nt]

  /**
   * Can t  user and contr butor  ncre nt t  g ven feature.  f t  result cannot be determ ned
   * because of an except on, t n   assu  t y can  ncre nt. T  w ll allow us to cont nue
   * serv c ng requests even  f t  l m er serv ce  sn't respond ng.
   *
   * @param feature T  feature that  s quer ed
   * @param user d T  current user t ed to t  current request
   * @param contr butorUser d T  contr butor,  f one ex sts, t ed to t  current request
   * @return a `Future[Boolean]` w h true  f both t  user and contr butor have rema n ng l m 
   * cap.
   *
   * @see [[m nRema n ng]]  f   would l ke to handle any except ons that occur on y  own
   */
  def hasRema n ng(
    feature: Feature
  )(
    user d: User d,
    contr butorUser d: Opt on[User d]
  ): Future[Boolean] =
    m nRema n ng(feature)(user d, contr butorUser d)
      .map(_ > 0)
      .handle { case _ => true }
}
