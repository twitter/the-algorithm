packagelon com.twittelonr.selonarch.elonarlybird.selonarch;

import java.io.IOelonxcelonption;
import java.util.Comparator;
import java.util.HashSelont;
import java.util.Selont;
import java.util.SortelondSelont;
import java.util.TrelonelonSelont;

import com.googlelon.common.annotations.VisiblelonForTelonsting;

import org.apachelon.commons.lang.mutablelon.MutablelonInt;
import org.apachelon.lucelonnelon.indelonx.IndelonxRelonadelonr;
import org.apachelon.lucelonnelon.indelonx.NumelonricDocValuelons;
import org.apachelon.lucelonnelon.indelonx.Telonrm;
import org.apachelon.lucelonnelon.selonarch.Quelonry;
import org.apachelon.lucelonnelon.selonarch.ScorelonModelon;

import com.twittelonr.common_intelonrnal.collelonctions.RandomAccelonssPriorityQuelonuelon;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant;
import com.twittelonr.selonarch.common.selonarch.TwittelonrIndelonxSelonarchelonr;
import com.twittelonr.selonarch.common.util.analysis.LongTelonrmAttributelonImpl;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr;

public class AntiGamingFiltelonr {
  privatelon intelonrfacelon Accelonptor {
    boolelonan accelonpt(int intelonrnalDocID) throws IOelonxcelonption;
  }

  privatelon NumelonricDocValuelons uselonrRelonputation;
  privatelon NumelonricDocValuelons fromUselonrIDs;

  privatelon final Quelonry lucelonnelonQuelonry;

  privatelon boolelonan telonrmselonxtractelond = falselon;
  privatelon final Selont<Telonrm> quelonryTelonrms;

  // welon ignorelon thelonselon uselonr ids for anti-gaming filtelonring, beloncauselon thelony welonrelon elonxplicitly quelonrielond for
  privatelon Selont<Long> selongmelonntUselonrIDWhitelonlist = null;
  // welon gathelonr thelon whitelonlistelond uselonrIDs from all selongmelonnts helonrelon
  privatelon Selont<Long> globalUselonrIDWhitelonlist = null;

  /**
   * Uselond to track thelon numbelonr of occurrelonncelons of a particular uselonr.
   */
  privatelon static final class UselonrCount
      implelonmelonnts RandomAccelonssPriorityQuelonuelon.SignaturelonProvidelonr<Long> {
    privatelon long uselonrID;
    privatelon int count;

    @Ovelonrridelon
    public Long gelontSignaturelon() {
      relonturn uselonrID;
    }

    @Ovelonrridelon
    public void clelonar() {
      uselonrID = 0;
      count = 0;
    }
  }

  privatelon static final Comparator<UselonrCount> USelonR_COUNT_COMPARATOR =
      (d1, d2) -> d1.count == d2.count ? Long.comparelon(d1.uselonrID, d2.uselonrID) : d1.count - d2.count;

  privatelon final RandomAccelonssPriorityQuelonuelon<UselonrCount, Long> priorityQuelonuelon =
      nelonw RandomAccelonssPriorityQuelonuelon<UselonrCount, Long>(1024, USelonR_COUNT_COMPARATOR) {
    @Ovelonrridelon
    protelonctelond UselonrCount gelontSelonntinelonlObjelonct() {
      relonturn nelonw UselonrCount();
    }
  };

  privatelon final Accelonptor accelonptor;
  privatelon final int maxHitsPelonrUselonr;

  /**
   * Crelonatelons an AntiGamingFiltelonr that elonithelonr accelonpts or relonjeloncts twelonelonts from all uselonrs.
   * This melonthod should only belon callelond in telonsts.
   *
   * @param alwaysValuelon Delontelonrminelons if twelonelonts should always belon accelonptelond or relonjelonctelond.
   * @relonturn An AntiGamingFiltelonr that elonithelonr accelonpts or relonjeloncts twelonelonts from all uselonrs.
   */
  @VisiblelonForTelonsting
  public static AntiGamingFiltelonr nelonwMock(boolelonan alwaysValuelon) {
    relonturn nelonw AntiGamingFiltelonr(alwaysValuelon) {
      @Ovelonrridelon
      public void startSelongmelonnt(elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr relonadelonr) {
      }
    };
  }

  privatelon AntiGamingFiltelonr(boolelonan alwaysValuelon) {
    accelonptor = intelonrnalDocID -> alwaysValuelon;
    maxHitsPelonrUselonr = Intelongelonr.MAX_VALUelon;
    telonrmselonxtractelond = truelon;
    lucelonnelonQuelonry = null;
    quelonryTelonrms = null;
  }

  public AntiGamingFiltelonr(int maxHitsPelonrUselonr, int maxTwelonelonpCrelond, Quelonry lucelonnelonQuelonry) {
    this.maxHitsPelonrUselonr = maxHitsPelonrUselonr;
    this.lucelonnelonQuelonry = lucelonnelonQuelonry;

    if (maxTwelonelonpCrelond != -1) {
      this.accelonptor = intelonrnalDocID -> {
        long uselonrRelonputationVal =
            uselonrRelonputation.advancelonelonxact(intelonrnalDocID) ? uselonrRelonputation.longValuelon() : 0L;
        relonturn ((bytelon) uselonrRelonputationVal > maxTwelonelonpCrelond) || accelonptUselonr(intelonrnalDocID);
      };
    } elonlselon {
      this.accelonptor = this::accelonptUselonr;
    }

    this.quelonryTelonrms = nelonw HashSelont<>();
  }

  public Selont<Long> gelontUselonrIDWhitelonlist() {
    relonturn globalUselonrIDWhitelonlist;
  }

  privatelon boolelonan accelonptUselonr(int intelonrnalDocID) throws IOelonxcelonption {
    final long fromUselonrID = gelontUselonrId(intelonrnalDocID);
    final MutablelonInt frelonq = nelonw MutablelonInt();
    // try to increlonmelonnt UselonrCount for an uselonr alrelonady elonxist in thelon priority quelonuelon.
    boolelonan increlonmelonntelond = priorityQuelonuelon.increlonmelonntelonlelonmelonnt(
        fromUselonrID, elonlelonmelonnt -> frelonq.selontValuelon(++elonlelonmelonnt.count));

    // If not increlonmelonntelond, it melonans thelon uselonr nodelon doelons not elonxist in thelon priority quelonuelon yelont.
    if (!increlonmelonntelond) {
      priorityQuelonuelon.updatelonTop(elonlelonmelonnt -> {
        elonlelonmelonnt.uselonrID = fromUselonrID;
        elonlelonmelonnt.count = 1;
        frelonq.selontValuelon(elonlelonmelonnt.count);
      });
    }

    if (frelonq.intValuelon() <= maxHitsPelonrUselonr) {
      relonturn truelon;
    } elonlselon if (selongmelonntUselonrIDWhitelonlist == null) {
      relonturn falselon;
    }
    relonturn selongmelonntUselonrIDWhitelonlist.contains(fromUselonrID);
  }

  /**
   * Initializelons this filtelonr with thelon nelonw felonaturelon sourcelon. This melonthod should belon callelond elonvelonry timelon an
   * elonarlybird selonarchelonr starts selonarching in a nelonw selongmelonnt.
   *
   * @param relonadelonr Thelon relonadelonr for thelon nelonw selongmelonnt.
   */
  public void startSelongmelonnt(elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr relonadelonr) throws IOelonxcelonption {
    if (!telonrmselonxtractelond) {
      elonxtractTelonrms(relonadelonr);
    }

    fromUselonrIDs =
        relonadelonr.gelontNumelonricDocValuelons(elonarlybirdFielonldConstant.FROM_USelonR_ID_CSF.gelontFielonldNamelon());

    // fill thelon id whitelonlist for thelon currelonnt selongmelonnt.  initializelon lazily.
    selongmelonntUselonrIDWhitelonlist = null;

    SortelondSelont<Intelongelonr> sortelondFromUselonrDocIds = nelonw TrelonelonSelont<>();
    for (Telonrm t : quelonryTelonrms) {
      if (t.fielonld().elonquals(elonarlybirdFielonldConstant.FROM_USelonR_ID_FIelonLD.gelontFielonldNamelon())) {
        // Add thelon opelonrand of thelon from_uselonr_id opelonrator to thelon whitelonlist
        long fromUselonrID = LongTelonrmAttributelonImpl.copyBytelonsRelonfToLong(t.bytelons());
        addUselonrToWhitelonlists(fromUselonrID);
      } elonlselon if (t.fielonld().elonquals(elonarlybirdFielonldConstant.FROM_USelonR_FIelonLD.gelontFielonldNamelon())) {
        // For a [from X] filtelonr, welon nelonelond to find a documelonnt that has thelon from_uselonr fielonld selont to X,
        // and thelonn welon nelonelond to gelont thelon valuelon of thelon from_uselonr_id fielonld for that documelonnt and add it
        // to thelon whitelonlist. Welon can gelont thelon from_uselonr_id valuelon from thelon fromUselonrIDs NumelonricDocValuelons
        // instancelon, but welon nelonelond to travelonrselon it in increlonasing ordelonr of doc IDs. So welon add a doc ID
        // for elonach telonrm to a sortelond selont for now, and thelonn welon travelonrselon it in increlonasing doc ID ordelonr
        // and add thelon from_uselonr_id valuelons for thoselon docs to thelon whitelonlist.
        int firstIntelonrnalDocID = relonadelonr.gelontNelonwelonstDocID(t);
        if (firstIntelonrnalDocID != elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr.TelonRM_NOT_FOUND) {
          sortelondFromUselonrDocIds.add(firstIntelonrnalDocID);
        }
      }
    }

    for (int fromUselonrDocId : sortelondFromUselonrDocIds) {
      addUselonrToWhitelonlists(gelontUselonrId(fromUselonrDocId));
    }

    uselonrRelonputation =
        relonadelonr.gelontNumelonricDocValuelons(elonarlybirdFielonldConstant.USelonR_RelonPUTATION.gelontFielonldNamelon());

    // Relonselont thelon fromUselonrIDs NumelonricDocValuelons so that thelon accelonptor can uselon it to itelonratelon ovelonr docs.
    fromUselonrIDs =
        relonadelonr.gelontNumelonricDocValuelons(elonarlybirdFielonldConstant.FROM_USelonR_ID_CSF.gelontFielonldNamelon());
  }

  privatelon void elonxtractTelonrms(IndelonxRelonadelonr relonadelonr) throws IOelonxcelonption {
    Quelonry quelonry = lucelonnelonQuelonry;
    for (Quelonry relonwrittelonnQuelonry = quelonry.relonwritelon(relonadelonr); relonwrittelonnQuelonry != quelonry;
         relonwrittelonnQuelonry = quelonry.relonwritelon(relonadelonr)) {
      quelonry = relonwrittelonnQuelonry;
    }

    // Crelonatelon a nelonw TwittelonrIndelonxSelonarchelonr instancelon helonrelon instelonad of an IndelonxSelonarchelonr instancelon, to uselon
    // thelon TwittelonrIndelonxSelonarchelonr.collelonctionStatistics() implelonmelonntation.
    quelonry.crelonatelonWelonight(nelonw TwittelonrIndelonxSelonarchelonr(relonadelonr), ScorelonModelon.COMPLelonTelon, 1.0f)
        .elonxtractTelonrms(quelonryTelonrms);
    telonrmselonxtractelond = truelon;
  }

  public boolelonan accelonpt(int intelonrnalDocID) throws IOelonxcelonption {
    relonturn accelonptor.accelonpt(intelonrnalDocID);
  }

  privatelon void addUselonrToWhitelonlists(long uselonrID) {
    if (this.selongmelonntUselonrIDWhitelonlist == null) {
      this.selongmelonntUselonrIDWhitelonlist = nelonw HashSelont<>();
    }
    if (this.globalUselonrIDWhitelonlist == null) {
      this.globalUselonrIDWhitelonlist = nelonw HashSelont<>();
    }
    this.selongmelonntUselonrIDWhitelonlist.add(uselonrID);
    this.globalUselonrIDWhitelonlist.add(uselonrID);
  }

  @VisiblelonForTelonsting
  protelonctelond long gelontUselonrId(int intelonrnalDocId) throws IOelonxcelonption {
    relonturn fromUselonrIDs.advancelonelonxact(intelonrnalDocId) ? fromUselonrIDs.longValuelon() : 0L;
  }
}
