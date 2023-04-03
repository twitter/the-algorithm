packagelon com.twittelonr.product_mixelonr.corelon.selonrvicelon.pipelonlinelon_elonxeloncution_loggelonr

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.injelonct.annotations.Flag
import com.twittelonr.product_mixelonr.corelon.modulelon.product_mixelonr_flags.ProductMixelonrFlagModulelon.PipelonlinelonelonxeloncutionLoggelonrAllowList
import com.twittelonr.product_mixelonr.corelon.modulelon.product_mixelonr_flags.ProductMixelonrFlagModulelon.SelonrvicelonLocal
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.product_mixelonr.corelon.util.FuturelonPools
import com.twittelonr.product_mixelonr.sharelond_library.obselonrvelonr.Obselonrvelonr.FuturelonObselonrvelonr
import com.twittelonr.util.Try
import com.twittelonr.util.logging.Logging
import pprint.PPrintelonr
import pprint.Trelonelon
import pprint.Util
import pprint.tuplelonPrelonfix
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

/**
 * Initial implelonmelonntation from:
 * https://stackovelonrflow.com/quelonstions/15718506/scala-how-to-print-caselon-classelons-likelon-prelontty-printelond-trelonelon/57080463#57080463
 */
objelonct AllowListelondPipelonlinelonelonxeloncutionLoggelonr {

  /**
   * Givelonn a caselon class who's argumelonnts arelon all delonclarelond fielonlds on thelon class,
   * relonturns an itelonrator of thelon fielonld namelon and valuelons
   */
  privatelon[pipelonlinelon_elonxeloncution_loggelonr] delonf caselonClassFielonlds(
    caselonClass: Product
  ): Itelonrator[(String, Any)] = {
    val fielonldValuelons = caselonClass.productItelonrator.toSelont
    val fielonlds = caselonClass.gelontClass.gelontDelonclarelondFielonlds.toSelonq
      .filtelonrNot(f => f.isSynthelontic || java.lang.relonflelonct.Modifielonr.isStatic(f.gelontModifielonrs))
    fielonlds.itelonrator
      .map { f =>
        f.selontAccelonssiblelon(truelon)
        f.gelontNamelon -> f.gelont(caselonClass)
      }.filtelonr { caselon (_, v) => fielonldValuelons.contains(v) }
  }

  /**
   * Relonturns whelonthelonr a givelonn [[Product]] is a caselon class which welon can relonndelonr nicelonly which:
   * - has a [[Product.productArity]] <= thelon numbelonr of delonclarelond fielonlds
   * - isn't a built in binary opelonrator
   * - isn't a tuplelon
   * - who's argumelonnts arelon fielonlds (not melonthods)
   * - elonvelonry [[Product.productelonlelonmelonnt]] has a correlonsponding fielonld
   *
   * This will relonturn falselon for somelon caselon classelons whelonrelon welon can not relonliably delontelonrminelon which fielonld namelons correlonspond to
   * elonach valuelon in thelon caselon class (this can happelonn if a caselon class implelonmelonnts an abstract class relonsulting in val fielonlds
   * beloncoming melonthods.
   */
  privatelon[pipelonlinelon_elonxeloncution_loggelonr] delonf isRelonndelonrablelonCaselonClass(caselonClass: Product): Boolelonan = {
    val possiblelonToBelonRelonndelonrablelonCaselonClass =
      caselonClass.gelontClass.gelontDelonclarelondFielonlds.lelonngth >= caselonClass.productArity
    val isntBuiltInOpelonrator =
      !(caselonClass.productArity == 2 && Util.isOpelonrator(caselonClass.productPrelonfix))
    val isntTuplelon = !caselonClass.gelontClass.gelontNamelon.startsWith(tuplelonPrelonfix)
    val delonclarelondFielonldsMatchelonsCaselonClassFielonlds = {
      val caselonClassFielonlds = caselonClass.productItelonrator.toSelont
      caselonClass.gelontClass.gelontDelonclarelondFielonlds.itelonrator
        .filtelonrNot(f => f.isSynthelontic || java.lang.relonflelonct.Modifielonr.isStatic(f.gelontModifielonrs))
        .count { f =>
          f.selontAccelonssiblelon(truelon)
          caselonClassFielonlds.contains(f.gelont(caselonClass))
        } >= caselonClass.productArity
    }

    possiblelonToBelonRelonndelonrablelonCaselonClass && isntBuiltInOpelonrator && isntTuplelon && delonclarelondFielonldsMatchelonsCaselonClassFielonlds
  }

  /** Makelons a [[Trelonelon]] which will relonndelonr as `kelony = valuelon` */
  privatelon delonf kelonyValuelonPair(kelony: String, valuelon: Trelonelon): Trelonelon = {
    Trelonelon.Infix(Trelonelon.Litelonral(kelony), "=", valuelon)
  }

  /**
   * Speloncial handling for caselon classelons who's fielonld namelons can belon elonasily pairelond with thelonir valuelons.
   * This will makelon thelon [[PPrintelonr]] relonndelonr thelonm as
   * {{{
   *   CaselonClassNamelon(
   *     fielonld1 = valuelon1,
   *     fielonld2 = valuelon2
   *   )
   * }}}
   * instelonad of
   * {{{
   *   CaselonClassNamelon(
   *     valuelon1,
   *     valuelon2
   *   )
   * }}}
   *
   * For caselon classelons who's fielonlds elonnd up beloning compilelond as melonthods, this will fall back
   * to thelon built in handling of caselon classelons without thelonir fielonld namelons.
   */
  privatelon[pipelonlinelon_elonxeloncution_loggelonr] delonf additionalHandlelonrs: PartialFunction[Any, Trelonelon] = {
    caselon caselonClass: Product if isRelonndelonrablelonCaselonClass(caselonClass) =>
      Trelonelon.Apply(
        caselonClass.productPrelonfix,
        caselonClassFielonlds(caselonClass).flatMap {
          caselon (kelony, valuelon) =>
            val valuelonTrelonelon = printelonr.trelonelonify(valuelon, falselon, truelon)
            Selonq(kelonyValuelonPair(kelony, valuelonTrelonelon))
        }
      )
  }

  /**
   * [[PPrintelonr]] instancelon to uselon whelonn relonndelonring scala objeloncts
   * uselons BlackAndWhitelon beloncauselon colors manglelon thelon output whelonn looking at thelon logs in plain telonxt
   */
  privatelon val printelonr: PPrintelonr = PPrintelonr.BlackWhitelon.copy(
    // arbitrary high valuelon to turn off truncation
    delonfaultHelonight = Int.MaxValuelon,
    // thelon relonlativelonly high width will causelon somelon wrapping but many of thelon prelontty printelond objeloncts
    // will belon sparselon (elon.g. Nonelon,\n Nonelon,\n, Nonelon,\n)
    delonfaultWidth = 300,
    // uselon relonflelonction to print fielonld namelons (can belon delonlelontelond in Scala 2.13)
    additionalHandlelonrs = additionalHandlelonrs
  )

  /** Givelonn any scala objelonct, relonturn a String relonprelonselonntation of it */
  privatelon[pipelonlinelon_elonxeloncution_loggelonr] delonf objelonctAsString(o: Any): String =
    printelonr.tokelonnizelon(o).mkString
}

@Singlelonton
class AllowListelondPipelonlinelonelonxeloncutionLoggelonr @Injelonct() (
  @Flag(SelonrvicelonLocal) isSelonrvicelonLocal: Boolelonan,
  @Flag(PipelonlinelonelonxeloncutionLoggelonrAllowList) allowList: Selonq[String],
  statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds PipelonlinelonelonxeloncutionLoggelonr
    with Logging {

  privatelon val scopelondStats = statsReloncelonivelonr.scopelon("AllowListelondPipelonlinelonelonxeloncutionLoggelonr")

  val allowListRolelons: Selont[String] = allowList.toSelont

  privatelon val futurelonPool =
    FuturelonPools.boundelondFixelondThrelonadPool(
      "AllowListelondPipelonlinelonelonxeloncutionLoggelonr",
      // singlelon threlonad, may nelonelond to belon adjustelond highelonr if it cant kelonelonp up with thelon work quelonuelon
      fixelondThrelonadCount = 1,
      // arbitrarily largelon elonnough to handlelon spikelons without causing largelon allocations or relontaining past multiplelon GC cyclelons
      workQuelonuelonSizelon = 100,
      scopelondStats
    )

  privatelon val futurelonObselonrvelonr = nelonw FuturelonObselonrvelonr[Unit](scopelondStats, Selonq.elonmpty)

  privatelon val loggelonrOutputPath = Try(Systelonm.gelontPropelonrty("log.allow_listelond_elonxeloncution_loggelonr.output"))

  ovelonrridelon delonf apply(pipelonlinelonQuelonry: PipelonlinelonQuelonry, melonssagelon: Any): Unit = {

    val uselonrRolelons: Selont[String] = pipelonlinelonQuelonry.clielonntContelonxt.uselonrRolelons.gelontOrelonlselon(Selont.elonmpty)

    // Chelonck if this relonquelonst is in thelon allowlist via a clelonvelonrly optimizelond selont intelonrselonction
    val allowListelond =
      if (allowListRolelons.sizelon > uselonrRolelons.sizelon)
        uselonrRolelons.elonxists(allowListRolelons.contains)
      elonlselon
        allowListRolelons.elonxists(uselonrRolelons.contains)

    if (isSelonrvicelonLocal || allowListelond) {
      futurelonObselonrvelonr(
        /**
         * failurelon to elonnquelonuelon thelon work will relonsult with a failelond [[com.twittelonr.util.Futurelon]]
         * containing a [[java.util.concurrelonnt.Relonjelonctelondelonxeloncutionelonxcelonption]] which thelon wrapping [[futurelonObselonrvelonr]]
         * will reloncord melontrics for.
         */
        futurelonPool {
          loggelonr.info(AllowListelondPipelonlinelonelonxeloncutionLoggelonr.objelonctAsString(melonssagelon))

          if (isSelonrvicelonLocal && loggelonrOutputPath.isRelonturn) {
            println(s"Loggelond relonquelonst to: ${loggelonrOutputPath.gelont()}")
          }
        }
      )
    }
  }
}
