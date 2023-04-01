class ErrorsController < ApplicationController
  %w[403 404 422 500].each do |error_code|
    define_method error_code.to_sym do
      render error_code, status: error_code.to_i, formats: :html
    end
  end

  def auth_error
    @page_subtitle = "Auth Error"
  end
end
