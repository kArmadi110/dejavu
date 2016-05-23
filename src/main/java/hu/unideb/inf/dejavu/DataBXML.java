package hu.unideb.inf.dejavu;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import hu.unideb.inf.dejavu.objects.Card;
import hu.unideb.inf.dejavu.objects.HighScoreRecord;
import hu.unideb.inf.dejavu.objects.HighScoreTable;
import hu.unideb.inf.dejavu.objects.Pack;
import hu.unideb.inf.dejavu.objects.Status;
import hu.unideb.inf.dejavu.objects.StopWatch;
import hu.unideb.inf.dejavu.objects.User;

/**
 * Az adatábziskezelést reprezentáló osztály.
 * 
 * @author gergo
 *
 */
public class DataBXML implements IData {

	/**
	 * Az xml kezeléshez szükséged builder.
	 */
	DocumentBuilder dBuilder;
	
	/**
	 * A szükséges xml állományok könyvtára.
	 * 
	 */
	String folder = System.getProperty("user.home") + "/.DejaVuSave/";

	/**
	 * Az adatbázist reprezentáló osztály konstruktora.
	 * 
	 */
	public DataBXML() {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		try {
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

		File dir = new File(folder);
		if (!dir.exists())
			dir.mkdir();

		createHighScoresTable();
		createStatusTable();
		createUsersTable();
	}

	@Override
	public boolean createUsersTable() {
		try {
			File user = new File(folder + "users.xml");
			if (user.exists())
				return true;

			Document doc = dBuilder.newDocument();

			Element root = doc.createElement("users");
			doc.appendChild(root);

			TransformerFactory transformerFactory = TransformerFactory.newInstance();

			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(user);
			transformer.transform(source, result);

			return true;
		} catch (TransformerException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean createStatusTable() {
		try {
			File status = new File(folder + "status.xml");

			if (status.exists())
				return true;

			Document doc = dBuilder.newDocument();

			Element root = doc.createElement("savedStatus");
			doc.appendChild(root);

			TransformerFactory transformerFactory = TransformerFactory.newInstance();

			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(status);
			transformer.transform(source, result);

			return true;
		} catch (TransformerException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean createHighScoresTable() {
		try {
			File highScore = new File(folder + "highScores.xml");

			if (highScore.exists())
				return true;

			Document doc = dBuilder.newDocument();

			Element root = doc.createElement("highScores");
			doc.appendChild(root);

			TransformerFactory transformerFactory = TransformerFactory.newInstance();

			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(highScore);
			transformer.transform(source, result);

			return true;
		} catch (TransformerException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean saveStatus(Status status) {
		try {
			File file = new File(folder + "status.xml");
			Document doc = dBuilder.parse(file);

			Element root = doc.getDocumentElement();

			Element s = doc.createElement("status");

			Element user = doc.createElement("name");
			user.appendChild(doc.createTextNode(status.getUser().getUserName()));
			s.appendChild(user);

			Element stopWatch = doc.createElement("time");
			stopWatch.appendChild(doc.createTextNode(status.getTime().toString()));
			s.appendChild(stopWatch);

			Element dimension = doc.createElement("dimension");
			dimension.appendChild(doc.createTextNode(Integer.toString(status.getDimension())));
			s.appendChild(dimension);

			for (int i = 0; i < status.getDimension(); i++)// cards & nem
															// tostring
				for (int j = 0; j < status.getDimension(); j++) {
					Element card = doc.createElement("card");
					card.appendChild(doc.createTextNode(status.getPack().getCard(i, j).getValue().toString()));
					card.setAttribute("X", Integer.toString(i));
					card.setAttribute("Y", Integer.toString(j));
					card.setAttribute("clicked", status.getPack().getCard(i, j).isClicked() ? "true" : "false");
					s.appendChild(card);
				}

			root.appendChild(s);

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);

			StreamResult result = new StreamResult(file);
			transformer.transform(source, result);

			return true;
		} catch (TransformerException | SAXException | IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updateHighScores(HighScoreRecord record) {
		try {
			File file = new File(folder + "highScores.xml");
			Document doc = dBuilder.parse(file);

			Element root = doc.getDocumentElement();

			Element highScore = doc.createElement("highScore");
			root.appendChild(highScore);

			Element name = doc.createElement("name");
			name.appendChild(doc.createTextNode(record.getName()));
			highScore.appendChild(name);

			Element time = doc.createElement("time");
			time.appendChild(doc.createTextNode(record.getTime()));
			highScore.appendChild(time);

			Element clicks = doc.createElement("clicks");
			clicks.appendChild(doc.createTextNode(Integer.toString(record.getClicks())));
			highScore.appendChild(clicks);

			Element dimension = doc.createElement("dimension");
			dimension.appendChild(doc.createTextNode(Integer.toString(record.getDimension())));
			highScore.appendChild(dimension);

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);

			StreamResult result = new StreamResult(file);
			transformer.transform(source, result);

			return true;
		} catch (TransformerException | SAXException | IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public StopWatch getTime(User user) {
		StopWatch sw = new StopWatch();
		try {
			File file = new File(folder + "status.xml");
			Document doc = dBuilder.parse(file);

			Element root = doc.getDocumentElement();
			NodeList nl = root.getChildNodes();

			if (nl != null && nl.getLength() > 0) {
				for (int i = 0; i < nl.getLength(); i++) {
					Node n = nl.item(i);
					if (n.getNodeType() == n.ELEMENT_NODE) {
						if (n.getNodeName().equals("status")) {
							if (((Element) n).getElementsByTagName("name").item(0).getTextContent()
									.equals(user.getUserName())) {
								sw.fromString(((Element) n).getElementsByTagName("time").item(0).getTextContent());

								break;
							}
						}
					}
				}
			}
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}

		return sw;
	}

	@Override
	public HighScoreTable getHighScores() {
		List<HighScoreRecord> hslist = new ArrayList<HighScoreRecord>();

		try {
			File file = new File(folder + "highScores.xml");
			Document doc = dBuilder.parse(file);

			Element root = doc.getDocumentElement();
			NodeList nl = root.getChildNodes();

			if (nl != null && nl.getLength() > 0) {
				for (int i = 0; i < nl.getLength(); i++) {
					Node n = nl.item(i);
					if (n.getNodeType() == n.ELEMENT_NODE) {
						if (n.getNodeName().equals("highScore")) {

							hslist.add(new HighScoreRecord(
									((Element) n).getElementsByTagName("name").item(0).getTextContent(),
									((Element) n).getElementsByTagName("time").item(0).getTextContent(),
									Integer.parseInt(
											((Element) n).getElementsByTagName("clicks").item(0).getTextContent()),
									Integer.parseInt(
											((Element) n).getElementsByTagName("dimension").item(0).getTextContent())));

						}
					}
				}
			}

		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}

		return new HighScoreTable(hslist);
	}

	@Override
	public Status loadStatus(User user) {
		try {
			String time = "", dimension = "";
			List<Card> cardList = new ArrayList<Card>();

			if (!isStatusExist(user))
				return new Status(user, new Pack(cardList, 0), new StopWatch(), 0);

			File file = new File(folder + "status.xml");
			Document doc = dBuilder.parse(file);

			Element root = doc.getDocumentElement();
			NodeList nl = root.getChildNodes();

			if (nl != null && nl.getLength() > 0) {
				for (int i = 0; i < nl.getLength(); i++) {
					Node n = nl.item(i);
					if (n.getNodeType() == n.ELEMENT_NODE) {
						if (n.getNodeName().equals("status")) {
							if (((Element) n).getElementsByTagName("name").item(0).getTextContent()
									.equals(user.getUserName())) {// ha name
								time = ((Element) n).getElementsByTagName("time").item(0).getTextContent();
								dimension = ((Element) n).getElementsByTagName("dimension").item(0).getTextContent();

								NodeList cardNodeList = ((Element) n).getElementsByTagName("card");
								for (int j = 0; j < cardNodeList.getLength(); j++) {
									File cardPath = new File(cardNodeList.item(j).getTextContent());
									String x = ((Element) cardNodeList.item(j)).getAttribute("X");
									String y = ((Element) cardNodeList.item(j)).getAttribute("Y");
									int dim = Integer.parseInt(dimension);
									Card card = new Card(cardPath, Integer.parseInt(x), Integer.parseInt(y), dim);
									card.setClicked(
											((Element) cardNodeList.item(j)).getAttribute("clicked").equals("true")
													? true : false);
									cardList.add(card);
								}
							}
						}
					}
				}
			}

			StopWatch sw = new StopWatch();
			sw.fromString(time);

			Status status = new Status(user, new Pack(cardList, Integer.parseInt(dimension)), sw,
					Integer.parseInt(dimension));
			return status;
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public boolean isStatusExist(User user) {
		try {
			File file = new File(folder + "status.xml");
			Document doc = dBuilder.parse(file);

			Element root = doc.getDocumentElement();
			NodeList nl = root.getChildNodes();

			if (nl != null && nl.getLength() > 0) {
				for (int i = 0; i < nl.getLength(); i++) {
					Node n = nl.item(i);
					if (n.getNodeType() == n.ELEMENT_NODE) {
						if (n.getNodeName().equals("status")) {
							if (((Element) n).getElementsByTagName("name").item(0).getTextContent()
									.equals(user.getUserName())) {
								return true;
							}
						}
					}
				}
			}
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean removeStatus(User user) {
		try {
			File file = new File(folder + "status.xml");
			Document doc = dBuilder.parse(file);

			Element root = doc.getDocumentElement();
			NodeList nl = root.getChildNodes();

			if (nl != null && nl.getLength() > 0) {
				for (int i = 0; i < nl.getLength(); i++) {
					Node n = nl.item(i);
					if (n.getNodeType() == n.ELEMENT_NODE) {
						if (n.getNodeName().equals("status")) {
							if (((Element) n).getElementsByTagName("name").item(0).getTextContent()
									.equals(user.getUserName())) {
								root.removeChild(n);
								return true;
							}
						}
					}
				}
			}
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean isUserExist(User user) {
		try {
			File file = new File(folder + "users.xml");
			Document doc = dBuilder.parse(file);

			Element root = doc.getDocumentElement();
			NodeList nl = root.getChildNodes();

			if (nl != null && nl.getLength() > 0) {
				for (int i = 0; i < nl.getLength(); i++) {
					Node n = nl.item(i);
					if (n.getNodeType() == n.ELEMENT_NODE) {
						if (n.getNodeName().equals("user")) {
							if (((Element) n).getElementsByTagName("name").item(0).getTextContent()
									.equals(user.getUserName())) {
								return true;
							}
						}
					}
				}
			}

		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean addProfile(User user) {
		if (isUserExist(user)) {
			return false;
		} else {
			try {
				File file = new File(folder + "users.xml");
				Document doc = dBuilder.parse(file);

				Element root = doc.getDocumentElement();

				Element usr = doc.createElement("user");

				Element name = doc.createElement("name");
				name.appendChild(doc.createTextNode(user.getUserName()));
				usr.appendChild(name);

				Element password = doc.createElement("password");
				password.appendChild(doc.createTextNode(user.getPassword()));
				usr.appendChild(password);

				root.appendChild(usr);

				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				DOMSource source = new DOMSource(doc);

				StreamResult result = new StreamResult(file);
				transformer.transform(source, result);

				return true;
			} catch (SAXException | IOException | TransformerException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public boolean loadProfile(User user) {
		try {
			File file = new File(folder + "users.xml");
			Document doc = dBuilder.parse(file);

			Element root = doc.getDocumentElement();
			NodeList nl = root.getChildNodes();

			if (nl != null && nl.getLength() > 0) {
				for (int i = 0; i < nl.getLength(); i++) {
					Node n = nl.item(i);
					if (n.getNodeType() == n.ELEMENT_NODE) {
						if (n.getNodeName().equals("user")) {
							if (((Element) n).getElementsByTagName("name").item(0).getTextContent()
									.equals(user.getUserName())) {
								if (((Element) n).getElementsByTagName("password").item(0).getTextContent()
										.equals(user.getPassword()))
									return true;
								else
									return false;
							}
						}
					}
				}
			}

		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}
