package io.vertx.device.discovery;

import java.util.Random;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class DeviceDiscoveryHandler {

	public static JsonArray fetchedDiscoveredDevices(String string,
			String string2) {

		JsonArray array = new JsonArray();

		Random rand = new Random();

		int n = rand.nextInt(10) + 1;

		for (int i = 1; i <= n; i++) {
			JsonObject device = createDummyDeviceObject(string, string2);

			if (!isDeviceDuplicate(array, device)) {
				array.add(device);
			}
		}
		return array;

	}

	private static boolean isDeviceDuplicate(JsonArray array, JsonObject device) {
		for (Object object : array.getList()) {
			if (((JsonObject) object).getString("tofinoid").equals(
					device.getString("tofinoid"))) {
				return true;
			}

		}
		return false;
	}

	private static JsonObject createDummyDeviceObject(String string,
			String string2) {
		JsonObject device = new JsonObject();
		device.put("hostname", "9.9.9.8");
		device.put("startip", string);
		device.put("endip", string2);
		Random rand = new Random();

		int n = rand.nextInt(8) + 1;

		device.put("tofinoid", "FA:AF:FA:FA:FA:F" + n);
		return device;
	}

}
