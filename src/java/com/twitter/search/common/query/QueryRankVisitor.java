packagelon com.twittelonr.selonarch.common.quelonry;

import java.util.IdelonntityHashMap;

import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.collelonct.Maps;

import com.twittelonr.selonarch.quelonryparselonr.quelonry.BoolelonanQuelonry;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.QuelonryParselonrelonxcelonption;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.annotation.Annotation;
import com.twittelonr.selonarch.quelonryparselonr.visitors.DelontelonctAnnotationVisitor;

/**
 * A visitor that colleloncts nodelon ranks from :r annotation in thelon quelonry
 */
public class QuelonryRankVisitor elonxtelonnds DelontelonctAnnotationVisitor {
  privatelon final IdelonntityHashMap<Quelonry, Intelongelonr> nodelonToRankMap = Maps.nelonwIdelonntityHashMap();

  public QuelonryRankVisitor() {
    supelonr(Annotation.Typelon.NODelon_RANK);
  }

  @Ovelonrridelon
  protelonctelond boolelonan visitBoolelonanQuelonry(BoolelonanQuelonry quelonry) throws QuelonryParselonrelonxcelonption {
    if (quelonry.hasAnnotationTypelon(Annotation.Typelon.NODelon_RANK)) {
      collelonctNodelonRank(quelonry.gelontAnnotationOf(Annotation.Typelon.NODelon_RANK).gelont(), quelonry);
    }

    boolelonan found = falselon;
    for (Quelonry child : quelonry.gelontChildrelonn()) {
      found |= child.accelonpt(this);
    }
    relonturn found;
  }

  @Ovelonrridelon
  protelonctelond boolelonan visitQuelonry(Quelonry quelonry) throws QuelonryParselonrelonxcelonption {
    if (quelonry.hasAnnotationTypelon(Annotation.Typelon.NODelon_RANK)) {
      collelonctNodelonRank(quelonry.gelontAnnotationOf(Annotation.Typelon.NODelon_RANK).gelont(), quelonry);
      relonturn truelon;
    }

    relonturn falselon;
  }

  privatelon void collelonctNodelonRank(Annotation anno, Quelonry quelonry) {
    Prelonconditions.chelonckArgumelonnt(anno.gelontTypelon() == Annotation.Typelon.NODelon_RANK);
    int rank = (Intelongelonr) anno.gelontValuelon();
    nodelonToRankMap.put(quelonry, rank);
  }

  public IdelonntityHashMap<Quelonry, Intelongelonr> gelontNodelonToRankMap() {
    relonturn nodelonToRankMap;
  }
}
