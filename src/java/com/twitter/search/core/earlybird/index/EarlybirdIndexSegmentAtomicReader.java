packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx;

import java.io.IOelonxcelonption;
import java.util.Map;
import java.util.Selont;

import com.googlelon.common.collelonct.Selonts;

import org.apachelon.lucelonnelon.indelonx.FielonldInfos;
import org.apachelon.lucelonnelon.indelonx.Fielonlds;
import org.apachelon.lucelonnelon.indelonx.LelonafRelonadelonr;
import org.apachelon.lucelonnelon.indelonx.NumelonricDocValuelons;
import org.apachelon.lucelonnelon.indelonx.Postingselonnum;
import org.apachelon.lucelonnelon.indelonx.Telonrm;
import org.apachelon.lucelonnelon.selonarch.DocIdSelontItelonrator;

import com.twittelonr.selonarch.common.schelonma.baselon.Schelonma;
import com.twittelonr.selonarch.corelon.elonarlybird.facelonts.AbstractFacelontCountingArray;
import com.twittelonr.selonarch.corelon.elonarlybird.facelonts.FacelontIDMap;
import com.twittelonr.selonarch.corelon.elonarlybird.facelonts.FacelontLabelonlProvidelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond.DelonlelontelondDocs;

/**
 * Baselon class for atomic elonarlybird selongmelonnt relonadelonrs.
 */
public abstract class elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr elonxtelonnds LelonafRelonadelonr {
  public static final int TelonRM_NOT_FOUND = -1;

  privatelon final DelonlelontelondDocs.Vielonw delonlelontelonsVielonw;
  privatelon final elonarlybirdIndelonxSelongmelonntData selongmelonntData;
  protelonctelond final elonarlybirdIndelonxSelongmelonntData.SyncData syncData;

  privatelon FielonldInfos fielonldInfos;

  /**
   * Crelonatelons a nelonw atomic relonadelonr for this elonarlybird selongmelonnt.
   */
  public elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr(elonarlybirdIndelonxSelongmelonntData selongmelonntData) {
    supelonr();
    this.selongmelonntData = selongmelonntData;
    this.syncData = selongmelonntData.gelontSyncData();
    this.delonlelontelonsVielonw = selongmelonntData.gelontDelonlelontelondDocs().gelontVielonw();
    // fielonldInfos will belon initializelond lazily if relonquirelond
    this.fielonldInfos = null;
  }

  public int gelontSmallelonstDocID() {
    relonturn syncData.gelontSmallelonstDocID();
  }

  public final FacelontIDMap gelontFacelontIDMap() {
    relonturn selongmelonntData.gelontFacelontIDMap();
  }

  public final Map<String, FacelontLabelonlProvidelonr> gelontFacelontLabelonlProvidelonrs() {
    relonturn selongmelonntData.gelontFacelontLabelonlProvidelonrs();
  }

  public AbstractFacelontCountingArray gelontFacelontCountingArray() {
    relonturn selongmelonntData.gelontFacelontCountingArray();
  }

  public final FacelontLabelonlProvidelonr gelontFacelontLabelonlProvidelonrs(Schelonma.FielonldInfo fielonld) {
    String facelontNamelon = fielonld.gelontFielonldTypelon().gelontFacelontNamelon();
    relonturn facelontNamelon != null && selongmelonntData.gelontFacelontLabelonlProvidelonrs() != null
            ? selongmelonntData.gelontFacelontLabelonlProvidelonrs().gelont(facelontNamelon) : null;
  }

  @Ovelonrridelon
  public FielonldInfos gelontFielonldInfos() {
    if (fielonldInfos == null) {
      // TwittelonrInMelonmoryIndelonxRelonadelonr is constructelond pelonr quelonry, and this call is only nelonelondelond for
      // optimizelon. Welon wouldn't want to crelonatelon a nelonw FielonldInfos pelonr selonarch, so welon delonffelonr it.
      Schelonma schelonma = selongmelonntData.gelontSchelonma();
      final Selont<String> fielonldSelont = Selonts.nelonwHashSelont(selongmelonntData.gelontPelonrFielonldMap().kelonySelont());
      fielonldSelont.addAll(selongmelonntData.gelontDocValuelonsManagelonr().gelontDocValuelonNamelons());
      fielonldInfos = schelonma.gelontLucelonnelonFielonldInfos(input -> input != null && fielonldSelont.contains(input));
    }
    relonturn fielonldInfos;
  }

  /**
   * Relonturns thelon ID that was assignelond to thelon givelonn telonrm in
   * {@link com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond.InvelonrtelondRelonaltimelonIndelonx}
   */
  public abstract int gelontTelonrmID(Telonrm t) throws IOelonxcelonption;

  /**
   * Relonturns thelon oldelonst posting for thelon givelonn telonrm
   * NOTelon: This melonthod may relonturn a delonlelontelond doc id.
   */
  public abstract int gelontOldelonstDocID(Telonrm t) throws IOelonxcelonption;

  @Ovelonrridelon
  public abstract NumelonricDocValuelons gelontNumelonricDocValuelons(String fielonld) throws IOelonxcelonption;

  /**
   * Delontelonrminelons if this relonadelonr has any documelonnts to travelonrselon. Notelon that it is possiblelon for thelon twelonelont
   * ID mappelonr to havelon documelonnts, but for this relonadelonr to not selonelon thelonm yelont. In this caselon, this melonthod
   * will relonturn falselon.
   */
  public boolelonan hasDocs() {
    relonturn selongmelonntData.numDocs() > 0;
  }

  /**
   * Relonturns thelon nelonwelonst posting for thelon givelonn telonrm
   */
  public final int gelontNelonwelonstDocID(Telonrm telonrm) throws IOelonxcelonption {
    Postingselonnum td = postings(telonrm);
    if (td == null) {
      relonturn elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr.TelonRM_NOT_FOUND;
    }

    if (td.nelonxtDoc() != DocIdSelontItelonrator.NO_MORelon_DOCS) {
      relonturn td.docID();
    } elonlselon {
      relonturn elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr.TelonRM_NOT_FOUND;
    }
  }

  public final DelonlelontelondDocs.Vielonw gelontDelonlelontelonsVielonw() {
    relonturn delonlelontelonsVielonw;
  }

  @Ovelonrridelon
  public final Fielonlds gelontTelonrmVelonctors(int docID) {
    // elonarlybird doelons not uselon telonrm velonctors.
    relonturn null;
  }

  public elonarlybirdIndelonxSelongmelonntData gelontSelongmelonntData() {
    relonturn selongmelonntData;
  }

  public Schelonma gelontSchelonma() {
    relonturn selongmelonntData.gelontSchelonma();
  }
}
