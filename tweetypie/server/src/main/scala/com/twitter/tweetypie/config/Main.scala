package com.tw ter.t etyp e
package conf g

 mport com.tw ter.app.Flag
 mport com.tw ter.app.Flaggable
 mport com.tw ter.app.Flags
 mport com.tw ter.f nagle.http.HttpMuxer
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.mtls.author zat on.server.MtlsServerSess onTrackerF lter
 mport com.tw ter.f nagle.mtls.server.MtlsStackServer._
 mport com.tw ter.f nagle.param.Reporter
 mport com.tw ter.f nagle.ssl.Opportun st cTls
 mport com.tw ter.f nagle.ut l.NullReporterFactory
 mport com.tw ter.f nagle.Thr ft
 mport com.tw ter.f nagle.Thr ftMux
 mport com.tw ter.flockdb.cl ent.thr ftscala.Pr or y
 mport com.tw ter. nject. njector
 mport com.tw ter. nject.annotat ons.{Flags =>  njectFlags}
 mport com.tw ter.scrooge.Thr ftEnum
 mport com.tw ter.scrooge.Thr ftEnumObject
 mport com.tw ter.server.handler. ndexHandler
 mport com.tw ter.strato.catalog.Catalog
 mport com.tw ter.strato.fed.StratoFed
 mport com.tw ter.strato.fed.server.StratoFedServer
 mport com.tw ter.strato.ut l.Ref
 mport com.tw ter.strato.warmup.War r
 mport com.tw ter.t etyp e.federated.StratoCatalogBu lder
 mport com.tw ter.t etyp e.federated.warmups.StratoCatalogWarmups
 mport com.tw ter.t etyp e.serverut l.Act v yServ ce
 mport java.net. netSocketAddress
 mport scala.reflect.ClassTag

object Env extends Enu rat on {
  val dev: Env.Value = Value
  val stag ng: Env.Value = Value
  val prod: Env.Value = Value
}

class T etServ ceFlags(flag: Flags,  njector: =>  njector) {
   mpl c  object EnvFlaggable extends Flaggable[Env.Value] {
    def parse(s: Str ng): Env.Value =
      s match {
        // Handle Aurora env na s that are d fferent from t etyp e's na s
        case "devel" => Env.dev
        case "test" => Env.stag ng
        // Handle T etyp e env na s
        case ot r => Env.w hNa (ot r)
      }
  }

  val zone: Flag[Str ng] =
    flag("zone", "localhost", "One of: atla, pdxa, localhost, etc.")

  val env: Flag[Env.Value] =
    flag("env", Env.dev, "One of: testbox, dev, stag ng, prod")

  val t mcac Dest: Flag[Str ng] =
    flag(
      "t mcac Dest",
      "/s/cac /t etyp e:t mcac s",
      "T  Na  for t  t etyp e cac  cluster."
    )

  val dec derOverr des: Flag[Map[Str ng, Boolean]] =
    flag(
      "dec derOverr des",
      Map.empty[Str ng, Boolean],
      "Set dec ders to constant values, overr d ng dec der conf gurat on f les."
    )(
      // Unfortunately, t   mpl c  Flaggable[Boolean] has a default
      // value and Flaggable.ofMap[K, V] requ res that t   mpl c 
      // Flaggable[V] not have a default. Even less fortunately,  
      // doesn't say why.  're stuck w h t .
      Flaggable.ofMap( mpl c ly, Flaggable.mandatory(_.toBoolean))
    )

  // "/dec der.yml" co s from t  res ces  ncluded at
  // "t etyp e/server/conf g", so   should not normally need to
  // overr de t  value. T  flag  s def ned as a step toward mak ng
  //   command-l ne usage more s m lar to t  standard
  // tw ter-server- nternal flags.
  def dec derBase(): Str ng =
     njector. nstance[Str ng]( njectFlags.na d("dec der.base"))

  // Om t ng a value for dec der overlay flag causes t  server to use
  // only t  stat c dec der.
  def dec derOverlay(): Str ng =
     njector. nstance[Str ng]( njectFlags.na d("dec der.overlay"))

  // Om t ng a value for t  VF dec der overlay flag causes t  server
  // to use only t  stat c dec der.
  val vfDec derOverlay: Flag[Str ng] =
    flag(
      "vf.dec der.overlay",
      "T  locat on of t  overlay dec der conf gurat on for V s b l y F lter ng")

  /**
   * Warmup Requests happen as part of t   n  al zat on process, before any real requests are
   * processed. T  prevents real requests from ever be ng served from a competely cold state
   */
  val enableWarmupRequests: Flag[Boolean] =
    flag(
      "enableWarmupRequests",
      true,
      """| warms up T etyp e serv ce by generat ng random requests
         | to T etyp e that are processed pr or to t  actual cl ent requests """.str pMarg n
    )

  val grayL stRateL m : Flag[Double] =
    flag("grayl stRateL m ", 5.0, "rate-l m  for non-allowl sted cl ents")

  val serv cePort: Flag[ netSocketAddress] =
    flag("serv ce.port", "port for t et-serv ce thr ft  nterface")

  val cl ent d: Flag[Str ng] =
    flag("cl ent d", "t etyp e.stag ng", "cl ent d to send  n requests")

  val allowl st: Flag[Boolean] =
    flag("allowl st", true, "enforce cl ent allowl st")

  val cl entHostStats: Flag[Boolean] =
    flag("cl entHostStats", false, "enable per cl ent host stats")

  val w hCac : Flag[Boolean] =
    flag("w hCac ", true, " f set to false, T etyp e w ll launch w hout  mcac ")

  /**
   * Make any [[Thr ftEnum]] value parseable as a [[Flag]] value. T 
   * w ll parse case- nsens  ve values that match t  unqual f ed
   * na s of t  values of t  enu rat on,  n t  manner of
   * [[Thr ftEnum]]'s `valueOf`  thod.
   *
   * Cons der a [[Thr ftEnum]] generated from t  follow ng Thr ft  DL sn ppet:
   *
   * {{{
   * enum Pr or y {
   *   Low = 1
   *   Throttled = 2
   *   H gh = 3
   * }
   * }}}
   *
   * To enable def n ng flags that spec fy one of t se enum values:
   *
   * {{{
   *  mpl c  val flaggablePr or y: Flaggable[Pr or y] = flaggableThr ftEnum(Pr or y)
   * }}}
   *
   *  n t  example, t  enu rat on value `Pr or y.Low` can be
   * represented as t  str ng "Low", "low", or "LOW".
   */
  def flaggableThr ftEnum[T <: Thr ftEnum: ClassTag](enum: Thr ftEnumObject[T]): Flaggable[T] =
    Flaggable.mandatory[T] { str ngValue: Str ng =>
      enum
        .valueOf(str ngValue)
        .getOrElse {
          val val dValues = enum.l st.map(_.na ).mkStr ng(", ")
          throw new  llegalArgu ntExcept on(
            s" nval d value ${str ngValue}. Val d values  nclude: ${val dValues}"
          )
        }
    }

   mpl c  val flaggablePr or y: Flaggable[Pr or y] = flaggableThr ftEnum(Pr or y)

  val background ndex ngPr or y: Flag[Pr or y] =
    flag(
      "background ndex ngPr or y",
      Pr or y.Low,
      "spec f es t  queue to use for \"background\" tflock operat ons, such as remov ng edges " +
        "for deleted T ets. T  ex sts for test ng scenar os, w n    s useful to see t  " +
        "effects of background  ndex ng operat ons sooner.  n product on, t  should always be " +
        "set to \"low\" (t  default)."
    )

  val tflockPageS ze: Flag[ nt] =
    flag("tflockPageS ze", 1000, "Number of  ems to return  n each page w n query ng tflock")

  val enable nProcessCac : Flag[Boolean] =
    flag(
      "enable nProcessCac ",
      true,
      " f set to false, T etyp e w ll not use t   n-process cac "
    )

  val  nProcessCac S ze: Flag[ nt] =
    flag(" nProcessCac S ze", 1700, "max mum  ems  n  n-process cac ")

  val  nProcessCac TtlMs: Flag[ nt] =
    flag(" nProcessCac TtlMs", 10000, "m ll seconds that hot keys are stored  n  mory")

  val  mcac Pend ngRequestL m : Flag[ nt] =
    flag(
      " mcac Pend ngRequestL m ",
      100,
      "Number of requests that can be queued on a s ngle  mcac  connect on (4 per cac  server)"
    )

  val  nstance d: Flag[ nt] =
    flag(
      "conf gbus. nstance d",
      -1,
      " nstance d of t  t etyp e serv ce  nstance for staged conf gurat on d str but on"
    )

  val  nstanceCount: Flag[ nt] =
    flag(
      "conf gbus. nstanceCount",
      -1,
      "Total number of t etyp e serv ce  nstances for staged conf gurat on d str but on"
    )

  def serv ce dent f er(): Serv ce dent f er =
     njector. nstance[Serv ce dent f er]

  val enableRepl cat on: Flag[Boolean] =
    flag(
      "enableRepl cat on",
      true,
      "Enable repl cat on of reads (conf gurable v a t etyp e_repl cate_reads dec der) and wr es (100%) v a DRPC"
    )

  val s mulateDeferredrpcCallbacks: Flag[Boolean] =
    flag(
      "s mulateDeferredrpcCallbacks",
      false,
      """|For async wr e path, call back  nto current  nstance  nstead of v a DRPC.
         |T   s used for test and devel  nstances so   can ensure t  test traff c
         | s go ng to t  test  nstance.""".str pMarg n
    )

  val shortC rcu L kelyPart alT etReadsMs: Flag[ nt] =
    flag(
      "shortC rcu L kelyPart alT etReadsMs",
      1500,
      """|Spec f es a number of m ll seconds before wh ch   w ll short-c rcu  l kely
         |part al reads from MH and return a NotFound t et response state. After
         |exper  nt ng    nt w h 1500 ms.""".str pMarg n
    )

  val str ngCenterProjects: Flag[Seq[Str ng]] =
    flag(
      "str ngcenter.projects",
      Seq.empty[Str ng],
      "Str ng Center project na s, comma separated")(Flaggable.ofSeq(Flaggable.ofStr ng))

  val languagesConf g: Flag[Str ng] =
    flag(" nternat onal.languages", "Supported languages conf g f le")
}

class T etyp eMa n extends StratoFedServer {
  overr de def dest: Str ng = "/s/t etyp e/t etyp e:federated"

  val t etServ ceFlags: T etServ ceFlags = new T etServ ceFlags(flag,  njector)

  // d splay all t  reg stered HttpMuxer handlers
  HttpMuxer.addHandler("", new  ndexHandler)

  pr vate[t ] lazy val serverBu lder = {
    val sett ngs = new T etServ ceSett ngs(t etServ ceFlags)
    val serverBu lder = new T etServerBu lder(sett ngs)

    val mtlsSess onTrackerF lter =
      new MtlsServerSess onTrackerF lter[Array[Byte], Array[Byte]](statsRece ver)

    val mtlsTrackedServ ce = mtlsSess onTrackerF lter.andT n(Act v yServ ce(serverBu lder.bu ld))

    val thr ftMuxServer = Thr ftMux.server
    // by default, f nagle logs except ons to ch ckadee, wh ch  s deprecated and
    // bas cally unused.  to avo d wasted over ad,   expl c ly d sable t  reporter.
      .conf gured(Reporter(NullReporterFactory))
      .w hLabel("t etyp e")
      .w hMutualTls(t etServ ceFlags.serv ce dent f er())
      .w hOpportun st cTls(Opportun st cTls.Requ red)
      .conf gured(Thr ft.param.Serv ceClass(So (classOf[Thr ftT etServ ce])))
      .serve(t etServ ceFlags.serv cePort(), mtlsTrackedServ ce)

    closeOnEx (thr ftMuxServer)
    awa (thr ftMuxServer)

    serverBu lder
  }

  overr de def conf gureRefCatalog(
    catalog: Ref[Catalog[StratoFed.Column]]
  ): Ref[Catalog[StratoFed.Column]] =
    catalog
      .jo n {
        Ref(
          serverBu lder.stratoT etServ ce.flatMap { t etServ ce =>
            StratoCatalogBu lder.catalog(
              t etServ ce,
              serverBu lder.backendCl ents.stratoserverCl ent,
              serverBu lder.backendCl ents.g zmoduck.getBy d,
              serverBu lder.backendCl ents.callbackPromotedContentLogger,
              statsRece ver,
              serverBu lder.dec derGates.enableCommun yT etCreates,
            )
          }
        )
      }
      .map { case (l, r) => l ++ r }

  overr de def conf gureWar r(war r: War r): Un  = {
    new T etServ ceSett ngs(t etServ ceFlags).warmupRequestsSett ngs.foreach { warmupSett ngs =>
      war r.add(
        "t etyp e strato catalog",
        () => StratoCatalogWarmups.warmup(warmupSett ngs, composedOps)
      )
    }
  }
}

object Ma n extends T etyp eMa n
