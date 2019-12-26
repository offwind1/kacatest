package com.mizholdings.util.XmlTool;

import org.w3c.dom.Element;

public class ElementMine {
    Element element;

    public ElementMine(Element element) {
        this.element = element;
    }

    public NodeListMine getElementsByTagName(String key) {
        return new NodeListMine(element.getElementsByTagName(key));
    }

    public String getAttribute(String key) {
        return element.getAttribute(key);
    }

    public ElementMine getElementsByTagNameAndValue(String tag, String value) {
        for (ElementMine el : getElementsByTagName(tag)) {
            if (el.getAttribute("value").toLowerCase().equals(value.toLowerCase())) {
                return el;
            }
        }
        throw new RuntimeException("未找到接口");
    }

    public boolean hasAttribute(String name) {
        return element.hasAttribute(name);
    }

}
