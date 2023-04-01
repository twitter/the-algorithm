module NewRelic
  module Agent
    class NewRelicService
      def create_http_connection
        if Agent.config[:proxy_host]
          ::NewRelic::Agent.logger.debug("Using proxy server #{Agent.config[:proxy_host]}:#{Agent.config[:proxy_port]}")

          proxy = Net::HTTP::Proxy(
            Agent.config[:proxy_host],
            Agent.config[:proxy_port],
            Agent.config[:proxy_user],
            Agent.config[:proxy_pass]
          )
          conn = proxy.new(@collector.name, @collector.port)
        else
          # adding nil as prxy_addr here bypasses http_proxy environment variable if it is present.
          # https://docs.ruby-lang.org/en/2.6.0/Net/HTTP.html#class-Net::HTTP-label-Proxies
          conn = Net::HTTP.new(@collector.name, @collector.port, nil) 
        end

        setup_connection_for_ssl(conn)
        setup_connection_timeouts(conn)

        ::NewRelic::Agent.logger.debug("Created PATCHED net/http handle to #{conn.address}:#{conn.port}")
        conn
      end
    end
  end
end
