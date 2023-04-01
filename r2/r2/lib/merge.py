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

import subprocess
import tempfile
import difflib
from pylons.i18n import _
from pylons import app_globals as g


MAX_DIFF_LINE_LENGTH = 4000


class ConflictException(Exception):
    def __init__(self, new, your, original):
        self.your = your
        self.new = new
        self.original = original
        self.htmldiff = make_htmldiff(new, your, _("current edit"), _("your edit"))
        Exception.__init__(self)


def make_htmldiff(a, b, adesc, bdesc):
    diffcontent = difflib.HtmlDiff(wrapcolumn=60)

    def truncate(line):
        if len(line) > MAX_DIFF_LINE_LENGTH:
            line = line[:MAX_DIFF_LINE_LENGTH] + "..."
        return line
    return diffcontent.make_table([truncate(i) for i in a.splitlines()],
                                  [truncate(i) for i in b.splitlines()],
                                  fromdesc=adesc,
                                  todesc=bdesc,
                                  context=3)

def threewaymerge(original, a, b):
    temp_dir = g.diff3_temp_location if g.diff3_temp_location else None
    data = [a, original, b]
    files = []
    try:
        for d in data:
            f = tempfile.NamedTemporaryFile(dir=temp_dir)
            f.write(d.encode('utf-8'))
            f.flush()
            files.append(f)
        try:
            final = subprocess.check_output(["diff3", "-a", "--merge"] + [f.name for f in files])
        except subprocess.CalledProcessError:
            raise ConflictException(b, a, original)
    finally:
        for f in files:
            f.close()
    return final.decode('utf-8')

if __name__ == "__main__":
    class test_globals:
        diff3_temp_location = None
    
    g = test_globals()
    
    original = "Hello people of the human rance\n\nHow are you tday"
    a = "Hello people of the human rance\n\nHow are you today"
    b = "Hello people of the human race\n\nHow are you tday"
    
    print threewaymerge(original, a, b)
    
    g.diff3_temp_location = '/dev/shm'
    
    print threewaymerge(original, a, b)
