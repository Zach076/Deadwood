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

public class BaseXML {

  protected Document d;

  public BaseXML(String filePath) {
    try {
      this.d = getDocFromFile(filePath);
    } catch (ParserConfigurationException p) {
      System.out.println("XML parse failure");
    }
  }

  public Document getDocFromFile(String filename) throws ParserConfigurationException {
    {

      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = null;

      try {
        doc = db.parse(filename);
      } catch (Exception ex) {
        System.out.println("XML parse failure");
        ex.printStackTrace();
      }
      return doc;
    } // exception handling

  }

  public static Area parsePosition(Node n){
    Area a = new Area();
    a.h = Integer.parseInt(n.getAttributes().getNamedItem("h").getNodeValue());
    a.w = Integer.parseInt(n.getAttributes().getNamedItem("w").getNodeValue());
    a.x = Integer.parseInt(n.getAttributes().getNamedItem("x").getNodeValue());
    a.y = Integer.parseInt(n.getAttributes().getNamedItem("y").getNodeValue());
  return a;
  }

}