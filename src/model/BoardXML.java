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

public class BoardXML extends BaseXML {

  public BoardXML(String path) {
    super(path);
  }

  public LinkedList<Room> readBoard() {

    Element root = d.getDocumentElement();

    NodeList sets = root.getElementsByTagName("set");
    LinkedList<Room> rooms = new LinkedList<Room>();

    for (int i = 0; i < sets.getLength(); i++) {
      LinkedList<String> neighborsList = new LinkedList<String>();
      LinkedList<Role> extras = new LinkedList<Role>();
      LinkedList<Area> shotMarkers = new LinkedList<Area>();
      Area area = new Area();
      Node set = sets.item(i);

      NodeList children = set.getChildNodes();
      for (int k = 0; k < children.getLength(); k++) {
        Node c = children.item(k);
        if (c.getNodeName().equals("neighbors")) {
          NodeList neighbors = c.getChildNodes();

          for (int j = 0; j < neighbors.getLength(); j++) {
            Node n = neighbors.item(j);
            if (n.getNodeType() == Node.ELEMENT_NODE) {
              String neightborName = n.getAttributes().getNamedItem("name").getNodeValue();
              neighborsList.add(neightborName);
            }
          }

        } else if (c.getNodeName().equals("takes")) {
          NodeList takeslist = c.getChildNodes();

          for (int j = 0; j < takeslist.getLength(); j++) {
            Node n = takeslist.item(j);
            if (n.getNodeType() == Node.ELEMENT_NODE) {
              //takes = takes + 1;
              NodeList aList = n.getChildNodes();
              Node aNode = aList.item(0);
              Area a = parsePosition(aNode);
              shotMarkers.add(a);
            }
          }
        } else if (c.getNodeName().equals("parts")) {
          NodeList parts = c.getChildNodes();
          for (int j = 0; j < parts.getLength(); j++) {
            Node n = parts.item(j);

            if (n.getNodeType() == Node.ELEMENT_NODE) {

              extras.add(parseRole(n, 1));
            }
          }
        } else if (c.getNodeName().equals("area")){
          area = parsePosition(c);
        }
      }

      String name = set.getAttributes().getNamedItem("name").getNodeValue();

      Scene s = new Scene(shotMarkers, extras, name, neighborsList);

      s.area = area;
      rooms.add(s);
    }

    rooms.add(this.getOffice());
    rooms.add(this.getTrailer());
    return rooms;
  }

  private Room getOffice() {
    Element root = d.getDocumentElement();
    NodeList officeNode = root.getElementsByTagName("office");
    LinkedList<String> officeNeighbors = new LinkedList<String>();
    NodeList officeChildren = officeNode.item(0).getChildNodes();
    Area area = new Area();
    for (int x = 0; x < officeChildren.getLength(); x++) {
      Node c = officeChildren.item(x);
      if (c.getNodeName().equals("neighbors")) {
        NodeList neighbors = c.getChildNodes();
        for (int j = 0; j < neighbors.getLength(); j++) {
          Node n = neighbors.item(j);
          if (n.getNodeType() == Node.ELEMENT_NODE) {
            String neightborName = n.getAttributes().getNamedItem("name").getNodeValue();
            officeNeighbors.add(neightborName);
          }
        }
      } else if (c.getNodeName().equals("area")){
      area = parsePosition(c);
    }

    }
    Room r = new Room(RoomType.CastingOffice, "office", officeNeighbors);
    r.area = area;
    return r;

  }

  private Room getTrailer(){
    Element root = d.getDocumentElement();
    NodeList trailer = root.getElementsByTagName("trailer");
    String name = "trailer";
    LinkedList<String> officeNeighbors = new LinkedList<String>();
    NodeList officeChildren = trailer.item(0).getChildNodes();
    Area area = new Area();
    for (int x = 0; x < officeChildren.getLength(); x++) {
      Node c = officeChildren.item(x);
      if (c.getNodeName().equals("neighbors")) {
        NodeList neighbors = c.getChildNodes();
        for (int j = 0; j < neighbors.getLength(); j++) {
          Node n = neighbors.item(j);
          if (n.getNodeType() == Node.ELEMENT_NODE) {
            String neightborName = n.getAttributes().getNamedItem("name").getNodeValue();
            officeNeighbors.add(neightborName);
          }
        }
      }else if (c.getNodeName().equals("area")){
        area = parsePosition(c);
      }
    }
    Room r = new Room(RoomType.Trailer, "trailer", officeNeighbors);
    r.area = area;

    return r;
  }

  public static Role parseRole(Node n, int type) {
    Area area = new Area();
    String name = n.getAttributes().getNamedItem("name").getNodeValue();
    int level = Integer.parseInt(n.getAttributes().getNamedItem("level").getNodeValue());
    String line;
    NodeList atts = n.getChildNodes();
    for (int q = 0; q < atts.getLength(); q++) {
      Node x = atts.item(q);
      if (x.getNodeName().equals("line")) {
        line = x.getNodeValue();
      } else if(x.getNodeName().equals("area")){
        area = parsePosition(x);
      }
    }
    Role r = new Role(name, level, type, "");
    r.area = area;
    return r;
  }

}