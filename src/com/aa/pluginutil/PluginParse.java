package com.aa.pluginutil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;





import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.aa.model.Plugin;
import com.aa.model.PluginRunConfig;
import com.aa.model.Process;
import com.aa.model.ProcessType;

public class PluginParse {
	private static final Logger log= Logger.getLogger(PluginParse.class);
	public static Plugin parse(String xmlPath) throws ParserConfigurationException, SAXException, IOException
	{
		Plugin plugin= new Plugin();
		PluginRunConfig config=null;
		File f= new File(xmlPath);
		if(!f.exists())
		{
			throw new FileNotFoundException();
		}
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		DocumentBuilder builder=factory.newDocumentBuilder();
		InputStream is= new FileInputStream(f);
		Document doc=builder.parse(is);
		
		NodeList runNodeList=doc.getElementsByTagName("run");
		for (int i = 0; i < runNodeList.getLength(); i++) {
			config= new PluginRunConfig();
			Node runNode=runNodeList.item(i);
			if(runNode.getNodeType()==Node.ELEMENT_NODE)
			{
				Element runElement=(Element)runNode;
				List<Process> processes= new ArrayList<>();
				if(runElement.hasAttributes())
				{
					config.setRunName(runElement.getAttribute("name"));
					config.setClassName(runElement.getAttribute("class"));
					
				}
				NodeList processNodeList=doc.getElementsByTagName("process");
				for(int j=0;j<processNodeList.getLength();j++)
				{
					Process p=new Process();
					Node processNode=processNodeList.item(j);
					if(processNode.getNodeType()==Node.ELEMENT_NODE)
					{
						Element processElement=(Element) processNode;
						switch(processElement.getAttribute("type"))
						{
						case "codeBase":
							p.setType(ProcessType.CODEBASE);
							break;
						case "individual":
							p.setType(ProcessType.INDIVIDUAL);
							break;
							default:
								log.info("In Apropriate Type");return null;
						}
					}
					processes.add(p);
				}
				config.setProcesses(processes);
			}
			
		}
		plugin.setConfig(config);
		plugin.setAuthorName(getVal(doc, "author", "name"));
		plugin.setIconPath(getVal(doc, "icon", "path"));
		plugin.setVersion(getVal(doc, "version", "val"));
		plugin.setName(getVal(doc, "name", "val"));
		plugin.setJarFileName(getVal(doc, "jarname", "val"));
		return plugin;
	}
	private static String getVal(Document doc,String tagName,String attributeName)
	{
		NodeList otherNode=doc.getElementsByTagName(tagName);
		if(otherNode.getLength()>0)
		{
			Node node=otherNode.item(0);
			if(node.getNodeType()==Node.ELEMENT_NODE)
			{
				Element element=(Element)node;
				if(element.hasAttributes())
				{
					return element.getAttribute(attributeName);
				}
			}
		}
		return "";
		
	}
	public static void main(String[] args) {
		try {
		Plugin p=	parse("/home/kumar/aaPlugin.xml");
		log.info(p);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
