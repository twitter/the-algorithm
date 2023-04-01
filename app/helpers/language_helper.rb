RTL_LOCALES = %w[ar fa he].freeze

module LanguageHelper
  def available_faq_locales
    ArchiveFaq.translated_locales.map { |code| Locale.find_by(iso: code) }
  end

  def rtl?
    RTL_LOCALES.include?(Globalize.locale.to_s)
  end

  def rtl_language?(language)
    RTL_LOCALES.include?(language.short)
  end

  def english?
    params[:language_id] == "en"
  end

  def translated_questions(all_questions)
    questions = []
    all_questions.each do |question|
      question.translations.each do |translation|
        if translation.is_translated == "1" && params[:language_id].to_s == translation.locale.to_s
          questions << question
        end
      end
    end
    questions
  end
end
