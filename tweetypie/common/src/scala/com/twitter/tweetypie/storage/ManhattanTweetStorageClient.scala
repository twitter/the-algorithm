package com.tw ter.t etyp e.storage

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.mtls.aut nt cat on.EmptyServ ce dent f er
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.ssl.Opportun st cTls
 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.logg ng.BareFormatter
 mport com.tw ter.logg ng.Level
 mport com.tw ter.logg ng.Scr beHandler
 mport com.tw ter.logg ng._
 mport com.tw ter.st ch.St ch
 mport com.tw ter.storage.cl ent.manhattan.b ject ons.B ject ons._
 mport com.tw ter.storage.cl ent.manhattan.kv._
 mport com.tw ter.storage.cl ent.manhattan.kv. mpl.ValueDescr ptor
 mport com.tw ter.t etyp e.cl ent_ d.Cl ent d lper
 mport com.tw ter.t etyp e.storage.Scr be.Scr beHandlerFactory
 mport com.tw ter.t etyp e.storage.T etStorageCl ent.BounceDelete
 mport com.tw ter.t etyp e.storage.T etStorageCl ent.GetT et
 mport com.tw ter.t etyp e.storage.T etStorageCl ent.HardDeleteT et
 mport com.tw ter.t etyp e.thr ftscala.T et
 mport com.tw ter.t etyp e.ut l.St chUt ls
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Return
 mport com.tw ter.ut l.Throw
 mport scala.ut l.Random

object ManhattanT etStorageCl ent {
  object Conf g {

    /**
     * T  Manhattan dataset w re t ets are stored  s not externally
     * conf gurable because wr  ng t ets to a non-product on dataset
     * requ res great care. Stag ng  nstances us ng a d fferent dataset w ll
     * wr e t ets to a non-product on store, but w ll publ sh events, log to
     * HDFS, and cac  data referenc ng t ets  n that store wh ch are not
     * access ble by t  rest of t  product on cluster.
     *
     *  n a completely  solated env ron nt   should be safe to wr e to
     * ot r datasets for test ng purposes.
     */
    val Dataset = "tb rd_mh"

    /**
     * Once a t et has been deleted   can only be undeleted w h n t  t  
     * w ndow, after wh ch [[UndeleteHandler]] w ll return an error on
     * undelete attempts.
     */
    val UndeleteW ndowH s = 240

    /**
     * Default label used for underly ng Manhattan Thr ft cl ent  tr cs
     *
     * T  f nagle cl ent  tr cs w ll be exported at clnt/:label.
     */
    val Thr ftCl entLabel = "mh_cylon"

    /**
     * Return t  correspond ng W ly path for t  Cylon cluster  n t  "ot r" DC
     */
    def remoteDest nat on(zone: Str ng): Str ng =
      s"/srv#/prod/${remoteZone(zone)}/manhattan/cylon.nat ve-thr ft"

    pr vate def remoteZone(zone: Str ng) = zone match {
      case "pdxa" => "atla"
      case "atla" | "localhost" => "pdxa"
      case _ =>
        throw new  llegalArgu ntExcept on(s"Cannot conf gure remote DC for unknown zone '$zone'")
    }
  }

  /**
   * @param appl cat on d Manhattan appl cat on  d used for quota account ng
   * @param localDest nat on W ly path to local Manhattan cluster
   * @param localT  out Overall t  out ( nclud ng retr es) for all reads/wr es to local cluster
   * @param remoteDest nat on W ly path to remote Manhattan cluster, used for undelete and force add
   * @param remoteT  out Overall t  out ( nclud ng retr es) for all reads/wr es to remote cluster
   * @param undeleteW ndowH s Amount of t   dur ng wh ch a deleted t et can be undeleted
   * @param thr ftCl entLabel Label used to scope stats for Manhattan Thr ft cl ent
   * @param maxRequestsPerBatch Conf gure t  St ch RequestGroup.Generator batch s ze
   * @param serv ce dent f er T  Serv ce dent f er to use w n mak ng connect ons to a Manhattan cluster
   * @param opportun st cTlsLevel T  level to use for opportun st c TLS for connect ons to t  Manhattan cluster
   */
  case class Conf g(
    appl cat on d: Str ng,
    localDest nat on: Str ng,
    localT  out: Durat on,
    remoteDest nat on: Str ng,
    remoteT  out: Durat on,
    undeleteW ndowH s:  nt = Conf g.UndeleteW ndowH s,
    thr ftCl entLabel: Str ng = Conf g.Thr ftCl entLabel,
    maxRequestsPerBatch:  nt =  nt.MaxValue,
    serv ce dent f er: Serv ce dent f er,
    opportun st cTlsLevel: Opportun st cTls.Level)

  /**
   * San  zes t   nput for AP s wh ch take  n a (T et, Seq[F eld]) as  nput.
   *
   * NOTE: T  funct on only appl es san y c cks wh ch are common to
   * all AP s wh ch take  n a (T et, Seq[F eld]) as  nput. AP  spec f c
   * c cks are not covered  re.
   *
   * @param ap St ch t  back ng AP  call
   * @tparam T t  output type of t  back ng AP  call
   * @return a st ch funct on wh ch does so  bas c  nput san y c ck ng
   */
  pr vate[storage] def san  zeT etF elds[T](
    ap St ch: (T et, Seq[F eld]) => St ch[T]
  ): (T et, Seq[F eld]) => St ch[T] =
    (t et, f elds) => {
      requ re(f elds.forall(_. d > 0), s"F eld  ds ${f elds} are not pos  ve numbers")
      ap St ch(t et, f elds)
    }

  // Returns a handler that asynchronously logs  ssages to Scr be us ng t  BareFormatter wh ch
  // logs just t   ssage w hout any add  onal  tadata
  def scr beHandler(categoryNa : Str ng): HandlerFactory =
    Scr beHandler(
      formatter = BareFormatter,
      max ssagesPerTransact on = 100,
      category = categoryNa ,
      level = So (Level.TRACE)
    )

  /**
   * A Conf g appropr ate for  nteract ve sess ons and scr pts.
   */
  def develConf g(): Conf g =
    Conf g(
      appl cat on d = Opt on(System.getenv("USER")).getOrElse("<unknown>") + ".devel",
      localDest nat on = "/s/manhattan/cylon.nat ve-thr ft",
      localT  out = 10.seconds,
      remoteDest nat on = "/s/manhattan/cylon.nat ve-thr ft",
      remoteT  out = 10.seconds,
      undeleteW ndowH s = Conf g.UndeleteW ndowH s,
      thr ftCl entLabel = Conf g.Thr ftCl entLabel,
      maxRequestsPerBatch =  nt.MaxValue,
      serv ce dent f er = Serv ce dent f er(System.getenv("USER"), "t etyp e", "devel", "local"),
      opportun st cTlsLevel = Opportun st cTls.Requ red
    )

  /**
   * Bu ld a Manhattan t et storage cl ent for use  n  nteract ve
   * sess ons and scr pts.
   */
  def devel(): T etStorageCl ent =
    new ManhattanT etStorageCl ent(
      develConf g(),
      NullStatsRece ver,
      Cl ent d lper.default,
    )
}

class ManhattanT etStorageCl ent(
  conf g: ManhattanT etStorageCl ent.Conf g,
  statsRece ver: StatsRece ver,
  pr vate val cl ent d lper: Cl ent d lper)
    extends T etStorageCl ent {
   mport ManhattanT etStorageCl ent._

  lazy val scr beHandlerFactory: Scr beHandlerFactory = scr beHandler _
  val scr be: Scr be = new Scr be(scr beHandlerFactory, statsRece ver)

  def mkCl ent(
    dest: Str ng,
    label: Str ng
  ): ManhattanKVCl ent = {
    val mhMtlsParams =
       f (conf g.serv ce dent f er == EmptyServ ce dent f er) NoMtlsParams
      else
        ManhattanKVCl entMtlsParams(
          serv ce dent f er = conf g.serv ce dent f er,
          opportun st cTls = conf g.opportun st cTlsLevel
        )

    new ManhattanKVCl ent(
      conf g.appl cat on d,
      dest,
      mhMtlsParams,
      label,
      Seq(Exper  nts.ApertureLoadBalancer))
  }

  val localCl ent: ManhattanKVCl ent = mkCl ent(conf g.localDest nat on, conf g.thr ftCl entLabel)

  val localMhEndpo nt: ManhattanKVEndpo nt = ManhattanKVEndpo ntBu lder(localCl ent)
    .defaultGuarantee(Guarantee.SoftDcRead Wr es)
    .defaultMaxT  out(conf g.localT  out)
    .maxRequestsPerBatch(conf g.maxRequestsPerBatch)
    .bu ld()

  val localManhattanOperat ons = new ManhattanOperat ons(Conf g.Dataset, localMhEndpo nt)

  val remoteCl ent: ManhattanKVCl ent =
    mkCl ent(conf g.remoteDest nat on, s"${conf g.thr ftCl entLabel}_remote")

  val remoteMhEndpo nt: ManhattanKVEndpo nt = ManhattanKVEndpo ntBu lder(remoteCl ent)
    .defaultGuarantee(Guarantee.SoftDcRead Wr es)
    .defaultMaxT  out(conf g.remoteT  out)
    .bu ld()

  val remoteManhattanOperat ons = new ManhattanOperat ons(Conf g.Dataset, remoteMhEndpo nt)

  /**
   * Note: T  translat on  s only useful for non-batch endpo nts. Batch endpo nts currently
   * represent fa lure w hout propagat ng an except on
   * (e.g. [[com.tw ter.t etyp e.storage.Response.T etResponseCode.Fa lure]]).
   */
  pr vate[t ] def translateExcept ons(
    ap Na : Str ng,
    statsRece ver: StatsRece ver
  ): Part alFunct on[Throwable, Throwable] = {
    case e:  llegalArgu ntExcept on => Cl entError(e.get ssage, e)
    case e: Den edManhattanExcept on => RateL m ed(e.get ssage, e)
    case e: Vers onM smatchError =>
      statsRece ver.scope(ap Na ).counter("mh_vers on_m smatc s"). ncr()
      e
    case e:  nternalError =>
      T etUt ls.log.error(e, s"Error process ng $ap Na  request: ${e.get ssage}")
      e
  }

  /**
   * Count requests per cl ent  d produc ng  tr cs of t  form
   * .../cl ents/:root_cl ent_ d/requests
   */
  def observeCl ent d[A, B](
    ap St ch: A => St ch[B],
    statsRece ver: StatsRece ver,
    cl ent d lper: Cl ent d lper,
  ): A => St ch[B] = {
    val cl ents = statsRece ver.scope("cl ents")

    val  ncre ntCl entRequests = { args: A =>
      val cl ent d = cl ent d lper.effect veCl ent dRoot.getOrElse(Cl ent d lper.UnknownCl ent d)
      cl ents.counter(cl ent d, "requests"). ncr
    }

    a => {
       ncre ntCl entRequests(a)
      ap St ch(a)
    }
  }

  /**
   *  ncre nt counters based on t  overall response status of t  returned [[GetT et.Response]].
   */
  def observeGetT etResponseCode[A](
    ap St ch: A => St ch[GetT et.Response],
    statsRece ver: StatsRece ver
  ): A => St ch[GetT et.Response] = {
    val scope = statsRece ver.scope("response_code")

    val success = scope.counter("success")
    val notFound = scope.counter("not_found")
    val fa lure = scope.counter("fa lure")
    val overCapac y = scope.counter("over_capac y")
    val deleted = scope.counter("deleted")
    val bounceDeleted = scope.counter("bounce_deleted")

    a =>
      ap St ch(a).respond {
        case Return(_: GetT et.Response.Found) => success. ncr()
        case Return(GetT et.Response.NotFound) => notFound. ncr()
        case Return(_: GetT et.Response.BounceDeleted) => bounceDeleted. ncr()
        case Return(GetT et.Response.Deleted) => deleted. ncr()
        case Throw(_: RateL m ed) => overCapac y. ncr()
        case Throw(_) => fa lure. ncr()
      }
  }

  /**
   *   do 3 th ngs  re:
   *
   * - Bookkeep ng for overall requests
   * - Bookkeep ng for per ap  requests
   * - Translate except ons
   *
   * @param ap Na  t  AP  be ng called
   * @param ap St ch t   mple ntat on of t  AP 
   * @tparam A template for  nput type of AP 
   * @tparam B template for output type of AP 
   * @return Funct on wh ch executes t  g ven AP  call
   */
  pr vate[storage] def endpo nt[A, B](
    ap Na : Str ng,
    ap St ch: A => St ch[B]
  ): A => St ch[B] = {
    val translateExcept on = translateExcept ons(ap Na , statsRece ver)
    val observe = St chUt ls.observe[B](statsRece ver, ap Na )

    a =>
      St chUt ls.translateExcept ons(
        observe(ap St ch(a)),
        translateExcept on
      )
  }

  pr vate[storage] def endpo nt2[A, B, C](
    ap Na : Str ng,
    ap St ch: (A, B) => St ch[C],
    cl ent d lper: Cl ent d lper,
  ): (A, B) => St ch[C] =
    Funct on.untupled(endpo nt(ap Na , ap St ch.tupled))

  val getT et: T etStorageCl ent.GetT et = {
    val stats = statsRece ver.scope("getT et")

    observeCl ent d(
      observeGetT etResponseCode(
        endpo nt(
          "getT et",
          GetT etHandler(
            read = localManhattanOperat ons.read,
            statsRece ver = stats,
          )
        ),
        stats,
      ),
      stats,
      cl ent d lper,
    )
  }

  val getStoredT et: T etStorageCl ent.GetStoredT et = {
    val stats = statsRece ver.scope("getStoredT et")

    observeCl ent d(
      endpo nt(
        "getStoredT et",
        GetStoredT etHandler(
          read = localManhattanOperat ons.read,
          statsRece ver = stats,
        )
      ),
      stats,
      cl ent d lper,
    )
  }

  val addT et: T etStorageCl ent.AddT et =
    endpo nt(
      "addT et",
      AddT etHandler(
         nsert = localManhattanOperat ons. nsert,
        scr be = scr be,
        stats = statsRece ver
      )
    )

  val updateT et: T etStorageCl ent.UpdateT et =
    endpo nt2(
      "updateT et",
      ManhattanT etStorageCl ent.san  zeT etF elds(
        UpdateT etHandler(
           nsert = localManhattanOperat ons. nsert,
          stats = statsRece ver,
        )
      ),
      cl ent d lper,
    )

  val softDelete: T etStorageCl ent.SoftDelete =
    endpo nt(
      "softDelete",
      SoftDeleteHandler(
         nsert = localManhattanOperat ons. nsert,
        scr be = scr be
      )
    )

  val bounceDelete: BounceDelete =
    endpo nt(
      "bounceDelete",
      BounceDeleteHandler(
         nsert = localManhattanOperat ons. nsert,
        scr be = scr be
      )
    )

  val undelete: T etStorageCl ent.Undelete =
    endpo nt(
      "undelete",
      UndeleteHandler(
        read = localManhattanOperat ons.read,
        local nsert = localManhattanOperat ons. nsert,
        remote nsert = remoteManhattanOperat ons. nsert,
        delete = localManhattanOperat ons.delete,
        undeleteW ndowH s = conf g.undeleteW ndowH s,
        stats = statsRece ver
      )
    )

  val getDeletedT ets: T etStorageCl ent.GetDeletedT ets =
    endpo nt(
      "getDeletedT ets",
      GetDeletedT etsHandler(
        read = localManhattanOperat ons.read,
        stats = statsRece ver
      )
    )

  val deleteAdd  onalF elds: T etStorageCl ent.DeleteAdd  onalF elds =
    endpo nt2(
      "deleteAdd  onalF elds",
      DeleteAdd  onalF eldsHandler(
        delete = localManhattanOperat ons.delete,
        stats = statsRece ver,
      ),
      cl ent d lper,
    )

  val scrub: T etStorageCl ent.Scrub =
    endpo nt2(
      "scrub",
      ScrubHandler(
         nsert = localManhattanOperat ons. nsert,
        delete = localManhattanOperat ons.delete,
        scr be = scr be,
        stats = statsRece ver,
      ),
      cl ent d lper,
    )

  val hardDeleteT et: HardDeleteT et =
    endpo nt(
      "hardDeleteT et",
      HardDeleteT etHandler(
        read = localManhattanOperat ons.read,
         nsert = localManhattanOperat ons. nsert,
        delete = localManhattanOperat ons.delete,
        scr be = scr be,
        stats = statsRece ver
      )
    )

  val p ng: T etStorageCl ent.P ng =
    () =>
      St ch
        .run(
          localMhEndpo nt
            .get(
              ManhattanOperat ons.KeyDescr ptor
                .w hDataset(Conf g.Dataset)
                .w hPkey(Random.nextLong().abs)
                .w hLkey(T etKey.LKey.CoreF eldsKey), // could be any lkey
              ValueDescr ptor(Buf nject on)
            ).un 
        )
}
