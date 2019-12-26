package com.mizholdings.util.XmlTool;

import cn.hutool.core.util.XmlUtil;
import org.w3c.dom.*;

import java.util.Iterator;

public class XmlTool {

    public static ElementMine readXML(String filePath) {
        Document document = XmlUtil.readXML(filePath);
        return new ElementMine(document.getDocumentElement());
    }

}









