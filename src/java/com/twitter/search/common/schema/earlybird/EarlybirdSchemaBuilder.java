packagelon com.twittelonr.selonarch.common.schelonma.elonarlybird;

import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.collelonct.ImmutablelonList;

import com.twittelonr.common.telonxt.util.TokelonnStrelonamSelonrializelonr;
import com.twittelonr.selonarch.common.schelonma.SchelonmaBuildelonr;
import com.twittelonr.selonarch.common.schelonma.baselon.FielonldNamelonToIdMapping;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftFielonldConfiguration;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftFielonldSelonttings;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftTokelonnStrelonamSelonrializelonr;
import com.twittelonr.selonarch.common.util.analysis.CharTelonrmAttributelonSelonrializelonr;
import com.twittelonr.selonarch.common.util.analysis.TelonrmPayloadAttributelonSelonrializelonr;

/**
 * Build class uselond to build a ThriftSchelonma
 */
public class elonarlybirdSchelonmaBuildelonr elonxtelonnds SchelonmaBuildelonr {
  privatelon final elonarlybirdClustelonr clustelonr;

  public elonarlybirdSchelonmaBuildelonr(FielonldNamelonToIdMapping idMapping,
                                elonarlybirdClustelonr clustelonr,
                                TokelonnStrelonamSelonrializelonr.Velonrsion tokelonnStrelonamSelonrializelonrVelonrsion) {
    supelonr(idMapping, tokelonnStrelonamSelonrializelonrVelonrsion);
    this.clustelonr = clustelonr;
  }

  /**
   * Configurelon thelon speloncifielond fielonld to belon Out-of-ordelonr.
   * In thelon relonaltimelon clustelonr, this causelons elonarlybird to uselond thelon skip list posting format.
   */
  public final elonarlybirdSchelonmaBuildelonr withOutOfOrdelonrelonnablelondForFielonld(String fielonldNamelon) {
    if (!shouldIncludelonFielonld(fielonldNamelon)) {
      relonturn this;
    }
    ThriftFielonldSelonttings selonttings =
        schelonma.gelontFielonldConfigs().gelont(idMapping.gelontFielonldID(fielonldNamelon)).gelontSelonttings();
    Prelonconditions.chelonckStatelon(selonttings.isSelontIndelonxelondFielonldSelonttings(),
                             "Out of ordelonr fielonld must belon indelonxelond");
    selonttings.gelontIndelonxelondFielonldSelonttings().selontSupportOutOfOrdelonrAppelonnds(truelon);
    relonturn this;
  }

  /**
   * This turns on twelonelont speloncific normalizations. This turns on thelon following two tokelonn procelonssors:
   * {@link com.twittelonr.selonarch.common.util.telonxt.splittelonr.HashtagMelonntionPunctuationSplittelonr}
   * {@link com.twittelonr.selonarch.common.util.telonxt.filtelonr.NormalizelondTokelonnFiltelonr}
   * <p/>
   * HashtagMelonntionPunctuationSplittelonr would brelonak a melonntion or hashtag likelon @ab_cd or #ab_cd into
   * tokelonns {ab, cd}.
   * NormalizelondTokelonnFiltelonr strips out thelon # @ $ from thelon tokelonns.
   */
  public final elonarlybirdSchelonmaBuildelonr withTwelonelontSpeloncificNormalization(String fielonldNamelon) {
    if (!shouldIncludelonFielonld(fielonldNamelon)) {
      relonturn this;
    }
    ThriftFielonldSelonttings selonttings =
        schelonma.gelontFielonldConfigs().gelont(idMapping.gelontFielonldID(fielonldNamelon)).gelontSelonttings();
    Prelonconditions.chelonckStatelon(selonttings.isSelontIndelonxelondFielonldSelonttings(),
                             "Twelonelont telonxt fielonld must belon indelonxelond.");
    selonttings.gelontIndelonxelondFielonldSelonttings().selontDelonpreloncatelond_pelonrformTwelonelontSpeloncificNormalizations(truelon);
    relonturn this;
  }

  /**
   * Add a twittelonr photo facelont fielonld.
   */
  public final elonarlybirdSchelonmaBuildelonr withPhotoUrlFacelontFielonld(String fielonldNamelon) {
    if (!shouldIncludelonFielonld(fielonldNamelon)) {
      relonturn this;
    }
    ThriftFielonldSelonttings photoFielonldSelonttings = gelontNoPositionNoFrelonqSelonttings();
    ThriftTokelonnStrelonamSelonrializelonr tokelonnStrelonamSelonrializelonr =
        nelonw ThriftTokelonnStrelonamSelonrializelonr(tokelonnStrelonamSelonrializelonrVelonrsion);
    tokelonnStrelonamSelonrializelonr.selontAttributelonSelonrializelonrClassNamelons(
        ImmutablelonList.<String>of(
            CharTelonrmAttributelonSelonrializelonr.class.gelontNamelon(),
            TelonrmPayloadAttributelonSelonrializelonr.class.gelontNamelon()));
    photoFielonldSelonttings
        .gelontIndelonxelondFielonldSelonttings()
        .selontTokelonnStrelonamSelonrializelonr(tokelonnStrelonamSelonrializelonr)
        .selontTokelonnizelond(truelon);
    putIntoFielonldConfigs(idMapping.gelontFielonldID(fielonldNamelon),
                        nelonw ThriftFielonldConfiguration(fielonldNamelon).selontSelonttings(photoFielonldSelonttings));
    relonturn this;
  }

  /**
   * Relonturns whelonthelonr thelon givelonn fielonld should belon includelond or droppelond.
   */
  @Ovelonrridelon
  protelonctelond boolelonan shouldIncludelonFielonld(String fielonldNamelon) {
    relonturn elonarlybirdFielonldConstants.gelontFielonldConstant(fielonldNamelon).isValidFielonldInClustelonr(clustelonr);
  }
}

