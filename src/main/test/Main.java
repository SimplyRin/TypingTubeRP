import java.time.OffsetDateTime;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.entities.DiscordBuild;
import com.jagrosh.discordipc.entities.RichPresence;
import com.jagrosh.discordipc.exceptions.NoDiscordClientException;

/**
 * Created by SimplyRin on 2020/02/12.
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
		new Main().run();
	}

	private IPCClient ipcClient;

	public void run() {
		this.ipcClient = new IPCClient(675987298642558988L);
		try {
			this.ipcClient.connect(new DiscordBuild[0]);
		} catch (NoDiscordClientException e) {
			System.out.println("Access it with Discord running.");
			return;
		}
		RichPresence.Builder presence = new RichPresence.Builder();
		presence.setDetails("debugging");
		presence.setStartTimestamp(OffsetDateTime.now());
		presence.setLargeImage("main");

		System.out.println("Sending Rich Presence information.");
		this.ipcClient.sendRichPresence(presence.build());

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		presence.setDetails("none");
		presence.setState("debugging");

		this.ipcClient.sendRichPresence(presence.build());
	}

}
