module AutocompleteSource
  AUTOCOMPLETE_DELIMITER = ": ".freeze
  AUTOCOMPLETE_COMPLETION_KEY = "completion".freeze
  AUTOCOMPLETE_SCORE_KEY = "score".freeze
  AUTOCOMPLETE_CACHE_KEY = "cache".freeze
  AUTOCOMPLETE_RANGE_LENGTH = 50 # not random
  AUTOCOMPLETE_BOOST = 1000 # amt by which we boost results that have all the words

  # this marks a completed word in the completion set -- we use double commas because
  # commas are not allowed in pseud and tag names, and double-commas have been disallowed
  # from collection titles
  AUTOCOMPLETE_WORD_TERMINATOR = ",,".freeze

  def transliterate(input)
    input = input.to_s.mb_chars.unicode_normalize(:nfkd).gsub(/[\u0300-\u036F]/, "")
    result = ""
    input.each_char do |char|
      tl = ActiveSupport::Inflector.transliterate(char)
      # If transliterate returns "?", the original character is either unsupported 
      # (e.g. a non-Latin character) or was actually a question mark.
      # In both cases, we should keep the original.
      result << if tl == "?"
                  char
                else
                  tl
                end
    end
    result
  end

  # override to define any autocomplete prefix spaces where this object should live
  def autocomplete_prefixes
    [self.transliterate("autocomplete_#{self.class.name.downcase}")]
  end

  def autocomplete_search_string
    self.transliterate(name)
  end

  def autocomplete_search_string_was
    self.transliterate(name_was)
  end

  def autocomplete_search_string_before_last_save
    self.transliterate(name_before_last_save)
  end

  def autocomplete_value
    "#{id}#{AUTOCOMPLETE_DELIMITER}#{name}" + (self.respond_to?(:title) ? "#{AUTOCOMPLETE_DELIMITER}#{title}" : "")
  end

  def autocomplete_value_was
    "#{id}#{AUTOCOMPLETE_DELIMITER}#{name_was}" + (self.respond_to?(:title) ? "#{AUTOCOMPLETE_DELIMITER}#{title_was}" : "")
  end

  def autocomplete_value_before_last_save
    "#{id}#{AUTOCOMPLETE_DELIMITER}#{name_before_last_save}" + (self.respond_to?(:title) ? "#{AUTOCOMPLETE_DELIMITER}#{title_before_last_save}" : "")
  end

  def autocomplete_score
    0
  end

  def add_to_autocomplete(score = nil)
    score = autocomplete_score unless score
    self.class.autocomplete_pieces(autocomplete_search_string).each do |word_piece|
      # each prefix represents an autocompletion space -- eg, "autocomplete_collection_all"
      autocomplete_prefixes.each do |prefix|
        # We put each prefix and the word + completion token into the set of all completions,
        # with score 0 so they just get sorted lexicographically --
        # this will be used to quickly find all possible completions in this space
        REDIS_AUTOCOMPLETE.zadd(self.transliterate(self.class.autocomplete_completion_key(prefix)), 0, word_piece)

        # We put each complete search string into a separate set indexed by word with specified score
        if self.class.is_complete_word?(word_piece)
          REDIS_AUTOCOMPLETE.zadd(self.transliterate(self.class.autocomplete_score_key(prefix, word_piece)), score, autocomplete_value)
        end
      end
    end
  end

  def remove_from_autocomplete
    self.class.remove_from_autocomplete(self.autocomplete_search_string, self.autocomplete_prefixes, self.autocomplete_value)
  end

  def remove_stale_from_autocomplete
    Rails.logger.debug "Removing stale from autocomplete: #{autocomplete_search_string_before_last_save}"
    self.class.remove_from_autocomplete(self.autocomplete_search_string_before_last_save, self.autocomplete_prefixes, self.autocomplete_value_before_last_save)
  end

  module ClassMethods
    include AutocompleteSource

    # returns a properly escaped and case-insensitive regexp for a more manual search
    def get_search_regex(search_param)
      Regexp.new(Regexp.escape(search_param), Regexp::IGNORECASE)
    end

    # takes either an array or string of search terms (typically extra values passed in through live params, like fandom)
    # and returns an array of stripped and lowercase words for actual searching or use in keys
    def get_search_terms(search_term)
      terms = if search_term.is_a?(Array)
                search_term.map { |term| term.split(",") }.flatten
              else
                search_term.blank? ? [] : search_term.split(",")
              end
      terms.map { |term| self.transliterate(term.strip.downcase) }
    end

    def parse_autocomplete_value(current_autocomplete_value)
      current_autocomplete_value.split(AUTOCOMPLETE_DELIMITER, 3)
    end

    def fullname_from_autocomplete(current_autocomplete_value)
      current_autocomplete_value.split(AUTOCOMPLETE_DELIMITER, 2)[1]
    end

    def id_from_autocomplete(current_autocomplete_value)
      parse_autocomplete_value(current_autocomplete_value)[0]
    end

    def name_from_autocomplete(current_autocomplete_value)
      parse_autocomplete_value(current_autocomplete_value)[1]
    end

    def title_from_autocomplete(current_autocomplete_value)
      parse_autocomplete_value(current_autocomplete_value)[2]
    end

    def autocomplete_lookup(options = {})
      options.reverse_merge!({search_param: "", autocomplete_prefix: "", sort: "down"})
      search_param = options[:search_param]
      autocomplete_prefix = options[:autocomplete_prefix]
      if REDIS_AUTOCOMPLETE.exists(autocomplete_cache_key(autocomplete_prefix, search_param))
        return REDIS_AUTOCOMPLETE.zrange(autocomplete_cache_key(autocomplete_prefix, search_param), 0, -1)
      end

      # we assume that if the user is typing in a phrase, any words they have
      # entered are the exact word they want, so we only get the prefixes for
      # the very last word they have entered so far
      search_pieces = autocomplete_phrase_split(search_param).map { |w| w + AUTOCOMPLETE_WORD_TERMINATOR }

      # for each complete word, we look up the phrases in that word's set
      # along with their scores and add up the scores
      scored_results = {}
      count = {}
      exact = {}
      search_regex = Regexp.new(Regexp.escape(search_param), Regexp::IGNORECASE)

      search_pieces.each_with_index do |search_piece, index|
        lastpiece = false
        if index == search_pieces.size - 1
          lastpiece = true
          search_piece.gsub!(/#{Tag::AUTOCOMPLETE_WORD_TERMINATOR}$/, '')

          break if search_pieces.size > 1 && search_piece.length == 1
        end

        # Get all the complete words which could match this search term
        completions = autocomplete_word_completions(search_piece, autocomplete_prefix)

        completions.each do |word|
          # O(logN + M) where M is number of items returned -- we could speed up even more by putting in a limit
          phrases_with_scores = []
          if lastpiece && search_piece.length < 3
            # use a limit
            phrases_with_scores = REDIS_AUTOCOMPLETE.zrevrangebyscore(autocomplete_score_key(autocomplete_prefix, word),
              'inf', 0, withscores: true, limit: [0, 50])
          else
            phrases_with_scores = REDIS_AUTOCOMPLETE.zrevrangebyscore(autocomplete_score_key(autocomplete_prefix, word),
              'inf', 0, withscores: true)
          end

          phrases_with_scores.each do |phrase, score|
            score = score.to_i
            if options[:constraint_sets]
              # phrases must be in these sets or else no go
              # O(logN) complexity
              next unless options[:constraint_sets].all {|set| REDIS_AUTOCOMPLETE.zrank(set, phrase)}
            end

            if count[phrase]
              # if we've already seen this phrase, increase the score
              scored_results[phrase] += score
              count[phrase] += 1
            else
              # initialize the score and check if it exactly matches our regexp
              scored_results[phrase] = score
              if lastpiece
                # don't count if it only matches the last search piece
                count[phrase] = 0
              else
                count[phrase] = 1
              end
              if phrase.match(search_regex)
                exact[phrase] = true
              else
                exact[phrase] = false
              end
            end
          end
        end
      end

      # final sort is O(NlogN) but N is only the number of complete phrase results which should be relatively small
      results = scored_results.keys.sort do |k1, k2|
        exact[k1] && !exact[k2] ? -1 : (exact[k2] && !exact[k1] ? 1 :
          count[k1] > count[k2] ? -1 : (count[k2] > count[k1] ? 1 :
            scored_results[options[:sort] == "down" ? k2 : k1].to_i <=> scored_results[options[:sort] == "down" ? k1 : k2].to_i))
      end
      limit = options[:limit] || 15

      if search_param.length <= 2
        # cache the result for really quick response when only 1-2 letters entered
        # adds only a little bit to memory and saves doing a lot of processing of many phrases
        results[0..limit].each_with_index {|res, index| REDIS_AUTOCOMPLETE.zadd(autocomplete_cache_key(autocomplete_prefix, search_param), index, res)}
        # expire every 24 hours so new entries get added if appropriate
        REDIS_AUTOCOMPLETE.expire(autocomplete_cache_key(autocomplete_prefix, search_param), 24*60*60)
      end
      results[0..limit]
    end

    def is_complete_word?(word_piece)
      word_piece.match(/#{AUTOCOMPLETE_WORD_TERMINATOR}$/)
    end

    def get_word(word_piece)
      word_piece.gsub(/#{AUTOCOMPLETE_WORD_TERMINATOR}$/, '')
    end

    def autocomplete_score_key(autocomplete_prefix, word)
      self.transliterate(autocomplete_prefix + "_" + AUTOCOMPLETE_SCORE_KEY + "_" + get_word(word))
    end

    def autocomplete_completion_key(autocomplete_prefix)
      self.transliterate(autocomplete_prefix + "_" + AUTOCOMPLETE_COMPLETION_KEY)
    end

    def autocomplete_cache_key(autocomplete_prefix, search_param)
      self.transliterate(autocomplete_prefix + "_" + AUTOCOMPLETE_CACHE_KEY + "_" + search_param)
    end

    # Split a string into words.
    def autocomplete_phrase_split(string)
      # Use the ActiveSupport::Multibyte::Chars class to handle downcasing
      # instead of the basic string class, because it can handle downcasing
      # letters with accents or other diacritics.
      normalized = self.transliterate(string).downcase.to_s

      # Split on one or more spaces, ampersand, slash, double quotation mark,
      # opening parenthesis, closing parenthesis (just in case), tilde, hyphen
      normalized.split(/(?:\s+|\&|\/|"|\(|\)|\~|-)/).reject(&:blank?)
    end

    def autocomplete_pieces(string)
      # prefixes for autocomplete
      prefixes = []

      words = autocomplete_phrase_split(string)

      words.each do |word|
        prefixes << self.transliterate(word) + AUTOCOMPLETE_WORD_TERMINATOR
        word.length.downto(1).each do |last_index|
          prefixes << word.slice(0, last_index)
        end
      end

      prefixes
    end

    # overall time complexity: O(log N)
    def autocomplete_word_completions(word_piece, autocomplete_prefix)
      get_exact = is_complete_word?(word_piece)

      # the rank of the word piece tells us where to start looking
      # in the completion set for possible completions
      # O(logN) N = number of things in the completion set (ie all the possible prefixes for all the words)
      start_position = REDIS_AUTOCOMPLETE.zrank(autocomplete_completion_key(autocomplete_prefix), word_piece)
      return [] unless start_position

      results = []
      # start from that position and go for the specified range length
      # O(logN + M) M is the range length, so reduces to logN
      REDIS_AUTOCOMPLETE.zrange(autocomplete_completion_key(autocomplete_prefix), start_position, start_position + AUTOCOMPLETE_RANGE_LENGTH - 1).each do |entry|
        minlen = [entry.length, word_piece.length].min
        # if the entry stops matching the prefix then we've passed out of
        # the completions that could belong to this word -- return
        return results if entry.slice(0, minlen) != word_piece.slice(0, minlen)

        # otherwise if we've hit a complete word add it to the results
        if is_complete_word?(entry)
          results << entry
          return results if get_exact
        end
      end

      results
    end

    # generic method to remove pieces for given search string and value from the given autocomplete prefixes
    def remove_from_autocomplete(search_string, prefixes, value)
      autocomplete_pieces(search_string).each do |word_piece|
        prefixes.each do |prefix|
          # we leave the word pieces in the completion set so we don't accidentally trash
          # parts of other completions -- doing a weekly reload for cleanup is good enough
          if is_complete_word?(word_piece)
            word = get_word(word_piece)
            phrases = REDIS_AUTOCOMPLETE.zrevrangebyscore(autocomplete_score_key(prefix, word), 'inf', 0)
            if phrases.count == 1 && phrases.first == value
              # there's only one phrase for this word and we're removing it, remove the completed word from the completion set
              REDIS_AUTOCOMPLETE.zrem(autocomplete_completion_key(prefix), word_piece)
            end
            # remove the phrase we're deleting from the score set
            REDIS_AUTOCOMPLETE.zrem(autocomplete_score_key(prefix, word_piece), value)
          end
        end
      end
    end
  end

  def self.included(base)
    base.extend(ClassMethods)
  end
end
