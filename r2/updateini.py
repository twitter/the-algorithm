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

from ConfigParser import MissingSectionHeaderError
from StringIO import StringIO
import sys

from r2.lib.utils import parse_ini_file

HEADER = '''
# YOU DO NOT NEED TO EDIT THIS FILE
# This is a generated file. To update the configuration,
# edit the *.update file of the same name, and then
# run 'make ini'
# Configuration settings in the *.update file will override
# or be added to the base 'example.ini' file.
'''

def main(source_ini, update_ini):
    with open(source_ini) as source:
        parser = parse_ini_file(source)
    with open(update_ini) as f:
        updates = f.read()
    try:
        # Existing *.update files don't include section
        # headers; inject a [DEFAULT] header if the parsing
        # fails
        parser.readfp(StringIO(updates))
    except MissingSectionHeaderError:
        updates = "[DEFAULT]\n" + updates
        parser.readfp(StringIO(updates))
    print HEADER
    parser.write(sys.stdout)

if __name__ == '__main__':
    args = sys.argv
    if len(args) != 3:
        print 'usage: %s [source] [update]' % sys.argv[0]
        sys.exit(1)
    else:
        main(sys.argv[1], sys.argv[2])
