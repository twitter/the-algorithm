# frozen_string_literal: true

require 'json'
require 'cgi'

if ARGV.size < 2
  puts "
    Usage: ruby requests_summary_to_json.rb <HTML_FILE> ... <JSON_OUTPUT>

    Takes a variable number of arguments. The last argument specifies the
    JSON file to be used as the output; the first N - 1 arguments specify
    a list of HTML files, downloaded from the Requests Summary of an
    existing exchange on AO3. The JSON is outputted in a format suitable
    for load_json_challenge.rb. It's assumed that every participant's offers
    are identical to their requests.

    This script will handle Fandoms, Characters, Relationships, and
    Freeforms, but not Categories, Ratings, or Warnings.

    (Note that this script doesn't require Rails, only Ruby.)
  "
  exit
end

request_files = ARGV[0..-2]
json_output = ARGV[-1]

PROMPT_DELIMETER = '<li class="prompt blurb group" role="article">'
BYLINE_START = '<h4 class="heading">'
BYLINE_END = '</h4>'
FANDOM_START = '<span class="landmark">Fandom:</span>'
FANDOM_END = '&nbsp;'
TAGS_START = '<ul class="tags commas">'
TAGS_END = '</ul>'

def load_user(text)
  byline = text.split(BYLINE_START)[1].split(BYLINE_END)[0]
  user = byline.split(" by ")[1].strip
  user = user.split("(")[1].split(")")[0] if user.include? "("
  CGI.unescapeHTML(user)
end

def load_fandoms(text)
  fandom_html = text.split(FANDOM_START)[1].split(FANDOM_END)[0]
  fandom_sections = fandom_html.split('</a>')
  fandom_sections.pop

  fandom_sections.map do |section|
    CGI.unescapeHTML(section.split(">")[1])
  end
end

def load_prompt(text, data_by_pseud)
  request = { any: [] }

  user = load_user(text)
  request[:fandoms] = load_fandoms(text)

  tags = text.split(TAGS_START)[1].split(TAGS_END)[0]

  tag_list = tags.split('<li class=')
  tag_list.shift(1)

  tag_list.each do |tag_html|
    type = tag_html.split(">")[0].gsub(/\W/, "")

    if type == "tag"
      value = tag_html.split(">Any ")[1].split("<")[0].downcase
      request[:any] << value
    else
      value = tag_html.split(">")[2].split("<")[0]
      value = CGI.unescapeHTML(value)
      request[type] ||= []
      request[type] << value
    end
  end

  unless data_by_pseud.key? user
    data_by_pseud[user] = { pseud: user, requests: [] }
  end

  data_by_pseud[user][:requests] << request
end

OWN_PROMPT = '<li class="own prompt blurb group" role="article">'

def load_file(filename, data_by_pseud)
  text = File.open(filename, 'r').read

  # make sure that all prompts are treated equally
  text = text.gsub(OWN_PROMPT, PROMPT_DELIMETER)

  prompts = text.split(PROMPT_DELIMETER)
  prompts.shift(1)
  prompts.each do |prompt|
    load_prompt(prompt, data_by_pseud)
  end
end

data_by_pseud = {}

request_files.each do |filename|
  load_file(filename, data_by_pseud)
end

data = data_by_pseud.values

# Next, fill in the offer data by copying the request data.

data.each do |complete_signup|
  complete_signup[:offers] = complete_signup[:requests]
end

# Save the data to a JSON file.

JSON.dump(data, File.open(json_output, 'w'))
