#!/usr/bin/env python
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

import inspect
import sys
import os.path
import re
import subprocess
import json

from pylons import app_globals as g
from pylons import tmpl_context as c

from r2.config.paths import get_built_statics_path
from r2.lib.permissions import ModeratorPermissionSet
from r2.lib.plugin import PluginLoader
from r2.lib.static import locate_static_file
from r2.lib.translation import (
    extract_javascript_msgids,
    get_catalog,
    iter_langs,
    validate_plural_forms,
)


script_tag = '<script type="text/javascript" src="{src}"></script>\n'
inline_script_tag = '<script type="text/javascript">{content}</script>'


class Uglify(object):
    def compile(self, data, dest):
        process = subprocess.Popen(
            ["/usr/bin/uglifyjs", "-nc"],
            stdin=subprocess.PIPE,
            stdout=dest,
        )

        process.communicate(input=data)

        if process.returncode != 0:
            raise subprocess.CalledProcessError(process.returncode, "uglifyjs")


class Source(object):
    """An abstract collection of JavaScript code."""
    def get_source(self, **kwargs):
        """Return the full JavaScript source code."""
        raise NotImplementedError

    def use(self, **kwargs):
        """Return HTML to insert the JavaScript source inside a template."""
        raise NotImplementedError

    @property
    def dependencies(self):
        raise NotImplementedError

    @property
    def outputs(self):
        raise NotImplementedError


class FileSource(Source):
    """A JavaScript source file on disk."""
    def __init__(self, name):
        self.name = name

    def __eq__(self, other):
        return type(self) is type(other) and self.name == other.name

    def get_source(self, use_built_statics=False):
        if use_built_statics:
            # we are in the build system so we have already copied all files
            # into the static build directory
            built_statics_path = get_built_statics_path()
            path = os.path.join(built_statics_path, "static", "js", self.name)
        else:
            # we are in request so we need to check the pylons static_files
            # path and the static paths for all plugins
            path = locate_static_file(os.path.join("static", "js", self.name))

        with open(path) as f:
            return f.read()

    def url(self, absolute=False, mangle_name=False):
        from r2.lib.template_helpers import static
        path = [g.static_path, self.name]
        if g.uncompressedJS:
            path.insert(1, "js")

        return static(os.path.join(*path), absolute, mangle_name)

    def use(self, **kwargs):
        return script_tag.format(src=self.url(**kwargs))

    @property
    def dependencies(self):
        built_statics_path = get_built_statics_path()
        path = os.path.join(built_statics_path, "static", "js", self.name)
        return [path]


class Module(Source):
    """A module of JS code consisting of a collection of sources."""
    def __init__(self, name, *sources, **kwargs):
        self.name = name
        self.should_compile = kwargs.get('should_compile', True)
        self.wrap = kwargs.get('wrap')
        self.sources = []
        filter_module = kwargs.get('filter_module')
        if isinstance(filter_module, Module):
            self.filter_sources = filter_module.get_flattened_sources([])
        else:
            self.filter_sources = None
        sources = sources or (name,)
        for source in sources:
            if not isinstance(source, Source):
                if 'prefix' in kwargs:
                    source = os.path.join(kwargs['prefix'], source)
                source = self.get_default_source(source)
            self.sources.append(source)

    def get_default_source(self, source):
        return FileSource(source)

    def get_flattened_sources(self, flattened_sources):
        for s in self.sources:
            if s in flattened_sources:
                continue
            elif isinstance(s, Module):
                s.get_flattened_sources(flattened_sources)
            else:
                flattened_sources.append(s)
        if self.filter_sources:
            flattened_sources = [s for s in flattened_sources
                                 if s not in self.filter_sources]
        return flattened_sources

    def get_source(self, use_built_statics=False):
        sources = self.get_flattened_sources([])
        return ";".join(
            s.get_source(use_built_statics=use_built_statics)
            for s in sources
        )

    def extend(self, module):
        self.sources.extend(module.sources)

    @property
    def destination_path(self):
        built_statics_path = get_built_statics_path()
        return os.path.join(built_statics_path, "static", self.name)

    def build(self, minifier):
        with open(self.destination_path, "w") as out:
            source = self.get_source(use_built_statics=True)
            if self.wrap:
                source = self.wrap.format(content=source, name=self.name)

            if self.should_compile:
                print >> sys.stderr, "Compiling {0}...".format(self.name),
                minifier.compile(source, out)
            else:
                print >> sys.stderr, "Concatenating {0}...".format(self.name),
                out.write(source)
        print >> sys.stderr, " done."

    def url(self, absolute=False, mangle_name=True):
        from r2.lib.template_helpers import static
        if g.uncompressedJS:
            return [source.url(absolute=absolute, mangle_name=mangle_name) for source in self.sources]
        else:
            return static(self.name, absolute=absolute, mangle_name=mangle_name)

    def use(self, **kwargs):
        if g.uncompressedJS:
            sources = self.get_flattened_sources([])
            return "".join(source.use(**kwargs) for source in sources)
        else:
            return script_tag.format(src=self.url(**kwargs))

    @property
    def dependencies(self):
        deps = []
        for source in self.sources:
            deps.extend(source.dependencies)
        return deps

    @property
    def outputs(self):
        return [self.destination_path]


class DataSource(Source):
    """A generated source consisting of wrapped JSON data."""
    def __init__(self, wrap, data=None):
        self.wrap = wrap
        self.data = data

    def get_content(self, **kw):
        return self.data

    def get_source(self, use_built_statics=False):
        content = self.get_content(use_built_statics=use_built_statics)
        json_data = json.dumps(content)
        return self.wrap.format(content=json_data) + "\n"

    def use(self):
        from r2.lib.filters import SC_OFF, SC_ON, websafe_json
        escaped_json = websafe_json(self.get_source())
        return (SC_OFF + inline_script_tag.format(content=escaped_json) +
                SC_ON + "\n")

    @property
    def dependencies(self):
        return []


class PermissionsDataSource(DataSource):
    """DataSource for PermissionEditor configuration data."""

    def __init__(self, permission_sets):
        self.permission_sets = permission_sets

    @classmethod
    def _make_marked_json(cls, obj):
        """Return serialized psuedo-JSON with translation support.

        Strings are marked for extraction with r.N_. Dictionaries are
        serialized to JSON objects as normal.

        """
        if isinstance(obj, dict):
            props = []
            for key, value in obj.iteritems():
                value_encoded = cls._make_marked_json(value)
                props.append("%s: %s" % (key, value_encoded))
            return "{%s}" % ",".join(props)
        elif isinstance(obj, basestring):
            return "r.N_(%s)" % json.dumps(obj)
        else:
            raise ValueError, "unsupported type"

    def get_source(self, **kw):
        permission_set_info = {k: v.info for k, v in
                               self.permission_sets.iteritems()}
        permissions = self._make_marked_json(permission_set_info)
        return "r.permissions = _.extend(r.permissions || {}, %s)" % permissions

    @property
    def dependencies(self):
        dependencies = set(super(PermissionsDataSource, self).dependencies)
        for permission_set in self.permission_sets.itervalues():
            dependencies.add(inspect.getsourcefile(permission_set))
        return list(dependencies)


class TemplateFileSource(DataSource, FileSource):
    """A JavaScript template file on disk."""
    def __init__(self, name, wrap="r.templates.set({content})"):
        DataSource.__init__(self, wrap)
        FileSource.__init__(self, name)
        self.name = name

    def get_content(self, use_built_statics=False):
        name, style = os.path.splitext(self.name)

        if use_built_statics:
            built_statics_path = get_built_statics_path()
            path = os.path.join(built_statics_path, 'static', 'js', self.name)
        else:
            path = locate_static_file(os.path.join('static', 'js', self.name))

        with open(path) as f:
            return [{
                "name": name,
                "style": style.lstrip('.'),
                "template": f.read(),
            }]


class LocaleSpecificSource(object):
    def get_localized_source(self, lang):
        raise NotImplementedError


class StringsSource(LocaleSpecificSource):
    """Translations sourced from a gettext catalog."""

    def __init__(self, keys):
        self.keys = keys

    invalid_formatting_specifier_re = re.compile(r"(?<!%)%\w|(?<!%)%\(\w+\)[^s]")
    def _check_formatting_specifiers(self, string):
        if not isinstance(string, basestring):
            return

        if self.invalid_formatting_specifier_re.search(string):
            raise ValueError("Invalid string formatting specifier: %r" % string)

    def get_localized_source(self, lang):
        catalog = get_catalog(lang)

        # relies on pyx files, so it can't be imported at global scope
        from r2.lib.utils import tup

        data = {}
        for key in self.keys:
            key = tup(key)[0]  # because the key for plurals is (sing, plur)
            self._check_formatting_specifiers(key)
            msg = catalog[key]

            if not msg or not msg.string:
                continue

            # jed expects to ignore the first value in the translations array
            # so we'll just make it null
            strings = tup(msg.string)
            data[key] = [None] + list(strings)
        return "r.i18n.addMessages(%s)" % json.dumps(data)


class PluralForms(LocaleSpecificSource):
    def get_localized_source(self, lang):
        catalog = get_catalog(lang)
        validate_plural_forms(catalog.plural_expr)
        return "r.i18n.setPluralForms('%s')" % catalog.plural_expr


class LocalizedModule(Module):
    """A module that generates localized code for each language.

    Strings marked for translation with one of the functions in i18n.js (viz.
    r._, r.P_, and r.N_) are extracted from the source and their translations
    are built into the compiled source.

    """

    def __init__(self, *args, **kwargs):
        self.localized_appendices = kwargs.pop("localized_appendices", [])
        Module.__init__(self, *args, **kwargs)

        for source in self.sources:
            if isinstance(source, LocalizedModule):
                self.localized_appendices.extend(source.localized_appendices)

    @staticmethod
    def languagize_path(path, lang):
        path_name, path_ext = os.path.splitext(path)
        return path_name + "." + lang + path_ext

    def build(self, minifier):
        Module.build(self, minifier)

        with open(self.destination_path) as f:
            reddit_source = f.read()

        localized_appendices = self.localized_appendices
        msgids = extract_javascript_msgids(reddit_source)
        if msgids:
            localized_appendices = localized_appendices + [StringsSource(msgids)]

        print >> sys.stderr, "Creating language-specific files:"
        for lang, unused in iter_langs():
            lang_path = LocalizedModule.languagize_path(
                self.destination_path, lang)

            # make sure we're not rewriting a different mangled file
            # via symlink
            if os.path.islink(lang_path):
                os.unlink(lang_path)

            with open(lang_path, "w") as out:
                print >> sys.stderr, "  " + lang_path
                out.write(reddit_source)
                for appendix in localized_appendices:
                    out.write(appendix.get_localized_source(lang) + ";")

    def use(self, **kwargs):
        from pylons.i18n import get_lang
        from r2.lib.template_helpers import static
        from r2.lib.filters import SC_OFF, SC_ON

        if g.uncompressedJS:
            if c.lang == "en" or c.lang not in g.all_languages:
                # in this case, the msgids *are* the translated strings and we
                # can save ourselves the pricey step of lexing the js source
                return Module.use(self, **kwargs)

            msgids = extract_javascript_msgids(Module.get_source(self))
            localized_appendices = self.localized_appendices + [StringsSource(msgids)]

            lines = [Module.use(self, **kwargs)]
            for appendix in localized_appendices:
                line = SC_OFF + inline_script_tag.format(
                    content=appendix.get_localized_source(c.lang)) + SC_ON
                lines.append(line)
            return "\n".join(lines)
        else:
            langs = get_lang() or [g.lang]
            url = LocalizedModule.languagize_path(self.name, langs[0])
            return script_tag.format(src=static(url), **kwargs)

    @property
    def outputs(self):
        for lang, unused in iter_langs():
            yield LocalizedModule.languagize_path(self.destination_path, lang)


_submodule = {}
module = {}

catch_errors = "try {{ {content} }} catch (err) {{ r.sendError('Error running module', '{name}', ':', err.toString()) }}"


_submodule["config"] = Module("_setup.js",
    "base.js",
    "setup.js",
    "hooks.js",
)

_submodule["utils"] = Module("_utils.js",
    "base.js",
    _submodule["config"],
    "utils.js",
)

_submodule["uibase"] = Module("_uibase.js",
    "base.js",
    "i18n.js",
    _submodule["utils"],
    "uibase.js",
)

_submodule["analytics"] = Module("_analytics.js",
    "base.js",
    _submodule["config"],
    _submodule["utils"],
    "events.js",
    "analytics.js",
)

_submodule["errors"] = Module("_errors.js",
    "base.js",
    "i18n.js",
    "errors.js",
)

_submodule["gate-popup"] = Module("_gate-popup.js",
    "base.js",
    _submodule["uibase"],
    _submodule["errors"],
    "gate-popup.js",
)

_submodule["timeouts"] = Module("_timeouts.js",
    "base.js",
    _submodule["config"],
    _submodule["analytics"],
    _submodule["gate-popup"],
    "access.js",
    "timeouts.js",
)

_submodule["locked"] = Module("_locked.js",
    "base.js",
    "access.js",
    _submodule["gate-popup"],
    "locked.js",
)

_submodule["archived"] = Module("_archived.js",
    "base.js",
    "hooks.js",
    _submodule["gate-popup"],
    "archived.js",
)

module["gtm-jail"] = Module("gtm-jail.js",
    "lib/json2.js",
    "custom-event.js",
    "frames.js",
    "google-tag-manager/gtm-jail-listener.js",
)


module["gtm"] = Module("gtm.js",
    "lib/json2.js",
    "custom-event.js",
    "frames.js",
    "google-tag-manager/gtm-listener.js",
)


module["reddit-embed-base"] = Module("reddit-embed-base.js",
    "lib/es5-shim.js",
    "lib/json2.js",
    "base.js",
    "uuid.js",
    "custom-event.js",
    "frames.js",
    "embed/utils.js",
    "embed/pixel-tracking.js",
)


module["reddit-embed"] = Module("reddit-embed.js",
    module["reddit-embed-base"],
    "embed/embed.js",
)


module["comment-embed"] = Module("comment-embed.js",
    module["reddit-embed-base"],
    "embed/comment-embed.js",
)


module["reddit-init-base"] = LocalizedModule("reddit-init-base.js",
    "lib/modernizr.js",
    "lib/json2.js",
    "lib/underscore-1.4.4-1.js",
    "lib/store.js",
    "lib/jed.js",
    "lib/bootstrap.modal.js",
    "lib/bootstrap.transition.js",
    "lib/bootstrap.tooltip.js",
    "lib/reddit-client-lib.js",
    "lib/jquery.cookie.js",
    "lib/event-tracker.js",
    "lib/hmac-sha256.js",
    "do-not-track.js",
    "bootstrap.tooltip.extension.js",
    "base.js",
    "uuid.js",
    "hooks.js",
    "setup.js",
    "migrate-global-reddit.js",
    "ajax.js",
    "safe-store.js",
    "preload.js",
    "logging.js",
    "client-error-logger.js",
    "voting.js",
    "uibase.js",
    "i18n.js",
    "utils.js",
    "analytics.js",
    "events.js",
    "access.js",
    "reddit-init-hook.js",
    "jquery.reddit.js",
    "stateify.js",
    "validator.js",
    "strength-meter.js",
    "toggles.js",
    "reddit.js",
    "sr-autocomplete.js",
    "spotlight.js",
    localized_appendices=[
        PluralForms(),
    ],
)

module["reddit-init-legacy"] = LocalizedModule("reddit-init-legacy.js",
    "lib/html5shiv.js",
    "lib/jquery-1.11.1.js",
    "lib/es5-shim.js",
    "lib/es5-sham.js",
    module["reddit-init-base"],
    wrap=catch_errors,
)

module["reddit-init"] = LocalizedModule("reddit-init.js",
    "lib/jquery-2.1.1.js",
    "lib/es5-shim.js",
    module["reddit-init-base"],
    wrap=catch_errors,
)

module["expando-nsfw-flow"] = Module("expando-nsfw-flow.js",
    TemplateFileSource('ui/formbar.html'),
    "ui/formbar.js",
    TemplateFileSource('expando/nsfwgate.html'),
    "expando/nsfwflow.js",
)

module["reddit"] = LocalizedModule("reddit.js",
    "lib/jquery.url.js",
    "lib/backbone-1.0.0.js",
    "custom-event.js",
    "frames.js",
    "embed/utils.js",
    "embed/pixel-tracking.js",
    "embed/comment-embed.js",
    "google-tag-manager/gtm.js",
    "backbone-init.js",
    "timings.js",
    "templates.js",
    "scrollupdater.js",
    "timetext.js",
    "ui.js",
    "popup.js",
    "login.js",
    _submodule["locked"],
    _submodule["timeouts"],
    _submodule["archived"],
    "newsletter.js",
    "flair.js",
    "report.js",
    "interestbar.js",
    "visited.js",
    "wiki.js",
    "apps.js",
    "gold.js",
    "multi.js",
    "filter.js",
    "recommender.js",
    "action-forms.js",
    "embed.js",
    "post-sharing.js",
    "expando.js",
    # inline expando-nsfw-flow.js module here when unflagged
    "saved.js",
    "cache-poisoning-detection.js",
    "messages.js",
    "reddit-hook.js",
    "link-click-tracking.js",
    "warn-on-unload.js",
    PermissionsDataSource({
        "moderator": ModeratorPermissionSet,
        "moderator_invite": ModeratorPermissionSet,
    }),
    wrap=catch_errors,
    filter_module=module["reddit-init-base"],
)

module["modtools"] = Module("modtools.js",
    "errors.js",
    "models/validators.js",
    "models/subreddit-rule.js",
    "edit-subreddit-rules.js",
    wrap=catch_errors,
)

module["admin"] = Module("admin.js",
    # include Backbone and timings so they are available early to render admin bar fast.
    "lib/backbone-1.0.0.js",
    "timings.js",
    "adminbar.js",
)

module["mobile"] = LocalizedModule("mobile.js",
    module["reddit"],
    "lib/jquery.lazyload.js",
    "compact.js",
    filter_module=module["reddit-init-base"],
)


module["policies"] = Module("policies.js",
    "policies.js",
)


module["sponsored"] = LocalizedModule("sponsored.js",
    "lib/ui.core.js",
    "lib/ui.datepicker.js",
    "lib/react-with-addons-0.11.2.js",
    "image-upload.js",
    "sponsored.js"
)


module["timeseries"] = Module("timeseries.js",
    "lib/jquery.flot.js",
    "lib/jquery.flot.time.js",
    "timeseries.js",
)


module["timeseries-ie"] = Module("timeseries-ie.js",
    "lib/excanvas.min.js",
    module["timeseries"],
)


module["traffic"] = LocalizedModule("traffic.js",
    "traffic.js",
)


module["qrcode"] = Module("qrcode.js",
    "lib/jquery.qrcode.min.js",
    "qrcode.js",
)


module["highlight"] = Module("highlight.js",
    "lib/highlight.pack.js",
    "highlight.js",
)

module["messagecompose"] = Module("messagecompose.js",
    # jquery, hooks, ajax, preload
    "messagecompose.js")

module["less"] = Module('less.js',
    'lib/less-1.4.2.js',
    should_compile=False,
)

# This needs to be separate module because we need it to load on old / bad
# browsers that choke on reddit.js
module["https-tester"] = Module("https-tester.js",
    "base.js",
    "uuid.js",
    "https-tester.js"
)

def src(*names, **kwargs):
    sources = []

    for name in names:
        urls = module[name].url(**kwargs)

        if isinstance(urls, str) or isinstance(urls, unicode):
            sources.append(urls)
        else:
            for url in list(urls):
                if isinstance(url, list):
                    sources.extend(url)
                else:
                    sources.append(url)

    return sources

def use(*names, **kwargs):
    return "\n".join(module[name].use(**kwargs) for name in names)


def load_plugin_modules(plugins=None):
    if not plugins:
        plugins = PluginLoader()
    for plugin in plugins:
        plugin.add_js(module)


commands = {}
def build_command(fn):
    def wrapped(*args):
        load_plugin_modules()
        fn(*args)
    commands[fn.__name__] = wrapped
    return wrapped


@build_command
def enumerate_modules():
    for name, m in module.iteritems():
        print name


@build_command
def dependencies(name):
    for dep in module[name].dependencies:
        print dep


@build_command
def enumerate_outputs(*names):
    if names:
        modules = [module[name] for name in names]
    else:
        modules = module.itervalues()

    for m in modules:
        for output in m.outputs:
            print output


@build_command
def build_module(name):
    minifier = Uglify()
    module[name].build(minifier)


if __name__ == "__main__":
    commands[sys.argv[1]](*sys.argv[2:])
