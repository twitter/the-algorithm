module TranslationHelper

def time_ago_in_words(from_time, include_seconds = false)
    
    to_time = Time.now
    if from_time.respond_to?(:to_time)
      from_time = from_time.to_time 
    else
      return
    end
    to_time = to_time.to_time if to_time.respond_to?(:to_time)
    distance_in_minutes = (((to_time - from_time).abs)/60).round
    distance_in_seconds = ((to_time - from_time).abs).round

    case distance_in_minutes
    when 0..1
      return (distance_in_minutes==0) ? 'less than 1 minute ago' : '1 minute ago' unless include_seconds
      case distance_in_seconds
      when 0..5 then "less than 5 seconds ago"
      when 6..10 then "less than 10 seconds ago"
      when 11..20 then "less than 20 seconds ago"
      when 21..40 then "half a minute ago"
      when 41..59 then "less than a minute ago"
      else "1 minute ago"
      end
    when 2..45 then "#{distance_in_minutes} minutes ago" 
    when 46..90 then "1 hour ago"
    when 90..1440 then "#{(distance_in_minutes.to_f / 60.0).round} hours ago"
    when 1441..2880 then "1 day ago"
    else "#{(distance_in_minutes / 1440).round} days ago"
    end
  end

alias distance_of_time_in_words_to_now time_ago_in_words

  # Take some text and add whatever punctuation, symbols, and/or spacing
  # we use to separate a metadata property from its value, e.g., "Property: ",
  # "Propriété : ".
  def metadata_property(text)
    text.html_safe + t("mailer.general.metadata_label_indicator")
  end
end
