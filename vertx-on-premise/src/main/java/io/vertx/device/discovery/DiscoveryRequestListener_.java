package io.vertx.device.discovery;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.bridge.BridgeOptions;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.eventbus.bridge.tcp.TcpEventBusBridge;

public class DiscoveryRequestListener_ implements Runnable {

	private static final String SAMPLE_DISCOVERY_REQUEST = "sample.discovery.request";

	private static final String SOURCE = "source";

	private static final String SAMPLE = "sample.*";

	private static final String END_IP = "endIP";
	private static final String START_IP = "startIP";

	public static int DEFAULT_CLIENT_PORT = 9232;

	public static int DEFAULT_SERVER_PORT = 9234;

	private static void sendDiscoveryRequest(Vertx vertx) {
		TcpEventBusBridge bridge = TcpEventBusBridge
				.create(vertx,
						new BridgeOptions().addInboundPermitted(
								new PermittedOptions().setAddressRegex(SAMPLE))
								.addOutboundPermitted(
										new PermittedOptions()
												.setAddressRegex(SAMPLE)));

		vertx.eventBus()
				.consumer(
						SAMPLE_DISCOVERY_REQUEST,
						message -> {
							JsonObject body = (JsonObject) message.body();
							System.out.println(body.encodePrettily());
							System.out.println(" I got the message");

							JsonArray fetchedDiscoveredDevices = DeviceDiscoveryHandler
									.fetchedDiscoveredDevices(body
											.getString(START_IP), body.getString(END_IP));
							StartDiscoveryUtils.sendDiscoveryResponseData(
									DEFAULT_CLIENT_PORT,
									body.getString(SOURCE),
									fetchedDiscoveredDevices);

						});
		

		// MessageProducer<Object> tickPublisher =
		// vertx.eventBus().publisher("sample.clock.ticks");
		// vertx.setPeriodic(1000L, id -> {
		// tickPublisher.send(new JsonObject().put("tick", id));
		// });
		// vertx.eventBus().send(arg0, arg1, new
		// Handler<AsyncResult<Message<T>>>() {
		//
		// @Override
		// public <T> void handle(AsyncResult<Message<T>> arg0) {
		// // TODO Auto-generated method stub
		//
		// }
		// });

		// vertx.eventBus().consumer("sample.echo", message -> {
		// JsonObject body = (JsonObject) message.body();
		// System.out.println("Echoing: " + body.encodePrettily());
		// message.reply(body);
		// });

		bridge.listen(DEFAULT_SERVER_PORT, result -> {
			if (result.failed()) {
				throw new RuntimeException(result.cause());
			} else {
				System.out.println("TCP Event Bus bridge running on port "
						+ DEFAULT_SERVER_PORT);
			}
		});
	}

	@Override
	public void run() {
		sendDiscoveryRequest(Vertx.vertx());

	}

	public static void main(String... args) throws Throwable {

		new Thread(new DiscoveryRequestListener_()).start();
	}

}
