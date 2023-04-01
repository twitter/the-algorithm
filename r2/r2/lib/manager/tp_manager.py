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


import hashlib
import inspect

from mako.exceptions import TemplateLookupException
from mako.template import Template as mTemplate
from pylons import app_globals as g


NULL_TEMPLATE = mTemplate("")
NULL_TEMPLATE.is_null = True

class tp_manager:
    def __init__(self, template_cls=mTemplate):
        self.templates = {}
        self.Template = template_cls
        self.cache_override_styles = set()

    def add_handler(self, name, style, handler):
        key = (name.lower(), style.lower())
        self.templates[key] = handler

        # a template has been manually specified for this style so record that
        # we should override g.reload_templates when retrieving templates
        self.cache_override_styles.add(style.lower())

    def cache_template(self, cls, style, template):
        use_cache = not g.reload_templates
        if use_cache:
            if (not hasattr(template, "hash") and
                    getattr(template, "filename", None)):
                with open(template.filename, 'r') as handle:
                    template.hash = hashlib.sha1(handle.read()).hexdigest()
            key = (cls.__name__.lower(), style)
            self.templates[key] = template

    def get_template(self, cls, style):
        name = cls.__name__.lower()
        use_cache = not g.reload_templates

        if use_cache or style.lower() in self.cache_override_styles:
            key = (name, style)
            template = self.templates.get(key)
            if template:
                return template

        filename = "/%s.%s" % (name, style)
        try:
            template = g.mako_lookup.get_template(filename)
        except TemplateLookupException:
            return

        self.cache_template(cls, style, template)

        return template

    def get(self, thing, style):
        if not isinstance(thing, type(object)):
            thing = thing.__class__

        style = style.lower()
        template = self.get_template(thing, style)
        if template:
            return template

        # walk back through base classes to find a template
        for cls in inspect.getmro(thing)[1:]:
            template = self.get_template(cls, style)
            if template:
                break
        else:
            # didn't find a template, use the null template
            template = NULL_TEMPLATE

        # cache template for thing so we don't need to introspect on subsequent
        # calls
        self.cache_template(thing, style, template)

        return template

