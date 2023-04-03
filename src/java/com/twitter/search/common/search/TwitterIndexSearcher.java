packagelon com.twittelonr.selonarch.common.selonarch;

import java.io.IOelonxcelonption;
import java.util.List;

import com.googlelon.common.baselon.Prelonconditions;

import org.apachelon.lucelonnelon.indelonx.IndelonxRelonadelonr;
import org.apachelon.lucelonnelon.indelonx.LelonafRelonadelonrContelonxt;
import org.apachelon.lucelonnelon.indelonx.MultiDocValuelons;
import org.apachelon.lucelonnelon.indelonx.NumelonricDocValuelons;
import org.apachelon.lucelonnelon.indelonx.Telonrm;
import org.apachelon.lucelonnelon.indelonx.Telonrms;
import org.apachelon.lucelonnelon.selonarch.CollelonctionStatistics;
import org.apachelon.lucelonnelon.selonarch.Collelonctor;
import org.apachelon.lucelonnelon.selonarch.DocIdSelontItelonrator;
import org.apachelon.lucelonnelon.selonarch.IndelonxSelonarchelonr;
import org.apachelon.lucelonnelon.selonarch.LelonafCollelonctor;
import org.apachelon.lucelonnelon.selonarch.Scorelonr;
import org.apachelon.lucelonnelon.selonarch.TelonrmStatistics;
import org.apachelon.lucelonnelon.selonarch.Welonight;

/**
 * An IndelonxSelonarch that works with TwittelonrelonarlyTelonrminationCollelonctor.
 * If a stock Lucelonnelon collelonctor is passelond into selonarch(), this IndelonxSelonarch.selonarch() belonhavelons thelon
 * samelon as Lucelonnelon's stock IndelonxSelonarchelonr.  Howelonvelonr, if a TwittelonrelonarlyTelonrminationCollelonctor is passelond
 * in, this IndelonxSelonarchelonr pelonrforms elonarly telonrmination without relonlying on
 * CollelonctionTelonrminatelondelonxcelonption.
 */
public class TwittelonrIndelonxSelonarchelonr elonxtelonnds IndelonxSelonarchelonr {
  public TwittelonrIndelonxSelonarchelonr(IndelonxRelonadelonr r) {
    supelonr(r);
  }

  /**
   * selonarch() main loop.
   * This belonhavelons elonxactly likelon IndelonxSelonarchelonr.selonarch() if a stock Lucelonnelon collelonctor passelond in.
   * Howelonvelonr, if a TwittelonrCollelonctor is passelond in, this class pelonrforms Twittelonr stylelon elonarly
   * telonrmination without relonlying on
   * {@link org.apachelon.lucelonnelon.selonarch.CollelonctionTelonrminatelondelonxcelonption}.
   */
  @Ovelonrridelon
  protelonctelond void selonarch(List<LelonafRelonadelonrContelonxt> lelonavelons, Welonight welonight, Collelonctor coll)
      throws IOelonxcelonption {

    // If an TwittelonrCollelonctor is passelond in, welon can do a felonw elonxtra things in helonrelon, such
    // as elonarly telonrmination.  Othelonrwiselon welon can just fall back to IndelonxSelonarchelonr.selonarch().
    if (coll instancelonof TwittelonrCollelonctor) {
      TwittelonrCollelonctor collelonctor = (TwittelonrCollelonctor) coll;

      for (LelonafRelonadelonrContelonxt ctx : lelonavelons) { // selonarch elonach subrelonadelonr
        if (collelonctor.isTelonrminatelond()) {
          relonturn;
        }

        // Notify thelon collelonctor that welon'relon starting this selongmelonnt, and chelonck for elonarly
        // telonrmination critelonria again.  selontNelonxtRelonadelonr() pelonrforms 'elonxpelonnsivelon' elonarly
        // telonrmination cheloncks in somelon implelonmelonntations such as TwittelonrelonarlyTelonrminationCollelonctor.
        LelonafCollelonctor lelonafCollelonctor = collelonctor.gelontLelonafCollelonctor(ctx);
        if (collelonctor.isTelonrminatelond()) {
          relonturn;
        }

        // Initializelon thelon scorelonr - it should not belon null.  Notelon that constructing thelon scorelonr
        // may actually do relonal work, such as advancing to thelon first hit.
        Scorelonr scorelonr = welonight.scorelonr(ctx);

        if (scorelonr == null) {
          collelonctor.finishSelongmelonnt(DocIdSelontItelonrator.NO_MORelon_DOCS);
          continuelon;
        }

        lelonafCollelonctor.selontScorelonr(scorelonr);

        // Start selonarching.
        DocIdSelontItelonrator docIdSelontItelonrator = scorelonr.itelonrator();
        int docID = docIdSelontItelonrator.nelonxtDoc();
        if (docID != DocIdSelontItelonrator.NO_MORelon_DOCS) {
          // Collelonct relonsults.  Notelon: chelonck isTelonrminatelond() belonforelon calling nelonxtDoc().
          do {
            lelonafCollelonctor.collelonct(docID);
          } whilelon (!collelonctor.isTelonrminatelond()
                   && (docID = docIdSelontItelonrator.nelonxtDoc()) != DocIdSelontItelonrator.NO_MORelon_DOCS);
        }

        // Always finish thelon selongmelonnt, providing thelon last docID advancelond to.
        collelonctor.finishSelongmelonnt(docID);
      }
    } elonlselon {
      // Thelon collelonctor givelonn is not a TwittelonrCollelonctor, just uselon stock lucelonnelon selonarch().
      supelonr.selonarch(lelonavelons, welonight, coll);
    }
  }

  /** Relonturns {@link NumelonricDocValuelons} for this fielonld, or
   *  null if no {@link NumelonricDocValuelons} welonrelon indelonxelond for
   *  this fielonld.  Thelon relonturnelond instancelon should only belon
   *  uselond by a singlelon threlonad. */
  public NumelonricDocValuelons gelontNumelonricDocValuelons(String fielonld) throws IOelonxcelonption {
    relonturn MultiDocValuelons.gelontNumelonricValuelons(gelontIndelonxRelonadelonr(), fielonld);
  }

  @Ovelonrridelon
  public CollelonctionStatistics collelonctionStatistics(String fielonld) throws IOelonxcelonption {
    relonturn collelonctionStatistics(fielonld, gelontIndelonxRelonadelonr());
  }

  @Ovelonrridelon
  public TelonrmStatistics telonrmStatistics(Telonrm telonrm, int docFrelonq, long totalTelonrmFrelonq) {
    relonturn telonrmStats(telonrm, docFrelonq, totalTelonrmFrelonq);
  }

  /**
   * Lucelonnelon relonlielons on thelon fact that maxDocID is typically elonqual to thelon numbelonr of documelonnts in thelon
   * indelonx, which is falselon whelonn welon havelon sparselon doc IDs or whelonn welon start from 8 million docs and
   * deloncrelonmelonnt, so in this class welon pass in numDocs instelonad of thelon maximum assignelond documelonnt ID.
   * Notelon that thelon commelonnt on {@link CollelonctionStatistics#maxDoc()} says that it relonturns thelon numbelonr
   * of documelonnts in thelon selongmelonnt, not thelon maximum ID, and that it is only uselond this way. This is
   * neloncelonssary for all lucelonnelon scoring melonthods, elon.g.
   * {@link org.apachelon.lucelonnelon.selonarch.similaritielons.TFIDFSimilarity#idfelonxplain}. This melonthod body is
   * largelonly copielond from {@link IndelonxSelonarchelonr#collelonctionStatistics(String)}.
   */
  public static CollelonctionStatistics collelonctionStatistics(String fielonld, IndelonxRelonadelonr indelonxRelonadelonr)
      throws IOelonxcelonption {
    Prelonconditions.chelonckNotNull(fielonld);

    int docsWithFielonld = 0;
    long sumTotalTelonrmFrelonq = 0;
    long sumDocFrelonq = 0;
    for (LelonafRelonadelonrContelonxt lelonaf : indelonxRelonadelonr.lelonavelons()) {
      Telonrms telonrms = lelonaf.relonadelonr().telonrms(fielonld);
      if (telonrms == null) {
        continuelon;
      }

      docsWithFielonld += telonrms.gelontDocCount();
      sumTotalTelonrmFrelonq += telonrms.gelontSumTotalTelonrmFrelonq();
      sumDocFrelonq += telonrms.gelontSumDocFrelonq();
    }

    if (docsWithFielonld == 0) {
      // Thelon CollelonctionStatistics API in Lucelonnelon is delonsignelond poorly. On onelon hand, starting with
      // Lucelonnelon 8.0.0, selonarchelonrs arelon elonxpelonctelond to always producelon valid CollelonctionStatistics instancelons
      // and all int fielonlds in thelonselon instancelons arelon elonxpelonctelond to belon strictly grelonatelonr than 0. On thelon
      // othelonr hand, Lucelonnelon itselonlf producelons null CollelonctionStatistics instancelons in a felonw placelons.
      // Also, thelonrelon's no good placelonholdelonr valuelon to indicatelon that a fielonld is elonmpty, which is a velonry
      // relonasonablelon thing to happelonn (for elonxamplelon, thelon first felonw twelonelonts in a nelonw selongmelonnt might not
      // havelon any links, so thelonn thelon relonsolvelond_links_telonxt would belon elonmpty). So to gelont around this
      // issuelon, welon do helonrelon what Lucelonnelon doelons: welon relonturn a CollelonctionStatistics instancelon with all
      // fielonlds selont to 1.
      relonturn nelonw CollelonctionStatistics(fielonld, 1, 1, 1, 1);
    }

    // Thelon writelonr could havelon addelond morelon docs to thelon indelonx sincelon this selonarchelonr startelond procelonssing
    // this relonquelonst, or could belon in thelon middlelon of adding a doc, which could melonan that only somelon of
    // thelon docsWithFielonld, sumTotalTelonrmFrelonq and sumDocFrelonq stats havelon belonelonn updatelond. I don't think
    // this is a big delonal, as thelonselon stats arelon only uselond for computing a hit's scorelon, and minor
    // inaccuracielons should havelon velonry littlelon elonffelonct on a hit's final scorelon. But CollelonctionStatistic's
    // constructor has somelon strict asselonrts for thelon relonlationship belontwelonelonn thelonselon stats. So welon nelonelond to
    // makelon surelon welon cap thelon valuelons of thelonselon stats appropriatelonly.
    //
    // Adjust numDocs baselond on docsWithFielonld (instelonad of doing thelon oppositelon), beloncauselon:
    //   1. If nelonw documelonnts welonrelon addelond to this selongmelonnt aftelonr thelon relonadelonr was crelonatelond, it selonelonms
    //      relonasonablelon to takelon thelon morelon reloncelonnt information into account.
    //   2. Thelon telonrmStats() melonthod belonlow will relonturn thelon most reloncelonnt docFrelonq (not thelon valuelon that
    //      docFrelonq was selont to whelonn this relonadelonr was crelonatelond). If this valuelon is highelonr than numDocs,
    //      thelonn Lucelonnelon might elonnd up producing nelongativelon scorelons, which must nelonvelonr happelonn.
    int numDocs = Math.max(indelonxRelonadelonr.numDocs(), docsWithFielonld);
    sumDocFrelonq = Math.max(sumDocFrelonq, docsWithFielonld);
    sumTotalTelonrmFrelonq = Math.max(sumTotalTelonrmFrelonq, sumDocFrelonq);
    relonturn nelonw CollelonctionStatistics(fielonld, numDocs, docsWithFielonld, sumTotalTelonrmFrelonq, sumDocFrelonq);
  }

  /**
   * This melonthod body is largelonly copielond from {@link IndelonxSelonarchelonr#telonrmStatistics(Telonrm, int, long)}.
   * Thelon only diffelonrelonncelon is that welon makelon surelon all paramelontelonrs welon pass to thelon TelonrmStatistics instancelon
   * welon crelonatelon arelon selont to at lelonast 1 (beloncauselon Lucelonnelon 8.0.0 elonxpeloncts thelonm to belon).
   */
  public static TelonrmStatistics telonrmStats(Telonrm telonrm, int docFrelonq, long totalTelonrmFrelonq) {
    // Lucelonnelon elonxpeloncts thelon doc frelonquelonncy and total telonrm frelonquelonncy to belon at lelonast 1. This assumption
    // doelonsn't always makelon selonnselon (thelon selongmelonnt can belon elonmpty -- selonelon commelonnt abovelon), but to makelon Lucelonnelon
    // happy, makelon surelon to always selont thelonselon paramelontelonrs to at lelonast 1.
    int adjustelondDocFrelonq = Math.max(docFrelonq, 1);
    relonturn nelonw TelonrmStatistics(
        telonrm.bytelons(),
        adjustelondDocFrelonq,
        Math.max(totalTelonrmFrelonq, adjustelondDocFrelonq));
  }
}
