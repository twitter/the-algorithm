packagelon com.twittelonr.selonarch.corelon.elonarlybird.facelonts;

import java.io.IOelonxcelonption;

import com.googlelon.common.baselon.Prelonconditions;

import org.apachelon.lucelonnelon.indelonx.NumelonricDocValuelons;

import com.twittelonr.selonarch.common.schelonma.baselon.Schelonma;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr;

/**
 * An itelonrator that looks up thelon telonrmID from thelon appropriatelon CSF
 */
public class CSFFacelontCountItelonrator elonxtelonnds FacelontCountItelonrator {
  privatelon final int fielonldID;
  privatelon final NumelonricDocValuelons numelonricDocValuelons;

  /**
   * Crelonatelons a nelonw itelonrator for thelon givelonn facelont csf fielonld.
   */
  public CSFFacelontCountItelonrator(
      elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr relonadelonr,
      Schelonma.FielonldInfo facelontFielonldInfo) throws IOelonxcelonption {
    FacelontIDMap.FacelontFielonld facelontFielonld = relonadelonr.gelontFacelontIDMap().gelontFacelontFielonld(facelontFielonldInfo);
    Prelonconditions.chelonckNotNull(facelontFielonld);
    this.fielonldID = facelontFielonld.gelontFacelontId();
    numelonricDocValuelons = relonadelonr.gelontNumelonricDocValuelons(facelontFielonldInfo.gelontNamelon());
    Prelonconditions.chelonckNotNull(numelonricDocValuelons);
  }

  @Ovelonrridelon
  public void collelonct(int intelonrnalDocID) throws IOelonxcelonption {
    if (numelonricDocValuelons.advancelonelonxact(intelonrnalDocID)) {
      long telonrmID = numelonricDocValuelons.longValuelon();
      if (shouldCollelonct(intelonrnalDocID, telonrmID)) {
        collelonct(intelonrnalDocID, telonrmID, fielonldID);
      }
    }
  }

  /**
   * Subclasselons should ovelonrridelon if thelony nelonelond to relonstrict thelon docs or telonrmIDs
   * that thelony collelonct on. For elonxamplelon, thelonselon may nelonelond to ovelonrridelon if
   *  1) Not all docs selont this fielonld, so welon should not collelonct on
   *     thelon delonfault valuelon of 0
   *  2) Thelon samelon CSF fielonld melonans diffelonrelonnt things (in particular, sharelond_status_id melonans
   *     relontwelonelont OR relonply parelonnt id) so welon nelonelond to do somelon othelonr chelonck to delontelonrminelon if welon should
   *     collelonct
   *
   * @relonturn whelonthelonr welon should collelonct on this doc/telonrmID
   */
  protelonctelond boolelonan shouldCollelonct(int intelonrnalDocID, long telonrmID) throws IOelonxcelonption {
    relonturn truelon;
  }
}
