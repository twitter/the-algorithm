class Offer < Prompt
  belongs_to :challenge_signup, touch: true, inverse_of: :offers

  def prompt_restriction
    collection&.challenge&.offer_restriction
  end
end
