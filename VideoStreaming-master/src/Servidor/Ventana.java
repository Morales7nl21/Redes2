package src.Servidor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.Utilities;

public class Ventana extends JEditorPane implements ActionListener {
	public static String Nombre;
	public int statusOp;
	public int salida;
	private JTabbedPane chats;
	public static JPanel panel;
	private ArrayList<JEditorPane> chatPersona;
	private ArrayList<String> contactos;
	private ArrayList<String> contactosChat;
	private ArrayList<JTextField> textoEnviar;
	private ArrayList<JButton> botonesEnviar;
	static ImageIcon SMILE_IMG;
	int i;

	public Ventana(int operacion,  JPanel panel) throws IOException {
		Ventana.panel = panel;
		new Ventana();
		statusOp = operacion;
		salida = 0;
		Nombre = JOptionPane.showInputDialog("Ingresa el nombre con el que te deseas identificar");

		while (Nombre.isEmpty()) {
			Nombre = JOptionPane.showInputDialog("Error: Ingresa el nombre con el que te deseas identificar");
		}
/*
	

		// Activa los eventos que pasen esta ventana
		ventana.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			// en este caso cuando el evento cerrar pantalla pase ejecuta su codigo
			public void windowClosing(WindowEvent we) {
				statusOp = 1;
				salida = 1;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ex) {
					Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		});*/
		
		chatPersona = new ArrayList();// Es el area en donde se muestran los mensajes
		contactosChat = new ArrayList();
		contactos = new ArrayList();
		textoEnviar = new ArrayList(); // Campo de texto para escribir mensajes
		botonesEnviar = new ArrayList();// Botones de envio de las interfaces

		inicializarComponentes();
		
	}

	// Parte interior de cada interfaz de los usuarios
	private void inicializarComponentes() {
		JLabel titulo = new JLabel("Tus chats: " + Nombre);
		panel.setLayout(null);
		titulo.setBounds(10, 10, 180, 30);
		panel.add(titulo);

		setButtons();
		setGeneral();
	}

//Para activar el chat privado con los demas usuarios del chat
	private void setButtons() {
		JButton newChat = new JButton();
		newChat.addActionListener(this);
		newChat.setText("Nuevo chat privado");
		newChat.setBounds(790, 10, 200, 30);
		panel.add(newChat);
	}

	// Se crea una ventana para que se muestren los mensajes que se envian en el
	// grupo(todos los usuarios)
	private void setGeneral() {
		chats = new JTabbedPane();
		chats.setBounds(510, 35, 480, 240);
		newChat("General");
		panel.add(chats);
	}

	// Aqui se personaliza la informacion de cada ventana del chat, para general, es
	// una pestaña fija, para todos los chats
	// Cuando un usuario llama a especifico usuario, una nueva pestaña se desplega
	// con el nombre del chat entre los individuos
	private void newChat(String nombre) {
	    JPanel newPanel = new JPanel();
		newPanel.setLayout(null);
		chats.addTab(nombre, newPanel);

		// Añade JEDITORPANE (donde se muestran los mensaje) y Ejecuta los eventos que
		// estan pendientes que no se haya enviado un emoji objetivo: :), :o ...
		chatPersona.add(new Ventana());

		JScrollPane scroll = new JScrollPane(chatPersona.get(chatPersona.size() - 1));
		scroll.setBounds(10, 10, 465, 200);
		newPanel.add(scroll);

		JTextField texto = new JTextField();
		// Campo de texto
		textoEnviar.add(texto);
	    textoEnviar.get(textoEnviar.size() - 1).setBounds(510, 290, 350, 39);
		//textoEnviar.get(textoEnviar.size() - 1).setBounds(10, 20, 350, 39);
		panel.add(textoEnviar.get(textoEnviar.size() - 1));

		JButton enviar = new JButton("Enviar");
		botonesEnviar.add(enviar);
		botonesEnviar.get(botonesEnviar.size() - 1).setBounds(895, 290, 95, 38);
		//botonesEnviar.get(botonesEnviar.size() - 1).setBounds(400, 20, 95, 38);
		botonesEnviar.get(botonesEnviar.size() - 1).setText("Enviar");
		botonesEnviar.get(botonesEnviar.size() - 1).addActionListener(this);
		String butname = "Enviar" + (botonesEnviar.size() - 1);

		System.out.println(butname);// 4

		botonesEnviar.get(botonesEnviar.size() - 1).setName(butname);
		panel.add(botonesEnviar.get(botonesEnviar.size() - 1));

		// Contactos del chat
		contactosChat.add(nombre);
	}

	// Funcion usada para indicarle a las interfaces cuando escribir y leer la
	// informacion dentro de los chats
	public int getStatus() {
		return statusOp;
	}

	// Usada para saber si el usuario ha acabado sus sesion (cerro su chat)
	public int getSalida() {
		return salida;
	}

	// Coloca status de lectura o escritura
	public void setStatus(int newStatus) {
		statusOp = newStatus;
	}

	public void setNewMessage(String mensaje) throws IOException {
		if (mensaje.contains("<contactos>")) {
			String contacto = "";
			contactos.clear();
			for (int i = 12; i < mensaje.length(); i++) {

				// 6 Aqui se escribe los contactos que estan en el chat, esta continuamente
				// ejecutandose por dando se rescriben continuamente
				System.out.println("|" + mensaje.charAt(i) + "|");

				if (Character.isLetter(mensaje.charAt(i))) {
					contacto += mensaje.charAt(i);
				} else if (mensaje.charAt(i) == ',') {

					// guarda los F en un array
					contactos.add(contacto);
					contacto = "";
				} else {
					// break;
				}
			}
			contactos.add(contacto);

			// 7 imprime el array
			System.out.println(contactos);
		} else if (mensaje.startsWith("S<msj>")) {// Si el mensaje viene del servidor
			String remitente = "";
			mensaje = mensaje.substring(6);
			if (mensaje.contains("<privado>")) {// Si es un mensaje privado
				String destinatario = "";
				int i = 1;

				mensaje = mensaje.substring(9);
				// Solo va a guardar el nombre del remitente
				while (Character.isLetter(mensaje.charAt(i))) {
					remitente = remitente + mensaje.charAt(i);
					i++;
				}
				
				//se adelanta para que obtenga,os el nombre del destinatario
				mensaje = mensaje.substring(i + 1);

				i = 1;
				System.out.println(mensaje);
				while (Character.isLetter(mensaje.charAt(i))) {
					destinatario = destinatario + mensaje.charAt(i);
					i++;
				}
				
				mensaje = mensaje.substring(i + 1);
				// es el mensaje que se mandara
				System.out.println(mensaje);
				// de donde viene hacia donde va
				System.out.println("Remitente: " + remitente + " destinatario: " + destinatario);

				//Si esta en la pestaña actual, va a crear una pestaña en el destinatario
				if (Nombre.equals(destinatario)) {
					if (contactosChat.contains(remitente)) {
						
						//Se escribe el mensaje en el remitente
						int selectedIndex = contactosChat.indexOf(remitente);
						textoEnviar.get(selectedIndex).setText("");
						chatPersona.get(selectedIndex)
								.setText(chatPersona.get(selectedIndex).getText() + "\n" + remitente + ":" + mensaje);
					
					} else {
						//se va a crear una nueva ventana en la pestaña del destinatario y se va escribir en el JeditorPane
						newChat(remitente);
						chats.setSelectedIndex(contactosChat.indexOf(remitente));
						int selectedIndex = chats.getSelectedIndex();
						textoEnviar.get(selectedIndex).setText("");
						chatPersona.get(selectedIndex)
								.setText(chatPersona.get(selectedIndex).getText() + "\n" + remitente + ":" + mensaje);
					}
				} else if (Nombre.equals(remitente)) {//Si esta en el destinatario ya no creara una pestaña, porque ya se creo un nodo entre los dos, es tonto
					//
					int selectedIndex = chats.getSelectedIndex();
					textoEnviar.get(selectedIndex).setText("");
					chatPersona.get(selectedIndex)
							.setText(chatPersona.get(selectedIndex).getText() + "\n" + remitente + ":" + mensaje);
				}
			} else {// Si es un mensaje general solo imprime el mensaje en la pestaña de "General"

				int selectedIndex = 0;// El mensaje 0 es el general
				textoEnviar.get(selectedIndex).setText("");
				chatPersona.get(selectedIndex).setText(chatPersona.get(selectedIndex).getText() + "\n" + mensaje);// se
																													// escribe
																													// el
																													// mensaje
			}
		}
	}

	// Cuando se esta en un chat si hay mas de 1 ventana se supone que hay mas
	// chats, el indice 0 simpre indica que esta en el chat general
	// y los demas son chats privados
	public int getActiveTab() {

		return chats.getSelectedIndex();
	}

	// Obtiene el nombre del chat actual
	public String getNombre() {
		return Nombre;
	}

	//Vamos a mandar el indice del destinatario, para que se obtenga el nombre del dest.
	public String getContactosChat(int indice) {
		return contactosChat.get(indice);
	}

	//Es el mensaje que se va a mandar, lo obtiene del mensaje del remitente
	public String getActiveMessage() {
		int selectedIndex = chats.getSelectedIndex();
		String texto = textoEnviar.get(selectedIndex).getText();
		textoEnviar.get(selectedIndex).setText("");
		return texto;
	}

	// Son los eventos de los botones "enviar" y "nuevo chat privado" 8
	@Override
	public void actionPerformed(ActionEvent ae) {

		JButton boton = (JButton) ae.getSource();

		// boton que checa el evento Nuevo chat privado
		if (boton.getActionCommand().equals("Nuevo chat privado")) {
			if (contactos.toArray().length == 0) {
				JOptionPane.showMessageDialog(null, "En este momento no hay contactos en linea", "Contactos",
						JOptionPane.ERROR_MESSAGE);
			} else {
				ArrayList<String> contactosMostrar = contactos;
				contactosMostrar.remove(Nombre);
				String seleccion = (String) JOptionPane.showInputDialog(null, "Nuevo chat",
						"Seleccione el usuario con el que desea crear un chat", JOptionPane.INFORMATION_MESSAGE, null,
						contactosMostrar.toArray(), contactosMostrar.toArray()[0]);
				if (contactosChat.contains(seleccion)) {
					chats.setSelectedIndex(contactosChat.indexOf(seleccion));
				} else {
					newChat(seleccion);
					chats.setSelectedIndex(contactosChat.indexOf(seleccion));
				}
				statusOp = 0;
			}
		} else if (boton.getActionCommand().equals("Enviar")) {// Evento que checa el boton enviar
			statusOp = 1;
		}
	}

	
	
	
	
	String emoji;

	private void initListener() {
		getDocument().addDocumentListener(new DocumentListener() {
			public void insertUpdate(DocumentEvent event) {
				final DocumentEvent e = event;
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						if (e.getDocument() instanceof StyledDocument) {
							try {
								StyledDocument doc = (StyledDocument) e.getDocument();
								int start = Utilities.getRowStart(Ventana.this, Math.max(0, e.getOffset() - 1));
								int end = Utilities.getWordStart(Ventana.this, e.getOffset() + e.getLength());
								String text = doc.getText(start, end - start);

								if (text.contains(":)")) {
									SMILE_IMG = createImage("risa.png");
									emoji = ":)";
									i = text.indexOf(":)");
								}

								if (text.contains(":o")) {
									SMILE_IMG = createImage("amor.png");
									emoji = ":o";
									i = text.indexOf(":o");
								}

								if (text.contains(":s")) {
									SMILE_IMG = createImage("enojo.png");
									emoji = ":s";
									i = text.indexOf(":s");
								}

								if (text.contains("_loco_")) {
									SMILE_IMG = createImage("loco.gif");
									emoji = "_loco_";
									i = text.indexOf("_loco_");
								}

								if (text.contains(":)") || text.contains(":o") || text.contains(":s")
										|| text.contains("_loco_"))
									while (i >= 0) {
										final SimpleAttributeSet attrs = new SimpleAttributeSet(
												doc.getCharacterElement(start + i).getAttributes());

										if (StyleConstants.getIcon(attrs) == null) {
											StyleConstants.setIcon(attrs, SMILE_IMG);
											doc.remove(start + i, 2);
											doc.insertString(start + i, emoji, attrs);
										}
										i = text.indexOf(emoji, i + 2);

									}
							} catch (BadLocationException | IOException e1) {
								e1.printStackTrace();
							}
						}
					}
				});
			}

			public void removeUpdate(DocumentEvent e) {
			}

			public void changedUpdate(DocumentEvent e) {
			}
		});
	}

	// Tipo y manipulacion de los datos dentro del JEDITORPANE
	public Ventana() {
		super();
		setEditorKit(new StyledEditorKit());
		initListener();
	}



	// Solo carga la imagen que nosotros queremos mostrar (emojis)
	static ImageIcon createImage(String emoji) throws IOException {
		BufferedImage res = ImageIO.read(Ventana.class.getResource(emoji));

		return new ImageIcon(res);
	}

}
