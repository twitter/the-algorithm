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

import datetime
import calendar
import os
from time import sleep
import urllib

from boto.s3.connection import S3Connection
from boto.emr.connection import EmrConnection
from boto.exception import S3ResponseError
from pylons import app_globals as g
from sqlalchemy.exc import DataError

from r2.lib.emr_helpers import (EmrException, terminate_jobflow,
    modify_slave_count)
from r2.lib.s3_helpers import get_text_from_s3, s3_key_exists, copy_to_s3
from r2.lib.traffic.emr_traffic import (extract_hour, aggregate_interval,
        coalesce_interval)
from r2.lib.utils import tup
from r2.models.traffic import (SitewidePageviews, PageviewsBySubreddit,
        PageviewsBySubredditAndPath, PageviewsByLanguage,
        ClickthroughsByCodename, TargetedClickthroughsByCodename,
        AdImpressionsByCodename, TargetedImpressionsByCodename)


RAW_LOG_DIR = g.RAW_LOG_DIR
PROCESSED_DIR = g.PROCESSED_DIR
AGGREGATE_DIR = g.AGGREGATE_DIR
AWS_LOG_DIR = g.AWS_LOG_DIR

# the "or None" business is so that a blank string becomes None to cause boto
# to look for credentials in other places.
s3_connection = S3Connection(g.TRAFFIC_ACCESS_KEY or None,
                             g.TRAFFIC_SECRET_KEY or None)
emr_connection = EmrConnection(g.TRAFFIC_ACCESS_KEY or None,
                               g.TRAFFIC_SECRET_KEY or None)

traffic_categories = (SitewidePageviews, PageviewsBySubreddit,
                      PageviewsBySubredditAndPath, PageviewsByLanguage,
                      ClickthroughsByCodename, TargetedClickthroughsByCodename,
                      AdImpressionsByCodename, TargetedImpressionsByCodename)

traffic_subdirectories = {
    SitewidePageviews: 'sitewide',
    PageviewsBySubreddit: 'subreddit',
    PageviewsBySubredditAndPath: 'srpath',
    PageviewsByLanguage: 'lang',
    ClickthroughsByCodename: 'clicks',
    TargetedClickthroughsByCodename: 'clicks_targeted',
    AdImpressionsByCodename: 'thing',
    TargetedImpressionsByCodename: 'thingtarget',
}


def _get_processed_path(basedir, interval, category_cls, filename):
    return os.path.join(basedir, interval,
                        traffic_subdirectories[category_cls], filename)


def get_aggregate(interval, category_cls):
    """Return the aggregate output file from S3."""
    part = 0
    data = {}

    while True:
        path = _get_processed_path(AGGREGATE_DIR, interval, category_cls,
                                   'part-r-%05d' % part)
        if not s3_key_exists(s3_connection, path):
            break

        # Sometimes S3 doesn't let us read immediately after key is written
        for i in xrange(5):
            try:
                txt = get_text_from_s3(s3_connection, path)
            except S3ResponseError as e:
                print 'S3ResponseError on %s, retrying' % path
                sleep(300)
            else:
                break
        else:
            print 'Could not retrieve %s' % path
            raise e

        for line in txt.splitlines():
            tuples = line.rstrip('\n').split('\t')
            group, uniques, pageviews = tuples[:-2], tuples[-2], tuples[-1]
            if len(group) > 1:
                group = tuple(group)
            else:
                group = group[0]
            data[group] = (int(uniques), int(pageviews))

        part += 1

    if not data:
        raise ValueError("No data for %s/%s" % (interval,
                                                category_cls.__name__))

    return data


def report_interval(interval, background=True):
    if background:
        from multiprocessing import Process
        p = Process(target=_report_interval, args=(interval,))
        p.start()
    else:
        _report_interval(interval)


def _name_to_kw(category_cls, name):
    """Get the keywords needed to build an instance of traffic data."""
    def target_split(name):
        """Split a name that contains multiple words.

        Name is (link,campaign-subreddit) where link and campaign are
        thing fullnames. campaign and subreddit are each optional, so
        the string could look like any of these:
        (t3_bh,t8_ab-pics), (t3_bh,t8_ab), (t3_bh,-pics), (t3_bh,)
        Also check for the old format (t3_by, pics)

        """

        link_codename, target_info = name
        campaign_codename = None
        if not target_info:
            subreddit = ''
        elif target_info.find('-') != -1:
            campaign_codename, subreddit = target_info.split('-', 1)
        elif target_info.find('_') != -1:
            campaign_codename = target_info
            subreddit = ''
        else:
            subreddit = target_info
        return {'codename': campaign_codename or link_codename,
                'subreddit': subreddit}

    d = {SitewidePageviews: lambda n: {},
         PageviewsBySubreddit: lambda n: {'subreddit': n},
         PageviewsBySubredditAndPath: lambda n: {'srpath': n},
         PageviewsByLanguage: lambda n: {'lang': n},
         ClickthroughsByCodename: lambda n: {'codename': name},
         AdImpressionsByCodename: lambda n: {'codename': name},
         TargetedClickthroughsByCodename: target_split,
         TargetedImpressionsByCodename: target_split}
    return d[category_cls](name)


def _report_interval(interval):
    """Read aggregated traffic from S3 and write to postgres."""
    from sqlalchemy.orm import scoped_session, sessionmaker
    from r2.models.traffic import engine
    Session = scoped_session(sessionmaker(bind=engine))

    # determine interval_type from YYYY-MM[-DD][-HH]
    pieces = interval.split('-')
    pieces = [int(i) for i in pieces]
    if len(pieces) == 4:
        interval_type = 'hour'
    elif len(pieces) == 3:
        interval_type = 'day'
        pieces.append(0)
    elif len(pieces) == 2:
        interval_type = 'month'
        pieces.append(1)
        pieces.append(0)
    else:
        raise

    pg_interval = "%04d-%02d-%02d %02d:00:00" % tuple(pieces)
    print 'reporting interval %s (%s)' % (pg_interval, interval_type)

    # Read aggregates and write to traffic db
    for category_cls in traffic_categories:
        now = datetime.datetime.now()
        print '*** %s - %s - %s' % (category_cls.__name__, interval, now)
        data = get_aggregate(interval, category_cls)
        len_data = len(data)
        step = max(len_data / 5, 100)
        for i, (name, (uniques, pageviews)) in enumerate(data.iteritems()):
            try:
                for n in tup(name):
                    unicode(n)
            except UnicodeDecodeError:
                print '%s - %s - %s - %s' % (category_cls.__name__, name,
                                             uniques, pageviews)
                continue

            if i % step == 0:
                now = datetime.datetime.now()
                print '%s - %s - %s/%s - %s' % (interval, category_cls.__name__,
                                                i, len_data, now)

            kw = {'date': pg_interval, 'interval': interval_type,
                  'unique_count': uniques, 'pageview_count': pageviews}
            kw.update(_name_to_kw(category_cls, name))
            r = category_cls(**kw)

            try:
                Session.merge(r)
                Session.commit()
            except DataError:
                Session.rollback()
                continue

    Session.remove()
    now = datetime.datetime.now()
    print 'finished reporting %s (%s) - %s' % (pg_interval, interval_type, now)


def process_pixel_log(log_path, fast=False):
    """Process an hourly pixel log file.

    Extract data from raw hourly log and aggregate it and report it. Also
    depending on the specific date and options, aggregate and report the day
    and month. Setting fast=True is appropriate for backfilling as it
    eliminates reduntant steps.

    """

    if log_path.endswith('/*'):
        log_dir = log_path[:-len('/*')]
        date_fields = os.path.basename(log_dir).split('.', 1)[0].split('-')
    else:
        date_fields = os.path.basename(log_path).split('.', 1)[0].split('-')
    year, month, day, hour = (int(i) for i in date_fields)
    hour_date = '%s-%02d-%02d-%02d' % (year, month, day, hour)
    day_date = '%s-%02d-%02d' % (year, month, day)
    month_date = '%s-%02d' % (year, month)

    # All logs from this day use the same jobflow
    jobflow_name = 'Traffic Processing %s' % day_date

    output_path = os.path.join(PROCESSED_DIR, 'hour', hour_date)
    extract_hour(emr_connection, jobflow_name, log_path, output_path,
                 log_uri=AWS_LOG_DIR)

    input_path = os.path.join(PROCESSED_DIR, 'hour', hour_date)
    output_path = os.path.join(AGGREGATE_DIR, hour_date)
    aggregate_interval(emr_connection, jobflow_name, input_path, output_path,
                       log_uri=AWS_LOG_DIR)
    if not fast:
        report_interval(hour_date)

    if hour == 23 or (not fast and (hour == 0 or hour % 4 == 3)):
        # Don't aggregate and report day on every hour
        input_path = os.path.join(PROCESSED_DIR, 'hour', '%s-*' % day_date)
        output_path = os.path.join(AGGREGATE_DIR, day_date)
        aggregate_interval(emr_connection, jobflow_name, input_path,
                           output_path, log_uri=AWS_LOG_DIR)
        if not fast:
            report_interval(day_date)

    if hour == 23:
        # Special tasks for final hour of the day
        input_path = os.path.join(PROCESSED_DIR, 'hour', '%s-*' % day_date)
        output_path = os.path.join(PROCESSED_DIR, 'day', day_date)
        coalesce_interval(emr_connection, jobflow_name, input_path,
                          output_path, log_uri=AWS_LOG_DIR)
        terminate_jobflow(emr_connection, jobflow_name)

        if not fast:
            aggregate_month(month_date)
            report_interval(month_date)


def aggregate_month(month_date):
    jobflow_name = 'Traffic Processing %s' % month_date
    input_path = os.path.join(PROCESSED_DIR, 'day', '%s-*' % month_date)
    output_path = os.path.join(AGGREGATE_DIR, month_date)
    aggregate_interval(emr_connection, jobflow_name, input_path, output_path,
                       log_uri=AWS_LOG_DIR, slave_instance_type='m2.2xlarge')
    terminate_jobflow(emr_connection, jobflow_name)


def process_month_hours(month_date, start_hour=0, days=None):
    """Process hourly logs from entire month.

    Complete monthly backfill requires running [verify_month_inputs,]
    process_month_hours, aggregate_month, [verify_month_outputs,] and
    report_entire_month.

    """

    year, month = month_date.split('-')
    year, month = int(year), int(month)

    days = days or xrange(1, calendar.monthrange(year, month)[1] + 1)
    hours = xrange(start_hour, 24)

    for day in days:
        for hour in hours:
            hour_date = '%04d-%02d-%02d-%02d' % (year, month, day, hour)
            log_path = os.path.join(RAW_LOG_DIR, '%s.log.gz' % hour_date)
            if not s3_key_exists(s3_connection, log_path):
                log_path = os.path.join(RAW_LOG_DIR, '%s.log.bz2' % hour_date)
                if not s3_key_exists(s3_connection, log_path):
                    print 'Missing log for %s' % hour_date
                    continue
            print 'Processing %s' % log_path
            process_pixel_log(log_path, fast=True)
        hours = xrange(24)


def report_entire_month(month_date, start_hour=0, start_day=1):
    """Report all hours and days from month."""
    year, month = month_date.split('-')
    year, month = int(year), int(month)
    hours = xrange(start_hour, 24)

    for day in xrange(start_day, calendar.monthrange(year, month)[1] + 1):
        for hour in hours:
            hour_date = '%04d-%02d-%02d-%02d' % (year, month, day, hour)
            try:
                report_interval(hour_date, background=False)
            except ValueError:
                print 'Failed for %s' % hour_date
                continue
        hours = xrange(24)
        day_date = '%04d-%02d-%02d' % (year, month, day)
        try:
            report_interval(day_date, background=False)
        except ValueError:
            print 'Failed for %s' % day_date
            continue
    report_interval(month_date, background=False)


def verify_month_outputs(month_date):
    """Check existance of all hour, day, month aggregates for month_date."""
    year, month = month_date.split('-')
    year, month = int(year), int(month)
    missing = []

    for day in xrange(1, calendar.monthrange(year, month)[1] + 1):
        for hour in xrange(24):
            hour_date = '%04d-%02d-%02d-%02d' % (year, month, day, hour)
            for category_cls in traffic_categories:
                for d in [AGGREGATE_DIR, os.path.join(PROCESSED_DIR, 'hour')]:
                    path = _get_processed_path(d, hour_date, category_cls,
                                               'part-r-00000')
                    if not s3_key_exists(s3_connection, path):
                        missing.append(hour_date)

        day_date = '%04d-%02d-%02d' % (year, month, day)
        for category_cls in traffic_categories:
            for d in [AGGREGATE_DIR, os.path.join(PROCESSED_DIR, 'day')]:
                path = _get_processed_path(d, day_date, category_cls,
                                           'part-r-00000')
                if not s3_key_exists(s3_connection, path):
                    missing.append(day_date)

    month_date = '%04d-%02d' % (year, month)
    for c in traffic_categories:
        path = _get_processed_path(AGGREGATE_DIR, month_date, category_cls,
                                   'part-r-00000')
        if not s3_key_exists(s3_connection, path):
            missing.append(month_date)

    for d in sorted(list(set(missing))):
        print d


def verify_month_inputs(month_date):
    """Check existance of all hourly traffic logs for month_date."""
    year, month = month_date.split('-')
    year, month = int(year), int(month)
    missing = []

    for day in xrange(1, calendar.monthrange(year, month)[1] + 1):
        for hour in xrange(24):
            hour_date = '%04d-%02d-%02d-%02d' % (year, month, day, hour)
            log_path = os.path.join(RAW_LOG_DIR, '%s.log.gz' % hour_date)
            if not s3_key_exists(s3_connection, log_path):
                log_path = os.path.join(RAW_LOG_DIR, '%s.log.bz2' % hour_date)
                if not s3_key_exists(s3_connection, log_path):
                    missing.append(hour_date)

    for d in missing:
        print d


def process_hour(hour_date):
    """Process hour_date's traffic.

    Can't fire at the very start of an hour because it takes time to bzip and
    upload the file to S3. Check the bucket for the file and sleep if it
    doesn't exist.

    """

    SLEEPTIME = 180

    log_dir = os.path.join(RAW_LOG_DIR, hour_date)
    files_missing = [os.path.join(log_dir, '%s.log.bz2' % h)
                     for h in g.TRAFFIC_LOG_HOSTS]
    files_missing = [f for f in files_missing
                       if not s3_key_exists(s3_connection, f)]

    while files_missing:
        print 'Missing log(s) %s, sleeping' % files_missing
        sleep(SLEEPTIME)
        files_missing = [f for f in files_missing
                           if not s3_key_exists(s3_connection, f)]
    process_pixel_log(os.path.join(log_dir, '*'))
