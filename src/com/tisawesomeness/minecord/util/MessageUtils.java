package com.tisawesomeness.minecord.util;

import java.awt.Color;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang3.ArrayUtils;

import com.tisawesomeness.minecord.Config;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.TextChannel;

public class MessageUtils {
	
	/**
	 * Sends a notification message deleted after the amount of time set in the config.
	 * @param m Message to send
	 * @param c Channel to send message in
	 */
	public static void notify(String m, TextChannel c) {
		Message msg = c.sendMessage(m).complete();
		notifyInternal(msg, 1);
	}
	
	/**
	 * Sends a notification message deleted after the amount of time set in the config.
	 * @param m Message to send
	 * @param c Channel to send message in
	 */
	public static void notify(Message m, TextChannel c) {
		Message msg = c.sendMessage(m).complete();
		notifyInternal(msg, 1);
	}
	
	/**
	 * Sends a notification message deleted after the amount of time set in the config.
	 * @param m Message to send
	 * @param c Channel to send message in
	 */
	public static void notify(String m, TextChannel c, double multiplier) {
		Message msg = c.sendMessage(m).complete();
		notifyInternal(msg, multiplier);
	}
	
	/**
	 * Sends a notification message deleted after the amount of time set in the config.
	 * @param m Message to send
	 * @param c Channel to send message in
	 */
	public static void notify(Message m, TextChannel c, double multiplier) {
		Message msg = c.sendMessage(m).complete();
		notifyInternal(msg, multiplier);
	}
	
	private static void notifyInternal(Message m, double multiplier) {
		if (Config.getNotificationTime() >= 0) {
			class Clean extends TimerTask {
				@Override
				public void run() {
					m.delete().queue();
				}
			}
		
			Timer timer = new Timer();
			timer.schedule(new Clean(), (long) (Config.getNotificationTime()*multiplier));
		}
	}
	
	/**
	 * Formats a message to look more fancy using an embed. Pass null in any argument (except color) to remove that aspect of the message.
	 * @param title The title or header of the message.
	 * @param url A URL that the title goes to when clicked. Only works if title is not null.
	 * @param body The main body of the message.
	 * @param color The color of the embed. Discord markdown formatting and newline are supported.
	 * @param thumb The URL of the thumbnail.
	 * @return A MessageEmbed representing the message. You can add additional info (e.g. fields) by passing this variable into a new EmbedBuilder.
	 */
	public static MessageEmbed wrapMessage(String title, String url, String body, Color color) {
		EmbedBuilder eb = new EmbedBuilder();
		if (title != null) {eb.setAuthor(title, url, null);}
		eb.setDescription(body);
		eb.setColor(color);
		return eb.build();
	}
	
	/**
	 * Formats an image to look more fancy using an embed.
	 * @param title The title or header.
	 * @param url The URL of the image.
	 * @param color The color of the embed. Discord markdown formatting and newline are supported.
	 * @return A MessageEmbed representing the message. You can add additional info (e.g. fields) by passing this variable into a new EmbedBuilder.
	 */
	public static MessageEmbed wrapImage(String title, String url, Color color) {
		EmbedBuilder eb = new EmbedBuilder();
		if (title != null) {eb.setAuthor(title, null, null);}
		eb.setImage(url);
		eb.setColor(color);
		return eb.build();
	}
	
	/**
	 * Returns a random color.
	 */
	public static Color randomColor() {
		Color[] colors = new Color[]{
			new Color(0, 0, 0),
			new Color(0, 0, 170),
			new Color(0, 170, 0),
			new Color(0, 170, 170),
			new Color(170, 0, 0),
			new Color(170, 0, 170),
			new Color(255, 170, 0),
			new Color(170, 170, 170),
			new Color(85, 85, 85),
			new Color(85, 85, 255),
			new Color(85, 255, 85),
			new Color(85, 255, 255),
			new Color(255, 85, 85),
			new Color(255, 85, 255),
			new Color(255, 255, 85),
			new Color(255, 255, 255)
		};
		return colors[new Random().nextInt(colors.length)];
	}
	
	/**
	 * Parses boolean arguments.
	 * @param search The string array to search through.
	 * @param include A string that also means true.
	 * @return The first index of the boolean argument. Returns -1 if not found.
	 */
	public static int parseBoolean(String[] search, String include) {
		
		String[] words = new String[]{"true", "yes", "allow", include};
		for (String word : words) {
			int index = ArrayUtils.indexOf(search, word);
			if (index > -1) {
				return index;
			}
		}
		return -1;
	}

}