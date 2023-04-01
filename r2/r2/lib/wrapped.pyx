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


from datetime import datetime
from hashlib import md5
from itertools import chain
import random
import re
import types

from r2.lib.cache import MemcachedError
from r2.lib.utils import SimpleSillyStub


CACHE_HIT_SAMPLE_RATE = 0.001
RENDER_TIMER_SAMPLE_RATE = 0.001

class _TemplateUpdater(object):
    # this class is just a hack to get around Cython's closure rules

    __slots = ['d', 'start', 'end', 'template', 'pattern']

    def __init__(self, d, start, end, template, pattern):
        self.d = d
        self.start, self.end = start, end
        self.template = template
        self.pattern = pattern

    def update(self):
        return self.pattern.sub(self._convert, self.template)

    def _convert(self, m):
        name = m.group("named")
        return self.d.get(name, self.start + name + self.end)

class StringTemplate(object):
    """
    Simple-minded string templating, where variables of the for $____
    in a strinf are replaced with values based on a dictionary.

    Unline the built-in Template class, this supports an update method

    We could use the built in python Template class for this, but
    unfortunately it doesn't handle unicode as gracefully as we'd
    like.
    """
    start_delim = "<$>"
    end_delim = "</$>"
    pattern2 = r"[_a-z][_a-z0-9]*"
    pattern2 = r"%(start_delim)s(?:(?P<named>%(pattern)s))%(end_delim)s" % \
               dict(pattern = pattern2,
                    start_delim = re.escape(start_delim),
                    end_delim = re.escape(end_delim),
                    )
    pattern2 = re.compile(pattern2,  re.UNICODE)

    def __init__(self, template):
        # for the nth time, we have to transform the string into
        # unicode.  Otherwise, re.sub will choke on non-ascii
        # characters.
        try:
            self.template = unicode(template)
        except UnicodeDecodeError:
            self.template = unicode(template, "utf8")

    def update(self, d):
        """
        Given a dictionary of replacement rules for the Template,
        replace variables in the template (once!) and return an
        updated Template.
        """
        if d:
            updater = _TemplateUpdater(d, self.start_delim, self.end_delim,
                                       self.template, self.pattern2)
            return self.__class__(updater.update())
        return self

    def finalize(self, d = {}):
        """
        The same as update, except the dictionary is optional and the
        object returned will be a unicode object.
        """
        return self.update(d).template


class CacheStub(object):
    """
    When using cached renderings, this class generates a stub based on
    the hash of the Templated item passed into init for the style
    specified.

    This class is suitable as a stub object (in the case of API calls)
    and wil render in a string form suitable for replacement with
    StringTemplate in the case of normal rendering. 
    """
    def __init__(self, item, style):
        self.name = "h%s%s" % (id(item), str(style).replace('-', '_'))

    def __str__(self):
        return StringTemplate.start_delim + self.name + \
               StringTemplate.end_delim

    def __repr__(self):
        return "<%s: %s>" % (self.__class__.__name__, self.name)

class CachedVariable(CacheStub):
    """
    Same use as CacheStubs in normal templates, except it can be
    applied to where we would normally put a '$____' variable by hand
    in a template (file).
    """
    def __init__(self, name):
        self.name = name


class Templated(object):
    """
    Replaces the Wrapped class (which has now a subclass and which
    takes an thing to be wrapped).

    Templated objects are suitable for rendering and caching, with a
    render loop desgined to fetch other cached templates and insert
    them into the current template.

    """

    # is this template cachable (see CachedTemplate)
    cachable = False
    # attributes that will not be made into the cache key
    cache_ignore = set()

    def __repr__(self):
        return "<Templated: %s>" % self.__class__.__name__
    
    def __init__(self, **context):
        """
        uses context to init __dict__ (making this object a bit like a storage)
        """
        for k, v in context.iteritems():
            setattr(self, k, v)
        if not hasattr(self, "render_class"):
            self.render_class = self.__class__

    def template(self, style='html'):
        """
        Fetches template from the template manager
        """
        from r2.config.templates import tpm

        return tpm.get(self.render_class, style)

    def template_is_null(self, style='html'):
        template = self.template(style)
        return getattr(template, "is_null", False)

    def cache_key(self, *a):
        """
        if cachable, this function is used to generate the cache key. 
        """
        raise NotImplementedError

    @property
    def render_class_name(self):
        return self.render_class.__name__

    def render_nocache(self, style):
        """
        No-frills (or caching) rendering of the template.  The
        template is returned as a subclass of StringTemplate and
        therefore finalize() must be called on it to turn it into its
        final form
        """
        from filters import unsafe
        from pylons import tmpl_context as c
        from pylons import app_globals as g

        if (self.cachable and
                style != "api" and
                random.random() < RENDER_TIMER_SAMPLE_RATE):
            timer = g.stats.get_timer(name="render.%s" % self.render_class_name)
        else:
            timer = SimpleSillyStub()

        timer.start()
        template = self.template(style)

        # store the global render style (child templates might override it)
        render_style = c.render_style
        c.render_style = style

        res = template.render(thing=self)
        if not isinstance(res, StringTemplate):
            res = StringTemplate(res)

        # reset the global render style
        c.render_style = render_style
        timer.stop()
        return res

    def _render(self, style, **kwargs):
        """
        Renders the current template with the current style.

        if this is the first template to be rendered, it is will track
        cachable templates, insert stubs for them in the output,
        get_multi from the cache, and render the uncached templates.
        Uncached but cachable templates are inserted back into the
        cache with a set_multi.

        NOTE: one of the interesting issues with this function is that
        on each newly rendered thing, it is possible that that
        rendering has in turn cause more cachable things to be
        fetched.  Thus the first template to be rendered runs a loop
        and keeps rendering until there is nothing left to render.
        Then it updates the master template until it doesn't change.

        NOTE 2: anything passed in as a kw to render (and thus
        _render) will not be part of the cached version of the object,
        and will substituted last.
        """
        from pylons import tmpl_context as c
        from pylons import app_globals as g

        style = style or c.render_style or 'html'

        # prepare (and store) the list of cachable items. 
        primary = False
        if not isinstance(c.render_tracker, dict):
            primary = True
            c.render_tracker = {}

        if (self.cachable and
                not self.template_is_null(style) and
                style != "api"):
            # insert a stub for cachable non-primary templates
            res = CacheStub(self, style)
            cache_key = self.cache_key(style)
            # in the tracker, we need to store:
            #  The render cache key (res.name)
            #  The memcached cache key(cache_key)
            #  who I am (self) and what am I doing (style) with what
            #  (kwargs)
            c.render_tracker[res.name] = (cache_key, (self, (style, kwargs)))
        else:
            # either a primary template or not cachable, so render it
            res = self.render_nocache(style)

        # if this is the primary template, let the caching games begin
        if primary:
            # updates will be the (self-updated) list of all of
            # the cached templates that have been cached or
            # rendered.
            updates = {}
            # to_cache is just the keys of the cached templates
            # that were not in the cache.
            to_cache = set([])
            while c.render_tracker:
                # copy and wipe the tracker.  It'll get repopulated if
                # any of the subsequent render()s call cached objects.
                current = c.render_tracker
                c.render_tracker = {}
    
                # do a multi-get.  NOTE: cache keys are the first item
                # in the tuple that is the current dict's values.
                # This dict cast will generate a new dict of cache_key
                # to value
                cached = self._read_cache(dict(current.values()))
                # replacements will be a map of key -> rendered content
                # for updateing the current set of updates
                replacements = {}

                new_updates = {}
                # render items that didn't make it into the cached list
                for key, (cache_key, others) in current.iteritems():
                    # unbundle the remaining args
                    item, (style, kw) = others
                    if cache_key not in cached:
                        # this had to be rendered, so cache it later
                        to_cache.add(cache_key)
                        # render the item and apply the stored kw args
                        r = item.render_nocache(style)
                    else:
                        r = cached[cache_key]

                    event_name = 'render-cache.%s' % item.render_class_name
                    name = 'hit' if cache_key in cached else 'miss'
                    g.stats.event_count(
                        event_name, name, sample_rate=CACHE_HIT_SAMPLE_RATE)

                    # store the unevaluated templates in
                    # cached for caching
                    replacements[key] = r.finalize(kw)
                    new_updates[key] = (cache_key, (r, kw))

                # update the updates so that when we can do the
                # replacement in one pass.
                
                # NOTE: keep kw, but don't update based on them.
                # We might have to cache these later, and we want
                # to have things like $child present.
                for k in updates.keys():
                    cache_key, (value, kw) = updates[k]
                    value = value.update(replacements)
                    updates[k] = cache_key, (value, kw)

                updates.update(new_updates)
    
            # at this point, we haven't touched res, but updates now
            # has the list of all the updates we could conceivably
            # want to make, and to_cache is the list of cache keys
            # that we didn't find in the cache.

            # cache content that was newly rendered
            _to_cache = {}
            for k, (v, kw) in updates.values():
                if k in to_cache:
                    _to_cache[k] = v
            self._write_cache(_to_cache)

            # edge case: this may be the primary tempalte and cachable
            if isinstance(res, CacheStub):
                res = updates[res.name][1][0]

            # now we can update the updates to make use of their kw args.
            _updates = {}
            for k, (foo, (v, kw)) in updates.iteritems():
                _updates[k] = v.finalize(kw)
            updates = _updates

            # update the response to use these values
            # replace till we can't replace any more. 
            npasses = 0
            while True:
                npasses += 1
                r = res
                res = res.update(kwargs).update(updates)
                semi_final = res.finalize()
                if r.finalize() == res.finalize():
                    res = semi_final
                    break

            # wipe out the render tracker object
            c.render_tracker = None
        elif not isinstance(res, CacheStub):
            # we're done.  Update the template based on the args passed in
            res = res.finalize(kwargs)

        return res

    def _write_cache(self, keys):
        from pylons import app_globals as g

        if not keys:
            return

        try:
            g.rendercache.set_multi(keys, time=3600)
        except MemcachedError as e:
            g.log.warning("rendercache error: %s", e)
            return

    def _read_cache(self, keys):
        from pylons import app_globals as g

        ret = g.rendercache.get_multi(keys)
        return ret

    def render(self, style = None, **kw):
        from r2.lib.filters import unsafe
        res = self._render(style, **kw)
        return unsafe(res) if isinstance(res, str) else res


class Uncachable(Exception): pass

_easy_cache_cls = set([bool, int, long, float, unicode, str, types.NoneType,
                      datetime])

def make_cachable(v, style):
    if v.__class__ in _easy_cache_cls or isinstance(v, type):
        try:
            return unicode(v)
        except UnicodeDecodeError:
            try:
                return unicode(v, "utf8")
            except (TypeError, UnicodeDecodeError):
                return repr(v)
    elif isinstance(v, (types.MethodType, CachedVariable)):
       return
    elif isinstance(v, (tuple, list, set)):
        return repr([make_cachable(x, style) for x in v])
    elif isinstance(v, dict):
        ret = {}
        for k in sorted(v.iterkeys()):
            ret[k] = make_cachable(v[k], style)
        return repr(ret)
    elif hasattr(v, "cache_key"):
        return v.cache_key(style)
    else:
        raise Uncachable, "%s, %s" % (v, type(v))

class CachedTemplate(Templated):
    cachable = True

    def template_hash(self, style):
        template = self.template(style)

        # if template debugging is on, there will be no hash and we can make the
        # caching process-local
        template_hash = getattr(template, "hash", id(self.__class__))
        return template_hash

    def cachable_attrs(self):
        """
        Generates an iterator of attr names and their values for every
        attr on this element that should be used in generating the cache key.
        """
        ret = []
        for k in sorted(self.__dict__):
            if k not in self.cache_ignore and not k.startswith('_'):
                ret.append((k, self.__dict__[k]))
        return ret

    def cache_key(self, style):
        from pylons import request
        from pylons import tmpl_context as c

        # these values are needed to render any link on the site, and
        # a menu is just a set of links, so we best cache against
        # them.
        keys = [
            c.user_is_loggedin,
            c.user_is_admin,
            c.domain_prefix,
            style,
            c.secure,
            c.lang,
            c.site.user_path,
            self.template_hash(style),
        ]

        if c.secure:
            keys.append(request.host)

        keys = [make_cachable(x, style) for x in keys]

        # add all parameters sent into __init__, using their current value
        auto_keys = [(k,  make_cachable(v, style))
                     for k, v in self.cachable_attrs()]
        keys.append(repr(auto_keys))
        h = md5(u''.join(keys)).hexdigest()
        return "rend:%s:%s" % (self.render_class_name, h)


class Wrapped(CachedTemplate):
    # default to false, evaluate
    cachable = False
    cache_ignore = set(['lookups'])
    
    def cache_key(self, style):
        if self.cachable:
            for i, l in enumerate(self.lookups):
                if hasattr(l, "wrapped_cache_key"):
                    # setattr will force a __dict__ entry, but only if the
                    # param doesn't start with "_"
                    setattr(self, "lookup%d_cache_key" % i,
                            ''.join(map(repr,
                                        l.wrapped_cache_key(self, style))))
        return CachedTemplate.cache_key(self, style)

    def __init__(self, *lookups, **context):
        self.lookups = lookups
        # set the default render class to be based on the lookup
        if self.__class__ == Wrapped and lookups:
            self.render_class = lookups[0].__class__
        else:
            self.render_class = self.__class__
        # this shouldn't be too surprising
        self.cache_ignore = self.cache_ignore.union(
            set(['cachable', 'render', 'cache_ignore', 'lookups']))
        if (not self._any_hasattr(lookups, 'cachable') and 
            self._any_hasattr(lookups, 'wrapped_cache_key')):
            self.cachable = True
        if self.cachable:
            for l in lookups:
                if hasattr(l, "cache_ignore"):
                    self.cache_ignore = self.cache_ignore.union(l.cache_ignore)
            
        Templated.__init__(self, **context)

    def _any_hasattr(self, lookups, attr):
        for l in lookups:
            if hasattr(l, attr):
                return True

    def __repr__(self):
        return "<Wrapped: %s,  %s>" % (self.__class__.__name__,
                                       self.lookups)

    def __getattr__(self, attr):
        if attr == 'lookups':
            raise AttributeError, attr

        res = None
        found = False
        for lookup in self.lookups:
            try:
                res = getattr(lookup, attr)
                found = True
                break
            except AttributeError:
                pass

        if not found:
            raise AttributeError, "%r has no %s" % (self, attr)

        setattr(self, attr, res)
        return res

    def __iter__(self):
        if self.lookups and hasattr(self.lookups[0], "__iter__"):
            return self.lookups[0].__iter__()
        raise NotImplementedError

class Styled(CachedTemplate):
    """Rather than creating a separate template for every possible
    menu/button style we might want to use, this class overrides the
    render function to render only the <%def> in the template whose
    name matches 'style'.

    Additionally, when rendering, the '_id' and 'css_class' attributes
    are intended to be used in the outermost container's id and class
    tag.
    """

    def __init__(self, style, _id = '', css_class = '', **kw):
        self._id = _id
        self.css_class = css_class
        self.style = style
        CachedTemplate.__init__(self, **kw)

    def template(self, style='html'):
        base_template = CachedTemplate.template(self, style)
        template = base_template.get_def(self.style)
        return template

    def template_hash(self, style):
        # use the hash of the base template so changes to the template file
        # will be recognized
        base_template = CachedTemplate.template(self, style)

        # if template debugging is on, there will be no hash and we can make the
        # caching process-local
        template_hash = getattr(base_template, "hash", id(self.__class__))
        return template_hash
