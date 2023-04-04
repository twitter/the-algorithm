#!/usr/bin/env python3

def add_custom_arguments(parser):
    group = parser.add_mutually_exclusive_group(required=False)
    group.add_argument('-j', dest='lang', action='store_const', const='jp',
            help="Set version to JP.")
    group.add_argument('-u', dest='lang', action='store_const', const='us',
            help="Set version to US.")
    group.add_argument('-e', dest='lang', action='store_const', const='eu',
            help="Set version to EU.")
    group.add_argument('-s', dest='lang', action='store_const', const='sh',
            help="Set version to SH.")

def apply(config, args):
    lang = args.lang or 'us'
    config['mapfile'] = f'build/{lang}/sm64.{lang}.map'
    config['myimg'] = f'build/{lang}/sm64.{lang}.z64'
    config['baseimg'] = f'baserom.{lang}.z64'
    config['makeflags'] = [f'VERSION={lang}']
    config['source_directories'] = ['src', 'include', 'lib', 'lib/src', 'asm', 'rsp']
