package io.vertx.device.discovery;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;
import io.vertx.ext.eventbus.bridge.tcp.impl.protocol.FrameHelper;

public class StartDiscoveryUtils {

	private static final String SAMPLE_DISCOVERY_RESPONSE = "sample.discovery.response";

	public static void sendDiscoveryResponseData(int port,
			String targetHost, JsonArray data) {
		Vertx vertx = Vertx.vertx();

		// Send a request and get a response
		NetClient client = vertx.createNetClient();

		// NetClient client = vertx.createNetClient(new NetClientOptions()
		// .setSsl(false)
		// .setTrustAll(true)
		// .setKeyStoreOptions(
		// new JksOptions().setPath("client.keystore")
		// .setPassword("wibble")));
		// final Async async = context.async();

		// vertx.eventBus().localConsumer("test", (Message<JsonObject> msg) -> {
		// client.close();
		// // async.complete();
		// });

		client.connect(port, targetHost,
				new Handler<AsyncResult<NetSocket>>() {

					@Override
					public void handle(AsyncResult<NetSocket> arg0) {
						if (arg0.failed()) {
							System.out.println(" Could not make connection");
						}
						NetSocket socket = arg0.result();
						if (socket != null) {
							System.out.println(" Sending the response back to client for discovery");
							for (Object deviceObject : data){
							FrameHelper.sendFrame("send", SAMPLE_DISCOVERY_RESPONSE,
									(JsonObject)deviceObject,// just fetch only
															// element
									socket);
							}
						}

					}
				});

		// client.connect(8000, "localhost", conn -> {
		// // context.assertFalse(conn.failed());
		//
		// NetSocket socket = conn.result();
		//
		// FrameHelper.sendFrame("send", "sample.dumb.inbox",
		// new JsonObject().put("value", "vert.x").put("check", "got"), socket);
		// });

	}

	public static void main(String args[]) {

		Vertx vertx = Vertx.vertx();

		// Send a request and get a response
		NetClient client = vertx.createNetClient();

		// NetClient client = vertx.createNetClient(new NetClientOptions()
		// .setSsl(false)
		// .setTrustAll(true)
		// .setKeyStoreOptions(
		// new JksOptions().setPath("client.keystore")
		// .setPassword("wibble")));
		// final Async async = context.async();

		// vertx.eventBus().localConsumer("test", (Message<JsonObject> msg) -> {
		// client.close();
		// // async.complete();
		// });

		client.connect(9232, "10.11.246.244",
				new Handler<AsyncResult<NetSocket>>() {

					@Override
					public void handle(AsyncResult<NetSocket> arg0) {
						if (arg0.failed()) {
							System.out.println(" Could not make connection");
						}
						NetSocket socket = arg0.result();
						if (socket != null) {
							FrameHelper.sendFrame("send", "sample.dumb.inbox",
									new JsonObject().put("value", "vert.x")
											.put("check", "got"), socket);

						}

					}
				});

		// client.connect(8000, "localhost", conn -> {
		// // context.assertFalse(conn.failed());
		//
		// NetSocket socket = conn.result();
		//
		// FrameHelper.sendFrame("send", "sample.dumb.inbox",
		// new JsonObject().put("value", "vert.x").put("check", "got"), socket);
		// });
	}

}
