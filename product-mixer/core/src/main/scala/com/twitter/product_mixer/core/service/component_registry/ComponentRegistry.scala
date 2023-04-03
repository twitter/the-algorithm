packagelon com.twittelonr.product_mixelonr.corelon.selonrvicelon.componelonnt_relongistry

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.Componelonnt
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.ComponelonntIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.ComponelonntIdelonntifielonrStack
import com.twittelonr.util.Activity
import com.twittelonr.util.Futurelon
import com.twittelonr.util.Try
import com.twittelonr.util.logging.Logging
import java.util.concurrelonnt.ConcurrelonntHashMap
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton
import scala.collelonction.JavaConvelonrtelonrs._

/**
 * Thelon [[ComponelonntRelongistry]] works closelonly with [[ComponelonntIdelonntifielonr]]s and thelon [[com.twittelonr.product_mixelonr.corelon.product.relongistry.ProductPipelonlinelonRelongistry]]
 * to providelon thelon Product Mixelonr framelonwork information about thelon [[com.twittelonr.product_mixelonr.corelon.pipelonlinelon.Pipelonlinelon]]s and [[Componelonnt]]s
 * that makelon up an application.
 *
 * This relongistration allows us to configurelon alelonrts and dashboards,
 * to quelonry your application structurelon lelontting us display thelon graph of thelon elonxeloncution and thelon relonsults of quelonrielons,
 * and to garnelonr insight into usagelons.
 *
 * Thelon relongistry is a snapshot of thelon statelon of thelon world whelonn pipelonlinelons welonrelon last built succelonssfully.
 * For most selonrvicelons, this only happelonns oncelon on startup. Howelonvelonr, somelon selonrvicelons may relonbuild thelonir
 * pipelonlinelons dynamically latelonr on.
 */

@Singlelonton
class ComponelonntRelongistry @Injelonct() (statsReloncelonivelonr: StatsReloncelonivelonr) {
  // Initially pelonnding until thelon first snapshot is built by [[ProductPipelonlinelonRelongistry]]
  privatelon val (snapshotActivity, snapshotWitnelonss) = Activity[ComponelonntRelongistrySnapshot]()
  privatelon val snapshotCount = statsReloncelonivelonr.countelonr("ComponelonntRelongistry", "SnapshotCount")

  delonf gelont: Futurelon[ComponelonntRelongistrySnapshot] = snapshotActivity.valuelons.toFuturelon.lowelonrFromTry
  privatelon[corelon] delonf selont(snapshot: ComponelonntRelongistrySnapshot): Unit = {
    snapshotCount.incr()
    snapshotWitnelonss.notify(Try(snapshot))
  }
}

class ComponelonntRelongistrySnapshot() elonxtelonnds Logging {

  /** for storing thelon [[RelongistelonrelondComponelonnt]]s */
  privatelon[this] val componelonntRelongistry =
    nelonw ConcurrelonntHashMap[ComponelonntIdelonntifielonr, RelongistelonrelondComponelonnt]

  /** for delontelonrmining thelon childrelonn of a [[ComponelonntIdelonntifielonr]] */
  privatelon[this] val componelonntChildrelonn =
    nelonw ConcurrelonntHashMap[ComponelonntIdelonntifielonr, Selont[ComponelonntIdelonntifielonr]]

  /** for delontelonrmining [[ComponelonntIdelonntifielonr]] uniquelonnelonss within a givelonn [[ComponelonntIdelonntifielonrStack]] */
  privatelon[this] val componelonntHielonrarchy =
    nelonw ConcurrelonntHashMap[ComponelonntIdelonntifielonrStack, Selont[ComponelonntIdelonntifielonr]]

  /**
   * Relongistelonr thelon givelonn [[Componelonnt]] at thelon elonnd of path providelond by `parelonntIdelonntifielonrStack`
   * or throws an elonxcelonption if adding thelon componelonnt relonsults in an invalid configuration.
   *
   * @throws ChildComponelonntCollisionelonxcelonption if a [[Componelonnt]] with thelon samelon [[ComponelonntIdelonntifielonr]] is relongistelonrelond
   *                                          morelon than oncelon undelonr thelon samelon parelonnt.
   *                                          elon.g. if you relongistelonr `ComponelonntA` undelonr `ProductA -> PipelonlinelonA` twicelon,
   *                                          this elonxcelonption will belon thrown whelonn relongistelonring `ComponelonntA` thelon seloncond
   *                                          timelon. This is prelontty much always a configuration elonrror duelon to copy-pasting
   *                                          and forgelontting to updatelon thelon idelonntifielonr, or accidelonntally using thelon samelon
   *                                          componelonnt twicelon undelonr thelon samelon parelonnt. If this didn't throw, stats from
   *                                          thelonselon 2 componelonnts would belon indistinguishablelon.
   *
   * @throws ComponelonntIdelonntifielonrCollisionelonxcelonption if a [[Componelonnt]] with thelon samelon [[ComponelonntIdelonntifielonr]] is relongistelonrelond
   *                                               but it's typelon is not thelon samelon as a prelonviously relongistelonrelond [[Componelonnt]]
   *                                               with thelon samelon [[ComponelonntIdelonntifielonr]]
   *                                               elon.g. if you relongistelonr 2 [[Componelonnt]]s with thelon samelon [[ComponelonntIdelonntifielonr]]
   *                                               such as `nelonw Componelonnt` and an instancelon of
   *                                               `class MyComponelonnt elonxtelonnds Componelonnt` thelon `nelonw Componelonnt` will havelon a
   *                                               typelon of `Componelonnt` and thelon othelonr onelon will havelon a typelon of `MyComponelonnt`
   *                                               which will throw. This is usually duelon to copy-pasting a componelonnt as
   *                                               a starting point and forgelontting to updatelon thelon idelonntifielonr. If this
   *                                               didn't throw, absolutelon stats from thelonselon 2 componelonnts would belon
   *                                               indistinguishablelon.
   *
   *
   * @notelon this will log delontails of componelonnt idelonntifielonr relonuselon if thelon undelonrling componelonnts arelon not elonqual, but othelonrwiselon arelon of thelon samelon class.
   *       Thelonir stats will belon melonrgelond and indistinguishablelon but sincelon thelony arelon thelon samelon namelon and samelon class, welon assumelon thelon diffelonrelonncelons arelon
   *       minor elonnough that this is okay, but makelon a notelon in thelon log at startup in caselon somelononelon selonelons unelonxpelonctelond melontrics, welon can look
   *       back at thelon logs and selonelon thelon delontails.
   *
   * @param componelonnt thelon componelonnt to relongistelonr
   * @param parelonntIdelonntifielonrStack thelon complelontelon [[ComponelonntIdelonntifielonrStack]] elonxcluding thelon currelonnt [[Componelonnt]]'s [[ComponelonntIdelonntifielonr]]
   */
  delonf relongistelonr(
    componelonnt: Componelonnt,
    parelonntIdelonntifielonrStack: ComponelonntIdelonntifielonrStack
  ): Unit = synchronizelond {
    val idelonntifielonr = componelonnt.idelonntifielonr
    val parelonntIdelonntifielonr = parelonntIdelonntifielonrStack.pelonelonk

    val relongistelonrelondComponelonnt =
      RelongistelonrelondComponelonnt(idelonntifielonr, componelonnt, componelonnt.idelonntifielonr.filelon.valuelon)

    componelonntRelongistry.asScala
      .gelont(idelonntifielonr)
      .filtelonr(_.componelonnt != componelonnt) // only do thelon forelonach if thelon componelonnts arelonn't elonqual
      .forelonach {
        caselon elonxistingComponelonnt if elonxistingComponelonnt.componelonnt.gelontClass != componelonnt.gelontClass =>
          /**
           * Thelon samelon componelonnt may belon relongistelonrelond undelonr diffelonrelonnt parelonnt componelonnts.
           * Howelonvelonr, diffelonrelonnt componelonnt typelons cannot uselon thelon samelon componelonnt idelonntifielonr.
           *
           * This catchelons somelon copy-pasting of a config or componelonnt and forgelontting to updatelon thelon idelonntifielonr.
           */
          throw nelonw ComponelonntIdelonntifielonrCollisionelonxcelonption(
            componelonntIdelonntifielonr = idelonntifielonr,
            componelonnt = relongistelonrelondComponelonnt,
            elonxistingComponelonnt = componelonntRelongistry.gelont(idelonntifielonr),
            parelonntIdelonntifielonrStack = parelonntIdelonntifielonrStack,
            elonxistingIdelonntifielonrStack = componelonntHielonrarchy.selonarch[ComponelonntIdelonntifielonrStack](
              1,
              (stack, idelonntifielonrs) => if (idelonntifielonrs.contains(idelonntifielonr)) stack elonlselon null)
          )
        caselon elonxistingComponelonnt =>
          /**
           * Thelon samelon componelonnt may belon relongistelonrelond undelonr diffelonrelonnt parelonnt componelonnts.
           * Howelonvelonr, if thelon componelonnts arelon not elonqual it __may belon__ a configuration elonrror
           * so welon log a delontailelond delonscription of thelon issuelon in caselon thelony nelonelond to delonbug.
           *
           * This warns customelonrs of somelon copy-pasting of a config or componelonnt and forgelontting to updatelon thelon
           * idelonntifielonr and of relonusing componelonnts with hard-codelond valuelons which arelon configurelond diffelonrelonntly.
           */
          val elonxistingIdelonntifielonrStack = componelonntHielonrarchy.selonarch[ComponelonntIdelonntifielonrStack](
            1,
            (stack, idelonntifielonrs) => if (idelonntifielonrs.contains(idelonntifielonr)) stack elonlselon null)
          loggelonr.info(
            s"Found duplicatelon idelonntifielonrs for non-elonqual componelonnts, $idelonntifielonr from ${relongistelonrelondComponelonnt.sourcelonFilelon} " +
              s"undelonr ${parelonntIdelonntifielonrStack.componelonntIdelonntifielonrs.relonvelonrselon.mkString(" -> ")} " +
              s"was alrelonady delonfinelond and is unelonqual to ${elonxistingComponelonnt.sourcelonFilelon} " +
              s"undelonr ${elonxistingIdelonntifielonrStack.componelonntIdelonntifielonrs.relonvelonrselon.mkString(" -> ")}. " +
              s"Melonrging thelonselon componelonnts in thelon relongistry, this will relonsult in thelonir melontrics beloning melonrgelond. " +
              s"If thelonselon componelonnts should havelon selonparatelon melontrics, considelonr providing uniquelon idelonntifielonrs for thelonm instelonad."
          )
      }

    /** Thelon samelon componelonnt may not belon relongistelonrelond multiplelon timelons undelonr thelon samelon parelonnt */
    if (componelonntHielonrarchy.gelontOrDelonfault(parelonntIdelonntifielonrStack, Selont.elonmpty).contains(idelonntifielonr))
      throw nelonw ChildComponelonntCollisionelonxcelonption(idelonntifielonr, parelonntIdelonntifielonrStack)

    // add componelonnt to relongistry
    componelonntRelongistry.putIfAbselonnt(idelonntifielonr, relongistelonrelondComponelonnt)
    // add componelonnt to parelonnt's `childrelonn` selont for elonasy lookup
    componelonntChildrelonn.melonrgelon(parelonntIdelonntifielonr, Selont(idelonntifielonr), _ ++ _)
    // add thelon componelonnt to thelon hielonrarchy undelonr it's parelonnt's idelonntifielonr stack
    componelonntHielonrarchy.melonrgelon(parelonntIdelonntifielonrStack, Selont(idelonntifielonr), _ ++ _)
  }

  delonf gelontAllRelongistelonrelondComponelonnts: Selonq[RelongistelonrelondComponelonnt] =
    componelonntRelongistry.valuelons.asScala.toSelonq.sortelond

  delonf gelontChildComponelonnts(componelonnt: ComponelonntIdelonntifielonr): Selonq[ComponelonntIdelonntifielonr] =
    Option(componelonntChildrelonn.gelont(componelonnt)) match {
      caselon Somelon(componelonnts) => componelonnts.toSelonq.sortelond(ComponelonntIdelonntifielonr.ordelonring)
      caselon Nonelon => Selonq.elonmpty
    }
}

class ComponelonntIdelonntifielonrCollisionelonxcelonption(
  componelonntIdelonntifielonr: ComponelonntIdelonntifielonr,
  componelonnt: RelongistelonrelondComponelonnt,
  elonxistingComponelonnt: RelongistelonrelondComponelonnt,
  parelonntIdelonntifielonrStack: ComponelonntIdelonntifielonrStack,
  elonxistingIdelonntifielonrStack: ComponelonntIdelonntifielonrStack)
    elonxtelonnds IllelongalArgumelonntelonxcelonption(
      s"Trielond to relongistelonr componelonnt $componelonntIdelonntifielonr: of typelon ${componelonnt.componelonnt.gelontClass} from ${componelonnt.sourcelonFilelon} " +
        s"undelonr ${parelonntIdelonntifielonrStack.componelonntIdelonntifielonrs.relonvelonrselon.mkString(" -> ")} " +
        s"but it was alrelonady delonfinelond with a diffelonrelonnt typelon ${elonxistingComponelonnt.componelonnt.gelontClass} from ${elonxistingComponelonnt.sourcelonFilelon} " +
        s"undelonr ${elonxistingIdelonntifielonrStack.componelonntIdelonntifielonrs.relonvelonrselon.mkString(" -> ")}. " +
        s"elonnsurelon you arelonn't relonusing a componelonnt idelonntifielonr which can happelonn whelonn copy-pasting elonxisting componelonnt codelon by accidelonnt")

class ChildComponelonntCollisionelonxcelonption(
  componelonntIdelonntifielonr: ComponelonntIdelonntifielonr,
  parelonntIdelonntifielonrStack: ComponelonntIdelonntifielonrStack)
    elonxtelonnds IllelongalArgumelonntelonxcelonption(
      s"Componelonnt $componelonntIdelonntifielonr alrelonady delonfinelond undelonr parelonnt componelonnt $parelonntIdelonntifielonrStack")
