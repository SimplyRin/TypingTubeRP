package net.simplyrin.typingtube.servlet;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.simplyrin.typingtube.Main;

/**
 * Created by SimplyRin on 2020/02/09.
 *
 * Copyright (c) 2020 SimplyRin
 *
 * Eclipse Public License - v 2.0
 */
public class PostTask extends HttpServlet {

	private Main instance;

	public PostTask(Main instance) {
		this.instance = instance;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Headers", "Content-Type");
		response.setContentType("application/json; charset=UTF-8");

		String ip = request.getRemoteAddr();

		String query = URLDecoder.decode(request.getQueryString(), "UTF-8");

		System.out.println("Connected from " + ip + ".");
		if (!ip.equals("127.0.0.1")) {
			System.out.println("It was accessed from an incorrect IP address.");
			return;
		}

		System.out.println("Body: " + query);

		if (query.equals("lobby")) {
			System.out.println("Lobby... (none, Lobby, 99999)");
			this.instance.connect("none", "Lobby", 99999);
			return;
		}

		JsonObject jsonObject = new JsonParser().parse(query).getAsJsonObject();

		String title = jsonObject.get("title").getAsString();
		int level = jsonObject.get("level").getAsInt();
		String url = jsonObject.get("url").getAsString();

		System.out.println("Lobby... (url, title, level)");
		this.instance.connect(url, title, level);
	}

}
