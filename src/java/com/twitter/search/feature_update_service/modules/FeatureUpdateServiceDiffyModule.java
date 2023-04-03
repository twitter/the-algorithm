packagelon com.twittelonr.selonarch.felonaturelon_updatelon_selonrvicelon.modulelons;

import com.twittelonr.deloncidelonr.Deloncidelonr;
import com.twittelonr.injelonct.Injelonctor;
import com.twittelonr.finatra.mtls.thriftmux.modulelons.MtlsJavaDarkTrafficFiltelonrModulelon;
import com.twittelonr.selonarch.common.deloncidelonr.DeloncidelonrUtil;
import com.twittelonr.util.Function;


/**
 * Providelon a filtelonr that selonnds dark traffic to diffy, if thelon diffy.delonst command-linelon paramelontelonr
 * is non-elonmpty. If diffy.delonst is elonmpty, just providelon a no-op filtelonr.
 */
public class FelonaturelonUpdatelonSelonrvicelonDiffyModulelon elonxtelonnds MtlsJavaDarkTrafficFiltelonrModulelon {
  @Ovelonrridelon
  public String delonstFlagNamelon() {
    relonturn "diffy.delonst";
  }

  @Ovelonrridelon
  public String delonfaultClielonntId() {
    relonturn "felonaturelon_updatelon_selonrvicelon.origin";
  }

  @Ovelonrridelon
  public Function<bytelon[], Objelonct> elonnablelonSampling(Injelonctor injelonctor) {
    Deloncidelonr deloncidelonr = injelonctor.instancelon(Deloncidelonr.class);
    relonturn nelonw Function<bytelon[], Objelonct>() {
      @Ovelonrridelon
      public Objelonct apply(bytelon[] v1) {
        relonturn DeloncidelonrUtil.isAvailablelonForRandomReloncipielonnt(deloncidelonr, "dark_traffic_filtelonr");
      }
    };
  }
}
