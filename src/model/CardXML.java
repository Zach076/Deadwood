package model;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.File;

import core.*;
import model.BaseXML;

import java.util.*;

public class CardXML extends BaseXML {

  public CardXML(String path) {
    super(path);
  }

  public LinkedList<Card> getCards() {
    LinkedList<Card> cardList = new LinkedList<Card>();
    Element root = d.getDocumentElement();

    NodeList cards = root.getElementsByTagName("card");
    LinkedList<Card> rooms = new LinkedList<Card>();
    for (int i = 0; i < cards.getLength(); i++) {
      LinkedList<Role> roles = new LinkedList<Role>();
      Node card = cards.item(i);
      String name = card.getAttributes().getNamedItem("name").getNodeValue();
      String image = card.getAttributes().getNamedItem("img").getNodeValue();
      int budget = Integer.parseInt(card.getAttributes().getNamedItem("budget").getNodeValue());
      String set = "";

      NodeList children = card.getChildNodes();
      for (int k = 0; k < children.getLength(); k++) {
        Node c = children.item(k);
        if (c.getNodeName().equals("scene")) {
          set = c.getTextContent();
        } else if (c.getNodeName().equals("part")) {
          roles.add(BoardXML.parseRole(c, 0));
        }
      }
      Card c = new Card(budget, image, name, 0, set, roles);
      cardList.add(c);
    }
    return cardList;
  }

}