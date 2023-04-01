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
"""Parse and validate a safe subset of CSS.

The goal of this validation is not to ensure functionally correct stylesheets
but rather that the stylesheet is safe to show to downstream users.  This
includes:

    * not generating requests to third party hosts (information leak)
    * xss via strange syntax in buggy browsers

Beyond that, every effort is made to allow the full gamut of modern CSS.

"""

import itertools
import re
import unicodedata

import tinycss2

from pylons.i18n import N_

from r2.lib.contrib import rcssmin
from r2.lib.utils import tup


__all__ = ["validate_css"]


SIMPLE_TOKEN_TYPES = {
    "dimension",
    "hash",
    "ident",
    "literal",
    "number",
    "percentage",
    "string",
    "whitespace",
}


VENDOR_PREFIXES = {
    "-apple-",
    "-khtml-",
    "-moz-",
    "-ms-",
    "-o-",
    "-webkit-",
}
assert all(prefix == prefix.lower() for prefix in VENDOR_PREFIXES)


SAFE_PROPERTIES = {
    "align-content",
    "align-items",
    "align-self",
    "animation",
    "animation-delay",
    "animation-direction",
    "animation-duration",
    "animation-fill-mode",
    "animation-iteration-count",
    "animation-name",
    "animation-play-state",
    "animation-timing-function",
    "appearance",
    "backface-visibility",
    "background",
    "background-attachment",
    "background-blend-mode",
    "background-clip",
    "background-color",
    "background-image",
    "background-origin",
    "background-position",
    "background-position-x",
    "background-position-y",
    "background-repeat",
    "background-size",
    "border",
    "border-bottom",
    "border-bottom-color",
    "border-bottom-left-radius",
    "border-bottom-right-radius",
    "border-bottom-style",
    "border-bottom-width",
    "border-collapse",
    "border-color",
    "border-image",
    "border-image-outset",
    "border-image-repeat",
    "border-image-slice",
    "border-image-source",
    "border-image-width",
    "border-left",
    "border-left-color",
    "border-left-style",
    "border-left-width",
    "border-radius",
    "border-radius-bottomleft",
    "border-radius-bottomright",
    "border-radius-topleft",
    "border-radius-topright",
    "border-right",
    "border-right-color",
    "border-right-style",
    "border-right-width",
    "border-spacing",
    "border-style",
    "border-top",
    "border-top-color",
    "border-top-left-radius",
    "border-top-right-radius",
    "border-top-style",
    "border-top-width",
    "border-width",
    "bottom",
    "box-shadow",
    "box-sizing",
    "caption-side",
    "clear",
    "clip",
    "clip-path",
    "color",
    "content",
    "counter-increment",
    "counter-reset",
    "cue",
    "cue-after",
    "cue-before",
    "cursor",
    "direction",
    "display",
    "elevation",
    "empty-cells",
    # the "filter" property cannot be safely added while IE9 is allowed to
    # use subreddit stylesheets. see explanation here:
    # https://github.com/reddit/reddit/pull/1058#issuecomment-76466180
    # "filter",
    "flex",
    "flex-align",
    "flex-basis",
    "flex-direction",
    "flex-flow",
    "flex-grow",
    "flex-item-align",
    "flex-line-pack",
    "flex-order",
    "flex-pack",
    "flex-shrink",
    "flex-wrap",
    "float",
    "font",
    "font-family",
    "font-size",
    "font-style",
    "font-variant",
    "font-weight",
    "grid",
    "grid-area",
    "grid-auto-columns",
    "grid-auto-flow",
    "grid-auto-position",
    "grid-auto-rows",
    "grid-column",
    "grid-column-start",
    "grid-column-end",
    "grid-row",
    "grid-row-start",
    "grid-row-end",
    "grid-template",
    "grid-template-areas",
    "grid-template-rows",
    "grid-template-columns",
    "hanging-punctuation",
    "height",
    "hyphens",
    "image-orientation",
    "image-rendering",
    "image-resolution",
    "justify-content",
    "left",
    "letter-spacing",
    "line-break",
    "line-height",
    "list-style",
    "list-style-image",
    "list-style-position",
    "list-style-type",
    "margin",
    "margin-bottom",
    "margin-left",
    "margin-right",
    "margin-top",
    "max-height",
    "max-width",
    "mask",
    "mask-border",
    "mask-border-mode",
    "mask-border-outset",
    "mask-border-repeat",
    "mask-border-source",
    "mask-border-slice",
    "mask-border-width",
    "mask-clip",
    "mask-composite",
    "mask-image",
    "mask-mode",
    "mask-origin",
    "mask-position",
    "mask-repeat",
    "mask-size",
    "min-height",
    "min-width",
    "mix-blend-mode",
    "opacity",
    "order",
    "orphans",
    "outline",
    "outline-color",
    "outline-offset",
    "outline-style",
    "outline-width",
    "overflow",
    "overflow-wrap",
    "overflow-x",
    "overflow-y",
    "padding",
    "padding-bottom",
    "padding-left",
    "padding-right",
    "padding-top",
    "page-break-after",
    "page-break-before",
    "page-break-inside",
    "pause",
    "pause-after",
    "pause-before",
    "perspective",
    "perspective-origin",
    "pitch",
    "pitch-range",
    "play-during",
    "pointer-events",
    "position",
    "quotes",
    "resize",
    "richness",
    "right",
    "speak",
    "speak-header",
    "speak-numeral",
    "speak-punctuation",
    "speech-rate",
    "stress",
    "table-layout",
    "tab-size",
    "text-align",
    "text-align-last",
    "text-decoration",
    "text-decoration-color",
    "text-decoration-line",
    "text-decoration-skip",
    "text-decoration-style",
    "text-indent",
    "text-justify",
    "text-overflow",
    "text-rendering",
    "text-shadow",
    "text-size-adjust",
    "text-space-collapse",
    "text-transform",
    "text-underline-position",
    "text-wrap",
    "top",
    "transform",
    "transform-origin",
    "transform-style",
    "transition",
    "transition-delay",
    "transition-duration",
    "transition-property",
    "transition-timing-function",
    "unicode-bidi",
    "vertical-align",
    "visibility",
    "voice-family",
    "volume",
    "white-space",
    "widows",
    "width",
    "will-change",
    "word-break",
    "word-spacing",
    "z-index",
}
assert all(property == property.lower() for property in SAFE_PROPERTIES)


SAFE_FUNCTIONS = {
    "attr",
    "calc",
    "circle",
    "counter",
    "counters",
    "cubic-bezier",
    "ellipse",
    "hsl",
    "hsla",
    "lang",
    "line",
    "linear-gradient",
    "matrix",
    "matrix3d",
    "not",
    "nth-child",
    "nth-last-child",
    "nth-last-of-type",
    "nth-of-type",
    "perspective",
    "polygon",
    "polyline",
    "radial-gradient",
    "rect",
    "repeating-linear-gradient",
    "repeating-radial-gradient",
    "rgb",
    "rgba",
    "rotate",
    "rotate3d",
    "rotatex",
    "rotatey",
    "rotatez",
    "scale",
    "scale3d",
    "scalex",
    "scaley",
    "scalez",
    "skewx",
    "skewy",
    "steps",
    "translate",
    "translate3d",
    "translatex",
    "translatey",
    "translatez",
}
assert all(function == function.lower() for function in SAFE_FUNCTIONS)


ERROR_MESSAGES = {
    "IMAGE_NOT_FOUND": N_('no image found with name "%(name)s"'),
    "NON_PLACEHOLDER_URL": N_("only uploaded images are allowed; reference "
                              "them with the %%%%imagename%%%% system"),
    "SYNTAX_ERROR": N_("syntax error: %(message)s"),
    "UNKNOWN_AT_RULE": N_("@%(keyword)s is not allowed"),
    "UNKNOWN_PROPERTY": N_('unknown property "%(name)s"'),
    "UNKNOWN_FUNCTION": N_('unknown function "%(function)s"'),
    "UNEXPECTED_TOKEN": N_('unexpected token "%(token)s"'),
    "BACKSLASH": N_("backslashes are not allowed"),
    "CONTROL_CHARACTER": N_("control characters are not allowed"),
    "TOO_BIG": N_("the stylesheet is too big. maximum size: %(size)d KiB"),
}


MAX_SIZE_KIB = 100
SUBREDDIT_IMAGE_URL_PLACEHOLDER = re.compile(r"\A%%([a-zA-Z0-9\-]+)%%\Z")


def strip_vendor_prefix(identifier):
    for prefix in VENDOR_PREFIXES:
        if identifier.startswith(prefix):
            return identifier[len(prefix):]
    return identifier


class ValidationError(object):
    def __init__(self, line_number, error_code, message_params=None):
        self.line = line_number
        self.error_code = error_code
        self.message_params = message_params or {}
        # note: _source_lines is added to these objects by the parser

    @property
    def offending_line(self):
        return self._source_lines[self.line - 1]

    @property
    def message_key(self):
        return ERROR_MESSAGES[self.error_code]


class StylesheetValidator(object):
    def __init__(self, images):
        self.images = images

    def validate_url(self, url_node):
        m = SUBREDDIT_IMAGE_URL_PLACEHOLDER.match(url_node.value)
        if not m:
            return ValidationError(url_node.source_line, "NON_PLACEHOLDER_URL")

        image_name = m.group(1)
        if image_name not in self.images:
            return ValidationError(url_node.source_line, "IMAGE_NOT_FOUND",
                                   {"name": image_name})

        # rewrite the url value to the actual url of the image
        url_node.value = self.images[image_name]

    def validate_function(self, function_node):
        function_name = strip_vendor_prefix(function_node.lower_name)

        if function_name not in SAFE_FUNCTIONS:
            return ValidationError(function_node.source_line,
                                   "UNKNOWN_FUNCTION",
                                   {"function": function_node.name})
        # property: attr(something url)
        # https://developer.mozilla.org/en-US/docs/Web/CSS/attr
        elif function_name == "attr":
            for argument in function_node.arguments:
                if argument.type == "ident" and argument.lower_value == "url":
                    return ValidationError(argument.source_line,
                                           "NON_PLACEHOLDER_URL")

        return self.validate_component_values(function_node.arguments)

    def validate_block(self, block):
        return self.validate_component_values(block.content)

    def validate_component_values(self, component_values):
        return self.validate_list(component_values, {
            # {} blocks are technically part of component values but i don't
            # know of any actual valid uses for them in selectors etc. and they
            # can cause issues with e.g.
            # Safari 5: p[foo=bar{}*{background:green}]{background:red}
            "[] block": self.validate_block,
            "() block": self.validate_block,
            "url": self.validate_url,
            "function": self.validate_function,
        }, ignored_types=SIMPLE_TOKEN_TYPES)

    def validate_declaration(self, declaration):
        if strip_vendor_prefix(declaration.lower_name) not in SAFE_PROPERTIES:
            return ValidationError(declaration.source_line, "UNKNOWN_PROPERTY",
                                   {"name": declaration.name})
        return self.validate_component_values(declaration.value)

    def validate_declaration_list(self, declarations):
        return self.validate_list(declarations, {
            "at-rule": self.validate_at_rule,
            "declaration": self.validate_declaration,
        })

    def validate_qualified_rule(self, rule):
        prelude_errors = self.validate_component_values(rule.prelude)
        declarations = tinycss2.parse_declaration_list(rule.content)
        declaration_errors = self.validate_declaration_list(declarations)
        return itertools.chain(prelude_errors, declaration_errors)

    def validate_at_rule(self, rule):
        prelude_errors = self.validate_component_values(rule.prelude)

        keyword = strip_vendor_prefix(rule.lower_at_keyword)

        if keyword in ("media", "keyframes"):
            rules = tinycss2.parse_rule_list(rule.content)
            rule_errors = self.validate_rule_list(rules)
        elif keyword == "page":
            rule_errors = self.validate_qualified_rule(rule)
        else:
            return ValidationError(rule.source_line, "UNKNOWN_AT_RULE",
                                   {"keyword": rule.at_keyword})

        return itertools.chain(prelude_errors, rule_errors)

    def validate_rule_list(self, rules):
        return self.validate_list(rules, {
            "qualified-rule": self.validate_qualified_rule,
            "at-rule": self.validate_at_rule,
        })

    def validate_list(self, nodes, validators_by_type, ignored_types=None):
        for node in nodes:
            if node.type == "error":
                yield ValidationError(node.source_line, "SYNTAX_ERROR",
                                      {"message": node.message})
                continue
            elif node.type == "literal":
                if node.value == ";":
                    # if we're seeing a semicolon as a literal, it's in a place
                    # that doesn't fit naturally in the syntax.
                    # Safari 5 will treat this as two color properties:
                    # color: calc(;color:red;);
                    message = "semicolons are not allowed in this context"
                    yield ValidationError(node.source_line, "SYNTAX_ERROR",
                                          {"message": message})
                    continue

            validator = validators_by_type.get(node.type)

            if validator:
                for error in tup(validator(node)):
                    if error:
                        yield error
            else:
                if not ignored_types or node.type not in ignored_types:
                    yield ValidationError(node.source_line,
                                          "UNEXPECTED_TOKEN",
                                          {"token": node.type})

    def check_for_evil_codepoints(self, source_lines):
        for line_number, line_text in enumerate(source_lines, start=1):
            for codepoint in line_text:
                # IE<8: *{color: expression\28 alert\28 1 \29 \29 }
                if codepoint == "\\":
                    yield ValidationError(line_number, "BACKSLASH")
                    break
                # accept these characters that get classified as control
                elif codepoint in ("\t", "\n", "\r"):
                    continue
                # Safari: *{font-family:'foobar\x03;background:url(evil);';}
                elif unicodedata.category(codepoint).startswith("C"):
                    yield ValidationError(line_number, "CONTROL_CHARACTER")
                    break

    def parse_and_validate(self, stylesheet_source):
        if len(stylesheet_source) > (MAX_SIZE_KIB * 1024):
            return "", [ValidationError(0, "TOO_BIG", {"size": MAX_SIZE_KIB})]

        nodes = tinycss2.parse_stylesheet(stylesheet_source)

        source_lines = stylesheet_source.splitlines()

        backslash_errors = self.check_for_evil_codepoints(source_lines)
        validation_errors = self.validate_rule_list(nodes)

        errors = []
        for error in itertools.chain(backslash_errors, validation_errors):
            error._source_lines = source_lines
            errors.append(error)
        errors.sort(key=lambda e: e.line)

        if not errors:
            serialized = rcssmin.cssmin(tinycss2.serialize(nodes))
        else:
            serialized = ""

        return serialized.encode("utf-8"), errors


def validate_css(stylesheet, images):
    """Validate and re-serialize the user submitted stylesheet.

    images is a mapping of subreddit image names to their URLs.  The
    re-serialized stylesheet will have %%name%% tokens replaced with their
    appropriate URLs.

    The return value is a two-tuple of the re-serialized (and minified)
    stylesheet and a list of errors.  If the list is empty, the stylesheet is
    valid.

    """
    assert isinstance(stylesheet, unicode)
    validator = StylesheetValidator(images)
    return validator.parse_and_validate(stylesheet)
