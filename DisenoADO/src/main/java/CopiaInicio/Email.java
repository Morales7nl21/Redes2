package CopiaInicio;

import java.util.Properties;
import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;

public class Email {

	public static void envia(String EnviaUsuario, File archivo,String NombreArchivo) {
		final String username = "eugenioayona1im2@gmail.com"; // Email desde done se mando el archivo
		final String password = "emalacm3pt"; // contraseña del usuario;

		Properties props = new Properties();
		props.put("mail.smtp.auth", true);
		props.put("mail.smtp.starttls.enable", true);
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			MimeBodyPart messageBodyPart = new MimeBodyPart(); //Sospecho que funciona para la estructura del correo
			Multipart multipart = new MimeMultipart();  
			MimeMessage message = new MimeMessage(session); //Verifica que la el envio sea exitoso.
			
			
			
			message.setFrom(new InternetAddress(username));//Con nuestro correo se remite a el correo seleccionado
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(EnviaUsuario));// A quien se envia correo
			DataSource source = new FileDataSource(archivo);//Archivo seleccionado para enviar
			
			
			message.setSubject("Invitacion para asignacion de casilleros ESCOM");//Mensaje que aparece en el correo
			message.setText("Mensaje enviado con exito");
			
			      
			messageBodyPart.setDataHandler(new DataHandler(source));// Envia este archivo al correo
			messageBodyPart.setFileName(NombreArchivo);//Convierte el archivo al tipo seleccionado
			
			multipart.addBodyPart(messageBodyPart);
			// ------------------------------------------------------

			/*
			 * //attached 2 -------------------------------------------- String
			 * file2="path of file"; String fileName2 = "AnyName2"; messageBodyPart = new
			 * MimeBodyPart(); DataSource source2 = new FileDataSource(file2);
			 * messageBodyPart.setDataHandler(new DataHandler(source2));
			 * messageBodyPart.setFileName(fileName2);
			 * multipart.addBodyPart(messageBodyPart); //attached
			 * 3------------------------------------------------
			 * 
			 * String file3="path of file"; String fileName3 = "AnyName3"; messageBodyPart =
			 * new MimeBodyPart(); DataSource source3 = new FileDataSource(file3);
			 * messageBodyPart.setDataHandler(new DataHandler(source3));
			 * messageBodyPart.setFileName(fileName3);
			 * multipart.addBodyPart(messageBodyPart); //attached
			 * 4------------------------------------------------ String
			 * file4="path of file"; String fileName4 = "AnyName4"; messageBodyPart = new
			 * MimeBodyPart(); DataSource source4 = new FileDataSource(file4);
			 * messageBodyPart.setDataHandler(new DataHandler(source4));
			 * messageBodyPart.setFileName(fileName4);
			 * multipart.addBodyPart(messageBodyPart); //>>>>>>>>
			 * 
			 * 
			 */
			message.setContent(multipart);

			System.out.println("sending");
			Transport.send(message);
			System.out.println("Done");

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
