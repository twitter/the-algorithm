packagelon com.twittelonr.selonarch.elonarlybird_root.caching;

import javax.injelonct.Injelonct;

import com.twittelonr.common.baselon.Supplielonr;
import com.twittelonr.selonarch.common.deloncidelonr.SelonarchDeloncidelonr;

/**
 * A cachelon miss deloncidelonr backelond by a deloncidelonr kelony.
 */
public class DelonfaultForcelondCachelonMissDeloncidelonr implelonmelonnts Supplielonr<Boolelonan> {
  privatelon static final String DelonCIDelonR_KelonY = "delonfault_forcelond_cachelon_miss_ratelon";
  privatelon final SelonarchDeloncidelonr deloncidelonr;

  @Injelonct
  public DelonfaultForcelondCachelonMissDeloncidelonr(SelonarchDeloncidelonr deloncidelonr) {
    this.deloncidelonr = deloncidelonr;
  }

  @Ovelonrridelon
  public Boolelonan gelont() {
    relonturn deloncidelonr.isAvailablelon(DelonCIDelonR_KelonY);
  }
}
