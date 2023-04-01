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

def select_provider(config_parser, working_set, type, name):
    """Given a type and name return an instantiated provider.

    Providers are objects that abstract away an external service. They are
    looked up via the pkg_resources system which means that they may not even
    be implemented in the main reddit code.

    A provider must implement the expected interface, be registered via
    setuptools with the right entry point, and have a no-argument constructor.

    If a provider class has an attribute `config` which is a ConfigParse-style
    spec dictionary, the spec will be added to the main configuration parser
    similarly to the plugin system. This allows providers to declare extra
    config options for parsing.

    """

    try:
        entry_point = working_set.iter_entry_points(type, name).next()
    except StopIteration:
        raise Exception("unknown %s provider: %r" % (type, name))
    else:
        provider_cls = entry_point.load()

    if hasattr(provider_cls, "config"):
        config_parser.add_spec(provider_cls.config)

    return provider_cls()
