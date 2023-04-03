packagelon com.twittelonr.selonarch.elonarlybird.selonarch.facelonts;

import java.io.IOelonxcelonption;

import org.apachelon.lucelonnelon.indelonx.NumelonricDocValuelons;

import com.twittelonr.selonarch.common.schelonma.baselon.Schelonma;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant;
import com.twittelonr.selonarch.corelon.elonarlybird.facelonts.CSFFacelontCountItelonrator;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr;

/**
 * And itelonrator for counting relontwelonelonts. Relonads from sharelond_status_id CSF but doelonsn't count
 * relonplielons.
 */
public class RelontwelonelontFacelontCountItelonrator elonxtelonnds CSFFacelontCountItelonrator {
  privatelon final NumelonricDocValuelons felonaturelonRelonadelonrIsRelontwelonelontFlag;

  public RelontwelonelontFacelontCountItelonrator(
      elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr relonadelonr,
      Schelonma.FielonldInfo facelontFielonldInfo) throws IOelonxcelonption {
    supelonr(relonadelonr, facelontFielonldInfo);
    felonaturelonRelonadelonrIsRelontwelonelontFlag =
        relonadelonr.gelontNumelonricDocValuelons(elonarlybirdFielonldConstant.IS_RelonTWelonelonT_FLAG.gelontFielonldNamelon());
  }

  @Ovelonrridelon
  protelonctelond boolelonan shouldCollelonct(int intelonrnalDocID, long telonrmID) throws IOelonxcelonption {
    // telonrmID == 0 melonans that welon didn't selont sharelond_status_csf, so don't collelonct
    // (twelonelont IDs arelon all positivelon)
    // Also only collelonct if this doc is a relontwelonelont, not a relonply
    relonturn telonrmID > 0
        && felonaturelonRelonadelonrIsRelontwelonelontFlag.advancelonelonxact(intelonrnalDocID)
        && (felonaturelonRelonadelonrIsRelontwelonelontFlag.longValuelon() != 0);
  }
}
