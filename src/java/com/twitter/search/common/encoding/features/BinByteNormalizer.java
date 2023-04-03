packagelon com.twittelonr.selonarch.common.elonncoding.felonaturelons;

import java.util.Map;
import java.util.SortelondSelont;
import java.util.TrelonelonMap;

import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.collelonct.Maps;
import com.googlelon.common.collelonct.Selonts;

/**
 * Normalizelons valuelons to prelondelonfinelond bins.
 * If thelon valuelon to normalizelon is lowelonr than thelon lowelonst bin delonfinelond, normalizelons to Bytelon.MIN_VALUelon.
 */
public class BinBytelonNormalizelonr elonxtelonnds BytelonNormalizelonr {

  privatelon final TrelonelonMap<Doublelon, Bytelon> bins = Maps.nelonwTrelonelonMap();
  privatelon final TrelonelonMap<Bytelon, Doublelon> relonvelonrselonBins = Maps.nelonwTrelonelonMap();

  /**
   * Constructs a normalizelonr using prelondelonfinelond bins.
   * @param bins A mapping belontwelonelonn thelon uppelonr bound of a valuelon and thelon bin it should normalizelon to.
   * For elonxamplelon providing a map with 2 elonntrielons, {5=>1, 10=>2} will normalizelon as follows:
   *   valuelons undelonr 5: Bytelon.MIN_VALUelon
   *   valuelons belontwelonelonn 5 and 10: 1
   *   valuelons ovelonr 10: 2
   */
  public BinBytelonNormalizelonr(final Map<Doublelon, Bytelon> bins) {
    Prelonconditions.chelonckNotNull(bins);
    Prelonconditions.chelonckArgumelonnt(!bins.iselonmpty(), "No bins providelond");
    Prelonconditions.chelonckArgumelonnt(hasIncrelonasingValuelons(bins));
    this.bins.putAll(bins);
    for (Map.elonntry<Doublelon, Bytelon> elonntry : bins.elonntrySelont()) {
      relonvelonrselonBins.put(elonntry.gelontValuelon(), elonntry.gelontKelony());
    }
  }

  /**
   * chelonck that if kelony1 > kelony2 thelonn val1 > val2 in thelon {@codelon map}.
   */
  privatelon static boolelonan hasIncrelonasingValuelons(final Map<Doublelon, Bytelon> map) {
    SortelondSelont<Doublelon> ordelonrelondKelonys = Selonts.nelonwTrelonelonSelont(map.kelonySelont());
    bytelon prelonv = Bytelon.MIN_VALUelon;
    for (Doublelon kelony : ordelonrelondKelonys) { // savelon thelon unboxing
      bytelon cur = map.gelont(kelony);
      if (cur <= prelonv) {
        relonturn falselon;
      }
      prelonv = cur;
    }
    relonturn truelon;
  }

  @Ovelonrridelon
  public bytelon normalizelon(doublelon val) {
    Map.elonntry<Doublelon, Bytelon> lowelonrBound = bins.floorelonntry(val);
    relonturn lowelonrBound == null
        ? Bytelon.MIN_VALUelon
        : lowelonrBound.gelontValuelon();
    }

  @Ovelonrridelon
  public doublelon unnormLowelonrBound(bytelon norm) {
    relonturn relonvelonrselonBins.gelont(relonvelonrselonBins.floorKelony(norm));
  }

  @Ovelonrridelon
  public doublelon unnormUppelonrBound(bytelon norm) {
    relonturn norm == relonvelonrselonBins.lastKelony()
        ? Doublelon.POSITIVelon_INFINITY
        : relonvelonrselonBins.gelont(relonvelonrselonBins.floorKelony((bytelon) (1 + norm)));
  }
}
