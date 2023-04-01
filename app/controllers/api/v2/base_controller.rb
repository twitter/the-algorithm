# frozen_string_literal: true

module Api
  # Version the API explicitly in the URL to allow different versions with breaking changes to co-exist if necessary.
  # The roll over to the next number should happen when code written against the old version will not work
  # with the new version.
  module V2
    class BaseController < ApplicationController
      skip_before_action :verify_authenticity_token
      before_action :restrict_access

      # Prevent unhandled errors from returning the normal HTML page
      rescue_from StandardError, with: :render_standard_error_response

      private

      # Look for a token in the Authorization header only and check that the token isn't currently banned
      def restrict_access
        authenticate_or_request_with_http_token do |token, _|
          ApiKey.exists?(access_token: token) && !ApiKey.find_by(access_token: token).banned?
        end
      end

      # Top-level error handling: returns a 403 forbidden if a valid archivist isn't supplied and a 400
      # if no works are supplied. If there is neither a valid archivist nor valid works, a 400 is returned
      # with both errors as a message
      def batch_errors(archivist, import_items)
        errors = []
        status = ""

        if archivist&.is_archivist?
          if import_items.nil? || import_items.empty?
            status = :empty_request
            errors << "No items to import were provided."
          elsif import_items.size >= ArchiveConfig.IMPORT_MAX_WORKS_BY_ARCHIVIST
            status = :too_many_request
            errors << "This request contains too many items to import. A maximum of #{ArchiveConfig.IMPORT_MAX_WORKS_BY_ARCHIVIST} " \
                      "items can be imported at one time by an archivist."
          end
        else
          status = :forbidden
          errors << "The \"archivist\" field must specify the name of an Archive user with archivist privileges."
        end

        status = :ok if errors.empty?
        [status, errors]
      end

      # Return a standard HTTP + Json envelope for all API responses
      def render_api_response(status, messages, response = {})
        # It's a bad request unless it's ok or an authorisation error
        http_status = %i[forbidden ok].include?(status) ? status : :bad_request
        render status: http_status, json: { status: status, messages: messages }.merge(response)
      end

      # Return a standard HTTP + Json envelope for errors that drop through other handling
      def render_standard_error_response(exception)
        message = "An error occurred in the Archive code: #{exception.message}"
        render status: :internal_server_error, json: { status: :internal_server_error, messages: [message] }
      end
    end
  end
end
