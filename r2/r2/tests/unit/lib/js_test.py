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
# All portions of the code written by reddit are Copyright (c) 2006-2016 reddit
# Inc. All Rights Reserved.
###############################################################################

import unittest

from r2.lib import js


def concat_sources(sources):
    return ";".join(sources)


class TestFileSource(js.FileSource):
    def get_source(self, *args, **kwargs):
        return self.name


class TestModule(js.Module):
    def get_default_source(self, source):
        return TestFileSource(source)

    def build(self, *args, **kwargs):
        sources = self.get_flattened_sources([])
        sources = [s.get_source() for s in sources]
        return concat_sources(sources)


class TestModuleGetFlattenedSources(unittest.TestCase):
    def test_flat_modules_include_all_sources(self):
        test_files = ["foo.js", "bar.js", "baz.js", "qux.js"]
        test_module = TestModule("test_module", *test_files)
        self.assertEqual(test_module.build(), concat_sources(test_files))

    def test_nested_modules_include_all_sources(self):
        test_files_a = ["foo.js", "bar.js"]
        test_module_a = TestModule("test_module_a", *test_files_a)
        test_files_b = ["baz.js", "qux.js"]
        test_module_b = TestModule("test_module_b", *test_files_b)
        test_module = TestModule("test_mobule", test_module_a, test_module_b)
        self.assertEqual(test_module.build(), concat_sources(test_files_a + test_files_b))

    def test_flat_modules_only_include_sources_once(self):
        test_files = ["foo.js", "bar.js", "baz.js", "qux.js"]
        test_files_dup = test_files * 2
        test_module = TestModule("test_module", *test_files_dup)
        self.assertEqual(test_module.build(), concat_sources(test_files))

    def test_nested_modules_only_include_sources_once(self):
        test_files = ["foo.js", "bar.js", "baz.js", "qux.js"]
        test_module_a = TestModule("test_module_a", *test_files)
        test_module_b = TestModule("test_module_b", *test_files)
        test_module = TestModule("test_mobule", test_module_a, test_module_b)
        self.assertEqual(test_module.build(), concat_sources(test_files))

    def test_filtered_modules_do_not_include_filtered_sources(self):
        test_files = ["foo.js", "bar.js"]
        filtered_files = ["baz.js", "qux.js"]
        all_files = test_files + filtered_files
        filter_module = TestModule("filter_module", *filtered_files)
        test_module = TestModule("test_module", filter_module=filter_module, *all_files)
        self.assertEqual(test_module.build(), concat_sources(test_files))
