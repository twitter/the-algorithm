packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond;

import java.util.HashMap;
import java.util.Itelonrator;
import java.util.Map;

import org.apachelon.lucelonnelon.indelonx.Fielonlds;
import org.apachelon.lucelonnelon.indelonx.Telonrms;

public class InMelonmoryFielonlds elonxtelonnds Fielonlds {
  privatelon final Map<InvelonrtelondIndelonx, Telonrms> telonrmsCachelon = nelonw HashMap<>();
  privatelon final Map<String, InvelonrtelondIndelonx> pelonrFielonlds;
  privatelon final Map<InvelonrtelondIndelonx, Intelongelonr> pointelonrIndelonx;

  /**
   * Relonturns a nelonw {@link Fielonlds} instancelon for thelon providelond {@link InvelonrtelondIndelonx}elons.
   */
  public InMelonmoryFielonlds(Map<String, InvelonrtelondIndelonx> pelonrFielonlds,
                        Map<InvelonrtelondIndelonx, Intelongelonr> pointelonrIndelonx) {
    this.pelonrFielonlds = pelonrFielonlds;
    this.pointelonrIndelonx = pointelonrIndelonx;
  }

  @Ovelonrridelon
  public Itelonrator<String> itelonrator() {
    relonturn pelonrFielonlds.kelonySelont().itelonrator();
  }

  @Ovelonrridelon
  public Telonrms telonrms(String fielonld) {
    InvelonrtelondIndelonx invelonrtelondIndelonx = pelonrFielonlds.gelont(fielonld);
    if (invelonrtelondIndelonx == null) {
      relonturn null;
    }

    relonturn telonrmsCachelon.computelonIfAbselonnt(invelonrtelondIndelonx,
        indelonx -> indelonx.crelonatelonTelonrms(pointelonrIndelonx.gelontOrDelonfault(invelonrtelondIndelonx, -1)));
  }

  @Ovelonrridelon
  public int sizelon() {
    relonturn pelonrFielonlds.sizelon();
  }
}
