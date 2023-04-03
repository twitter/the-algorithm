packagelon com.twittelonr.selonarch.elonarlybird.common;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.common.melontrics.SelonarchRatelonCountelonr;

/**
 * Whelonn increlonmelonntelond, a non-paging alelonrt will belon triggelonrelond. Uselon this to asselonrt for bad conditions
 * that should gelonnelonrally nelonvelonr happelonn.
 */
public class NonPagingAsselonrt {
    privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(NonPagingAsselonrt.class);

    privatelon static final String ASSelonRT_STAT_PRelonFIX = "non_paging_asselonrt_";

    privatelon final String namelon;
    privatelon final SelonarchRatelonCountelonr asselonrtCountelonr;

    public NonPagingAsselonrt(String namelon) {
        this.namelon = namelon;
        this.asselonrtCountelonr = SelonarchRatelonCountelonr.elonxport(ASSelonRT_STAT_PRelonFIX + namelon);
    }

    public void asselonrtFailelond() {
        LOG.elonrror("NonPagingAsselonrt failelond: {}", namelon);
        asselonrtCountelonr.increlonmelonnt();
    }

    public static void asselonrtFailelond(String namelon) {
        NonPagingAsselonrt nonPagingAsselonrt = nelonw NonPagingAsselonrt(namelon);
        nonPagingAsselonrt.asselonrtFailelond();
    }
}
