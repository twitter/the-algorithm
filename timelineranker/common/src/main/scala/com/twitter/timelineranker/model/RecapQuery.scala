package com.tw ter.t  l neranker.model

 mport com.tw ter.servo.ut l.Gate
 mport com.tw ter.t  l nes.model.cand date.Cand dateT etS ce d
 mport com.tw ter.t  l neranker.{thr ftscala => thr ft}
 mport com.tw ter.t  l nes.common.model._
 mport com.tw ter.t  l nes.earlyb rd.common.opt ons.Earlyb rdOpt ons
 mport com.tw ter.t  l nes.earlyb rd.common.ut ls.SearchOperator
 mport com.tw ter.t  l nes.conf gap .{
  DependencyProv der => Conf gAp DependencyProv der,
  FutureDependencyProv der => Conf gAp FutureDependencyProv der,
  _
}
 mport com.tw ter.t  l nes.model.T et d
 mport com.tw ter.t  l nes.model.User d
 mport com.tw ter.t  l neserv ce.Dev ceContext

object RecapQuery {

  val EngagedT etsSupportedT etK ndOpt on: T etK ndOpt on.ValueSet = T etK ndOpt on(
     ncludeRepl es = false,
     ncludeRet ets = false,
     ncludeExtendedRepl es = false,
     ncludeOr g nalT etsAndQuotes = true
  )

  val DefaultSearchOperator: SearchOperator.Value = SearchOperator.Exclude
  def fromThr ft(query: thr ft.RecapQuery): RecapQuery = {

    RecapQuery(
      user d = query.user d,
      maxCount = query.maxCount,
      range = query.range.map(T  l neRange.fromThr ft),
      opt ons = query.opt ons
        .map(opt ons => T etK ndOpt on.fromThr ft(opt ons.to[Set]))
        .getOrElse(T etK ndOpt on.None),
      searchOperator = query.searchOperator
        .map(SearchOperator.fromThr ft)
        .getOrElse(DefaultSearchOperator),
      earlyb rdOpt ons = query.earlyb rdOpt ons.map(Earlyb rdOpt ons.fromThr ft),
      dev ceContext = query.dev ceContext.map(Dev ceContext.fromThr ft),
      author ds = query.author ds,
      excludedT et ds = query.excludedT et ds,
      searchCl entSub d = query.searchCl entSub d,
      cand dateT etS ce d =
        query.cand dateT etS ce d.flatMap(Cand dateT etS ce d.fromThr ft),
      hydratesContentFeatures = query.hydratesContentFeatures
    )
  }

  def fromThr ft(query: thr ft.RecapHydrat onQuery): RecapQuery = {
    requ re(query.t et ds.nonEmpty, "t et ds must be non-empty")

    RecapQuery(
      user d = query.user d,
      t et ds = So (query.t et ds),
      searchOperator = DefaultSearchOperator,
      earlyb rdOpt ons = query.earlyb rdOpt ons.map(Earlyb rdOpt ons.fromThr ft),
      dev ceContext = query.dev ceContext.map(Dev ceContext.fromThr ft),
      cand dateT etS ce d =
        query.cand dateT etS ce d.flatMap(Cand dateT etS ce d.fromThr ft),
      hydratesContentFeatures = query.hydratesContentFeatures
    )
  }

  def fromThr ft(query: thr ft.EngagedT etsQuery): RecapQuery = {
    val opt ons = query.t etK ndOpt ons
      .map(t etK ndOpt ons => T etK ndOpt on.fromThr ft(t etK ndOpt ons.to[Set]))
      .getOrElse(T etK ndOpt on.None)

     f (!(opt ons. sEmpty ||
        (opt ons == EngagedT etsSupportedT etK ndOpt on))) {
      throw new  llegalArgu ntExcept on(s"Unsupported T etK ndOpt on value: $opt ons")
    }

    RecapQuery(
      user d = query.user d,
      maxCount = query.maxCount,
      range = query.range.map(T  l neRange.fromThr ft),
      opt ons = opt ons,
      searchOperator = DefaultSearchOperator,
      earlyb rdOpt ons = query.earlyb rdOpt ons.map(Earlyb rdOpt ons.fromThr ft),
      dev ceContext = query.dev ceContext.map(Dev ceContext.fromThr ft),
      author ds = query.user ds,
      excludedT et ds = query.excludedT et ds,
    )
  }

  def fromThr ft(query: thr ft.Ent yT etsQuery): RecapQuery = {
    requ re(
      query.semant cCore ds. sDef ned,
      "ent  es(semant cCore ds) can't be None"
    )
    val opt ons = query.t etK ndOpt ons
      .map(t etK ndOpt ons => T etK ndOpt on.fromThr ft(t etK ndOpt ons.to[Set]))
      .getOrElse(T etK ndOpt on.None)

    RecapQuery(
      user d = query.user d,
      maxCount = query.maxCount,
      range = query.range.map(T  l neRange.fromThr ft),
      opt ons = opt ons,
      searchOperator = DefaultSearchOperator,
      earlyb rdOpt ons = query.earlyb rdOpt ons.map(Earlyb rdOpt ons.fromThr ft),
      dev ceContext = query.dev ceContext.map(Dev ceContext.fromThr ft),
      excludedT et ds = query.excludedT et ds,
      semant cCore ds = query.semant cCore ds.map(_.map(Semant cCoreAnnotat on.fromThr ft).toSet),
      hashtags = query.hashtags.map(_.toSet),
      languages = query.languages.map(_.map(Language.fromThr ft).toSet),
      cand dateT etS ce d =
        query.cand dateT etS ce d.flatMap(Cand dateT etS ce d.fromThr ft),
       ncludeNullcastT ets = query. ncludeNullcastT ets,
       ncludeT etsFromArch ve ndex = query. ncludeT etsFromArch ve ndex,
      author ds = query.author ds,
      hydratesContentFeatures = query.hydratesContentFeatures
    )
  }

  def fromThr ft(query: thr ft.UtegL kedByT etsQuery): RecapQuery = {
    val opt ons = query.t etK ndOpt ons
      .map(t etK ndOpt ons => T etK ndOpt on.fromThr ft(t etK ndOpt ons.to[Set]))
      .getOrElse(T etK ndOpt on.None)

    RecapQuery(
      user d = query.user d,
      maxCount = query.maxCount,
      range = query.range.map(T  l neRange.fromThr ft),
      opt ons = opt ons,
      earlyb rdOpt ons = query.earlyb rdOpt ons.map(Earlyb rdOpt ons.fromThr ft),
      dev ceContext = query.dev ceContext.map(Dev ceContext.fromThr ft),
      excludedT et ds = query.excludedT et ds,
      utegL kedByT etsOpt ons = for {
        utegCount <- query.utegCount
          ghtedFollow ngs <- query.  ghtedFollow ngs.map(_.toMap)
      } y eld {
        UtegL kedByT etsOpt ons(
          utegCount = utegCount,
           s nNetwork = query. s nNetwork,
            ghtedFollow ngs =   ghtedFollow ngs
        )
      },
      cand dateT etS ce d =
        query.cand dateT etS ce d.flatMap(Cand dateT etS ce d.fromThr ft),
      hydratesContentFeatures = query.hydratesContentFeatures
    )
  }

  val paramGate: (Param[Boolean] => Gate[RecapQuery]) = HasParams.paramGate

  type DependencyProv der[+T] = Conf gAp DependencyProv der[RecapQuery, T]
  object DependencyProv der extends DependencyProv derFunct ons[RecapQuery]

  type FutureDependencyProv der[+T] = Conf gAp FutureDependencyProv der[RecapQuery, T]
  object FutureDependencyProv der extends FutureDependencyProv derFunct ons[RecapQuery]
}

/**
 * Model object correspond ng to RecapQuery thr ft struct.
 */
case class RecapQuery(
  user d: User d,
  maxCount: Opt on[ nt] = None,
  range: Opt on[T  l neRange] = None,
  opt ons: T etK ndOpt on.ValueSet = T etK ndOpt on.None,
  searchOperator: SearchOperator.Value = RecapQuery.DefaultSearchOperator,
  earlyb rdOpt ons: Opt on[Earlyb rdOpt ons] = None,
  dev ceContext: Opt on[Dev ceContext] = None,
  author ds: Opt on[Seq[User d]] = None,
  t et ds: Opt on[Seq[T et d]] = None,
  semant cCore ds: Opt on[Set[Semant cCoreAnnotat on]] = None,
  hashtags: Opt on[Set[Str ng]] = None,
  languages: Opt on[Set[Language]] = None,
  excludedT et ds: Opt on[Seq[T et d]] = None,
  // opt ons used only for yml t ets
  utegL kedByT etsOpt ons: Opt on[UtegL kedByT etsOpt ons] = None,
  searchCl entSub d: Opt on[Str ng] = None,
  overr de val params: Params = Params.Empty,
  cand dateT etS ce d: Opt on[Cand dateT etS ce d.Value] = None,
   ncludeNullcastT ets: Opt on[Boolean] = None,
   ncludeT etsFromArch ve ndex: Opt on[Boolean] = None,
  hydratesContentFeatures: Opt on[Boolean] = None)
    extends HasParams {

  overr de def toStr ng: Str ng = {
    s"RecapQuery(user d: $user d, maxCount: $maxCount, range: $range, opt ons: $opt ons, searchOperator: $searchOperator, " +
      s"earlyb rdOpt ons: $earlyb rdOpt ons, dev ceContext: $dev ceContext, author ds: $author ds, " +
      s"t et ds: $t et ds, semant cCore ds: $semant cCore ds, hashtags: $hashtags, languages: $languages, excludedT et ds: $excludedT et ds, " +
      s"utegL kedByT etsOpt ons: $utegL kedByT etsOpt ons, searchCl entSub d: $searchCl entSub d, " +
      s"params: $params, cand dateT etS ce d: $cand dateT etS ce d,  ncludeNullcastT ets: $ ncludeNullcastT ets, " +
      s" ncludeT etsFromArch ve ndex: $ ncludeT etsFromArch ve ndex), hydratesContentFeatures: $hydratesContentFeatures"
  }

  def throw f nval d(): Un  = {
    def noDupl cates[T <: Traversable[_]](ele nts: T) = {
      ele nts.toSet.s ze == ele nts.s ze
    }

    maxCount.foreach { max => requ re(max > 0, "maxCount must be a pos  ve  nteger") }
    range.foreach(_.throw f nval d())
    earlyb rdOpt ons.foreach(_.throw f nval d())
    t et ds.foreach {  ds => requ re( ds.nonEmpty, "t et ds must be nonEmpty  f present") }
    semant cCore ds.foreach(_.foreach(_.throw f nval d()))
    languages.foreach(_.foreach(_.throw f nval d()))
    languages.foreach { langs =>
      requ re(langs.nonEmpty, "languages must be nonEmpty  f present")
      requ re(noDupl cates(langs.map(_.language)), "languages must be un que")
    }
  }

  throw f nval d()

  def toThr ftRecapQuery: thr ft.RecapQuery = {
    val thr ftOpt ons = So (T etK ndOpt on.toThr ft(opt ons))
    thr ft.RecapQuery(
      user d,
      maxCount,
      range.map(_.toT  l neRangeThr ft),
      deprecatedM nCount = None,
      thr ftOpt ons,
      earlyb rdOpt ons.map(_.toThr ft),
      dev ceContext.map(_.toThr ft),
      author ds,
      excludedT et ds,
      So (SearchOperator.toThr ft(searchOperator)),
      searchCl entSub d,
      cand dateT etS ce d.flatMap(Cand dateT etS ce d.toThr ft)
    )
  }

  def toThr ftRecapHydrat onQuery: thr ft.RecapHydrat onQuery = {
    requ re(t et ds. sDef ned && t et ds.get.nonEmpty, "t et ds must be present")
    thr ft.RecapHydrat onQuery(
      user d,
      t et ds.get,
      earlyb rdOpt ons.map(_.toThr ft),
      dev ceContext.map(_.toThr ft),
      cand dateT etS ce d.flatMap(Cand dateT etS ce d.toThr ft)
    )
  }

  def toThr ftEnt yT etsQuery: thr ft.Ent yT etsQuery = {
    val thr ftT etK ndOpt ons = So (T etK ndOpt on.toThr ft(opt ons))
    thr ft.Ent yT etsQuery(
      user d = user d,
      maxCount = maxCount,
      range = range.map(_.toT  l neRangeThr ft),
      t etK ndOpt ons = thr ftT etK ndOpt ons,
      earlyb rdOpt ons = earlyb rdOpt ons.map(_.toThr ft),
      dev ceContext = dev ceContext.map(_.toThr ft),
      excludedT et ds = excludedT et ds,
      semant cCore ds = semant cCore ds.map(_.map(_.toThr ft)),
      hashtags = hashtags,
      languages = languages.map(_.map(_.toThr ft)),
      cand dateT etS ce d.flatMap(Cand dateT etS ce d.toThr ft),
       ncludeNullcastT ets =  ncludeNullcastT ets,
       ncludeT etsFromArch ve ndex =  ncludeT etsFromArch ve ndex,
      author ds = author ds
    )
  }

  def toThr ftUtegL kedByT etsQuery: thr ft.UtegL kedByT etsQuery = {

    val thr ftT etK ndOpt ons = So (T etK ndOpt on.toThr ft(opt ons))
    thr ft.UtegL kedByT etsQuery(
      user d = user d,
      maxCount = maxCount,
      utegCount = utegL kedByT etsOpt ons.map(_.utegCount),
      range = range.map(_.toT  l neRangeThr ft),
      t etK ndOpt ons = thr ftT etK ndOpt ons,
      earlyb rdOpt ons = earlyb rdOpt ons.map(_.toThr ft),
      dev ceContext = dev ceContext.map(_.toThr ft),
      excludedT et ds = excludedT et ds,
       s nNetwork = utegL kedByT etsOpt ons.map(_. s nNetwork).get,
        ghtedFollow ngs = utegL kedByT etsOpt ons.map(_.  ghtedFollow ngs),
      cand dateT etS ce d = cand dateT etS ce d.flatMap(Cand dateT etS ce d.toThr ft)
    )
  }
}
