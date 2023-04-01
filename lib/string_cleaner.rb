module StringCleaner

  def remove_articles_from_string(str)
    str.gsub(article_removing_regex, '')
  end

  def article_removing_regex
    Regexp.new(/^(a|an|the|la|le|les|l'|un|une|des|die|das|il|el|las|los|der|den)\s/i)
  end

end
  
