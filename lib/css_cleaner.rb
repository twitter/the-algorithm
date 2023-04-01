# Use css parser to break up style blocks
require 'css_parser'

module CssCleaner
  include CssParser

  # constant regexps for css values
  ALPHA_REGEX = Regexp.new('[a-z\-]+')
  UNITS_REGEX = Regexp.new('deg|cm|em|ex|in|mm|pc|pt|px|s|%', Regexp::IGNORECASE)
  NUMBER_REGEX = Regexp.new('-?\.?\d{1,3}\.?\d{0,3}')
  NUMBER_WITH_UNIT_REGEX = Regexp.new("#{NUMBER_REGEX}\s*#{UNITS_REGEX}?\s*,?\s*")
  PAREN_NUMBER_REGEX = Regexp.new('\(\s*' + NUMBER_WITH_UNIT_REGEX.to_s + '+\s*\)')
  PREFIX_REGEX = Regexp.new('moz|ms|o|webkit')

  FUNCTION_NAME_REGEX = Regexp.new('scalex?y?|translatex?y?|skewx?y?|rotatex?y?|matrix', Regexp::IGNORECASE)
  TRANSFORM_FUNCTION_REGEX = Regexp.new("#{FUNCTION_NAME_REGEX}#{PAREN_NUMBER_REGEX}")

  SHAPE_NAME_REGEX = Regexp.new('rect', Regexp::IGNORECASE)
  SHAPE_FUNCTION_REGEX = Regexp.new("#{SHAPE_NAME_REGEX}#{PAREN_NUMBER_REGEX}")

  RGBA_REGEX = Regexp.new("rgba?" + PAREN_NUMBER_REGEX.to_s, Regexp::IGNORECASE)
  HSLA_REGEX = Regexp.new("hsla?" + PAREN_NUMBER_REGEX.to_s, Regexp::IGNORECASE)
  COLOR_REGEX = Regexp.new("#[0-9a-f]{3,6}|" + ALPHA_REGEX.to_s + "|" + RGBA_REGEX.to_s + "|" + HSLA_REGEX.to_s)
  COLOR_STOP_FUNCTION_REGEX = Regexp.new('color-stop\s*\(' + NUMBER_WITH_UNIT_REGEX.to_s + '\s*\,?\s*' + COLOR_REGEX.to_s + '\s*\)', Regexp::IGNORECASE)

  # from the ICANN list at http://www.icann.org/en/registries/top-level-domains.htm
  TOP_LEVEL_DOMAINS = %w(ac ad ae aero af ag ai al am an ao aq ar arpa as asia at au aw ax az ba bb bd be bf bg bh bi biz bj bm bn bo br bs bt bv bw by bz ca cat cc cd cf cg ch ci ck cl cm cn co com coop cr cu cv cx cy cz de dj dk dm do dz ec edu ee eg er es et eu fi fj fk fm fo fr ga gb gd ge gf gg gh gi gl gm gn gov gp gq gr gs gt gu gw gy hk hm hn hr ht hu id ie il im in info int io iq ir is it je jm jo jobs jp ke kg kh ki km kn kp kr kw ky kz la lb lc li lk lr ls lt lu lv ly ma mc md me mg mh mil mk ml mm mn mo mobi mp mq mr ms mt mu museum mv mw mx my mz na name nc ne net nf ng ni nl no np nr nu nz om org pa pe pf pg ph pk pl pm pn pr pro ps pt pw py qa re ro rs ru rw sa sb sc sd se sg sh si sj sk sl sm sn so sr st su sv sy sz tc td tel tf tg th tj tk tl tm tn to tp tr travel tt tv tw tz ua ug uk us uy uz va vc ve vg vi vn vu wf ws xn xxx ye yt za zm zw)
  DOMAIN_REGEX = Regexp.new('https?://\w[\w\-\.]+\.(' + TOP_LEVEL_DOMAINS.join('|') + ')')
  DOMAIN_OR_IMAGES_REGEX = Regexp.new('\/images|' + DOMAIN_REGEX.to_s)
  URI_REGEX = Regexp.new(DOMAIN_OR_IMAGES_REGEX.to_s + '/[\w\-\.\/]*[\w\-]\.(' + ArchiveConfig.SUPPORTED_EXTERNAL_URLS.join('|') + ')')
  URL_REGEX = Regexp.new(URI_REGEX.to_s + '|"' + URI_REGEX.to_s + '"|\'' + URI_REGEX.to_s + '\'')
  URL_FUNCTION_REGEX = Regexp.new('url\(\s*' + URL_REGEX.to_s + '\s*\)')

  VALUE_REGEX = Regexp.new("#{TRANSFORM_FUNCTION_REGEX}|#{URL_FUNCTION_REGEX}|#{COLOR_STOP_FUNCTION_REGEX}|#{COLOR_REGEX}|#{NUMBER_WITH_UNIT_REGEX}|#{ALPHA_REGEX}|#{SHAPE_FUNCTION_REGEX}")


  # For use in ActiveRecord models
  # We parse and clean the CSS line by line in order to provide more helpful error messages.
  # The prefix is used if you want to make sure a particular prefix appears on all the selectors in
  # this block of css, eg ".userstuff p" instead of just "p"
  def clean_css_code(css_code, options = {})
    return "" if !css_code.match(/\w/) # only spaces of various kinds
    clean_css = ""
    parser = CssParser::Parser.new
    parser.add_block!(css_code)
    
    prefix = options[:prefix] || ''
    caller_check = options[:caller_check]

    if parser.to_s.blank?
      errors.add(:base, ts("We couldn't find any valid CSS rules in that code."))
    else
      parser.each_rule_set do |rs|
        selectors = rs.selectors.map do |selector|
          if selector.match(/@font-face/i)
            errors.add(:base, ts("We don't allow the @font-face feature."))
            next
          end
          # remove whitespace and convert &gt; entities back to the > direct child selector
          sel = selector.gsub(/\n/, '').gsub('&gt;', '>').strip
          (prefix.blank? || sel.start_with?(prefix)) ? sel : "#{prefix} #{sel}"
        end
        clean_declarations = ""
        rs.each_declaration do |property, value, is_important|
          if property.blank? || value.blank?
            errors.add(:base, ts("The code for #{rs.selectors.join(',')} doesn't seem to be a valid CSS rule."))
          elsif sanitize_css_property(property).blank?
            errors.add(:base, ts("We don't currently allow the CSS property #{property} -- please notify support if you think this is an error."))
          elsif (cleanval = sanitize_css_declaration_value(property, value)).blank?
            errors.add(:base, ts("The #{property} property in #{rs.selectors.join(', ')} cannot have the value #{value}, sorry!"))
          elsif (!caller_check || caller_check.call(rs, property, value))
            clean_declarations += "  #{property}: #{cleanval}#{is_important ? ' !important' : ''};\n"
          end
        end
        if clean_declarations.blank?
          errors.add(:base, ts("There don't seem to be any rules for #{rs.selectors.join(',')}"))
        else
          # everything looks ok, add it to the css
          clean_css += "#{selectors.join(",\n")} {\n"
          clean_css += clean_declarations
          clean_css += "}\n\n"
        end
      end
    end
    return clean_css
  end

  def is_legal_property(property)
    ArchiveConfig.SUPPORTED_CSS_PROPERTIES.include?(property) || 
      property.match(/-(#{PREFIX_REGEX})-(#{ArchiveConfig.SUPPORTED_CSS_PROPERTIES.join('|')})/)
  end

  def is_legal_shorthand_property(property)
    property.match(/#{ArchiveConfig.SUPPORTED_CSS_SHORTHAND_PROPERTIES.join('|')}/)
  end

  def sanitize_css_property(property)
    return (is_legal_property(property) || is_legal_shorthand_property(property)) ? property : ""
  end

  # A declaration must match the format:   property: value;
  # All properties must appear in ArchiveConfig.SUPPORTED_CSS_PROPERTIES or ArchiveConfig.SUPPORTED_CSS_SHORTHAND_PROPERTIES,
  # or that property and its value will be omitted.
  # All values are sanitized. If any values in a declaration are invalid, the value will be blanked out and an
  #   empty property returned.
  def sanitize_css_declaration_value(property, value)
    clean = ""
    property = property.downcase
    if property == "font-family"
      if !sanitize_css_font(value).blank?
        # preserve the original capitalization
        clean = value
      end
    elsif property == "content"
      clean = sanitize_css_content(value)
    elsif value.match(/\burl\b/) && (!ArchiveConfig.SUPPORTED_CSS_KEYWORDS.include?("url") || !%w(background background-image border border-image list-style list-style-image).include?(property))
      # check whether we can use urls in this property
      clean = ""
    elsif is_legal_shorthand_property(property)
      clean = tokenize_and_sanitize_css_value(value)
    elsif is_legal_property(property)
      clean = sanitize_css_value(value)
    end
    clean.strip
  end

  # divide a css value into tokens and clean them individually
  def tokenize_and_sanitize_css_value(value)
    cleanval = ""
    scanner = StringScanner.new(value)

    # we scan until we find either a space, a comma, or an open parenthesis
    while scanner.exist?(/\s+|,|\(/)
      # we have some tokens left to break up
      in_paren = 0
      token = scanner.scan_until(/\s+|,|\(/)
      if token.blank? || token == ","
        cleanval += token
        next
      end
      in_paren = 1 if token.match(/\($/)
      while in_paren > 0
        # scan until closing paren or another opening paren
        nextpart = scanner.scan_until(/\(|\)/)
        if nextpart
          token += nextpart
          in_paren += 1 if token.match(/\($/)
          in_paren -= 1 if token.match(/\)$/)
        else
          # mismatched parens
          return ""
        end
      end

      # we now have a single token
      separator = token.match(/(\s|,)$/) || ""
      token.strip!
      token.chomp!(',')
      cleantoken = sanitize_css_token(token)
      return "" if cleantoken.blank?
      cleanval += cleantoken + separator.to_s
    end

    token = scanner.rest
    if token && !token.blank?
      cleantoken = sanitize_css_token(token)
      return "" if cleantoken.blank?
      cleanval += cleantoken
    end

    return cleanval
  end

  def sanitize_css_token(token)
    cleantoken = ""
    if token.match(/gradient/)
      cleantoken = sanitize_css_gradient(token)
    else
      cleantoken = sanitize_css_value(token)
    end
    return cleantoken
  end

  # sanitize a CSS gradient
  # background:-webkit-gradient( linear, left bottom, left top, color-stop(0, rgb(82,82,82)), color-stop(1, rgb(125,124,125)));
  # -moz-linear-gradient(bottom, rgba(120,120,120,1) 5%, rgba(94,94,94,1) 50%, rgba(108,108,108,1) 55%, rgba(137,137,137,1) 100%);
  def sanitize_css_gradient(value)
    if value.match(/^([a-z\-]+)\((.*)\)/)
      function = $1
      interior = $2
      cleaned_interior = tokenize_and_sanitize_css_value(interior)
      if function.match(/gradient/) && !cleaned_interior.blank?
        return "#{function}(#{cleaned_interior})"
      end
    end
    return ""
  end


  # all values must either appear in ArchiveConfig.SUPPORTED_CSS_KEYWORDS, be urls of the format url(http://url/) or be
  # rgba(), hex (#), or numeric values, or a comma-separated list of same
  def sanitize_css_value(value)
    value_stripped = value.downcase.gsub(/(!important)/, '').strip

    # if it's a comma-separated set of valid values it's fine
    return value if value_stripped =~ /^(#{VALUE_REGEX}\,?\s*)+$/i

    # If it's explicitly in our keywords it's fine
    return value if value_stripped.split(',').all? {|subval| ArchiveConfig.SUPPORTED_CSS_KEYWORDS.include?(subval.strip)}

    return ""
  end


  def sanitize_css_content(value)
    # For now we only allow a single completely quoted string
    return value if value =~ /^\'([^\']*)\'$/
    return value if value =~ /^\"([^\"]*)\"$/

    # or a valid img url
    return value if value.match(URL_FUNCTION_REGEX)

    # or "none"
    return value if value == "none"

    return ""
  end


  # Font family names may be alphanumeric values with dashes
  def sanitize_css_font(value)
    value_stripped = value.downcase.gsub(/(!important)/, '').strip
    if value_stripped.split(',').all? {|fontname| fontname.strip =~ /^(\'?[a-z0-9\- ]+\'?|\"?[a-z0-9\- ]+\"?)$/}
      return value
    else
      return ""
    end
  end


end
