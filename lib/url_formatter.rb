require 'uri'
require 'cgi'
require 'addressable/uri'

class UrlFormatter
  
  attr_accessor :url
  
  def initialize(url)
    @url = url || ""
  end
  
  def original
    url
  end
  
  # Remove anchors and query parameters, preserve sid parameter for eFiction sites
  def minimal (input = url)
    uri = Addressable::URI.parse(input)
    queries = CGI::parse(uri.query) unless uri.query.nil?
    if queries.nil?
      return input.gsub(/(\?|#).*$/, '')
    else
      queries.keep_if { |k,v| ['sid'].include? k }
      querystring = ('?' + URI.encode_www_form(queries)) unless queries.empty?
      return input.gsub(/(\?|#).*$/, '') << querystring.to_s
    end
  end

  def minimal_no_http
    minimal.gsub(/https?:\/\/www\./, "")
  end
  
  def no_www
    minimal.gsub(/http:\/\/www\./, "http://")
  end
  
  def with_www
    minimal.gsub(/http:\/\//, "http://www.")
  end
  
  def encoded
    minimal URI::Parser.new.escape(url)
  end
  
  def decoded
    URI::Parser.new.unescape(minimal)
  end
  
  # Adds http if not present and downcases the host
  # Extracted from story parser class
  def standardized
    clean_url = URI.parse(url)
    clean_url = URI.parse('http://' + url) if clean_url.class.name == "URI::Generic"
    clean_url.host = clean_url.host.downcase
    clean_url.to_s
  end
  
end
