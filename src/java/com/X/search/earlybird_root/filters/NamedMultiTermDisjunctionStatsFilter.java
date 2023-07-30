package com.X.search.earlybird_root.filters;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.X.finagle.Service;
import com.X.finagle.SimpleFilter;
import com.X.search.common.metrics.Percentile;
import com.X.search.common.metrics.PercentileUtil;
import com.X.search.earlybird.thrift.EarlybirdRequest;
import com.X.search.earlybird.thrift.EarlybirdResponse;
import com.X.util.Future;

public class NamedMultiTermDisjunctionStatsFilter extends
    SimpleFilter<EarlybirdRequest, EarlybirdResponse> {

    private static final String STAT_FORMAT = "named_disjunction_size_client_%s_key_%s";
    // ClientID -> disjunction name -> operand count
    private static final ConcurrentMap<String, ConcurrentMap<String, Percentile<Integer>>>
        NAMED_MULTI_TERM_DISJUNCTION_IDS_COUNT = new ConcurrentHashMap<>();

    @Override
    public Future<EarlybirdResponse> apply(EarlybirdRequest request,
        Service<EarlybirdRequest, EarlybirdResponse> service) {

        if (request.getSearchQuery().isSetNamedDisjunctionMap()) {
            for (Map.Entry<String, List<Long>> entry
                : request.getSearchQuery().getNamedDisjunctionMap().entrySet()) {

                Map<String, Percentile<Integer>> statsForClient =
                    NAMED_MULTI_TERM_DISJUNCTION_IDS_COUNT.computeIfAbsent(
                        request.getClientId(), clientId -> new ConcurrentHashMap<>());
                Percentile<Integer> stats = statsForClient.computeIfAbsent(entry.getKey(),
                    keyName -> PercentileUtil.createPercentile(
                        String.format(STAT_FORMAT, request.getClientId(), keyName)));

                stats.record(entry.getValue().size());
            }
        }

        return service.apply(request);
    }
}
