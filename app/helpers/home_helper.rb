module HomeHelper
  def html_to_text(string)
    string.gsub!(/<br\s*\/?>/, "\n")
    string.gsub!(/<\/?p>/, "\n\n")
    string = strip_tags(string)
    string.gsub!(/^[ \t]*/, "")
    while !string.gsub!(/\n\s*\n\s*\n/, "\n\n").nil?
      # keep going
    end
    return string
  end
end
