require 'timeout'
require 'uri'

module UrlHelpers
  # Make urls consistent
  def reformat_url(url)
    url = url.gsub(/https/, "http")
    url = "http://" + url if url && url.length > 0 && /http/.match(url[0..3]).nil?
    url.chop if url.last == "/"
    url
  end
end
