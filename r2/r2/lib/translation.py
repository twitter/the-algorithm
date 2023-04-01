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

import json
import os
import token
import tokenize

from babel.messages.extract import extract_javascript
from cStringIO import StringIO

import babel.messages.frontend
import babel.messages.pofile
import pylons

from pylons.i18n.translation import translation, LanguageError, NullTranslations

try:
    import reddit_i18n
except ImportError:
    I18N_PATH = ''
else:
    I18N_PATH = os.path.dirname(reddit_i18n.__file__)

# Different from the default lang (as defined in the ini file)
# Source language is what is in the source code
SOURCE_LANG = 'en'


def _get_translator(lang, graceful_fail=False, **kwargs):
    """Utility method to get a valid translator object from a language name"""
    from pylons import config

    if not isinstance(lang, list):
        lang = [lang]
    try:
        translator = translation(config['pylons.package'], I18N_PATH,
                                 languages=lang, **kwargs)
    except IOError, ioe:
        if graceful_fail:
            translator = NullTranslations()
        else:
            raise LanguageError('IOError: %s' % ioe)
    translator.pylons_lang = lang
    return translator


def set_lang(lang, graceful_fail=False, fallback_lang=None, **kwargs):
    """Set the i18n language used"""
    registry = pylons.request.environ['paste.registry']
    if not lang:
        registry.replace(pylons.translator, NullTranslations())
    else:
        translator = _get_translator(lang, graceful_fail = graceful_fail, **kwargs)
        base_lang, is_dialect, dialect = lang.partition("-")
        if is_dialect:
            try:
                base_translator = _get_translator(base_lang)
            except LanguageError:
                pass
            else:
                translator.add_fallback(base_translator)
        if fallback_lang:
            fallback_translator = _get_translator(fallback_lang,
                                                  graceful_fail=True)
            translator.add_fallback(fallback_translator)
        registry.replace(pylons.translator, translator)


def load_data(lang_path, domain, extension='data'):
    filename = os.path.join(lang_path, domain + '.' + extension)
    with open(filename) as datafile:
        data = json.load(datafile)
    return data


def iter_langs(base_path=I18N_PATH):
    if base_path:
        # sorted() so that get_active_langs can check completion
        # data on "base" languages of a dialect
        for lang in sorted(os.listdir(base_path)):
            full_path = os.path.join(base_path, lang, 'LC_MESSAGES')
            if os.path.isdir(full_path):
                yield lang, full_path


def get_active_langs(config, path=I18N_PATH, default_lang='en'):
    trans = []
    trans_name = {}
    completions = {}
    domain = config['pylons.package']

    for lang, lang_path in iter_langs(path):
        data = load_data(lang_path, domain)
        name = [data['name'], '']
        if data['_is_enabled'] and lang != default_lang:
            trans.append(lang)
            completion = float(data['num_completed']) / float(data['num_total'])
            completions[lang] = completion
            # This relies on iter_langs hitting the base_lang first
            base_lang, is_dialect, dialect = lang.partition("-")
            if is_dialect:
                if base_lang == SOURCE_LANG:
                    # Source language has to be 100% complete
                    base_completion = 1.0
                else:
                    base_completion = completions.get(base_lang, 0)
                completion = max(completion, base_completion)
            if completion < .5:
                name[1] = ' (*)'
        trans_name[lang] = name
    trans.sort()
    # insert the default language at the top of the list
    trans.insert(0, default_lang)
    if default_lang not in trans_name:
        trans_name[default_lang] = default_lang
    return trans, trans_name


def get_catalog(lang):
    """Return a Catalog object given the language code."""
    path = os.path.join(I18N_PATH, lang, "LC_MESSAGES", "r2.po")
    with open(path, "r") as f:
        return babel.messages.pofile.read_po(f)


def validate_plural_forms(plural_forms_str):
    """Ensure the gettext plural forms expression supplied is valid."""

    # this code is taken from the python stdlib; gettext.py:c2py
    tokens = tokenize.generate_tokens(StringIO(plural_forms_str).readline)

    try:
        danger = [x for x in tokens if x[0] == token.NAME and x[1] != 'n']
    except tokenize.TokenError:
        raise ValueError, \
              'plural forms expression error, maybe unbalanced parenthesis'
    else:
        if danger:
            raise ValueError, 'plural forms expression could be dangerous'


def extract_javascript_msgids(source):
    """Return message ids of translateable strings in JS source."""

    extracted = extract_javascript(
        fileobj=StringIO(source),
        keywords={
            "_": None,
            "P_": (1, 2),
            "N_": None,
            "NP_": (1, 2),
        },
        comment_tags={},
        options={},
    )

    return [msg_id for line, func, msg_id, comments in extracted]
