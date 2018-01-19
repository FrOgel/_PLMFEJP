package de.mpa.application;

import java.io.IOException;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Stateless
@LocalBean
public class LocationService {
	// Methods for retrieving the longitude and latitude values of a specific
	// (country, postal code, city)
	public String getLocationGeometryData(String country, String city, String zipCode) {

		Client client = ClientBuilder.newClient();

		country = country.replace(" ", "%20");
		zipCode = "+" + zipCode.replace(" ", "%20");
		city = "+" + city.replace(" ", "%20");

		WebTarget webTarget = client
				.target("https://nominatim.openstreetmap.org/search?q=" + country + zipCode + city + "&format=json");

		System.out.println("https://nominatim.openstreetmap.org/search?q=" + country + zipCode + city + "&format=json");

		Invocation.Builder invocationBuilder = webTarget.request(MediaType.TEXT_PLAIN);

		Response response = invocationBuilder.get();

		return (String) response.readEntity(String.class);

	}

	public double getLatFromJson(String json) {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode geo1 = null;
		System.out.println(json);
		try {
			geo1 = mapper.readTree(json);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		double lat = geo1.get(0).get("lat").asDouble();

		return lat;
	}

	public double getLngFromJson(String json) {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode geo1 = null;
		System.out.println(json);
		try {
			geo1 = mapper.readTree(json);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		double lng = geo1.get(0).get("lon").asDouble();

		return lng;
	}

	// Distance calculation with Haversine formula
	public double getDistance(String country1, String zipCode1, String city1, String country2, String zipCode2,
			String city2) {

		double lng1 = 0, lat1 = 0, lng2 = 0, lat2 = 0;

		String latLng1 = this.getLocationGeometryData(country1, zipCode1, city1);
		String latLng2 = this.getLocationGeometryData(country2, zipCode2, city2);

		lng1 = getLngFromJson(latLng1);
		lat1 = getLatFromJson(latLng1);
		lng2 = getLngFromJson(latLng2);
		lat2 = getLatFromJson(latLng2);

		double earthRadius = 6371;
		double dLat = Math.toRadians(lat2 - lat1);
		double dLng = Math.toRadians(lng2 - lng1);
		double sindLat = Math.sin(dLat / 2);
		double sindLng = Math.sin(dLng / 2);
		double a = Math.pow(sindLat, 2)
				+ Math.pow(sindLng, 2) * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double dist = earthRadius * c;

		return dist;
	}

	public double getDistance(double lat1, double lng1, double lat2, double lng2) {

		double earthRadius = 6371;
		double dLat = Math.toRadians(lat2 - lat1);
		double dLng = Math.toRadians(lng2 - lng1);
		double sindLat = Math.sin(dLat / 2);
		double sindLng = Math.sin(dLng / 2);
		double a = Math.pow(sindLat, 2)
				+ Math.pow(sindLng, 2) * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double dist = earthRadius * c;

		return dist;
	}
}
