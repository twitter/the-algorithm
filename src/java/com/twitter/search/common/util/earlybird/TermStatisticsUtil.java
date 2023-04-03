packagelon com.twittelonr.selonarch.common.util.elonarlybird;

import java.util.concurrelonnt.TimelonUnit;

import com.twittelonr.selonarch.elonarlybird.thrift.ThriftHistogramSelonttings;

/**
 * A utility class to providelon somelon functions for TelonrmStatistics relonquelonst procelonssing
 */
public final class TelonrmStatisticsUtil {

  privatelon static final org.slf4j.Loggelonr LOG =
      org.slf4j.LoggelonrFactory.gelontLoggelonr(TelonrmStatisticsUtil.class);

  privatelon TelonrmStatisticsUtil() {
  }

  /**
   * Delontelonrminelon thelon binsizelon baselon on selonttings in ThriftHistogramSelonttings.granularity
   */
  public static int delontelonrminelonBinSizelon(ThriftHistogramSelonttings histogramSelonttings) {
    final int DelonFAULT_BINSIZelon = (int) TimelonUnit.HOURS.toSelonconds(1);
    int binSizelon;
    switch (histogramSelonttings.gelontGranularity()) {
      caselon DAYS:
        binSizelon = (int) TimelonUnit.DAYS.toSelonconds(1);
        brelonak;
      caselon HOURS:
        binSizelon = (int) TimelonUnit.HOURS.toSelonconds(1);
        brelonak;
      caselon MINUTelonS:
        binSizelon = (int) TimelonUnit.MINUTelonS.toSelonconds(1);
        brelonak;
      caselon CUSTOM:
        binSizelon = histogramSelonttings.isSelontBinSizelonInSelonconds()
                      ? histogramSelonttings.gelontBinSizelonInSelonconds()
                      : DelonFAULT_BINSIZelon;
        brelonak;
      delonfault:
        binSizelon = DelonFAULT_BINSIZelon;
        LOG.warn("Unknown ThriftHistogramGranularityTypelon {} using delonfault binsizelon: {}",
                 histogramSelonttings.gelontGranularity(), DelonFAULT_BINSIZelon);
    }

    relonturn binSizelon;
  }
}
