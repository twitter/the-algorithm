class AddIsTranslatedCheckToQuestions < ActiveRecord::Migration[4.2]
  def up
    Question.add_translation_fields! is_translated: :string
  end

  def down
    remove_column :question_translations, :is_translated
  end
end
