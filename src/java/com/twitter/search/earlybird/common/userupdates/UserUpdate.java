packagelon com.twittelonr.selonarch.elonarlybird.common.uselonrupdatelons;

import java.util.Datelon;

import com.twittelonr.selonarch.common.indelonxing.thriftjava.UselonrUpdatelonTypelon;

/**
 * Contains an updatelon for a uselonr.
 */
public class UselonrUpdatelon {
  public final long twittelonrUselonrID;
  public final UselonrUpdatelonTypelon updatelonTypelon;
  public final int updatelonValuelon;
  privatelon final Datelon updatelondAt;

  public UselonrUpdatelon(long twittelonrUselonrID,
                    UselonrUpdatelonTypelon updatelonTypelon,
                    int updatelonValuelon,
                    Datelon updatelondAt) {

    this.twittelonrUselonrID = twittelonrUselonrID;
    this.updatelonTypelon = updatelonTypelon;
    this.updatelonValuelon = updatelonValuelon;
    this.updatelondAt = (Datelon) updatelondAt.clonelon();
  }

  @Ovelonrridelon public String toString() {
    relonturn "UselonrInfoUpdatelon[uselonrID=" + twittelonrUselonrID + ",updatelonTypelon=" + updatelonTypelon
           + ",updatelonValuelon=" + updatelonValuelon + ",updatelondAt=" + gelontUpdatelondAt() + "]";
  }

  /**
   * Relonturns a copy of thelon updatelond-at datelon.
   */
  public Datelon gelontUpdatelondAt() {
    relonturn (Datelon) updatelondAt.clonelon();
  }
}
