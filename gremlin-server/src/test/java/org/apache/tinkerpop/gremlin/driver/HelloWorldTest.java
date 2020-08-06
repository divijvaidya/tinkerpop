package org.apache.tinkerpop.gremlin.driver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.local.LocalAddress;
import io.netty.channel.local.LocalServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import org.apache.tinkerpop.gremlin.driver.ser.Serializers;
import org.apache.tinkerpop.gremlin.driver.simple.WebSocketClient;
import org.apache.tinkerpop.gremlin.server.TestClientFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Locale;

public class HelloWorldTest {
    private SimpleWebSocketServer server;

    @Before
    public void setUp() throws InterruptedException, InstantiationException, IllegalAccessException {
        server = new SimpleWebSocketServer();
        server.start(new AlwaysCloseFrameWebSockerServer());
    }

    @After
    public void shutdown() {
        server.stop();
    }

    @Test
    public void test() {
        final Cluster cluster = Cluster.build("localhost").port(45940)
                .minConnectionPoolSize(2)
                .maxConnectionPoolSize(2)
                .create();
        final Client.ClusteredClient client = cluster.connect();
        client.submit("1");
        client.submit("1");
        client.submit("1").one();
        System.out.println(client.hostConnectionPools.values().stream().findFirst().get().getPoolInfo());
    }
}