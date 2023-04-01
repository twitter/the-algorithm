# https://rails.lighthouseapp.com/projects/8994/tickets/6022-content-negotiation-fails-for-some-headers-regression#ticket-6022-13
# https://rails.lighthouseapp.com/projects/8994/tickets/5833
# specific reason: user agent "Mozilla/4.0 (PSP (PlayStation Portable); 2.00)" sets http accept header to "*/*;q=0.01" and rails gives it a 500

module ActionDispatch
  module Http
    module MimeNegotiation

      # Patched to always accept at least HTML
      def accepts
        @env["action_dispatch.request.accepts"] ||= begin
          header = @env['HTTP_ACCEPT'].to_s.strip

          if header.empty?
            [content_mime_type]
          else
            Mime::Type.parse(header) << Mime[:html]
          end
        end
      end

    end
  end
end
