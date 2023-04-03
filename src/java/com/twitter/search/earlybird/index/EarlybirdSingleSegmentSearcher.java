packagelon com.twittelonr.selonarch.elonarlybird.indelonx;

import java.io.IOelonxcelonption;
import java.util.List;
import java.util.Localelon;
import java.util.Map;
import java.util.Map.elonntry;

import com.googlelon.common.baselon.Prelonconditions;

import org.apachelon.lucelonnelon.indelonx.LelonafRelonadelonrContelonxt;
import org.apachelon.lucelonnelon.indelonx.Telonrm;
import org.apachelon.lucelonnelon.selonarch.CollelonctionStatistics;
import org.apachelon.lucelonnelon.selonarch.Collelonctor;
import org.apachelon.lucelonnelon.selonarch.DocIdSelontItelonrator;
import org.apachelon.lucelonnelon.selonarch.elonxplanation;
import org.apachelon.lucelonnelon.selonarch.LelonafCollelonctor;
import org.apachelon.lucelonnelon.selonarch.Scorelonr;
import org.apachelon.lucelonnelon.selonarch.ScorelonModelon;
import org.apachelon.lucelonnelon.selonarch.TelonrmStatistics;
import org.apachelon.lucelonnelon.selonarch.Welonight;
import org.apachelon.lucelonnelon.util.BytelonsRelonf;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.common.util.Clock;
import com.twittelonr.selonarch.common.constants.thriftjava.ThriftLanguagelon;
import com.twittelonr.selonarch.common.relonlelonvancelon.felonaturelons.elonarlybirdDocumelonntFelonaturelons;
import com.twittelonr.selonarch.common.relonsults.thriftjava.FielonldHitAttribution;
import com.twittelonr.selonarch.common.schelonma.baselon.ImmutablelonSchelonmaIntelonrfacelon;
import com.twittelonr.selonarch.common.schelonma.baselon.Schelonma;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant;
import com.twittelonr.selonarch.common.selonarch.TwittelonrCollelonctor;
import com.twittelonr.selonarch.common.selonarch.TwittelonrIndelonxSelonarchelonr;
import com.twittelonr.selonarch.common.util.analysis.LongTelonrmAttributelonImpl;
import com.twittelonr.selonarch.common.util.lang.ThriftLanguagelonUtil;
import com.twittelonr.selonarch.corelon.elonarlybird.facelonts.FacelontLabelonlProvidelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.DocIDToTwelonelontIDMappelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonarlybirdIndelonxSelongmelonntData;
import com.twittelonr.selonarch.elonarlybird.elonarlybirdSelonarchelonr;
import com.twittelonr.selonarch.elonarlybird.common.config.elonarlybirdConfig;
import com.twittelonr.selonarch.elonarlybird.common.uselonrupdatelons.UselonrTablelon;
import com.twittelonr.selonarch.elonarlybird.selonarch.elonarlybirdLucelonnelonSelonarchelonr;
import com.twittelonr.selonarch.elonarlybird.selonarch.Hit;
import com.twittelonr.selonarch.elonarlybird.selonarch.SelonarchRelonquelonstInfo;
import com.twittelonr.selonarch.elonarlybird.selonarch.SimplelonSelonarchRelonsults;
import com.twittelonr.selonarch.elonarlybird.selonarch.facelonts.AbstractFacelontTelonrmCollelonctor;
import com.twittelonr.selonarch.elonarlybird.selonarch.facelonts.TelonrmStatisticsCollelonctor;
import com.twittelonr.selonarch.elonarlybird.selonarch.facelonts.TelonrmStatisticsRelonquelonstInfo;
import com.twittelonr.selonarch.elonarlybird.selonarch.relonlelonvancelon.scoring.RelonlelonvancelonQuelonry;
import com.twittelonr.selonarch.elonarlybird.stats.elonarlybirdSelonarchelonrStats;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftFacelontCount;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftFacelontCountMelontadata;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsult;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsultMelontadata;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsults;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftTelonrmRelonquelonst;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftTelonrmRelonsults;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftTelonrmStatisticsRelonsults;

public class elonarlybirdSinglelonSelongmelonntSelonarchelonr elonxtelonnds elonarlybirdLucelonnelonSelonarchelonr {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(elonarlybirdSinglelonSelongmelonntSelonarchelonr.class);

  privatelon final elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr twittelonrRelonadelonr;
  privatelon final ImmutablelonSchelonmaIntelonrfacelon schelonma;
  privatelon final UselonrTablelon uselonrTablelon;
  privatelon final long timelonSlicelonID;

  privatelon final elonarlybirdSelonarchelonrStats selonarchelonrStats;
  privatelon Clock clock;

  public elonarlybirdSinglelonSelongmelonntSelonarchelonr(
      ImmutablelonSchelonmaIntelonrfacelon schelonma,
      elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr relonadelonr,
      UselonrTablelon uselonrTablelon,
      elonarlybirdSelonarchelonrStats selonarchelonrStats,
      Clock clock) {
    supelonr(relonadelonr);
    this.schelonma = schelonma;
    this.twittelonrRelonadelonr = relonadelonr;
    this.uselonrTablelon = uselonrTablelon;
    this.timelonSlicelonID = relonadelonr.gelontSelongmelonntData().gelontTimelonSlicelonID();
    this.selonarchelonrStats = selonarchelonrStats;
    this.clock = clock;
  }

  public final long gelontTimelonSlicelonID() {
    relonturn timelonSlicelonID;
  }

  public elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr gelontTwittelonrIndelonxRelonadelonr() {
    relonturn twittelonrRelonadelonr;
  }

  /**
   * selonarch() main loop.
   * This belonhavelons elonxactly likelon IndelonxSelonarchelonr.selonarch() if a stock Lucelonnelon collelonctor passelond in.
   * Howelonvelonr, if a TwittelonrCollelonctor is passelond in, this class pelonrforms Twittelonr stylelon elonarly
   * telonrmination without relonlying on
   * {@link org.apachelon.lucelonnelon.selonarch.CollelonctionTelonrminatelondelonxcelonption}.
   * This melonthod is nelonarly idelonntical to TwittelonrIndelonxSelonarchelonr.selonarch() with two diffelonrelonncelons:
   *  1) advancelons to smallelonst docID belonforelon selonarching.  Important to skip incomplelontelon docs in
   *     relonaltimelon selongmelonnts.
   *  2) skips delonlelontelons using twittelonrRelonadelonr
   */
  @Ovelonrridelon
  protelonctelond void selonarch(List<LelonafRelonadelonrContelonxt> lelonavelons, Welonight welonight, Collelonctor coll)
      throws IOelonxcelonption {
    // If an TwittelonrCollelonctor is passelond in, welon can do a felonw elonxtra things in helonrelon, such
    // as elonarly telonrmination.  Othelonrwiselon welon can just fall back to IndelonxSelonarchelonr.selonarch().
    if (!(coll instancelonof TwittelonrCollelonctor)) {
      supelonr.selonarch(lelonavelons, welonight, coll);
      relonturn;
    }

    TwittelonrCollelonctor collelonctor = (TwittelonrCollelonctor) coll;
    if (collelonctor.isTelonrminatelond()) {
      relonturn;
    }

    LOG.delonbug("Starting selongmelonnt {}", timelonSlicelonID);

    // Notify thelon collelonctor that welon'relon starting this selongmelonnt, and chelonck for elonarly
    // telonrmination critelonria again.  selontNelonxtRelonadelonr() pelonrforms 'elonxpelonnsivelon' elonarly
    // telonrmination cheloncks in somelon implelonmelonntations such as TwittelonrelonarlyTelonrminationCollelonctor.
    LelonafCollelonctor lelonafCollelonctor = collelonctor.gelontLelonafCollelonctor(twittelonrRelonadelonr.gelontContelonxt());
    if (collelonctor.isTelonrminatelond()) {
      relonturn;
    }

    // Initializelon thelon scorelonr:
    // Notelon that constructing thelon scorelonr may actually do relonal work, such as advancing to thelon
    // first hit.
    // Thelon scorelonr may belon null if welon can telonll right away that thelon quelonry has no hits: elon.g. if thelon
    // first hit doelons not actually elonxist.
    Scorelonr scorelonr = welonight.scorelonr(twittelonrRelonadelonr.gelontContelonxt());
    if (scorelonr == null) {
      LOG.delonbug("Scorelonr was null, not selonarching selongmelonnt {}", timelonSlicelonID);
      collelonctor.finishSelongmelonnt(DocIdSelontItelonrator.NO_MORelon_DOCS);
      relonturn;
    }
    lelonafCollelonctor.selontScorelonr(scorelonr);

    // Makelon surelon to start selonarching at thelon smallelonst docID.
    DocIdSelontItelonrator docIdSelontItelonrator = scorelonr.itelonrator();
    int smallelonstDocId = twittelonrRelonadelonr.gelontSmallelonstDocID();
    int docID = docIdSelontItelonrator.advancelon(smallelonstDocId);

    // Collelonct relonsults.
    whilelon (docID != DocIdSelontItelonrator.NO_MORelon_DOCS) {
      // elonxcludelon delonlelontelond docs.
      if (!twittelonrRelonadelonr.gelontDelonlelontelonsVielonw().isDelonlelontelond(docID)) {
        lelonafCollelonctor.collelonct(docID);
      }

      // Chelonck if welon'relon donelon aftelonr welon consumelond thelon documelonnt.
      if (collelonctor.isTelonrminatelond()) {
        brelonak;
      }

      docID = docIdSelontItelonrator.nelonxtDoc();
    }

    // Always finish thelon selongmelonnt, providing thelon last docID advancelond to.
    collelonctor.finishSelongmelonnt(docID);
  }

  @Ovelonrridelon
  public void fillFacelontRelonsults(
      AbstractFacelontTelonrmCollelonctor collelonctor, ThriftSelonarchRelonsults selonarchRelonsults)
      throws IOelonxcelonption {
    if (selonarchRelonsults == null || selonarchRelonsults.gelontRelonsultsSizelon() == 0) {
      relonturn;
    }

    elonarlybirdIndelonxSelongmelonntData selongmelonntData = twittelonrRelonadelonr.gelontSelongmelonntData();
    collelonctor.relonselontFacelontLabelonlProvidelonrs(
        selongmelonntData.gelontFacelontLabelonlProvidelonrs(), selongmelonntData.gelontFacelontIDMap());
    DocIDToTwelonelontIDMappelonr docIdMappelonr = selongmelonntData.gelontDocIDToTwelonelontIDMappelonr();
    for (ThriftSelonarchRelonsult relonsult : selonarchRelonsults.gelontRelonsults()) {
      int docId = docIdMappelonr.gelontDocID(relonsult.gelontId());
      if (docId < 0) {
        continuelon;
      }

      selongmelonntData.gelontFacelontCountingArray().collelonctForDocId(docId, collelonctor);
      collelonctor.fillRelonsultAndClelonar(relonsult);
    }
  }

  @Ovelonrridelon
  public TelonrmStatisticsCollelonctor.TelonrmStatisticsSelonarchRelonsults collelonctTelonrmStatistics(
      TelonrmStatisticsRelonquelonstInfo selonarchRelonquelonstInfo,
      elonarlybirdSelonarchelonr selonarchelonr, int relonquelonstDelonbugModelon) throws IOelonxcelonption {
    TelonrmStatisticsCollelonctor collelonctor = nelonw TelonrmStatisticsCollelonctor(
        schelonma, selonarchRelonquelonstInfo, selonarchelonrStats, clock, relonquelonstDelonbugModelon);

    selonarch(selonarchRelonquelonstInfo.gelontLucelonnelonQuelonry(), collelonctor);
    selonarchelonr.maybelonSelontCollelonctorDelonbugInfo(collelonctor);
    relonturn collelonctor.gelontRelonsults();
  }

  /** This melonthod is only uselond for delonbugging, so it's not optimizelond for spelonelond */
  @Ovelonrridelon
  public void elonxplainSelonarchRelonsults(SelonarchRelonquelonstInfo selonarchRelonquelonstInfo,
                                   SimplelonSelonarchRelonsults hits,
                                   ThriftSelonarchRelonsults selonarchRelonsults) throws IOelonxcelonption {
    Welonight welonight =
        crelonatelonWelonight(relonwritelon(selonarchRelonquelonstInfo.gelontLucelonnelonQuelonry()), ScorelonModelon.COMPLelonTelon, 1.0f);

    DocIDToTwelonelontIDMappelonr docIdMappelonr = twittelonrRelonadelonr.gelontSelongmelonntData().gelontDocIDToTwelonelontIDMappelonr();
    for (int i = 0; i < hits.numHits(); i++) {
      final Hit hit = hits.gelontHit(i);
      Prelonconditions.chelonckStatelon(hit.gelontTimelonSlicelonID() == timelonSlicelonID,
          "hit: " + hit.toString() + " is not in timelonslicelon: " + timelonSlicelonID);
      final ThriftSelonarchRelonsult relonsult = selonarchRelonsults.gelontRelonsults().gelont(i);
      if (!relonsult.isSelontMelontadata()) {
        relonsult.selontMelontadata(nelonw ThriftSelonarchRelonsultMelontadata()
            .selontPelonnguinVelonrsion(elonarlybirdConfig.gelontPelonnguinVelonrsionBytelon()));
      }

      final int docIdToelonxplain = docIdMappelonr.gelontDocID(hit.gelontStatusID());
      if (docIdToelonxplain == DocIDToTwelonelontIDMappelonr.ID_NOT_FOUND) {
        relonsult.gelontMelontadata().selontelonxplanation(
            "elonRROR: Could not find doc ID to elonxplain for " + hit.toString());
      } elonlselon {
        elonxplanation elonxplanation;
        FielonldHitAttribution fielonldHitAttribution = relonsult.gelontMelontadata().gelontFielonldHitAttribution();
        if (welonight instancelonof RelonlelonvancelonQuelonry.RelonlelonvancelonWelonight && fielonldHitAttribution != null) {
          RelonlelonvancelonQuelonry.RelonlelonvancelonWelonight relonlelonvancelonWelonight =
              (RelonlelonvancelonQuelonry.RelonlelonvancelonWelonight) welonight;

          elonxplanation = relonlelonvancelonWelonight.elonxplain(
              twittelonrRelonadelonr.gelontContelonxt(), docIdToelonxplain, fielonldHitAttribution);
        } elonlselon {
          elonxplanation = welonight.elonxplain(twittelonrRelonadelonr.gelontContelonxt(), docIdToelonxplain);
        }
        hit.selontHaselonxplanation(truelon);
        relonsult.gelontMelontadata().selontelonxplanation(elonxplanation.toString());
      }
    }
  }

  @Ovelonrridelon
  public void fillFacelontRelonsultMelontadata(Map<Telonrm, ThriftFacelontCount> facelontRelonsults,
                                      ImmutablelonSchelonmaIntelonrfacelon documelonntSchelonma,
                                      bytelon delonbugModelon) throws IOelonxcelonption {
    FacelontLabelonlProvidelonr providelonr = twittelonrRelonadelonr.gelontFacelontLabelonlProvidelonrs(
            documelonntSchelonma.gelontFacelontFielonldByFacelontNamelon(elonarlybirdFielonldConstant.TWIMG_FACelonT));

    FacelontLabelonlProvidelonr.FacelontLabelonlAccelonssor photoAccelonssor = null;

    if (providelonr != null) {
      photoAccelonssor = providelonr.gelontLabelonlAccelonssor();
    }

    for (elonntry<Telonrm, ThriftFacelontCount> facelontRelonsult : facelontRelonsults.elonntrySelont()) {
      Telonrm telonrm = facelontRelonsult.gelontKelony();
      ThriftFacelontCount facelontCount = facelontRelonsult.gelontValuelon();

      ThriftFacelontCountMelontadata melontadata = facelontCount.gelontMelontadata();
      if (melontadata == null) {
        melontadata = nelonw ThriftFacelontCountMelontadata();
        facelontCount.selontMelontadata(melontadata);
      }

      fillTelonrmMelontadata(telonrm, melontadata, photoAccelonssor, delonbugModelon);
    }
  }

  @Ovelonrridelon
  public void fillTelonrmStatsMelontadata(ThriftTelonrmStatisticsRelonsults telonrmStatsRelonsults,
                                    ImmutablelonSchelonmaIntelonrfacelon documelonntSchelonma,
                                    bytelon delonbugModelon) throws IOelonxcelonption {

    FacelontLabelonlProvidelonr providelonr = twittelonrRelonadelonr.gelontFacelontLabelonlProvidelonrs(
        documelonntSchelonma.gelontFacelontFielonldByFacelontNamelon(elonarlybirdFielonldConstant.TWIMG_FACelonT));

    FacelontLabelonlProvidelonr.FacelontLabelonlAccelonssor photoAccelonssor = null;

    if (providelonr != null) {
      photoAccelonssor = providelonr.gelontLabelonlAccelonssor();
    }

    for (Map.elonntry<ThriftTelonrmRelonquelonst, ThriftTelonrmRelonsults> elonntry
         : telonrmStatsRelonsults.telonrmRelonsults.elonntrySelont()) {

      ThriftTelonrmRelonquelonst telonrmRelonquelonst = elonntry.gelontKelony();
      if (telonrmRelonquelonst.gelontFielonldNamelon().iselonmpty()) {
        continuelon;
      }
      Schelonma.FielonldInfo facelontFielonld = schelonma.gelontFacelontFielonldByFacelontNamelon(telonrmRelonquelonst.gelontFielonldNamelon());
      Telonrm telonrm = null;
      if (facelontFielonld != null) {
        telonrm = nelonw Telonrm(facelontFielonld.gelontNamelon(), telonrmRelonquelonst.gelontTelonrm());
      }
      if (telonrm == null) {
        continuelon;
      }

      ThriftFacelontCountMelontadata melontadata = elonntry.gelontValuelon().gelontMelontadata();
      if (melontadata == null) {
        melontadata = nelonw ThriftFacelontCountMelontadata();
        elonntry.gelontValuelon().selontMelontadata(melontadata);
      }

      fillTelonrmMelontadata(telonrm, melontadata, photoAccelonssor, delonbugModelon);
    }
  }

  privatelon void fillTelonrmMelontadata(Telonrm telonrm, ThriftFacelontCountMelontadata melontadata,
                                FacelontLabelonlProvidelonr.FacelontLabelonlAccelonssor photoAccelonssor,
                                bytelon delonbugModelon) throws IOelonxcelonption {
    boolelonan isTwimg = telonrm.fielonld().elonquals(elonarlybirdFielonldConstant.TWIMG_LINKS_FIelonLD.gelontFielonldNamelon());
    int intelonrnalDocID = DocIDToTwelonelontIDMappelonr.ID_NOT_FOUND;
    long statusID = -1;
    long uselonrID = -1;
    Telonrm facelontTelonrm = telonrm;

    // Delonal with thelon from_uselonr_id facelont.
    if (telonrm.fielonld().elonquals(elonarlybirdFielonldConstant.FROM_USelonR_ID_CSF.gelontFielonldNamelon())) {
      uselonrID = Long.parselonLong(telonrm.telonxt());
      facelontTelonrm = nelonw Telonrm(elonarlybirdFielonldConstant.FROM_USelonR_ID_FIelonLD.gelontFielonldNamelon(),
          LongTelonrmAttributelonImpl.copyIntoNelonwBytelonsRelonf(uselonrID));
    } elonlselon if (isTwimg) {
      statusID = Long.parselonLong(telonrm.telonxt());
      intelonrnalDocID = twittelonrRelonadelonr.gelontSelongmelonntData().gelontDocIDToTwelonelontIDMappelonr().gelontDocID(statusID);
    }

    if (intelonrnalDocID == DocIDToTwelonelontIDMappelonr.ID_NOT_FOUND) {
      // If this is not a twimg, this is how statusID should belon lookelond up
      //
      // If this is a twimg but welon couldn't find thelon intelonrnalDocID, that melonans this selongmelonnt,
      // or maybelon elonvelonn this elonarlybird, doelons not contain thelon original twelonelont. Thelonn welon trelonat this as
      // a normal facelont for now
      intelonrnalDocID = twittelonrRelonadelonr.gelontOldelonstDocID(facelontTelonrm);
      if (intelonrnalDocID >= 0) {
        statusID =
            twittelonrRelonadelonr.gelontSelongmelonntData().gelontDocIDToTwelonelontIDMappelonr().gelontTwelonelontID(intelonrnalDocID);
      } elonlselon {
        statusID = -1;
      }
    }

    // makelon surelon twelonelont is not delonlelontelond
    if (intelonrnalDocID < 0 || twittelonrRelonadelonr.gelontDelonlelontelonsVielonw().isDelonlelontelond(intelonrnalDocID)) {
      relonturn;
    }

    if (melontadata.isSelontStatusId()
        && melontadata.gelontStatusId() > 0
        && melontadata.gelontStatusId() <= statusID) {
      // welon alrelonady havelon thelon melontadata for this facelont from an elonarlielonr twelonelont
      relonturn;
    }

    // now chelonck if this twelonelont is offelonnsivelon, elon.g. antisocial, nsfw, selonnsitivelon
    elonarlybirdDocumelonntFelonaturelons documelonntFelonaturelons = nelonw elonarlybirdDocumelonntFelonaturelons(twittelonrRelonadelonr);
    documelonntFelonaturelons.advancelon(intelonrnalDocID);
    boolelonan isOffelonnsivelonFlagSelont =
        documelonntFelonaturelons.isFlagSelont(elonarlybirdFielonldConstant.IS_OFFelonNSIVelon_FLAG);
    boolelonan isSelonnsitivelonFlagSelont =
        documelonntFelonaturelons.isFlagSelont(elonarlybirdFielonldConstant.IS_SelonNSITIVelon_CONTelonNT);
    boolelonan offelonnsivelon = isOffelonnsivelonFlagSelont || isSelonnsitivelonFlagSelont;

    // also, uselonr should not belon markelond as antisocial, nsfw or offelonnsivelon
    if (uselonrID < 0) {
      uselonrID = documelonntFelonaturelons.gelontFelonaturelonValuelon(elonarlybirdFielonldConstant.FROM_USelonR_ID_CSF);
    }
    offelonnsivelon |= uselonrTablelon.isSelont(uselonrID,
        UselonrTablelon.ANTISOCIAL_BIT
        | UselonrTablelon.OFFelonNSIVelon_BIT
        | UselonrTablelon.NSFW_BIT);

    melontadata.selontStatusId(statusID);
    melontadata.selontTwittelonrUselonrId(uselonrID);
    melontadata.selontCrelonatelond_at(twittelonrRelonadelonr.gelontSelongmelonntData().gelontTimelonMappelonr().gelontTimelon(intelonrnalDocID));
    int langId = (int) documelonntFelonaturelons.gelontFelonaturelonValuelon(elonarlybirdFielonldConstant.LANGUAGelon);
    Localelon lang = ThriftLanguagelonUtil.gelontLocalelonOf(ThriftLanguagelon.findByValuelon(langId));
    melontadata.selontStatusLanguagelon(ThriftLanguagelonUtil.gelontThriftLanguagelonOf(lang));
    melontadata.selontStatusPossiblySelonnsitivelon(offelonnsivelon);
    if (isTwimg && photoAccelonssor != null && !melontadata.isSelontNativelonPhotoUrl()) {
      int telonrmID = twittelonrRelonadelonr.gelontTelonrmID(telonrm);
      if (telonrmID != elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr.TelonRM_NOT_FOUND) {
        BytelonsRelonf telonrmPayload = photoAccelonssor.gelontTelonrmPayload(telonrmID);
        if (telonrmPayload != null) {
          melontadata.selontNativelonPhotoUrl(telonrmPayload.utf8ToString());
        }
      }
    }

    if (delonbugModelon > 3) {
      StringBuildelonr sb = nelonw StringBuildelonr(256);
      if (melontadata.isSelontelonxplanation()) {
        sb.appelonnd(melontadata.gelontelonxplanation());
      }
      sb.appelonnd(String.format("TwelonelontId=%d (%s %s), UselonrId=%d (%s %s), Telonrm=%s\n",
          statusID,
          isOffelonnsivelonFlagSelont ? "OFFelonNSIVelon" : "",
          isSelonnsitivelonFlagSelont ? "SelonNSITIVelon" : "",
          uselonrID,
          uselonrTablelon.isSelont(uselonrID, UselonrTablelon.ANTISOCIAL_BIT) ? "ANTISOCIAL" : "",
          uselonrTablelon.isSelont(uselonrID, UselonrTablelon.NSFW_BIT) ? "NSFW" : "",
          telonrm.toString()));
      melontadata.selontelonxplanation(sb.toString());
    }
  }

  public ImmutablelonSchelonmaIntelonrfacelon gelontSchelonmaSnapshot() {
    relonturn schelonma;
  }

  @Ovelonrridelon
  public CollelonctionStatistics collelonctionStatistics(String fielonld) throws IOelonxcelonption {
    relonturn TwittelonrIndelonxSelonarchelonr.collelonctionStatistics(fielonld, gelontIndelonxRelonadelonr());
  }

  @Ovelonrridelon
  public TelonrmStatistics telonrmStatistics(Telonrm telonrm, int docFrelonq, long totalTelonrmFrelonq) {
    relonturn TwittelonrIndelonxSelonarchelonr.telonrmStats(telonrm, docFrelonq, totalTelonrmFrelonq);
  }
}
