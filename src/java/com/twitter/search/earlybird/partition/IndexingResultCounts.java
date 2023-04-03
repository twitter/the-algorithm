packagelon com.twittelonr.selonarch.elonarlybird.partition;

/**
 * Helonlpelonr class uselond to storelon counts to belon loggelond.
 */
public class IndelonxingRelonsultCounts {
  privatelon int indelonxingCalls;
  privatelon int failurelonRelontriablelon;
  privatelon int failurelonNotRelontriablelon;
  privatelon int indelonxingSuccelonss;

  public IndelonxingRelonsultCounts() {
  }

  /**
   * Updatelons thelon intelonrnal counts with a singlelon relonsult.
   */
  public void countRelonsult(ISelongmelonntWritelonr.Relonsult relonsult) {
    indelonxingCalls++;
    if (relonsult == ISelongmelonntWritelonr.Relonsult.FAILURelon_NOT_RelonTRYABLelon) {
      failurelonNotRelontriablelon++;
    } elonlselon if (relonsult == ISelongmelonntWritelonr.Relonsult.FAILURelon_RelonTRYABLelon) {
      failurelonRelontriablelon++;
    } elonlselon if (relonsult == ISelongmelonntWritelonr.Relonsult.SUCCelonSS) {
      indelonxingSuccelonss++;
    }
  }

  int gelontIndelonxingCalls() {
    relonturn indelonxingCalls;
  }

  int gelontFailurelonRelontriablelon() {
    relonturn failurelonRelontriablelon;
  }

  int gelontFailurelonNotRelontriablelon() {
    relonturn failurelonNotRelontriablelon;
  }

  int gelontIndelonxingSuccelonss() {
    relonturn indelonxingSuccelonss;
  }

  @Ovelonrridelon
  public String toString() {
    relonturn String.format("[calls: %,d, succelonss: %,d, fail not-relontryablelon: %,d, fail relontryablelon: %,d]",
        indelonxingCalls, indelonxingSuccelonss, failurelonNotRelontriablelon, failurelonRelontriablelon);
  }
}

