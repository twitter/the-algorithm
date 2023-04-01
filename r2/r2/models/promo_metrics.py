# The contents of this file are subject to the Common Public Attribution
# License Version 1.0. (the "License"); you may not use this file except in
# compliance with the License. You may obtain a copy of the License at
# http://code.reddit.com/LICENSE. The License is based on the Mozilla Public
# License Version 1.1, but Sections 14 and 15 have been added to cover use of
# software over a computer network and provide for limited attribution for the
# Original Developer. In addition, Exhibit A has been modified to be consistent
# with Exhibit B.
#
# Software distributed under the License is distributed on an "AS IS" basis,
# WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for
# the specific language governing rights and limitations under the License.
#
# The Original Code is reddit.
#
# The Original Developer is the Initial Developer.  The Initial Developer of
# the Original Code is reddit Inc.
#
# All portions of the code written by reddit are Copyright (c) 2006-2015 reddit
# Inc. All Rights Reserved.
###############################################################################

from itertools import product

from pycassa.types import IntegerType

from r2.lib.db import tdb_cassandra
from r2.lib.utils import tup


class PromoMetrics(tdb_cassandra.View):
    '''
    Cassandra data store for promotion metrics. Used for inventory prediction.

    Usage:
      # set metric value for many subreddits at once
      > PromoMetrics.set('min_daily_pageviews.GET_listing',
                          {'funny': 63432, 'pics': 48829, 'books': 4})

      # get metric value for one subreddit
      > res = PromoMetrics.get('min_daily_pageviews.GET_listing', 'funny')
      {'funny': 1234}

      # get metric value for many subreddits
      > res = PromoMetrics.get('min_daily_pageviews.GET_listing',
                               ['funny', 'pics'])
      {'funny':1234, 'pics':4321}

      # get metric values for all subreddits
      > res = PromoMetrics.get('min_daily_pageviews.GET_listing')
    '''
    _use_db = True
    _value_type = 'int'
    _fetch_all_columns = True

    @classmethod
    def get(cls, metric_name, sr_names=None):
        sr_names = tup(sr_names)
        try:
            metric = cls._byID(metric_name, properties=sr_names)
            return metric._values()  # might have additional values
        except tdb_cassandra.NotFound:
            return {}

    @classmethod
    def set(cls, metric_name, values_by_sr):
        cls._set_values(metric_name, values_by_sr)


class LocationPromoMetrics(tdb_cassandra.View):
    _use_db = True
    _write_consistency_level = tdb_cassandra.CL.QUORUM
    _read_consistency_level = tdb_cassandra.CL.ONE
    _extra_schema_creation_args = {
        "default_validation_class": IntegerType(),
    }

    @classmethod
    def _rowkey(cls, location):
        fields = [location.country, location.region, location.metro]
        return '-'.join(map(lambda field: field or '', fields))

    @classmethod
    def _column_name(cls, sr):
        return sr.name

    @classmethod
    def get(cls, srs, locations):
        srs, srs_is_single = tup(srs, ret_is_single=True)
        locations, locations_is_single = tup(locations, ret_is_single=True)
        is_single = srs_is_single and locations_is_single

        rowkeys = {location: cls._rowkey(location) for location in locations}
        columns = {sr: cls._column_name(sr) for sr in srs}
        rcl = cls._read_consistency_level
        metrics = cls._cf.multiget(rowkeys.values(), columns.values(),
                                   read_consistency_level=rcl)
        ret = {}

        for sr, location in product(srs, locations):
            rowkey = rowkeys[location]
            column = columns[sr]
            impressions = metrics.get(rowkey, {}).get(column, 0)
            ret[(sr, location)] = impressions

        if is_single:
            return ret.values()[0]
        else:
            return ret

    @classmethod
    def set(cls, metrics):
        wcl = cls._write_consistency_level
        with cls._cf.batch(write_consistency_level=wcl) as b:
            for location, sr, impressions in metrics:
                rowkey = cls._rowkey(location)
                column = {cls._column_name(sr): impressions}
                b.insert(rowkey, column)
