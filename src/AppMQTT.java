import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class AppMQTT {

	public static void main(String[] args) {
		String subTopic="/ktmt/iot";
		String pubTopic="/ktmt/pub";
		String content="{\"id\":11, \"packet_no\":126, \"temperature\":30, \"humidity\":60, \"tds\":1100, \"pH\":5.0}";
		int pos=2;
		String broker="tcp://broker.hivemq.com:1883";
		String clientId="ID_OF_CLIENT";
		MemoryPersistence persistence=new MemoryPersistence();
		
		try {
			MqttClient client=new MqttClient(broker, clientId,persistence);
			MqttConnectOptions connPots=new MqttConnectOptions();
			connPots.setCleanSession(true);
			client.setCallback(new  OnMessageCallback());
			System.out.println("Connecting to broker: "+broker);
			client.connect();
			System.out.println("Connected!");
			System.out.println("Publishing message: "+content);
			client.subscribe(subTopic);
			MqttMessage message=new MqttMessage(content.getBytes());
			message.setQos(pos);
			client.publish(pubTopic, message);
			System.out.println("Message published");
			client.disconnect();
			System.out.println("Disconnected");
			client.close();
			System.exit(0);
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
