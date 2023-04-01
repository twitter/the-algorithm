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
import cPickle as pickle
from datetime import datetime, timedelta
import functools
import httplib
import json
from lxml import etree
from pylons import tmpl_context as c
from pylons import app_globals as g
import socket
import time
import urllib

import l2cs

from r2.lib import amqp, filters
from r2.lib.db.operators import desc
from r2.lib.db.sorts import epoch_seconds
from r2.lib.filters import _force_unicode
from r2.lib.providers.search import SearchProvider
from r2.lib.providers.search.common import (
    InvalidQuery,
    LinkFields,
    Results,
    safe_get,
    safe_xml_str,
    SearchError,
    SearchHTTPError,
    SubredditFields,
)
import r2.lib.utils as r2utils
from r2.models import (
    Account,
    AllMinus,
    DomainSR,
    FakeSubreddit,
    FriendsSR,
    Link,
    MultiReddit,
    NotFound,
    Subreddit,
    Thing,
)

_TIMEOUT = 5 # seconds for http requests to cloudsearch
_CHUNK_SIZE = 4000000 # Approx. 4 MB, to stay under the 5MB limit
_VERSION_OFFSET = 13257906857


class CloudSearchUploader(object):
    use_safe_get = False
    types = ()

    def __init__(self, doc_api, fullnames=None, version_offset=_VERSION_OFFSET):
        self.doc_api = doc_api
        self._version_offset = version_offset
        self.fullnames = fullnames

    @classmethod
    def desired_fullnames(cls, items):
        '''Pull fullnames that represent instances of 'types' out of items'''
        fullnames = set()
        type_ids = [type_._type_id for type_ in cls.types]
        for item in items:
            item_type = r2utils.decompose_fullname(item['fullname'])[1]
            if item_type in type_ids:
                fullnames.add(item['fullname'])
        return fullnames

    def _version_tenths(self):
        '''Cloudsearch documents don't update unless the sent "version" field
        is higher than the one currently indexed. As our documents don't have
        "versions" and could in theory be updated multiple times in one second,
        for now, use "tenths of a second since 12:00:00.00 1/1/2012" as the
        "version" - this will last approximately 13 years until bumping up against
        the version max of 2^32 for cloudsearch docs'''
        return int(time.time() * 10) - self._version_offset

    def _version_seconds(self):
        return int(time.time()) - int(self._version_offset / 10)

    _version = _version_tenths

    def add_xml(self, thing, version):
        add = etree.Element("add", id=thing._fullname, version=str(version),
                            lang="en")

        for field_name, value in self.fields(thing).iteritems():
            field = etree.SubElement(add, "field", name=field_name)
            field.text = safe_xml_str(value)

        return add

    def delete_xml(self, thing, version=None):
        '''Return the cloudsearch XML representation of
        "delete this from the index"
        
        '''
        version = str(version or self._version())
        delete = etree.Element("delete", id=thing._fullname, version=version)
        return delete

    def delete_ids(self, ids):
        '''Delete documents from the index.
        'ids' should be a list of fullnames
        
        '''
        version = self._version()
        deletes = [etree.Element("delete", id=id_, version=str(version))
                   for id_ in ids]
        batch = etree.Element("batch")
        batch.extend(deletes)
        return self.send_documents(batch)

    def xml_from_things(self):
        '''Generate a <batch> XML tree to send to cloudsearch for
        adding/updating/deleting the given things
        
        '''
        batch = etree.Element("batch")
        self.batch_lookups()
        version = self._version()
        for thing in self.things:
            try:
                if thing._spam or thing._deleted:
                    delete_node = self.delete_xml(thing, version)
                    batch.append(delete_node)
                elif self.should_index(thing):
                    add_node = self.add_xml(thing, version)
                    batch.append(add_node)
            except (AttributeError, KeyError) as e:
                # Problem! Bail out, which means these items won't get
                # "consumed" from the queue. If the problem is from DB
                # lag or a transient issue, then the queue consumer
                # will succeed eventually. If it's something else,
                # then manually run a consumer with 'use_safe_get'
                # on to get past the bad Thing in the queue
                if not self.use_safe_get:
                    raise
                else:
                    g.log.warning("Ignoring problem on thing %r.\n\n%r",
                                  thing, e)
        return batch

    def should_index(self, thing):
        raise NotImplementedError

    def batch_lookups(self):
        try:
            self.things = Thing._by_fullname(self.fullnames, data=True,
                                             return_dict=False)
        except NotFound:
            if self.use_safe_get:
                self.things = safe_get(Thing._by_fullname, self.fullnames,
                                       data=True, return_dict=False)
            else:
                raise

    def fields(self, thing):
        raise NotImplementedError

    def inject(self, quiet=False):
        '''Send things to cloudsearch. Return value is time elapsed, in seconds,
        of the communication with the cloudsearch endpoint
        
        '''
        xml_things = self.xml_from_things()

        if not len(xml_things):
            return 0

        cs_start = datetime.now(g.tz)
        sent = self.send_documents(xml_things)
        cs_time = (datetime.now(g.tz) - cs_start).total_seconds()

        adds, deletes, warnings = 0, 0, []
        for record in sent:
            response = etree.fromstring(record)
            adds += int(response.get("adds", 0))
            deletes += int(response.get("deletes", 0))
            if response.get("warnings"):
                warnings.append(response.get("warnings"))

        g.stats.simple_event("cloudsearch.uploads.adds", delta=adds)
        g.stats.simple_event("cloudsearch.uploads.deletes", delta=deletes)
        g.stats.simple_event("cloudsearch.uploads.warnings",
                delta=len(warnings))

        if not quiet:
            print "%s Changes: +%i -%i" % (self.__class__.__name__,
                                           adds, deletes)
            if len(warnings):
                print "%s Warnings: %s" % (self.__class__.__name__,
                                           "; ".join(warnings))

        return cs_time

    def send_documents(self, docs):
        '''Open a connection to the cloudsearch endpoint, and send the documents
        for indexing. Multiple requests are sent if a large number of documents
        are being sent (see chunk_xml())
        
        Raises SearchHTTPError if the endpoint indicates a failure
        '''
        responses = []
        connection = httplib.HTTPConnection(
            self.doc_api, port=80, timeout=_TIMEOUT)
        chunker = chunk_xml(docs)
        try:
            for data in chunker:
                headers = {}
                headers['Content-Type'] = 'application/xml'
                # HTTPLib calculates Content-Length header automatically
                connection.request('POST', "/2011-02-01/documents/batch",
                                   data, headers)
                response = connection.getresponse()
                if 200 <= response.status < 300:
                    responses.append(response.read())
                else:
                    raise SearchHTTPError(response.status,
                                               response.reason,
                                               response.read())
        finally:
            connection.close()
        return responses


class LinkUploader(CloudSearchUploader):
    types = (Link,)

    def __init__(self, doc_api, fullnames=None, version_offset=_VERSION_OFFSET):
        super(LinkUploader, self).__init__(doc_api, fullnames, version_offset)
        self.accounts = {}
        self.srs = {}

    def fields(self, thing):
        '''Return fields relevant to a Link search index'''
        account = self.accounts[thing.author_id]
        sr = self.srs[thing.sr_id]
        return LinkFields(thing, account, sr).fields()

    def batch_lookups(self):
        super(LinkUploader, self).batch_lookups()
        author_ids = [thing.author_id for thing in self.things
                      if hasattr(thing, 'author_id')]
        try:
            self.accounts = Account._byID(author_ids, data=True,
                                          return_dict=True)
        except NotFound:
            if self.use_safe_get:
                self.accounts = safe_get(Account._byID, author_ids, data=True,
                                         return_dict=True)
            else:
                raise

        sr_ids = [thing.sr_id for thing in self.things
                  if hasattr(thing, 'sr_id')]
        try:
            self.srs = Subreddit._byID(sr_ids, data=True, return_dict=True)
        except NotFound:
            if self.use_safe_get:
                self.srs = safe_get(Subreddit._byID, sr_ids, data=True,
                                    return_dict=True)
            else:
                raise

    def should_index(self, thing):
        return (thing.promoted is None and getattr(thing, "sr_id", None) != -1)


class SubredditUploader(CloudSearchUploader):
    types = (Subreddit,)
    _version = CloudSearchUploader._version_seconds

    def fields(self, thing):
        return SubredditFields(thing).fields()

    def should_index(self, thing):
        return thing._id != Subreddit.get_promote_srid()


def chunk_xml(xml, depth=0):
    '''Chunk POST data into pieces that are smaller than the 20 MB limit.
    
    Ideally, this never happens (if chunking is necessary, would be better
    to avoid xml'ifying before testing content_length)'''
    data = etree.tostring(xml)
    content_length = len(data)
    if content_length < _CHUNK_SIZE:
        yield data
    else:
        depth += 1
        print "WARNING: Chunking (depth=%s)" % depth
        half = len(xml) / 2
        left_half = xml # for ease of reading
        right_half = etree.Element("batch")
        # etree magic simultaneously removes the elements from one tree
        # when they are appended to a different tree
        right_half.extend(xml[half:])
        for chunk in chunk_xml(left_half, depth=depth):
            yield chunk
        for chunk in chunk_xml(right_half, depth=depth):
            yield chunk


@g.stats.amqp_processor('cloudsearch_changes')
def _run_changed(msgs, chan):
    '''Consume the cloudsearch_changes queue, and print reporting information
    on how long it took and how many remain
    
    '''
    start = datetime.now(g.tz)

    changed = [pickle.loads(msg.body) for msg in msgs]

    link_fns = LinkUploader.desired_fullnames(changed)
    sr_fns = SubredditUploader.desired_fullnames(changed)

    link_uploader = LinkUploader(g.CLOUDSEARCH_DOC_API, fullnames=link_fns)
    subreddit_uploader = SubredditUploader(g.CLOUDSEARCH_SUBREDDIT_DOC_API,
                                           fullnames=sr_fns)

    link_time = link_uploader.inject()
    subreddit_time = subreddit_uploader.inject()
    cloudsearch_time = link_time + subreddit_time

    totaltime = (datetime.now(g.tz) - start).total_seconds()

    print ("%s: %d messages in %.2fs seconds (%.2fs secs waiting on "
           "cloudsearch); %d duplicates, %s remaining)" %
           (start, len(changed), totaltime, cloudsearch_time,
            len(changed) - len(link_fns | sr_fns),
            msgs[-1].delivery_info.get('message_count', 'unknown')))


def run_changed(drain=False, min_size=500, limit=1000, sleep_time=10,
                use_safe_get=False, verbose=False):
    '''Run by `cron` (through `paster run`) on a schedule to send Things to
        Amazon CloudSearch
    
    '''
    if use_safe_get:
        CloudSearchUploader.use_safe_get = True
    amqp.handle_items('cloudsearch_changes', _run_changed, min_size=min_size,
                      limit=limit, drain=drain, sleep_time=sleep_time,
                      verbose=verbose)


def _progress_key(item):
    return "%s/%s" % (item._id, item._date)


def rebuild_link_index(start_at=None, sleeptime=1, cls=Link,
                       uploader=LinkUploader, doc_api='CLOUDSEARCH_DOC_API',
                       estimate=50000000, chunk_size=1000):
    doc_api = getattr(g, doc_api)
    uploader = uploader(doc_api)

    q = cls._query(cls.c._deleted == (True, False), sort=desc('_date'))

    if start_at:
        after = cls._by_fullname(start_at)
        assert isinstance(after, cls)
        q._after(after)

    q = r2utils.fetch_things2(q, chunk_size=chunk_size)
    q = r2utils.progress(q, verbosity=1000, estimate=estimate, persec=True,
                         key=_progress_key)
    for chunk in r2utils.in_chunks(q, size=chunk_size):
        uploader.things = chunk
        for x in range(5):
            try:
                uploader.inject()
            except httplib.HTTPException as err:
                print "Got %s, sleeping %s secs" % (err, x)
                time.sleep(x)
                continue
            else:
                break
        else:
            raise err
        last_update = chunk[-1]
        print "last updated %s" % last_update._fullname
        time.sleep(sleeptime)


rebuild_subreddit_index = functools.partial(rebuild_link_index,
                                            cls=Subreddit,
                                            uploader=SubredditUploader,
                                            doc_api='CLOUDSEARCH_SUBREDDIT_DOC_API',
                                            estimate=200000,
                                            chunk_size=1000)


def test_run_link(start_link, count=1000):
    '''Inject `count` number of links, starting with `start_link`'''
    if isinstance(start_link, basestring):
        start_link = int(start_link, 36)
    links = Link._byID(range(start_link - count, start_link), data=True,
                       return_dict=False)
    uploader = LinkUploader(g.CLOUDSEARCH_DOC_API, things=links)
    return uploader.inject()


def test_run_srs(*sr_names):
    '''Inject Subreddits by name into the index'''
    srs = Subreddit._by_name(sr_names).values()
    uploader = SubredditUploader(g.CLOUDSEARCH_SUBREDDIT_DOC_API, things=srs)
    return uploader.inject()


### Query Code ###
_SEARCH = "/2011-02-01/search?"
INVALID_QUERY_CODES = ('CS-UnknownFieldInMatchExpression',
                       'CS-IncorrectFieldTypeInMatchExpression',
                       'CS-InvalidMatchSetExpression',)
DEFAULT_FACETS = {"reddit": {"count":20}}
def basic_query(query=None, bq=None, faceting=None, size=1000,
                start=0, rank=None, rank_expressions=None,
                return_fields=None, record_stats=False, search_api=None):
    if search_api is None:
        search_api = g.CLOUDSEARCH_SEARCH_API
    if faceting is None:
        faceting = DEFAULT_FACETS
    path = _encode_query(query, bq, faceting, size, start, rank,
                         rank_expressions, return_fields)
    timer = None
    if record_stats:
        timer = g.stats.get_timer("providers.cloudsearch")
        timer.start()
    connection = httplib.HTTPConnection(search_api, port=80, timeout=_TIMEOUT)
    try:
        connection.request('GET', path)
        resp = connection.getresponse()
        response = resp.read()
        if record_stats:
            g.stats.action_count("event.search_query", resp.status)
        if resp.status >= 300:
            try:
                reasons = json.loads(response)
            except ValueError:
                pass
            else:
                messages = reasons.get("messages", [])
                for message in messages:
                    if message['code'] in INVALID_QUERY_CODES:
                        raise InvalidQuery(resp.status, resp.reason, message,
                                           search_api, path, reasons)
            raise SearchHTTPError(resp.status, resp.reason,
                                  search_api, path, response)
    except socket.timeout as e:
        g.stats.simple_event('cloudsearch.error.timeout')
        raise SearchError(e, search_api, path)
    except socket.error as e:
        g.stats.simple_event('cloudsearch.error.socket')
        raise SearchError(e, search_api, path)
    finally:
        connection.close()
        if timer is not None:
            timer.stop()

    return json.loads(response)


basic_link = functools.partial(basic_query, size=10, start=0,
                               rank="-relevance",
                               return_fields=['title', 'reddit',
                                              'author_fullname'],
                               record_stats=False,
                               search_api=g.CLOUDSEARCH_SEARCH_API)


basic_subreddit = functools.partial(basic_query,
                                    faceting=None,
                                    size=10, start=0,
                                    rank="-activity",
                                    return_fields=['title', 'reddit',
                                                   'author_fullname'],
                                    record_stats=False,
                                    search_api=g.CLOUDSEARCH_SUBREDDIT_SEARCH_API)


def _encode_query(query, bq, faceting, size, start, rank, rank_expressions,
                  return_fields):
    if not (query or bq):
        raise ValueError("Need query or bq")
    params = {}
    if bq:
        params["bq"] = bq
    if query:
        params["q"] = query
    params["results-type"] = "json"
    params["size"] = size
    params["start"] = start
    if rank:
        params["rank"] = rank
    if rank_expressions:
        for rank, expression in rank_expressions.iteritems():
            params['rank-%s' % rank] = expression
    if faceting:
        params["facet"] = ",".join(faceting.iterkeys())
        for facet, options in faceting.iteritems():
            params["facet-%s-top-n" % facet] = options.get("count", 20)
            if "sort" in options:
                params["facet-%s-sort" % facet] = options["sort"]
    if return_fields:
        params["return-fields"] = ",".join(return_fields)
    encoded_query = urllib.urlencode(params)
    path = _SEARCH + encoded_query
    return path


class CloudSearchQuery(object):
    '''Represents a search query sent to cloudsearch'''
    search_api = None
    sorts = {}
    recents = {None: None}
    default_syntax = "plain"
    lucene_parser = None

    def __init__(self, query, sr=None, sort=None, syntax=None, raw_sort=None,
                 faceting=None, recent=None, include_over18=True,
                 rank_expressions=None, start=0, num=1000):
        if syntax is None:
            syntax = self.default_syntax
        elif syntax not in self.known_syntaxes:
            raise ValueError("Unknown search syntax: %s" % syntax)
        self.syntax = syntax

        self.query = filters._force_unicode(query or u'')

        # parsed query
        self.converted_data = None
        self.q = u''
        self.bq = u''

        # filters
        self.sr = sr
        self._recent = recent
        self.recent = self.recents[recent]
        self.include_over18 = include_over18

        # rank / rank expressions
        self._sort = sort
        if raw_sort:
            self.sort = raw_sort
        else:
            self.sort = self.sorts.get(sort)
        self.rank_expressions = rank_expressions

        # pagination
        self.start = start
        self.num = num

        # facets
        self.faceting = faceting

        self.results = None

    def run(self, _update=False):
        results = self._run(_update=_update)
        self.results = Results(results.docs, results.hits, results._facets)
        return self.results

    def _parse(self):
        query = self.preprocess_query(self.query)

        if self.syntax == "cloudsearch":
            self.bq = self.customize_query(query)
        elif self.syntax == "lucene":
            bq = l2cs.convert(query, self.lucene_parser)
            self.converted_data = {"syntax": "cloudsearch", "converted": bq}
            self.bq = self.customize_query(bq)
        elif self.syntax == "plain":
            self.q = query.encode('utf-8')
            self.bq = self.customize_query()

        if not self.q and not self.bq:
            raise InvalidQuery

    def _run(self, _update=False):
        '''Run the search against self.query'''
        try:
            self._parse()
        except InvalidQuery:
            return Results([], 0, {})

        if g.sqlprinting:
            g.log.info("%s", self)

        return self._run_cached(self.q, self.bq.encode('utf-8'), self.sort,
                                self.rank_expressions, self.faceting,
                                start=self.start, num=self.num, _update=_update)

    def preprocess_query(self, query):
        return query

    def customize_query(self, bq=u''):
        return bq

    @classmethod
    def create_boolean_query(cls, queries):
        '''Return an AND clause combining all queries'''
        if len(queries) > 1:
            return '(and ' + ' '.join(queries) + ')'
        elif queries:
            return queries[0]
        return u''

    def __repr__(self):
        '''Return a string representation of this query'''
        result = ["<", self.__class__.__name__, "> query:",
                  repr(self.query), " "]
        if self.bq:
            result.append(" bq:")
            result.append(repr(self.bq))
            result.append(" ")
        if self.sort:
            result.append("sort:")
            result.append(self.sort)
        return ''.join(result)

    @classmethod
    def _run_cached(cls, query, bq, sort="relevance", rank_expressions=None,
                    faceting=None, start=0, num=1000, _update=False):
        '''Query the cloudsearch API. _update parameter allows for supposed
        easy memoization at later date.
        
        Example result set:
        
        {u'facets': {u'reddit': {u'constraints':
                                    [{u'count': 114, u'value': u'politics'},
                                    {u'count': 42, u'value': u'atheism'},
                                    {u'count': 27, u'value': u'wtf'},
                                    {u'count': 19, u'value': u'gaming'},
                                    {u'count': 12, u'value': u'bestof'},
                                    {u'count': 12, u'value': u'tf2'},
                                    {u'count': 11, u'value': u'AdviceAnimals'},
                                    {u'count': 9, u'value': u'todayilearned'},
                                    {u'count': 9, u'value': u'pics'},
                                    {u'count': 9, u'value': u'funny'}]}},
         u'hits': {u'found': 399,
                   u'hit': [{u'id': u't3_11111'},
                            {u'id': u't3_22222'},
                            {u'id': u't3_33333'},
                            {u'id': u't3_44444'},
                            ...
                            ],
                   u'start': 0},
         u'info': {u'cpu-time-ms': 10,
                   u'messages': [{u'code': u'CS-InvalidFieldOrRankAliasInRankParameter',
                                  u'message': u"Unable to create score object for rank '-hot'",
                                  u'severity': u'warning'}],
                   u'rid': u'<hash>',
                   u'time-ms': 9},
                   u'match-expr': u"(label 'my query')",
                   u'rank': u'-text_relevance'}
        
        '''
        try:
            response = basic_query(query=query, bq=bq, size=num, start=start,
                                   rank=sort, rank_expressions=rank_expressions,
                                   search_api=cls.search_api,
                                   faceting=faceting, record_stats=True)
        except (SearchHTTPError, SearchError) as e:
            g.log.error("Search Error: %r", e)
            raise

        warnings = response['info'].get('messages', [])
        for warning in warnings:
            g.log.warning("%(code)s (%(severity)s): %(message)s" % warning)

        hits = response['hits']['found']
        docs = [doc['id'] for doc in response['hits']['hit']]
        facets = response.get('facets', {})
        for facet in facets.keys():
            values = facets[facet]['constraints']
            facets[facet] = values

        results = Results(docs, hits, facets)
        return results


class LinkSearchQuery(CloudSearchQuery):
    search_api = g.CLOUDSEARCH_SEARCH_API
    sorts = {
        'relevance': '-relevance',
        'relevance2': '-relevance2',
        'hot': '-hot2',
        'top': '-top',
        'new': '-timestamp',
        'comments': '-num_comments',
    }
    recents = {
        'hour': timedelta(hours=1),
        'day': timedelta(days=1),
        'week': timedelta(days=7),
        'month': timedelta(days=31),
        'year': timedelta(days=366),
        'all': None,
        None: None,
    }
    schema = l2cs.make_schema(LinkFields.lucene_fieldnames())
    lucene_parser = l2cs.make_parser(
             int_fields=LinkFields.lucene_fieldnames(type_=int),
             yesno_fields=LinkFields.lucene_fieldnames(type_="yesno"),
             schema=schema)
    known_syntaxes = g.search_syntaxes
    default_syntax = "lucene"

    def customize_query(self, bq=u''):
        queries = []
        if bq:
            queries = [bq]
        if self.sr:
            subreddit_query = self._restrict_sr(self.sr)
            if subreddit_query:
                queries.append(subreddit_query)
        if self.recent:
            recent_query = self._restrict_recent(self.recent)
            queries.append(recent_query)
        if not self.include_over18:
            queries.append('over18:0')
        return self.create_boolean_query(queries)

    @staticmethod
    def _restrict_recent(recent):
        now = datetime.now(g.tz)
        since = epoch_seconds(now - recent)
        return 'timestamp:%i..' % since

    @staticmethod
    def _restrict_sr(sr):
        '''Return a cloudsearch appropriate query string that restricts
        results to only contain results from sr
        
        '''
        if isinstance(sr, MultiReddit):
            if not sr.sr_ids:
                raise InvalidQuery
            srs = ["sr_id:%s" % sr_id for sr_id in sr.sr_ids]
            return "(or %s)" % ' '.join(srs)
        elif isinstance(sr, DomainSR):
            return "site:'\"%s\"'" % sr.domain
        elif isinstance(sr, FriendsSR):
            if not c.user_is_loggedin or not c.user.friends:
                raise InvalidQuery
            # The query limit is roughly 8k bytes. Limit to 200 friends to
            # avoid getting too close to that limit
            friend_ids = c.user.friends[:200]
            friends = ["author_fullname:'%s'" %
                       Account._fullname_from_id36(r2utils.to36(id_))
                       for id_ in friend_ids]
            return "(or %s)" % ' '.join(friends)
        elif isinstance(sr, AllMinus):
            if not sr.exclude_sr_ids:
                raise InvalidQuery
            exclude_srs = ["sr_id:%s" % sr_id for sr_id in sr.exclude_sr_ids]
            return "(not (or %s))" % ' '.join(exclude_srs)
        elif not isinstance(sr, FakeSubreddit):
            return "sr_id:%s" % sr._id

        return None


class CloudSearchSubredditSearchQuery(CloudSearchQuery):
    search_api = g.CLOUDSEARCH_SUBREDDIT_SEARCH_API
    sorts = {
        'relevance': '-relevance',
        'activity': '-activity',
    }
    known_syntaxes = ("plain",)
    default_syntax = "plain"

    def preprocess_query(self, query):
        # Expand search for /r/subreddit to include subreddit name.
        sr = query.strip('/').split('/')
        if len(sr) == 2 and sr[0] == 'r' and Subreddit.is_valid_name(sr[1]):
            query = '"%s" | %s' % (query, sr[1])
        return query

    def customize_query(self, bq=u''):
        queries = []
        if bq:
            queries = [bq]
        if not self.include_over18:
            queries.append('over18:0')
        return self.create_boolean_query(queries)


class CloudSearchProvider(SearchProvider):
    '''Provider implementation: wrap it all up as a SearchProvider'''
    InvalidQuery = (InvalidQuery,)
    SearchException = (SearchHTTPError, SearchError)

    SearchQuery = LinkSearchQuery

    SubredditSearchQuery = CloudSearchSubredditSearchQuery

    def run_changed(self, drain=False, min_size=int(getattr(g, 'SOLR_MIN_BATCH', 500)), limit=1000, sleep_time=10, 
            use_safe_get=False, verbose=False):
        '''Run by `cron` (through `paster run`) on a schedule to send Things to Cloud
        '''
        if use_safe_get:
            CloudSearchUploader.use_safe_get = True
        amqp.handle_items('cloudsearch_changes', _run_changed, min_size=min_size,
                          limit=limit, drain=drain, sleep_time=sleep_time,
                          verbose=verbose)
