package net.simplyrin.typingtube;

import java.time.OffsetDateTime;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.entities.DiscordBuild;
import com.jagrosh.discordipc.entities.RichPresence;
import com.jagrosh.discordipc.entities.RichPresence.Builder;
import com.jagrosh.discordipc.exceptions.NoDiscordClientException;

import net.simplyrin.rinstream.RinStream;
import net.simplyrin.typingtube.servlet.PostTask;

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
public class Main {

	public static void main(String[] args) {
		new RinStream();
		new Main().run();
	}

	public void run() {
		ServletContextHandler servletHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);

		servletHandler.setMaxFormContentSize(128 * 128);
		servletHandler.addServlet(new ServletHolder(new PostTask(this)), "/post");

		HandlerList handlerList = new HandlerList();

		handlerList.addHandler(servletHandler);

		Server server = new Server();
		server.setHandler(handlerList);

		HttpConfiguration httpConfiguration = new HttpConfiguration();
		httpConfiguration.setSendServerVersion(false);

		HttpConnectionFactory httpConnectionFactory = new HttpConnectionFactory(httpConfiguration);
		ServerConnector serverConnector = new ServerConnector(server, httpConnectionFactory);
		serverConnector.setPort(8843);

		server.setConnectors(new Connector[] { serverConnector });

		try {
			server.start();
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean first;

	private IPCClient ipcClient;
	private Builder builder;
	private String title;

	private Thread currentThread;

	public void connect(String url, String title, int level) {
		if (!this.first) {
			System.out.println("--------------------------------------------------------------");

			System.out.println("Initializing connection...");

			this.ipcClient = new IPCClient(675987298642558988L);
			try {
				this.ipcClient.connect(new DiscordBuild[0]);
			} catch (NoDiscordClientException e) {
				System.out.println("Access it with Discord running.");
				return;
			}

			this.builder = new RichPresence.Builder();

			this.builder.setStartTimestamp(OffsetDateTime.now());
			this.builder.setLargeImage("main");

			this.first = true;
		}

		if (this.title != null && this.title.equals(title)) {
			System.out.println("The same song as last time was selected. Skipping.");
			System.out.println("--------------------------------------------------------------");
			return;
		}

		if (level != 99999) {
			this.builder.setState("Level: " + level);
		}

		this.title = title;
		if (title.equals("Lobby")) {
			this.builder.setDetails("Lobby");
			this.builder.setState(null);
		} else {
			this.builder.setDetails("Title: " + title);
		}

		System.out.println("Sending Rich Presence information.");
		this.ipcClient.sendRichPresence(builder.build());
		System.out.println("Sended Rich Presence infomation!");

		System.out.println("--------------------------------------------------------------");

		if (this.currentThread != null) {
			this.currentThread.stop();
		}

		Thread thread = new Thread(() -> {
			try {
				Thread.sleep(1000 * 60 * 5);
			} catch (Exception e) {
			}

			this.ipcClient.close();

			this.ipcClient = null;
			this.builder = null;

			this.first = false;
		});
		thread.start();
		this.currentThread = thread;
	}

}
