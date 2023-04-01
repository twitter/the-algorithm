class Request < Prompt
  belongs_to :challenge_signup, touch: true, inverse_of: :requests

  def prompt_restriction
    collection&.challenge&.request_restriction
  end
end
