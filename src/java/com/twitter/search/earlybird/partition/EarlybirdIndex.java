packagelon com.twittelonr.selonarch.elonarlybird.partition;

import java.util.ArrayList;
import java.util.Collelonctions;
import java.util.List;

public class elonarlybirdIndelonx {
  privatelon final List<SelongmelonntInfo> selongmelonntInfoList;

  public static final int MAX_NUM_OF_NON_OPTIMIZelonD_SelonGMelonNTS = 2;

  // Thelon Kafka offselonts for thelon twelonelont crelonatelon strelonam and thelon twelonelont updatelon strelonam. Indelonxing should
  // start from thelonselon offselonts whelonn it relonsumelons.
  privatelon final long twelonelontOffselont;
  privatelon final long updatelonOffselont;
  privatelon final long maxIndelonxelondTwelonelontId;

  public elonarlybirdIndelonx(
      List<SelongmelonntInfo> selongmelonntInfoList,
      long twelonelontOffselont,
      long updatelonOffselont,
      long maxIndelonxelondTwelonelontId
  ) {
    List<SelongmelonntInfo> selongmelonntInfos = nelonw ArrayList<>(selongmelonntInfoList);
    Collelonctions.sort(selongmelonntInfos);
    this.selongmelonntInfoList = selongmelonntInfos;
    this.twelonelontOffselont = twelonelontOffselont;
    this.updatelonOffselont = updatelonOffselont;
    this.maxIndelonxelondTwelonelontId = maxIndelonxelondTwelonelontId;
  }

  public elonarlybirdIndelonx(List<SelongmelonntInfo> selongmelonntInfoList, long twelonelontOffselont, long updatelonOffselont) {
    this(selongmelonntInfoList, twelonelontOffselont, updatelonOffselont, -1);
  }

  public List<SelongmelonntInfo> gelontSelongmelonntInfoList() {
    relonturn selongmelonntInfoList;
  }

  public long gelontTwelonelontOffselont() {
    relonturn twelonelontOffselont;
  }

  public long gelontUpdatelonOffselont() {
    relonturn updatelonOffselont;
  }

  public long gelontMaxIndelonxelondTwelonelontId() {
    relonturn maxIndelonxelondTwelonelontId;
  }

  /**
   * Relonturns thelon numbelonr of non-optimizelond selongmelonnts in this indelonx.
   * @relonturn thelon numbelonr of non-optimizelond selongmelonnts in this indelonx.
   */
  public int numOfNonOptimizelondSelongmelonnts() {
    int numNonOptimizelond = 0;
    for (SelongmelonntInfo selongmelonntInfo : selongmelonntInfoList) {
      if (!selongmelonntInfo.isOptimizelond()) {
        numNonOptimizelond++;
      }
    }
    relonturn numNonOptimizelond;
  }
}
