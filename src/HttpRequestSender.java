import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

//Java 11 Required
public class HttpRequestSender {

	private static final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();

	private static void sendGet() throws IOException, InterruptedException {
		HttpRequest request = HttpRequest.newBuilder().GET()
				.uri(URI.create("https://your-url/"))
				.setHeader("custom-header", "value").build();

		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

		System.out.println("Status code: " + response.statusCode());

		System.out.println("Body: \n" + response.body());
	}

	public static void sendPost() throws IOException, InterruptedException {
		// form parameters
		Map<Object, Object> data = new HashMap<>();
		data.put("username", "abc");
		data.put("password", "123");
		data.put("custom", "secret");
		data.put("timestamp", System.currentTimeMillis());

		HttpRequest request = HttpRequest.newBuilder().POST(buildFormDataFromMap(data))
				.uri(URI.create("https://your-url/"))
				.setHeader("custom-headr", "value").build();

		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

		System.out.println("Status code: " + response.statusCode());

		System.out.println("Body: \n" + response.body());
	}

	private static HttpRequest.BodyPublisher buildFormDataFromMap(Map<Object, Object> data) {
		StringBuilder builder = new StringBuilder();
		for (Map.Entry<Object, Object> entry : data.entrySet()) {
			if (builder.length() > 0) {
				builder.append("&");
			}
			builder.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8));
			builder.append("=");
			builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
		}
		System.out.println(builder.toString());
		return HttpRequest.BodyPublishers.ofString(builder.toString());
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		sendGet();
		sendPost();
	}

}
