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
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
