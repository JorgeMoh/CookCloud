package cookcloud.servicios;

import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;

public class EmailService {

    public void enviarCorreoVerificacion(int identificador, String email){

        // Datos de la sesion
        final Properties prop = new Properties();
        prop.put("mail.smtp.username", "cookcloud.soporte@gmail.com"); // Correo
        prop.put("mail.smtp.password", "xocp wzqw pjvr pfpv"); // Clave
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587"); // Puerto
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); // TLS
//        prop.put("mail.debug", "true");

        // Creamos la sesion del correo
        Session mailSession = Session.getInstance(prop, new jakarta.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(prop.getProperty("mail.smtp.username"),
                        prop.getProperty("mail.smtp.password"));
            }
        });

        try{

        // Preparamos el mensaje
        Message message = new MimeMessage(mailSession);
        message.setFrom(new InternetAddress(email)); // Seteamos el receptor
        message.setSubject("Clave de verificación"); // Asunto

        // Receptores
        InternetAddress[] toEmailAddresses = InternetAddress.parse(email);
        message.setRecipients(Message.RecipientType.TO, toEmailAddresses);


        Multipart multipart = new MimeMultipart("related");
        MimeBodyPart htmlPart = new MimeBodyPart();

        // Formamos el cuerpo con html metiendo en el cuerpo del html url de la imagen y el número de verificación
        String messageBody = """
                <div style="padding: 15px;border-radius: 10px; background: #363636">
                            <div style="padding: 15px; border-radius: 10px; background: #151515;text-align: center;">
                                <img src="%s" style="width: 170px; height: 150px; margin: 0 auto; display: block;"/>
                                <hr style="border: 1px solid white; width: 90%%; margin: 10px auto;">
                                <h2 style="color: white">Código de verificación de correo</h2>
                                <p style="color: white; text-align: left" >Gracias por ingresar en CookCloud, introduce el siguiente código numérico en la aplicación para finalizar la creación de su cuenta</p>
                                <h1 style="color: white"> %s </h1>
                            </div>
                        </div>
                """.formatted("https://i.postimg.cc/26Rmfxf1/1771401829972.png",identificador);

        htmlPart.setText(messageBody, "utf-8", "html");

        multipart.addBodyPart(htmlPart); // añadimos el html

        message.setContent(multipart); // lo añadimos al mensaje

        Transport.send(message); // Enviamos el mensaje

        } catch (MessagingException e){
            e.printStackTrace();
        }

    }

}
