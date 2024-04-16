package com.tw ter.t etyp e.handler

 mport com.tw ter.featuresw c s.v2.FeatureSw chResults
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.User d
 mport com.tw ter.t etyp e._
 mport com.tw ter.t etyp e.core.T etCreateFa lure
 mport com.tw ter.t etyp e.repos ory.User dent yRepos ory
 mport com.tw ter.t etyp e.repos ory.UserKey
 mport com.tw ter.t etyp e.thr ftscala.Conversat onControl
 mport com.tw ter.t etyp e.thr ftscala.T et
 mport com.tw ter.t etyp e.thr ftscala.T etCreateConversat onControl
 mport com.tw ter.t etyp e.thr ftscala.T etCreateState.Conversat onControlNotAllo d
 mport com.tw ter.t etyp e.thr ftscala.T etCreateState. nval dConversat onControl
 mport com.tw ter.t etyp e.ut l.Conversat onControls
 mport com.tw ter.ut l.logg ng.Logg ng

/**
 * Process request para ters  nto a Conversat onControl value.
 */
object Conversat onControlBu lder extends Logg ng {
  type Type = Request => St ch[Opt on[Conversat onControl]]

  type ScreenNa  = Str ng

  /**
   * T  f elds necessary to create a [[Conversat onControl]].
   *
   * T   s a tra  rat r than a case class to avo d runn ng t 
   * code to extract t   nt ons  n t  cases w re handl ng t 
   * request doesn't need to use t m (t  common case w re
   * t etCreateConversat onControl  s None).
   */
  tra  Request {
    def t etCreateConversat onControl: Opt on[T etCreateConversat onControl]
    def t etAuthor d: User d
    def  nt onedUserScreenNa s: Set[Str ng]

    def noteT et nt onedUser ds: Opt on[Set[Long]]
  }

  object Request {

    /**
     * Extract t  data necessary to create a [[Conversat onControl]]
     * for a new [[T et]]. T   s  ntended for use w n creat ng
     * T ets.   must be called after t  T et has had  s ent  es
     * extracted.
     */
    def fromT et(
      t et: T et,
      t etCreateConversat onControl: Opt on[T etCreateConversat onControl],
      noteT et nt onedUser dsL st: Opt on[Seq[Long]]
    ): Request = {
      val cctl = t etCreateConversat onControl
      new Request {
        def t etCreateConversat onControl: Opt on[T etCreateConversat onControl] = cctl
        def  nt onedUserScreenNa s: Set[ScreenNa ] =
          t et. nt ons
          // Enforce that t  T et's  nt ons have already been
          // extracted from t  text. ( nt ons w ll be None  f t y
          // have not yet been extracted.)
            .getOrElse(
              throw new Runt  Except on(
                " nt ons must be extracted before apply ng Conversat onControls"))
            .map(_.screenNa )
            .toSet

        def t etAuthor d: User d = t et.coreData.get.user d
        def noteT et nt onedUser ds: Opt on[Set[Long]] =
          noteT et nt onedUser dsL st.map(_.toSet)
      }
    }
  }

  /**
   * Create a Conversat onControlBu lder that looks up user  ds for
   * screen na s us ng t  spec f ed User dent yRepos ory.
   */
  def fromUser dent yRepo(
    statsRece ver: StatsRece ver,
    user dent yRepo: User dent yRepos ory.Type
  ): Request => St ch[Opt on[Conversat onControl]] =
    Conversat onControlBu lder(
      getUser d = screenNa  => user dent yRepo(UserKey.byScreenNa (screenNa )).map(_. d),
      statsRece ver = statsRece ver
    )

  /**
   * Extract t   nv eV a nt on value wh ch does not ex st on t  T etCreateConversat onControl
   *  self but does ex st on t  structures   un ons.
   */
  def  nv eV a nt on(tccc: T etCreateConversat onControl): Boolean =
    tccc match {
      case T etCreateConversat onControl.By nv at on(c) => c. nv eV a nt on.conta ns(true)
      case T etCreateConversat onControl.Commun y(c) => c. nv eV a nt on.conta ns(true)
      case T etCreateConversat onControl.Follo rs(c) => c. nv eV a nt on.conta ns(true)
      case _ => false
    }

  /**
   * Translates t  T etCreateConversat onControl  nto
   * Conversat onControl us ng t  context from t  rest of t  t et
   * creat on. For t  most part, t   s just a d rect translat on,
   * plus f ll ng  n t  contextual user  ds ( nt oned users and t et
   * author).
   */
  def apply(
    statsRece ver: StatsRece ver,
    getUser d: ScreenNa  => St ch[User d]
  ): Request => St ch[Opt on[Conversat onControl]] = {
    val user dLookupsCounter = statsRece ver.counter("user_ d_lookups")
    val conversat onControlPresentCounter = statsRece ver.counter("conversat on_control_present")
    val conversat onControl nv eV a nt onPresentCounter =
      statsRece ver.counter("conversat on_control_ nv e_v a_ nt on_present")
    val fa lureCounter = statsRece ver.counter("fa lures")

    // Get t  user  ds for t se screen na s. Any users who do not
    // ex st w ll be s lently dropped.
    def getEx st ngUser ds(
      screenNa s: Set[ScreenNa ],
       nt onedUser ds: Opt on[Set[Long]]
    ): St ch[Set[User d]] = {
       nt onedUser ds match {
        case So (user ds) => St ch.value(user ds)
        case _ =>
          St ch
            .traverse(screenNa s.toSeq) { screenNa  =>
              getUser d(screenNa ).l ftNotFoundToOpt on
                .ensure(user dLookupsCounter. ncr())
            }
            .map(user dOpt ons => user dOpt ons.flatten.toSet)
      }
    }

    // T   s broken out just to make   syntact cally n cer to add
    // t  stats handl ng
    def process(request: Request): St ch[Opt on[Conversat onControl]] =
      request.t etCreateConversat onControl match {
        case None => St ch.None
        case So (cctl) =>
          cctl match {
            case T etCreateConversat onControl.By nv at on(by nv at onControl) =>
              for {
                 nv edUser ds <- getEx st ngUser ds(
                  request. nt onedUserScreenNa s,
                  request.noteT et nt onedUser ds)
              } y eld So (
                Conversat onControls.by nv at on(
                   nv edUser ds =  nv edUser ds.toSeq.f lterNot(_ == request.t etAuthor d),
                  conversat onT etAuthor d = request.t etAuthor d,
                  by nv at onControl. nv eV a nt on
                )
              )

            case T etCreateConversat onControl.Commun y(commun yControl) =>
              for {
                 nv edUser ds <- getEx st ngUser ds(
                  request. nt onedUserScreenNa s,
                  request.noteT et nt onedUser ds)
              } y eld So (
                Conversat onControls.commun y(
                   nv edUser ds =  nv edUser ds.toSeq.f lterNot(_ == request.t etAuthor d),
                  conversat onT etAuthor d = request.t etAuthor d,
                  commun yControl. nv eV a nt on
                )
              )
            case T etCreateConversat onControl.Follo rs(follo rsControl) =>
              for {
                 nv edUser ds <- getEx st ngUser ds(
                  request. nt onedUserScreenNa s,
                  request.noteT et nt onedUser ds)
              } y eld So (
                Conversat onControls.follo rs(
                   nv edUser ds =  nv edUser ds.toSeq.f lterNot(_ == request.t etAuthor d),
                  conversat onT etAuthor d = request.t etAuthor d,
                  follo rsControl. nv eV a nt on
                )
              )
            // T  should only ever happen  f a new value  s added to t 
            // un on and   don't update t  code.
            case T etCreateConversat onControl.UnknownUn onF eld(fld) =>
              throw new Runt  Except on(s"Unexpected T etCreateConversat onControl: $fld")
          }
      }

    (request: Request) => {
      // Wrap  n St ch to encapsulate any except ons that happen
      // before mak ng a St ch call  ns de of process.
      St ch(process(request)).flatten.respond { response =>
        //  f   count t  before do ng t  work, and t  stats are
        // collected before t  RPC completes, t n any fa lures
        // w ll get counted  n a d fferent m nute than t  request
        // that caused  .
        request.t etCreateConversat onControl.foreach { cc =>
          conversat onControlPresentCounter. ncr()
           f ( nv eV a nt on(cc)) conversat onControl nv eV a nt onPresentCounter. ncr()
        }

        response.onFa lure { e =>
          error( ssage = "Fa led to create conversat on control", cause = e)
          // Don't bot r count ng  nd v dual except ons, because
          // t  cost of keep ng those stats  s probably not worth
          // t  conven ence of not hav ng to look  n t  logs.
          fa lureCounter. ncr()
        }
      }
    }
  }

  /**
   * Val dates  f a conversat on control request  s allo d by feature sw c s
   * and  s only requested on a root t et.
   */
  object Val date {
    case class Request(
      matc dResults: Opt on[FeatureSw chResults],
      conversat onControl: Opt on[T etCreateConversat onControl],
       nReplyToT et d: Opt on[T et d])

    type Type = FutureEffect[Request]

    val Ex nval dConversat onControl = T etCreateFa lure.State( nval dConversat onControl)
    val ExConversat onControlNotAllo d = T etCreateFa lure.State(Conversat onControlNotAllo d)
    val Conversat onControlStatusUpdateEnabledKey = "conversat on_control_status_update_enabled"
    val Conversat onControlFollo rsEnabledKey = "conversat on_control_ _follo rs_enabled"

    def apply(
      useFeatureSw chResults: Gate[Un ],
      statsRece ver: StatsRece ver
    ): Type = request => {
      def fsDen ed(fsKey: Str ng): Boolean = {
        val featureEnabledOpt: Opt on[Boolean] =
          // Do not log  mpress ons, wh ch would  nterfere w h shared cl ent exper  nt data.
          request.matc dResults.flatMap(_.getBoolean(fsKey, shouldLog mpress on = false))
        val fsEnabled = featureEnabledOpt.conta ns(true)
         f (!fsEnabled) {
          statsRece ver.counter(s"c ck_conversat on_control/unauthor zed/fs/$fsKey"). ncr()
        }
        !fsEnabled
      }

      val  sCcRequest: Boolean = request.conversat onControl. sDef ned

      val  sCc nval dParams =  sCcRequest && {
        val  sRootT et = request. nReplyToT et d. sEmpty
         f (! sRootT et) {
          statsRece ver.counter("c ck_conversat on_control/ nval d"). ncr()
        }
        ! sRootT et
      }

      val  sCcDen edByFs =  sCcRequest && {
        val  sFollo r = request.conversat onControl.ex sts {
          case _: T etCreateConversat onControl.Follo rs => true
          case _ => false
        }

        fsDen ed(Conversat onControlStatusUpdateEnabledKey) ||
        ( sFollo r && fsDen ed(Conversat onControlFollo rsEnabledKey))
      }

       f ( sCcDen edByFs && useFeatureSw chResults()) {
        Future.except on(ExConversat onControlNotAllo d)
      } else  f ( sCc nval dParams) {
        Future.except on(Ex nval dConversat onControl)
      } else {
        Future.Un 
      }
    }
  }
}
