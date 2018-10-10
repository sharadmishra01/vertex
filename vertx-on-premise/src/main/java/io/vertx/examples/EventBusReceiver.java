package io.vertx.examples;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

public class EventBusReceiver {
	
	public static <T> void main(String args[]) {

		Vertx vertx = Vertx.vertx();
		JsonObject message = new JsonObject();
		message.put("first", "hi");
		
		
		vertx.eventBus().consumer("localhost", new Handler<Message<T>>() {

			@Override
			public void handle(Message<T> event) {
				System.out.println(" I am in");
				
			}
		});

		
	}

}
