# Sanitize: http://github.com/rgrove/sanitize.git
class Sanitize
  # This defines the configuration we use for HTML tags and attributes allowed in the archive.
  module Config
    ARCHIVE = freeze_config(
      elements: %w[
        a abbr acronym address b big blockquote br caption center cite code col
        colgroup details figcaption figure dd del dfn div dl dt em h1 h2 h3 h4 h5 h6 hr
        i img ins kbd li ol p pre q rp rt ruby s samp small span strike strong
        sub summary sup table tbody td tfoot th thead tr tt u ul var
      ],
      attributes: {
        all: %w[align title dir],
        "a" => %w[href name],
        "blockquote" => %w[cite],
        "col" => %w[span width],
        "colgroup" => %w[span width],
        "details" => %w[open],
        "hr" => %w[align width],
        "img" => %w[align alt border height src width],
        "ol" => %w[start type],
        "q" => %w[cite],
        "table" => %w[border summary width],
        "td" => %w[abbr axis colspan height rowspan width],
        "th" => %w[abbr axis colspan height rowspan scope width],
        "ul" => %w[type]
      },

      add_attributes: {
        "a" => { "rel" => "nofollow" }
      },

      protocols: {
        "a" => { "href" => ["ftp", "http", "https", "mailto", :relative] },
        "blockquote" => { "cite" => ["http", "https", :relative] },
        "img" => { "src" => ["http", "https", :relative] },
        "q" => { "cite" => ["http", "https", :relative] }
      },
      
      # TODO: This can be removed once we upgrade sanitizer gem, AO3-5801
      # I would leave the tests we added in AO3-5974 though.
      remove_contents: %w[iframe math noembed noframes noscript plaintext script style svg xmp]
    )

    CLASS_ATTRIBUTE = freeze_config(
      # see in the Transformers section for what classes we strip
      attributes: {
        all: ARCHIVE[:attributes][:all] + ["class"]
      }
    )

    CSS_ALLOWED = freeze_config(merge(ARCHIVE, CLASS_ATTRIBUTE))

    # On details elements, force boolean attribute "open" to have
    # value "open", if it exists
    OPEN_ATTRIBUTE_TRANSFORMER = lambda do |env|
      return unless env[:node_name] == "details"

      env[:node]["open"] = "open" if env[:node].has_attribute?("open")
    end
  end
end
