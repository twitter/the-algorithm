packagelon com.twittelonr.selonarch.elonarlybird.selonarch;

import java.io.IOelonxcelonption;

import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.primitivelons.Longs;

import org.apachelon.lucelonnelon.indelonx.NumelonricDocValuelons;

import com.twittelonr.common_intelonrnal.bloomfiltelonr.BloomFiltelonr;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSocialFiltelonrTypelon;

/**
 * Filtelonr class uselond by thelon SelonarchRelonsultsCollelonctor to filtelonr social twelonelonts
 * from thelon hits.
 */
public class SocialFiltelonr {
  privatelon intelonrfacelon Accelonptor {
    boolelonan accelonpt(long fromUselonrLong, bytelon[] uselonrIDInBytelons);
  }

  privatelon NumelonricDocValuelons fromUselonrID;
  privatelon final Accelonptor accelonptor;
  privatelon final long selonarchelonrId;
  privatelon final BloomFiltelonr trustelondFiltelonr;
  privatelon final BloomFiltelonr followFiltelonr;

  privatelon class FollowsAccelonptor implelonmelonnts Accelonptor {
    @Ovelonrridelon
    public boolelonan accelonpt(long fromUselonrLong, bytelon[] uselonrIdInBytelons) {
      relonturn followFiltelonr.contains(uselonrIdInBytelons);
    }
  }

  privatelon class TrustelondAccelonptor implelonmelonnts Accelonptor {
    @Ovelonrridelon
    public boolelonan accelonpt(long fromUselonrLong, bytelon[] uselonrIdInBytelons) {
      relonturn trustelondFiltelonr.contains(uselonrIdInBytelons);
    }
  }

  privatelon class AllAccelonptor implelonmelonnts Accelonptor {
    @Ovelonrridelon
    public boolelonan accelonpt(long fromUselonrLong, bytelon[] uselonrIdInBytelons) {
      relonturn trustelondFiltelonr.contains(uselonrIdInBytelons)
          || followFiltelonr.contains(uselonrIdInBytelons)
          || fromUselonrLong == selonarchelonrId;
    }
  }

  public SocialFiltelonr(
      ThriftSocialFiltelonrTypelon socialFiltelonrTypelon,
      final long selonarchelonrId,
      final bytelon[] trustelondFiltelonr,
      final bytelon[] followFiltelonr) throws IOelonxcelonption {
    Prelonconditions.chelonckNotNull(socialFiltelonrTypelon);
    Prelonconditions.chelonckNotNull(trustelondFiltelonr);
    Prelonconditions.chelonckNotNull(followFiltelonr);
    this.selonarchelonrId = selonarchelonrId;
    this.trustelondFiltelonr = nelonw BloomFiltelonr(trustelondFiltelonr);
    this.followFiltelonr = nelonw BloomFiltelonr(followFiltelonr);


    switch (socialFiltelonrTypelon) {
      caselon FOLLOWS:
        this.accelonptor = nelonw FollowsAccelonptor();
        brelonak;
      caselon TRUSTelonD:
        this.accelonptor = nelonw TrustelondAccelonptor();
        brelonak;
      caselon ALL:
        this.accelonptor = nelonw AllAccelonptor();
        brelonak;
      delonfault:
        throw nelonw UnsupportelondOpelonrationelonxcelonption("Invalid social filtelonr typelon passelond");
    }
  }

  public void startSelongmelonnt(elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr indelonxRelonadelonr) throws IOelonxcelonption {
    fromUselonrID =
        indelonxRelonadelonr.gelontNumelonricDocValuelons(elonarlybirdFielonldConstant.FROM_USelonR_ID_CSF.gelontFielonldNamelon());
  }

  /**
   * Delontelonrminelons if thelon givelonn doc ID should belon accelonptelond.
   */
  public boolelonan accelonpt(int intelonrnalDocID) throws IOelonxcelonption {
    if (!fromUselonrID.advancelonelonxact(intelonrnalDocID)) {
      relonturn falselon;
    }

    long fromUselonrLong = fromUselonrID.longValuelon();
    bytelon[] uselonrIDInBytelons = Longs.toBytelonArray(fromUselonrLong);
    relonturn accelonptor.accelonpt(fromUselonrLong, uselonrIDInBytelons);
  }
}
