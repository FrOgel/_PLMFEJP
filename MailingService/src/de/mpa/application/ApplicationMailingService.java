package de.mpa.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.net.ssl.HttpsURLConnection;

/**
 * @author frank.vogel created on: 06.01.2018 purpose: Class for mail sending
 *         logic
 */
@Stateless
public class ApplicationMailingService implements _ApplicationMailingService {

	final private String SYSTEM_MAIL = "mpadhbw@gmail.com";

	// Sends the verification mail to the user mail address
	@Override
	public void sendVerificationMail(String to, String id, String hash) {
		// Verification mail recipient is the to variable
		// Password: Hallo123!
		// User: mpadhbw@gmail.com

		this.sendMail(to, "Thank you for your registration. Please verify your account.",
				this.getVerificationMailTemplate(id, hash));

	}

	private void sendMail(String to, String subject, String html) {
		// Set properties for the SSL connection to the gmail smtp server
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.port", "465");

		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("mpadhbw@gmail.com", "Hallo123!");
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(SYSTEM_MAIL));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject);
			message.setContent(html, "text/html; charset=utf-8");

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	private String getVerificationMailTemplate(String id, String hash) {
		URL url;
		try {
			url = new URL("https://localhost:8443/MailingService/VerificationMail.jsp?id=" + id + "&" + "hash=" + hash);
			System.out.println(url);
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

			String inputline;
			StringBuffer html = new StringBuffer();

			while ((inputline = in.readLine()) != null) {
				html.append(inputline);
			}

			in.close();

			return html.toString();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Wrong URL";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Not available";
		}

	}

	private String getPasswordChangeMailTemplate(String id, String hash) {
		URL url;
		try {
			url = new URL("https://localhost:8443/MailingService/PasswordChangeMail.jsp?id=" + id + "&" + "hash=" + hash);
			System.out.println(url);
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

			String inputline;
			StringBuffer html = new StringBuffer();

			while ((inputline = in.readLine()) != null) {
				html.append(inputline);
			}

			in.close();

			return html.toString();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Wrong URL";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Not available";
		}

	}

	@Override
	public void sendPasswordChangeMail(String to, String id, String hash) {
		this.sendMail(to, "Reset your password.", this.getPasswordChangeMailTemplate(id, hash));
	}

}
